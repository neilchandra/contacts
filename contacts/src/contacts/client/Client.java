package contacts.client;

import java.io.*;
import java.net.*;

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
	private BufferedReader servReader;

	/** Writers that writer to the server and the user */
	private BufferedWriter servWriter;
	private BufferedWriter userWriter;

	/** The local copy of the address book */
	private AddressBook addressBook;

	private String queryFirst, querySecond;

	private int port;

	public Client(String filePath, int _port) throws IOException,
			ParseException, ImaginaryFriendException, ThisIsntMutualException {
		addressBook = new AddressBook(XMLParser.parse(filePath));
		port = _port;
		userReader = new BufferedReader(new InputStreamReader(System.in));
		userWriter = new BufferedWriter(new OutputStreamWriter(System.out));
		finished = false;
		System.out.println(addressBook.toXML());
	}

	public String getCommand(String prompt) throws IOException {
		userReader = new BufferedReader(new InputStreamReader(System.in));

		System.out.print(prompt);
		String command = userReader.readLine();

		return command;
	}

	public void dialog() throws IOException, ImaginaryFriendException,
			ThisIsntMutualException, ParseException {
		while (!finished) {
			getUserInput();
		}
	}

	private void sendToServer(String message) throws IOException {

		socket = new Socket("localhost", port);
		inputStream = socket.getInputStream();
		outputStream = socket.getOutputStream();

		System.out.println("client sent: " + message);
		outputStream.write(message.getBytes());
		outputStream.flush();
		socket.shutdownOutput();
	}

	private void getServerOutput() throws IOException {
		StringBuilder sb = new StringBuilder();

		int content = inputStream.read();
		while (content != -1) {
			sb.append((char) content);
			content = inputStream.read();
		}
		System.out.println("client received: " + sb.toString());

		if (sb.toString().equals("PUSH CALLED")) {
			sendAddressBook();
			getServerOutput();
		} else if (sb.toString().equals("QUERY PATH CALLED")
				|| sb.toString().equals("QUERY MUTUAL CALLED")) {
			sendToServer(queryFirst + "\n" + querySecond);
			getServerOutput();
		}
	}

	private void getUserInput() throws IOException, ThisIsntMutualException {

		// Read command in and break in words
		String command = getCommand("Input a command:");

		command = command.toLowerCase();

		switch (command) {
		case "add":
			String person = getCommand("name: ");
			String number = getCommand("number: ");
			String friendsString = getCommand("friends: ");
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

			sendToServer("PULL");
			try {
				receiveAddressBook();
			} catch (ImaginaryFriendException e) {
				System.out
						.println("address book was not received: ImaginaryFriendException");
			}
			System.out.println("new: " + addressBook.toXML());

			break;
		case "push":
			// writes Client version of addressbook to XML
			// sends that XML version to the server
			sendToServer("PUSH");
			getServerOutput();
			// sendToServer(addressBook.toXML());

			System.out.println(getConfirmation());
			break;
		case "query path":
			queryFirst = getCommand("Person 1: ");
			querySecond = getCommand("Person 2: ");
			sendToServer("QUERY PATH");
			getServerOutput();
			break;
		case "query mutual":
			queryFirst = getCommand("Person 1: ");
			querySecond = getCommand("Person 2: ");
			sendToServer("QUERY MUTUAL");
			getServerOutput();
			break;
		case "quit":
			// saves addressbook and exits the program
			finished = true;
			break;
		default:
			System.out.println("Please input one of the following: remove, "
					+ "group, pull, push, query path, query mutual, quit");
			getUserInput();
			break;
		}

	}

	private void sendAddressBook() throws IOException {
		sendToServer(addressBook.toXML());
	}

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

	private String getConfirmation() throws IOException {
		StringBuilder sb = new StringBuilder();

		int content = inputStream.read();
		while (content != -1 && content != 10) {
			sb.append((char) content);
			content = inputStream.read();
		}
		return sb.toString();

	}

	/**
	 * @param args
	 * @throws ThisIsntMutualException
	 * @throws ImaginaryFriendException
	 * @throws ParseException
	 * @throws IOException
	 */
	public static void main(String[] args) throws ParseException,
			ImaginaryFriendException, ThisIsntMutualException, IOException {
		String filePath = args[0];
		Integer _port = Integer.parseInt(args[1]);

		Client c = new Client(filePath, _port);
		c.dialog();

	}
}
