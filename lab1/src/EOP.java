
public class EOP extends TextToken {
	int number;  //paragraph number
	static int totalNum =0;
	EOP()
	{
		totalNum++;
		number = totalNum;
	}
	
	int whichParagraph()
	{
		return number;
	}
}
