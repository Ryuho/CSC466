import java.util.ArrayList;


public class Apriori
{
	//first pass F1:= {{i}|i e I; support({i})>= minSup);
	
	public static ArrayList<Vector> AprMainLoop(ArrayList<Vector> vec, 
			double minConf, double minSupp)
	{
		ArrayList<Vector> tmp = new ArrayList<Vector>();
		ArrayList<Vector> firstPassResult = firstPass(vec, minSupp);
		
		return tmp;
	}
	
	private static ArrayList<Vector> firstPass(ArrayList<Vector> vec, 
			double minSupp)
		{


		return null;
	}
	
	private static ArrayList<Item> getFrequency(ArrayList<Vector> vec){
		ArrayList<Item> itemFreq = new ArrayList<Item>();
		for(int i = 0; i < vec.size(); i++)
		{
			//check for frequency
			boolean contains = false;
			int containsIndex = 0;
			
			for(int k = 0; k < vec.get(i).getSize(); k++)
			{
				for(int j = 0; j< itemFreq.size(); j++)
				{
					if(vec.get(i).getElement(k) == itemFreq.get(j).itemID)
					{
						contains = true;
						containsIndex = j;
						break;
					}
				}
				if(contains)
				{
					itemFreq.get(containsIndex).addOne(); 
				}
				else
				{
					itemFreq.add(new Item(vec.get(i).getElement(k)));
				}
			}
		}
		return itemFreq;
	}
	
	private static ArrayList<Vector> canidateGen()
	{
		ArrayList<Vector> tmp = new ArrayList<Vector>();
		return tmp;
	}
	

}
