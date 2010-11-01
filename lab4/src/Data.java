package src;
import java.util.ArrayList;


public class Data {
	ArrayList<Double> dataSets;
	int id;
	int category;

	
	public Data(ArrayList<Double> dataSets, int id, int category){
		this.dataSets = dataSets;
		this.id = id;
		this.category = category;
	}
	
    public Data(Data data) {
		this.dataSets = new ArrayList<Double>(data.dataSets);
		this.id = data.id;
		this.category = data.category;
	}

	@Override
    public String toString() {
        String answer = "";
        answer += this.dataSets.toString(); 
        answer += "|id="+id+"|category="+category; 
        return answer;
    }
}
