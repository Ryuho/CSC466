

import java.util.ArrayList;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;


public class classifier
{	
    public static void main(String [ ] args){
        //java classifier <CSVFile> <XMLFile>
    	
    	//parse tree
    	Document tree = fileParser.parseXMLDomain("data/tree03.xml");
    	Element root = tree.getDocumentElement();
    	
    	//parse in CVS dataset
    	csvInfo dataset = fileParser.parseCSV("data/tree03-20-numbers.csv");
    	
    	
    	ArrayList<ArrayList<Integer>> dataArray = dataset.getDataSets();  //table of data
    	ArrayList<String> GrNames = dataset.getStringNames();
    	
    	//Evaluate dataset in the tree, for each person in table
    	for(int i = 0; i < dataArray.size(); i++)
    	{
    		System.out.println(root.getFirstChild().getNodeName());
    		System.out.println("Person "+ (i+1) + ": " + traverseTree( root.getFirstChild()
    				, GrNames, 
    				dataArray.get(i)));
    	}
    }
    
    
    public static String traverseTree(Node node, ArrayList<String> GrNames, 
    		ArrayList<Integer> dataArray )
    {
    	String result = "";
    	NodeList children = node.getChildNodes();
    	
    	//base case: Leaf reached.
    	if(children.getLength() == 0)
    	{
    		System.out.println(node.getAttributes());
    		result = node.getAttributes().item(0).toString();
    	}
    	//else, make a decision and move to a child
    	else
    	{
    		//get which question we're asking.
    		int Qindex = GrNames.indexOf(  //grabbing index out of array
    				node.getAttributes().item(0).toString()) ;  //attribute of current Node
    		System.out.println(node.getAttributes().item(0).toString());
    		
    		int answerEdge =  dataArray.get(Qindex-1);
    		
    		//determine which edge to take.
    		Node edge = children.item(answerEdge);
    		/*Assumes tree order maintains edge ordering
    		 * for(int i = 0; i < children.getLength(); i++)
    		{
    			if(children.item(i).getAttributes().item(0) )
    		}*/
    		
    		//get next question node
    		result = traverseTree( edge.getChildNodes().item(0), GrNames, dataArray);
    	}
    	return result;
    }
}