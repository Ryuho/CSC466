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
public class Matrix
{
	private static ArrayList<Row> matrix;
	
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
		val.id = matrix.size()-1;
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
	
	static Row getRowbyId(int i)
	{
		return matrix.get(i);
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
	static int matrixSize()
	{
		return matrix.size();
	}
	
	static void calcOutInMatrix()
	{
		for(Row r : matrix)
		{
			r.calcOutgoing();
		}
	}
}
