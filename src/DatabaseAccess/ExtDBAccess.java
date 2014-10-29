package DatabaseAccess;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;

import org.joda.time.DateTime;


/**
 * Singleton class that handles all database access for external services.
 * Please note that this class cannot be instantiated manually. The only way to
 * access this class is through the getInstance() method.
 * 
 * @author Ralph
 * 
 */
public class ExtDBAccess implements ExtDBAccessI {

	
	private static ExtDBAccess instance;

	private SQLInterface sql;
	
	/**Stores the last ID used when inserting data into the WaitTime and OwnerWaitTimes tables.
	 */
	private int lastWaitTimeID, lastOwnerWaitTimeID;
	
	private Algorithm alg;
	

	
	/////////////////////////////////////////////////////////////////////
	// Initialization section
	/////////////////////////////////////////////////////////////////////
	
	
	private ExtDBAccess() throws IOException{
		sql = SQLInterface.getSQLInterface();
		try{
			lastWaitTimeID = getLastUsedWaitTimeID();
			lastOwnerWaitTimeID = getLastUsedOwnerWaitTimeID();
			alg = new Algorithm();
		}
		catch(SQLException e){
			throw new YelpDBException("Unable to initialize last used waitTimeIDs");
		}
	}

	/**Retrieves the last used waitTime entryID.
	 * @return
	 * @throws SQLException
	 */
	private int getLastUsedWaitTimeID() throws SQLException{
		ResultSet rs = sql.getResultSafe("select max(entryID) from rben.WaitTimes");
		if(rs.next()){
			return rs.getInt(1);
		}
		else{
			return 0;
		}
	}
	
