package contacts.client;

import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.NoSuchElementException;

import contacts.adressbook.*;
import contacts.parser.*;

public class Client {

	/** indicates if interaction is over */
	private boolean finished;

	/** The socket to interact from */
	private Socket socket;

	/** input and output streams */
	private InputStream inputStream;
	private OutputStream outputStream;

	/** Buffered readers for user input and server signals */
	private BufferedReader userReader;

	/** The local copy of the address book */
	private AddressBook addressBook;

	private String queryFirst, querySecond;

	/** the port of Client/Server interaction */
	private int port;
	private String host;
	private String filePath;

	/**
	 * Constructs a Client Object
	 * 
	 * @param filePath
	 *            - the path of the xml file to start form
	 * @param _port
	 *            - the port on which to communicate with the server
	 * @throws IOException
	 *             - in case of Server/Client initialization error
	 * @throws ParseException
	 *             - XML unable to be read
	 * @throws ImaginaryFriendException
	 *             - friend doesn't exist in addressBook
	 * @throws ThisIsntMutualException
	 *             - friendship not symmetric
	 */
	public Client(String _filePath, String _host, int _port) throws IOException,
			ImaginaryFriendException, ThisIsntMutualException {
		filePath = _filePath;
		host = _host;
		try {
			addressBook = new AddressBook(XMLParser.parse(filePath));
		} catch (Exception e) {
			System.out.println("Client couldn't parse XML file!");
			System.exit(0);
		}
		port = _port;
		userReader = new BufferedReader(new InputStreamReader(System.in));
		finished = false;
		System.out.println(addressBook.toXML());
	}

	/**
	 * Prompts the user for a command to act upon
	 * 
	 * @param prompt
	 *            - the string requesting the client for input
	 * @return - the client's response to the prompt
	 * @throws IOException
	 *             - if readLine fails
	 */
	public String getCommand(String prompt) throws IOException {
		userReader = new BufferedReader(new InputStreamReader(System.in));

		System.out.print(prompt);
		String command = userReader.readLine();

		return command;
	}

	/**
	 * Runs while the client is not finished
	 * 
	 * @throws IOException
	 * @throws ImaginaryFriendException
	 * @throws ThisIsntMutualException
	 * @throws ParseException
	 */
	public void dialog() throws IOException {
		while (!finished) {
			try {
				getUserInput();
			} catch (ThisIsntMutualException e) {
				System.out.println("This isn't mutual error");
			}
		}
	}

	/**
	 * Sends a message to the server
	 * 
	 * @param message
	 *            - the String to be sent to the server
	 * @throws IOException
	 *             - if connection fails for some reason
	 */
	private void sendToServer(String message) throws IOException {

		socket = new Socket(host, port);
		inputStream = socket.getInputStream();
		outputStream = socket.getOutputStream();
		outputStream.write(message.getBytes());
		outputStream.flush();
		socket.shutdownOutput();
	}

	/**
	 * Receives the output from the server
	 * 
	 * @throws IOException
	 *             - if connection is interrupted
	 */
	private void getServerOutput() throws IOException {
		StringBuilder sb = new StringBuilder();

		int content = inputStream.read();
		while (content != -1) {
			sb.append((char) content);
			content = inputStream.read();
		}

		if (sb.toString().equals("PUSH CALLED")) {
			sendAddressBook();
			getServerOutput();
		} else if (sb.toString().equals("QUERY PATH CALLED")
				|| sb.toString().equals("QUERY MUTUAL CALLED")) {
			sendToServer(queryFirst + "\n" + querySecond);
			getServerOutput();
		} else {
			System.out.println(sb.toString());
		}
	}

