package gui.tests.IntegrationTests;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import datamodel.Graph;
import datamodel.Graph.GraphTypes;
import datamodel.Position;
import datamodel.petri.Petrinet;
import gui.GuiController;
import datamodel.cr.CrGraph;
import datamodel.cr.CrObject;
import datamodel.cr.Event;
import datamodel.cr.Response;
import datamodel.petri.Place;
import datamodel.petri.Transition;

public class GuiControllerTests {

	private static final String testname = "Test";
	
	private static gui.GuiController controller; 
	
	private int crgraphid;
	
	private int petriid;

	@Before
	public void setUpBefore() throws Exception {
		controller = new gui.GuiController();
		crgraphid = controller.createGraph(testname, GraphTypes.CR);
		petriid = controller.createGraph(testname, GraphTypes.PETRI);
	
	}




	@Test
	public void CreatePetrinetTest_getName() {
		
		try{
			int graphid = controller.createGraph(testname, Graph.GraphTypes.PETRI);
			
			
			assertTrue(controller.getGraphs().get(graphid).getName() == testname);
			
		} catch(Exception e){
			e.printStackTrace();
			fail();
		}
	}
	
	@Test
	public void CreatePetrinetTest_getGraphType() {
		
		try{
			int graphid = controller.createGraph(testname, Graph.GraphTypes.PETRI);
			
			
			assertTrue(controller.getGraphs().get(graphid).getGraphType() == Graph.GraphTypes.PETRI);
			
		} catch(Exception e){
			e.printStackTrace();
			fail();
		}
	}
	
	@Test
	public void CreateCRGraphTest_Name(){
		Graph testgraph = new CrGraph(testname);
		
		try{
			int graphid = controller.createGraph(testname, Graph.GraphTypes.CR);
			
			
			assertTrue(controller.getGraphs().get(graphid).getName() == testgraph.getName());
			
		} catch(Exception e){
			e.printStackTrace();
			fail();
		}
	}
	
	@Test
	public void CreateCRGraphTest_GraphType(){
		
		try{
			int graphid = controller.createGraph(testname, Graph.GraphTypes.CR);
			
			
			assertTrue(controller.getGraphs().get(graphid).getGraphType() == Graph.GraphTypes.CR);
			
		} catch(Exception e){
			e.printStackTrace();
			fail();
		}
	}
	
	@Test 
	public void CreateNullGraphTest(){
		
		try{
			controller.createGraph(testname, null);
			fail();
		} catch (Exception e){
			return;
		}
	}

	
	@Test
	public void DeleteCrGraphTest(){
		int graphid;
		try{
			graphid = controller.createGraph(testname, Graph.GraphTypes.CR);
			
			controller.deleteGraph(graphid);
		} catch (Exception e){
			e.printStackTrace();
			fail();
			return;
		}
		
		assertNull(controller.getGraphs().get(graphid));
	}
	
	@Test 
	public void testIsActiveGraphPetriPositive() {
		int graphid;
		try{
			graphid = controller.createGraph(testname, Graph.GraphTypes.PETRI);
			GuiController.ActiveGraphID = graphid;
			assertTrue(controller.isActiveGraphPetri());
		} catch (Exception e){
			e.printStackTrace();
			fail();
		}
		
		
	}
	
	@Test 
	public void testIsActiveGraphPetriNegative() {
		int graphid;
		try{
			graphid = controller.createGraph(testname, Graph.GraphTypes.CR);
			GuiController.ActiveGraphID = graphid;
			assertTrue(!controller.isActiveGraphPetri());
		} catch (Exception e){
			e.printStackTrace();
			fail();
		}
		
		
	}
	
	@Test 
	public void testIsActiveGraphPetriDoesNotExist() {
		
		try{
			
			GuiController.ActiveGraphID = 500;
			controller.isActiveGraphPetri();
			fail();
		} catch (Exception e){
			return;
		}
		
		
	}
	
	@Test 
	public void testIsActiveGraphCRPositive() {
		int graphid;
		try{
			graphid = controller.createGraph(testname, Graph.GraphTypes.CR);
			GuiController.ActiveGraphID = graphid;
			assertTrue(controller.isActiveGraphCr());
		} catch (Exception e){
			e.printStackTrace();
			fail();
		}
		
		
	}
	
	@Test 
	public void testIsActiveGraphCRNegative() {
		int graphid;
		try{
			graphid = controller.createGraph(testname, Graph.GraphTypes.PETRI);
			GuiController.ActiveGraphID = graphid;
			assertTrue(!controller.isActiveGraphCr());
		} catch (Exception e){
			e.printStackTrace();
			fail();
		}
		
		
	}
	
