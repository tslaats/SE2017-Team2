package gui.tests.unitTests;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import datamodel.Graph;
import datamodel.Position;
import datamodel.petri.Petrinet;
import gui.GuiController;
import datamodel.cr.CrGraph;;

public class GuiControllerTests {

	private static final String testname = "Test";
	
	private static gui.GuiController controller = new gui.GuiController(); 
	
	private static int CRGraphID;
	
	private static int PetrinetID;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		CRGraphID = controller.CreateGraph(testname, Graph.GraphTypes.CR);
		PetrinetID = controller.CreateGraph(testname, Graph.GraphTypes.PETRI);
	}




	@Test
	public void CreatePetrinetTest() {
		
		Graph testgraph = new Petrinet(testname);
		
		try{
			int graphid = controller.CreateGraph(testname, Graph.GraphTypes.PETRI);
			
			
			assertTrue(controller.getGraphs().get(graphid).getName() == testgraph.getName()
					&& controller.getGraphs().get(graphid).getGraphType() == Graph.GraphTypes.PETRI);
			
		} catch(Exception e){
			e.printStackTrace();
			fail();
		}
	}
	
	@Test
	public void CreateCRGraphTest(){
		Graph testgraph = new CrGraph(testname);
		
		try{
			int graphid = controller.CreateGraph(testname, Graph.GraphTypes.CR);
			
			
			assertTrue(controller.getGraphs().get(graphid).getName() == testgraph.getName()
					&& controller.getGraphs().get(graphid).getGraphType() == Graph.GraphTypes.CR);
			
		} catch(Exception e){
			e.printStackTrace();
			fail();
		}
	}
	
	@Test 
	public void CreateNullGraphTest(){
		
		try{
			controller.CreateGraph(testname, null);
			fail();
		} catch (Exception e){
			assertTrue(true);
		}
	}

	
	@Test
	public void DeleteCrGraphTest(){
		int graphid;
		try{
			graphid = controller.CreateGraph(testname, Graph.GraphTypes.CR);
			
			controller.DeleteCrGraph(graphid);
		} catch (Exception e){
			e.printStackTrace();
			fail();
			return;
		}
		
		assertNull(controller.getGraphs().get(graphid));
	}
	
	
}