	/**Retrieves the last used ownerWaitTime entryID.
	 * @return
	 * @throws SQLException
	 */
	private int getLastUsedOwnerWaitTimeID() throws SQLException{
		ResultSet rs = sql.getResultSafe("select max(entryID) from rben.OwnerWaitTimes");
		if(rs.next()){
			return rs.getInt(1);
		}
		else{
			return 0;
		}
	}
	
	
	/**
	 * Retrieves the active instance of this class. Enforces only having one
	 * global instance for database access accross the entire project.
	 * 
	 * @return the active instance of the public database access object
	 */
	public static ExtDBAccessI getInstance() {
		if (instance == null) {
			try{
				instance = new ExtDBAccess();
			}
			catch(IOException e){
				throw new YelpDBException("Unable to access temporary storage files");
			}
		}
		return instance;
	}

	
	/////////////////////////////////////////////////////////////////////
	// DB access methods section
	/////////////////////////////////////////////////////////////////////
	
	
	@Override
	public synchronized void addWaitTimeData(String businessID, DateTime t, int timeWaited) {
		if(businessID == null || t == null || timeWaited < 0 || timeWaited > 600){
			return;
		}
		
		int entryID = lastWaitTimeID + 1;
		String date = t.getMonthOfYear() + "/" + t.getDayOfMonth() + "/" + t.getYear() + 
				"-" + t.getHourOfDay() + ":" + t.getMinuteOfHour();
		
		String stmt = "insert into rben.WaitTimes (entryID, businessID, timeLogged, " +
				"waitTime) values (" + entryID + ", '" + businessID + 
				"', STR_TO_DATE('" + date + ":00', '%m/%d/%Y-%H:%i:%s'), " + timeWaited + ")";
		
		sql.getResultSafe(stmt);
		
		lastWaitTimeID++;
	}
	
	
	@Override
	public void addOwnerWaitTimeData(String businessID, DateTime t,
			int waitEstimate) {
		if(businessID == null || t == null || waitEstimate < 0 || waitEstimate > 600){
			return;
		}
		
		int entryID = lastOwnerWaitTimeID + 1;
		String date = t.getMonthOfYear() + "/" + t.getDayOfMonth() + "/" + t.getYear() + 
				"-" + t.getHourOfDay() + ":" + t.getMinuteOfHour();
		
		String stmt = "insert into rben.OwnerWaitTimes (entryID, businessID, timeLogged, " +
				"waitTime) values (" + entryID + ", '" + businessID + 
				"', STR_TO_DATE('" + date + ":00', '%m/%d/%Y-%H:%i:%s'), " + waitEstimate + ")";
		
		sql.getResultSafe(stmt);
		
		lastOwnerWaitTimeID++;
	}

	
	@Override
	public synchronized String businessNameForID(String businessID) {
		if(businessID == null){
			return null;
		}
		String stmt = "select businessName from rben.Businesses b " +
				"where b.businessID = " + businessID;
		ResultSet rs = sql.getResultSafe(stmt);
		try{
			while(rs.next()){
				return rs.getString(1);
			}
			return null;
		}
		catch(SQLException e){
			e.printStackTrace();
			return null;
		}
	}

	
	@Override
	public synchronized LinkedList<String> restaurantsForNameContains(String name) {
		if(name == null || name.length() == 0){
			return null;
		}
		name = makeSafeString(name);
		ResultSet rs = sql.getResultSafe(
				"select businessID, businessName, address from rben.Restaurants " +
				"where UPPER(businessName) like '%" + 
						name.toUpperCase() + "%' order by businessName");
		return JSONResultBuilder.generateJSONFormat(rs);
	}
	
	
	@Override
	public synchronized LinkedList<String> restaurantIDsNamesAddressesSorted() {
		String query = "select b.businessID as id, b.businessName as name, b.address as address " +
				"from rben.Restaurants b";
		ResultSet rs = sql.getResultSafe(query);
		return JSONResultBuilder.generateJSONFormat(rs);
	}
	
	
	@Override
	public synchronized LinkedList<String> allRestaurants() {
		String query = "select businessID, businessName, address " +
				"from rben.Restaurants order by businessName";
		ResultSet rs = sql.getResultSafe(query);
		return JSONResultBuilder.generateJSONFormat(rs);
	}
	
	
	@Override
	public synchronized LinkedList<String> getQuickEat(double longitude, double latitude, int maxResults) {
		if(maxResults <= 0){
			return null;
		}
		
		//Calls the algorithm object
		return alg.getQuickEat(longitude, latitude, maxResults);
	}
	
	
	@Override
	public synchronized LinkedList<String> findNearbyRestaurants(double longitude,
			double latitude, int max) {
		if(max <= 0){
			return null;
		}
		
		String query = "select businessID, businessName, address " +
				"from rben.Restaurants " +
				"order by sqrt(pow(longitude - " + longitude + 
				", 2) + pow(latitude - " + latitude + ", 2)) limit " + max;
		
		return JSONResultBuilder.generateJSONFormat(sql.getResultSafe(query));
	}
	
	
	@Override
	public synchronized boolean registerOwner(String user, String pass, String businessID) {
		if(user == null || pass == null || !isSafeString(user) || !isSafeString(pass)){
			return false;
		}
		String query = "select username, pass, businessID from rben.OwnerRegistry " +
				"where username = '" + user + "'";
		
		try{
			ResultSet rs = sql.getResultSafe(query);
			if(rs.next()){
				return false;
			}
			String addOwner = "insert into rben.OwnerRegistry (username, pass, businessID) " +
					"values ('" + user + "', '" + pass + "', '" + businessID + "')";
			sql.getResultSafe(addOwner);
			return true;
		}
		catch(SQLException e){
			e.printStackTrace();
			return false;	//Return null if there are any exceptions
		}
		
	}

	
	@Override
	public synchronized String getBusinessForOwner(String user, String pass) {
		if(user == null || pass == null){
			return null;
		}
		
		user = makeSafeString(user);
		pass = makeSafeString(pass);
		
		String query = "select username, pass, businessID from rben.OwnerRegistry " +
				"where username = '" + user + "'";
		
		ResultSet rs = sql.getResultSafe(query);
		
		//Enters the loop if there exists a user with the inputed username, otherwise skips
		//and returns null
		try{
			while(rs.next()){
				if(rs.getString(2).equals(pass)){
					return rs.getString(3);
				}
			}
			return null;
		}
		catch(SQLException e){
			e.printStackTrace();
			return null;	//Return null if there are any exceptions
		}
	}
	
	
	/**Removes quotes from strings to avoid having the SQL syntax be invalidated.
	 * @param s the string to clean
	 * @return the clean string
	 */
	private String makeSafeString(String s){
		String temp = s.replace("'", "");
		return temp.replace("\"", "");
	}

	
	/**Checks if the inputed is safe. Used in the owner registration method.
	 * @param s the string to check
	 * @return true if the string is safe, false otherwise
	 */
	private boolean isSafeString(String s){
		if(s == null){
			return false;
		}
		return !s.contains("'") && !s.contains("\"");
	}

}
