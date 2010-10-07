import java.util.*;

public class csvInfo {
    ArrayList<String> stringNames;
    ArrayList<Integer> domainSizes;
    String categoryAttribute;
    ArrayList<ArrayList<Integer>> dataSets;
    

    csvInfo(ArrayList<String> stringNames, 
            ArrayList<Integer> domainSizes, 
            ArrayList<ArrayList<Integer>> dataSets, 
            String categoryAttribute)
    {
        this.stringNames = stringNames;
        this.domainSizes = domainSizes;
        this.dataSets = dataSets;
        this.categoryAttribute = categoryAttribute;
    }
    
    public ArrayList<Integer> getDomainSizes() {
        return domainSizes;
    }

    public void setDomainSizes(ArrayList<Integer> domainSizes) {
        this.domainSizes = domainSizes;
    }
    
    public ArrayList<String> getStringNames() {
        return stringNames;
    }

    public void setStringNames(ArrayList<String> stringNames) {
        this.stringNames = stringNames;
    }

    public ArrayList<ArrayList<Integer>> getDataSets() {
        return dataSets;
    }

    public void setDataSets(ArrayList<ArrayList<Integer>> dataSets) {
        this.dataSets = dataSets;
    }

    public String getCategoryAttribute() {
        return categoryAttribute;
    }

    public void setCategoryAttribute(String categoryAttribute) {
        this.categoryAttribute = categoryAttribute;
    }
    
    @Override
    public String toString() {
        String answer = "";
        
        answer += this.stringNames.toString() + "\n";
        answer += this.domainSizes.toString() + "\n";
        answer += this.categoryAttribute.toString() + "\n";
        for(int index = 0; index < this.dataSets.size(); index++)
        {
            answer += this.dataSets.get(index).toString() + "\n";
        }

        return answer;
    }
    
}
