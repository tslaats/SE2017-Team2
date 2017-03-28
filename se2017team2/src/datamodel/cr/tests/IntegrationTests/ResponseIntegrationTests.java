package datamodel.cr.tests.IntegrationTests;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import datamodel.Position;
import datamodel.cr.Event;
import datamodel.cr.Response;


public class ResponseIntegrationTests {
	
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
	public void testResponseIn() {
		assertTrue(response.getIn() == eventIn);
	}
	
	@Test
	public void testResponseOut() {
		assertTrue(response.getOut() == eventOut);
	}
	
}
