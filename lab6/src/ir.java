import java.io.*;
import java.util.*;

class ir {
	static HashMap<String,IRDocument> docs;
	static Vocabulary vocab;
	public static void main(String[] args) {
		docs = new HashMap<String,IRDocument>();
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		String input = null;
		
		DeserializeDoc();
		System.out.println("IR System:");
		Parser.loadStopwords();
		
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
			else if(tokInput.get(0).compareToIgnoreCase("QUIT") == 0 &&
			        tokInput.size() == 1){
				System.out.println("Quitting");
				SerializeDoc();
				System.exit(0);
			}
			else if(tokInput.get(0).compareToIgnoreCase("READ") == 0 &&
                    tokInput.size() >= 1){
				tokInput.remove(0);
				if(tokInput.get(0).compareToIgnoreCase("LIST") == 0){
					tokInput.remove(0);
				}
				//for each fileName, make a list of IRDoc, and add it to the hashmap of docs
				for(int i = 0; i < tokInput.size(); i++){
				    HashMap<String,IRDocument> temp = Parser.Read(tokInput.get(i));
				    if(temp != null){
				        docs.putAll(temp);
				    }
				    else{
				        System.out.println("Could not read in "+ tokInput.get(i));
				    }
				}
				SerializeDoc();
			}
			else if(tokInput.get(0).compareToIgnoreCase("LIST") == 0 &&
                    tokInput.size() == 1){
				List();
			}
			else if(tokInput.get(0).compareToIgnoreCase("CLEAR") == 0 &&
                    tokInput.size() == 1){
				System.out.println("Cleared documents");
				docs.clear();
				SerializeDoc();
			}
			else if(tokInput.get(0).compareToIgnoreCase("PRINT") == 0 
			        && tokInput.size() == 2){
			    if(!docs.containsKey(tokInput.get(1))){
                    System.out.println("Document ID: " + tokInput.get(1)+" does not exist!");
                }
                else{
                    //System.out.println(Parser.printDoc(tokInput.get(1)));
                }
			}
			else if(tokInput.get(0).compareToIgnoreCase("SHOW") == 0 
			        && tokInput.size() == 2){
			    if(!docs.containsKey(tokInput.get(1))){
			        System.out.println("Document ID: " + tokInput.get(1)+" does not exist!");
			    }
			    else{
			        System.out.println(docs.get(tokInput.get(1)).toString());
			    }
			}
			else if(tokInput.get(0).compareToIgnoreCase("SIM") == 0 
			        && tokInput.size() == 3){
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
			else if(tokInput.get(0).compareToIgnoreCase("SEARCH") == 0 &&
                    tokInput.size() >= 2){
                if(tokInput.get(1).compareToIgnoreCase("DOC") == 0 &&
                        tokInput.size() >= 3){
                    if(!docs.containsKey(tokInput.get(2))){
                        System.out.println("Document ID: " + tokInput.get(1)+" does not exist!");
                    }
                    else{
                        //TODO search for documents similar to given document
                    }
                }
                else if(tokInput.size() == 2){
                    //TODO take in a string, search for documents relevant to the query string
                }
                else{
                    System.out.println("Unrecognized command");
                    printHelp();
                }
			}
			
			else{
				System.out.println("Unrecognized command");
				printHelp();
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
        double TF = (double)freq / (double)doc.wCount;
        
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
        }
        try {
            FileOutputStream fos;
            fos = new FileOutputStream("voc.save");
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(vocab);
            oos.close();
        } catch (Exception e) {
            System.err.println("Error while trying to serialize vocabulary!");
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
            }
        }
        if((new File("voc.save")).exists()){
            try {
                FileInputStream fis = new FileInputStream("voc.save");
                ObjectInputStream ois = new ObjectInputStream(fis);
                vocab = (Vocabulary) ois.readObject();
                System.out.println("---------");
                System.out.println(vocab.toString());
                System.out.println("---------");
                ois.close();
            } catch (Exception e) {
                System.err.println("Error while trying to deserialize vocabulary!");
                vocab = new Vocabulary();
            }
        }
        else{
            vocab = new Vocabulary();
        }
    }
    
    private static void printHelp(){
        System.out.println("Available commands are:");
        System.out.println("READ <file.xml>       Read text document(s) from an XML file");
        System.out.println("READ <file.txt>       Read text document from a text file");
        System.out.println("READ LIST <file>      Read text documents from files listed in <file>");
        System.out.println("LIST                  Output the list of documents available in the system");
        System.out.println("CLEAR                 Remove all documents from the system");
        System.out.println("PRINT <DocID>         Print the content of the document to screen");
        System.out.println("SHOW <DocID>          Show the internal representation of the document");
        System.out.println("SIM <Doc1> <Doc2>     Compute and output the similarity between two documents");
        System.out.println("SEARCH DOC <DocId>    Search for documents similar to given document");
        System.out.println("SEARCH \"string\"       Search for documents relevant to the query string");
        System.out.println("QUIT                  Quit the proram");
    }
    
}
