package datamodel.petri.tests.UnitTests;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import datamodel.Position;
import datamodel.petri.Petrinet;

public class PetrinetUnitTests {

	private static final String testname = "Test";
	
	private static final String testname2 = "Test2";
	
	private static Petrinet petrinet;
	
	private static Petrinet petrinet2;
	
	@Before
	public void setUpBefore() throws Exception {
		petrinet = new Petrinet(testname);
		petrinet2 = new Petrinet(testname2);
	}

	@Test
	public void testPetriConstructorName1() {
		
		
		assertTrue(petrinet.getName() == testname);
		
	}
	
	@Test
	public void testPetriConstructorName2() {
		
		
		assertTrue(petrinet2.getName() == testname2);
		
	}
	
	
	@Test 
	public void testAddPlace(){
		
		Position pos = new Position(2,3);
		
		int id = petrinet.addPlace(pos);
		
		assertNotNull(petrinet.getPlaces().get(id));
	}

	
	
	@Test
	public void testDeletePlace(){
		
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
	public void testDeletePlaceDoesNotContainKey(){
		
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
	public void testDeletePlaceCannotBeDeleted(){
		
		int id = petrinet.getStart().getID();
		
		try{
			petrinet.deletePlace(id);
			fail();
		} catch(Exception e){
			return;
		}
	}
	
	@Test
	public void testAddTransition(){
		
		
		Position pos = new Position(5,6);
		
		int id = petrinet.addTransition(pos, testname);
				
		assertNotNull(petrinet.getTransitions().get(id));
	}
	
	@Test
	public void testDeleteTransition(){
		
		
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
	public void testDeleteTransitionDoesNotContainKey(){
		
		
		try{
			petrinet.deleteTranistion(99999999);
			fail();
		} catch (Exception e){
			return;
		}
	}
	
	@Test
	public void testAddArcTranstoPlaceOutgoing(){
		
		
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
	public void testAddArcTranstoPlaceIncoming(){
		
		
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
	public void testAddArcPlacetoTransOutgoing(){
		
		
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
	public void testAddArcPlacetoTransIncoming(){
		
		
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
	public void testAddArcNoMatch(){
		
		try{
			petrinet.addArc(-1, -2);
			fail();
		} catch (Exception e){
			
		}
	}
	
	@Test
	public void testAddArcTtoT(){
		
		
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
	public void testAddArcPtoP(){
		
		
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
	public void testAddArcEndpointMismatch1(){
		
		
		Position pos1 = new Position(5,5);

		int placeID = petrinet.addPlace(pos1);
		
		try{
			petrinet.addArc(placeID, 99999999);
			fail();
		} catch (Exception e){

		}
	}
	
	@Test
	public void testAddArcEndpointMismatch2(){
		
		
		Position pos1 = new Position(5,5);

		int placeID = petrinet.addPlace(pos1);
		
		try{
			petrinet.addArc(99999999, placeID);
			fail();
		} catch (Exception e){

		}
	}
	
	@Test
	public void testDeleteArcPtoT1(){
		
		
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
	public void testDeleteArcPtoT2(){
		
		
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
	public void testDeleteArcTtoP1(){
		
		
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
	public void testDeleteArcTtoP2(){
		
		
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
	public void testDeleteArcNoMatch(){
		
		
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
			return;
			
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
