package GeneralClasses;

public class Restaurant {
	String businessID;
	String businessName;
	String address;
	double stars;
	double longitude;
	double latitude;
	double timeToEat;
	
	//owner wait time
	double ownerWaitTime;
	
	public Restaurant(String bId, String n, String a, double st, double lon, double lat, double wt, double owt){
		businessID = bId;
		businessName = n;
		address = a;
		stars = st;
		longitude = lon;
		latitude = lat;
		timeToEat = wt;
		ownerWaitTime = owt;
	}
}
