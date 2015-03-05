package contacts.client;

import java.io.*;
import java.net.Socket;
import contacts.adressbook.*;

public class Client {

	/** Buffered reader for user input */
	private BufferedReader user;
	
	/** indicates if interaction is over*/
	private boolean finished; 
	
	/** The socket to interact from */
	private Socket socket;
	
	/**input and output streams*/
	private InputStream inputStream;
	private OutputStream outputStream;

	/**the writer that flushes to the server*/
	private BufferedWriter writer;
	
	/**The local copy of the address book*/
	private AddressBook addressBook;
	
	private int PORT;
	
	public Client(int port) throws IOException {
		user = new BufferedReader(new InputStreamReader(System.in));	
		socket = new Socket("localhost", PORT);
		inputStream = socket.getInputStream();
		outputStream = socket.getOutputStream();
		finished = false;
		PORT = port;
	}
	
	private void sendAddressBook() {
		//TODO: convert addressBook to XML and send line by line
	}

	private void receiveAddressBook() {
		//TODO: receive addressbook line by line and covert to Addressbook
		//then store as current addressbook
	}
	
	private void listenToUser() throws IOException {
		while(!finished) {
			// Read command in and break in words
			String command = user.readLine();
			
			switch(command) {
				case "remove" : 
					//prompts for name of contact to be deleted					
					System.out.print("name:");
					command = user.readLine();
					//contact should be deleted and references from friends deleted
					
					//test code
					outputStream.write(1);
					user.close();
					outputStream.flush();
					socket.shutdownOutput();
					waitForServer();
					break;
				case "group" :
					//prompts user for group name
					System.out.print("group name:");

					//prints out all members of that group
					break;
				case "pull" :
					//retrieves the latest version of the addressbook from the server
					//converts that XML version into an addressbook
					//sets addressbook equal to that newly parsed one
					receiveAddressBook();
					
					break;
				case "push" :
					//writes Client version of addressbook to XML
					//sends that XML version to the server
					sendAddressBook();
					
					break;
				case "query path" :
					//do stuff
					break;
				case "query mutual" :
					//do stuff
					break;
				case "quit" :
					//saves addressbook and exits the program
					finished = true;
					break;
				default :
					System.out.println("Please input one of the following: remove, "
							+ "group, pull, push, query path, query mutual, quit");
					break;
			}
			
		}
	}
	
	private void waitForServer() throws IOException {
		BufferedReader input = new BufferedReader(new InputStreamReader(
				inputStream));
		BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(
				System.out));

		int content = input.read();
		while (content != -1) {
			writer.write(content);

			content = input.read();

		}
		writer.flush();
		socket.shutdownInput();	
		listenToUser();
	}
	
	
	public static void main (String[] args) {
		try {
			Client c = new Client(8080);
			c.listenToUser();
			c.waitForServer();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
