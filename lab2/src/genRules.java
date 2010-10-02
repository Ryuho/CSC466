import java.util.ArrayList;


public class genRules
{
	/* determines association rules
	 * Using number -1 to indicate where an arrow is */
	public static ArrayList<AssociationRule> genRulesMainLoop(ArrayList<Vector> itemsets, double minConf)
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
	
	public static ArrayList<AssociationRule> apGenRules(double minConf, Vector rule, int m)
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
