
public class Item implements Comparable {
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

    @Override
	public String toString()
	{
		return Integer.toString(itemID);
	}

    public int compareTo(Object o) {
        if(o instanceof Item){
            Item temp = (Item)o;
            if(temp.itemID == this.itemID){
                return 0;
            }
            if(temp.itemID < this.itemID){
                return 1;
            }
        }
        return -1;
    }

    
}
