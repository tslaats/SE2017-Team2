// --------------------------------------------------------
// Code generated by Papyrus Java
// --------------------------------------------------------

package gui;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import datamodel.Graph;
import datamodel.Graph.GraphTypes;
import datamodel.Position;
import datamodel.cr.CrGraph;
import datamodel.petri.Petrinet;

/************************************************************/
/**
 *
 */
public class GuiController {
	/**
	 * Map of graphs maintained by the GUIController
	 */
	private Map<Integer, Graph> graphs = new HashMap<>();
	/**
	 * ID of the current active/selected graph. Determines which graph gets edited
	 */
	public static int ActiveGraphID;

	/**
	 * Creates a Graph of the given type with the given name
	 * 
	 * @param name Name of the graph
	 * @param graphType Type of the graph
	 * @throws Exception If the graph type is not supported
	 */
	public void CreateGraph(String name, Graph.GraphTypes graphType) throws Exception {
		
		Graph newGraph = null;
		
		switch (graphType) {
		case PETRI:
			newGraph = new Petrinet(name);
			break;
		case CR:
			newGraph = new CrGraph(name);
			break;
		default:
			break;
		}
		
		if (newGraph == null) {
			throw new Exception("Unsupported graph type");
		}
		
		// Store the new graph in the hashmap
		this.graphs.put(newGraph.getID(), newGraph);
		
	}

	/**
	 * Deletes the specified Graph object
	 * 
	 * @param graphID ID of the graph to be deleted
	 * @throws Exception If the given graphID does not exist
	 */
	public void DeleteCrGraph(int graphID) throws Exception {
		
		if (!this.graphs.containsKey(graphID)) {
			throw new Exception("The given graphID does not exist");
		}
		
		// Remove graph from the HashMap and set its reference to null such that other graphs loses their reference to it as well.
		Graph graph = this.graphs.get(graphID);
		this.graphs.remove(graphID);
		graph = null;
		
	}
	
	// Returns true if the active graph is a Petri Net
	private Boolean isActiveGraphPetri() throws Exception {
		if (!this.graphs.containsKey(ActiveGraphID)) {
			throw new Exception("The ActiveGraphID: " +ActiveGraphID+ " does not exist");
		}
		
		// Get the current graph
		Graph curGraph = this.graphs.get(ActiveGraphID);
		
		return (curGraph.getGraphType() == GraphTypes.PETRI);
	}

	// Returns true if the active graph is a Cr Graph
	private Boolean isActiveGraphCr() throws Exception {
		if (!this.graphs.containsKey(ActiveGraphID)) {
			throw new Exception("The ActiveGraphID does not exist");
		}
		
		// Get the current graph
		Graph curGraph = this.graphs.get(ActiveGraphID);
		
		return (curGraph.getGraphType() == GraphTypes.CR);
	}
	
	/**
	 * Creates a Petri Place at the given Position
	 * 
	 * @param pos Position of the new Place
	 * @throws Exception If the Place could not be added
	 */
	public void CreatePlace(Position pos) throws Exception {
		// Make sure that active graph is a Petri Net 
		if (!this.isActiveGraphPetri()) {
			throw new Exception("The active graph is not Petri Net");
		}
		
		Petrinet petrinet = (Petrinet) this.graphs.get(ActiveGraphID);
		petrinet.addPlace(pos);
	
	}

	/**
	 * Deletes the Petri Place with given ID
	 * 
	 * @param placeID ID of the Place to be removed
	 * @throws Exception If the Place could not be deleted
	 */
	public void DeletePlace(int placeID) throws Exception {
		// Make sure that active graph is a Petri Net 
		if (!this.isActiveGraphPetri()) {
			throw new Exception("The active graph is not Petri Net");
		}
		
		Petrinet petrinet = (Petrinet) this.graphs.get(ActiveGraphID);
		petrinet.deletePlace(placeID);
	}

