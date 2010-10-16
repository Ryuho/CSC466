import java.util.ArrayList;

public class DomainTreeNode
{
	int id;
	int decision;
	String node;
	String edge;
	DomainTreeNode parent;
	ArrayList<DomainTreeNode> children;
	
	public DomainTreeNode(){
		id = -1;
		decision = -1;
		node = "";
		edge = "";
		parent = null;
		children = new ArrayList<DomainTreeNode>();
	}
	
	public DomainTreeNode(int id, int decision, String node, String edge, DomainTreeNode parent, ArrayList<DomainTreeNode> children){
		this.id = id;
		this.decision = decision;
		this.node = node;
		this.edge = edge;
		this.parent = parent;
		this.children = children;
	}
	
	public void addNode(DomainTreeNode child){
		
		child.parent = this;
		children.add(child);		
	}
	
}
