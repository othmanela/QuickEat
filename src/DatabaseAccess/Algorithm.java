package DatabaseAccess;

import java.io.File;
import java.io.IOException;
import java.util.LinkedList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;


class Algorithm {

	/**The path to the config file.
	 */
	private final static String CONFIG_PATH = "./ConfigFiles/AlgorithmConfig.xml";
	
	/**The threshold number of minutes for a time stamp to be considered comparable to the
	 * current time. Used in the algorithm query.
	 */
	private int currentTimeThreshold, ownerWaitTimeThreshold;
	
	/**The conversion factor from distance to time.
	 */
	private double distanceToTimeConversionFactor;
	
	private SQLInterface sql;
	
	
	Algorithm(){
		initializeParameters();
		sql = SQLInterface.getSQLInterface();
	}

	
	/**Reads AlgorithmConfig.xml and sets the algorithm parameter variables accordingly.
	 * Called from the constructor.
	 */
	private void initializeParameters(){
		try{
			DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
	        DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder();
	        Document doc = docBuilder.parse (new File(CONFIG_PATH));
	        
	        NodeList nl = doc.getElementsByTagName("CurrentTimeThreshold");
	        Element e = (Element) nl.item(0);
	        currentTimeThreshold = Integer.parseInt(e.getChildNodes().item(0).getNodeValue());
	        
	        nl = doc.getElementsByTagName("OwnerWaitTimeThreshold");
	        e = (Element) nl.item(0);
	        ownerWaitTimeThreshold = Integer.parseInt(e.getChildNodes().item(0).getNodeValue());
	        
	        nl = doc.getElementsByTagName("DistanceToTimeConversionFactor");
	        e = (Element) nl.item(0);
	        distanceToTimeConversionFactor = Double.parseDouble(e.getChildNodes().item(0).getNodeValue());
		}
		catch (IOException e) {
			e.printStackTrace();
			System.exit(1);
		} 
		catch (ParserConfigurationException e) {
			e.printStackTrace();
			System.exit(1);
		}
		catch (SAXException e) {
			e.printStackTrace();
			System.exit(1);
		}
	}
	
	
	LinkedList<String> getQuickEat(double longitude, double latitude, int maxResults) {
		if(maxResults <= 0){
			return null;
		}
		
		String currentWaitTimes = "create view rben.CurrentWaitTimes as " +
				"select businessID, avg(waitTime) as currentWait " +
				"from rben.WaitTimes " +
				"where time(timeLogged) between time(DATE_ADD(NOW(), INTERVAL -" + 
				currentTimeThreshold + " MINUTE)) " + 
				"and time(DATE_ADD(NOW(), INTERVAL " + currentTimeThreshold + " MINUTE)) " + 
				"group by businessID";
		
		String currentOwnerWaitTimes = "create view rben.CurrentOwnerWaitTime as " +
				"select owt.businessID, owt.waitTime as ownerWaitTime " +
				"from rben.OwnerWaitTimes owt " +
				"where owt.timeLogged >= all(select owt2.timeLogged " +
				"from rben.OwnerWaitTimes owt2 " +
				"where owt2.businessID = owt.businessID) and " +
				"owt.timeLogged between DATE_ADD(NOW(), INTERVAL -" + ownerWaitTimeThreshold
				+ " MINUTE) and NOW()";
		
		String businessDistances = "create view rben.BusinessDistances as " +
				"select businessID, " +
				"sqrt(pow(longitude - " + longitude + 
				", 2) + pow(latitude - " + latitude + ", 2)) as distance " +
				"from rben.Restaurants";
		
		String query = "select b.businessID, b.businessName, b.address, b.stars, b.longitude, " +
				"b.latitude, cwt.currentWait + " + 
				distanceToTimeConversionFactor + "*bd.distance as timeToEat, owt.ownerWaitTime " +
				"from rben.Restaurants b " +
				"left outer join rben.CurrentOwnerWaitTime owt on b.businessID = owt.businessID, " +
				"rben.CurrentWaitTimes cwt, " +
				"rben.BusinessDistances bd " +
				"where b.businessID = cwt.businessID and cwt.businessID = bd.businessID " +
				"order by timeToEat limit " + maxResults;
		
		String drop1 = "drop view rben.CurrentWaitTimes";
		String drop2 = "drop view rben.CurrentOwnerWaitTime";
		String drop3 = "drop view rben.BusinessDistances";
		
		sql.getResultSafe(currentWaitTimes);
		sql.getResultSafe(currentOwnerWaitTimes);
		sql.getResultSafe(businessDistances);
		LinkedList<String> out = JSONResultBuilder.generateJSONFormat(sql.getResultSafe(query));
		sql.getResultSafe(drop1);
		sql.getResultSafe(drop2);
		sql.getResultSafe(drop3);
		return out;
	}
	
}
