import java.io.Serializable;


public class Word extends TextToken implements Serializable{
    private static final long serialVersionUID = -7497663920290511180L;
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
