package datamodel.cr.tests;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import datamodel.Graph;
import datamodel.Position;
import datamodel.cr.CrGraph;
import datamodel.cr.Event;

public class CRTests {

	private static final String testname = "Test";
	
	private static CrGraph crgraph;
	
	@Before
	public void setUpBeforeClass() throws Exception {
		crgraph = new CrGraph(testname);
	}


	@Test
	public void testCrGraph() {
		assertTrue(crgraph.getGraphType() == Graph.GraphTypes.CR
				&& crgraph.getName() == testname);
	}

	@Test
	public void testAddEvent() {
		Position pos = new Position(3,5);
		
		int id = crgraph.addEvent(pos, testname);
		
		Event theevent = (Event)crgraph.getCrObjects().get(id);
		
		assertTrue(theevent.getX() == 3
				&& theevent.getY() == 5
				&& theevent.getName() == testname
				&& !theevent.isExecuted());
	}

	@Test
	public void testDeleteEvent() {
		Position pos = new Position(3,5);
		
		int id = crgraph.addEvent(pos, testname);
		
		try{
			crgraph.deleteEvent(id);
			assertNull(crgraph.getCrObjects().get(id));
		} catch (Exception e){
			e.printStackTrace();
			fail();
		}
	}

	@Test
	public void testAddCondition() {
		Position pos1 = new Position(3,5);
		Position pos2 = new Position(6, 8);
		
		int incomingID = crgraph.addEvent(pos1, testname);
		int outgoingID = crgraph.addEvent(pos2, testname);
		
		int conditionID = -1;
		
		try{
			conditionID = crgraph.addCondition(incomingID, outgoingID);
		} catch (Exception e){
			e.printStackTrace();
			fail();
		}
		
		
		assertNotNull(crgraph.getCrObjects().get(conditionID));
	}

	@Test
	public void testAddConditionSameID(){
		Position pos1 = new Position(3,5);
		
		int eventID = crgraph.addEvent(pos1, testname);
		
		try{
			crgraph.addCondition(eventID, eventID);
		} catch(Exception e){
			assertTrue(true);
			return;
		}
		
		fail();
	}
	
	@Test
	public void testAddConditionIncomingEventIdDoesntExist(){
		Position pos1 = new Position(3,5);
		
		int eventID = crgraph.addEvent(pos1, testname);
		
		try{
			crgraph.addCondition(eventID+1, eventID);
		} catch(Exception e){
			assertTrue(true);
			return;
		}
		
		fail();
	}
	
	@Test
	public void testAddConditionOutgoingEventIdDoesntExist(){
		Position pos1 = new Position(3,5);
		
		int eventID = crgraph.addEvent(pos1, testname);
		
		try{
			crgraph.addCondition(eventID, eventID+1);
		} catch(Exception e){
			assertTrue(true);
			return;
		}
		
		fail();
	}
	
	@Test
	public void testDeleteCondition() {
		Position pos1 = new Position(3,5);
		Position pos2 = new Position(6, 8);
		
		int incomingID = crgraph.addEvent(pos1, testname);
		int outgoingID = crgraph.addEvent(pos2, testname);
		
		int conditionID = -1;
		
		try{
			conditionID = crgraph.addCondition(incomingID, outgoingID);
			crgraph.deleteCondition(conditionID);
		} catch (Exception e){
			e.printStackTrace();
			fail();
		}
		
		
		assertNull(crgraph.getCrObjects().get(conditionID));
	}
	
	@Test
	public void testDeleteConditionNoConditionWithThatID() {
		Position pos1 = new Position(3,5);
		Position pos2 = new Position(6, 8);
		
		int incomingID = crgraph.addEvent(pos1, testname);
		int outgoingID = crgraph.addEvent(pos2, testname);
		
		int conditionID = -1;
		
		try{
			conditionID = crgraph.addCondition(incomingID, outgoingID);
		} catch (Exception e){
			e.printStackTrace();
			fail();
			return;
		}
		
		try{
			crgraph.deleteCondition(conditionID+incomingID+outgoingID);
		} catch (Exception e){
			assertTrue(true);
		}
		
	}
	
	@Test
	public void testDeleteConditionIDNotCondition() {
		Position pos1 = new Position(3,5);
		Position pos2 = new Position(6, 8);
		
		int incomingID = crgraph.addEvent(pos1, testname);
		int outgoingID = crgraph.addEvent(pos2, testname);
		
		try{
			crgraph.addCondition(incomingID, outgoingID);
		} catch (Exception e){
			e.printStackTrace();
			fail();
			return;
		}
		
		try{
			crgraph.deleteCondition(incomingID);
		} catch (Exception e){
			assertTrue(true);
		}
		
	}

