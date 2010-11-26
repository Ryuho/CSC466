

public class pageRank {
	// pageRank <fileName>
	static CSV file;
	public static void main(String args[]){
		file = new CSV("data/NCAA_football.csv");
		
		System.out.println(file.toString());
	}
}
