package src;


import java.util.ArrayList;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.traversal.NodeFilter;
import com.sun.org.apache.xerces.internal.dom.DocumentImpl;
import com.sun.org.apache.xerces.internal.dom.TreeWalkerImpl;


public class classifier
{	
    public static void main(String [ ] args){
        //java classifier <CSVFile> <XMLFile>
    	
    	//parse tree
    	try
    	{
    	DocumentImpl tree = (DocumentImpl) fileParser.parseXMLDomain(args[1]);
    	Node root = tree.getLastChild(); //why last?
    	
    	//parse in CVS dataset
    	csvInfo dataset = fileParser.parseCSV(args[0]);
    	
    	
    	ArrayList<Data> dataArray = dataset.dataSets;  //table of data
    	ArrayList<String> GrNames = dataset.stringNames;
    	AllElements allelements = new AllElements();

    	TreeWalkerImpl walk = (TreeWalkerImpl) tree.createTreeWalker(root, NodeFilter.SHOW_ELEMENT,
    			(NodeFilter) allelements, true);
    	
    	
    	//Evaluate dataset in the tree, for each person in table
    	for(int i = 0; i < dataArray.size(); i++)
    	{
    		walk.setCurrentNode(root);
    		walk.lastChild();
    		//System.out.println(walk.getCurrentNode().getAttributes().item(0));
    		System.out.println("Person "+ (i+1) + ": " + traverseTree(walk , GrNames, 
    				dataArray.get(i)));
    	}
    	}catch(Exception e)
    	{
    		System.out.println("Check Paramaters. Files not found or not specified");
    		System.exit(-1);
    	}
    }
    
    
    public static String traverseTree(TreeWalkerImpl walk, ArrayList<String> GrNames, 
    		Data data )
    {
    	
    	String result = "";
    	NodeList children = walk.getCurrentNode().getChildNodes();
    	
    	//base case: Leaf reached.
    	if(children.getLength() == 0)
    	{
    		//System.out.println(walk.getCurrentNode().getNodeName() );
    		//result = walk.getCurrentNode().getAttributes().item(0).toString() ;
    		String raw = walk.getCurrentNode().getAttributes().item(0).toString();
    		raw = raw.substring(raw.indexOf('"')+1, raw.length()-1);
    		result = raw;//.getNodeName();
    	}
    	//else, make a decision and move to a child
    	else
    	{
    		String raw = walk.getCurrentNode().getAttributes().item(0).toString();
    		raw = raw.substring(raw.indexOf('"')+1, raw.length()-1);
    		//get which question we're asking.
    		int Qindex = GrNames.indexOf(raw) ;  //attribute of current Node
    		System.out.println(raw);
    		
    		int answerEdge = data.dataSets.get(Qindex);
    		
    		//determine which edge to take. Move down tree. Assumes tree preserves ordering.
    		//walk.setCurrentNode(children.item(answerEdge));
    		int i = 0;
    		walk.firstChild();
    		for(i = 0; i < answerEdge ;i++ )
    		{
    			walk.nextSibling();
    		}
    		
    		
    		//System.out.println( walk.getCurrentNode().getNodeName());//.getAttributes().item(0).toString());
    		
    		//Check for base case again.
    		if(walk.getCurrentNode().getChildNodes().getLength() == 0)
    		{
    			result = walk.getCurrentNode().getNodeName() ;
    		}
    		else
    		{	//recursing down tree now.
    			walk.firstChild();
    			result = traverseTree(walk, GrNames, data);
    		}
    	}
    	return result;
    }
}

//filters the elements of the XML document
class AllElements implements NodeFilter
{
  public short acceptNode (Node n)
  {
    if (n.getNodeType() == Node.ELEMENT_NODE)
      return FILTER_ACCEPT;
    return FILTER_SKIP;
  }
}