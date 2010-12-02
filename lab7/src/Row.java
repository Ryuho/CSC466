import java.util.ArrayList;

/*
 * Call methods in this order:
 * initMatrix()
 * addRow() until matrix full
 * checkIfSquare()  after matrix is full
 * calcOutInMatrix() to get outgoing links
 * 
 * and THEN, you may perform  PageRank
 */
public class Row
{
	private static ArrayList<Row> matrix;
	
	String rowName;
	ArrayList<Link> values;
	int outgoing;
	int incoming;
	int id;
	
	
	
	
	Row(String name)
	{
		rowName = name;
		outgoing = 0;
		incoming = 0;
		values = new ArrayList<Link>();
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
	
	static void initMatrix()
	{
		matrix = new ArrayList<Row>();
	}
	
	static void addRow(Row val)
	{
		if(val.id != -1 )
		{
			System.err.println("value already in matrix");
			System.exit(-1);
		}
		matrix.add(val);
		val.id = matrix.size();
	}
	
	static Row getRowbyId(int i)
	{
		return matrix.get(i);
	}
	
	static boolean checkIfSquare()
	{
		int lastval = -1;
		for(Row r : matrix)
		{
			if(lastval != -1 && r.values.size() != lastval)
			{
				return false;
			}
			lastval = r.values.size();
		}
		return matrix.size() == lastval;
	}
	
	static Row getRowbyName(String name)
	{
		for(Row r : matrix)
		{
			if(name.equals(r.rowName))
			{
				return r;
			}
		}
		return null;
	}
	
	static void calcOutInMatrix()
	{
		for(Row r : matrix)
		{
			r.calcOutgoing();
		}
	}
	
	private void calcOutgoing()
	{
		//for each value, check its brother column if it has the same number
		for(int i = 0; i < values.size(); i++)
		{
			if(values.get(i).value != -1 && matrix.get(i).id != id)
			{
				int thisval = values.get(i).value;
				int othval = matrix.get(i).values.get(id).value;
				
				//undirected graph
				if(thisval == othval)
				{
					outgoing++;
					incoming++;
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
}
