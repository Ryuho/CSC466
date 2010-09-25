import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;


public class MyParser {
	ArrayList<Vector> parseNumCSV(String filename)
	{
		try {
			FileInputStream fstream = new FileInputStream("textfile.txt");
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			System.err.println("File Not Found.");
		}
		
		
		return null;
	}
	
	//ArrayList<TextToken> parseTextFile(String filename)
	//{
	//	return null;
	//}

}
