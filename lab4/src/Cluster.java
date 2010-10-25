package src;

import java.util.ArrayList;

public class Cluster
{
	ArrayList<ArrayList<Double>> data;
	ArrayList<Integer> restrictions;
	
	Cluster()
	{
		data = new ArrayList<ArrayList<Double>>();
		restrictions = new ArrayList<Integer>();
	}
	
	Cluster(ArrayList<Double> singlepoint, ArrayList<Integer> rest)
	{
		data = new ArrayList<ArrayList<Double>>();
		restrictions = rest;
		data.add(singlepoint);
	}
	
	public static ArrayList<Double> intToDoub(ArrayList<Integer> lis)
	{
		ArrayList<Double> ret = new ArrayList<Double>();
		for(int i = 0; i < lis.size(); i++)
		{
			ret.add(lis.get(i)*1.0);
		}
		return ret;
	}
	
	public static ArrayList<Cluster> makeInitClusters(ArrayList<ArrayList<Integer>> datasets, 
			ArrayList<Integer> rest)
	{
		ArrayList<Cluster> set = new ArrayList<Cluster>();
		
		for(int i = 0; i < datasets.size(); i++)
		{
			ArrayList<Double> dat = intToDoub(datasets.get(i));
			set.add(new Cluster(dat, rest));
		}
		return set;
	}
	
	public void addOnePoint(ArrayList<Double> point)
	{
		data.add(point);
	}
	
	public void combineCluster(Cluster other)
	{
		data.addAll(other.data);
	}
	
	public static double pointDistance(ArrayList<Double> pt1, 
			ArrayList<Double> pt2, ArrayList<Integer> restriction)
	{
	    	double result = 0.0;
	    	for(int i = 0; i < restriction.size(); i++)
	    	{
	    		if(restriction.get(i) != 0)
	    		{
	    			result += Math.pow(pt1.get(i) - pt2.get(i), 2);
	    		}
	    	}
	    	result = Math.sqrt(result);
	    	return result;
	}
	
	public ArrayList<Double> pointAverage(ArrayList<Double> pt1, 
			ArrayList<Double> pt2)
	{
		ArrayList<Double> result = new ArrayList<Double>();
		for(int i = 0; i < pt1.size();i++)
		{
			//TODO wait, why is it Integer and not double??????/
			result.add((pt1.get(i) + pt2.get(i))/2.0);
		}
		return result;
	}
	
	public ArrayList<Double> clusterCenter()
	{
		ArrayList<Double> point = new ArrayList<Double>();
		if(data.size() == 1)
		{
			return data.get(0);
		}
		else if (data.size() <= 0)
		{
			System.err.println("No data in cluster");
			System.exit(-1);
		}
		else
		{
			point = data.get(0);
			//get average of all points in cluster
			for(int i = 1; i < data.size(); i++)
			{
				point = pointAverage(point, data.get(i));
			}
		}
		return point;
	}
}