package src;


import src.DecisionTreeNode;
import src.InduceC45;

import java.util.ArrayList;

import com.sun.org.apache.xerces.internal.dom.DocumentImpl;





/*Performs cross-validation analysis */
public class Evaluate{
	/*Return the OVERALL confusion matrix, OVERALL recall, precision, pf and f-measure
	 * Overall and average accuracy (two-way), and error rate */
	
	static csvInfo inSet;
	static DocumentImpl domain;
	static csvInfo csvAL;
	static int n;
	static ArrayList<Integer> restrictions;
	
    public static void main(String [ ] args){
        //training file, optional restrictions file, integer n for cross validation 
    	ArrayList<csvInfo> dataslice;
    	
    	//domain = (DocumentImpl) fileParser.parseXMLDomain(args[2]);
    	
    	//csvAL = fileParser.parseCSV(args[1]);
    	n = Integer.valueOf(args[0]);
    	String restFilename = "";
    	if(args.length > 3)
    	{
    		//restrictions = fileParser.parseRestricted();
    		restFilename = args[3];
    	}
    	
    	dataslice = holdoutGen(csvAL, n);
    	
    	classification(dataslice, .10, restFilename);
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
    public static ArrayList<csvInfo> holdoutGen(csvInfo initSet, int numSlices)
    {
    	ArrayList<csvInfo> result = new ArrayList<csvInfo>();
    	int sliceSize;
    	int rand = 0;
    	
    	
    	//checking for cross validation flag
    	if(numSlices != 0)
    	{
    		sliceSize = initSet.dataSets.size() / numSlices;
    	}
    	else  //One giant slice
    	{
    		sliceSize = initSet.dataSets.size();
    		numSlices = 0;
    		result.add(new csvInfo());
    		result.get(0).stringNames = initSet.stringNames;
			result.get(0).attributes = initSet.attributes;
    	}
    
    	if(numSlices < 0) //Leave one Out
    	{
    		numSlices = initSet.getTupleSize();
    		sliceSize = 1;
    	}
    	
    	//if(numSlices > 0)
    	//{
    		for(int i = 0; i < numSlices; i++)
    		{
    			result.add(new csvInfo());  //initializing to slice size;
    			result.get(i).stringNames = initSet.stringNames;
    			result.get(i).attributes = initSet.attributes;
    		}
    	
    		/*Create slices of size sliceSize, but use random sampling to place slices in */
    		for(int i = 0; i < initSet.dataSets.size() ; i++)
    		{
    			if(numSlices == 0) //n = 0 special case
				{
					rand = 0;
				}
    			else if(sliceSize > 1)
    			{
    				rand = ((int) (Math.random() * 100)) % numSlices;
    				//check if slice is full before trying to add.
    				while(rand >= numSlices || 
        					result.get(rand).getTupleSize() >= sliceSize)
        			{
        				rand = (rand +1) % numSlices;
        			}
    			}
    			else
    			{
    				rand = i;
    			}
    		
    			//add to slice
    			result.get(rand).dataSets.add(initSet.dataSets.get(i));
    		}
    	//}
    	inSet = initSet;
    	return result;
    }
    
    public static csvInfo getTrainingSet(csvInfo holdout)
    {
    	csvInfo train = new csvInfo();
    	if(n != 0)
    	{
    		
    		train.dataSets.addAll(csvAL.dataSets);
    		train.categoryIndex = csvAL.categoryIndex;
    		train.categoryNumber = csvAL.categoryNumber;
    		train.categoryName = csvAL.categoryName;
    		train.idName = csvAL.idName;
    		train.attributes.addAll(csvAL.attributes);
    		train.stringNames.addAll(csvAL.stringNames );
    		train.dataSets.removeAll(holdout.dataSets);
    	}
    	else
    	{
    		train = csvAL;
    	}
    	return train;
    }
    
    /*return type would contain a set of numbers to analyze. (pos/negs) */
    public static void classification(ArrayList<csvInfo> data, double threshold, 
    		String restrFilename)
    {
    	csvInfo curSlice = null;
    	csvInfo train = null;
    	DocumentImpl runRes = null;
    	ConfusionMatrix results = new ConfusionMatrix();
    	double avgAccu = 0;
    	
    	//For each slice
    	for(int slce = 0; slce < data.size(); slce++)
    	{
    		//Select Di as holdout set
    		curSlice = data.get(slce);
    		//System.out.println(curSlice.getTupleSize());
    		
    		//Add all others into a single csvInfo training set and push it into InduceC45.
    		train = getTrainingSet(curSlice);
    		//System.out.println(train.getTupleSize());
    		runRes = InduceC45.creatTree(domain, train, restrFilename);
    		
    		//run holdout set through parsed tree, 
    		//and record results for averaging by comparing result with projected 
    		results.combine(classifier.confuseTree(runRes, curSlice));
    		
    		/*computations for average accuracy */
    		
    		
    		avgAccu += results.trueNegatives + results.truePositives /
    					(results.falseNegatives + results.falsePositives + 
    					results.trueNegatives + results.truePositives);
    		
    	}
    	
    	avgAccu /= data.size();
    	generateConfusion(results, avgAccu);
    }
    
    /*return type and paramaters pending */
    public static void generateConfusion(ConfusionMatrix confuse, double avgAccu)
    {
    	double recall;
    	double precision;
    	double pf;
    	double fMeasure;
    	double overAccu;
    	
    	System.out.println("Resulting Confusion Matrix:\n" +
    			" True Positives: " + confuse.truePositives +
    			"\n True Negatives: "+ confuse.trueNegatives +
    			"\n False Positives: " + confuse.falsePositives + 
    			"\n False Negatives: " + confuse.falseNegatives + "\n");
    	
    	//calculate precision and print
    	precision = confuse.truePositives*1.0 / 
    				(confuse.truePositives + confuse.falsePositives);
    	System.out.println("Precision = " + precision*100);
    	
    	//calculate recall
    	if((confuse.truePositives + confuse.falseNegatives) != 0.0)
    	{
    		recall = confuse.truePositives*1.0 / 
					(confuse.truePositives + confuse.falseNegatives);
    	}
    	else{
    		recall = 0;}
    	System.out.println("Recall = " + recall*100);
    	
    	//calculate pf
    	if(confuse.falsePositives + confuse.trueNegatives != 0)
    	{
    		pf = confuse.falsePositives*1.0 / 
					(confuse.falsePositives + confuse.trueNegatives);
    	}
    	else{
    		pf = 0;}
    	System.out.println("PF = " + pf*100);
    	
    	//calculate f-measure
    	if(precision !=0 && recall != 0)
    	{
    		fMeasure = 2.0 / (1.0/precision + 1.0/recall);
    	}
    	else{
    		fMeasure = 0;}
    	System.out.println("F-Measure = " + fMeasure);
    	
    	//overall and average accuracy
    	overAccu = confuse.trueNegatives + confuse.truePositives / confuse.tuple;
    	System.out.println("Overall Accuracy = " + overAccu);
    	System.out.println("Average Accuracy = " + avgAccu);

    }
}