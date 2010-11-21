import java.util.HashSet;


public class Vocabulary
{
	static HashSet<Word> vocab = new HashSet<Word>();
	static int docNumber;  // tracker of which document is interated though
	
	Vocabulary()
	{
		vocab =  new HashSet<Word>();
		docNumber = 0;
	}
	
	
}
