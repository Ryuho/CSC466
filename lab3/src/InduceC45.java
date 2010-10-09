
public class InduceC45{
    public static void main(String [ ] args){
        //java InduceC45 <domainFile.xml> <TrainingSetFile.csv> [<restrictionsFile>]
        
        DecisionTree test = fileParser.parseXMLDomain("data/domain.xml");

        
        csvInfo csvAL = fileParser.parseCSV("data/tree02-20-numbers.csv");
        
        //System.out.println(csvAL);        
    }

}