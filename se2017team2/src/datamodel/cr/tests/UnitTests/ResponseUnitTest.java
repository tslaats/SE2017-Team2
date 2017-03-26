package datamodel.cr.tests.UnitTests;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import datamodel.Position;
import datamodel.cr.Event;
import datamodel.cr.Relation.Type;
import datamodel.cr.Response;

public class ResponseUnitTest {

	private static Response response;
	
	private static Event eventIn;
	
	private static final String eventInName = "eventIn";
	
	private static final Position eventInPos = new Position(2,3);
	
	private static Event eventOut;
	
	private static final String eventOutName = "eventOut";
	
	private static final Position eventOutPos = new Position(6,7);
	
	@Before
	public static void setUpBefore() throws Exception {
		eventIn = new Event(eventInPos, eventInName);
		eventOut = new Event(eventOutPos, eventOutName);
		response = new Response(eventIn, eventOut);
	}


	@Test
	public void testGetType() {
		assertTrue(response.getType() == Type.Response);
	}

	@Test
	public void testResponse() {
		assertNotNull(response);
	}

}
