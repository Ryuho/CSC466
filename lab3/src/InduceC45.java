import java.util.ArrayList;
import org.w3c.dom.*;



public class InduceC45{
    public static void main(String [ ] args){
        //java InduceC45 <domainFile.xml> <TrainingSetFile.csv> [<restrictionsFile>]
    	
    	//get csv data
    	csvInfo csvAL = fileParser.parseCSV("data/tree02-20-numbers.csv");
    	System.out.println(csvAL);  
    	
    	//selectSplittingAttributeIG(int catIndex, ArrayList<Integer> attributes, ArrayList<ArrayList<Integer>> dataSet, double threshold){
    	int test = selectSplittingAttributeIG(11, csvAL.domainSizes, csvAL.dataSets, 0.1);
    	
    	System.out.println(test);
    	/*
    	
    	//get domain data
        Document parsedDoc = fileParser.parseXMLDomain("data/domain.xml");
        DomainTreeNode domTreeNode = DocToTree(parsedDoc);

        DecisionTreeNode root = new DecisionTreeNode();
        
        int catIndex = whichIsCategory(csvAL.categoryAttribute, csvAL.stringNames);
        DecisionTreeNode result = C45(root,csvAL.dataSets, csvAL.domainSizes, 0.1, catIndex);
        
        System.out.println(result);
        */
    }
    
    public static DecisionTreeNode C45(DecisionTreeNode tree, ArrayList<ArrayList<Integer>> dataSet, 
            ArrayList<Integer> attributes, double threshold, int catIndex){
    	
    	//if there is only one category left
    	if(oneCategoryLeft(catIndex,dataSet)){
    		System.out.println("Only one category left!");
    		//make a node and label it as the category, add it to the tree, then return the tree
    		//public DecisionTreeNode(int id, int node, int edge, int decision){
    		return new DecisionTreeNode(-1, -1, -1, dataSet.get(0).get(catIndex));
    	}
    	//else if there are no more attributes, find the most common category and make a node,
    	//name it the most common cat, add it to the tree, reutrn the tree
    	else if(noMoreAttributes(catIndex, attributes)){
    		System.out.println("No more attribute to split on!");
    		int commCat = commonCategory(catIndex, attributes.get(catIndex), dataSet);
    		return new DecisionTreeNode(-1, -1, -1, commCat);
    	}
    	else{
    		//find the splitting attribute
    		int splitAtt = selectSplittingAttributeIG(catIndex,attributes, dataSet, threshold);
    		if(splitAtt == -1){
    			System.out.println("Couldn't find a good splitting attribute!");
    	    	//else if there is no good attribute to split on, find the most common category 
    			//and make a node, name it the most common cat, add it to the tree, reutrn the tree
        		int commCat = commonCategory(catIndex, attributes.get(catIndex), dataSet);
        		return new DecisionTreeNode(-1, -1, -1, commCat);
    		}
    		//actual tree construction
    		else{
    			System.out.println("Created a split node!");
    			DecisionTreeNode tempNode = new DecisionTreeNode();
                //label the node splitting attribute
    			tempNode.node = splitAtt;
    			ArrayList<ArrayList<Integer>> tempAL;
    			//for each dataset split by splitAtt, recurse C45 and append each of the result
    			for(int catLoop = 0; catLoop < attributes.get(splitAtt); catLoop++){
    				tempAL = new ArrayList<ArrayList<Integer>>();
    				
    				// if the attribute matches with the current loop, add it to the list
    				for(int dataLoop = 0; dataLoop < dataSet.size(); dataLoop++){
        				if(dataSet.get(dataLoop).get(splitAtt) == catLoop){
        					tempAL.add(dataSet.get(dataLoop));
        				}    					
    				}
    				
    			}
                //for for each different groups the splitting attributes create
        			//recurse with C45 and append the result
    		}
    	}
        return tree;
    }
    
    //FIXME this doesn't work currently
    //so we are modeling domain.xml for now
    public static DomainTreeNode DocToTree(Document doc){
    	DomainTreeNode answer = new DomainTreeNode();
    	
    	return answer;
    }
    
    //given index of category, list of attributes, return the enthropy
    public static double enthropy(int catIndex, ArrayList<Integer> attributes,  ArrayList<ArrayList<Integer>> dataSet){
    	double answer = 0.0;
    	int catNum = attributes.get(catIndex);
    	int dataNum = dataSet.size();
    	double p [] = new double[catNum];
    	for(int catCount = 1; catCount <= catNum; catCount++){
    		for(int dataCount = 0; dataCount < dataSet.size(); dataCount++){
    			//System.out.println("Compareing "+dataSet.get(dataCount).get(catIndex)+" and "+ catCount);
    			if(dataSet.get(dataCount).get(catIndex) == catCount){
        			p[catCount-1]++;
        		}
    		}
    		p[catCount-1] /= (double)dataNum;
    		//System.out.println(p[catCount-1]);
    		answer -= (p[catCount-1])*(Math.log((p[catCount-1]))/Math.log(2));
    	}
    	return answer;
    }
    
    //index of category in attribute, list of attributes, dataset and a threashold, return the splitting attribute index
    public static int selectSplittingAttributeIG(int catIndex, ArrayList<Integer> attributes, ArrayList<ArrayList<Integer>> dataSet, double threshold){
    	double p0 = enthropy(catIndex,attributes, dataSet);
    	System.out.println("enthropy="+p0);
    	double p [] = new double[attributes.size()];
    	for(int i = 0; i < attributes.size()-1; i++){
    		
    		
    	}
    	return -1;
    }
    
    public static int selectSplittingAttributeIFR(int catNum, ArrayList<Integer> attributes, ArrayList<ArrayList<Integer>> dataSet, double threshold){
    	return -1;
    }
    
    //given the category string and list of group names, returns the index of the category (class)
    public static int whichIsCategory(String cat, ArrayList<String> nameList){
    	int answer = -1;
    	for(int index = 0; index < nameList.size(); index++){
    		if(cat.compareTo(nameList.get(index)) == 0){
    			answer = index;
    		}
    	}
    	return answer;
    }
    
    //given the category string and dataset, returns true if there are only one category left, false otherwise
    public static boolean oneCategoryLeft(int catIndex, ArrayList<ArrayList<Integer>> dataSet){
    	boolean oneCatLeft = true;
    	int catChoice = dataSet.get(0).get(catIndex);
    	for(int i = 0; i < dataSet.size(); i++){
    		if(catChoice != dataSet.get(i).get(catIndex)){
    			oneCatLeft = false;
    		}
    	}
    	return oneCatLeft;
    }
    
    //given the category index and list of attributes, returns true if there are no more unused attributes, false otherwise
    public static boolean noMoreAttributes(int catIndex, ArrayList<Integer> attributes){
    	boolean noMoreAttribute = true;
    	for(int i = 0; i < attributes.size(); i++){
    		if(attributes.get(i) != catIndex && attributes.get(i) != -1){
    			noMoreAttribute = false;
    		}
    	}
    	return noMoreAttribute;
    }
    
    //given the category index, category choice numbers and dataset, returns the most common category decision
    public static int commonCategory(int catIndex, int catNum, ArrayList<ArrayList<Integer>> dataSet){
    	
    	int tally [] = new int[catNum];
    	for(int i = 0; i < dataSet.size(); i++){
    		tally[dataSet.get(i).get(catIndex)-1]++;
    	}
    	int answer = -1;
    	
    	for(int i = 0; i < catNum; i++){
    		if(tally[i] > answer){
    			answer = tally[i];
    		}
    	}
    	
    	return answer;
    }
    
}

