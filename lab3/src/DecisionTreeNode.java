package src;
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
	
	public DecisionTreeNode(int id, int node, int edge, int edgeChoice, int decision){
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
	
	public static String printTree(DecisionTreeNode tree, int depth, int nodeNumber, int categoryNumber, ArrayList<String> nodes, ArrayList<ArrayList<String>> edges){
		String answer = "";
		String space = "";
		for(int i = 0; i < depth; i++){
			space += "    ";
		}

		if(tree.node != -1){
			//<node var = "Location">
			answer += space+"<node var = \"" + nodes.get(tree.node-1)+"\">\n";
			nodeNumber = tree.node;
			depth += 1;
		}
		else if(tree.edge != -1){
			//<edge var = "North County">
			answer += space+"<edge var = \"" + edges.get(nodeNumber-1).get(tree.edge-1)+"\">\n";
			depth += 1;
		}
		else if(tree.decision != -1){
			//<decision end = "1" p = "1.0" />
			answer += space+"<decision end = \""+tree.decision+"\" choice = \""+edges.get(categoryNumber-1).get(tree.decision-1)+"/>\n";
			depth -= 2;
		}

		if(tree.children == null){
			return answer;
		}
		
		for(int i = 0; i < tree.children.size(); i++){
			answer += printTree(tree.children.get(i),depth,nodeNumber,categoryNumber, nodes,edges);
		}
		return answer;
	}
	
}