	@Test 
	public void testIsActiveGraphCRDoesNotExist() {
		
		try{
			
			GuiController.ActiveGraphID = 500;
			controller.isActiveGraphCr();
			fail();
		} catch (Exception e){
			return;
		}
	}
	
	
	@Test 
	public void testCreatePlace() {
		
		try{
			Position pos = new Position(2,5);
			int graphid = controller.createGraph(testname,  Graph.GraphTypes.PETRI);
			GuiController.ActiveGraphID = graphid;
			controller.createPlace(pos);
			Petrinet petrinet = (Petrinet)controller.getActiveGraph();
			
			assertTrue(petrinet.getPlaceValues().size() == 3);
		} catch (Exception e){
			e.printStackTrace();
			fail();
		}
		
		
	}
	
	@Test 
	public void testCreatePlaceActiveNotPetri() {
		
		try{
			Position pos = new Position(2,5);
			controller.createGraph(testname, Graph.GraphTypes.CR);
			GuiController.ActiveGraphID = 500;
			controller.createPlace(pos);
			fail();
		} catch (Exception e){
			return;
		}
		
		
	}
	
	@Test 
	public void testDeletePlace() {
		
		try{
			Position pos = new Position(2,5);
			int graphid = controller.createGraph(testname,  Graph.GraphTypes.PETRI);
			GuiController.ActiveGraphID = graphid;
			controller.createPlace(pos);
			Petrinet petrinet = (Petrinet)controller.getActiveGraph();
			ArrayList<Place> places = new ArrayList<Place>( petrinet.getPlaceValues());
			
			
			
			int placeid = places.get(2).getID();
			controller.deletePlace(placeid);
			
			assertTrue(petrinet.getPlaceValues().size() == 2);
		} catch (Exception e){
			e.printStackTrace();
			fail();
		}
		
		
	}
	
	@Test 
	public void testDeletePlaceNotPetri() {
		
		try{
			int graphid = controller.createGraph(testname, Graph.GraphTypes.CR);
			
			GuiController.ActiveGraphID = graphid;
			
			controller.deletePlace(graphid + 1);
			
			fail();
		} catch (Exception e){
			return;
		}
		
	}
	
	@Test 
	public void testChangePlaceToken(){
		
		try{
			int graphid = controller.createGraph(testname,  Graph.GraphTypes.PETRI);
				
			GuiController.ActiveGraphID = graphid;
			
			Petrinet petrinet = (Petrinet)controller.getActiveGraph();
			List<Place> places = new ArrayList<>();
			places = new ArrayList<Place>( petrinet.getPlaceValues());
			
			int placeid = places.get(1).getID();
			
			controller.changePlaceToken(placeid, true);
			

			places = new ArrayList<Place> (petrinet.getPlaceValues());
			
			
			assertTrue(places.get(1).hasToken());
		} catch(Exception e){
			e.printStackTrace();
			fail();
		}
		
	}
	
	@Test 
	public void testChangePlaceTokenNotCR(){
		
		try{
			int graphid = controller.createGraph(testname,  Graph.GraphTypes.CR);
				
			GuiController.ActiveGraphID = graphid;
			
			controller.changePlaceToken(graphid, true);
			
			fail();
		} catch(Exception e){
			return;
		}
		
	}
	
	@Test 
	public void testcreateTransition(){
		
		try{
			Position pos = new Position(5,5);
			int graphid = controller.createGraph(testname,  Graph.GraphTypes.PETRI);
				
			GuiController.ActiveGraphID = graphid;
			
			controller.createTransition(pos, testname);

			Petrinet petrinet = (Petrinet) controller.getActiveGraph();
			
			ArrayList<Transition> transitions = new ArrayList<Transition>( petrinet.getTransitionValues());
			
			assertTrue(!transitions.isEmpty());
			
		} catch(Exception e){
			e.printStackTrace();
			fail();
		}
		
	}
	
	@Test 
	public void testcreateTransitionNotPetri(){
		
		try{
			int graphid = controller.createGraph(testname,  Graph.GraphTypes.CR);
				
			GuiController.ActiveGraphID = graphid;
			
			controller.createTransition(null, null);
			
			fail();
		} catch(Exception e){
			return;
		}
	}
		
		
		@Test 
		public void testcreateArcIncoming(){
			
			try{
				Position pos = new Position(5,5);
				int graphid = controller.createGraph(testname,  Graph.GraphTypes.PETRI);
					
				GuiController.ActiveGraphID = graphid;
				
				controller.createTransition(pos, testname);

				Petrinet petrinet = (Petrinet) controller.getActiveGraph();
				
				ArrayList<Place> places = new ArrayList<Place>(petrinet.getPlaceValues());
				
				Place place1 = places.get(0);
				Place place2 = places.get(1);
				
				controller.createArc(place1.getID(), place2.getID());
				
				places = new ArrayList<Place>( petrinet.getPlaceValues());
				
				assertTrue(place2.getIncoming().containsKey(place1));
				
			} catch(Exception e){
				e.printStackTrace();
				fail();
			}
			
		}
		
