package contacts.client;

import java.io.*;
import java.net.*;

import contacts.adressbook.*;
import contacts.parser.ImaginaryFriendException;
import contacts.parser.ParseException;
import contacts.parser.ParsedAddressBook;
import contacts.parser.ThisIsntMutualException;
import contacts.parser.XMLParser;

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

	private int port;

	public Client(int _port) throws IOException, ParseException,
			ImaginaryFriendException, ThisIsntMutualException {
		ParsedAddressBook pab = XMLParser.parse("src/contacts/example.xml");
		addressBook = new AddressBook(pab);

		port = _port;
		socket = new Socket("localhost", port);
		finished = false;
		inputStream = socket.getInputStream();
		outputStream = socket.getOutputStream();
		userReader = new BufferedReader(new InputStreamReader(System.in));
		servReader = new BufferedReader(new InputStreamReader(inputStream));
		userWriter = new BufferedWriter(new OutputStreamWriter(System.out));
		servWriter = new BufferedWriter(new OutputStreamWriter(outputStream));
	}


	private void getUserInput() throws IOException {
		while (!finished) {

			// Read command in and break in words
			String command = userReader.readLine();
			command = command.toLowerCase();

			switch (command) {
			case "add":
				break;
			case "remove":
				// prompts for name of contact to be deleted
				System.out.print("name:");
				command = userReader.readLine();

				// contact should be deleted and references from friends deleted

				break;
			case "group":
				// prompts user for group name
				System.out.print("group name:");

				// prints out all members of that group
				break;
			case "pull":
				// retrieves the latest version of the addressbook from the
				// server
				// converts that XML version into an addressbook
				// sets addressbook equal to that newly parsed one

				sendToServer("PULL");

				getServerOutput();
				receiveAddressBook();

				break;
			case "push":
				// writes Client version of addressbook to XML
				// sends that XML version to the server
				sendToServer("PUSH");
				sendAddressBook();
				getServerOutput();
				break;
			case "query path":
				// do stuff
				sendToServer("QUERY PATH");
				getServerOutput();
				break;
			case "query mutual":
				// do stuff
				sendToServer("QUERY MUTUAL");
				getServerOutput();
				break;
			case "quit":
				// saves addressbook and exits the program
				finished = true;
				break;
			default:
				System.out
						.println("Please input one of the following: remove, "
								+ "group, pull, push, query path, query mutual, quit");
				break;
			}
		}

	}

	private void sendAddressBook() throws IOException {
		sendToServer(addressBook.toXML());
	}

	private void receiveAddressBook() {

	}

	private void sendToServer(String output) throws IOException {
		if (output != null) {
			servWriter.write(output);
		}
		servWriter.flush();
		socket.shutdownOutput();
	}

	private void getServerOutput() throws IOException {

		String content = servReader.readLine();
		while (content != null) {
			userWriter.write(content + "\n");
			content = servReader.readLine();
		}
		userWriter.flush();
		socket.shutdownInput();
		finished = false;
		getUserInput();
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

		Client c;
		c = new Client(1818);
		c.getUserInput();
	}
}
