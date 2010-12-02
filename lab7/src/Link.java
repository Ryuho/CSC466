
public class Link
{
	int value;
	int rank;
	
	public Link(int val)
	{
		this.value = val;
		rank = -1;
	}
	
	public int getValue()
	{
		return value;
	}
	public void setValue(int value)
	{
		this.value = value;
	}
	public int getRank()
	{
		return rank;
	}
	public void setRank(int pagerank)
	{
		this.rank = pagerank;
	}
}
