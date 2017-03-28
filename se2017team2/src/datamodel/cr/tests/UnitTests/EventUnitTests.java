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
	
	private static Event event3;
	
	private static Event event4;
	
	
	
	private static final String testname = "test1";
	
	private static final String testname2 = "test2";
	
	
	private static final Position testpos = new Position(2,3);
	
	private static final Petrinet petrinet = new Petrinet(testname);
	
	
	@Before
	public static void setUpBeforeEachTest() throws Exception {
		event1 = new Event(testpos, testname);
		event2 = new Event(testpos, testname, true, true, petrinet);
		
		event3 = new Event(testpos, testname2);
		event4 = new Event(testpos, testname2, false, false, petrinet);
		
	}

	@Test
	public void testEventPositionStringName1() {
		assertTrue(event1.getName() == testname);
	}
	
	@Test
	public void testEventPositionStringName2() {
		assertTrue(event3.getName() == testname2);
	}
	
	@Test
	public void testEventPositionStringisPending() {
		assertTrue(!event1.isPending());
	}
	
	@Test
	public void testEventPositionStringisExecuted() {
		assertTrue(!event1.isExecuted());
	}


	@Test
	public void testEventPositionStringBooleanBooleanPetrinet_Name1() {
		assertTrue(event2.getName() == testname);
	}
	
	@Test
	public void testEventPositionStringBooleanBooleanPetrinet_Name2() {
		assertTrue(event4.getName() == testname2);
	}
	
	@Test
	public void testEventPositionStringBooleanBooleanPetrinet_isExecuted1() {
		assertTrue(event2.isExecuted());
	}
	
	@Test
	public void testEventPositionStringBooleanBooleanPetrinet_isPending1() {
		assertTrue(event2.isPending());
	}
	
	@Test
	public void testEventPositionStringBooleanBooleanPetrinet_isExecuted2() {
		assertTrue(!event4.isExecuted());
	}
	
	@Test
	public void testEventPositionStringBooleanBooleanPetrinet_isPending2() {
		assertTrue(!event4.isPending());
	}
	
	@Test
	public void testSetName1() {
		String newname = "newname1";
		
		event1.setName(newname);
		
		assertTrue(event1.getName() == newname);
	}
	
	@Test
	public void testSetName2() {
		String newname = "newname2";
		
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
