import java.util.*;

public class Vector {
	
	ArrayList<Number> elements;
	
	Vector()
	{
		elements = new ArrayList<Number>();
	}
	
	//makes a zero vector the same length as a
	Vector(Vector a)
	{
		elements = new ArrayList<Number>();
		for(int i = 0; i < a.getSize(); i++)
		{
			addElement(0);
		}
	}
	
	int getSize()
	{
		return elements.size();
	}

	double getElement(int index)
	{
		return elements.get(index).doubleValue();
	}
	
	boolean addElement(Number num)
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
			bacon += getElement(i);
			if(!(i >= size-1))
			{
				bacon += ", ";
			}
		}
		return bacon + ">";
	}
}
