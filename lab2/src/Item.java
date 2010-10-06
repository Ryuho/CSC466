
public class Item implements Comparable {
	int itemID;
	int freq;

	Item(int id)
	{
		itemID = id;
		freq = 1;
	}

	/* increment word count*/
	public void addOne()
	{
		freq++;
	}

	

	public String toString()
	{		
		return itemName(itemID);
	}

    public static String itemName(int id){
    	switch (id){
    	case 0: return "Chocolate Cake";
		  case 1: return "Lemon Cake";
		  case 2: return "Casino Cake"; 
		  case 3: return "Opera Cake"; 
		  case 4: return "Strawberry Cake";
		  case 5: return "Truffle Cake";
		  case 6: return "Chocolate Eclair";
		  case 7: return "Coffee Eclair";
		  case 8: return "Vanilla Eclair";
		  case 9: return "Napoleon Eclair";
		  case 10: return "Almond Tart";
		  case 11: return "Apple Pie";
		  case 12: return "Apple Tart";
		  case 13: return "Apricot Tart";
		  case 14: return "Berry Tart";
		  case 15: return "Blackberry Tart";
		  case 16: return "Blueberry Tart";
		  case 17: return "Chocolate Tart";
		  case 18: return "Cherry Tart";
		  case 19: return "Lemon Tart";
		  case 20: return "Pecan Tart";
		  case 21: return "Ganache Cookie";
		  case 22: return "Gongolais Cookie";
		  case 23: return "Raspberry Cookie";
		  case 24: return "Lemon Cookie";
		  case 25: return "Chocolate Meringue";
		  case 26: return "Vanilla Meringue";
		  case 27: return "Marzipan Cookie";
		  case 28: return "Tuile Cookie";
		  case 29: return "Walnut Cookie";
		  case 30: return "Almond Croissant";
		  case 31: return "Apple Croissant";
		  case 32: return "Apricot Croissant";
		  case 33: return "Cheese Croissant";
		  case 34: return "Chocolate Croissant";
		  case 35: return "Apricot Danish";
		  case 36: return "Apple Danish";
		  case 37: return "Almond Twist";
		  case 38: return "Almond Bear Claw";
		  case 39: return "Blueberry Danish";
		  case 40: return "Lemon Lemonade";
		  case 41: return "Raspberry Lemonade";
		  case 42: return "Orange Juice";
		  case 43: return "Green Tea";
		  case 44: return "Bottled Water";
		  case 45: return "Hot Coffee";
		  case 46: return "Chocolate Coffee";
		  case 47: return "Vanilla Frappuccino";
		  case 48: return "Cherry Soda";
		  case 49: return "Single Espresso";
    	}
		return "Invalid itemNumber.";
    }
    
    public int compareTo(Object o) {
        if(o instanceof Item){
            Item temp = (Item)o;
            if(temp.itemID == this.itemID){
                return 0;
            }
            if(temp.itemID < this.itemID){
                return 1;
            }
        }
        return -1;
    }

    
}
