package src;

public class kmeans {
    public static void main(String[] args) {
        //java kmeans <Filename> <k>
        Csv csv1 = new Csv("data/AccidentsSet01.csv");
        for(int i = 0; i < csv1.datas.size(); i++){
            System.out.println(csv1.datas.get(i));
        }
    }

}
