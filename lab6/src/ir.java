import java.io.*;
import java.util.*;

class ir {
	static HashMap<String,IRDocument> docs;
    final static double k1 = 1.5;
    final static double b = 0.75;
    final static double k2 = 500;
	public static void main(String[] args) {
		docs = new HashMap<String,IRDocument>();
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		String input = null;
		
		DeserializeDoc();
		System.out.println("IR System:");
		Parser.loadStopwords();
		
		while(true){
			System.out.print("IR: ");
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
                    System.out.println(Parser.printDoc(tokInput.get(1)));
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
                    double sim = Similarity(docs.get(tokInput.get(1)),docs.get(tokInput.get(2)));
                    System.out.println("TFIDF similarity Value: "+sim);
                    double simOka = Okapi(docs.get(tokInput.get(1)),docs.get(tokInput.get(2)));
                    System.out.println("Okapi similarity Value: "+simOka);
                }
			}
			else if(tokInput.get(0).compareToIgnoreCase("SEARCH") == 0 &&
                    tokInput.size() >= 2){
                if(tokInput.get(1).compareToIgnoreCase("DOC") == 0 &&
                        tokInput.size() == 3){
                    if(docs.containsKey(tokInput.get(2))){
                        searchDoc(docs.get(tokInput.get(2)),docs.size()-1);
                    }
                    else{
                        System.out.println("Document ID: " + tokInput.get(1)+" does not exist!");
                    }
                }
                else if(tokInput.get(1).compareToIgnoreCase("DOC") == 0 &&
                        tokInput.size() == 4){
                    if(docs.containsKey(tokInput.get(2))){
                        int size = Integer.valueOf(tokInput.get(3));
                        searchDoc(docs.get(tokInput.get(2)), size);
                    }
                    else{
                        System.out.println("Document ID: " + tokInput.get(1)+" does not exist!");
                    }
                }
                else if(tokInput.size() >= 2){
                    tokInput.remove(0);
                    int size = docs.size();
                    if(tokInput.get(0).startsWith("\"")){
                        tokInput.set(0, tokInput.get(0).substring(1));
                    }
                    else{
                        System.out.println("Usage: SEARCH \"string\"");
                        continue;
                    }
                    if(tokInput.get(tokInput.size()-1).endsWith("\"")){
                        String temp = tokInput.get(tokInput.size()-1);
                        tokInput.set(tokInput.size()-1, temp.substring(0, temp.length()-1));
                    }
                    else{
                        System.out.println("Usage: SEARCH \"string\"");
                        continue;
                    }
                    searchString(tokInput,size);
                }
                else{
                    System.out.println("Usage: SEARCH DOC <DocId>");
                    System.out.println("Usage: SEARCH DOC <DocId> (<number>)");
                    System.out.println("Usage: SEARCH \"string\"");
                    System.out.println("Usage: SEARCH \"string\" (<number>)");
                    printHelp();
                }
			}
			
