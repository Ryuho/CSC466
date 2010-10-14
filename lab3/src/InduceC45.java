import java.util.ArrayList;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.traversal.NodeFilter;

import com.sun.org.apache.xerces.internal.dom.DocumentImpl;
import com.sun.org.apache.xerces.internal.dom.TreeWalkerImpl;



public class InduceC45{
    public static void main(String [ ] args){
        //java InduceC45 <domainFile.xml> <TrainingSetFile.csv> [<restrictionsFile>]
        
        Document test = fileParser.parseXMLDomain("data/domain.xml");

        csvInfo csvAL = fileParser.parseCSV("data/tree02-20-numbers.csv");
        
        System.out.println(csvAL);   
        
        DocumentImpl tree = new DocumentImpl();
        Node root = tree.getLastChild(); //why last?
        AllElements allelements = new AllElements();
        TreeWalkerImpl walk = (TreeWalkerImpl) tree.createTreeWalker(root, NodeFilter.SHOW_ELEMENT,
                (NodeFilter) allelements, true);
        
    }

    
    public TreeWalkerImpl C45(TreeWalkerImpl tree, ArrayList<ArrayList<Integer>> dataSet, 
            ArrayList<Integer> attributes, double threshold){
        //for all dataset
            // if they are all in the same class
        
        //create a leaf node
        //label the leaf as that class
        //add it to the tree
        
        //find the splitting attribute
        //if the splitting attribute is null
            //create a leaf node
            //label the leaf node with the most frequent category
            //add the node to tree
        
        //else splitting attribute is valid
            //create a tree node
            //label the node splitting attribute
            
            //for for each different groups the splitting attributes create
                //
        
        return tree;
    }
}

