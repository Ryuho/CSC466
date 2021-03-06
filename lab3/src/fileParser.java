package src;

import java.io.*;
import java.util.*;
import org.w3c.dom.*;
import javax.xml.parsers.*;


public class fileParser {
	
	static ArrayList<Integer> parseRestricted(String filename)
	{
		//ArrayList<String> stringNames = new ArrayList<String>();
        ArrayList<Integer> domainSizes = new ArrayList<Integer>();
       
        FileInputStream fstream = null;
        try {
            fstream = new FileInputStream(filename);
        } catch (FileNotFoundException e) {
            System.err.println("File Not Found.");
            System.exit(1);
        }

        DataInputStream in = new DataInputStream(fstream);
        BufferedReader br = new BufferedReader(new InputStreamReader(in));
        
        try {
            String firstLine = br.readLine();
            domainSizes = stringToIntegerAL(firstLine);
            
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Exception while reading file.");
        }
        
        return domainSizes;

	}
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

        int idIndex = -1;
        int categoryIndex = -1;
        int categoryNumber = -1;
        String categoryName = "";
        String idName = "";
        
        for(int i = 0; i < domainSizes.size(); i++){
        	if(domainSizes.get(i) == -1){
        		idIndex = i;
        		idName = stringNames.get(i);
        	}
        }
        
        for(int i = 0; i < stringNames.size(); i++){
        	if(stringNames.get(i).compareTo(categoryAttribute) == 0){
        		categoryNumber = domainSizes.get(i);
        		categoryIndex = i;
        		categoryName = stringNames.get(i);
        	}
        }
        
        if(idIndex < categoryIndex){
        	domainSizes.remove(categoryIndex);
        	domainSizes.remove(idIndex);
        	stringNames.remove(categoryIndex);
        	stringNames.remove(idIndex);
        }
        else{
        	domainSizes.remove(idIndex);
        	domainSizes.remove(categoryIndex);
        	stringNames.remove(idIndex);
        	stringNames.remove(categoryIndex);
        }
        
        ArrayList<Data> datas = new ArrayList<Data>();
        if(idIndex < categoryIndex){
            for(int i = 0; i < dataSets.size(); i++){
            	//record id and category choice
            	int id = dataSets.get(i).get(idIndex);
            	int category = dataSets.get(i).get(categoryIndex);
            	//remove id and category choice from data
            	dataSets.get(i).remove(categoryIndex);
            	dataSets.get(i).remove(idIndex);
            	//put all of it in Data
            	Data temp = new Data(dataSets.get(i), id, category);
            	datas.add(temp);
            }
            categoryIndex--;
        }
        else{
            for(int i = 0; i < dataSets.size(); i++){
            	//record id and category choice
            	int id = dataSets.get(i).get(idIndex);
            	int category = dataSets.get(i).get(categoryIndex);
            	//remove id and category choice from data
            	dataSets.get(i).remove(idIndex);
            	dataSets.get(i).remove(categoryIndex);
            	//put all of it in Data
            	Data temp = new Data(dataSets.get(i), id, category);
            	datas.add(temp);
            }
            idIndex--;
        }
        
        csvInfo answer = new csvInfo(stringNames, domainSizes, datas, categoryName, idName, categoryNumber,categoryIndex);
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
    
    static Document parseXMLDomain(String filename){
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        factory.setIgnoringComments(true);
        factory.setCoalescing(true); // Convert CDATA to Text nodes; 
        factory.setNamespaceAware(false); // No namespaces: this is default
        factory.setValidating(false); // Don't validate DTD: also default
    	factory.setExpandEntityReferences(false); 
    	factory.setIgnoringElementContentWhitespace(true);
        DocumentBuilder parser = null;
        Document document = null;
        
        /*
        NodeList domNL = null;
        NodeList varNL = null;
        NodeList grpNL = null;
        NodeList catNL = null;
        */
        
        try {
            parser = factory.newDocumentBuilder();
            parser.setErrorHandler(null);
            document = parser.parse(new File(filename));
            /*
            domNL = document.getElementsByTagName("Tree");
            varNL = document.getElementsByTagName("node");
            grpNL = document.getElementsByTagName("edge");
            catNL = document.getElementsByTagName("decision");
            */
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            //return null;
            System.exit(-1);
        }
        /*
        System.out.println("domain #:"+domNL.getLength());
        System.out.println("variable #:"+varNL.getLength());
        System.out.println("group #:"+grpNL.getLength());
        System.out.println("Category #:"+catNL.getLength());
        
        for(int index = 0; index < domNL.getLength(); index++){
            System.out.println(domNL.item(index).getAttributes().item(0));
        }

        for(int index = 0; index < varNL.getLength(); index++){
            System.out.println(varNL.item(index).getAttributes().item(0));
        }
        
        for(int index = 0; index < grpNL.getLength(); index++){
            System.out.println(grpNL.item(index).getAttributes().item(0));
        }
        
        for(int index = 0; index < catNL.getLength(); index++){
            System.out.println(catNL.item(index).getAttributes().item(0));
        }
        */
        return document;
    }
    
    
}
