package DatabaseAccess;


public class TestDB {

	public static void main(String[] args){
		ExtDBAccessI db = ExtDBAccess.getInstance();
		for(String s: db.getQuickEat(-75.199935, 39.955286, 10)){
			System.out.println(s);
		}
	}
}
