package contacts.server;
import java.util.ArrayList;
import java.util.Collection;

import graph.*;

/**
 * node class
 */
public class Node implements IGraphNode {

	private int ownId;
	private ArrayList<IGraphNode> children;
	/**
	 * constructor for a node which takes in id
	 * @param ownId an int representing the id
	 */
	public Node(int ownId) {
		children = new ArrayList<IGraphNode>();
		this.ownId = ownId;
	}

	@Override
	public Collection<IGraphNode> getChildren() {
		return this.children;
	}

	@Override
	public int getOwnID() {
		return ownId;
	}
	/**
	 * adds a child node to the node
	 * @param node the child node to be added
	 */
	public void addChild(Node node) {
		children.add(node);		
	}

}
