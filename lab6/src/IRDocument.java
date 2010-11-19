import java.util.HashMap;


class IRDocument{
    String id;
    HashMap<String, Word> hashMap;
    int wCount;
    
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