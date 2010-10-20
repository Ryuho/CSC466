package src;

import java.io.*;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class Header {
    ArrayList<String> datas;

    Header(String fileName) {
        datas = new ArrayList<String>();

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
                datas = stringToStringAL(strLine);
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Exception while reading file.");
        }
    }
    
    private static ArrayList<String> stringToStringAL(String s){
        boolean empyString = true;
        ArrayList<String> answer = new ArrayList<String>();

        StringTokenizer st = new StringTokenizer(s," ,");

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
}
