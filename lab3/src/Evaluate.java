import java.util.ArrayList;


/*Performs cross-validation analysis */
public class Evaluate{
	/*Return the OVERALL confusion matrix, OVERALL recal, precision, pf and f-measure
	 * Overall and average accuracy (two-way), and error rate */
    public static void main(String [ ] args){
        //training file, optional restrictions file, integer n for cross validation 
    	
    	/*Cross validation algorithm:
    	 * select n (given)
    	 * using random sampling, split D into n slices of equal size
    	 * perform n classification procedures (using Di as the holdout set while all other
    	 * 		elements are the training set.  */
    	
    	/*Construct confusion matrix */
    }
    
    public ArrayList<ArrayList<csvInfo>> holdoutGen(csvInfo initSet, int numSlices)
    {
    	ArrayList<ArrayList<csvInfo>> result = new ArrayList<ArrayList<csvInfo>>();
    	int sliceSize = initSet.dataSets.size() / numSlices; 
    	
    	
    	/*Create slices of size sliceSize, but use random sampling to place slices in */
    	
    	return result;
    }
    
    /*return type would contain a set of numbers to analyze. (pos/negs) */
    public void classification(ArrayList<ArrayList<csvInfo>> data)
    {
    	//For each slice
    		//Select Di as holdout set
    		//Add all others into a single csvInfo training set and push it into InduceC45.
    		//read tree back in.
    		//run holdout set through parsed tree, 
    		//and record results for averaging by comparing result with projected 
    			//(ture pos, false pos, false neg, true neg)
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