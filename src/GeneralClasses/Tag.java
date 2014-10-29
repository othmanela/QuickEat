package GeneralClasses;

public class Tag {

	String businessID = "";
	String businessName = "";
	String address = "";

	
	public String toString(){
		return "\""+ businessName + " (" + address + ") **id:" + businessID + "**\"";
	}
}
