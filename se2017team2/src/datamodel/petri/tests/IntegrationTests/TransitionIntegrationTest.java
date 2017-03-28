package datamodel.petri.tests.IntegrationTests;

import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import datamodel.Position;
import datamodel.cr.CrGraph;
import datamodel.petri.Transition;

public class TransitionIntegrationTest {
	
	private static final String testname = "test";
	
	private static Transition transition;
	
	private static Position pos = new Position(2,3);
	
	@Before
	public void setUpBefore() throws Exception {
		transition = new Transition(pos, testname);
	}
	
	
	@Test
	public void testSetCrGraph() {
		String newname = "newname";
		
		CrGraph crgraph = new CrGraph(newname);
		
		transition.setCrGraph(crgraph);
		
		assertTrue(transition.getCrGraph() == crgraph);
	}
}
