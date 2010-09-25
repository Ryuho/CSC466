
public class Words implements java.lang.Comparable {
	String str;
	Integer freq;
	static boolean isStrSorted;
	
	Words(String w)
	{
		str = w;
		freq = 1;
	}
	
	/* increment word count*/
	public void addOne()
	{
		freq++;
	}
	
	public int compareTo(Object o) 
	{
		if(isStrSorted)
		{
			return str.compareTo(o);
		}
		return freq.compareTo(o);
	}
	
	public static void setFreqSort()
	{
		isStrSorted = false;
	}
	
	public static void setStrSort()
	{
		isStrSorted = true;
	}

}
