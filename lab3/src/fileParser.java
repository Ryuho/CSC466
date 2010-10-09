import java.io.*;
import java.util.*;
import org.w3c.dom.*;
import javax.xml.parsers.*;

import com.sun.org.apache.xerces.internal.parsers.DOMParser;


public class fileParser {
    static csvInfo parseCSV(String filename)
    {
        ArrayList<String> stringNames = new ArrayList<String>();
        ArrayList<Integer> domainSizes = new ArrayList<Integer>();
        String categoryAttribute = "";
        ArrayList<ArrayList<Integer>> dataSets = new ArrayList<ArrayList<Integer>>();
        

        FileInputStream fstream = null;
        try {
            fstream = new FileInputStream(filename);
        } catch (FileNotFoundException e) {
            System.err.println("File Not Found.");
            System.exit(1);
        }

        DataInputStream in = new DataInputStream(fstream);
        BufferedReader br = new BufferedReader(new InputStreamReader(in));
        String strLine = null;

        try {
            String firstLine = br.readLine();
            stringNames = stringToStringAL(firstLine);
            String secondLine = br.readLine();
            domainSizes = stringToIntegerAL(secondLine);
            categoryAttribute = br.readLine();
            
            while ((strLine = br.readLine()) != null)
                {
                ArrayList<Integer> tempAL = stringToIntegerAL(strLine);
                    if(tempAL != null)
                    {
                        dataSets.add(tempAL);
                    }

                }
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Exception while reading file.");
        }

        
        
        csvInfo answer = new csvInfo(stringNames, domainSizes, dataSets, categoryAttribute);
        return answer;
    }

    private static ArrayList<Integer> stringToIntegerAL(String s){
        s = s.replaceAll("^\\s*,", "0,");
        s = s.replaceAll(",{2}", ",0,");
        s = s.replaceAll(",{2}", ",0,");
        s = s.replaceAll(",\\s*$", ",0");

        boolean empyString = true;
        ArrayList<Integer> answer = new ArrayList<Integer>();

        StringTokenizer st = new StringTokenizer(s,", ");

        while (st.hasMoreTokens()) {
            empyString = true;
            try{
                answer.add(Integer.parseInt(st.nextToken()));
                empyString = false;
            }catch(java.lang.NumberFormatException e){
                answer.add(0);
            }
        }

        if(empyString){
            return null;
        }
        return answer;
    }
    
    private static ArrayList<String> stringToStringAL(String s){
        boolean empyString = true;
        ArrayList<String> answer = new ArrayList<String>();

        StringTokenizer st = new StringTokenizer(s,",");

        while (st.hasMoreTokens()) {
            empyString = true;
            try{
                answer.add(st.nextToken());
                empyString = false;
            }catch(java.lang.NumberFormatException e){
            }
        }

        if(empyString){
            return null;
        }
        return answer;
    }
    
    static DecisionTree parseXMLDomain(String filename){
        DecisionTree answer = new DecisionTree();
        
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        factory.setIgnoringComments(true);
        factory.setCoalescing(true); // Convert CDATA to Text nodes
        factory.setNamespaceAware(false); // No namespaces: this is default
        factory.setValidating(false); // Don't validate DTD: also default
        DocumentBuilder parser = null;
        Document document = null;
        
        NodeList domNL = null;
        NodeList varNL = null;
        NodeList grpNL = null;
        NodeList catNL = null;
        
        try {
            parser = factory.newDocumentBuilder();
            document = parser.parse(new File(filename));
            domNL = document.getElementsByTagName("domain");
            varNL = document.getElementsByTagName("variable");
            grpNL = document.getElementsByTagName("group");
            catNL = document.getElementsByTagName("Category");
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return null;
        }
        
        System.out.println("domain #:"+domNL.getLength());
        System.out.println("variable #:"+varNL.getLength());
        System.out.println("group #:"+grpNL.getLength());
        System.out.println("Category #:"+catNL.getLength());
        
        for(int index = 0; index < domNL.getLength(); index++){
            System.out.println(domNL.item(index));
        }

        for(int index = 0; index < varNL.getLength(); index++){
            System.out.println(varNL.item(index));
        }
        
        for(int index = 0; index < grpNL.getLength(); index++){
            System.out.println(grpNL.item(index));
        }
        
        for(int index = 0; index < catNL.getLength(); index++){
            System.out.println(catNL.item(index));
        }
        
        return answer;
    }
    
    static DecisionTree parseXMLTree(String filename){
        return null;
    }
}
