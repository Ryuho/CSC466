import java.io.Serializable;
import java.util.HashSet;


public class Vocabulary implements Serializable
{
    private static final long serialVersionUID = -5466773756764427305L;
    static HashSet<Word> vocab = new HashSet<Word>();
	static int docNumber;  // tracker of which document is interated though
	
	Vocabulary()
	{
		vocab =  new HashSet<Word>();
		docNumber = 0;
	}
	
	
}