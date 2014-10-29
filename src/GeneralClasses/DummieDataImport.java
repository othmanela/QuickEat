package GeneralClasses;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.StringTokenizer;

import org.joda.time.DateTime;

import DatabaseAccess.ExtDBAccess;
import DatabaseAccess.ExtDBAccessI;


class DummieDataImport {

	private final static String PATH = "./ConfigFiles/DummieWaitData.txt";
	
	public static void main(String[] args) throws IOException{
		BufferedReader buf = new BufferedReader(new FileReader(PATH));
		ExtDBAccessI db = ExtDBAccess.getInstance();
		
		for(String line = buf.readLine(); line != null; line = buf.readLine()){
			StringTokenizer st = new StringTokenizer(line, ",");
			String businessID = st.nextToken().trim();
			DateTime dt = parseDateTime(st.nextToken().trim());
			int timeWaited = Integer.parseInt(st.nextToken().trim());
			
			db.addWaitTimeData(businessID, dt, timeWaited);
		}
		
		buf.close();
	}
	
	
	private static DateTime parseDateTime(String s){
		String[] dateTime = s.split(" ");
		String[] date = dateTime[0].split("/");
		String[] time = dateTime[1].split(":");
		int month = Integer.parseInt(date[0]);
		int day = Integer.parseInt(date[1]);
		int year = Integer.parseInt(date[2]);
		int hour = Integer.parseInt(time[0]);
		int minute = Integer.parseInt(time[1]);
		
		return new DateTime(year, month, day, hour, minute);
	}
	
}
