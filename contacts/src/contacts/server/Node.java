package contacts.server;
import java.util.ArrayList;
import java.util.Collection;

import graph.*;
public class Node implements IGraphNode {

	private int ownId;
	private ArrayList<IGraphNode> children;
	
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

	public void addChild(Node node) {
		children.add(node);		
	}

}
