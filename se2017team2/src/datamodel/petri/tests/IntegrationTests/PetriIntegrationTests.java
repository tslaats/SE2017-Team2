package datamodel.petri.tests.IntegrationTests;
import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import datamodel.Position;
import datamodel.petri.Petrinet;

public class PetriIntegrationTests {
	
	private static final String testname = "Test";
	
	private static Petrinet petrinet;
	
	@Before
	public void setUpBefore() throws Exception {
		petrinet = new Petrinet(testname);
	}
	
	@Test
	public void PetriConstructorTest_StartPosX() {
		
		
		assertTrue(petrinet.getStart().getPos().x() == 1);
		
	}
	
	@Test
	public void PetriConstructorTest_StartPosY() {
		
		
		assertTrue(petrinet.getStart().getPos().y() == 1);
		
	}
	
	@Test
	public void PetriConstructorTest_EndPosX() {
		
		assertTrue(petrinet.getEnd().getPos().x() == 10);
		
	}
	
	@Test
	public void PetriConstructorTest_EndPosY() {
		assertTrue(petrinet.getEnd().getPos().y() == 1);
		
	}
	
	@Test
	public void PetriConstructorTest_StartToken() {
		
		
		assertTrue(petrinet.getStart().hasToken());
		
	}
	
	@Test
	public void PetriConstructorTest_StartCanBeDeleted() {
		
		assertTrue(!petrinet.getStart().getCanBeDeleted());
		
	}
	
	@Test
	public void PetriConstructorTest_EndCanBeDeleted() {
		
		assertTrue(!petrinet.getEnd().getCanBeDeleted());
		
	}
	
	@Test
	public void PetriConstructorTest_PetriName() {
		
		assertTrue(petrinet.getName() == testname);
		
	}
	
	@Test 
	public void AddPlaceTest_Position(){
		
		Position pos = new Position(2,3);
		
		int id = petrinet.addPlace(pos);
		
		assertTrue(petrinet.getPlaces().get(id).getPos() == pos);
	}
	
	@Test 
	public void AddPlaceTest_CanBeDeleted(){
		
		Position pos = new Position(2,3);
		
		int id = petrinet.addPlace(pos);
		
		assertTrue(petrinet.getPlaces().get(id).getCanBeDeleted());
	}
	
	@Test
	public void AddTransitionTest_Position(){
		
		
		Position pos = new Position(5,6);
		
		int id = petrinet.addTransition(pos, testname);
				
		assertTrue(petrinet.getTransitions().get(id).getPos() == pos);
	}
	
	@Test
	public void AddTransitionTest_CanBeDeleted(){
		
		
		Position pos = new Position(5,6);
		
		int id = petrinet.addTransition(pos, testname);
				
		assertTrue(petrinet.getTransitions().get(id).getCanBeDeleted());
	}
}
