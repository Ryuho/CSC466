
public class Chars extends TextToken {
	Chars(String w)
	{
		str = w;
		freq = 1;
	}
	
	/* increment word count*/
	public void addOne()
	{
		freq++;
	}
	
	public String toString()
	{
		return "<"+str+">";
	}
}
