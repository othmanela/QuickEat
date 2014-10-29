package DatabaseAccess;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import com.google.gson.Gson;

/**
 * Handles initial import of the JSON data into the database. Assumes that the
 * tables already exist
 * 
 * @author Ralph
 * 
 */
class JSONImport {

	private static final String PATH = "/Users/Ralph/College/Spring 2013/CIS 330/Project/yelp_academic_dataset.json";

	private static final String RESTAURANT_KEYWORDS_PATH = "./ConfigFiles/RestaurantKeywords.txt";
	
	private static final Gson gson = new Gson();

	private static SQLInterface sql = SQLInterface.getSQLInterface();

	public static void main(String[] args) throws IOException {
		BufferedReader buf = new BufferedReader(new FileReader(PATH));

		// Skips all Reviews on the first pass because the Reviews table has
		// dependencies on the
		// Businesses table and the businesses data comes after the Reviews data
		for (String line = buf.readLine(); line != null; line = buf.readLine()) {
			if (line.contains("\"type\": \"user\"")) {
				handleUser(line);
			} else if (line.contains("\"type\": \"business\"")) {
				handleBusiness(line);
			}
		}
		buf.close();

		buf = new BufferedReader(new FileReader(PATH));

		// Populates the Reviews after the Users and Businesses data has been
		// populated
		for (String line = buf.readLine(); line != null; line = buf.readLine()) {
			if (line.contains("\"type\": \"review\"")) {
				handleReview(line);
			}
		}
		buf.close();
		
		// Import categories from RestaurantKeywords.txt into the FoodCategories table
		buf = new BufferedReader(new FileReader(RESTAURANT_KEYWORDS_PATH));
		for(String line = buf.readLine(); line != null; line = buf.readLine()){
			String update = "insert into rben.FoodCategories (categoryName) values ('" 
					+ line.toLowerCase().trim() +"')";
			System.out.println(update);
			sql.getResultSafe(update);
		}
		buf.close();
	}

	/**
	 * Handles parsing of the JSON string as well as insertion into the
	 * database.
	 * 
	 * @param line
	 *            the JSON line
	 */
	private static void handleUser(String line) {
		User u = gson.fromJson(line, User.class);

		String update = "insert into rben.Users (userID, userName, url, averageStars, reviewCount, funnyVotes, usefulVotes, coolVotes) "
				+ "values ('"
				+ u.user_id
				+ "', '"
				+ sanitize(u.name)
				+ "', '"
				+ u.url
				+ "', "
				+ u.average_stars
				+ ", "
				+ u.review_count
				+ ", "
				+ u.votes.funny
				+ ", "
				+ u.votes.useful
				+ ", "
				+ u.votes.cool + ")";
		System.out.println(update);
		sql.getResultSafe(update);
	}

	/**
	 * Handles parsing of the JSON string as well as insertion into the
	 * database.
	 * 
	 * @param line
	 *            the JSON line
	 */
	private static void handleBusiness(String line) {
		Business b = gson.fromJson(line, Business.class);
		String update = "insert into rben.Businesses (businessID, address, businessName, photoURL, city, state, url, reviewCount, stars, longitude, latitude) "
				+ "values ('"
				+ b.business_id
				+ "', '"
				+ sanitize(b.full_address)
				+ "', '"
				+ sanitize(b.name)
				+ "', '"
				+ b.photo_url
				+ "', '"
				+ sanitize(b.city)
				+ "', '"
				+ b.state
				+ "', '"
				+ b.url
				+ "', "
				+ b.review_count
				+ ", "
				+ b.stars
				+ ", "
				+ b.longitude
				+ ", "
				+ b.latitude + ")";
		System.out.println(update);
		sql.getResultSafe(update);
		insertSchools(b.schools, b.business_id);
		insertCategories(b.categories, b.business_id);
		insertNeighborhoods(b.neighborhoods, b.business_id);
	}

	/**
	 * Inserts schools into the schools table, and inserts a tuple into the
	 * schools proximity table.
	 * 
	 * @param schools
	 */
	private static void insertSchools(ArrayList<String> schools,
			String businessID) {
		for (String school : schools) {
			String update = "insert into rben.SchoolProximities (businessID, schoolName) values ('"
					+ businessID + "', '" + sanitize(school) + "')";
			System.out.println(update);
			sql.getResultSafe(update);
		}
	}

	/**
	 * Inserts schools into the schools table, and inserts a tuple into the
	 * schools proximity table.
	 * 
	 * @param schools
	 */
	private static void insertCategories(ArrayList<String> categories,
			String businessID) {
		for (String category : categories) {

			String update = "insert into rben.BusinessTypes (businessID, categoryName) values ('"
					+ businessID + "', '" + sanitize(category.toLowerCase()) + "')";
			System.out.println(update);
			sql.getResultSafe(update);
		}
	}

	/**
	 * Inserts schools into the schools table, and inserts a tuple into the
	 * schools proximity table.
	 * 
	 * @param schools
	 */
	private static void insertNeighborhoods(ArrayList<String> neighborhoods,
			String businessID) {
		for (String neighborhood : neighborhoods) {
			String update = "insert into rben.BusinessNeighborhoods (businessID, neighborhoodName) values ('"
					+ businessID + "', '" + sanitize(neighborhood) + "')";
			System.out.println(update);
			sql.getResultSafe(update);
		}
	}

	/**
	 * Handles parsing of the JSON string as well as insertion into the
	 * database.
	 * 
	 * @param line
	 *            the JSON line
	 */
	private static void handleReview(String line) {
		Review r = gson.fromJson(line, Review.class);
		String update = "insert into rben.Reviews (reviewID, userID, businessID, stars, reviewDate, reviewText, funnyVotes, usefulVotes, coolVotes) "
				+ "values ('"
				+ r.review_id
				+ "', '"
				+ r.user_id
				+ "', '"
				+ r.business_id
				+ "', "
				+ r.stars
				+ " , STR_TO_DATE('"
				+ r.date
				+ "', '%Y-%m-%d'), '"
				+ sanitize(r.text)
				+ "', "
				+ r.votes.funny
				+ ", "
				+ r.votes.useful
				+ ", "
				+ r.votes.cool
				+ ")";
		System.out.println(update);
		sql.getResultSafe(update);
	}

	/**
	 * Places the escape character before appropriate characters so they will
	 * not interfere with the SQL syntax.
	 * 
	 * @param s
	 *            the String to sanitize
	 * @return
	 */
	private static String sanitize(String s) {
		String temp = s.replace("'", "\\'");
		temp = temp.replace("\\\"", "\"");
		temp = temp.replace("\n", " ");
		if (temp.length() > 0 && temp.charAt(temp.length() - 1) == '\\') {
			temp = temp.substring(0, temp.length() - 1);
		}
		return temp;
	}
	
}
