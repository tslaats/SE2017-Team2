package datamodel.cr.tests;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import datamodel.Graph;
import datamodel.Position;
import datamodel.cr.CrGraph;
import datamodel.cr.Event;

public class CRTests {

	private static final String testname = "Test";
	
	private static CrGraph crgraph;
	
	@Before
	public void setUpBeforeClass() throws Exception {
		crgraph = new CrGraph(testname);
	}


	@Test
	public void testCrGraph() {
		assertTrue(crgraph.getGraphType() == Graph.GraphTypes.CR
				&& crgraph.getName() == testname);
	}

	@Test
	public void testAddEvent() {
		Position pos = new Position(3,5);
		
		int id = crgraph.addEvent(pos, testname);
		
		Event theevent = (Event)crgraph.getCrObjects().get(id);
		
		assertTrue(theevent.getX() == 3
				&& theevent.getY() == 5
				&& theevent.getName() == testname
				&& !theevent.isExecuted());
	}

	@Test
	public void testDeleteEvent() {
		Position pos = new Position(3,5);
		
		int id = crgraph.addEvent(pos, testname);
		
		try{
			crgraph.deleteEvent(id);
			assertNull(crgraph.getCrObjects().get(id));
		} catch (Exception e){
			e.printStackTrace();
			fail();
		}
	}

	@Test
	public void testAddCondition() {
		fail("Not yet implemented");
	}

	@Test
	public void testDeleteCondition() {
		fail("Not yet implemented");
	}

	@Test
	public void testAddResponse() {
		fail("Not yet implemented");
	}

	@Test
	public void testDeleteResponse() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetPendingEvents() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetTrace() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetPossibleActions() {
		fail("Not yet implemented");
	}

	@Test
	public void testExecuteAction() {
		fail("Not yet implemented");
	}

	@Test
	public void testDraw() {
		fail("Not yet implemented");
	}

}
