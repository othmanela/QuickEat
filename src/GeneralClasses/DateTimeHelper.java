package GeneralClasses;

import org.joda.time.DateTime;

public class DateTimeHelper {

	/**
	 * @param s - the datetime as a JQuery string
	 * @return the parsed JQuery string
	 */
	public static DateTime parseJQuery(String s){
		
		String[] dt = s.split(" ");
		String[] date = dt[0].split("/");
		String[] time = dt[1].split(":");
		
		int month = Integer.parseInt(date[0]);
		int day = Integer.parseInt(date[1]);
		int year = Integer.parseInt(date[2]);
		int hour = Integer.parseInt(time[0]);
		int minute = Integer.parseInt(time[1]);
		
		return new DateTime(year, month, day, hour, minute);
	}
	
}
