package DatabaseAccess;

import java.util.LinkedList;

import org.joda.time.DateTime;


/**
 * Please add database request method signatures to this interface. I (Ralph)
 * will implement the methods as requested. Please also add javadocs to your
 * method signatures specifying exactly what you want to access so that there is
 * no ambiguity when I implement the methods.
 * 
 * @author Ralph
 * 
 */
public interface ExtDBAccessI {

	/**Adds a data point for the amount of time waited for service for a given business and time
	 * @param businessID the businesses's unique ID
	 * @param t the time of the review (year, month, day, hour, minute)
	 * @param timeWaited the amount of time waited
	 */
	public void addWaitTimeData(String businessID, DateTime t, int timeWaited);

	
	
	/**Adds a data point for the amount of time waited for service for a given business and time
	 * @param businessID the businesses's unique ID
	 * @param t the time of the review
	 * @param waitEstimate the owner's estimate of the wait time
	 */

	public void addOwnerWaitTimeData(String businessID, DateTime t, int waitEstimate);

	/**Retrieves the name of the business for a given ID string.
	 * @param businessID the business's ID
	 * @return the business's name or null if the ID cannot be found or an error occurs.
	 */
	public String businessNameForID(String businessID);

	/**Returns a list of businessIDs of businesses whose names contain the inputed name. If the
	 * input parameter is null or has 0 length, the method returns null.
	 * @param name the part of the business name to check for
	 * @return {businessID: string, businessName: string, address: string}
	 */
	public LinkedList<String> restaurantsForNameContains(String name);

	
	
	/**Returns a list of all restaurantIDs and their names and addresses
	 * @return a JSON list {id: restaurantID, name: restaurantName, address: restaurantAddress}, 
	 * sorted by name 
	 */
	public LinkedList<String> restaurantIDsNamesAddressesSorted();
	
	
	/**Returns all restaurants in JSON format in a linked list.
	 * @return {businessID: string, businessName: string, address: string}
	 */
	public LinkedList<String> allRestaurants();
	
	
	/**Returns a JSON object with the fastest restaurants in your area and for your
	 * time of day. Returns null if any of the parameters are invalid.
	 * @param longitude
	 * @param latitude
	 * @param time
	 * @param maxResults the maximum number of results you want
	 * @return {businessID: string, name: string, address: string, stars: double, 
	 * longitude: double, latitude: double, timeToEat: int}
	 */
	public LinkedList<String> getQuickEat(double longitude, double latitude, int maxResults);
	
	
	/**Returns the closest restaurants to the client's location based on longitude and
	 * latitude. Returns null if the max parameter is invalid.
	 * @param longitude
	 * @param latitude
	 * @param max the maximum number of results desired
	 * @return {businessID: string, businessName: string, address: string}
	 */
	public LinkedList<String> findNearbyRestaurants(double longitude, double latitude, int max);
	
	
	/**Registers a new business owner with the inputed information.
	 * @param user the user name
	 * @param pass the password
	 * @param businessID the ID of the business the owner represents
	 * @return true if the operation was successful, false otherwise
	 */
	public boolean registerOwner(String user, String pass, String businessID);
	
	
	/**Returns the businessID for the inputed user name and correct password.
	 * @param user the user name
	 * @param pass the password
	 * @return the ID for the business that user represents, or null if the user name or
	 * password are incorrect
	 */
	public String getBusinessForOwner(String user, String pass);

}
