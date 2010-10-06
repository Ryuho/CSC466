import java.util.ArrayList;


public class AssociationRule 
{
	//Describes an Association Rule.  LEFT -> RIGHT
	ArrayList<Integer> left; //cause
	ArrayList<Integer> right; //effect
	double support;
	double confidence;
	
	AssociationRule()
	{
		left = new ArrayList<Integer>();
		right = new ArrayList<Integer>();
		support = 0;
		confidence = 0;
	}
	
	public void setSupport(double num)
	{
		support = num;
	}
	
	public void setConfidence(double num)
	{
		confidence = num;
	}
	
	public Integer getLeft(int index)
	{
		return left.get(index);
	}
	
	public Integer getRight(int index)
	{
		return right.get(index);
	}
	
	public void removeLeft(int index)
	{
		left.remove(index);
	}
	
	public void removeRight(int index)
	{
		right.remove(index);
	}
/*	
	public int freqLeft(int index)
	{
		return left.get(index).freq;
	}
	
	public int freqRight(int index)
	{
		return right.get(index).freq;
	}
	
	public int idLeft(int index)
	{
		return left.get(index).itemID;
	}
	
	public int idRight(int index)
	{
		return right.get(index).itemID;
	}*/
	
	public void addLeft(int it)
	{
		left.add(it);
	}
	
	public void addRight(int it)
	{
		right.add(it);
	}
	
	public String toString()
	{
		String compound = "{ ";
		for (int i = 0; i < left.size(); i++)
		{
			compound += Item.itemName(left.get(i)) + ", ";
			//compound += left.get(i) + ", ";
		}
		compound += "--> " + Item.itemName(right.get(0)) + " (Support = " + support +" confidence = "+confidence+ ")}" ;
		//compound += "--> " + right.get(0) + " (Support-" + support +" confidence-"+confidence+ ")}" ;
		return compound;
	}
	
}
