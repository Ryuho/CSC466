package src;

import java.util.ArrayList;

public class Item
{
	private int ID;
	private float averageRating;
	private float invUserFreq;
	private int numRatings;
	
	/*Pass in ID of item, IE: COLUMN+1 OF THE ORIGINAL DATASET. 
	 * Requires that Csv is already constructed*/
	Item(int identifier)
	{
		ID = identifier;
		numRatings = 0; //denominator of average function
		for(int i = 0; i < Csv.data.size(); i++)
		{
			if(Csv.data.get(i).at(ID) != 99)
			{
				averageRating += Csv.data.get(i).at(ID);
				numRatings++;
			}
		}
		averageRating /= numRatings;
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
		result = (float) (Math.log(numRatings/Csv.data.size())/
						Math.log(2));  //log 2 at end makes it into log2
		invUserFreq = result;
		return result;
	}
	
	/*requires Csv is instintianted 
	 * Generates a list of all items with average rating and IUF already computed*/
	public static ArrayList<Item> genAllItems()
	{
		ArrayList<Item> output = new ArrayList<Item>();
		for(int i = 0; i < 100; i++)
		{
			output.add(new Item(i));
		}
		return output;
	}
}
