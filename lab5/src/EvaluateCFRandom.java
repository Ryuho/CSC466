//package src;

import java.util.ArrayList;

public class EvaluateCFRandom
{
	int metho;
	int siz;
	Csv matrix;

	/**
	 * @param args
	 */
	public static void main(String[] args)
	{
		
		// EvaluateCFRandom <Method> <Size>
		
		EvaluateCFRandom ev = new EvaluateCFRandom();
		if(args[1] == null || args[0] == null)
		{
			System.out.println("1. Mean Utility\n" +
					"2. Adjusted Weighted Sum\n" +
					"3. Pearson Correlation");
			System.exit(0);
		}
		else
		{
			ev.metho = Integer.parseInt(args[0]);
			ev.siz = Integer.parseInt(args[1]);
		}
		
	}
	
	public void Evaluate(int method, int size)
	{
		//randomly generate size test cases. find elements in matrix not 99.
		ArrayList<Integer> uInd = new ArrayList<Integer>();  //index of users
		ArrayList<Integer> iInd = new ArrayList<Integer>();  //index of items
		
		//for(int u = 0; u < )
		//Make that rating 99, and use method to predict rating.
		//compare predicted rating with actual rating.
		
		//output results
		outputResults();
		//compute MAE
		getMAE();
	}
	
	public void outputResults()
	{
		
	}
	
	public void getMAE()
	{
		
	}

}
