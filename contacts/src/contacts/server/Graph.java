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
public class Graph implements IGraph {
	
	private AddressBook ab;
	private ArrayList<IGraphNode> nodes;
	private HashMap<Integer, Node> intToNode;

	public Graph(String xml) throws ImaginaryFriendException, ThisIsntMutualException, ParseException{
		nodes = new ArrayList<IGraphNode>();
		this.ab = new AddressBook(XMLParser.parseString(xml));
		this.ab.buildGraph(this);
	}
	@Override
	public Collection<IGraphNode> getNodes() {
		return nodes;
	}
	public void addNode(Node node) {
		this.nodes.add(node);		
	}
	public String toXML() {
		return ab.toXML();
	}
	
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
	public void setIntToNode(HashMap<Integer, Node> intToNode) {
		this.intToNode = intToNode;
	}
	
	public static void main(String[] args) throws FileNotFoundException, ImaginaryFriendException, ThisIsntMutualException, ParseException {
		AddressBook clientsAb = new AddressBook(XMLParser.parse("src/contacts/example.xml"));
		Graph serverGraph = new Graph(clientsAb.toXML());
		System.out.println(serverGraph.mutualNodes("Sam", "Ally"));
		System.out.println(serverGraph.shortestPath("Sam", "Ally"));
	}

}
