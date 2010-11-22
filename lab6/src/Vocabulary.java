import java.io.Serializable;
import java.util.HashMap;


public class Vocabulary implements Serializable
{
    private static final long serialVersionUID = -5466773756764427305L;
    static HashMap<String, Integer> vocab = new HashMap<String, Integer>();
	static int docNumber;  // tracker of which document is interated though
	
	Vocabulary()
	{
		vocab =  new HashMap<String, Integer>();
		docNumber = 0;
	}
	
	public String toString(){
	    String answer = "";
	   //Iterator<String, Word> iter = vocab. ;
	   // while(iter.hasNext()){
	   //     answer += iter.next().toString() + "\n";
	   // }
	    for(String value: vocab.keySet())
	    {
	    	answer += value + "|"+ vocab.get(value) +"\n";
	    }
        return answer;
	}
	
	public void addDocument(IRDocument d){
	    for(Word w : d.hashMap.values()){
	        //System.out.println("doing vocab stuff for "+w.str);
	        if(vocab.containsKey(w.str)){
	            vocab.put(w.str, vocab.get(w.str)+1);
	        }
	        else{
	            vocab.put(w.str, 1);
	        }
	    }
	}
	
}
