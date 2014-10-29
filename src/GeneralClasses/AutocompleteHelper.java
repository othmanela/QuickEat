package GeneralClasses;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import DatabaseAccess.ExtDBAccess;
import DatabaseAccess.ExtDBAccessI;

import com.google.gson.Gson;

/**
 * @author josh
 *
 */
public class AutocompleteHelper {

	/**
	 * @param autocomplete - the string to build the autocomplete for
	 * @return JSON array of the autocomplete tags
	 * @throws IOException
	 */
	public static String buildAutocompleteJSON(String autocomplete) throws IOException{
		ExtDBAccessI db = ExtDBAccess.getInstance();
		List<String> tags = db.restaurantsForNameContains(autocomplete);

		Gson gson = new Gson();
		
		List<String> tagStrings = new LinkedList<String>();
		
		for (String tag : tags){
			Tag t = gson.fromJson(tag, Tag.class);
			tagStrings.add(t.toString());
		}
		
		
		String arr = toJSONArray(tagStrings);
		
		return arr;

	}
	
	
	
	/**
	 * @param elements - list of elements to put into the JSON array
	 * @return a JSON array of elements
	 */
	public static String toJSONArray(List<String> elements) {
		
		StringBuffer sb = new StringBuffer();

		sb.append("[");
		
		if (elements.size() == 0) {
			
		} else {
			
			//add all elements (except for the last one) with a comma
			// and newline separator
			
			for (String e : elements.subList(0, elements.size() -1 )){
				sb.append(e);
				sb.append(",\n");
			}

			//add last element
			sb.append(elements.get(elements.size()-1));
			
		}
		
		sb.append("]");
		
		return sb.toString();
		
	}

	// assuming the String s has the business id in the format **id:3**, this extracts 3
	/**
	 * @param s - has the business id in the format **id:__** (e.g., given **id:3**, this extracts 3)
	 * @return business id as a String
	 */
	public static String extractBusinessID(String s){
		final String ID_PATTERN = ".*\\*\\*id:(.+)\\*\\*.*";

		Pattern p = Pattern.compile(ID_PATTERN);
		Matcher m = p.matcher(s);

		m.find();

		String businessId = m.group(1);

		//		String b = businessId;

		return businessId;

	}

}
