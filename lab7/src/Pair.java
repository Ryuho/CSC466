
public class Pair implements Comparable<Object> 
{
    private double simValue;
    private String str;
    Pair(double simValue, String str){
        this.simValue = simValue;
        this.str = str;
    }
    
    public double getSimValue() {
        return simValue;
    }
    public void setSimValue(float simValue) {
        this.simValue = simValue;
    }
    public String getStr() {
        return str;
    }
    public void setStr(String str) {
        this.str = str;
    }
    
    public int compareTo(Object o){
        if (!(o instanceof Pair)){
            throw new ClassCastException("A Pair object expected.");
        }
        double anotherSimValue = ((Pair) o).simValue;  
        if(anotherSimValue > this.simValue){
            return 1;
        }
        else if(anotherSimValue < this.simValue){
            return -1;
        }
        else{
            return 0;
        }
    }
}