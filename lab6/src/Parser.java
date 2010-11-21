import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.StringTokenizer;

import java.io.*; //import java.util.*;
import org.w3c.dom.*;
import com.sun.org.apache.xerces.internal.dom.TreeWalkerImpl;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.traversal.NodeFilter;

import com.sun.org.apache.xerces.internal.dom.DocumentImpl;

import javax.xml.parsers.*;

public class Parser
{
	static HashMap<String, String> stopwords = new HashMap<String, String>();
	
	// need to change return valye to document
	public static HashMap<String, IRDocument> Read(String filename)
	{
		// HashMap<String, Word> wrds;
		HashMap<String, IRDocument> output = new HashMap<String, IRDocument>();
		// determine file type

		if (filename.toLowerCase().endsWith(".xml"))
		{
			// call parseXML()
			output = ParseXML(filename);
		} else if (filename.toLowerCase().endsWith(".txt"))
		{
			// call ParseText()
			output = parseTextFile(filename);
		} else
		{
			System.out.println("Internal error. This is a list of files\n");
			return null;
		}
		return output;

	}

	public static HashMap<String, String> loadStopwords()
	{
		String filename = "data/stopwords-onix.txt";

		FileInputStream fstream = null;
		try
		{
			fstream = new FileInputStream(filename);
		} catch (FileNotFoundException e)
		{
			e.printStackTrace();
			System.err.println("Stopword File Onix Not Found.");
		}

		DataInputStream in = new DataInputStream(fstream);
		BufferedReader br = new BufferedReader(new InputStreamReader(in));
		String strLine = null;

		try
		{
			while ((strLine = br.readLine()) != null)
			{

				StringTokenizer st = new StringTokenizer(strLine, " ");
				while (st.hasMoreTokens())
				{
					String currChunk = st.nextToken();
					System.out.println(currChunk);
						if(!currChunk.isEmpty())
						{
							stopwords.put(currChunk, currChunk);
						}
				}
			}
		} catch (IOException e)
		{
			e.printStackTrace();
			System.err.println("Exception while reading Onix Stopword file.");
			return null;
		}
		return stopwords;
	}

	static HashMap<String, IRDocument> parseTextFile(String filename)
	{
		HashMap<String, Word> werd = new HashMap<String, Word>();
		HashMap<String, IRDocument> output = new HashMap<String, IRDocument>();
		// ArrayList<TextToken> tok = new ArrayList<TextToken>();

		FileInputStream fstream = null;
		try
		{
			fstream = new FileInputStream(filename);
		} catch (FileNotFoundException e)
		{
			e.printStackTrace();
			System.err.println("File Not Found.");
			return null;
		}

		DataInputStream in = new DataInputStream(fstream);
		BufferedReader br = new BufferedReader(new InputStreamReader(in));
		String strLine = null;
		filename = filename.substring(0, filename.indexOf("."));

		try
		{
			while ((strLine = br.readLine()) != null)
			{
				// for each line...
				// System.out.println("Now processing: "+ strLine);
				stringToTextTokens(strLine, werd);
			}
		} catch (IOException e)
		{
			e.printStackTrace();
			System.err.println("Exception while reading file.");
			return null;
		}

		IRDocument result = new IRDocument();
		result.id = filename;
		result.hashMap = werd;
		output.put(filename, result);
		return output;
	}

	static HashMap<String, Word> stringToTextTokens(String s,
			HashMap<String, Word> tokens)
	{
		StringTokenizer st = new StringTokenizer(s, " ");
		Stemmer stemr = new Stemmer();
		while (st.hasMoreTokens())
		{
			String currChunk = st.nextToken();
			String[] lis = currChunk.split("[^0-9A-Za-z'-]");
			for (int i = 0; i < lis.length; i++)
			{
				// TODO check for stopword here
				// TODO if not a stopword, stem it
				char[] tmp = lis[i].trim().toCharArray();
				stemr.add(tmp, lis[i].length());
				stemr.stem();
				lis[i] = stemr.toString();

				if (!lis[i].isEmpty())
				{
					Word ex = tokens.get(lis[i].toLowerCase());
					if (ex != null)
					{
						ex.addOne();
					} else
					{
						tokens.put(lis[i].toLowerCase(), new Word(lis[i]
								.toLowerCase()));
					}
				}
			}
		}

		return tokens;
	}

	public static HashMap<String, IRDocument> ParseXML(String filename)
	{
		HashMap<String, IRDocument> output = new HashMap<String, IRDocument>();
		DocumentImpl doc = (DocumentImpl) parseXMLDomain(filename);
		if (doc == null)
		{
			// file was not found
			return null;
		}
		Node root = doc.getLastChild();
		AllElements allelements = new AllElements();
		TreeWalkerImpl walk = (TreeWalkerImpl) doc.createTreeWalker(root,
				NodeFilter.SHOW_ELEMENT, (NodeFilter) allelements, true);

		NodeList subDocs = root.getChildNodes();
		Node cur = walk.firstChild();
		filename = filename.substring(0, filename.indexOf("."));
		int i = 0;
		while (cur != null)
		{
			// convert each child node into a Document.
			IRDocument ird = new IRDocument();
			HashMap<String, Word> doctext = new HashMap<String, Word>();

			// System.out.println(cur.getTextContent());
			stringToTextTokens(cur.getTextContent(), doctext);
			ird.hashMap = doctext;
			ird.id = filename + "-" + i;
			output.put(filename + "-" + i, ird);
			cur = walk.nextSibling();
			i++;
		}
		return output;
	}

	static org.w3c.dom.Document parseXMLDomain(String filename)
	{
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		factory.setIgnoringComments(true);
		factory.setCoalescing(true); // Convert CDATA to Text nodes;
		factory.setNamespaceAware(false); // No namespaces: this is default
		factory.setValidating(false); // Don't validate DTD: also default
		factory.setExpandEntityReferences(false);
		factory.setIgnoringElementContentWhitespace(true);
		DocumentBuilder parser = null;
		org.w3c.dom.Document document = null;

		try
		{
			parser = factory.newDocumentBuilder();
			parser.setErrorHandler(null);
			document = parser.parse(new File(filename));
		} catch (Exception e)
		{
			System.out.println("File not found\n");
			// e.printStackTrace();
			// System.exit(-1);
			return null;
		}
		return document;
	}
	
	public String printDoc(String dname)
	{
		String output = "";
		return output;
	}
}

// filters the elements of the XML document
class AllElements implements NodeFilter
{
	public short acceptNode(Node n)
	{
		if (n.getNodeType() == Node.ELEMENT_NODE)
			return FILTER_ACCEPT;
		return FILTER_SKIP;
	}
}