	@Test
	public void testAddResponse() {
		Position pos1 = new Position(3,5);
		Position pos2 = new Position(6, 8);
		
		int incomingID = crgraph.addEvent(pos1, testname);
		int outgoingID = crgraph.addEvent(pos2, testname);
		
		int responseID = -1;
		
		try{
			responseID = crgraph.addResponse(incomingID, outgoingID);
		} catch (Exception e){
			e.printStackTrace();
			fail();
		}
		
		
		assertNotNull(crgraph.getCrObjects().get(responseID));
	}
	
	@Test
	public void testAddResponseSameEventID() {
		Position pos1 = new Position(3,5);
		
		int eventID = crgraph.addEvent(pos1, testname);
		
		try{
			crgraph.addResponse(eventID, eventID);
		} catch (Exception e){
			return;
		}
		
		fail();
	}
	
	@Test
	public void testAddResponseSameIncomingDoesNotExist() {
		Position pos1 = new Position(3,5);
		
		int eventID = crgraph.addEvent(pos1, testname);
		
		try{
			crgraph.addResponse(999999999, eventID);
		} catch (Exception e){
			return;
		}
		
		fail();
	}
	
	@Test
	public void testAddResponseSameOutgoingDoesNotExist() {
		Position pos1 = new Position(3,5);
		
		int eventID = crgraph.addEvent(pos1, testname);
		
		try{
			crgraph.addResponse(eventID, 999999999);
		} catch (Exception e){
			return;
		}
		
		fail();
	}

	@Test
	public void testDeleteResponse() {
		Position pos1 = new Position(3,5);
		Position pos2 = new Position(6, 8);
		
		int incomingID = crgraph.addEvent(pos1, testname);
		int outgoingID = crgraph.addEvent(pos2, testname);
		
		int responseID = -1;
		
		try{
			responseID = crgraph.addResponse(incomingID, outgoingID);
			crgraph.deleteResponse(responseID);
		} catch (Exception e){
			e.printStackTrace();
			fail();
		}
		
		assertNull(crgraph.getCrObjects().get(responseID));
	}
	
	@Test
	public void testDeleteResponseNoID() {
		Position pos1 = new Position(3,5);
		Position pos2 = new Position(6, 8);
		
		int incomingID = crgraph.addEvent(pos1, testname);
		int outgoingID = crgraph.addEvent(pos2, testname);
		
		int responseID = -1;
		
		try{
			responseID = crgraph.addResponse(incomingID, outgoingID);
			
		} catch (Exception e){
			e.printStackTrace();
			fail();
		}

		try{
			crgraph.deleteResponse(responseID+incomingID+outgoingID);
		} catch(Exception e){
			return;
		}
		
		fail();
	}
	
	@Test
	public void testDeleteResponseIDNotAReponse() {
		Position pos1 = new Position(3,5);
		Position pos2 = new Position(6, 8);
		
		int incomingID = crgraph.addEvent(pos1, testname);
		int outgoingID = crgraph.addEvent(pos2, testname);
		
		int responseID = -1;
		
		try{
			responseID = crgraph.addResponse(incomingID, outgoingID);
			
		} catch (Exception e){
			e.printStackTrace();
			fail();
		}

		try{
			crgraph.deleteResponse(incomingID);
		} catch(Exception e){
			return;
		}
		
		fail();
	}

	@Test
	public void testGetPendingEvents() {
		Position pos1 = new Position(3,5);
		Position pos2 = new Position(5,7);
		
		Event event1 = new Event(pos1, testname);
		Event event2 = new Event(pos2, testname);
		
		event1.setPending(true);
		
		event2.setPending(false);
		
		crgraph.addEvent(event1);
		crgraph.addEvent(event2);
		
		
		
		List<Event> returnlist = new ArrayList<>();
		returnlist = crgraph.getPendingEvents();
		
		assertTrue(returnlist.contains(event1)
				&& !returnlist.contains(event2));
	}
	
	@Test
	public void testGetAllEvents() {
		Position pos1 = new Position(3,5);
		Position pos2 = new Position(5,7);
		
		Event event1 = new Event(pos1, testname);
		Event event2 = new Event(pos2, testname);
		
		event1.setPending(true);
		
		event2.setPending(false);
		
		crgraph.addEvent(event1);
		crgraph.addEvent(event2);
		
		
		
		List<Event> returnlist = new ArrayList<>();
		returnlist = crgraph.getAllEvents();
		
		assertTrue(returnlist.contains(event1)
				&& returnlist.contains(event2));
	}

	

}
