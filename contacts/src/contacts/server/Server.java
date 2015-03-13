package contacts.server;

import java.io.*;
import java.net.*;
import java.util.NoSuchElementException;

import contacts.parser.ImaginaryFriendException;
import contacts.parser.ParseException;
import contacts.parser.ThisIsntMutualException;
import contacts.parser.XMLParser;

public class Server {

	/** I/O attributes */
	private int port;
	public Socket socket;
	public ServerSocket serv;

	/** Streams for input and output */
	private InputStream inputStream;
	private OutputStream outputStream;

	/** Buffered Streams for input and output */
	BufferedReader clientInput;
	BufferedWriter clientWriter;

	/** The local copy of the AddressBook */
	private Graph graph;

	/**
	 * Constructs a Server from an xml file and a port
	 * 
	 * @param filePath
	 *            - the path to the file from which the xml is imported
	 * @param _port
	 *            - the port on which to operate
	 * @throws IOException
	 * @throws ParseException
	 * @throws ImaginaryFriendException
	 * @throws ThisIsntMutualException
	 */
	public Server(String filePath, int _port) throws IOException,
			ParseException, ImaginaryFriendException, ThisIsntMutualException {
		String xml = XMLParser.parse(filePath).toXML();
		this.graph = new Graph(xml);
		port = _port;
		serv = new ServerSocket(port);
	}

	/**
	 * Listens to client inputs and responds
	 * @throws ImaginaryFriendException
	 * @throws ThisIsntMutualException
	 * @throws IOException
	 */
	public void listen() throws ImaginaryFriendException,
			ThisIsntMutualException, IOException {

		while (true) {
			socket = serv.accept();
			String input = receiveInput();
			sendToClient(input, socket);

			if (input.equals("PUSH CALLED")) {
				socket = serv.accept();
				String xml = receiveInput();
				String reply;
				try {
					System.out.println("old ab: " + graph.toXML());
					graph = new Graph(xml);
					System.out.println("new ab: " + graph.toXML());
					reply = "OK";
				} catch (ParseException e) {
					reply = "ERROR";
				}
				sendToClient(reply, socket);
			} else if (input.equals("QUERY PATH CALLED")) {
				socket = serv.accept();
				String[] people = receiveInput().split("\n");
				if (people.length == 2) {
					try {
						sendToClient(graph.shortestPath(people[0], people[1]),
								socket);
						graph = new Graph(graph.toXML());
					} catch (Exception e) {
						sendToClient("Invalid inputs", socket);
					}

				} else {
					sendToClient("Invalid inputs", socket);
				}
			} else if (input.equals("QUERY MUTUAL CALLED")) {
				socket = serv.accept();
				String[] people = receiveInput().split("\n");
				if (people.length == 2) {
					try {
						sendToClient(graph.mutualNodes(people[0], people[1]),
								socket);
						graph = new Graph(graph.toXML());
					} catch (Exception e) {
						sendToClient("Invalid inputs", socket);
					}

				} else {
					sendToClient("Invalid inputs", socket);
				}
			}

		}
	}

	/**
	 * Sends a message to a client through a certain port
	 * @param message - the String to be sent
	 * @param sock - the socket in which to interact
	 * @throws IOException
	 */
	private void sendToClient(String message, Socket sock) throws IOException {
		outputStream = sock.getOutputStream();
		outputStream.write(message.getBytes());
		System.out.println("server sent:" + message);
		outputStream.flush();
		socket.shutdownOutput();
	}

	/**
	 * Receives input from da client
	 * @return
	 * @throws IOException
	 */
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
		System.out.println("server received: " + message);

		switch (message) {
		case "PULL":
			message = graph.toXML();
			break;
		case "PUSH":
			message = "PUSH CALLED";
			break;
		case "QUERY PATH":
			message = "QUERY PATH CALLED";
			break;
		case "QUERY MUTUAL":
			message = "QUERY MUTUAL CALLED";
			break;
		}
		socket.shutdownInput();

		return message;
	}

	/**
	 * @param args
	 *            - <filename> <port>
	 * @throws ThisIsntMutualException
	 * @throws ImaginaryFriendException
	 * @throws ParseException
	 */
	public static void main(String[] args) {
		try {
			Integer p = Integer.parseInt(args[1]);
			String fileName = args[0];
			Server s = new Server(fileName, p);
			s.listen();

		} catch (IOException e) {
			e.printStackTrace();
		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
