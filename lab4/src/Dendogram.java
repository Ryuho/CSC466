package src;

import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Element;

import com.sun.org.apache.xerces.internal.dom.DocumentImpl;
import com.sun.org.apache.xerces.internal.dom.ElementImpl;

public class Dendogram
{
	Cluster cluster1; //left hand cluster
	Dendogram den1; //Dendogram beneath cluster
	Cluster cluster2; // right hand cluster
	Dendogram den2; //Dendogram beneath cluster
	double distance;
	
	Dendogram()
	{
		cluster1 = new Cluster();
		cluster2 = new Cluster();
		distance = 0;
		den1 = null;
		den2 = null;
	}
	
	Dendogram(Cluster one, Cluster two, double dist)
	{
		cluster1 = one;
		cluster2 = two;
		distance = dist;
		den1 = null;
		den2 = null;
	}
	
	Dendogram(Cluster one, Cluster two, double dist, Dendogram left, Dendogram right)
	{
		cluster1 = one;
		cluster2 = two;
		distance = dist;
		den1 = left;
		den2 = right;
	}
	
	public DocumentImpl toDocument() throws ParserConfigurationException
	{
		DocumentBuilderFactory dbfac = DocumentBuilderFactory.newInstance();
        DocumentBuilder docBuilder = dbfac.newDocumentBuilder();
        DocumentImpl doc = (DocumentImpl) docBuilder.newDocument();

        Element cur2 = new ElementImpl(doc, "tree");
        cur2.setAttribute("name", "lab4");
        
		cur2 = appendChild(doc);

		doc.appendChild(cur2);
		return doc;
	}
	
	
	private Element appendChild(DocumentImpl doc)
	{
		Element current = new ElementImpl(doc, "node");
		
		/* if(tree.node != -1){
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
				//recurse
				current = appendChild();
			}
		}

		//current.setAttribute(tree.id,tree.id);
		 * */
		 
		return current;
	}
}
