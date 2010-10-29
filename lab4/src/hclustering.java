package src;

import java.util.ArrayList;


public class hclustering {
    public static void main(String[] args) 
    {  
    	//hclustering <Filename.csv> <threshold>
    	double threshold = 3;
    	String filename = "data/mammal_milk.csv";
    	
    	if(args.length == 2){
            filename = args[0];
            threshold = Double.valueOf(args[1]);
        }
        Csv data = new Csv(filename);
        
        Dendogram wholeTree = Agglo(threshold, data);
    	wholeTree.toXML(false, "lala.xml");

    }
    
/*    private int getDimensions(ArrayList<Integer> restrictions)
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
    }*/
    
   
    
    //Main algorithm of the Agglomerative heriarchical clustering algorithm
    // Takes dataset D as input
  //for every element in D
	//C1 = currentElement. Make each point a cluster.
	//C1 := all elements in C1 
	//i = 1
	//While the length of Ci > 1  (now filling diagonal matrix)
		//for all clusters j
			//for all clusters k greater than j+1
				//Compute distance measure for Cij and Cik to this clsuter.
		//Select the "min" distance for these cluster, and then merge them into one.
		//Merge
    public static Dendogram Agglo(double threshold, Csv dataset)
    {
    	Dendogram output = null;
    	//ArrayList<Cluster> clusterList = 
    	//	Cluster.makeInitClusters(dataset.datas, dataset.restrictions); 
    		//making initial clusters
    	ArrayList<Dendogram> dendoList = Dendogram.pointsToDendo(
    			Cluster.makeInitClusters(dataset.datas, dataset.restrictions));
    	//int dimensions = getDimensions(dataset.restrictions); //graph will be n-dimensional
    	Dendogram curLowest;
    	Cluster curr;
    	int ikeep;
    	int jkeep;
    	double dist;
    	int county = 0;
    	
    	
    	
    	while(dendoList.size() > 1) //make tree up to one whole cluster
    	{
    		Dendogram left = null;
    		Dendogram right = null;
    		ikeep = -1;
    		jkeep = -1;
    		curLowest = null;
    		dist = 0.0;
    		Cluster iC = new Cluster();
    		Cluster jC = new Cluster();
    		
    		for(int i = 0; i < dendoList.size();i++) //computing distances
	    	{
	    		for(int j = i+1; j < dendoList.size();j++) 
	    		{
	    			iC = dendoList.get(i).cluster;
	    			jC = dendoList.get(j).cluster;
	    			ArrayList<Double> center1 = iC.clusterCenter();
	    			ArrayList<Double> center2 = jC.clusterCenter();
	    			
	    			dist = Cluster.pointDistance(center1, center2, dataset.restrictions);
	    			
	    			//only keep minimum distance between clusters as the one to create;
	    			if(curLowest == null || dist < curLowest.distance)
	    			{
	    				ikeep = i;
	    				jkeep = j;
	    				left = dendoList.get(i);
	    				right = dendoList.get(j);
	    				curLowest = new Dendogram();
	    				curLowest.distance = dist;
	    				curLowest.cluster = iC.combineCluster(jC);
	    			}
	    		}
	    	} //minimum cluster with min distance comes out of loop
	    	
    		//System.out.println(left.cluster.data.size()+"mmmmmmmmmm"+ right.cluster.data.size());
	    	curLowest.den1 = left;
	    	curLowest.den2 = right;
	    	dendoList.remove(left);
	    	dendoList.remove(right);
	    	
	    	//Merging selected mindistance clusters
	    	dendoList.add(curLowest); //adding it to the list
	    	output = curLowest;  //if loop terminates. the root is at here.
	    	//System.out.println(dendoList.size());
    	}
    	
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
