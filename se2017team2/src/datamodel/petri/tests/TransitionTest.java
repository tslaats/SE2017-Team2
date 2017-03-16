package datamodel.petri.tests;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import datamodel.Position;
import datamodel.cr.CrGraph;
import datamodel.petri.Transition;

public class TransitionTest {

	private static final String testname = "test";
	
	private static Transition transition;
	
	private static Position pos = new Position(2,3);
	
	@Before
	public static void setUpBeforeClass() throws Exception {
		transition = new Transition(pos, testname);
	}

	@Test
	public void testTransition() {
		assertTrue(transition.getCanBeDeleted()
				&& transition.getPos().x() == 2
				&& transition.getPos().y() == 3
				&& transition.getName() == testname);
	}

	@Test
	public void testSetName() {
		String newname = "newname";
		
		transition.setName(newname);
	
		assertTrue(transition.getName() == newname);
	}

	@Test
	public void testSetCrGraph() {
		String newname = "newname";
		
		CrGraph crgraph = new CrGraph(newname);
		int id = crgraph.getID();
		
		transition.setCrGraph(crgraph);
		
		assertTrue(transition.getCrGraph().getID() == id);
	}

}
