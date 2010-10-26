package src;

import java.util.ArrayList;
import java.util.Random;

public class kmeans {
    static String filename;
    static int k;
    
    static int stopCriteria = 1;
    static int reassignMinimum = 4;
    static double clustoidDistMinimum = 1.0;
    static double SSEDecreaseMinimum = 5.0;
    
    
    public static void main(String[] args) {
        //java kmeans <Filename> <k>
        if(args.length == 2){
            filename = args[0];
            k = Integer.parseInt(args[1]);
        }
        else{
        	System.err.println("Use: java kmeans <Filename> <k>");
        	System.exit(1);
        }
        //read in the file and extract the needed information
        Csv csvInfo = new Csv(filename);
        ArrayList<ArrayList<Double>> dataPoints = csvInfo.datas;
        ArrayList<String> stringLists = csvInfo.strings;
        ArrayList<Integer> restrictions = csvInfo.restrictions;
        
        System.out.println("Restrictions:");
        System.out.println(restrictions);
        System.out.println("strings:");
        for(int i = 0; i < stringLists.size(); i++){
            System.out.println(stringLists.get(i));
        }
        System.out.println("Datas:");
        for(int i = 0; i < dataPoints.size(); i++){
            System.out.println(dataPoints.get(i));
        }
        
        //select initial centroid
        ArrayList<Centroid> cent = SIC_Random(dataPoints, k);
        //calculate the initial position
    	for(int i = 0; i < cent.size(); i++){
    		cent.get(i).calcCenter_Mean();
    	}
    	
    	//this becomes false when the main loop should be over
        boolean keepOnGoing = true;
        //keeps track of SSE to see if main loops should be over
        double SSEVal = -1;
        
        //while we should keep on going
        while(keepOnGoing){
        	//if it's not the first time around
        	if(SSEVal != -1){
        		//calculate the SSE
        		SSEVal = calcSSE(cent);
        	}
        	
        	//reset the centroids: clear all datapoints, and changed to false
        	for(int i = 0; i < cent.size(); i++){
        		cent.get(i).reset();
        	}
        	
        	//for each data points
        	for(int idx = 0; idx < dataPoints.size(); idx++){
        		//find the nearest centroid
        		int indexOfCent = nearestCentroid(dataPoints.get(idx),cent);
        		//put the data point into the centroid
        		cent.get(indexOfCent).add(dataPoints.get(idx));
        	}
        	
        	//recalcuate the centroid positions for each centroids
        	for(int i = 0; i < cent.size(); i++){
        		cent.get(i).calcCenter_Mean();
        	}
        	
        	//check if we should stop:-------------------------
        	
        	// reassignement of data pointers
        	int reassign = 0;
        	for(int i = 0; i < cent.size(); i++){
        		reassign += cent.get(i).reassigned;
        	}
        	if((stopCriteria == 1) && reassign <= reassignMinimum){
        		keepOnGoing = false;
        	}
        	// change in clustoids position
        	double distanceChanged = 0.0;
        	for(int i = 0; i < cent.size(); i++){
        		distanceChanged += cent.get(i).distChange();
        	}
        	if((stopCriteria == 2) && distanceChanged <= clustoidDistMinimum){
        		keepOnGoing = false;
        	}
        	// insignificant decrease in SSE
        	double decreseSSE = SSEVal - calcSSE(cent);
        	SSEVal = calcSSE(cent);
        	if((stopCriteria == 3) && decreseSSE <= SSEDecreaseMinimum){
        		keepOnGoing = false;
        	}
        }
        
        //print out and evaluate the centroids
    }

    //select initial centroid randomly
    private static ArrayList<Centroid> SIC_Random(ArrayList<ArrayList<Double>> arrayList, int k){
    	ArrayList<Centroid> answer = new ArrayList<Centroid>();
    	Random rand = new Random();
    	for(int i = 0; i < k; i++){
    		int index = Math.abs(rand.nextInt())%arrayList.size();
    		Centroid tempCent = new Centroid();
    		tempCent.add(arrayList.get(index));
    		answer.add(tempCent);
    	}
        return answer;
    }
    
    //select initial centroid using the select centroids algorithm
    private static ArrayList<Centroid> SIC_SelectCentroids(ArrayList<ArrayList<Double>> arrayList, int k){
    	//TODO
        return null;
    }
    
    //given a data point and list of centroids, return the index of the nearest centroid
    private static int nearestCentroid(ArrayList<Double> dataPoint, ArrayList<Centroid> cent){
    	//TODO
        return -1;
    }
    
    private static double calcSSE(ArrayList<Centroid> cent){
    	//TODO
    	return -1;
    }
}
