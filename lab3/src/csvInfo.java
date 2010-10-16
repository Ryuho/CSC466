import java.util.*;

public class csvInfo {
    ArrayList<String> stringNames;
    ArrayList<Integer> attributes;
    String categoryName;
    String idName;
    ArrayList<Data> dataSets;
    

    csvInfo(ArrayList<String> stringNames, 
            ArrayList<Integer> attributes, 
            ArrayList<Data> dataSets,
            String categoryName,
            String idName,
            int categoryIndex)
    {
        this.stringNames = stringNames;
        this.attributes = attributes;
        this.dataSets = dataSets;
        this.categoryName = categoryName;
        this.idName = idName;
    }
    
    
    @Override
    public String toString() {
        String answer = "";
        answer += "stringNames="+this.stringNames.toString() + "\n";
        answer += "attributes="+this.attributes.toString() + "\n";
        answer += "categoryName="+this.categoryName.toString() + "\n";
        answer += "idName="+this.idName.toString() + "\n";
        
        for(int index = 0; index < this.dataSets.size(); index++)
        {
            answer += this.dataSets.get(index).toString() + "\n";
        }

        return answer;
    }
    
}
