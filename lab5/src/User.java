//package src;

import java.util.ArrayList;

public class User
{
	private int numRated;
	private ArrayList<Float> ratings;
	private float averageRating;
	private int userID;
	
	User()
	{
		numRated = 0;
		ratings = new ArrayList<Float>();
		averageRating = 0.0f;
	}
	
	User(int userID, ArrayList<Float> dat)
	{
	    this.userID = userID;
		numRated = Math.round(dat.get(0));
		ratings = new ArrayList<Float>();
		ratings.addAll(dat.subList(1, dat.size()));
		if(this.ratings.size() != 100)
		{
			System.out.println("user generation went wrong");
			System.exit(-1);
		}
		this.computeAverageRating();
	}
	
	/*function that returns an entire matrix*/
	/*public static ArrayList<User> ReadUsers(String filename)
	{
		ArrayList<User> matrix = new ArrayList<User>();
		new Csv(filename);
		
		for(int i = 1; i <  Csv.datas.size(); i++)
		{
			User cur = new User();
			cur.numRated = Math.round(Csv.datas.get(i).get(0));
			cur.ratings.addAll(Csv.datas.get(i).subList(1, Csv.datas.get(i).size()));
			cur.computeAverageRating();
			if(cur.ratings.size() != 100)
			{
				System.out.println("user generation went wrong");
				System.exit(-1);
			}
		}
		
		//wipe Csv out of memory, not needed anymore
		Csv.datas = null;
		
		return matrix;
	}*/
	
	public int numRated()
	{
		return numRated;
	}
	
	public ArrayList<Float> ratings()
	{
		return ratings;
	}

	public float at(int index)
	{
		return ratings.get(index);
	}
	
	public float averageRating()
	{
		return averageRating;
	}
	
	public int getUserID(){
	    return this.userID;
	}
	
	/*changes averageRating to computed average rating */
	public float computeAverageRating()
	{
		float result = 0f;
		int counter = 0;
		for(int i = 0; i < ratings.size(); i++)
		{
			if(ratings.get(i) != 99)
			{
				result += ratings.get(i);
				counter++;
			}
		}
		result /= (numRated * 1f);
		if(counter != numRated)
		{
			System.err.println("nonmatching number of rated items ");
			System.exit(-1);
		}
		averageRating = result;
		return result;
	}
}
