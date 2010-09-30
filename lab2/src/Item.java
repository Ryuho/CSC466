
public class Item {
	int itemID;
	int freq;
	
	Item(int id)
	{
		itemID = id;
		freq = 1;
	}
	
	/* increment word count*/
	public void addOne()
	{
		freq++;
	}
	
	public String toString()
	{
		return Integer.toString(itemID);
	}
}
