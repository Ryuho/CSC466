package src;

import java.io.*;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class Csv {
    ArrayList<ArrayList<Integer>> datas;

    Csv(String fileName) {
        datas = new ArrayList<ArrayList<Integer>>();

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
            while ((strLine = br.readLine()) != null) {
                ArrayList<Integer> tempAL = stringToIntegerAL(strLine);
                if (tempAL != null){
                    datas.add(tempAL);
                }

            }
        } catch (IOException e) {
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

        while (st.hasMoreTokens()) {
            empyString = true;
            try {
                answer.add(Integer.parseInt(st.nextToken()));
                empyString = false;
            } catch (java.lang.NumberFormatException e) {
                answer.add(0);
            }
        }

        if (empyString) {
            return null;
        }
        return answer;
    }
}
