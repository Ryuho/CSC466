import java.util.ArrayList;
import java.util.Collections;


public class genRules
{
	/* determines association rules
	 * Using number -1 to indicate where an arrow is */
	public static ArrayList<AssociationRule> genRulesMainLoop(ArrayList<Vector> itemsets, 
			ArrayList<Vector> vec, double minConf)
	{
		double support = 0.0;
		
		
		// for each f e F s.t. |f| = k >= 2 (for all itemsets greater than size 1).
		ArrayList<AssociationRule> possRules = new ArrayList<AssociationRule>();
		for(int i = 0; i< itemsets.size(); i++) //for each itemset.
		{
			ArrayList<AssociationRule> newRule = new ArrayList<AssociationRule>();
			Vector curr = itemsets.get(i);
			
			if(curr.getSize() > 1) 
			{
				//Vector w/o currElement ---> currElement
				//H1 = 0 //initialize
				//support = fk.count/n. Counting rules
				//for s e f //for all elements in the vector
				for(int j = 0; j < curr.getSize(); j++)  //for each element in the current itemset
				{
					AssociationRule tmpRule = new AssociationRule();
					//if support(f-{s} -> {s}) >= minConf
					//H1 = H1 (union) {f-{s}->{s}};
					
					//take j out of antecedent and put it in the consequent.
					//And then calculate its support
					
					for(int k = 0; k < curr.getSize(); k++) 
					{
						if(k != j)
						{
							tmpRule.addLeft(curr.getElement(k));
						}
						else
						{
							tmpRule.addRight(curr.getElement(j));
						}
					}
					newRule.add(tmpRule);
				}
				//support now. A receipt has to contain all items in a receipt for it to count.
				int siz = curr.getSize();
				int suppCount = 0;  //items in receipt that matches
				ArrayList<Integer> leftSupCount = new ArrayList<Integer>(newRule.size()); 
				int confidenceCount = 0;
				/*for(int j = 0; j < vec.size(); j++)
				{
					for(int k = 0; k < siz; k++)
					{
						for(int l =0; l < vec.get(j).getSize(); l++)
						{
							if(vec.get(j).getElement(l) == curr.getElement(k))
							{
								suppCount++;
							}
							
						//TODO. modify loop to accommodate multiple rules
							if(k == 0 && vec.get(j).getElement(l) == newRule.getRight(0))
							{
								confidenceCount++;
							}
							if(newRule.left.contains(vec.get(j).getElement(l)))
							{
								leftSupCount++;
							}
						}
					}
					if(suppCount == siz)
					{
						support++;
					}
				
				
					suppCount = 0;
				}*///end support loop
				
				//universal support for ALL elements in current itemset. (numerators)
				for(int j = 0; j < vec.size(); j++)
				{
					for(int l =0; l < vec.get(j).getSize(); l++)
					{
						if(vec.get(j).getElement(l) == curr.getElement(0))
						{
							suppCount++;
						}
					}
					if(suppCount == siz)
					{
						support++;
					}
					suppCount = 0;
				}///end universal support loop
				
				//unique support. (denominators)
				//for each new Rule
				//for every receipt, and for every element in that receipt verses
				int minsupport = 0;
				for(int k = 0; k < newRule.size(); k++) //all new rules
				{
					leftSupCount.add(k, 0); //init this element for modification
					for(int j = 0; j < vec.size(); j++) //all itemsets
					{
						for(int l =0; l < vec.get(j).getSize(); l++) // all elements in itemsets
						{
							for(int m = 0; m < newRule.get(k).left.size(); m++) //elements in left rule
							{
								if(vec.get(j).getElement(l) == newRule.get(k).left.get(m))
								{
									leftSupCount.set(k, leftSupCount.get(k)+1);
								}
							}
						}
						if(leftSupCount.get(k) == newRule.get(k).left.size())
						{
							minsupport++;
						}
					}
					
					support /= minsupport;
					
					//check confidence
					if(support <= minConf)
					{
						newRule.remove(k);
					}
					
					//loop resets
					minsupport = 0;
				}//end individual support
				
				//ADD new rule to set
				possRules = joinItems(possRules, newRule);
				
				//loop resets
				//leftSupCount = 0;
				support = 0;
			} 
		}
		return possRules;
	}
	
	
	
	private static ArrayList<AssociationRule> joinItems(ArrayList<AssociationRule> possRules, 
			ArrayList<AssociationRule> newRule)
	{
        for(int i = 0; i < possRules.size(); i++)
        {
        	for(int j=0; j<newRule.size(); j++)
        	{
        		if(newRule.get(j).left.containsAll(possRules.get(i).left) &&
        				newRule.get(j).right.containsAll(possRules.get(i).right))
        		{
        			newRule.remove(j);
        		}
        	}
        }
        possRules.addAll(newRule);
        return possRules;
    }
	
/*	private ArrayList<AssociationRule> canidateGen(AssociationRule itemVec, int itemSize)
	{
		ArrayList<AssociationRule> answer = new ArrayList<AssociationRule>();
        for(int i = 0; i < itemVec.left.size(); i++)
        {
            for(int j = i+1; j < itemVec.right.size(); j++)
            { 
            	//Make a new set of item combinations. 
                ArrayList<AssociationRule> joinedItems = 
                	joinItems(itemVec.left,itemVec.right);
                
                //add them to the new set, if applicable
                if(joinedItems.size() == itemSize + 1)
                {
                    if(!answer.contains(joinedItems))
                    {
                        answer.addAll(joinedItems);
                    }
                }
            }
        }
        //System.out.println("candidateGen="+answer);
        //System.out.println(answer.size());
		return answer;
	}
	*/
	
	/*public static ArrayList<AssociationRule> apGenRules(double minConf, AssociationRule rule, int m)
	{
		//if (k> m+ 1) AND H not null
			//H(m+1) = canidateGen(Hm, m);
			//for h e H (m+1)
				//confidence = count(f)/count(f-h);
				//if confidence >= minConf
					//output (f - h) -> h;  //found new rule
				//else
					//H(m+1) = H(m+1) - {h}
			//apGenRules(f, H(m+1), M+1)
		ArrayList<AssociationRule> H = new ArrayList<AssociationRule>( );
		ArrayList<AssociationRule> ruleList = new ArrayList<AssociationRule>();
		if((rule.getSize()-1 > m+1) && rule.getSize() != 0)
		{
			//run canidateGen here.

			//for each rule permutation, count the confidence of it.
			for(int i = 0; i < H.size(); i++)
			{
				double confidence = 0;
				//count the occurrences of f. Divide by the occurrences of f without the rule?
				if(confidence >= minConf)
				{
					ruleList.add(H.get(i));
				}
			}
			//apGenRules(minConf, );
		}
		return H;
	}*/
}
