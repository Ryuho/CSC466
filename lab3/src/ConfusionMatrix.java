package src;

public class ConfusionMatrix
{
	int truePositives;
	int falsePositives;
	int trueNegatives;
	int falseNegatives;
	int tuple;
	
	ConfusionMatrix()
	{
		tuple = 0;
		truePositives = 0;
		falsePositives = 0;
		trueNegatives = 0;
		falseNegatives = 0;
	}
	public void tuple()
	{
		tuple++;
	}
	
	public void tp()
	{
		truePositives++;
	}
	
	public void fp()
	{
		falsePositives++;
	}
	
	public void tn()
	{
		trueNegatives++;
	}
	
	public void fn()
	{
		falseNegatives++;
	}
	
	public ConfusionMatrix combine(ConfusionMatrix other)
	{
		this.truePositives += other.truePositives;
		this.trueNegatives += other.trueNegatives;
		this.falsePositives += other.falsePositives;
		this.falseNegatives += other.falseNegatives;
		this.tuple += other.tuple;
		return this;
	}
	
	
}
