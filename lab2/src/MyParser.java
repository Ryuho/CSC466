import java.io.*;
import java.util.*;


public class MyParser {
	static ArrayList<Vector> parseNumCSV(String filename)
	{
		ArrayList<Vector> answer = new ArrayList<Vector>();
		
		FileInputStream fstream = null;
		try {
			fstream = new FileInputStream(filename);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			System.err.println("File Not Found.");
		}
		
		DataInputStream in = new DataInputStream(fstream);
		BufferedReader br = new BufferedReader(new InputStreamReader(in));
		String strLine = null;
		
		try {
			while ((strLine = br.readLine()) != null)
				{
					Vector tempVec = stringToVec(strLine);
					if(tempVec != null)
					{
						answer.add(tempVec);
					}
					
				}
		} catch (IOException e) {
			e.printStackTrace();
			System.err.println("Exception while reading file.");
		}
		

		return answer;
	}

	static Vector stringToVec(String s){
		s = s.replaceAll("^\\s*,", "0,");
		s = s.replaceAll(",{2}", ",0,");
		s = s.replaceAll(",{2}", ",0,");
		s = s.replaceAll(",\\s*$", ",0");

		boolean empyString = true;
		Vector answer = new Vector();

		StringTokenizer st = new StringTokenizer(s,",");

		while (st.hasMoreTokens()) {
			empyString = false;
			try{
				answer.addElement(Double.parseDouble(st.nextToken()));
				empyString = false;
			}catch(java.lang.NumberFormatException e){
				answer.addElement(0.0);
			}
		}
		
		if(empyString){
			return null;
		}
		return answer;
	}
	
}