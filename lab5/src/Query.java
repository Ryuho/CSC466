import java.util.ArrayList;
import java.util.Collections;

//package src;

public class Query {
	Csv csv;
	Query(Csv input){
		csv = input;
	}
	
    public float MeanUtility(int userID, int itemID){
        //blank out the user's item specified by param
        csv.data.get(userID).ratings().set(itemID, (float)99);
        
        //for each item with valid rating for all users, sum the ratings up
        float sum = 0;
        int numOfData = 0;
        
        
        
        for(int userIdx = 0; userIdx < csv.data.size(); userIdx++){
            if(csv.data.get(userIdx).ratings().get(itemID) != 99){
                sum += csv.data.get(userIdx).at(itemID);
                numOfData++;
            }
        }
        
        //now divide it to get the average
        sum = sum / (float)numOfData;
        
        return sum;
    }
    
    public float MeanUtilityWRank(int userID, int itemID, int N){
        //blank out the user's item specified by param
        csv.data.get(userID).ratings().set(itemID, (float)99);
        
        //for each item with valid rating for all users, sum the ratings up
        float sum = 0;
        int numOfData = 0;
        
        ArrayList<User> tempUsers = getRank(userID, csv.data, N);
        
        for(int userIdx = 0; userIdx < tempUsers.size(); userIdx++){
            if(tempUsers.get(userIdx).at(itemID) != 99){
                sum += tempUsers.get(userIdx).at(itemID);
                numOfData++;
            }
        }
        
        //now divide it to get the average
        sum = sum / (float)numOfData;
        
        return sum;
    }
	
    public float K(int userID, int itemID){
        float sum = 0;
        for(int i = 0; i < csv.data.size(); i++){
            if(i != userID){
                sum += Math.abs(CosineSimilarity(userID,i));
            }
        }
        return 1f/sum;
    }

    
    public float PearsonCorrelation(int UID1, int UID2){
        // sum1 / sqrt( (sum2)^2 * (sum3)^2)
        
        float sum1 = 0;
        float sum2 = 0;
        float sum3 = 0;
        for(int i = 0; i < csv.data.get(0).ratings().size(); i++){
        	if(csv.data.get(UID1).at(i) != 99 && csv.data.get(UID2).at(i) != 99){
                sum1 += (csv.data.get(UID1).at(i) - csv.data.get(UID1).averageRating()) *
                (csv.data.get(UID2).at(i) - csv.data.get(UID2).averageRating()) ;
        	}

            if(csv.data.get(UID1).at(i) != 99){
                sum2 += Math.pow((csv.data.get(UID1).at(i) - csv.data.get(UID1).averageRating()), 2);
            }
            
            if(csv.data.get(UID2).at(i) != 99){
                sum3 += Math.pow((csv.data.get(UID2).at(i) - csv.data.get(UID2).averageRating()), 2);
            }
            
            if(sum2 == 0)
            {
            	sum2+=.0001;
            }
            if(sum3 ==0)
            {
            	sum3 += .0001;
            }
        }
        
        if(Math.sqrt(sum2*sum3) == 0)
        {
        	System.err.println("oopz:" + sum2 + " " + sum3 + "UID2=" + UID2 + " UID1=" + UID1);
        	System.exit(-1);
        }
        
        float result = (float) (sum1 / Math.sqrt(sum2*sum3));
        if(result < -1.01 || result > 1.01)
        {
        	System.err.println("Illegal Pearson Correlation value of "+result);
        	System.exit(-1);
        }
        return result;
    }
    
    public float CosineSimilarity(int UID1, int UID2){
        // sum1 / sqrt( (sum2)^2 * (sum3)^2)
        
        float sum1 = 0;
        float sum2 = 0;
        float sum3 = 0;
        for(int i = 0; i < csv.data.get(0).ratings().size(); i++){
            if(csv.data.get(UID1).at(i) != 99 && csv.data.get(UID2).at(i) != 99){
                sum1 += csv.data.get(UID1).at(i) * csv.data.get(UID2).at(i);
            }

            if(csv.data.get(UID1).at(i) != 99){
                sum2 += Math.pow(csv.data.get(UID1).at(i), 2);
            }
            
            if(csv.data.get(UID2).at(i) != 99){
                sum3 += Math.pow(csv.data.get(UID2).at(i), 2);
            }
            
            if(sum2 == 0)
            {
                sum2+=.0001;
            }
            if(sum3 ==0)
            {
                sum3 += .0001;
            }
        }
        
        if(Math.sqrt(sum2*sum3) == 0)
        {
            System.err.println("oopz:" + sum2 + " " + sum3 + "UID2=" + UID2 + " UID1=" + UID1);
            System.exit(-1);
        }
        
        float result = (float) (sum1 / Math.sqrt(sum2*sum3));
        if(result < -1 || result > 1)
        {
            System.err.println("BAD Cosine Similarity!! No din din for you!");
            System.exit(-1);
        }
        return result;
    }
	
    
    private ArrayList<User> getRank(int userID, ArrayList<User> UA, int N){
        ArrayList<User> answer = new ArrayList<User>();
        ArrayList<Pair> pairList= new ArrayList<Pair>();
        for(int i = 0; i < UA.size(); i++){
            if(i != userID){
                pairList.add(new Pair(CosineSimilarity(userID,i), csv.data.get(i)));
            }
        }
        Collections.sort(pairList);
        
        for(int i = 0; i < N; i++){
            //System.out.println("simVar="+pairList.get(i).getSimValue());
            answer.add(pairList.get(i).getUser());
        }

        return answer;
    }
    
}
