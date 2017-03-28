package datamodel.cr.tests.UnitTests;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import datamodel.Position;
import datamodel.cr.Conditional;
import datamodel.cr.Event;
import datamodel.cr.Relation.Type;

public class ConditionalUnitTests {

	private static Conditional conditional;
	
	private static Event eventIn;
	
	private static final String eventInName = "eventIn";
	
	private static final Position eventInPos = new Position(2,3);
	
	private static Event eventOut;
	
	private static final String eventOutName = "eventOut";
	
	private static final Position eventOutPos = new Position(6,7);
	
	@Before
	public void setUpBeforeClass() throws Exception {
		eventIn = new Event(eventInPos, eventInName);
		eventOut = new Event(eventOutPos, eventOutName);
		conditional = new Conditional(eventIn, eventOut);
	}

	@Test
	public void testConditional() {
		assertNotNull(conditional);
	}

	@Test
	public void testGetType(){
		assertTrue(conditional.getType() == Type.Conditional);
	}
}
