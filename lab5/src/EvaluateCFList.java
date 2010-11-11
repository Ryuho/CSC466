import java.util.ArrayList;


import java.io.*;

//package src;

public class EvaluateCFList {
	int metho;
	String filename;
	Csv matrix;
	ArrayList<Item> items;
	int size;

	/**
	 * @param args
	 */
	public static void main(String[] args)
	{
		
		// EvaluateCFRandom <Method> <Filename>
		
		EvaluateCFList ev = new EvaluateCFList();
		ArrayList<Integer> uInd = new ArrayList<Integer>(); //users
		ArrayList<Integer> iInd = new ArrayList<Integer>(); //items
		ev.size = 0;
		
		if(args.length <=1)
		{
			System.out.println("1. Mean Utility\n" +
					"2. Mean Utility with rank\n" +
					"3. Adjusted Weighted Sum\n" +
					"4. Adjusted Weighted Sum with rank\n" +
					"5. Adjusted Weighted Sum with transformed vote\n"
					);
			System.exit(0);
		}
		else
		{
			ev.metho = Integer.parseInt(args[0]);
			ev.filename = args[1];
			FileInputStream fstream = null;
			try {
				fstream = new FileInputStream(ev.filename);
			} catch (FileNotFoundException e) {
				System.err.println("File Not Found.");
				System.exit(1);
			}

			DataInputStream in = new DataInputStream(fstream);
			BufferedReader br = new BufferedReader(new InputStreamReader(in));
			String strLine = null;

			try {
				while ((strLine = br.readLine()) != null)
					{
						int user = Integer.parseInt(
								(strLine.substring(0, strLine.indexOf(","))).trim());
						int item = Integer.parseInt(
								(strLine.substring(strLine.indexOf(",")+1 )).trim());
						uInd.add(user);
						iInd.add(item);
						ev.size++;

					}
			} catch (IOException e) {
				e.printStackTrace();
				System.err.println("Exception while reading file.");
			}

			
			
			
			//create the matrix set
			ev.matrix = new Csv("jester-data-1.csv");
			ev.items = Item.genAllItems(ev.matrix);
			
			ev.Evaluate(ev.metho, uInd, iInd);
		}
		
	}
	
	public void Evaluate(int method, ArrayList<Integer> uInd, ArrayList<Integer> iInd)
	{
		//randomly generate size test cases. find elements in matrix not 99.
		ArrayList<Float> fInd = new ArrayList<Float>(); 
		ArrayList<Float> mae = new ArrayList<Float>();
			//values to save so they can get overwritten
		Query funcs = new Query(matrix);
		
		for(int u = 0; u < size; u++)
		{
			fInd.add(matrix.data.get(uInd.get(u)).at(iInd.get(u))); 
			//rating saved. okay to overwrite.
		}
		System.out.println("Sample#\tuserID\titemID\tActual_Rating\tPredicted_Rating\tDelta_rating");
		
		for(int i = 0; i < size; i++)
		{
			if(fInd.get(i) != 99) //checking validity
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
					predicted = funcs.MeanUtilityWRank(matrix.data.get(uInd.get(i)), iInd.get(i), 100);
				}
				else if(method == 3)
				{
					predicted = funcs.AdjustedWeightedSum(uInd.get(i), iInd.get(i));
				}
				else if(method == 4)
				{
					predicted = funcs.AdjustedWeightedSumRanked(uInd.get(i), iInd.get(i), 100);
				}
				else if(method == 5)
				{
					predicted = funcs.AdjustedWeightedSumTransformed(uInd.get(i), iInd.get(i), items);
				}
				else
				{
					System.out.println("1. Mean Utility\n" +
							"2. Mean Utility with rank\n" +
							"3. Adjusted Weighted Sum\n" +
							"4. Adjusted Weighted Sum with rank\n" +
							"5. Adjusted Weighted Sum with transformed vote\n"
							);
					System.exit(0);
				}
				
				//compare predicted rating with actual rating.
				float diff = Math.abs(predicted - fInd.get(i)); //element of MAE
				mae.add(diff);
				
				//output results
				outputResults(i ,uInd.get(i), iInd.get(i), fInd.get(i), predicted, diff);
				
				//put back into matrix
				matrix.data.get(uInd.get(i)).ratings().set(iInd.get(i), fInd.get(i)); 
					//value restored
			}
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
