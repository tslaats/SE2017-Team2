package datamodel.petri.tests.UnitTests;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import datamodel.Position;
import datamodel.cr.CrGraph;
import datamodel.petri.Transition;

public class TransitionUnitTest {

	private static final String testname = "test";
	
	private static Transition transition;
	
	private static Position pos = new Position(2,3);
	
	@Before
	public void setUpBefore() throws Exception {
		transition = new Transition(pos, testname);
	}

	@Test
	public void testTransitionCanBeDeleted() {
		assertTrue(transition.getCanBeDeleted());
	}
	
	@Test
	public void testTransitionPosition() {
		assertTrue(transition.getPos() == pos);
	}
	
	@Test
	public void testTransitionName() {
		assertTrue(transition.getName() == testname);
	}

	@Test
	public void testSetName() {
		String newname = "newname";
		
		transition.setName(newname);
	
		assertTrue(transition.getName() == newname);
	}

	

}