	/**
	 * Helps find the index of the group in which to add a new contact
	 * 
	 * @param end
	 *            - the last possible index the client could select
	 * @return - the index the client chose
	 * @throws IOException
	 *             - if getCommand fails
	 */
	private int getGroupIndex(int end) throws IOException {
		System.out.println("Please input a number between 1 and " + (end + 1));
		String selectedGroup = getCommand("index: ");

		try {
			int groupIndex = Integer.parseInt(selectedGroup);

			while (groupIndex < 1 || groupIndex > (end + 1)) {
				System.out.println("Please input a number between 1 and "
						+ (end + 1));
				selectedGroup = getCommand("index: ");
				groupIndex = Integer.parseInt(selectedGroup);
			}
			return groupIndex - 1;
		} catch (NumberFormatException e) {
			getGroupIndex(end);
		}

		return -1;
	}

	/**
	 * Gets the group in which the client wants to add a new contact
	 * 
	 * @param depth
	 *            - the current distance from the top of addressbook
	 * @param current
	 *            - the name of the current group
	 * @return - the Group in which the client wants to add a contact
	 * @throws IOException
	 *             - if getGroupIndex fails
	 */
	private Group getGroup(int depth, String current) throws IOException {

		ArrayList<Group> group;
		if (depth == 0) {
			group = addressBook.listGroups();
			System.out.println("Current Group: none");
		} else {
			group = addressBook.listSubGroups(current);
			System.out.println("Current Group: " + current);
		}
		int groupSize;
		int counter = 0;
		if (group != null) {
			groupSize = group.size();
			for (Group g : group) {
				System.out.print("(" + (counter + 1) + ") " + g.getName()
						+ "  ");
				counter++;
			}
		} else {
			groupSize = 0;
		}

		System.out.print("(" + (counter + 1) + ") new group  ");

		if (depth > 0) {
			counter++;
			System.out.print("(" + (counter + 1) + ") current group");
		}
		System.out.println(""); // newline

		int groupIndex = getGroupIndex(counter);

		if (groupIndex == groupSize) { // create new group
			String groupName = getCommand("Group Name: ");
			try {
				if (depth == 0) {
					addressBook.addGroup(groupName);
					if (addressBook.nameToGroup(groupName) != null)
						return addressBook.nameToGroup(groupName);
					else
						System.out.println("Error in adding group!");
				} else
					addressBook.addGroup(groupName, current);
				if (addressBook.nameToGroup(groupName) != null)
					return addressBook.nameToGroup(groupName);
				else
					System.out.println("Error in adding group!");
			} catch (ParseException e) {
				System.out.println("Group couldn't be created!");
			}
		} else if (depth > 0 && groupIndex == groupSize + 1) { // current group
			Group toReturn = addressBook.nameToGroup(current);
			if (toReturn == null)
				System.out.println("Group couldn't be passed!");
			else
				return toReturn;
		} else if (groupIndex < groupSize && group != null) {
			return getGroup(depth + 1, group.get(groupIndex).getName());
		}

		// return null if things go wrong
		return null;
	}

	/**
	 * Gets ID's for friends
	 * 
	 * @return - an array of ints representing legal Contacts
	 * @throws IOException
	 *             - if getCommand fails
	 */
	private int[] getFriends() throws IOException {
		String friendString = getCommand("friends (separate by \", \"): ");		
		String[] friends = friendString.split(", ");
		
		if(friendString.equals("")){
			return new int [0];
		}
		
		int[] friendsID = new int[friends.length];
		
		
		for (int i = 0; i < friends.length; i++) {
			try {
				friendsID[i] = addressBook.nameToInt(friends[i]);
			} catch (NoSuchElementException e) {
				System.out.println("No contact found called " + friends[i]);
				return getFriends();
			}
		}
		return friendsID;
	}

