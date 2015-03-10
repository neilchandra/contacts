package contacts.server;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import contacts.adressbook.AddressBook;
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
	 * @param xml a string of xml
	 * @throws ImaginaryFriendException if a friend does not exist
	 * @throws ThisIsntMutualException if a friendship is not bidirectional
	 * @throws ParseException if the xml is invalid
	 */
	public Graph(String xml) throws ImaginaryFriendException, ThisIsntMutualException, ParseException{
		nodes = new ArrayList<IGraphNode>();
		this.ab = new AddressBook(XMLParser.parseString(xml));
		this.ab.buildGraph(this);
	}
	@Override
	public Collection<IGraphNode> getNodes() {
		return nodes;
	}
	/**
	 * adds a node to the graph
	 * @param node, the node to be added
	 */
	public void addNode(Node node) {
		this.nodes.add(node);		
	}
	/**
	 * converts the internal address book to xml
	 * @return a string of xml
	 */
	public String toXML() {
		return ab.toXML();
	}
	/**
	 * finds the shortest path between two nodes
	 * @param contactName1 the name of the contact of the start node
	 * @param contactName2 the name of the contact of the end node
	 * @return a string representation of the shortest path between the nodes
	 */
	public String shortestPath(String contactName1, String contactName2) {
		Node start = this.intToNode.get(ab.nameToInt(contactName1));
		Node end = this.intToNode.get(ab.nameToInt(contactName2));
		List<IGraphNode> shortestPath = GraphAlgorithms.shortestPath(start, end);
		StringBuilder sb = new StringBuilder();
		for(IGraphNode node : shortestPath){
			sb.append(ab.intToName(node.getOwnID()));
			sb.append("\n");
		}
		return sb.toString();
	}
	/**
	 * finds all the the mutual nodes of two nodes
	 * @param contactName1 the name of the contact of node1
	 * @param contactName2 the name of the contact of node2
	 * @return a string representation of all mutual nodes
	 */
	public String mutualNodes(String contactName1, String contactName2) {
		Node node1 = this.intToNode.get(ab.nameToInt(contactName1));
		Node node2 = this.intToNode.get(ab.nameToInt(contactName2));
		Set<IGraphNode> mutual = GraphAlgorithms.mutualNodes(node1, node2);
		StringBuilder sb = new StringBuilder();
		for(IGraphNode node : mutual){
			sb.append(ab.intToName(node.getOwnID()));
			sb.append("\n");
		}
		return sb.toString();
	}
	/**
	 * sets the Hash map of integers to nodes
	 * @param intToNode a hash map of Integers to Nodes
	 */
	public void setIntToNode(HashMap<Integer, Node> intToNode) {
		this.intToNode = intToNode;
	}
	/**
	 * main method for testing
	 * @param args
	 * @throws FileNotFoundException
	 * @throws ImaginaryFriendException
	 * @throws ThisIsntMutualException
	 * @throws ParseException
	 */
	public static void main(String[] args) throws FileNotFoundException, ImaginaryFriendException, ThisIsntMutualException, ParseException {
		AddressBook clientsAb = new AddressBook(XMLParser.parse("src/contacts/example.xml"));
		Graph serverGraph = new Graph(clientsAb.toXML());
		System.out.println(serverGraph.mutualNodes("Sam", "Ally"));
		System.out.println(serverGraph.shortestPath("Sam", "Ally"));
	}

}
