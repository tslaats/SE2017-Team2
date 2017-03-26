package datamodel.cr.tests.IntegrationTests;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import datamodel.Position;
import datamodel.cr.Conditional;
import datamodel.cr.Event;

public class ConditionalIntegrationTests {

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
	public void testConditionalGetIn() {
		assertTrue(conditional.getIn() == eventIn);
	}
	
	@Test
	public void testConditionalGetOut() {
		assertTrue(conditional.getOut() == eventOut);
	}

}
