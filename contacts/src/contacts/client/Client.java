package contacts.client;

import java.io.*;
import java.net.*;

import contacts.parser.ParsedAddressBook;

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
	
	/**The local copy of the address book*/
	private ParsedAddressBook addressBook;

	private int port;

	public Client(int _port) throws IOException {
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

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		Client c;
		try {
			c = new Client(1818);
			c.getUserInput();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	private void getUserInput() throws IOException {
		while (!finished) {

			// Read command in and break in words
			String command = userReader.readLine();
			command = command.toLowerCase();

			switch (command) {
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
				getServerOutput();
				
				sendAddressBook();

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

	private void sendAddressBook() {

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

}
