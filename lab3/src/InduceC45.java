import java.util.ArrayList;
import org.w3c.dom.*;



public class InduceC45{
    public static void main(String [ ] args){
        //java InduceC45 <domainFile.xml> <TrainingSetFile.csv> [<restrictionsFile>]
    	
    	//get csv data
    	csvInfo csvAL = fileParser.parseCSV("data/tree02-20-numbers.csv");
    	System.out.println(csvAL);  
    	
    	//selectSplittingAttributeIG(int catIndex, ArrayList<Integer> attributes, ArrayList<Data> dataSet, double threshold){
    	int test = selectSplittingAttributeIG(2, csvAL.attributes, csvAL.dataSets, 0.1);
    	
    	System.out.println(test);
    }
    
    public static DecisionTreeNode C45(DecisionTreeNode tree, ArrayList<Data> dataSet, 
            ArrayList<Integer> attributes, double threshold, int catNum){
    	
    	//if there is only one category left
    	if(oneCategoryLeft(dataSet)){
    		System.out.println("Only one category left!");
    		//make a node and label it as the category, add it to the tree, then return the tree
    		//public DecisionTreeNode(int id, int node, int edge, int decision){
    		return new DecisionTreeNode(-1, -1, -1, dataSet.get(0).category);
    	}
    	//else if there are no more attributes, find the most common category and make a node,
    	//name it the most common cat, add it to the tree, reutrn the tree
    	else if(attributes.size() == 0){
    		System.out.println("No more attribute to split on!");
    		int commCat = commonCategory(catNum, dataSet);
    		return new DecisionTreeNode(-1, -1, -1, commCat);
    	}
    	else{
    		//find the splitting attribute
    		int splitAtt = selectSplittingAttributeIG(catNum,attributes, dataSet, threshold);
    		if(splitAtt == -1){
    			System.out.println("Couldn't find a good splitting attribute!");
    	    	//else if there is no good attribute to split on, find the most common category 
    			//and make a node, name it the most common cat, add it to the tree, reutrn the tree
        		int commCat = commonCategory(catNum, dataSet);
        		return new DecisionTreeNode(-1, -1, -1, commCat);
    		}
    		//actual tree construction
    		else{
    			System.out.println("Created a split node!");
    			DecisionTreeNode tempNode = new DecisionTreeNode();
                //label the node splitting attribute
    			tempNode.node = splitAtt;
    			ArrayList<Data> tempAL;
    			//for each dataset split by splitAtt, recurse C45 and append each of the result
    			for(int catLoop = 0; catLoop < attributes.get(splitAtt); catLoop++){
    				tempAL = new ArrayList<Data>();
    				
    				// if the attribute matches with the current loop, add it to the list
    				for(int dataLoop = 0; dataLoop < dataSet.size(); dataLoop++){
        				if(dataSet.get(dataLoop).dataSets.get(splitAtt) == catLoop){
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
    
    //given number of category, list of attributes, list of data, return the enthropy
    public static double enthropy(int catNum, ArrayList<Integer> attributes,  ArrayList<Data> dataSet){
    	double answer = 0.0;
    	int dataNum = dataSet.size();
    	double p [] = new double[catNum+1];
    	//System.out.println("catNum="+catNum);
    	for(int catCount = 1; catCount <= catNum; catCount++){
    		for(int dataCount = 0; dataCount < dataSet.size(); dataCount++){
    			//System.out.println("Compareing "+dataSet.get(dataCount).category+" and "+ catCount);
    			if(dataSet.get(dataCount).category == catCount){
        			p[catCount]++;
        		}
    		}
    		p[catCount] /= (double)dataNum;
    		//System.out.println(p[catCount]);
    		answer -= (p[catCount])*(Math.log((p[catCount]))/Math.log(2));
    	}
    	return answer;
    }
    
    //given index of attribute it's calculating for, index of category, list of attributes, return the enthropyi
    public static double enthropyi(int currAtt, int catNum, ArrayList<Integer> attributes,  ArrayList<Data> dataSet){
    	double answer = 0.0;
    	System.out.println("currAtt="+currAtt+"==========================");
    	ArrayList<ArrayList<Integer>> catChoice = new ArrayList<ArrayList<Integer>>();
    	//for each attribute choice
    	for(int attIdx = 0; attIdx < attributes.get(currAtt); attIdx++){
    		catChoice.add(new ArrayList<Integer>());
    		//for each dataset
    		for(int i = 0; i < dataSet.size(); i++){
    			//if this current dataset chose the same category as the current loop
    			if(dataSet.get(i).dataSets.get(currAtt) == attIdx+1){
    				//add the choice for current attribute
    				catChoice.get(attIdx).add(dataSet.get(i).category);
    			}
    		}
    	}
    	System.out.println(catChoice);
    	
    	
    	System.out.println("attributes.get(currAtt)="+attributes.get(currAtt));
    	double sum [] = new double[attributes.get(currAtt)];
    	//for each attribute AL
    	for(int ALIdx = 0; ALIdx < attributes.get(currAtt); ALIdx++){
    		//for each kind of category
    		double p [] = new double[catNum+1];
    		for(int catIdx = 0; catIdx < catNum; catIdx++){
    			//for each item in the array
    			for(int i = 0; i < catChoice.get(ALIdx).size(); i++){
        			if(catChoice.get(ALIdx).get(i) == catIdx+1){
        				p[catIdx]++;
        			}
    			}
    		}
    		for(int temp = 0; temp < catNum; temp++){
    			System.out.print(p[temp]+",");
    		}
    		System.out.println();

    		double total = catChoice.get(ALIdx).size();
    		for(int calcAttEnthro = 0; calcAttEnthro < catNum-1; calcAttEnthro++){
	    		for(int i = 0; i < catNum; i++){
	    			sum[calcAttEnthro] -= (p[i]/total)*(Math.log((p[i]/total))/Math.log(2.0));
	    			System.out.println(""+-(p[i]/total)*(Math.log((p[i]/total))/Math.log(2.0)));
	    		}
    		}
    		
    	}
    	
    	return answer;
    }
    
    //index of category in attribute, list of attributes, dataset and a threashold, return the splitting attribute index
    public static int selectSplittingAttributeIG(int catNum, ArrayList<Integer> attributes, ArrayList<Data> dataSet, double threshold){
    	double p0 = enthropy(catNum,attributes, dataSet);
    	double p [] = new double[attributes.size()];
    	//for each attribute, calculate the enthropyi
    	for(int i = 0; i < attributes.size(); i++){
    		p[i] = enthropyi(i, catNum, attributes, dataSet);
    	}
    	return -1;
    }
    
    
    //given the category string and dataset, returns true if there are only one category left, false otherwise
    public static boolean oneCategoryLeft(ArrayList<Data> dataSet){
    	boolean oneCatLeft = true;
    	int catChoice = dataSet.get(0).category;
    	for(int i = 0; i < dataSet.size(); i++){
    		if(catChoice != dataSet.get(i).category){
    			oneCatLeft = false;
    		}
    	}
    	return oneCatLeft;
    }
    
    //given the category index, category choice numbers and dataset, returns the most common category decision
    public static int commonCategory(int catNum, ArrayList<Data> dataSet){
    	int tally [] = new int[catNum];
    	for(int i = 0; i < dataSet.size(); i++){
    		tally[dataSet.get(i).category]++;
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

