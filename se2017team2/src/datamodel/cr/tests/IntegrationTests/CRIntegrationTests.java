package datamodel.cr.tests.IntegrationTests;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import datamodel.Graph;
import datamodel.Position;
import datamodel.cr.CrGraph;
import datamodel.cr.Event;


public class CRIntegrationTests {
	
	private static final String testname = "Test";
	
	private static CrGraph crgraph;
	
	@Before
	public void beforeEachTest() throws Exception {
		crgraph = new CrGraph(testname);
	}
	
	@Test
	public void testAddEvent_Position() {
		Position pos = new Position(3,5);
		
		int id = crgraph.addEvent(pos, testname);
		
		Event theevent = (Event)crgraph.getCrObjects().get(id);
		
		assertTrue(theevent.getPosition() == pos);
	}
	
	@Test
	public void testAddEvent_Name() {
		Position pos = new Position(3,5);
		
		int id = crgraph.addEvent(pos, testname);
		
		Event theevent = (Event)crgraph.getCrObjects().get(id);
		
		assertTrue(theevent.getName() == testname);
	}
	
	@Test
	public void testAddEvent_isExecuted() {
		Position pos = new Position(3,5);
		
		int id = crgraph.addEvent(pos, testname);
		
		Event theevent = (Event)crgraph.getCrObjects().get(id);
		
		assertTrue(!theevent.isExecuted());
	}
	
	@Test
	public void testGetAllEventsContainsFirst() {
		Position pos1 = new Position(3,5);
		Position pos2 = new Position(5,7);
		
		Event event1 = new Event(pos1, testname);
		Event event2 = new Event(pos2, testname);
		
		event1.setPending(true);
		
		event2.setPending(false);
		
		crgraph.addEvent(event1);
		crgraph.addEvent(event2);
		
		
		
		List<Event> returnlist = new ArrayList<>();
		returnlist = crgraph.getAllEvents();
		
		assertTrue(returnlist.contains(event1));
	}
	
	@Test
	public void testGetAllEventsContainsSecond() {
		Position pos1 = new Position(3,5);
		Position pos2 = new Position(5,7);
		
		Event event1 = new Event(pos1, testname);
		Event event2 = new Event(pos2, testname);
		
		event1.setPending(true);
		
		event2.setPending(false);
		
		crgraph.addEvent(event1);
		crgraph.addEvent(event2);
		
		
		
		List<Event> returnlist = new ArrayList<>();
		returnlist = crgraph.getAllEvents();
		
		assertTrue(returnlist.contains(event2));
	}

	
}
