import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.StringTokenizer;

import java.io.*;
//import java.util.*;
import org.w3c.dom.*;
import com.sun.org.apache.xerces.internal.dom.TreeWalkerImpl;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.traversal.NodeFilter;

import com.sun.org.apache.xerces.internal.dom.DocumentImpl;

import javax.xml.parsers.*;

public class Parser
{
	//need to change return valye to document
	public static HashMap<String, IRDocument> Read(String filename)
	{
		HashMap<String, Word> wrds;
		HashMap<String, IRDocument> output = new HashMap<String, IRDocument>();
		//determine file type
		
		if(filename.toLowerCase().endsWith(".xml"))
		{
			//call parseXML()
		}
		else if(filename.toLowerCase().endsWith(".txt"))
		{
			//call ParseText()
			wrds = parseTextFile(filename);
		}
		else
		{
			System.out.println("Internal error. This is a list of files\n");
		}
		return output;
		
	}
	
	static HashMap<String, Word> parseTextFile(String filename)
	{
		HashMap<String, Word> answer = new HashMap<String, Word>();
		//ArrayList<TextToken> tok = new ArrayList<TextToken>();
		
		FileInputStream fstream = null;
		try {
			fstream = new FileInputStream(filename);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			System.err.println("File Not Found.");
		}
		
		DataInputStream in = new DataInputStream(fstream);
		BufferedReader br = new BufferedReader(new InputStreamReader(in));
		String strLine = null;
		
		try {
			while ((strLine = br.readLine()) != null)
			{
				//for each line...
				//System.out.println("Now processing: "+ strLine);
				stringToTextTokens(strLine,answer);
			}
		} catch (IOException e) {
			e.printStackTrace();
			System.err.println("Exception while reading file.");
		}
		
		//TODO make a new IRDocument and add the answer to it and 
		//return that rather than the answer
		return answer;
	}

