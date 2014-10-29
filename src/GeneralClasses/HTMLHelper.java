package GeneralClasses;

import java.text.DecimalFormat;
import java.util.LinkedList;
import java.util.List;

public class HTMLHelper {
	
	/**
	 * @param s - the text to go inside the td tag
	 * @param cls - the class field for tag
	 * @return a td tag with s as its text
	 */
	public static String td(String s, String cls){
		if (cls == null) cls ="";
		return "<td " + "class=\""+ cls + "\">" + s + "</td>";
	}
	
	/**
	 * @param i - the int to go inside the td tag
	 * @param cls - the class field for tag
	 * @return a td tag with s as its text
	 */
	public static String td(int i, String cls){
		return td(Integer.toString(i), cls);
	}
	
	/**
	 * @param d - the double to go inside the td tag
	 * @param cls - the class field for tag
	 * @return a td tag with s as its text
	 */
	public static String td(double d, String cls){
		return td(Double.toString(d), cls);
	}

	
	/**
	 * @param rows - a list of HTML <tr> rows 
	 * @return an HTML <table> of rows
	 */
	public static String HTMLTableRowsToTable(List<String> rows){
		
		StringBuffer s = new StringBuffer();
		
		// To fit with our new CSS file
		s.append("<table class=\"table table-striped\">");
		
		s.append("<thead>");
		s.append("<tr><th>Name</th><th>Address</th><th>ExpectedWaitTime</th><th>Wait According to Owner</th><th>Rating</th></tr>");
		s.append("</thead>");
		
		s.append("<tbody>");
		for (String r : rows) {
			s.append(r);
		}
		s.append("</tbody>");
		
		s.append("</table>");
		
		return s.toString();
	}
	
	
	/**
	 * @param r - the restaurant Object
	 * @return an HTML <tr> row containing the relevant data from restaurant r
	 */
	public static String RestaurantToHTMLTableRow(Restaurant r){
		StringBuffer s = new StringBuffer();
		
		s.append("<tr>");
		
		s.append(td(r.businessName, null));
		s.append(td(r.address, null));
		
		
		s.append(td(new DecimalFormat("###.##").format(r.timeToEat),null));
		s.append(td(r.ownerWaitTime,null));
		s.append(td(r.stars,"rating"));	
		
		s.append("</tr>");
		
		return s.toString();
	}
	
	/**
	 * @param restaurants - a list of restaurant objects
	 * @return an HTML table containing the data from restaurants
	 */
	public static String RestaurantsToHTMLTable(List<Restaurant> restaurants){
		List<String> rows = new LinkedList<String>();
		
		

		for (Restaurant rest : restaurants){
			String row = HTMLHelper.RestaurantToHTMLTableRow(rest);
			rows.add(row);
		}
		
		String htmlTable = HTMLHelper.HTMLTableRowsToTable(rows);
		
		return htmlTable;
	}

}
