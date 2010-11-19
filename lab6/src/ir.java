import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.StringTokenizer;

class ir {
	static HashMap<String,IRDocument> docs;
	public static void main(String[] args) {
		docs = new HashMap<String,IRDocument>();
		
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		String input = null;
		DeserializeDoc();
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
				SerializeDoc();
				System.exit(0);
			}
			else if(tokInput.get(0).compareToIgnoreCase("READ") == 0){
				tokInput.remove(0);
				if(tokInput.get(0).compareToIgnoreCase("LIST") == 0){
					tokInput.remove(0);
				}
				//for each fileName, make a list of IRDoc, and add it to the hashmap of docs
				for(int i = 0; i < tokInput.size(); i++){
				    HashMap<String,IRDocument> temp = Parser.Read(tokInput.get(i));
				    docs.putAll(temp);
				}
			}
			else if(tokInput.get(0).compareToIgnoreCase("LIST") == 0){
				List();
			}
			else if(tokInput.get(0).compareToIgnoreCase("CLEAR") == 0){
				System.out.println("Cleared documents");
				docs.clear();
				//TODO possibly remove persistant data
			}
			else if(tokInput.get(0).compareToIgnoreCase("PRINT") == 0 && tokInput.size() == 2){
			    if(!docs.containsKey(tokInput.get(1))){
                    System.out.println("Document ID: " + tokInput.get(1)+" does not exist!");
                }
                else{
                    //TODO make a function that seeks to the needed document in a text or xml file
                }
			}
			else if(tokInput.get(0).compareToIgnoreCase("SHOW") == 0 && tokInput.size() == 2){
			    if(!docs.containsKey(tokInput.get(1))){
			        System.out.println("Document ID: " + tokInput.get(1)+" does not exist!");
			    }
			    else{
			        System.out.println(docs.get(tokInput.get(1)).toString());
			    }
			}
			else if(tokInput.get(0).compareToIgnoreCase("SIM") == 0 && tokInput.size() == 3){
			    if(!docs.containsKey(tokInput.get(1))){
                    System.out.println("Document ID: " + tokInput.get(1)+" does not exist!");
                }
			    else if(!docs.containsKey(tokInput.get(2))){
			        System.out.println("Document ID: " + tokInput.get(2)+" does not exist!");
			    }
                else{
                    Similarity(docs.get(tokInput.get(1)),docs.get(tokInput.get(2)));
                }
			}
			else if(tokInput.get(0).compareToIgnoreCase("SEARCH") == 0){
                if(tokInput.get(1).compareToIgnoreCase("DOC") == 0){
                    if(!docs.containsKey(tokInput.get(2))){
                        System.out.println("Document ID: " + tokInput.get(1)+" does not exist!");
                    }
                    else{
                        //TODO search for documents similar to given document
                    }
                }
                else if(tokInput.size() == 1){
                    //TODO take in a string, search for documents relevant to the query string
                }
                else{
                    System.out.println("Unrecognized command");
                }
			}
			else{
				System.out.println("Unrecognized command");
			}
			
		}

	}
	
    private static void Similarity(IRDocument document, IRDocument document2) {
        //TODO 
    }
    
    private static double TFIDF(IRDocument doc, String term){
        int freq = 0;
        if(doc.hashMap.containsKey(term)){
            freq = doc.hashMap.get(term).freq;
        }
        double TF = (double)freq/ (double)doc.wCount;
        
        int docCount = 0;
        for (IRDocument value : docs.values()) {
            if(value.hashMap.containsKey(term)){
                docCount++;
            }
        }
        double IDF = Math.log((double)docs.size() / (double)docCount)/Math.log(2.0);
        
        return TF*IDF;
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
	
	private static void List(){
	    System.out.println("Listing documents available in the system:");
        for (IRDocument value : docs.values()) {
            System.out.println(value.id);
        }
	}

    private static void SerializeDoc(){
        try {
            FileOutputStream fos;
            fos = new FileOutputStream("hm.save");
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(docs);
            oos.close();
        } catch (Exception e) {
            System.err.println("Error while trying to serialize doc!");
            e.printStackTrace();
        }
    }
    
    private static void DeserializeDoc(){
        if((new File("hm.save")).exists()){
            try {
                FileInputStream fis = new FileInputStream("hm.save");
                ObjectInputStream ois = new ObjectInputStream(fis);
                docs = (HashMap) ois.readObject();
                ois.close();
            } catch (Exception e) {
                System.err.println("Error while trying to deserialize doc!");
                e.printStackTrace();
            }
        }
    }
	
}