		@Test 
		public void testcreateArcOutgoing(){
			
			try{
				Position pos = new Position(5,5);
				int graphid = controller.createGraph(testname,  Graph.GraphTypes.PETRI);
					
				GuiController.ActiveGraphID = graphid;
				
				controller.createTransition(pos, testname);

				Petrinet petrinet = (Petrinet) controller.getActiveGraph();
				
				ArrayList<Place> places = new ArrayList<Place>( petrinet.getPlaceValues());
				
				Place place1 = places.get(0);
				Place place2 = places.get(1);
				
				controller.createArc(place1.getID(), place2.getID());
				places = new ArrayList<Place>( petrinet.getPlaceValues());
				
				assertTrue(place1.getOutgoing().containsValue(place2));
				
			} catch(Exception e){
				e.printStackTrace();
				fail();
			}
			
		}
		
		@Test 
		public void testcreateArcNotPetri(){
			
			try{
				int graphid = controller.createGraph(testname,  Graph.GraphTypes.CR);
					
				GuiController.ActiveGraphID = graphid;
				
				controller.createArc(-1, -1);
				
				fail();
			} catch(Exception e){
				return;
			}
			
						
		}
	
		@Test
		public void testCreateEventPositionStringPos(){
			try{
				int graphid = controller.createGraph(testname,  Graph.GraphTypes.CR);
				Position pos = new Position(3,5);
				GuiController.ActiveGraphID = graphid;
				
				controller.createEvent(pos, testname);
				
				CrGraph crgraph = (CrGraph) controller.getActiveGraph();
				
				ArrayList<Event> events = new ArrayList<Event>( crgraph.getAllEvents());

				assertTrue(events.get(0).getPosition() == pos);
				
			} catch(Exception e){
				e.printStackTrace();
				fail();
			}
		}
		
		@Test
		public void testCreateEventPositionStringName(){
			try{
				int graphid = controller.createGraph(testname,  Graph.GraphTypes.CR);
				Position pos = new Position(3,5);
				GuiController.ActiveGraphID = graphid;
				
				controller.createEvent(pos, testname);
				
				CrGraph crgraph = (CrGraph) controller.getActiveGraph();
				
				ArrayList<Event> events = new ArrayList<Event>( crgraph.getAllEvents());

				assertTrue(events.get(0).getName() == testname);
				
			} catch(Exception e){
				e.printStackTrace();
				fail();
			}
		}
		
		@Test
		public void testCreateEventPositionStringNotCRGraph(){
			try{
				GuiController.ActiveGraphID = petriid;
				
				controller.createEvent(null, testname);
				
				fail();
				
			} catch(Exception e){
				return;

			}
		}
		
		@Test
		public void testCreateEventIsPendingName(){
			try{
				int graphid = controller.createGraph(testname,  Graph.GraphTypes.CR);
				Position pos = new Position(3,5);
				GuiController.ActiveGraphID = graphid;
				
				controller.createEvent(pos, testname, true);
				
				CrGraph crgraph = (CrGraph) controller.getActiveGraph();
				
				ArrayList<Event> events = new ArrayList<Event>( crgraph.getAllEvents());

				assertTrue(events.get(0).getName() == testname);
				
			} catch(Exception e){
				e.printStackTrace();
				fail();
			}
		}
		
		@Test
		public void testCreateEventIsPendingPosition(){
			try{
				int graphid = controller.createGraph(testname,  Graph.GraphTypes.CR);
				Position pos = new Position(3,5);
				GuiController.ActiveGraphID = graphid;
				
				controller.createEvent(pos, testname, true);
				
				CrGraph crgraph = (CrGraph) controller.getActiveGraph();
				
				ArrayList<Event> events = new ArrayList<Event>( crgraph.getAllEvents());

				assertTrue(events.get(0).getPosition() == pos);
				
			} catch(Exception e){
				e.printStackTrace();
				fail();
			}
		}
		
		@Test
		public void testCreateEventIsPendingIsPendingTrue(){
			try{
				int graphid = controller.createGraph(testname,  Graph.GraphTypes.CR);
				Position pos = new Position(3,5);
				GuiController.ActiveGraphID = graphid;
				
				controller.createEvent(pos, testname, true);
				
				CrGraph crgraph = (CrGraph) controller.getActiveGraph();
				
				ArrayList<Event> events = new ArrayList<Event>( crgraph.getAllEvents());

				assertTrue(events.get(0).isPending());
				
			} catch(Exception e){
				e.printStackTrace();
				fail();
			}
		}
		
