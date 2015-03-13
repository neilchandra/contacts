package contacts.server;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;

import contacts.adressbook.AddressBook;
import contacts.parser.Contact;
import contacts.parser.ImaginaryFriendException;
import contacts.parser.ParseException;
import contacts.parser.ThisIsntMutualException;
import contacts.parser.XMLParser;
import graph.*;

/**
 * graph class
 */
public class Graph implements IGraph {

	private AddressBook ab;
	private ArrayList<IGraphNode> nodes;
	private HashMap<Integer, Node> intToNode;

	/**
	 * constructor consuming a string of xml
	 * 
	 * @param xml
	 *            a string of xml
	 * @throws ImaginaryFriendException
	 *             if a friend does not exist
	 * @throws ThisIsntMutualException
	 *             if a friendship is not bidirectional
	 * @throws ParseException
	 *             if the xml is invalid
	 */
	public Graph(String xml) throws ImaginaryFriendException,
			ThisIsntMutualException, ParseException {
		nodes = new ArrayList<IGraphNode>();
		this.ab = new AddressBook(XMLParser.parseString(xml));
		this.ab.buildGraph(this);
	}

	@Override
	public Collection<IGraphNode> getNodes() {
		try {
			return (new Graph(this.toXML())).nodes;
		} catch (ImaginaryFriendException | ThisIsntMutualException
				| ParseException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * adds a node to the graph
	 * 
	 * @param node
	 *            , the node to be added
	 */
	public void addNode(Node node) {
		this.nodes.add(node);
	}

	/**
	 * converts the internal address book to xml
	 * 
	 * @return a string of xml
	 */
	public String toXML() {
		return ab.toXML();
	}

	/**
	 * Returns the currrent address book
	 * 
	 * @return -- ab
	 */
	public AddressBook getAddressBook() {
		return this.ab;
	}

	/**
	 * finds the shortest path between two nodes
	 * 
	 * @param contactName1
	 *            the name of the contact of the start node
	 * @param contactName2
	 *            the name of the contact of the end node
	 * @return a string representation of the shortest path between the nodes
	 */
	public String shortestPath(String contactName1, String contactName2)
			throws NoSuchElementException {
		Node start = null, end = null;
		try {
			start = this.intToNode.get(ab.nameToInt(contactName1));
			end = this.intToNode.get(ab.nameToInt(contactName2));
		} catch (NullPointerException e) {
			System.out.println("not a valid person");
			throw new NoSuchElementException();
		}
		List<IGraphNode> shortestPath = GraphAlgorithms
				.shortestPath(start, end);
		if (shortestPath == null) {
			return "no path between contacts";
		}
		StringBuilder sb = new StringBuilder();
		for (IGraphNode node : shortestPath) {
			sb.append(ab.intToName(node.getOwnID()));
			sb.append("\n");
		}
		return sb.toString();
	}

	/**
	 * finds all the the mutual nodes of two nodes
	 * 
	 * @param contactName1
	 *            the name of the contact of node1
	 * @param contactName2
	 *            the name of the contact of node2
	 * @return a string representation of all mutual nodes
	 */
	public String mutualNodes(String contactName1, String contactName2)
			throws NoSuchElementException {
		Node node1 = null, node2 = null;
		try {
			node1 = this.intToNode.get(ab.nameToInt(contactName1));
			node2 = this.intToNode.get(ab.nameToInt(contactName2));
		} catch (NullPointerException e) {
			System.out.println("not a valid person");
			throw new NoSuchElementException();
		}
		Set<IGraphNode> mutual = GraphAlgorithms.mutualNodes(node1, node2);
		if (mutual.size() == 0) {
			return "no mutual friends";
		}
		StringBuilder sb = new StringBuilder();
		for (IGraphNode node : mutual) {
			sb.append(ab.intToName(node.getOwnID()));
			sb.append("\n");
		}
		return sb.toString();
	}

	/**
	 * sets the Hash map of integers to nodes
	 * 
	 * @param intToNode
	 *            a hash map of Integers to Nodes
	 */
	public void setIntToNode(HashMap<Integer, Node> intToNode) {
		this.intToNode = intToNode;
	}

	/**
	 * main method for testing
	 * 
	 * @param args
	 * @throws FileNotFoundException
	 * @throws ImaginaryFriendException
	 * @throws ThisIsntMutualException
	 * @throws ParseException
	 */
	public static void graphTests() {
		System.out.println("=========== creating the graph ===========");
		// initializing the addressBook
		AddressBook ab = null;
		// creating the addressBook
		try {
			ab = new AddressBook(
					XMLParser.parseString("<addressBook></addressBook>"));
		} catch (ImaginaryFriendException | ThisIsntMutualException
				| ParseException e) {
			System.out.println(false);
		}
		// adding a group
		try {
			ab.addGroup("everyone");
			ab.addGroup("south wayland 3rd floor", "everyone");
			System.out.println(true);
		} catch (ParseException e) {
			System.out.println(false);
		}
		// constructing contacts and adding them to the address book
		Contact neil = null, mitch = null, sam = null, timmy = null, luke = null, jake = null, mark = null;
		try {
			neil = ab.makeContact("Neil", "", 1, new int[] {});
			ab.addContact(neil, "everyone");
			mitch = ab.makeContact("Mitch", "", 2, new int[] { 1 });
			ab.addContact(mitch, "south wayland 3rd floor");
			sam = ab.makeContact("Sam", "", 3, new int[] { 2 });
			ab.addContact(sam, "south wayland 3rd floor");
			timmy = ab.makeContact("Timmy", "", 4, new int[] { 3 });
			ab.addContact(timmy, "everyone");
			luke = ab.makeContact("Luke", "", 5, new int[] { 2, 3 });
			ab.addContact(luke, "south wayland 3rd floor");
			jake = ab.makeContact("Jake", "", 7, new int[] { 2, 3 });
			ab.addContact(jake, "everyone");
			mark = ab.makeContact("Mark", "", 6, new int[] {});
			ab.addContact(mark, "everyone");
			System.out.println(true);
		} catch (ParseException | ImaginaryFriendException
				| ThisIsntMutualException e) {
			System.out.println(false);
		}
		// creating the graph
		Graph g = null;
		try {
			g = new Graph(ab.toXML());
			System.out.println(true);
		} catch (ImaginaryFriendException | ThisIsntMutualException
				| ParseException e) {
			System.out.println(false);
		}
		System.out.println("=========== mutual nodes ===========");
		// one mutual friend
		System.out.println(g.mutualNodes("Mitch", "Luke").equals("Sam\n"));
		// no mutual friends
		System.out.println(g.mutualNodes("Mitch", "Neil").equals(
				"no mutual friends"));
		// many mutual friends
		System.out
				.println(g.mutualNodes("Luke", "Jake").equals("Sam\nMitch\n"));
		System.out.println("=========== shortest path ===========");
		try {
			g = new Graph(g.toXML());
		} catch (ImaginaryFriendException | ThisIsntMutualException
				| ParseException e) {
			e.printStackTrace();
		}
		// no path between contacts
		System.out.println(g.shortestPath("Luke", "Mark").equals(
				"no path between contacts"));
		// one preson between
		System.out.println(g.shortestPath("Luke", "Jake").equals(
				"Luke\nSam\nJake\n"));
		// shortest path to themselves
		System.out.println(g.shortestPath("Luke", "Luke").equals("Luke\n"));
		// invalid person
		try {
			System.out.println(g.shortestPath("Jim", "Jake"));
			System.out.println(false);
		} catch (NoSuchElementException e) {
			System.out.println(true);
		}
		try {
			g = new Graph(g.toXML());
		} catch (ImaginaryFriendException | ThisIsntMutualException
				| ParseException e) {
			e.printStackTrace();
		}
		System.out.println(g.shortestPath("Neil", "Timmy").equals(
				"Neil\nMitch\nSam\nTimmy\n"));
		try {
			g = new Graph(g.toXML());
		} catch (ImaginaryFriendException | ThisIsntMutualException
				| ParseException e) {
			e.printStackTrace();
		}
		System.out.println(g.shortestPath("Timmy", "Neil").equals("Timmy\nSam\nMitch\nNeil\n"));
	}
}
