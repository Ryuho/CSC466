package src;

import java.util.ArrayList;

public class hclustering {
    public static void main(String[] args) 
    {  
    	//hclustering <Filename.csv> <threshold>
    	

    }
    
    private int getDimensions(ArrayList<Integer> restrictions)
    {
    	int dimensions = 0;
    	for(int i =0; i < restrictions.size(); i++)
    	{
    		if(restrictions.get(i) != 0 )
    		{
    			dimensions++;
    		}
    	}
    	return dimensions;
    }
    
   
    
    //Main algorithm of the Agglomerative heriarchical clustering algorithm
    // Takes dataset D as input
    public Dendogram Agglo(double threshold, Csv dataset)
    {
    	//for every element in D
    		//C1 = currentElement. Make each point a cluster.
    		//C1 := all elements in C1 
    		//i = 1
    		//While the length of Ci > 1  (now filling diagonal matrix)
    			//for all clusters j
    				//for all clusters k greater than j+1
    					//Compute distance measure for Cij and Cik to this clsuter.
    			//Select the "min" distance for these cluster, and then merge them into one.
    			//Merging: for j =1 to size of Ci, do
    				//
    	Dendogram output = new Dendogram();
    	ArrayList<Cluster> clusterList = 
    		Cluster.makeInitClusters(dataset.datas, dataset.restrictions); 
    		//making initial clusters
    	ArrayList<Dendogram> dendoList = new ArrayList<Dendogram>();
    	//int dimensions = getDimensions(dataset.restrictions); //graph will be n-dimensional
    	
    	Dendogram curLowest = null;
    	int ikeep = -1;
    	int jkeep = -1;
    	//getting distances between all clusters
    	for(int i = 0; i < clusterList.size();i++)
    	{
    		for(int j = i+1; j < clusterList.size();j++) //TODO check i+1
    		{
    			ArrayList<Double> center1 = clusterList.get(i).clusterCenter();
    			ArrayList<Double> center2 = clusterList.get(j).clusterCenter();
    			
    			double dist = Cluster.pointDistance(center1, center2, dataset.restrictions);
    			
    			//only keep minimum distance between clusters as the one to create;
    			if(curLowest == null || dist < curLowest.distance)
    			{
    				curLowest = new Dendogram(clusterList.get(i), 
							clusterList.get(j), dist);
    				ikeep = i;
    				jkeep = j;
    			}

    		}
    	} //minimum cluster distance comes out of loop
    	
    	
    	
    	
    	return output;
    	
    	
    }
    
    //Distance Matrix
    
    //Distance measure for clusters. 
    public double distMeasure()
    {
    	double output = 0.0;
    	return output;
    }
}
