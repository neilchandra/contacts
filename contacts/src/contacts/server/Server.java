package contacts.server;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

import contacts.parser.ParsedAddressBook;

public class Server {

	/** The port on which to interact with the client */
	private int PORT;
	
	/** The client socket */
	public Socket socket;
	
	/** The server Socket */
	public ServerSocket serv;
	
	/**Buffered inputs and output */
	private BufferedReader input;
	private BufferedWriter writer;
	
	/**The local copy of the address book*/
	private ParsedAddressBook addressBook;
	
	public Server(int port) throws IOException {
		port = PORT;
		serv = new ServerSocket(PORT);
	}
	
	public void dialog() {
		while(true){
			try {
				Socket socket = serv.accept(); //connect with socket
				input = new BufferedReader(new InputStreamReader(
						socket.getInputStream()));
				writer = new BufferedWriter(new OutputStreamWriter(
						socket.getOutputStream()));

				int content = input.read();
				while (content != -1) {
					//reactToUserCommand(content);
					content = input.read();

				}
				writer.flush();
				socket.shutdownOutput();
			} catch (IOException e) {
				System.out.println("Server couldn't connect!");
			}
			
		}
	}
	
	
	private void reactToUserCommand(String command) throws IOException {
		command = command.toLowerCase();
		switch(command) {
			case "push" :
				receiveAddressBook();
				//do stuff
				break;
			case "pull" :
				sendAddressBook();
				//do stuff
				break;
			case "query path" :
				//do stuff
				break;
			case "query mutual" :
				//do stuff
				break;
			default :
				//do nothing basically
				break;
		}
		
		//just echoes for now
		writer.write(1);		
	}
	
	private void sendAddressBook() {
		//TODO: convert addressBook to XML and send line by line
	}

	private void receiveAddressBook() {
		//TODO: receive addressbook line by line and covert to Addressbook
		//then store as current addressbook
	}
	
	
	public static void main (String[] args) {
		try {
			Server s = new Server(10);
			s.dialog();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
