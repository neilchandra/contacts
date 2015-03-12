package contacts.server;

import java.io.*;
import java.net.*;
import java.util.NoSuchElementException;

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
	private boolean receivedFirst, receivedSecond, queryingPath;
	private String queryFirst, querySecond;

	private Graph graph;

	public Server(String xml, int _port) throws IOException, ParseException,
			ImaginaryFriendException, ThisIsntMutualException {
		this.graph = new Graph(xml);
		port = _port;
		serv = new ServerSocket(port);
		finished = false;
		queryingPath = false;
		receivedFirst = false;
		receivedSecond = false;
	}

	public void listen() throws ImaginaryFriendException,
			ThisIsntMutualException, IOException {

		while (true) {
			socket = serv.accept();
			String input = receiveInput();
			sendToClient(input, socket);
			
			if(input.equals("PUSH CALLED")){
				socket = serv.accept();
				String xml = receiveInput();
				String reply;
				try {
					System.out.println("old ab: "+graph.toXML());
					graph = new Graph(xml);
					System.out.println("new ab: "+graph.toXML());

					reply = "OK";
				} catch (ParseException e){
					reply = "ERROR";
				}
				sendToClient(reply, socket);
			} else if(input.equals("QUERY PATH CALLED")){
				socket = serv.accept();
				String [] people = receiveInput().split("\n");
				if(people.length == 2) {
					try {
						sendToClient(graph.shortestPath(people[0], people[1]), socket);
						graph = new Graph(graph.toXML());
					} catch (Exception e) {
						sendToClient("Invalid inputs", socket);
					}
					
				} else {
					sendToClient("Invalid inputs", socket);
				}
			}
			
			/*
			else if(!receivedFirst && queryingPath){
				socket = serv.accept();
				queryFirst = receiveInput();
				receivedFirst = true;
				String reply = "SEND SECOND QUERY";
				sendToClient(reply, socket);
			} else if(!receivedSecond && receivedFirst && queryingPath){
				socket = serv.accept();
				querySecond = receiveInput();
				System.out.println("query1: "+queryFirst+" query2: "+querySecond);
				receivedSecond = true;
				queryingPath = false;
				String reply = graph.shortestPath(queryFirst, querySecond);
				sendToClient(reply, socket);
			}
			*/
			

		}
	}

	private void sendToClient(String message, Socket sock) throws IOException {
		outputStream = sock.getOutputStream();
		outputStream.write(message.getBytes());
		System.out.println("server sent:" + message);
		outputStream.flush();
		// System.out.println("message: " + sb.toString());
		socket.shutdownOutput();
	}

	private String receiveInput() throws IOException {
		BufferedReader clientInput = new BufferedReader(new InputStreamReader(
				socket.getInputStream()));
		OutputStream outputStream = socket.getOutputStream();
		clientWriter = new BufferedWriter(new OutputStreamWriter(outputStream));
		StringBuilder sb = new StringBuilder();

		int content = clientInput.read();
		while (content != -1) { // 10 = \n char
			sb.append((char) content);
			content = clientInput.read();
		}

		String message = sb.toString();
		System.out.println("server received: "+message);
		
		switch (message) {
		case "PULL":
			message = graph.toXML();
			break;
		case "PUSH":
			message = "PUSH CALLED";
			break;
		case "QUERY PATH" :
			message = "QUERY PATH CALLED";
			break;
		}
		socket.shutdownInput();

		return message;
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
		} catch (NumberFormatException e) {
			e.printStackTrace();
		}

	}

}
