package GeneralClasses;

import static org.junit.Assert.*;

import java.util.LinkedList;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class HTMLHelperTest {

	Restaurant r1;
	Restaurant r2;
	
	@Before
	public void setUp() throws Exception {
		String name1 = "Chipotle";
		String address1 = "3900 Walnut St, Philadelphia PA 19104";
		double stars1 = 3.5;
		r1 = new Restaurant("bid", name1, address1, 0, stars1, 0, 0, 0);
		
		String name2 = "Qdoba";
		String address2 = "4031 Locust St, Philadelphia PA 19104";
		double stars2 = 2.1;
		r2 = new Restaurant("bid2", name2, address2, 0, stars2, 0, 0, 0);
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testRestaurantToHTMLTableRow() {
		String row1 = HTMLHelper.RestaurantToHTMLTableRow(r1);
		String expected1 = "<tr><td>Chipotle</td><td>3900 Walnut St, Philadelphia PA 19104</td><td>3.5</td></tr>";
		assertEquals(expected1, row1);
		
		String row2 = HTMLHelper.RestaurantToHTMLTableRow(r2);
		String expected2 = "<tr><td>Qdoba</td><td>4031 Locust St, Philadelphia PA 19104</td><td>2.1</td></tr>";
		assertEquals(expected2, row2);
	}

	@Test
	public void testHTMLTableRowsToTable(){
		
		String row1 = HTMLHelper.RestaurantToHTMLTableRow(r1);
		String row2 = HTMLHelper.RestaurantToHTMLTableRow(r2);
		
		String expectedTable = "<table border=\"1\">" + row1 + row2 + "</table>";
		
		List<String> rows = new LinkedList<String>(); 
		rows.add(row1);
		rows.add(row2);
		String table = HTMLHelper.HTMLTableRowsToTable(rows);
		
		assertEquals(expectedTable, table);
	}
}
