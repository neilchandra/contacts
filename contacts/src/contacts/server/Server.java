package contacts.server;

import java.io.*;
import java.net.*;

import contacts.parser.ParsedAddressBook;

public class Server {
	
	private boolean finished;
	private int port;
	public Socket socket;
	public ServerSocket serv;
	private InputStream inputStream;
	private OutputStream outputStream;
	BufferedReader clientInput;
	BufferedWriter clientWriter;
	
	/**The local copy of the address book*/
	private ParsedAddressBook addressBook;
	
	public Server(int _port) throws IOException {
		port = _port;
		serv = new ServerSocket(port);
		finished = false;
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			Server s = new Server(1818);
			s.getClientInput();
		} catch (IOException e) {
			e.printStackTrace();
		}
		

	}
	
	public void getClientInput() {
		
		while(!finished){
			try {
				socket = serv.accept(); //connect with socket
				BufferedReader clientInput = new BufferedReader(new InputStreamReader(
						socket.getInputStream()));

				String content = clientInput.readLine();
				while (content != null) {
					reactToClient(content, socket);
				}
				clientWriter.flush();
				socket.shutdownOutput();
			} catch (IOException e) {
				//System.out.println("Server couldn't connect!");
			}
			
		}
	}
	
	private void sendToClient(String content, Socket socket) throws IOException {
		clientWriter = new BufferedWriter(new OutputStreamWriter(
				socket.getOutputStream()));
		
		if(content != null) {
			clientWriter.write(content + " recieved");
			clientWriter.flush();
			socket.shutdownOutput();
		}
	}

	private void reactToClient(String content, Socket socket) throws IOException {
		
		content = content.toLowerCase();
		switch(content) {
			case "pull" :
				sendToClient(content, socket);
				break;
			case "push" :
				//do something
				sendToClient(content, socket);
				break;
			case "query mutual":
				sendToClient(content, socket);
				//do something
				break;
			case "query path" :
				sendToClient(content, socket);
				//do something
				break;
		}
		
	}

}
