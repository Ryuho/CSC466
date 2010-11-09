//package src;

import java.util.ArrayList;

public class Item
{
	private int ID;
	private float averageRating;
	private float invUserFreq;
	private int numRatings;
	private int dataSize;
	//private Csv csv;
	
	/*Pass in ID of item, IE: COLUMN+1 OF THE ORIGINAL DATASET. 
	 * Requires that Csv is already constructed*/
	Item(int identifier, Csv csv)
	{
		//csv = matrix;
		ID = identifier;
		numRatings = 0; //denominator of average function
		for(int i = 0; i < csv.data.size(); i++)
		{
			if(csv.data.get(i).at(ID) != 99)
			{
				averageRating += csv.data.get(i).at(ID);
				numRatings++;
			}
		}
		averageRating /= numRatings;
		dataSize = csv.data.size();
		computeIUF();
	}
	
	/*Query methods */
	public int ID()
	{
		return ID;
	}
	
	public int numRatings()
	{
		return numRatings;
	}
	
	public float averageRating()
	{
		return averageRating;
	}
	
	/*gets IUF. multiply to a rating to get a transformed vote */
	public float invUserFreq()
	{
		return invUserFreq;
	}
	
	/* computer inverse user frequency */
	public float computeIUF()
	{
		float result = 0f;
		result = (float) (Math.log(numRatings/dataSize)/
						Math.log(2));  //log 2 at end makes it into log2
		invUserFreq = result;
		return result;
	}
	
	/*requires Csv is instintianted 
	 * Generates a list of all items with average rating and IUF already computed*/
	public static ArrayList<Item> genAllItems(Csv matrix)
	{
		ArrayList<Item> output = new ArrayList<Item>();
		for(int i = 0; i < 100; i++)
		{
			output.add(new Item(i, matrix));
		}
		return output;
	}
}
