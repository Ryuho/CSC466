import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.StringTokenizer;


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
        return null;
    }
    static DecisionTree parseXMLTree(String filename){
        return null;
    }
}
