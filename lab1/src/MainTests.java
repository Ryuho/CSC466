import java.io.IOException;
import java.util.ArrayList;


public class MainTests
{

	public static void main(String [ ] args) throws IOException
	{
		int select;
		System.out.println("Pick a format: 1. CVS. 2. Text");
		select = System.in.read();
		
		switch(select)
		{
			case('1'): 
			{
				testCVS();break;
			}
			case('2'):
			{
				testText(); break;
			}
		}
	}
	
	static void testCVS()
	{
		ArrayList<Vector> vecList = MyParser.parseNumCSV("data/numFile.txt");
		System.out.println("Euclidean distance for 1st vector <1, 2, 3, 5>: " + 
				VectorMath.getEucledianDistance(vecList.get(1)));
		System.out.println("Euclidean distance between 1st and 2nd vectors: " + 
				VectorMath.getEucledianDistance(vecList.get(1), vecList.get(2)));
		
		System.out.println("Dimensional length of 1st vector <1, 2, 3, 5>: " + 
				VectorMath.getDimensionLength(vecList.get(1)));
		System.out.println("Dot product of first two vectors: " + 
				VectorMath.getDotProduct(vecList.get(1), vecList.get(2)));
		System.out.println("Manhattan Distance of 2 vectors: " + 
				VectorMath.getManhattanDistance(vecList.get(1), vecList.get(2)));
		System.out.println("Pearson Correlation of first two vectors: " + 
				VectorMath.getPearsonCorrelation(vecList.get(1), vecList.get(2)));
		System.out.println(" ");
		
		VectorMath.LSMrow(vecList.get(1));
		VectorMath.LSMcolumn(vecList, 1);
		
		System.out.println("Standard Deviation for 1st vector <1, 2, 3, 5>: " + 
				VectorMath.standardDeviation(vecList.get(1)));
		System.out.println("Standard Deviation for 2nd column: " + 
				VectorMath.standardDeviation(vecList, 1));
		
		
	}
	
	static void testText()
	{
		ArrayList<TextToken> lis = MyParser.parseTextFile("data/strFile.txt");
	}
}
