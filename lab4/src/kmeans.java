package src;

import java.util.ArrayList;

public class kmeans {
    static String filename;
    static Csv csv1;
    static int k;
    public static void main(String[] args) {
        //java kmeans <Filename> <k>
        filename = "data/mammal_milk.csv";
        k = 3;
        if(args.length == 2){
            filename = args[0];
            k = Integer.parseInt(args[1]);
        }
        csv1 = new Csv(filename);
        
        System.out.println("Restrictions:");
        System.out.println(csv1.restrictions);
        System.out.println("Datas:");
        for(int i = 0; i < csv1.datas.size(); i++){
            System.out.println(csv1.datas.get(i));
        }
        
        ArrayList<Integer> seed = SelectInitialCentroids(csv1.datas, k);
    }

    public static ArrayList<Integer> SelectInitialCentroids(ArrayList<ArrayList<Integer>> arrayList, int k){
        return null;
    }
    
    public static ArrayList<Double> centerOfPoints(ArrayList<ArrayList<Integer>> arrayList){
        
        return null;
    }
}
