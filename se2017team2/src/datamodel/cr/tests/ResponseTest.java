package datamodel.cr.tests;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import datamodel.Position;
import datamodel.cr.Event;
import datamodel.cr.Relation.Type;
import datamodel.cr.Response;

public class ResponseTest {

	private static Response response;
	
	private static Event eventIn;
	
	private static final String eventInName = "eventIn";
	
	private static final Position eventInPos = new Position(2,3);
	
	private static Event eventOut;
	
	private static final String eventOutName = "eventOut";
	
	private static final Position eventOutPos = new Position(6,7);
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
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
		assertTrue(response.getIn().getID() == eventIn.getID()
				&& response.getOut().getID() == eventOut.getID());
	}

}
