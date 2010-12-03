import java.util.ArrayList;
import java.util.Collections;


public class pageRank
{
	static final double epsilon = .01;
	static final double d = 0.95;
	// pageRank <fileName>
	static CSV file;

	public static void main(String args[])
	{
		boolean isDirected = false;
		pageRank ranker = new pageRank();
		file = new CSV("data/stateborders.csv");

		if (args.length == 1)
		{
			file = new CSV(args[0]);
		} else if (args.length == 2)
		{
			file = new CSV(args[0]);
			isDirected = true;
		}

		Matrix.initMatrix(isDirected);

		// System.out.println(file.toString());
		// System.out.println("size="+file.rows.size());

		int matrixSize = file.rows.size();

		for (int i = 0; i < file.rows.size(); i++)
		{
			Row tempRow = new Row(file.rows.get(i), matrixSize);
			Matrix.addRow(tempRow);
		}

		for (int i = 0; i < file.data.size(); i++)
		{
			Row tempRow = Matrix.getRowbyName(file.data.get(i).get(0));
			Row tempRow2 = null;
			Link tempLink = new Link(Integer.parseInt(file.data.get(i).get(1)));
			Link tempLink2 = null;
			tempRow.addValue(tempLink, Matrix.getRowbyName(file.data.get(i)
					.get(2)).id);
			if (isDirected)
			{
				tempRow2 = Matrix.getRowbyName(file.data.get(i).get(2));
				tempLink2 = new Link(Integer.parseInt(file.data.get(i).get(3)));
				tempRow2.addValue(tempLink2, Matrix.getRowbyName(file.data.get(
						i).get(0)).id);
			}

		}
		Matrix.checkIfSquare();
		Matrix.calcOutInMatrix();
		
		ranker.rankPages();
		ranker.printResult();
	}// end main

	
	private void printResult()
	{
		ArrayList<Pair> ranks = new ArrayList<Pair>();
		
		//organize into Pairs
		for(int i = 0; i < Matrix.matrixSize(); i++)
		{
			Row cur = Matrix.getRowbyId(i);
			ranks.add(new Pair(cur.getNewPageRank(), cur.name()));
		}
		
		//sort
		Collections.sort(ranks);
		
		//print
		for(int i = 0; i < ranks.size(); i++)
		{
			System.out.println(ranks.get(i).getStr() 
					+ " with pagerank: \t" + ranks.get(i).getSimValue());
		}
	} // end print
	
	private void initPhase()
	{
		for (int i = 0; i < Matrix.matrixSize(); i++)
		{
			Matrix.getRowbyId(i).setNewPageRank(1.0 / ((double) Matrix.matrixSize()));
			Matrix.getRowbyId(i).setPageRank(Double.MAX_VALUE);
		}
	} // end init

	private double sumofRankDifference()
	{
		double output = 0.0;
		for (int i = 0; i < Matrix.matrixSize(); i++)
		{
			output += Math.abs(Matrix.getRowbyId(i).getNewPageRank()
					- Matrix.getRowbyId(i).getPageRank());
		}
		return output;
	} // end rankDifference

	
	private void shiftPageRanks()
	{
		for (int i = 0; i < Matrix.matrixSize(); i++)
		{
			Row cur = Matrix.getRowbyId(i);
			cur.setPageRank(cur.getNewPageRank());
			cur.setNewPageRank(-1.0);
		}
	} //end shiftPageRanks

	
	public void rankPages()
	{
		// init phase. PageRank is set now
		initPhase();

		// ranking phase
		while (sumofRankDifference() > epsilon)
		{
			// move over for next iteration
			shiftPageRanks();

			for (int i = 0; i < Matrix.matrixSize(); i++)
			{
				Row iCur = Matrix.getRowbyId(i);
				// calculate random probability
				double randProb = (1.0 - d) * (1.0 / ((double) Matrix.matrixSize()));

				// calculate outgoing link probability
				double sum = 0;
				for (int j = 0; j < Matrix.matrixSize(); j++)
				{
					Row jCur = Matrix.getRowbyId(j);
					if(jCur.getLinkValue(i) >= 0 && (iCur.getLinkValue(j) <= jCur.getLinkValue(i)))
					{
					    sum += (1.0 / jCur.numOutgoing()) * jCur.getPageRank();
					}
				}
				iCur.setNewPageRank(randProb + (d * sum));
			}
		}
	} // end rankPages
}
