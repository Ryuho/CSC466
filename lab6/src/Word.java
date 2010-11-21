import java.io.Serializable;
import java.util.HashSet;


public class Word extends TextToken implements Serializable{
    private static final long serialVersionUID = -7497663920290511180L;
	double weight;
	
	//vocabulary terms
	HashSet<Integer> documentFreq;
	
	
	Word(String w)
	{
		str = w;
		freq = 1;
		documentFreq = new HashSet<Integer>();
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
	
	public void incrementDocumentFreq()
	{
		documentFreq.add(Vocabulary.docNumber);
	}
	
	public int documentFreq()
	{
		return documentFreq.size();
	}
	
	public boolean equals(Object o)
	{
		if(o instanceof Word)
		{
			if(((Word) o).str.equals(str))
			{
				return true;
			}
		}
		return false;
	}
}
