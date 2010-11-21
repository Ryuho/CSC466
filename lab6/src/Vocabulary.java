import java.io.Serializable;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;


public class Vocabulary implements Serializable
{
    private static final long serialVersionUID = -5466773756764427305L;
    static HashMap<String, Word> vocab = new HashMap<String, Word>();
	static int docNumber;  // tracker of which document is interated though
	
	Vocabulary()
	{
		vocab =  new HashMap<String, Word>();
		docNumber = 0;
	}
	
	public String toString(){
	    String answer = "";
	   //Iterator<String, Word> iter = vocab. ;
	   // while(iter.hasNext()){
	   //     answer += iter.next().toString() + "\n";
	   // }
	    for(Word value: vocab.values())
	    {
	    	answer += value.str + "\n";
	    }
        return answer;
	}
}
