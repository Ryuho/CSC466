package src;
import java.util.ArrayList;

public class DecisionTreeNode
{
	int id;
	int decision;
	int node;
	int edge;
	int edgeChoice;
	DecisionTreeNode parent;
	ArrayList<DecisionTreeNode> children;
	
	public DecisionTreeNode(){
		id = -1;
		decision = -1;
		node = -1;
		edge = -1;
		edgeChoice = -1;
		parent = null;
		children = new ArrayList<DecisionTreeNode>();
	}
	
	public DecisionTreeNode(int id, int node, int edge, int edgeChoice, int decision){
		this.id = id;
		this.decision = decision;
		this.node = node;
		this.edge = edge;
		this.edgeChoice = edgeChoice;
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

	@Override
	public String toString() {
		String answer = "";
		if(this.node != -1){
			answer += "Node: " + this.node+"| ";
		}
		else if(this.edge != -1){
			answer += "edge: " + this.edge+"| ";
		}
		else if(this.edgeChoice != -1){
			answer += "edgeChoice: " + this.edgeChoice+"| ";
		}
		else if(this.decision != -1){
			answer += "decision: " + this.decision+"| ";
		}
		
		answer += "\n";
		
		if(this.children == null){
			return answer;
		}
		
		for(int i = 0; i < this.children.size(); i++){
			answer += this.children.get(i).toString();
		}
		
		return answer;
	}
	
}
