
public class Pair implements Comparable<Object> 
{
    private float simValue;
    private User user;
    Pair(float simValue, User u){
        this.simValue = simValue;
        this.user = u;
    }
    
    public float getSimValue() {
        return simValue;
    }
    public void setSimValue(float simValue) {
        this.simValue = simValue;
    }
    public User getUser() {
        return user;
    }
    public void setUser(User user) {
        this.user = user;
    }
    
    public int compareTo(Object o){
        if (!(o instanceof Pair)){
            throw new ClassCastException("A Pair object expected.");
        }
        float anotherPersonAge = ((Pair) o).simValue;  
        if(anotherPersonAge > this.simValue){
            return 1;
        }
        else if(anotherPersonAge < this.simValue){
            return -1;
        }
        else{
            return 0;
        }
    }
}
