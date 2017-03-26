package datamodel.petri.tests.UnitTests;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import datamodel.Position;
import datamodel.petri.Petrinet;

public class PetriUnitTests {

	private static final String testname = "Test";
	
	private static Petrinet petrinet;
	
	@Before
	public void setUpBefore() throws Exception {
		petrinet = new Petrinet(testname);
	}

	@Test
	public void PetriConstructorTest_Name() {
		
		
		assertTrue(petrinet.getName() == testname);
		
	}
	
	
	@Test 
	public void AddPlaceTest(){
		
		Position pos = new Position(2,3);
		
		int id = petrinet.addPlace(pos);
		
		assertNotNull(petrinet.getPlaces().get(id));
	}

	
	
	@Test
	public void DeletePlaceTest(){
		
		Position pos = new Position(2,3);
		
		int id = petrinet.addPlace(pos);
		
		try{
			petrinet.deletePlace(id);
			assertNull(petrinet.getPlaces().get(id));
		}catch (Exception e){
			e.printStackTrace();
			fail();
		}
		
	}
	
	@Test
	public void DeletePlaceDoesNotContainKey(){
		
		Position pos = new Position(2,3);
		
		petrinet.addPlace(pos);
		
		try{
			petrinet.deletePlace(999999999);
			fail();
		} catch(Exception e){
			return;
		}
		
	}
	
	@Test
	public void DeletePlaceCannotBeDeleted(){
		
		int id = petrinet.getStart().getID();
		
		try{
			petrinet.deletePlace(id);
			fail();
		} catch(Exception e){
			return;
		}
	}
	
	@Test
	public void AddTransitionTest(){
		
		
		Position pos = new Position(5,6);
		
		int id = petrinet.addTransition(pos, testname);
				
		assertNotNull(petrinet.getTransitions().get(id));
	}
	
	@Test
	public void DeleteTransitionTest(){
		
		
		Position pos = new Position (5,5);
		
		int id = petrinet.addTransition(pos, testname);
		
		try{
			petrinet.deleteTranistion(id);
			assertNull(petrinet.getTransitions().get(id));
		} catch (Exception e){
			e.printStackTrace();
			fail();
		}
		
		
	}
	
	@Test
	public void DeleteTransitionDoesNotContainKey(){
		
		
		try{
			petrinet.deleteTranistion(99999999);
			fail();
		} catch (Exception e){
			return;
		}
	}
	
	@Test
	public void AddArcTranstoPlaceOutgoing(){
		
		
		Position pos1 = new Position(5,5);
		Position pos2 = new Position(7,7);
		int placeID = petrinet.addPlace(pos1);
		int transID = petrinet.addTransition(pos2, testname);
		
		try{
			petrinet.addArc(transID, placeID);
		} catch (Exception e){
			e.printStackTrace();
			fail();
		}
		
		assertTrue(petrinet.getTransitions().get(transID).getOutgoing().get(placeID).getID() == placeID);
	}
	
	@Test
	public void AddArcTranstoPlaceIncoming(){
		
		
		Position pos1 = new Position(5,5);
		Position pos2 = new Position(7,7);
		int placeID = petrinet.addPlace(pos1);
		int transID = petrinet.addTransition(pos2, testname);
		
		try{
			petrinet.addArc(transID, placeID);
		} catch (Exception e){
			e.printStackTrace();
			fail();
		}
		
		assertTrue(petrinet.getPlaces().get(placeID).getIncoming().get(transID).getID() == transID);
	}
	
	@Test
	public void AddArcPlacetoTransOutgoing(){
		
		
		Position pos1 = new Position(5,5);
		Position pos2 = new Position(7,7);

		int placeID = petrinet.addPlace(pos1);
		int transID = petrinet.addTransition(pos2, testname);
		
		try{
			petrinet.addArc(placeID, transID);
		} catch(Exception e){
			e.printStackTrace();
			fail();
		}
		assertTrue(petrinet.getPlaces().get(placeID).getOutgoing().get(transID).getID() == transID);
	
	}
	
	@Test
	public void AddArcPlacetoTransIncoming(){
		
		
		Position pos1 = new Position(5,5);
		Position pos2 = new Position(7,7);

		int placeID = petrinet.addPlace(pos1);
		int transID = petrinet.addTransition(pos2, testname);
		
		try{
			petrinet.addArc(placeID, transID);
		} catch(Exception e){
			e.printStackTrace();
			fail();
		}
		assertTrue(petrinet.getTransitions().get(transID).getIncoming().get(placeID).getID() == placeID);
	
	}
	
