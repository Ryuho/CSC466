import java.io.Serializable;
import java.util.HashMap;


class IRDocument implements Serializable{
    private static final long serialVersionUID = -6973441547574981818L;
    String id;
    HashMap<String, Word> hashMap;
    int wCount;
    
	IRDocument(){
	    
	}
	
	public String toString(){
	    String answer = "";
	    answer += "Document ID: " + id + "\n";
	    for (Word value : hashMap.values()) {
	        answer += value.toString() + "\n";
	    }

        return answer;
	}
}