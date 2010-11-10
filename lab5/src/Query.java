import java.util.ArrayList;

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
	
	public float AdjustedWeightedSum(int userID, int itemID){
        //user's average rating + k * sim * (u(c',s) - uc') 
	    float userAverage = csv.data.get(userID).averageRating();
	    //calculate k
	    float k = K(userID,itemID);
	    
	    //calculate the sum of (sim(u[c],u[c']) * (u(c',s) - uc'))
	    float sum = 0;
        for(int cp = 0; cp < csv.data.size(); cp++){
            if(cp != userID && (csv.data.get(cp).at(itemID) != 99)){
                float CPAverage = csv.data.get(cp).averageRating();
                sum += (CosineSimilarity(userID,cp) * (csv.data.get(cp).at(itemID) - CPAverage));
            }
        }
        return userAverage + k * sum;
    }
	
	public float AdjustedWeightedSumTransformed(int userID, int itemID, ArrayList<Item> items){
        //user's average rating + k * sim * (u(c',s) - uc') 
	    float userAverage = csv.data.get(userID).averageRating();
	    //calculate k
	    float k = K(userID,itemID);
	    
	    //calculate the sum of (sim(u[c],u[c']) * (u(c',s) - uc'))
	    float sum = 0;
        for(int cp = 0; cp < csv.data.size(); cp++){
            if(cp != userID && (csv.data.get(cp).at(itemID) != 99)){
                float CPAverage = csv.data.get(cp).averageRating();
                sum += (PearsonCorrelationTransformed(userID,cp, items) 
                		* (csv.data.get(cp).at(itemID) - CPAverage));
            }
        }
        return userAverage + k * sum;
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
        if(result < -1 || result > 1)
        {
        	System.err.println("BAD Pearson Correlation!! No din din for you!");
        	System.exit(-1);
        }
        return result;
    }
    
    public float PearsonCorrelationTransformed(int UID1, int UID2, ArrayList<Item> items){
        // sum1 / sqrt( (sum2)^2 * (sum3)^2)
        
        float sum1 = 0;
        float sum2 = 0;
        float sum3 = 0;
        float sum4 = 0f;
        float sum5 = 0f;
        float fjAll = 0f;
        
        
        for(int i = 0; i < csv.data.get(0).ratings().size(); i++)
        {
        	float fj1 = items.get(i).invUserFreq();
        	fjAll += fj1;
        	
        	if(csv.data.get(UID1).at(i) != 99 && csv.data.get(UID2).at(i) != 99)
        	{	
                sum1 += fj1 * (csv.data.get(UID1).at(i)) * (csv.data.get(UID2).at(i));
        	}

            if(csv.data.get(UID1).at(i) != 99)
            {
                sum2 += fj1 * (csv.data.get(UID1).at(i));
                sum4 += Math.pow(fj1 * (csv.data.get(UID1).at(i)), 2);
            }
            
            if(csv.data.get(UID2).at(i) != 99)
            {
                sum3 += fj1 * (csv.data.get(UID2).at(i));
                sum5 += Math.pow(fj1 * (csv.data.get(UID2).at(i)), 2);
            }
            
            if(sum2 == 0)
            {
            	sum2+=.00001;
            }
            if(sum3 ==0)
            {
            	sum3 += .00001;
            }
            if(sum4 == 0)
            {
            	sum4 += .00001;
            }
            if(sum5 == 0)
            {
            	sum5 += .00001;
            }
            if(sum1 == 0)
            {
            	sum1 += .00001;
            }
        }
        
        float result = (float) ((fjAll*sum1 - sum2*sum3 ) / 
        		((fjAll * (sum4 - Math.pow(sum2,2)))     // U
        	   * (fjAll * (sum5 - Math.pow(sum3,2)))));  // V
        
        if(Math.abs((fjAll * (sum4 - Math.pow(sum2,2)))     // U
         	   * (fjAll * (sum5 - Math.pow(sum3,2)))) < Math.abs(fjAll*sum1 - sum2*sum3 ))
        {
        	//System.err.println(fjAll + " " + sum1 + " " +sum2 + " " +sum3 + " " +sum4 + " " +sum5 
        	//		+ " \n" + result);
        	//System.exit(-1);
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
        return null;
    }
    
}
