package gui.tests.unitTests;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import datamodel.Graph;
import gui.GuiController;

public class GuiControllerUnitTests {

	private static GuiController controller;
	
	@Before
	public void setUp() throws Exception {
		controller = new GuiController();
	}

	@Test
	public void testGraphs() {
		assertNotNull(controller.getGraphs());
	}
	
	@Test 
	public void testActiveGraphID() {
		GuiController.ActiveGraphID = 500;
		assertTrue(GuiController.ActiveGraphID == 500);
	}


}