	@Test
	public void AddArcNoMatch(){
		
		try{
			petrinet.addArc(-1, -2);
			fail();
		} catch (Exception e){
			
		}
	}
	
	@Test
	public void AddArcTtoT(){
		
		
		Position pos1 = new Position(5,5);
		Position pos2 = new Position(7,7);

		int transID1 = petrinet.addTransition(pos1, testname);
		int transID2 = petrinet.addTransition(pos2, testname);
		
		try{
			petrinet.addArc(transID1, transID2);
			fail();
		} catch (Exception e){
			
		}
	}
	
	@Test
	public void AddArcPtoP(){
		
		
		Position pos1 = new Position(5,5);
		Position pos2 = new Position(7,7);

		int placeID1 = petrinet.addPlace(pos1);
		int placeID2 = petrinet.addPlace(pos2);
		
		try{
			petrinet.addArc(placeID1, placeID2);
			fail();
		} catch (Exception e){

		}
	}
	
	@Test
	public void AddArcEndpointMismatch1(){
		
		
		Position pos1 = new Position(5,5);

		int placeID = petrinet.addPlace(pos1);
		
		try{
			petrinet.addArc(placeID, -2);
			fail();
		} catch (Exception e){

		}
	}
	
	@Test
	public void AddArcEndpointMismatch2(){
		
		
		Position pos1 = new Position(5,5);

		int placeID = petrinet.addPlace(pos1);
		
		try{
			petrinet.addArc(-2, placeID);
			fail();
		} catch (Exception e){

		}
	}
	
	@Test
	public void DeleteArcPtoT1(){
		
		
		Position pos1 = new Position(5,5);
		Position pos2 = new Position(7,7);

		int placeID = petrinet.addPlace(pos1);
		int transID = petrinet.addTransition(pos2, testname);
		
		try{
			petrinet.addArc(placeID, transID);
		} catch(Exception e){
			
		}
		
		try{
			petrinet.deleteArc(placeID, transID);
		} catch(Exception e){
			e.printStackTrace();
			fail();
		}
		
		assertNull(petrinet.getPlaces().get(placeID).getOutgoing().get(transID));
		
	}
	
	@Test
	public void DeleteArcPtoT2(){
		
		
		Position pos1 = new Position(5,5);
		Position pos2 = new Position(7,7);

		int placeID = petrinet.addPlace(pos1);
		int transID = petrinet.addTransition(pos2, testname);
		
		try{
			petrinet.addArc(placeID, transID);
		} catch(Exception e){
			
		}
		
		try{
			petrinet.deleteArc(placeID, transID);
		} catch(Exception e){
			e.printStackTrace();
			fail();
		}
		
		assertNull(petrinet.getTransitions().get(transID).getIncoming().get(placeID));
		
	}
	
	@Test
	public void DeleteArcTtoP1(){
		
		
		Position pos1 = new Position(5,5);
		Position pos2 = new Position(7,7);

		int placeID = petrinet.addPlace(pos1);
		int transID = petrinet.addTransition(pos2, testname);
		
		try{
			petrinet.addArc(transID, placeID);
		} catch(Exception e){
			
		}
		
		try{
			petrinet.deleteArc(transID, placeID);
		} catch(Exception e){
			e.printStackTrace();
			fail();
		}
		
		assertNull(petrinet.getTransitions().get(transID).getOutgoing().get(placeID));
		
	}
	
	@Test
	public void DeleteArcTtoP2(){
		
		
		Position pos1 = new Position(5,5);
		Position pos2 = new Position(7,7);

		int placeID = petrinet.addPlace(pos1);
		int transID = petrinet.addTransition(pos2, testname);
		
		try{
			petrinet.addArc(transID, placeID);
		} catch(Exception e){

		}
		
		try{
			petrinet.deleteArc(transID, placeID);
		} catch(Exception e){
			e.printStackTrace();
			fail();
		}
		
		assertNull(petrinet.getPlaces().get(placeID).getIncoming().get(placeID));
		
	}
	
	@Test
	public void DeleteArcNoMatch(){
		
		
		Position pos1 = new Position(5,5);
		Position pos2 = new Position(7,7);

		int placeID = petrinet.addPlace(pos1);
		int transID = petrinet.addTransition(pos2, testname);
		
		try{
			petrinet.addArc(transID, placeID);
		} catch(Exception e){

		}
		
		try{
			petrinet.deleteArc(999999, 999998);
			fail();
		} catch(Exception e){
			assertTrue(true);
			
		}
		
	}
	
	
	@Test
	public void GetPossibleActionsTest(){
		
	}
	
	@Test
	public void ExecuteActionTest(){
		
	}
	
	@Test
	public void DrawTest(){
		
	}
	

}
