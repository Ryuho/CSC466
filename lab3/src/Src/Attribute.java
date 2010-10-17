package Src;

public class Attribute
{
	private int id;
	private String name;
	boolean isEdge;
	
	Attribute(String n, int col, boolean isNode)
	{
		id = col;
		name = n;
		isEdge = !isNode;
	}
	
	public int id()
	{
		return id;
	}
	
	public String name()
	{
		return name;
	}
	
	public boolean isEdge()
	{
		return isEdge;
	}
}
