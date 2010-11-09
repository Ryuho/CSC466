

import java.io.*;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class Csv {
	//static ArrayList<ArrayList<Float>> datas;
	ArrayList<User> data;
	//static ArrayList<String> strings;
	//static ArrayList<Integer> restrictions;

    Csv(String fileName) {
        //datas = new ArrayList<ArrayList<Float>>();
        data = new ArrayList<User>();
        //restrictions = new ArrayList<Integer>();
        //strings = new ArrayList<String>();
        FileInputStream fstream = null;
        
        //opening input stream
        try {
            fstream = new FileInputStream(fileName);
        } catch (FileNotFoundException e) {
            System.err.println("File Not Found.");
            System.exit(1);
        }

        //init streams
        DataInputStream in = new DataInputStream(fstream);
        BufferedReader br = new BufferedReader(new InputStreamReader(in));
        String strLine = null;

        //reading and parsing file
        try {
            /*if((strLine = br.readLine()) != null) {  //NO RESTRICTIONS
                restrictions = stringToIntegerAL(strLine);
            }*/
            while ((strLine = br.readLine()) != null) {
                ArrayList<Float> tempAL = stringToFloatAL(strLine);
                if (tempAL != null){
                    data.add(new User(tempAL));
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Exception while reading file.");
        }
    }

    public static ArrayList<Integer> stringToIntegerAL(String s) {
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
    
    private static ArrayList<Float> stringToFloatAL(String s) {
        s = s.replaceAll("^\\s*,", "0,");
        s = s.replaceAll(",{2}", ",0,");
        s = s.replaceAll(",{2}", ",0,");
        s = s.replaceAll(",\\s*$", ",0");

        boolean empyString = true;
        ArrayList<Float> answer = new ArrayList<Float>();

        StringTokenizer st = new StringTokenizer(s, ",");
        
        String currString;
        while (st.hasMoreTokens()) {
            currString = st.nextToken();
            empyString = true;
            try {
                answer.add(Float.parseFloat(currString));
                empyString = false;
            } catch (java.lang.NumberFormatException e) {
            	answer.add(-1.0f);
            	//strings.add(currString);
            }
        }

        if (empyString) {
            return null;
        }
        return answer;
    }
    
    
    public static ArrayList<Double> stringToDoubleAL(String s) {
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
            	//strings.add(currString);
            }
        }

        if (empyString) {
            return null;
        }
        return answer;
    }
    
}
