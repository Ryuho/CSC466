import java.util.ArrayList;
import java.util.Iterator;

public class Main {
	public static void main(String [ ] args)
	{
		String fileName = "data/1000-out1.csv";
		if(args.length == 1){
			fileName = args[0];
		}

        ArrayList<Vector> vecList = null;
        vecList = MyParser.parseSparseVectorCSV(fileName);

        double minSupport = 0.01;
        if(args.length == 2){
        	minSupport = Double.parseDouble(args[1]);
        }
        else{
        	System.out.println("No argument for minimum support found, using 0.01 as value.");
        }
        double minConfidence = 0.01;
        if(args.length == 3){
        	minConfidence = Double.parseDouble(args[2]);
        }
        else{
        	System.out.println("No argument for minimum confidence found, using 0.01 as value.");
        }
        
        ArrayList<Vector> apriOut =  Apriori.AprMainLoop(vecList, minSupport);

        System.out.println("apriOut= "+apriOut);

	}
}

