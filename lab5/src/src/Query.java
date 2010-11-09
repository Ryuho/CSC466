package src;

public class Query {
	static Csv csv;
	Query(Csv input){
		csv = input;
	}
	
	float MeanUtility(int userId, int itemId){
		//blank out the user's item specified by param
		csv.data.get(userId).ratings().set(itemId, (float)99);
		
		//for each item with valid rating for all users, sum the ratings up
		float sum = 0;
		int numOfData = 0;
		for(int userIdx = 0; userIdx < csv.data.size(); userIdx++){
			if(csv.data.get(userIdx).ratings().get(itemId) != 99){
				sum += csv.data.get(userIdx).ratings().get(itemId);
				numOfData++;
			}
		}
		
		//now divide it to get the average
		sum = sum / (float)numOfData;
		
		return sum;
	}

	//sim(u[c],u[c'])
	// which means a specified c user, find similarity for all ratings (s)
	float Similarity(int UID1, int UID2){
		return 0;
	}
	
	float K(int UID1, int UID2){
		float sum = 0;
		for(int i = 0; i < csv.data.size(); i++){
			for(int j = i+1; j < csv.data.size(); j++){
				sum += Math.abs(Similarity(i,j));
			}
		}
		return (float)1/sum;
	}
	
	float AdjustedWeightedSum(int userId, int itemId){
		//user's average rating + k * sim * (u(c',s) - uc') 
		return 99;
	}
	
	float PearsonCorrelation(int userId, int itemId){
		return 99;
	}
	

}