	static HashMap<String, Word> stringToTextTokens(String s, HashMap<String, Word> tokens)
	{
		//else
		//{
		StringTokenizer st = new StringTokenizer(s, " ");
		while (st.hasMoreTokens()) 
		{
				String currChunk = st.nextToken();
				//HashMap<String, Word> bufferTokens = new HashMap<String, Word>();
				boolean done = false;
				while(!done)
				{
					if(currChunk.length() == 0)
					{
						break;
					}
					else if(currChunk.length() >= 4 && 
							currChunk.substring(currChunk.length()-4, currChunk.length()).compareTo("....") == 0)
					{
						//bufferTokens.add(new Chars("..."));
						//bufferTokens.add(new Chars("."));
						currChunk = currChunk.substring(0,currChunk.length()-4);
					}
					else if(currChunk.length() >= 3 && 
							currChunk.substring(currChunk.length()-3, currChunk.length()).compareTo("...") == 0)
					{
						//bufferTokens.add(new Chars("..."));
						currChunk = currChunk.substring(0,currChunk.length()-3);
					}
					else if(currChunk.length() == 1 && currChunk.charAt(0) == '-')
					{
						//tokens.add(new Chars("-"));
						break;
					}
					else if(currChunk.charAt(0) == ':')
					{
						//tokens.add(new Chars(":"));
						currChunk = currChunk.substring(1,currChunk.length());
					}
					else if(currChunk.charAt(0) == '(')
					{
						//tokens.add(new Chars("("));
						currChunk = currChunk.substring(1,currChunk.length());
					}
					else if(currChunk.charAt(0) == '’')
					{
						//tokens.add(new Chars("\'"));
						currChunk = currChunk.substring(1,currChunk.length());
					}
					else if(currChunk.charAt(0) == '"')
					{
						//tokens.add(new Chars("\""));
						currChunk = currChunk.substring(1,currChunk.length());
					}
					else if(currChunk.charAt(currChunk.length()-1) == '"')
					{
						//bufferTokens.add(new Chars("\""));
						currChunk = currChunk.substring(0,currChunk.length()-1);
					}
					else if(currChunk.charAt(currChunk.length()-1) == '’')
					{
						//bufferTokens.add(new Chars("’"));
						currChunk = currChunk.substring(0,currChunk.length()-1);
					}
					else if(currChunk.charAt(currChunk.length()-1) == ')')
					{
						//bufferTokens.add(new Chars(")"));
						currChunk = currChunk.substring(0,currChunk.length()-1);
					}
					else if(currChunk.charAt(currChunk.length()-1) == ':')
					{
						//bufferTokens.add(new Chars(":"));
						currChunk = currChunk.substring(0,currChunk.length()-1);
					}
					else if(currChunk.charAt(currChunk.length()-1) == ';')
					{
						//bufferTokens.add(new Chars(";"));
						currChunk = currChunk.substring(0,currChunk.length()-1);
					}
					else if(currChunk.charAt(currChunk.length()-1) == ',')
					{
						//bufferTokens.add(new Chars(","));
						currChunk = currChunk.substring(0,currChunk.length()-1);
					}
					else if(currChunk.charAt(currChunk.length()-1) == '.')
					{
						//bufferTokens.add(new Chars("."));
						//bufferTokens.add(new EOS());
						currChunk = currChunk.substring(0,currChunk.length()-1);
					}
					else if(currChunk.charAt(currChunk.length()-1) == '?')
					{
						//bufferTokens.add(new Chars("?"));
						//bufferTokens.add(new EOS());
						currChunk = currChunk.substring(0,currChunk.length()-1);
					}
					else if(currChunk.charAt(currChunk.length()-1) == '!')
					{
						//bufferTokens.add(new Chars("!"));
						//bufferTokens.add(new EOS());
						currChunk = currChunk.substring(0,currChunk.length()-1);
					}
					else
					{
						done = true;
						if(tokens.get(currChunk) != null)
						{
							
						}
						tokens.put(currChunk, new Word(currChunk));
						//while(!bufferTokens.isEmpty())
						//{
							//tokens.putAll(bufferTokens);
							//bufferTokens.clear();
						//}
					}
				}				
			}
		//}
		return tokens;
	}
	
	public HashMap<String, Word> ParseXML(String filename)
	{
		HashMap<String, Word> output = new HashMap<String, Word>();
		DocumentImpl doc = (DocumentImpl) parseXMLDomain(filename);
		Node root = doc.getLastChild();
		AllElements allelements = new AllElements();
		TreeWalkerImpl walk = (TreeWalkerImpl) doc.createTreeWalker
				(root, NodeFilter.SHOW_ELEMENT,
    			(NodeFilter) allelements, true);
		
		NodeList subDocs = root.getChildNodes();
		Node cur = walk.firstChild();
		for(int i = 0; i < subDocs.getLength(); i++ )
		{
			//convert each child node into a Document.
			HashMap<String, Word> doctext = new HashMap<String, Word>();
			System.out.println(cur.getTextContent());
			stringToTextTokens(cur.getTextContent(), doctext);
			//TODO add doctext to a IRDocument and return the list of Document
			walk.nextSibling();
		}
		return output;
	}
	
	 static org.w3c.dom.Document parseXMLDomain(String filename){
	        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
	        factory.setIgnoringComments(true);
	        factory.setCoalescing(true); // Convert CDATA to Text nodes; 
	        factory.setNamespaceAware(false); // No namespaces: this is default
	        factory.setValidating(false); // Don't validate DTD: also default
	    	factory.setExpandEntityReferences(false); 
	    	factory.setIgnoringElementContentWhitespace(true);
	        DocumentBuilder parser = null;
	        org.w3c.dom.Document document = null;
	        
	        try {
	            parser = factory.newDocumentBuilder();
	            parser.setErrorHandler(null);
	            document = parser.parse(new File(filename));
	        } catch (Exception e) {
	            e.printStackTrace();
	            System.exit(-1);
	        }
	        return document;
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
