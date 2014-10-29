package GeneralClasses;

import static org.junit.Assert.*;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class AutocompleteHelperTest {

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testBuildAutocompleteJSON() throws IOException {

//		AutocompleteHelper.buildAutocompleteJSON();


	}
	
	@Test
	public void testToJSONArray() {
		
		List<String> l0 = new LinkedList<String>();
		String a0 = AutocompleteHelper.toJSONArray(l0);
		assertEquals("[]", a0);
		
		List<String> l1 = new LinkedList<String>();
		l1.add("a");
		String a1 = AutocompleteHelper.toJSONArray(l1);
		assertEquals("[\"a\"]", a1);
		
		
		
		List<String> l2 = new LinkedList<String>();
		l2.add("a");
		l2.add("b");
		l2.add("c");
		String a2 = AutocompleteHelper.toJSONArray(l2);
		assertEquals("[\"a\",\n\"b\",\n\"c\"]", a2);


	}

	@Test
	public void testExtractBusinessID() {
		String greekLady = "GreekLady (2001 Walnut St, Philadelphia PA 19104) **id:_QzlIe0DdxSHBJtbPvNG2A**";


		String greekLadyId = AutocompleteHelper.extractBusinessID(greekLady);

		assertEquals("_QzlIe0DdxSHBJtbPvNG2A", greekLadyId);

		String madMex = "Mad Mex (1002 Market St, Philadelphia PA 19104) **id:Zw550zrQJ2zxCteW0oW48g**";

		String madMexId = AutocompleteHelper.extractBusinessID(madMex);

		assertEquals("Zw550zrQJ2zxCteW0oW48g", madMexId);

	}

}
