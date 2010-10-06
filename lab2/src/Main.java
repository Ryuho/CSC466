import java.util.ArrayList;
import java.util.Iterator;

public class Main {
	public static void main(String [ ] args)
	{
        ArrayList<Vector> vecList = null;
        vecList = MyParser.parseSparseVectorCSV("data/1000-out1.csv");

        double minSupport = 0.01;
        if(args.length == 1){
        	minSupport = Double.parseDouble(args[0]);
        }
        else{
        	System.out.println("No argument for minimum support found, using 0.01 as value.");
        }
        double minConfidence = 0.01;
        if(args.length == 2){
        	minConfidence = Double.parseDouble(args[1]);
        }
        else{
        	System.out.println("No argument for minimum confidence found, using 0.01 as value.");
        }
        ArrayList<Vector> apriOut =  Apriori.AprMainLoop(vecList, minSupport);

        System.out.println("apriOut= "+apriOut);

	}
}

