
public class EOS extends TextToken {
	int number;  //sentence number
	static int totalNum =0;
	EOS()
	{
		totalNum++;
		number = totalNum;
	}
	
	int whichSentence()
	{
		return number;
	}
}
