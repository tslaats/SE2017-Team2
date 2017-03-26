package gui.tests.IntegrationTests;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import datamodel.Graph;
import datamodel.Position;
import datamodel.petri.Petrinet;
import gui.GuiController;
import datamodel.cr.CrGraph;
import datamodel.petri.Place;
import datamodel.petri.Transition;

public class GuiControllerTests {

	private static final String testname = "Test";
	
	private static gui.GuiController controller; 

	@Before
	public static void setUpBefore() throws Exception {
		controller = new gui.GuiController();
	}




	@Test
	public void CreatePetrinetTest_getName() {
		
		Graph testgraph = new Petrinet(testname);
		
		try{
			int graphid = controller.createGraph(testname, Graph.GraphTypes.PETRI);
			
			
			assertTrue(controller.getGraphs().get(graphid).getName() == testgraph.getName());
			
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
			
			controller.deleteCrGraph(graphid);
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
			List<Place> places = new ArrayList<>();
			places = (List<Place>) petrinet.getPlaceValues();
			
			
			
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
			places = (List<Place>) petrinet.getPlaceValues();
			
			int placeid = places.get(1).getID();
			
			controller.changePlaceToken(placeid, true);
			
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
			
			controller.changePlaceToken(1, true);
			
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

			List<Transition> transitions = new ArrayList<>();
			
			Petrinet petrinet = (Petrinet) controller.getActiveGraph();
			
			transitions = (List<Transition>) petrinet.getTransitionValues();
			
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

				List<Place> places = new ArrayList<>();
				
				Petrinet petrinet = (Petrinet) controller.getActiveGraph();
				
				places = (List<Place>) petrinet.getPlaceValues();
				
				Place place1 = places.get(0);
				Place place2 = places.get(1);
				
				controller.createArc(place1.getID(), place2.getID());
				
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

				List<Place> places = new ArrayList<>();
				
				Petrinet petrinet = (Petrinet) controller.getActiveGraph();
				
				places = (List<Place>) petrinet.getPlaceValues();
				
				Place place1 = places.get(0);
				Place place2 = places.get(1);
				
				controller.createArc(place1.getID(), place2.getID());
				
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
		public void testCreateEventPositionString(){
			fail();
		}
		
		@Test
		public void testCreateEventPositionStringNotCRGraph(){
			fail();
		}
		
		@Test
		public void testCreateEventIsPending(){
			fail();
		}
		
		@Test
		public void testCreateEventIsPendingNotCRGraph(){
			fail();
		}
		
		@Test
		public void testDeleteEvent(){
			fail();
		}
		
		@Test
		public void testDeleteEventNotCrGraph(){
			fail();
		}
		
		@Test
		public void testCreateResponse(){
			fail();
		}
		
		@Test
		public void testCreateResponseNotCrGraph(){
			fail();
		}
		
		@Test
		public void testDeleteReponse(){
			fail();
		}
		
		@Test
		public void testDeleteReponseNotCrGraph(){
			fail();
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
