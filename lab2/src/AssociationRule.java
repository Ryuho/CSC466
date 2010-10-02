import java.util.ArrayList;


public class AssociationRule
{
	//Describes an Association Rule.  LEFT -> RIGHT
	ArrayList<Item> left; //cause
	ArrayList<Item> right; //effect
	
	AssociationRule()
	{
		left = new ArrayList<Item>();
		right = new ArrayList<Item>();
	}
	
	public Item getLeft(int index)
	{
		return left.get(index);
	}
	
	public Item getRight(int index)
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
	}
	
}
