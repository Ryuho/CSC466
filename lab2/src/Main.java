import java.util.ArrayList;
import java.util.Iterator;

public class Main {
	public static void main(String [ ] args)
	{
        ArrayList<Vector> vecList = null;
        //vecList = MyParser.parseSparseVectorCSV("data/1000-out1.csv");
        vecList = MyParser.parseSparseVectorCSV("data/example/out1.csv");

        ArrayList<Vector> apriOut =  Apriori.AprMainLoop(vecList, 0.01, 0.01);

        System.out.println("apriOut= "+apriOut);

	}
}

