package datamodel.cr.tests;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import datamodel.Position;
import datamodel.cr.Event;
import datamodel.petri.Petrinet;

public class EventTests {

	private static Event event1;
	
	private static Event event2;
	
	private static final String testname = "test";
	
	private static final Position testpos = new Position(2,3);
	
	private static final Petrinet petrinet = new Petrinet(testname);;
	
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		event1 = new Event(testpos, testname);
		event2 = new Event(testpos, testname, true, true, petrinet);
		
	}

	@Test
	public void testEventPositionString() {
		assertTrue(event1.getName() == testname
				&& event1.getPosition().x() == 2
				&& event1.getPosition().y() == 3
				&& !event1.isPending()
				&& !event1.isExecuted());
	}

	@Test
	public void testEventPositionStringBooleanBooleanPetrinet() {
		assertTrue(event2.getName() == testname
				&& event2.getPosition().x() == 2
				&& event2.getPosition().y() == 3
				&& event2.isExecuted()
				&& event2.isPending()
				&& event2.getPetrinet() == petrinet);
	}

	@Test
	public void testSetPosition() {
		Position newpos = new Position(5,6);
		
		event1.setPosition(newpos);
		
		assertTrue(event1.getPosition().x() == 5
				&& event1.getPosition().y() == 6);
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
		
		event1.setPending(executed);
		
		assertTrue(event1.isExecuted());
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
