package GeneralClasses;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class TagTest {

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testToString() {
		Tag t = new Tag();
		t.businessID = "3aq";
		t.address = "39 South St Philadelphia, PA 19104";
		t.businessName = "Tom's Place";
		
		assertEquals("Tom's Place (39 South St Philadelphia, PA 19104) **id:3aq**", t.toString());
	}

}
