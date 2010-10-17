package src;


import src.DecisionTreeNode;
import src.InduceC45;

import java.util.ArrayList;





/*Performs cross-validation analysis */
public class Evaluate{
	/*Return the OVERALL confusion matrix, OVERALL recall, precision, pf and f-measure
	 * Overall and average accuracy (two-way), and error rate */
	
	csvInfo inSet;
	
    public static void main(String [ ] args){
        //training file, optional restrictions file, integer n for cross validation 
    	
    	/*Cross validation algorithm:
    	 * select n (given)
    	 * using random sampling, split D into n slices of equal size
    	 * perform n classification procedures (using Di as the holdout set while all other
    	 * 		elements are the training set.  */
    	
    	/*Construct confusion matrix */
    }
    
    /*Generates a set of randomly sampled slices
     * initSet: the initial csvInfo that was read out of the tree
     * numSlices: the number of slices to create from this dataset. */
    public ArrayList<csvInfo> holdoutGen(csvInfo initSet, int numSlices)
    {
    	ArrayList<csvInfo> result = new ArrayList<csvInfo>();
    	int sliceSize = initSet.dataSets.size() / numSlices; 
    	int rand = 0;
    	
    	for(int i = 0; i < sliceSize; i++)
    	{
    		result.add(new csvInfo());  //initializing to slice size;
    		result.get(i).stringNames = initSet.stringNames;
    		result.get(i).attributes = initSet.attributes;
    	}
    	
    	/*Create slices of size sliceSize, but use random sampling to place slices in */
    	for(int i = 0; i < initSet.dataSets.size() ; i++)
    	{
    		rand = ((int) (Math.random() * 100)) % numSlices;
    		
    		//check if slice is full before trying to add.
    		while(result.get(rand).getTupleSize() >= sliceSize)
    		{
    			rand = (rand +1) % sliceSize;
    		}
    		
    		//add to slice
    		result.get(rand).dataSets.add(initSet.dataSets.get(i));
    		
    	}
    	inSet = initSet;
    	return result;
    }
    
    public csvInfo getTrainingSet(csvInfo holdout)
    {
    	csvInfo train = inSet;
    	train.dataSets.removeAll(holdout.dataSets);
    	return train;
    }
    
    /*return type would contain a set of numbers to analyze. (pos/negs) */
    public void classification(ArrayList<csvInfo> data, double threshold)
    {
    	csvInfo curSlice = null;
    	csvInfo train = null;
    	DecisionTreeNode runRes = null;
    	//For each slice
    	for(int slce = 0; slce < data.size(); slce++)
    	{
    		//Select Di as holdout set
    		curSlice = data.get(slce);
    		
    		//Add all others into a single csvInfo training set and push it into InduceC45.
    		train = getTrainingSet(curSlice);
    		runRes = InduceC45.C45(train.dataSets, train.attributes, 
    								threshold, train.categoryNumber);
    		//read tree back in.
    		//run holdout set through parsed tree, 
    		//and record results for averaging by comparing result with projected 
    			//(ture pos, false pos, false neg, true neg)
    	}
    }
    
    /*return type and paramaters pending */
    public void generateConfusion()
    {
    	//get true positives, false positives, and likewise for negatives
    	
    	//calculate precision and print
    	
    	//calculate recall
    	
    	//calculate pf
    	
    	//calculate f-measure
    	
    	//overall and average accuracy
    	
    	//error rate
    }
}