package datamodel.cr.tests.IntegrationTests;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import datamodel.Position;
import datamodel.cr.Event;
import datamodel.petri.Petrinet;


public class EventIntegrationTests {
	
	private static Event event1;
	
	private static Event event2;
	
	private static final String testname = "test";
	
	private static final Position testpos = new Position(2,3);
	
	private static final Petrinet petrinet = new Petrinet(testname);
	
	@Before
	public static void setUpBeforeEachTest() throws Exception {
		event1 = new Event(testpos, testname);
		event2 = new Event(testpos, testname, true, true, petrinet);
	}
	
	@Test
	public void testEventPositionString_Position() {
		assertTrue(event1.getPosition() == testpos);
	}
	
	@Test
	public void testEventPositionStringBooleanBooleanPetrinet_Position() {
		assertTrue(event2.getPosition() == testpos);
	}
	
	@Test
	public void testEventPositionStringBooleanBooleanPetrinet_Petrinet() {
		assertTrue(event2.getPetrinet() == petrinet);
	}

	@Test
	public void testSetPosition() {
		Position newpos = new Position(5,6);
		
		event1.setPosition(newpos);
		
		assertTrue(event1.getPosition() == newpos);
	}
	
	@Test
	public void testSetPetrinet() {
		String newname = "newname";
		Petrinet newpetrinet = new Petrinet(newname);
		
		if (event1.getPetrinet() != null){
			fail("Petrinet is set. This should not be");
			return;
		}
		
		event1.setPetrinet(newpetrinet);
		
		assertTrue(event1.getPetrinet() == newpetrinet);
		
	}
	
}
