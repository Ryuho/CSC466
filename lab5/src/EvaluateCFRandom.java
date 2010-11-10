//package src;

import java.util.ArrayList;

public class EvaluateCFRandom
{
	int metho;
	int siz;
	Csv matrix;
	ArrayList<Item> items;

	/**
	 * @param args
	 */
	public static void main(String[] args)
	{
		
		// EvaluateCFRandom <Method> <Size>
		
		EvaluateCFRandom ev = new EvaluateCFRandom();
		if(args.length <=1)
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
			
			//create the matrix set
			ev.matrix = new Csv("data/jester-data-1.csv");
			ev.items = Item.genAllItems(ev.matrix);
			
			ev.Evaluate(ev.metho, ev.siz);
		}
		
	}
	
	public void Evaluate(int method, int size)
	{
		//randomly generate size test cases. find elements in matrix not 99.
		ArrayList<Integer> uInd = new ArrayList<Integer>();  //index of users
		ArrayList<Integer> iInd = new ArrayList<Integer>();  //index of items
		ArrayList<Float> fInd = new ArrayList<Float>(); 
		ArrayList<Float> mae = new ArrayList<Float>();
			//values to save so they can get overwritten
		Query funcs = new Query(matrix);
		
		for(int u = 0; u < size; u++)
		{
			int user = 0;
			int item = 0;
			do
			{
				item = (int) (Math.random()*100);
				user = (int) Math.floor(Math.random() * matrix.data.size());
			}while(matrix.data.get(user).at(item) == 99);
			
			uInd.add(user);
			iInd.add(item);
			fInd.add(matrix.data.get(user).at(item)); //rating saved. okay to overwrite.
		}
		System.out.println("Sample#\tuserID\titemID\tActual_Rating\tPredicted_Rating\tDelta_rating");
		
		for(int i = 0; i < size; i++)
		{
			float predicted = 0f;
			
			//Make that rating 99, and use method to predict rating.
			matrix.data.get(uInd.get(i)).ratings().set(iInd.get(i), 99f); //overwritten
			
			if(method == 1)
			{
				predicted = funcs.MeanUtility(uInd.get(i), iInd.get(i));
			}
			else if(method == 2)
			{
				predicted = funcs.AdjustedWeightedSum(uInd.get(i), iInd.get(i));
			}
			else if(method == 3)
			{
				//predicted = funcs.
			}
			else
			{
				System.out.println("1. Mean Utility\n" +
						"2. Adjusted Weighted Sum\n" +
						"3. Pearson Correlation");
				System.exit(0);
			}
			
			//compare predicted rating with actual rating.
			float diff = Math.abs(predicted - fInd.get(i)); //element of MAE
			mae.add(diff);
			
			//output results
			outputResults(i ,uInd.get(i), iInd.get(i), fInd.get(i), predicted, diff);
			
			if(diff >= 20.0){
			    System.err.println("illegal delta value!!");
			}
			
			//put back into matrix
			matrix.data.get(uInd.get(i)).ratings().set(iInd.get(i), fInd.get(i)); 
				//value restored
		}
		//compute Mean Absolute Error
		getMAE(mae);
	}
	
	public void outputResults(int i, int user, int item, float actual, float predicted, 
							float diff)
	{
		System.out.println((i+1) + "\t" + user + "\t" + item +
							 "\t" + actual + "\t\t" + predicted +
							 "\t\t" + diff);
	}
	
	public void getMAE(ArrayList<Float> mae)
	{
		float error = 0f;
		for(int i = 0; i < mae.size(); i++)
		{
			error += mae.get(i);
		}
		error /= mae.size();
		
		System.out.println("Mean Absolute Error: " + error);
	}

}
