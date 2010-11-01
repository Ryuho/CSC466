package src;

import java.util.ArrayList;


public class hclustering {
    public static void main(String[] args) 
    {  
    	//hclustering <Filename.csv> <threshold>
    	double threshold = -1;
    	String filename = "data/mammal_milk.csv";
    	
    	if(args.length >= 1){
            filename = args[0];
        }
    	if(args.length == 2)
    	{
    		threshold = Double.valueOf(args[1]);
    	}
        Csv data = new Csv(filename);
        
        Dendogram wholeTree = Agglo(data);
    	wholeTree.toXML(false, "Dendogram.xml");
    	if(threshold >= 1)
    	{
    		//Cluster identification step
    		ArrayList<Cluster> tClusters;
    		tClusters = getClusters(threshold, wholeTree);
    		System.out.println(printClusters(tClusters));
    		
    		//Evaluation step
    		try
    		{
    			Evaluate.domain = data.toC45Doc(tClusters);
    		}
    		catch (Exception e)
    		{
    			System.err.println("ooo bad");
    			e.printStackTrace();
    			System.exit(-1);
    		}
    		csvInfo inf = toInfo(tClusters, Csv.strings);
    		relearnCluster(inf, 5, .3);
    	}

    }
    

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
    public static Dendogram Agglo(Csv dataset)
    {
    	Dendogram output = null;
    	
    	ArrayList<Dendogram> dendoList = Dendogram.pointsToDendo(
    			Cluster.makeInitClusters(dataset.datas, dataset.restrictions));
    	Dendogram curLowest;
    	double dist = 0.0;
    	
    	while(dendoList.size() > 1) //make tree up to one whole cluster
    	{
    		Dendogram left = null;
    		Dendogram right = null;
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
    
    
    
    public static ArrayList<Cluster> getClusters(double threshold, Dendogram gram)
    {
    	ArrayList<Cluster> list = new ArrayList<Cluster>();
    	//base case: if distance below threshold, return empty;
    	if(gram.distance <= threshold)
    	{
    		list.add(gram.cluster);
    		return list;
    	}
    	if(gram.den1 == null && gram.den2 == null) //leaf case
    	{
    		list.add(gram.cluster);
    	}
    	else //non-leaf, non-terminus case
    	{
    		if(gram.den1!= null)
    		{
    			ArrayList<Cluster> res1 = getClusters(threshold, gram.den1);
    			if(res1.size() == 0)
    			{
    				list.add(gram.cluster);
    			}
    			else
    			{
    				list.addAll(res1);
    			}
    		}
    		if(gram.den2!= null)
    		{
    			ArrayList<Cluster> res2 = getClusters(threshold, gram.den2);
    			if(res2.size() == 0 && !list.contains(gram.cluster))
    			{
    				list.add(gram.cluster);
    			}
    			else
    			{
    				list.addAll(res2);
    			}
    		}
    	}
	    return list;
    }
    

    public static String printClusters(ArrayList<Cluster> clusters)
    {
    	String output = "";
    	for(int i = 0; i <  clusters.size();i++)
    	{
    		output += "Cluster "+ (i+1) +": " + clusters.get(i).toString() + "\n";
    	}
    	return output;
    }
    
    //need to convert back to csvInfo to use with lab3
    public static csvInfo toInfo(ArrayList<Cluster> tClusters, ArrayList<String> names)
    {
    	csvInfo output = new csvInfo();;
    	output.idName = "Clusters";
    	output.dataSets = new ArrayList<Data>();
    	output.categoryNumber = tClusters.size();
    	int k = 0;
    	//add a "class" at the end of all data points that indicates which cluster it was added to
    	for(int i = 0; i < tClusters.size(); i++)
    	{
    		for(int j = 0; j < tClusters.get(i).data.size(); j++)
    		{
    			Data dat = new Data(tClusters.get(i).data.get(j), k++, i);
    			output.dataSets.add(dat);
    		}
    	}
    	for(int i = 0; i < Csv.datas.get(0).size(); i++)
    	{
    		output.stringNames.add(String.valueOf(i));
    	}
    	
    	//Now for attributes.
    	ArrayList<ArrayList<Double>> shark = Csv.transposeData();
    	if(shark.get(0).size() == 1)
    		output.attributes.add(-1); //first column is IDentifier
    	else
    		output.attributes.add(shark.get(0).size());
    	for(int i = 1; i < shark.size(); i++)
    	{
    		output.attributes.add(shark.get(i).size());
    	}
    	System.out.println(output.attributes);
    	System.out.println(output.stringNames);
    	System.out.println(output.dataSets);
    	return output;
    }
    
    public static void relearnCluster(csvInfo info, int n, double threshold)
    {
    	ArrayList<csvInfo> dataslice;
    	dataslice = Evaluate.holdoutGen(info, -1);
    	Evaluate.n = n;
    	Evaluate.csvAL = info;
    	Evaluate.classification(dataslice, .10, "data/restrictions.csv~");
    }
}
