package src;

import java.io.*;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class Csv {
    ArrayList<ArrayList<Integer>> datas;
    ArrayList<Integer> restrictions;

    Csv(String fileName) {
        datas = new ArrayList<ArrayList<Integer>>();
        restrictions = new ArrayList<Integer>();
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
                ArrayList<Integer> tempAL = stringToIntegerAL(strLine);
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

        StringTokenizer st = new StringTokenizer(s, ", ");
        
        String currString;
        while (st.hasMoreTokens()) {
            currString = st.nextToken();
            empyString = true;
            try {
                answer.add((int)Double.parseDouble(currString));
                empyString = false;
            } catch (java.lang.NumberFormatException e) {
                //System.err.println("Couldn't parse "+ currString + " into int!");
            }
        }

        if (empyString) {
            return null;
        }
        return answer;
    }
}
