
public class Word extends TextToken {
	double weight;
	Word(String w)
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
		return str + " | " + freq;
	}

	public double getWeight()
	{
		return weight;
	}

	public void setWeight(double weight)
	{
		this.weight = weight;
	}
	
}