	/**
	 * Creates a Petri Transition
	 * 
	 * @param pos Position of the Transition
	 * @param name Name of the Transition
	 * @throws Exception If the Transition could not be added
	 */
	public void CreateTransition(Position pos, String name) throws Exception {
		// Make sure that active graph is a Petri Net 
		if (!this.isActiveGraphPetri()) {
			throw new Exception("The active graph is not Petri Net");
		}
		
		Petrinet petrinet = (Petrinet) this.graphs.get(ActiveGraphID);
		petrinet.addTransition(pos, name);
	}

	/**
	 * Deletes a Petri Transition
	 * 
	 * @param transitionID ID of the Transition
	 * @throws Exception If the Transition could not be deleted
	 */
	public void DeleteTransition(int transitionID) throws Exception {
		// Make sure that active graph is a Petri Net 
		if (!this.isActiveGraphPetri()) {
			throw new Exception("The active graph is not Petri Net");
		}
		
		Petrinet petrinet = (Petrinet) this.graphs.get(ActiveGraphID);
		petrinet.deleteTranistion(transitionID);
	}

	/**
	 * Creates a Petri Arc
	 * 
	 * @param incomingID ID of the Arc origin PetriObject
	 * @param outgoingID ID of the Arc destination PetriObject
	 * @throws Exception If the Arc could not be added
	 */
	public void CreateArc(int incomingID, int outgoingID) throws Exception {
		// Make sure that active graph is a Petri Net 
		if (!this.isActiveGraphPetri()) {
			throw new Exception("The active graph is not Petri Net");
		}
		
		Petrinet petrinet = (Petrinet) this.graphs.get(ActiveGraphID);
		petrinet.addArc(incomingID, outgoingID);
	}

	/**
	 * Deletes a Petri Arc
	 * 
	 * @param incomingID ID of the Arc origin PetriObject
	 * @param outgoingID ID of the Arc destination PetriObject
	 * @throws Exception If the Arc could not be deleted
	 */
	public void DeleteArc(int incomingID, int outgoingID) throws Exception {
		// Make sure that active graph is a Petri Net 
		if (!this.isActiveGraphPetri()) {
			throw new Exception("The active graph is not Petri Net");
		}
		
		Petrinet petrinet = (Petrinet) this.graphs.get(ActiveGraphID);
		petrinet.deleteArc(incomingID, outgoingID);
	}

	/**
	 * 
	 * @param pos 
	 * @param name 
	 * @throws Exception 
	 */
	public void CreateEvent(Position pos, String name) throws Exception {
		// Make sure that active graph is a Cr Graph 
		if (!this.isActiveGraphCr()) {
			throw new Exception("The active graph is not Cr Graph");
		}
	}

	/**
	 * 
	 * @param eventID 
	 * @throws Exception 
	 */
	public void DeleteEvent(int eventID) throws Exception {
		// Make sure that active graph is a Cr Graph 
		if (!this.isActiveGraphCr()) {
			throw new Exception("The active graph is not Cr Graph");
		}
	}

	/**
	 * 
	 * @param incomingID 
	 * @param outgoingID 
	 * @throws Exception 
	 */
	public void CreateResponse(int incomingID, int outgoingID) throws Exception {
		// Make sure that active graph is a Cr Graph 
		if (!this.isActiveGraphCr()) {
			throw new Exception("The active graph is not Cr Graph");
		}
	}

	/**
	 * 
	 * @param crObjID 
	 * @throws Exception 
	 */
	public void DeleteResponse(int crObjID) throws Exception {
		// Make sure that active graph is a Cr Graph 
		if (!this.isActiveGraphCr()) {
			throw new Exception("The active graph is not Cr Graph");
		}
	}

	/**
	 * 
	 * @param incomingID 
	 * @param outgoingID 
	 * @throws Exception 
	 */
	public void CreateCondition(int incomingID, int outgoingID) throws Exception {
		// Make sure that active graph is a Cr Graph 
		if (!this.isActiveGraphCr()) {
			throw new Exception("The active graph is not Cr Graph");
		}
	}

	/**
	 * 
	 * @param crObjID 
	 * @throws Exception 
	 */
	public void DeleteCondition(int crObjID) throws Exception {
		// Make sure that active graph is a Cr Graph 
		if (!this.isActiveGraphCr()) {
			throw new Exception("The active graph is not Cr Graph");
		}
	}
};
