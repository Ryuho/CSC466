import java.io.*;
import java.util.ArrayList;
import java.util.StringTokenizer;

class Core {
	
	public static void main(String[] args) {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		String input = null;
		
		System.out.println("IR System:");

		while(true){
			System.out.print("Core: ");
			try {
				input = br.readLine();
			} catch (IOException ioe) {
				System.out.println("IO error!");
				System.exit(1);
			}
			
			ArrayList<String> tokInput = stringToStringAL(input);
			
			//if empty string, just loop around
			if(tokInput.size() == 0){
				continue;
			}
			else if(tokInput.get(0).compareToIgnoreCase("QUIT") == 0){
				System.out.println("Quitting");
				System.exit(0);
			}
			else if(tokInput.get(0).compareToIgnoreCase("READ") == 0){
				System.out.println("READ not implemented yet!");
			}
			else if(tokInput.get(0).compareToIgnoreCase("LIST") == 0){
				System.out.println("LIST not implemented yet!");
			}
			else if(tokInput.get(0).compareToIgnoreCase("CLEAR") == 0){
				System.out.println("CLEAR not implemented yet!");
			}
			else if(tokInput.get(0).compareToIgnoreCase("PRINT") == 0){
				System.out.println("PRINT not implemented yet!");
			}
			else if(tokInput.get(0).compareToIgnoreCase("SHOW") == 0){
				System.out.println("SHOW not implemented yet!");
			}
			else if(tokInput.get(0).compareToIgnoreCase("SIM") == 0){
				System.out.println("SIM not implemented yet!");
			}
			else if(tokInput.get(0).compareToIgnoreCase("SEARCH") == 0){
				System.out.println("SEARCH not implemented yet!");
			}
			else{
				System.out.println("Unrecognized command");
			}
			
		}

	}
	
    private static ArrayList<String> stringToStringAL(String s) {
        s = s.replaceAll("^\\s*,", "0,");
        s = s.replaceAll(",{2}", ",0,");
        s = s.replaceAll(",{2}", ",0,");
        s = s.replaceAll(",\\s*$", ",0");

        ArrayList<String> answer = new ArrayList<String>();

        StringTokenizer st = new StringTokenizer(s, " ");
        
        String currString;
        while (st.hasMoreTokens()) {
            currString = st.nextToken();
            try {
                answer.add(currString);
            } catch (java.lang.NumberFormatException e) {
            	System.err.println("Error encountered while parsing command: "+ s);
            }
        }

        return answer;
    }
}
