package datamodel.petri.tests.UnitTests;

import static org.junit.Assert.*;

import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

import datamodel.Position;
import datamodel.petri.Petrinet;
import datamodel.petri.Transition;

public class PetrinetUnitTests {

	private static final String testname = "Test";
	
	private static final String testname2 = "Test2";
	
	private static final String testname3 = "Test3";
	
	private static final String testname4 = "Test4";
	
	private static Petrinet petrinet;
	
	private static Petrinet petrinet2;
	
	private static Petrinet petrinet3;
	
	private static Petrinet petrinet4;
	
	@Before
	public void setUpBefore() throws Exception {
		petrinet = new Petrinet(testname);
		petrinet2 = new Petrinet(testname2);
		petrinet3 = new Petrinet(testname3);
		petrinet4 = new Petrinet(testname4);
		
		
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
	public void GetPossibleActionsOneAction(){
		Position pos = new Position(1,1);
		
		int start = petrinet3.getStart().getID();
		int end = petrinet3.getEnd().getID();
		
		int t1 = petrinet3.addTransition(pos, "A");
		
		try {
			petrinet3.addArc(start, t1);
			petrinet3.addArc(t1, end);
		} catch(Exception e) {
			return;
		}
		List<Transition> possibleActions = petrinet3.getPossibleActions();
		
		assertTrue(possibleActions.get(0).getID() == t1);
	}
	
	@Test
	public void GetPossibleActionsTestTwoActions(){
		Position pos = new Position(1,1);
		
		int start = petrinet3.getStart().getID();
		int end = petrinet3.getEnd().getID();
		
		int t1 = petrinet3.addTransition(pos, "A");
		int t2 = petrinet3.addTransition(pos, "B");
		
		try {
			petrinet3.addArc(start, t1);
			petrinet3.addArc(start, t2);
			
			petrinet3.addArc(t1, end);
			petrinet3.addArc(t2, end);
		} catch(Exception e) {
			return;
		}
		
		List<Transition> possibleActions = petrinet3.getPossibleActions();

		
		assertTrue(possibleActions.size() == 2);
	}
	
	@Test
	public void GetPossibleActionsTestCompleteNet(){
		Position pos = new Position(1,1);
		
		int start = petrinet3.getStart().getID();
		int end = petrinet3.getEnd().getID();
		
		int t1 = petrinet3.addTransition(pos, "A");
		int t2 = petrinet3.addTransition(pos, "B");
		int t3 = petrinet3.addTransition(pos, "C");
		int t4 = petrinet3.addTransition(pos, "D");
		
		int p1 = petrinet3.addPlace(pos);
		int p2 = petrinet3.addPlace(pos);
		int p3 = petrinet3.addPlace(pos);
		int p4 = petrinet3.addPlace(pos);
		
		
		petrinet3.getStart().setToken(false);
		petrinet3.getPlaces().get(p4).setToken(true);
		petrinet3.getPlaces().get(p1).setToken(true);
		
		
		
		try {
			petrinet3.addArc(start, t1);
			
			petrinet3.addArc(t1, p1);
			petrinet3.addArc(t1, p2);
			
			petrinet3.addArc(p1, t2);
			petrinet3.addArc(p2, t3);
			
			petrinet3.addArc(t2, p3);
			petrinet3.addArc(t3, p4);
			
			petrinet3.addArc(p3, t4);
			petrinet3.addArc(p4, t4);
			
			
			petrinet3.addArc(t4, end);
		} catch(Exception e) {
			return;
		}
		petrinet3.getStart().setToken(false);
		
		List<Transition> possibleActions = petrinet3.getPossibleActions();
		
		assertTrue(possibleActions.size() == 1 &&
					possibleActions.get(0).getID() == t2);
	}
	
	@Test
	public void ExecuteActionTestOneAction(){
		Position pos = new Position(1,1);
		
		int start = petrinet4.getStart().getID();
		int end = petrinet4.getEnd().getID();
		
		int t1 = petrinet4.addTransition(pos, "A");
		
		try {
			petrinet4.addArc(start, t1);
			petrinet4.addArc(t1, end);
		} catch(Exception e) {
			return;
		}
		
		assertTrue(petrinet4.getStart().hasToken());
		try {
			petrinet4.executeAction(petrinet4.getTransition(t1));
		} catch(Exception e) {
			return;
		}
		assertTrue(petrinet4.getEnd().hasToken());
	}
	
	@Test
	public void ExecuteActionTestTwoActions(){
		Position pos = new Position(1,1);
		
		int start = petrinet4.getStart().getID();
		int end = petrinet4.getEnd().getID();
		
		int t1 = petrinet4.addTransition(pos, "A");
		int t2 = petrinet4.addTransition(pos, "B");
		
		try {
			petrinet4.addArc(start, t1);
			petrinet4.addArc(start, t2);
			
			petrinet4.addArc(t1, end);
			petrinet4.addArc(t2, end);
		} catch(Exception e) {
			return;
		}
		
		assertTrue(petrinet4.getStart().hasToken());
		try {
			petrinet4.executeAction(petrinet4.getTransition(t2));
		} catch (Exception e) {
			return;
		}
		assertTrue(petrinet4.getEnd().hasToken());
		
	}
	
	@Test
	public void ExecuteActionTestFullNet(){
		Position pos = new Position(1,1);
		
		int start = petrinet4.getStart().getID();
		int end = petrinet4.getEnd().getID();
		
		int t1 = petrinet4.addTransition(pos, "A");
		int t2 = petrinet4.addTransition(pos, "B");
		int t3 = petrinet4.addTransition(pos, "C");
		int t4 = petrinet4.addTransition(pos, "D");
		
		int p1 = petrinet4.addPlace(pos);
		int p2 = petrinet4.addPlace(pos);
		int p3 = petrinet4.addPlace(pos);
		int p4 = petrinet4.addPlace(pos);
		
		
		try {
			petrinet4.addArc(start, t1);
			
			petrinet4.addArc(t1, p1);
			petrinet4.addArc(t1, p2);
			
			petrinet4.addArc(p1, t2);
			petrinet4.addArc(p2, t3);
			
			petrinet4.addArc(t2, p3);
			petrinet4.addArc(t3, p4);
			
			petrinet4.addArc(p3, t4);
			petrinet4.addArc(p4, t4);
			
			
			petrinet4.addArc(t4, end);
		} catch(Exception e) {
			return;
		}
		
		assertTrue(petrinet4.getStart().hasToken());
		
		try {
			petrinet4.executeAction(petrinet4.getTransition(t1));
		} catch(Exception e) {
			return;
		}
		assertTrue(petrinet4.getPlaces().get(p1).hasToken() &&
				petrinet4.getPlaces().get(p2).hasToken());
		
		try {
			petrinet4.executeAction(petrinet4.getTransition(t2));
		} catch(Exception e) {
			return;
		}
		assertTrue(petrinet4.getPlaces().get(p3).hasToken() &&
				petrinet4.getPlaces().get(p2).hasToken());
		
		try {
			petrinet4.executeAction(petrinet4.getTransition(t3));
		} catch(Exception e) {
			return;
		}
		assertTrue(petrinet4.getPlaces().get(p3).hasToken() &&
				petrinet4.getPlaces().get(p4).hasToken());
		
		try {
			petrinet4.executeAction(petrinet4.getTransition(t4));
		} catch(Exception e) {
			return;
		}
		assertTrue(petrinet4.getEnd().hasToken());
	}
	
	@Test
	public void ExecuteActionTestIllegalActiont(){
		Position pos = new Position(1,1);
		
		int start = petrinet4.getStart().getID();
		int end = petrinet4.getEnd().getID();
		
		int t1 = petrinet4.addTransition(pos, "A");
		int t2 = petrinet4.addTransition(pos, "B");
		
		int p1 = petrinet4.addPlace(pos);
		
		
		try {
			petrinet4.addArc(start, t1);
			petrinet4.addArc(t1, p1);
			petrinet4.addArc(p1, t2);
			petrinet4.addArc(t2, end);
		} catch(Exception e) {
			return;
		}
		try {
			petrinet4.executeAction(petrinet4.getTransition(t2));
			fail("Expected exception did not occur");
		} catch(Exception e) {
			assertTrue(true);
		}
		
	}
	
	@Test
	public void DrawTest(){
		
	}
	

}