			else{
				System.out.println("Unrecognized command");
				printHelp();
			}
			
		}

	}
	

    private static void searchString(ArrayList<String> strList, int size){
        String input = concactALString(strList);
        IRDocument fakeDoc = Parser.parseQuery(input);
        
        searchDoc(fakeDoc,size);
    }
    
	private static void searchDoc(IRDocument d, int size){
        ArrayList<Pair> list = new ArrayList<Pair>();
        for(IRDocument tempDoc : docs.values()){
            if(tempDoc.id.compareTo(d.id) != 0){
                double tempSim = Similarity(d,tempDoc);
                if(tempSim != 0){
                    list.add(new Pair(tempSim,tempDoc.id));
                }
            }
        }
        Collections.sort(list);
        
        System.out.println("Using TFIDF for comparison:");
        int i = 0;
        while(i < list.size() && i < size){
            System.out.println("DocID: "+list.get(i).getStr()+ "\t" +list.get(i).getSimValue());
            i++;
        }
        
        System.out.println("Using Okapi for comparison:");
        list = new ArrayList<Pair>();
        for(IRDocument tempDoc : docs.values()){
            if(tempDoc.id.compareTo(d.id) != 0){
                double tempSim = Okapi(d,tempDoc);
                if(tempSim != 0){
                    list.add(new Pair(tempSim,tempDoc.id));
                }
            }
        }
        Collections.sort(list);
        
        i = 0;
        while(i < list.size() && i < size){
            System.out.println("DocID: "+list.get(i).getStr()+ "\t" +list.get(i).getSimValue());
            i++;
        }
    }
	
	private static String concactALString(ArrayList<String> strList){
	    String answer = "";
	    for(int i = 0; i < strList.size(); i++){
	        answer += strList.get(i) + " ";
	    }
        return answer;
    }
    
    private static double Okapi(IRDocument doc1, IRDocument doc2){
        double answer = 0;
        double sum1 = 0;
        double sum2 = 0;
        double sum3 = 0;
        int docFreq = 0;
        int size = 0;
        HashMap<String,Word> bigHM;
        HashMap<String,Word> smallHM;
        if(doc1.hashMap.size() >= doc2.hashMap.size()){
            bigHM = doc1.hashMap;
            smallHM = doc2.hashMap;
        }
        else{
            bigHM = doc2.hashMap;
            smallHM = doc1.hashMap;
        }
        
        //figure out the avgByteLength
        double avgByteLength = 0;
        for (IRDocument value : docs.values()) {
            avgByteLength += value.bytelength;
        }
        avgByteLength /= (double)docs.size();
        
        
        for(Word w : bigHM.values()){
            if(smallHM.containsKey(w.str)){
                docFreq = 0;
                for (IRDocument value : docs.values()) {
                    if(value.hashMap.containsKey(w.str)){
                        docFreq++;
                    }
                }
                
                sum1 = ((double)docs.size()-((double)docFreq)+0.5);
                sum1 /= ((double)docFreq)+0.5;
                
                sum1 = Math.log(sum1)/Math.log(Math.E);
                
                sum2 = (k1+1.0)*(doc1.hashMap.get(w.str).freq);
                sum2 /= k1*(1.0 - b + b*((double)doc1.bytelength)/(avgByteLength)) + (doc1.hashMap.get(w.str).freq);
                
                sum3 = (k2 + 1.0)*(doc2.hashMap.get(w.str).freq);
                sum3 /= k2 + (doc2.hashMap.get(w.str).freq);
                
                answer += sum1*sum2*sum3;
            }
        }
        return answer;        
    }
	
    private static double Similarity(IRDocument doc1, IRDocument doc2) {
        double answer = 0;
        double sum1 = 0;
        double sum2 = 0;
        double sum3 = 0;
        
        int size = 0;
        HashMap<String,Word> temp;
        if(doc1.hashMap.size() < doc2.hashMap.size()){
            size = doc1.hashMap.size();
            temp = doc1.hashMap;
        }
        else{
            size = doc2.hashMap.size();
            temp = doc2.hashMap;
        }
        for(Word w : temp.values()){
            sum1 += TFIDF(doc1,w) * TFIDF(doc2,w);
            //System.out.print(w.str+" "+TFIDF(doc1,w)+"\t"+TFIDF(doc2,w));
            //System.out.println();
        }
        
        
        for(Word w : doc1.hashMap.values())
        {
            sum2 += Math.pow(TFIDF(doc1,w),2);
        }
        
        for(Word w : doc2.hashMap.values())
        {
            sum3 += Math.pow(TFIDF(doc1,w),2);
        }
        
        //System.out.println("sim1="+sum1);
        //System.out.println("sim2="+sum2);
        //System.out.println("sim3="+sum3);
        
        answer = sum1 / Math.sqrt(sum2*sum3);
        
        if(Double.isInfinite(answer) || Double.isNaN(answer)){
            answer = 0;
        }
        
        return answer;
    }
    
    public static double TFIDF(IRDocument doc, Word w){
        //System.out.println("==============================");
        //System.out.println(doc.toString());
        //System.out.println("Word="+w.str);
        int freq = 0;
        if(doc.hashMap.containsKey(w.str)){
            freq = doc.hashMap.get(w.str).freq;
        }
        int maxFreq = 0;
        for(Word tempW : doc.hashMap.values()){
            if(maxFreq < tempW.freq){
                maxFreq = tempW.freq;
            }
        }
        //System.out.println("freq="+freq);
        //System.out.println("maxFreq="+maxFreq);
        double TF = ((double)freq) / ((double)maxFreq);
        //System.out.println("TF="+TF);
        
        int docFreq = 0;
        for (IRDocument value : docs.values()) {
            if(value.hashMap.containsKey(w.str)){
                docFreq++;
                //System.out.println(w.str+" in "+ "documentID "+value.id);
            }
        }
        double IDF = Math.log((double)docs.size() / (double)docFreq) / Math.log(2.0);
        
        double answer = TF*IDF;
        
        if(Double.isInfinite(answer)){
            return 0;
        }
        else if(Double.isNaN(answer)){
            return 0;
        }
        //System.out.println("==============================");
        return TF*IDF;
    }

    private static ArrayList<String> stringToStringAL(String s) {
        if(s == null){
            return null;
        }
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
