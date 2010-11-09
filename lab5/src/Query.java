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
				sum += csv.data.get(userIdx).ratings().get(itemID);
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
            if(cp != userID){
                float CPAverage = csv.data.get(cp).averageRating();
                sum += (PearsonCorrelation(userID,cp, itemID) * (csv.data.get(cp).at(itemID) - CPAverage));
            }
        }
        return userAverage + k * sum;
    }
	
    public float K(int userID, int itemID){
        float sum = 0;
        for(int i = 0; i < csv.data.size(); i++){
            if(i != userID){
                sum += Math.abs(PearsonCorrelation(userID,i, itemID));
            }
        }
        return 1f/sum;
    }
	

	
    public float PearsonCorrelation(int UID1, int UID2, int itemID){
        // sum1 / sqrt( (sum2)^2 * (sum3)^2)
        
        float sum1 = 0;
        float sum2 = 0;
        float sum3 = 0;
        for(int i = 0; i < csv.data.size(); i++){
            sum1 += (csv.data.get(UID1).at(i) - csv.data.get(UID1).averageRating()) *
                        (csv.data.get(UID2).at(i) - csv.data.get(UID2).averageRating()) ;
            
            sum2 += Math.pow((csv.data.get(UID1).at(i) - csv.data.get(UID1).averageRating()), 2);
            
            sum3 += Math.pow((csv.data.get(UID2).at(i) - csv.data.get(UID2).averageRating()), 2);
        }
        
        
        
        return (float) (sum1 / Math.sqrt(sum2*sum3));
}
	
	/*
    public static double getPearsonCorrelation(Vector a, Vector b)
    {
            int siz1 = a.getSize();
            int siz2 = b.getSize();
            double product = 0.0;

            if(siz2 > siz1)
            {
                    a = a.makeEqualLength(a, b);
                    siz1 = siz2;
            }
            else if(siz2 < siz1)
            {
                    b = b.makeEqualLength(b, a);
            }

            for(int i = 0; i < siz1; i++)
            {
                    product += (a.getElement(i) - vectorMean(a)) *
                            (b.getElement(i) - vectorMean(b));
            }

            product /= (siz1 - 1) * standardDeviation(a) * standardDeviation(b);
            return product;
    }*/
	

	
}
