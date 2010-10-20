package src;

public class Main {
    public static void main(String[] args) {
        Csv csv1 = new Csv("data/AccidentsSet01.csv");
        for(int i = 0; i < csv1.datas.size(); i++){
            System.out.println(csv1.datas.get(i));
        }
        
        Header txt1 = new Header("data/header_AccidentsSet01.txt");
        for(int i = 0; i < txt1.datas.size(); i++){
            System.out.println(txt1.datas.get(i));
        }
    }
}