		@Test
		public void testCreateEventIsPendingIsPendingFalse(){
			try{
				int graphid = controller.createGraph(testname,  Graph.GraphTypes.CR);
				Position pos = new Position(3,5);
				GuiController.ActiveGraphID = graphid;
				
				controller.createEvent(pos, testname, false);
				
				CrGraph crgraph = (CrGraph) controller.getActiveGraph();
				
				ArrayList<Event> events = new ArrayList<Event>( crgraph.getAllEvents());

				assertTrue(!events.get(0).isPending());
				
			} catch(Exception e){
				e.printStackTrace();
				fail();
			}
		}
		
		@Test
		public void testCreateEventIsPendingNotCRGraph(){
			try{
				
				int graphid = controller.createGraph(testname,  Graph.GraphTypes.PETRI);
			
				GuiController.ActiveGraphID = graphid;
				
				controller.createEvent(null, testname, true);
				
				fail();
				
			} catch(Exception e){
				return;

			}
		}
		
		@Test
		public void testDeleteEvent(){
			try{
				int graphid = controller.createGraph(testname,  Graph.GraphTypes.CR);
				Position pos = new Position(3,5);
				GuiController.ActiveGraphID = graphid;
				
				controller.createEvent(pos, testname);
				
				CrGraph crgraph = (CrGraph) controller.getActiveGraph();
				
				ArrayList<Event> events = new ArrayList<Event>( crgraph.getAllEvents());

				Event event = events.get(0);
				
				controller.deleteEvent(event.getID());
				
				assertTrue(crgraph.getAllEvents().isEmpty());
				
			} catch(Exception e){
				e.printStackTrace();
				fail();
			}
		}
		
		@Test
		public void testDeleteEventNotCrGraph(){
			try{
				
				int graphid = controller.createGraph(testname,  Graph.GraphTypes.PETRI);
			
				GuiController.ActiveGraphID = graphid;
				
				controller.deleteEvent(0);
				
				fail();
				
			} catch(Exception e){
				return;

			}
		}
		
		@Test
		public void testCreateResponse(){
			try{
				GuiController.ActiveGraphID = crgraphid;
				
				Position pos1 = new Position(4,5);
				
				Position pos2 = new Position(5,6);
				
				Event event1 = new Event(pos1, testname);
				Event event2 = new Event(pos2, testname);
				
				CrGraph crgraph = (CrGraph) controller.getActiveGraph();
				
				crgraph.addEvent(event1);
				crgraph.addEvent(event2);
				
				controller.createResponse(event1.getID(), event2.getID());
				
			} catch(Exception e){
				e.printStackTrace();
				fail();
			}
		}
		
		@Test
		public void testCreateResponseNotCrGraph(){
			try{
				GuiController.ActiveGraphID = petriid;
				
				controller.createResponse(-1, -1);
				
				fail();
			} catch(Exception e){
				return;
			}
		}
		
		@Test
		public void testDeleteReponse(){
			try{
				GuiController.ActiveGraphID = crgraphid;
				
				Position pos1 = new Position(4,5);
				
				Position pos2 = new Position(5,6);
				
				Event event1 = new Event(pos1, testname);
				Event event2 = new Event(pos2, testname);
				
				CrGraph crgraph = (CrGraph) controller.getActiveGraph();
				
				crgraph.addEvent(event1);
				crgraph.addEvent(event2);
				
				
				controller.createResponse(event1.getID(), event2.getID());
				
				int responseid = -1;
				ArrayList<CrObject> crobjects = new ArrayList<CrObject>(crgraph.getCrObjectValues());
				
				for(int i = 0; i < crobjects.size(); i++){
					try{
						Response response = (Response) crobjects.get(i);
						responseid = response.getID();
					} catch(Exception e){
						
					}
				}
				
				
				controller.deleteResponse(responseid);
				
				assertNull(crgraph.getCrObjects().get(responseid));
				
			} catch(Exception e){
				e.printStackTrace();
				fail();
			}
		}
		
		@Test
		public void testDeleteReponseNotCrGraph(){
			try{
				GuiController.ActiveGraphID = petriid;
				
				controller.deleteResponse(-1);
				
				fail();
			}catch(Exception e){
				return;
			}
		}
		
		
		@Test
		public void testCreateCondition(){
			fail();
		}
		
		@Test
		public void testCreateConditionNotCRGraph(){
			fail();
		}
		
		@Test
		public void testDeleteCondition(){
			fail();
		}
		
		@Test
		public void testDeleteConditionNotCRGraph(){
			fail();
		}
		
		@Test
		public void testGetActiveGraph(){
			fail();
		}
		
		@Test
		public void testGetActiveGraphDoesNotExist(){
			fail();
		}
		
		@Test
		public void testDraw(){
			fail();
		}
		
		@Test
		public void testDrawIDDoesNotExist(){
			fail();
		}
		
		@Test
		public void testGetPossibleActions(){
			fail();
		}
		
		@Test
		public void testGetPossibleActionsIDDoesNotExist(){
			fail();
		}
		
		
}
