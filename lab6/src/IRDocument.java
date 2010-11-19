import java.util.HashMap;
import java.util.Iterator;


class IRDocument{
    String id;
    HashMap<String, Word> hashMap;
    
	IRDocument(){
	}
	
	public String toString(){
	    String answer = "";
	    answer += "Document ID: " + id + "\n";
	    for (Object value : hashMap.values()) {
	        answer += value.toString() + "\n";
	    }

        return answer;
	}
}