	/**
	 * Asks the client to input a legal command and acts upon that legal command
	 * 
	 * @throws IOException
	 *             - if getCommand fails
	 * @throws ThisIsntMutualException
	 *             - in case friendship isn't symmetric
	 */
	private void getUserInput() throws IOException, ThisIsntMutualException {

		// Read command in and break in words
		String command = getCommand("Input a command:");

		command = command.toLowerCase();

		switch (command) {
		case "add":
			String person = getCommand("name: ");
			String number = getCommand("number: ");
			int[] friends = getFriends();
			Group g = getGroup(0, null);
			if (g == null) {
				System.out.println("Couldn't add contact!");
			} else {
				try {
					Contact c = addressBook.makeContact(person, number,
							person.hashCode(), friends);
					addressBook.addContact(c, g.getName());
				} catch (ParseException e) {
					System.out.println("Couldn't create Contact, parse error!");
				} catch (ImaginaryFriendException e) {
					System.out
							.println("Couldn't create Contact, friend error!");
				}
			}

			break;
		case "remove":
			// prompts for name of contact to be deleted
			String name = getCommand("name: ");
			try {
				addressBook.removeContact(name);
			} catch (ImaginaryFriendException e) {
				System.out.println(name + " couldn't be removed!");
			}

			break;
		case "group":
			// prompts user for group name
			String groupName = getCommand("group name: ");

			// prints out all members of that group
			break;
		case "pull":

			try {
				sendToServer("PULL");
				receiveAddressBook();
			} catch (ImaginaryFriendException e) {
				System.out
						.println("address book was not received: ImaginaryFriendException");
			} catch (ConnectException e){
				System.out.println("Couldn't connect to Server!");
			}
			System.out.println("new: " + addressBook.toXML());

			break;
		case "push":
			try {
				sendToServer("PUSH");
				getServerOutput();
			} catch (ConnectException e) {
				System.out.println("Couldn't connect to Server!");
			}
			break;
		case "query path":
			try {
				queryFirst = getCommand("Person 1: ");
				querySecond = getCommand("Person 2: ");
				sendToServer("QUERY PATH");
				getServerOutput();
			} catch (ConnectException e) {
				System.out.println("Couldn't connect to Server!");
			}
			break;
		case "query mutual":
			try {
				queryFirst = getCommand("Person 1: ");
				querySecond = getCommand("Person 2: ");
				sendToServer("QUERY MUTUAL");
				getServerOutput();
			} catch (ConnectException e) {
				System.out.println("Couldn't connect to Server!");
			}
			break;
		case "quit":
			finished = true;
			BufferedWriter writer = new BufferedWriter(new FileWriter(filePath));
			writer.write(addressBook.toXML());
			writer.flush();
			writer.close();
			System.out.println("Goodbye!");
			break;
		default:
			System.out.println("Please input one of the following: remove, "
					+ "group, pull, push, query path, query mutual, quit");
			getUserInput();
			break;
		}

	}

	/**
	 * Sends the local copy of the addressBook to the server
	 * 
	 * @throws IOException
	 *             - in case sendToServer fails
	 */
	private void sendAddressBook() throws IOException {
		sendToServer(addressBook.toXML());
	}

	/**
	 * Receives the addressBook from the server and sets the local copy equal to
	 * it
	 * 
	 * @throws IOException
	 * @throws ImaginaryFriendException
	 * @throws ThisIsntMutualException
	 */
	private void receiveAddressBook() throws IOException,
			ImaginaryFriendException, ThisIsntMutualException {
		StringBuilder sb = new StringBuilder();

		int content = inputStream.read();
		while (content != -1 && content != 10) {
			sb.append((char) content);
			content = inputStream.read();
		}
		String book = sb.toString();

		if (book != null) {
			try {
				addressBook = new AddressBook(XMLParser.parseString(book));
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * @param args
	 *            - <filename> <port>
	 * @throws ThisIsntMutualException
	 * @throws ImaginaryFriendException
	 * @throws ParseException
	 * @throws IOException
	 */
	public static void main(String[] args) throws ParseException,
			ImaginaryFriendException, ThisIsntMutualException, IOException {
		String filePath = args[0];
		String host = args[1];
		Integer _port = Integer.parseInt(args[2]);

		Client c = new Client(filePath, host, _port);
		c.dialog();

	}
}
