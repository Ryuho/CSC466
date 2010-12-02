import java.util.ArrayList;


public class Row
{
	
	
	String rowName;
	ArrayList<Link> values;
	int outgoing;
	int incoming;
	int id;
	
	double pageRank;
	double newPageRank;

	
	
	Row(String name, int matrixSize)
	{
		rowName = name;
		outgoing = 0;
		incoming = 0;
		values = new ArrayList<Link>(matrixSize);
		id = -1;
	}
	
	Row()
	{
		rowName = "";
		outgoing = 0;
		incoming = 0;
		values = new ArrayList<Link>();
		id = -1;
	}
	
	
	public void calcOutgoing()
	{
		//for each value, check its brother column if it has the same number
		for(int i = 0; i < values.size(); i++)
		{
			if(values.get(i).value != -1 && Matrix.getRowbyId(i).id != id)
			{
				int thisval = values.get(i).value;
				int othval = Matrix.getRowbyId(i).values.get(id).value;
				
				//undirected graph
				if(thisval == othval)
				{
					outgoing++;
					incoming++;
					this.values.get(i).setOutgoing(true);
				}
				else if(othval < 0)
				{
					System.err.println("matrix not symmetric");
					System.exit(-1);
				}
				else if(thisval > othval)
				{
					incoming++;
				}
				else if(thisval < othval)
				{
					outgoing++;
					this.values.get(i).setOutgoing(true);
				}
			}
		}
	}
	
	public int numOutgoing()
	{
		return outgoing;
	}
	
	public int numIncoming()
	{
		return incoming;
	}
	
	public String name()
	{
		return rowName;
	}
	
	public void addValue(Link newVal, int index)
	{
		this.values.add(index, newVal);
	}

	
	public int getLinkValue(int valId)
	{
		return values.get(valId).value;
	}
	
	public int getLinkRank(int valId)
	{
		return values.get(valId).rank;
	}
	
	public Link getLink(int valId)
	{
		return values.get(valId);
	}
	
	
}
