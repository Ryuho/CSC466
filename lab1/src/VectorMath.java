
public class VectorMath {
	
	double getEucledianDistance(Vector a, Vector b)
	{
		//subtract corresponding elements and then square the elements
		int siz1 = a.getSize();
		int siz2 = b.getSize();
		double product = 0.0;
		
		if(siz2 > siz1) 
		{
			a = a.makeEqualLength(a, b);
			siz1 = siz2;
		}
		else if(siz2 < siz1)
		{
			b = b.makeEqualLength(b, a);
		}
		
		for(int i = 0; i < siz1; i++)
		{
			product += Math.pow(a.getElement(i) - b.getElement(i), 
					2);
		}
		//take square root
		return Math.sqrt(product);
	}
	double getEucledianDistance(Vector a)
	{
		return getEucledianDistance(a, new Vector(a));
	}
	int getDimensionLength(Vector a)
	{
		return a.getSize();
	}
	double getDotProduct(Vector a, Vector b)
	{
		int siz1 = a.getSize();
		int siz2 = b.getSize();
		double product = 0.0;
		
		if(siz2 > siz1) 
		{
			a = a.makeEqualLength(a, b);
			siz1 = siz2;
		}
		else if(siz2 < siz1)
		{
			b = b.makeEqualLength(b, a);
		}
		
		for(int i = 0; i < siz1; i++)
		{
			product += a.getElement(i) * b.getElement(i);
		}
		return product;
	}
	double getManhattanDistance(Vector a, Vector b)
	{
		int siz1 = a.getSize();
		int siz2 = b.getSize();
		double product = 0.0;
		
		if(siz2 > siz1) 
		{
			a = a.makeEqualLength(a, b);
			siz1 = siz2;
		}
		else if(siz2 < siz1)
		{
			b = b.makeEqualLength(b, a);
		}
		
		for(int i=0; i<siz1; i++)
		{
			product += Math.abs(a.getElement(i) - b.getElement(i));
		}
		return product;
	}
	
	double getPearsonCorrelation(Vector a, Vector b)
	{
		int siz1 = a.getSize();
		int siz2 = b.getSize();
		double product = 0.0;
		
		if(siz2 > siz1) 
		{
			a = a.makeEqualLength(a, b);
			siz1 = siz2;
		}
		else if(siz2 < siz1)
		{
			b = b.makeEqualLength(b, a);
		}
		
		for(int i = 0; i < siz1; i++)
		{
			product += (a.getElement(i) - vectorMean(a)) * 
				(b.getElement(i) - vectorMean(b));
		}
		
		product /= (siz1 - 1) * standardDeviation(a) * standardDeviation(b);
		return product;
	}
	double vectorMean(Vector a)
	{
		double sum = 0;
		for(int i = 0; i < a.getSize(); i++)
		{
			sum += a.getElement(i);
		}
		sum /= a.getSize();
		return sum;
	}
	double standardDeviation(Vector v)
	{
		double mean = 0;
		final int n = v.getSize();
		if(n<2)
		{
			return Double.NaN;
		}
		for(int i = 0; i< n; i++)
		{
			mean += v.getElement(i);
		}
		mean /= n;
		
		double sum = 0;
		for (int i = 0; i< n; i++)
		{
			double b = v.getElement(i) - mean;
			sum += b *b;
		}
		
		return Math.sqrt(sum / (n));
		
	}

}
