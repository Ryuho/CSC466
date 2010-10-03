import java.util.ArrayList;
import java.util.Collections;


public class genRules
{
	/* determines association rules
	 * Using number -1 to indicate where an arrow is */
	public static ArrayList<AssociationRule> genRulesMainLoop(ArrayList<Vector> itemsets, 
			ArrayList<Item> items, double minConf)
	{
		// for each f e F s.t. |f| = k >= 2 (for all itemsets greater than size 1).
			//H1 = 0 //initialize
			//for s e f //for all elements in the vector
				//if support(f-{s} -> {s}) >= minConf
					//H1 = H1 (union) {f-{s}->{s}};
			//apGenRules (f, H1)
		ArrayList<AssociationRule> possRules = new ArrayList<AssociationRule>();
		for(int i = 0; i< itemsets.size(); i++)
		{
			Vector curr = itemsets.get(i);
			if(curr.getSize() > 1)
			{
				//Vector w/o currElement ---> currElement
				for(int j = 0; j < curr.getSize(); j++)
				{
					//possRules.add();
				}
			}
		}
		return possRules;
	}
	
	private static ArrayList<AssociationRule> joinItems(ArrayList<Item> a, ArrayList<Item> b){
        ArrayList<Item> answer = new ArrayList<Item>();
        
        //TODO. modify such that this method makes new AssociationRules out of the given one.
        //for each item in AL a
        for(int aIdx = 0; aIdx < a.size(); aIdx++){
            //for each item in AL b
            for(int bIdx = 0; bIdx < b.size(); bIdx++){
                //check to see if they are the same item
                if(a.get(aIdx).itemID == b.get(bIdx).itemID){
                    //remve the item from AL b
                    b.remove(bIdx);
                }
            }
        }

        for(int aIdx = 0; aIdx < a.size(); aIdx++){
            answer.add(a.get(aIdx));
        }

        for(int bIdx = 0; bIdx < b.size(); bIdx++){
            answer.add(b.get(bIdx));
        }
        Collections.sort(answer);
        return answer;
    }
	
	private ArrayList<AssociationRule> canidateGen(AssociationRule itemVec, int itemSize)
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
	
	public static ArrayList<AssociationRule> apGenRules(double minConf, AssociationRule rule, int m)
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
	}
}
