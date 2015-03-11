package contacts.server;

import java.io.*;
import java.net.*;

import contacts.adressbook.*;
import contacts.parser.ImaginaryFriendException;
import contacts.parser.ParseException;
import contacts.parser.ParsedAddressBook;
import contacts.parser.ThisIsntMutualException;
import contacts.parser.XMLParser;

public class Server {

	private boolean finished;
	private int port;
	public Socket socket;
	public ServerSocket serv;
	private InputStream inputStream;
	private OutputStream outputStream;
	BufferedReader clientInput;
	BufferedWriter clientWriter;

	private Graph graph;

	public Server(String xml, int _port) throws IOException, ParseException,
			ImaginaryFriendException, ThisIsntMutualException {
		this.graph = new Graph(xml);
		port = _port;
		serv = new ServerSocket(port);
		finished = false;
	}

	public void listen() throws ImaginaryFriendException,
			ThisIsntMutualException, ParseException, IOException {

		while (true) {		
			socket = serv.accept(); 

			String input = receiveInput();
			sendToClient(input, socket);

		}
	}
	
	private void sendToClient(String message, Socket sock) throws IOException {
		outputStream = sock.getOutputStream();
		outputStream.write(message.getBytes());
		System.out.println("server sent:" + message);
		outputStream.flush();
		//System.out.println("message: " + sb.toString());
		socket.shutdownOutput();
	}
	
	private String receiveInput() throws IOException {
		BufferedReader clientInput = new BufferedReader(
				new InputStreamReader(socket.getInputStream()));
		OutputStream outputStream = socket.getOutputStream();
		clientWriter = new BufferedWriter(new OutputStreamWriter(outputStream));
		StringBuilder sb = new StringBuilder();

		int content = clientInput.read();
		while (content != -1 && content != 10) { //10 = \n char
			sb.append((char)content);								
			content = clientInput.read();
		}
		socket.shutdownInput();
		
		String message = sb.toString();
		
		switch(message) {
			case "PULL" :
				message = graph.toXML();
				break;
		}
		
		return message;
	}


	private void reactToClient(String content, Socket socket)
			throws IOException, ImaginaryFriendException,
			ThisIsntMutualException, ParseException {

		content = content.toLowerCase();
		switch (content) {
		case "PULL":
			sendToClient(graph.toXML(), socket);
			break;
		case "PUSH":
			sendToClient(content, socket);
			/*
			 * String xml = clientInput.readLine(); if (content != null) { graph
			 * = new Graph(xml); } clientWriter.write("xml received!");
			 * graph.getAddressBook().printAdressBook(); clientWriter.flush();
			 */
			break;
		case "QUERY MUTUAL":
			sendToClient(content, socket);
			// do something
			break;
		case "QUERY PATH":
			sendToClient(content, socket);
			// do something
			break;
		}
	}

	/**
	 * @param args
	 * @throws ThisIsntMutualException
	 * @throws ImaginaryFriendException
	 * @throws ParseException
	 */
	public static void main(String[] args) throws ParseException,
			ImaginaryFriendException, ThisIsntMutualException {
		try {
			Integer p = Integer.parseInt(args[1]);
			String fileName = args[0];
			String xml = XMLParser.parse(fileName).toXML();
			Server s = new Server(xml, p);
			s.listen();

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}
