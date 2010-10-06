import java.util.*;

public class Vector {
	
	ArrayList<Integer> elements;
	
	Vector()
	{
		elements = new ArrayList<Integer>();
	}
	
	//makes a zero vector the same length as a
	Vector(Vector a)
	{
		elements = new ArrayList<Integer>();
		for(int i = 0; i < a.getSize(); i++)
		{
			addElement(0);
		}
	}
	
	int getSize()
	{
		return elements.size();
	}

	int getElement(int index)
	{
		return elements.get(index);
	}
	
	boolean addElement(Integer num)
	{
		return elements.add(num);
	}
	
	Vector makeEqualLength(Vector a, Vector b)
	{
		int siz1 = a.getSize();
		int siz2 = b.getSize();
		if(siz2 > siz1) 
		{
			while(siz1 < siz2)
			{
				a.addElement(0);
				siz1++;
			}
		}
		return a;
	}
	
	public String toString()
	{
		String bacon = "<";
		int size = this.getSize();
		for(int i = 0; i < size;i++)
		{
			//bacon += Item.itemName(getElement(i));
			bacon += getElement(i)+ " ";
			if(!(i >= size-1))
			{
				bacon += ", ";
			}
		}
		return bacon + ">";
	}
}
