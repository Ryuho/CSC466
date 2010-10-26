package src;

import java.io.*;
import java.util.ArrayList;
import java.util.StringTokenizer;

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
}
