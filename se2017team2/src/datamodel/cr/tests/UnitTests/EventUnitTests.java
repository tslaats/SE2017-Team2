package datamodel.cr.tests.UnitTests;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import datamodel.Position;
import datamodel.cr.Event;
import datamodel.petri.Petrinet;

public class EventUnitTests {

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
	public void testEventPositionStringName_Name() {
		assertTrue(event1.getName() == testname);
	}
	
	@Test
	public void testEventPositionStringName_isPending() {
		assertTrue(!event1.isPending());
	}
	
	@Test
	public void testEventPositionStringName_isExecuted() {
		assertTrue(!event1.isExecuted());
	}


	@Test
	public void testEventPositionStringBooleanBooleanPetrinet_Name() {
		assertTrue(event2.getPetrinet() == petrinet);
	}
	
	@Test
	public void testEventPositionStringBooleanBooleanPetrinet_isExecuted() {
		assertTrue(event2.isExecuted());
	}
	
	@Test
	public void testEventPositionStringBooleanBooleanPetrinet_isPending() {
		assertTrue(event2.isPending());
	}
	
	@Test
	public void testSetName() {
		String newname = "newname";
		
		event1.setName(newname);
		
		assertTrue(event1.getName() == newname);
	}

	@Test
	public void testSetPending() {
		boolean pending = true;
		
		if (event1.isPending()){
			fail("Event is set to pending");
			return;
		}
		
		event1.setPending(pending);
		
		assertTrue(event1.isPending());
	}

	@Test
	public void testSetExecuted() {
		boolean executed = true;
		
		if (event1.isExecuted()){
			fail("Event is set to executed");
			return;
		}
		
		event1.setExecuted(executed);
		
		assertTrue(event1.isExecuted());
	}

	

}
