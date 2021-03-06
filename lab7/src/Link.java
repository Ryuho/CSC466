
public class Link
{
	int value;
	int rank;
	boolean isOutgoing;
	boolean isIncoming;
	
	public Link(int val)
	{
		this.value = val;
		rank = -1;
		isOutgoing = false;
		isIncoming = false;
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

	public boolean isOutgoing()
	{
		return isOutgoing;
	}

	public void setOutgoing(boolean isOutgoing)
	{
		this.isOutgoing = isOutgoing;
	}

	public boolean isIncoming()
	{
		return isIncoming;
	}

	public void setIncoming(boolean isIncoming)
	{
		this.isIncoming = isIncoming;
	}
}
