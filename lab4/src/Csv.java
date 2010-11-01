package src;

import java.io.*;
import java.util.ArrayList;
import java.util.StringTokenizer;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Element;

import com.sun.org.apache.xerces.internal.dom.DocumentImpl;
import com.sun.org.apache.xerces.internal.dom.ElementImpl;

public class Csv {
	static ArrayList<ArrayList<Double>> datas;
	static ArrayList<String> strings;
	static ArrayList<Integer> restrictions;

    Csv(String fileName) {
        datas = new ArrayList<ArrayList<Double>>();
        restrictions = new ArrayList<Integer>();
        strings = new ArrayList<String>();
        FileInputStream fstream = null;
        try {
            fstream = new FileInputStream(fileName);
        } catch (FileNotFoundException e) {
            System.err.println("File Not Found.");
            System.exit(1);
        }

        DataInputStream in = new DataInputStream(fstream);
        BufferedReader br = new BufferedReader(new InputStreamReader(in));
        String strLine = null;

        try {
            if((strLine = br.readLine()) != null) {
                restrictions = stringToIntegerAL(strLine);
            }
            while ((strLine = br.readLine()) != null) {
                ArrayList<Double> tempAL = stringToDoubleAL(strLine);
                if (tempAL != null){
                    datas.add(tempAL);
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Exception while reading file.");
        }
    }

    private static ArrayList<Integer> stringToIntegerAL(String s) {
        s = s.replaceAll("^\\s*,", "0,");
        s = s.replaceAll(",{2}", ",0,");
        s = s.replaceAll(",{2}", ",0,");
        s = s.replaceAll(",\\s*$", ",0");

        boolean empyString = true;
        ArrayList<Integer> answer = new ArrayList<Integer>();

        StringTokenizer st = new StringTokenizer(s, ",");
        
        String currString;
        while (st.hasMoreTokens()) {
            currString = st.nextToken();
            empyString = true;
            try {
                answer.add((int)Double.parseDouble(currString));
                empyString = false;
            } catch (java.lang.NumberFormatException e) {
            	System.err.println("Error encountered while parsing restriction (first line)!");
            }
        }

        if (empyString) {
            return null;
        }
        return answer;
    }
    
    private static ArrayList<Double> stringToDoubleAL(String s) {
        s = s.replaceAll("^\\s*,", "0,");
        s = s.replaceAll(",{2}", ",0,");
        s = s.replaceAll(",{2}", ",0,");
        s = s.replaceAll(",\\s*$", ",0");

        boolean empyString = true;
        ArrayList<Double> answer = new ArrayList<Double>();

        StringTokenizer st = new StringTokenizer(s, ",");
        
        String currString;
        while (st.hasMoreTokens()) {
            currString = st.nextToken();
            empyString = true;
            try {
                answer.add(Double.parseDouble(currString));
                empyString = false;
            } catch (java.lang.NumberFormatException e) {
            	answer.add(-1.0);
            	strings.add(currString);
            }
        }

        if (empyString) {
            return null;
        }
        return answer;
    }
    
    /*Treats datas like a matrix in 2 space, transposes it, 
     * and removes duplicates in the rows of the transposed matrix*/
    public static ArrayList<ArrayList<Double>> transposeData()
    {
    	ArrayList<ArrayList<Double>> shark = new ArrayList<ArrayList<Double>>();
        int m = 0;
        while(shark.size() != datas.get(0).size())
        {
        	shark.add(m, new ArrayList<Double>());
        	for(int i = 0; i < datas.size();i++)
        	{
        		if(!shark.get(m).contains(datas.get(i).get(m)))
        		{
        			shark.get(m).add(datas.get(i).get(m));
        		}
        	}
        	m++;
        }
        
        return shark;
    }
    public DocumentImpl toC45Doc(ArrayList<Cluster> jc) throws ParserConfigurationException, TransformerConfigurationException, TransformerException
	{
		DocumentBuilderFactory dbfac = DocumentBuilderFactory.newInstance();
        DocumentBuilder docBuilder = dbfac.newDocumentBuilder();
        DocumentImpl doc = (DocumentImpl) docBuilder.newDocument();
        
        //init counting. Transpose data matrix and cut out duplicates
        ArrayList<ArrayList<Double>> shark = transposeData();

        Element noo  = new ElementImpl(doc, "tree");
    	for(int i = 0; i < shark.size(); i++)
    	{
    		Element current = new ElementImpl(doc, "variable");
    		current.setAttribute("name", Integer.toString(i));
    		//find max int of each set
    		for(int j = 0; j < shark.get(i).size(); j++)
    		{
    			Element chip = new ElementImpl(doc, "group");
    			chip.setAttribute("name", Double.toString(shark.get(i).get(j)));
    			current.appendChild(chip);
    		}
    		noo.appendChild(current);
    	}
    	Element cat = new ElementImpl(doc, "Category");
		cat.setAttribute("name", "cluster");
    	for(int i = 0; i < jc.size(); i++)
    	{
    		Element cho = new ElementImpl(doc, "choice");
    		cho.setAttribute("name", Integer.toString(i));
    		cat.appendChild(cho);
    	}
    	noo.appendChild(cat);
    	doc.appendChild(noo);
    	
    	/*TransformerFactory transfac = TransformerFactory.newInstance();
    	Transformer trans = transfac.newTransformer();
    	trans.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "no");
    	trans.setOutputProperty(OutputKeys.INDENT, "yes");

    	//create string from xml tree
    	StringWriter sw = new StringWriter();
    	StreamResult result = new StreamResult(sw);
    	DOMSource source = new DOMSource(doc);
    	trans.transform(source, result);
    	String xmlString = sw.toString();
    	System.out.println(xmlString);
    	 */
		
		return doc;
	}
    
}
