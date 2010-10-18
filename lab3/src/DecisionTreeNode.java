package src;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.*;



import com.sun.org.apache.xerces.internal.dom.DocumentImpl;
import com.sun.org.apache.xerces.internal.dom.ElementImpl;
import com.sun.org.apache.xerces.internal.dom.NodeImpl;

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
	public static Document toDoc(DecisionTreeNode tree,
			int depth, int nodeNumber, int categoryNumber,
			ArrayList<String> nodes, ArrayList<ArrayList<String>> edges) 
	throws ParserConfigurationException
	{
		DocumentBuilderFactory dbfac = DocumentBuilderFactory.newInstance();
        DocumentBuilder docBuilder = dbfac.newDocumentBuilder();
        DocumentImpl doc = (DocumentImpl) docBuilder.newDocument();

        Element cur2 = new ElementImpl(doc, "tree");
        cur2.setAttribute("name", "lab3");
        
		cur2.appendChild(appendTree(tree, doc,
			depth, nodeNumber, categoryNumber,
			nodes,  edges));

		doc.appendChild(cur2);
		return doc;
	}
	
	private static Element appendTree(DecisionTreeNode tree, DocumentImpl doc,
			int depth, int nodeNumber, int categoryNumber,
			ArrayList<String> nodes, ArrayList<ArrayList<String>> edges)
	{
		//DecisionTreeNode cur;
		Element current;
		
		if(tree.node != -1){
			//<node var = "Location">
			current = new ElementImpl(doc, "node");
			current.setAttribute("var",nodes.get(tree.node-1));
			nodeNumber = tree.node;
			depth += 1;
		}
		else if(tree.edge != -1){
			//<edge var = "North County">
			current = new ElementImpl(doc, "edge");
			//System.out.println(edges.get(nodeNumber-1).get(tree.edge-1));
			current.setAttribute("var",
					edges.get(nodeNumber-1).get(tree.edge-1));
			depth += 1;
		}
		else//(tree.decision != -1){
		{
			//<decision end = "1" p = "1.0" />
			current = new ElementImpl(doc, "decision");
			current.setAttribute("p" ,
					edges.get(categoryNumber-1).get(tree.decision-1));
			current.setAttribute("end" ,
					String.valueOf(tree.decision));
			depth -= 2;
		}
		
		//recurse;
		if(tree.children != null)
		{
			for(int i = 0; i < tree.children.size(); i++)
			{
				current.appendChild(appendTree(tree.children.get(i), doc,
						depth, nodeNumber, categoryNumber, nodes, edges));
			}
		}
		else
		{
			
		}

		//current.setAttribute(tree.id,tree.id);
		return current;
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
