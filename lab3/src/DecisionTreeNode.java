import java.util.ArrayList;

public class DecisionTreeNode
{
	int id;
	int decision;
	int node;
	int edge;
	DecisionTreeNode parent;
	ArrayList<DecisionTreeNode> children;
	
	public DecisionTreeNode(){
		id = -1;
		decision = -1;
		node = -1;
		edge = -1;
		parent = null;
		children = new ArrayList<DecisionTreeNode>();
	}
	
	public DecisionTreeNode(int id, int node, int edge, int decision){
		this.id = id;
		this.decision = decision;
		this.node = node;
		this.edge = edge;
		this.parent = null;
		this.children = null;
	}
	
	public void addNode(DecisionTreeNode child){
		if(children == null){
			children = new ArrayList<DecisionTreeNode>();
		}
		child.parent = this;
		children.add(child);		
	}
	
}
