import java.io.*;
import java.util.ArrayList;
import java.util.StringTokenizer;

class ir {
	static ArrayList<IRDocument> docs;
	public static void main(String[] args) {
		docs = new ArrayList<IRDocument>();
		
		
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
				tokInput.remove(0);
				if(tokInput.get(0).compareToIgnoreCase("LIST") == 0){
					tokInput.remove(0);
				}
				Read(tokInput);
			}
			else if(tokInput.get(0).compareToIgnoreCase("LIST") == 0){
				List();
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
    
	private static void Read(ArrayList<String> fileList){
		while(!fileList.isEmpty()){
			String fileName = fileList.get(0);
			fileList.remove(0);
			ArrayList<IRDocument> temp = Parser.Read(fileName);
			
		}
	}
	
	private static void List(){
		System.out.println("Listing documents available in the system:");
		for(int docIdx = 0; docIdx < docs.size(); docIdx++){
			System.out.println(docs.get(docIdx).toString());
		}
	}
}
