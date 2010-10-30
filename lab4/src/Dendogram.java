package src;

import java.io.FileOutputStream;
import java.io.PrintStream;
import java.io.StringWriter;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Element;

import com.sun.org.apache.xerces.internal.dom.DocumentImpl;
import com.sun.org.apache.xerces.internal.dom.ElementImpl;

public class Dendogram
{
	//Cluster cluster1; //left hand cluster
	Dendogram den1; //Dendogram beneath cluster
	//Cluster cluster2; // right hand cluster
	Dendogram den2; //Dendogram beneath cluster
	Cluster cluster;
	double distance;
	
	Dendogram()
	{
		cluster = new Cluster();
		//cluster2 = new Cluster();
		distance = 0;
		den1 = null;
		den2 = null;
	}
	
	Dendogram(Cluster one, double dist)
	{
		cluster = one;
		//cluster2 = two;
		distance = dist;
		den1 = null;
		den2 = null;
	}
	
	Dendogram(Cluster one, double dist, Dendogram left, Dendogram right)
	{
		cluster = one;
		//cluster2 = two;
		distance = dist;
		den1 = left;
		den2 = right;
	}
	
	/*public boolean equals(Object obj)
	{
		if(obj instanceof Dendogram)
		{
			for(int i = 0; i < ((Dendogram) obj.data.size(); i++ )
			{
				
			}
		}
		return false;
	}*/
	
	public static ArrayList<Dendogram> pointsToDendo(ArrayList<Cluster> cList)
	{
		ArrayList<Dendogram> dendoList = new ArrayList<Dendogram>();
		for(int i = 0; i < cList.size(); i++)
		{
			Dendogram noo = new Dendogram();
			noo.distance = 0.0;
			noo.cluster = cList.get(i);
			//System.out.println(noo.cluster.data.size());
			dendoList.add(noo);
		}
		return dendoList;
	}
	
	public DocumentImpl toXML(boolean export, String filename)
	{
		DocumentImpl docu = null;
    	
    	FileOutputStream out = null; // declare a file output object
        PrintStream p = null; // declare a print stream object
        try{
        	if(export)
        	{
        		out = new FileOutputStream(filename);
        		p = new PrintStream( out );
        	}

        }
        catch(Exception e){
        	
        }
        /*if(export)
        {
        	p.println ("<?xml version=\"1.0\" encoding=\"utf-8\"?>");
        	p.println ("<Tree name = \"lab3\">");
        }*/
        
        //String xml = DecisionTreeNode.printTree(tree,0,-1, csvAL.categoryIndex,csvAL.stringNames, edgeNames);
        try
        {
        	docu = this.toDocument();
        
        	TransformerFactory transfac = TransformerFactory.newInstance();
        	Transformer trans = transfac.newTransformer();
        	trans.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "no");
        	trans.setOutputProperty(OutputKeys.INDENT, "yes");

        	//create string from xml tree
        	StringWriter sw = new StringWriter();
        	StreamResult result = new StreamResult(sw);
        	DOMSource source = new DOMSource(docu);
        	trans.transform(source, result);
        	String xmlString = sw.toString();

        	if(!export)
        	{
        		//print xml
        		System.out.println("Dendogram:\n\n" + xmlString);
        	}
        
        	if(export)
        	{
        		p.print(xmlString) ;
        		p.close();
        	}
        }
        catch(Exception e)
        {
        	//problem
        	System.err.println("XML print error");
        	e.printStackTrace();
        	System.exit(-1);
        }
        
        return docu;
        
	}
	
	public DocumentImpl toDocument() throws ParserConfigurationException
	{
		DocumentBuilderFactory dbfac = DocumentBuilderFactory.newInstance();
        DocumentBuilder docBuilder = dbfac.newDocumentBuilder();
        DocumentImpl doc = (DocumentImpl) docBuilder.newDocument();

        Element cur2 = new ElementImpl(doc, "tree");
        //cur2.setAttribute("height", Double.toString(distance));
        
		cur2 = appendChild(doc, this);

		doc.appendChild(cur2);
		return doc;
	}
	
	
	private Element appendChild(DocumentImpl doc, Dendogram cur)
	{
		Element current;
		if(cur.cluster.data.size() <= 1)
		{
			//System.out.println(cur.cluster.data.size()+ " " + cur.den2);
			current = new ElementImpl(doc, "leaf");
			current.setAttribute("data", cur.cluster.toString());
		}
		else
		{
			current = new ElementImpl(doc, "node");
			current.setAttribute("height", Double.toString(distance));
			if(den2 != null)
				current.appendChild(cur.appendChild(doc, cur.den2));
			if(den1!=null)
				current.appendChild(cur.appendChild(doc, cur.den1));
		}
		return current;
	}
	
	public String toString()
	{
		String output = "dist:" + distance + "\n";
		if(den1 != null)
		{
			output += "\t" + den1.toString();
		}
/*		else
		{
			output += cluster;
		}*/
		//output+= "cccccccccc";
		if(den2 != null)
		{
			output += "\t" + den2.toString();
		}
		else if(den1 == null && den2 == null)
		{
			output += cluster;
		}
		return output;
	}
}
