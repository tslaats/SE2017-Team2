// --------------------------------------------------------
// Code generated by Papyrus Java
// --------------------------------------------------------

package gui;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import datamodel.Graph;
import datamodel.Graph.GraphTypes;
import datamodel.Position;
import datamodel.cr.CrGraph;
import datamodel.cr.Event;
import datamodel.petri.Petrinet;
import datamodel.petri.Place;
import datamodel.petri.Transition;

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
	 * ID of the current active/selected graph. Determines which graph gets
	 * edited
	 */
	public static int ActiveGraphID;

	/**
	 * Creates a Graph of the given type with the given name
	 * 
	 * @param name
	 *            Name of the graph
	 * @param graphType
	 *            Type of the graph
	 * @return
	 * @throws Exception
	 *             If the graph type is not supported
	 */
	public int createGraph(String name, Graph.GraphTypes graphType) throws Exception {

		Graph newGraph = null;
		int id;

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
		id = newGraph.getID();
		this.graphs.put(id, newGraph);
		return id;
	}

	/**
	 * Deletes the specified Graph object
	 * 
	 * @param graphID
	 *            ID of the graph to be deleted
	 * @throws Exception
	 *             If the given graphID does not exist
	 */
	public void deleteCrGraph(int graphID) throws Exception {

		if (!this.graphs.containsKey(graphID)) {
			throw new Exception("The given graphID does not exist");
		}

		// Remove graph from the HashMap and set its reference to null such that
		// other graphs loses their reference to it as well.
		Graph graph = this.graphs.get(graphID);
		this.graphs.remove(graphID);
		graph = null;

	}

	// Returns true if the active graph is a Petri Net
	public Boolean isActiveGraphPetri() throws Exception {
		if (!this.graphs.containsKey(ActiveGraphID)) {
			throw new Exception("The ActiveGraphID: " + ActiveGraphID + " does not exist");
		}

		// Get the current graph
		Graph curGraph = this.graphs.get(ActiveGraphID);

		return (curGraph.getGraphType() == GraphTypes.PETRI);
	}

	// Returns true if the active graph is a Cr Graph
	public Boolean isActiveGraphCr() throws Exception {
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
	 * @param pos
	 *            Position of the new Place
	 * @throws Exception
	 *             If the Place could not be added
	 */
	public void createPlace(Position pos) throws Exception {
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
	 * @param placeID
	 *            ID of the Place to be removed
	 * @throws Exception
	 *             If the Place could not be deleted
	 */
	public void deletePlace(int placeID) throws Exception {
		// Make sure that active graph is a Petri Net
		if (!this.isActiveGraphPetri()) {
			throw new Exception("The active graph is not Petri Net");
		}

		Petrinet petrinet = (Petrinet) this.graphs.get(ActiveGraphID);
		petrinet.deletePlace(placeID);
	}

	/**
	 * Sets whether or not the given Place has a token or not
	 * 
	 * @param placeID ID of the Place
	 * @param hasToken If true then the Place gets a token, if false then it does not
	 * @throws Exception If the ID does not correspond to a existing Place
	 */
	public void changePlaceToken(int placeID, boolean hasToken) throws Exception {
		// Make sure that active graph is a Petri Net
		if (!this.isActiveGraphPetri()) {
			throw new Exception("The active graph is not Petri Net");
		}		
		
		Petrinet petrinet = (Petrinet) this.graphs.get(ActiveGraphID);
		petrinet.changePlaceToken(placeID, hasToken);
	}
	
	/**
	 * Creates a Petri Transition
	 * 
	 * @param pos
	 *            Position of the Transition
	 * @param name
	 *            Name of the Transition
	 * @return 
	 * @throws Exception
	 *             If the Transition could not be added
	 */
	public int createTransition(Position pos, String name) throws Exception {
		// Make sure that active graph is a Petri Net
		if (!this.isActiveGraphPetri()) {
			throw new Exception("The active graph is not Petri Net");
		}

		Petrinet petrinet = (Petrinet) this.graphs.get(ActiveGraphID);
		
		return petrinet.addTransition(pos, name);
	}

	/**
	 * Deletes a Petri Transition
	 * 
	 * @param transitionID
	 *            ID of the Transition
	 * @throws Exception
	 *             If the Transition could not be deleted
	 */
	public void deleteTransition(int transitionID) throws Exception {
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
	 * @param incomingID
	 *            ID of the Arc origin PetriObject
	 * @param outgoingID
	 *            ID of the Arc destination PetriObject
	 * @throws Exception
	 *             If the Arc could not be added
	 */
	public void createArc(int incomingID, int outgoingID) throws Exception {
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
	 * @param incomingID
	 *            ID of the Arc origin PetriObject
	 * @param outgoingID
	 *            ID of the Arc destination PetriObject
	 * @throws Exception
	 *             If the Arc could not be deleted
	 */
	public void deleteArc(int incomingID, int outgoingID) throws Exception {
		// Make sure that active graph is a Petri Net
		if (!this.isActiveGraphPetri()) {
			throw new Exception("The active graph is not Petri Net");
		}

		Petrinet petrinet = (Petrinet) this.graphs.get(ActiveGraphID);
		petrinet.deleteArc(incomingID, outgoingID);
	}

	/**
	 * Creates a Cr Event
	 * 
	 * @param pos
	 *            Position of the Event
	 * @param name
	 *            Name of the Event
	 * @throws Exception
	 *             If there was problem creating the Event
	 */
	public int createEvent(Position pos, String name) throws Exception {
		// Make sure that active graph is a Cr Graph
		if (!this.isActiveGraphCr()) {
			throw new Exception("The active graph is not Cr Graph");
		}

		CrGraph crGraph = (CrGraph) this.graphs.get(ActiveGraphID);
		
		return crGraph.addEvent(pos, name);
	}
	
	/**
	 * Creates a Cr Event
	 * 
	 * @param pos
	 *            Position of the Event
	 * @param name
	 *            Name of the Event
	 * @parem isPending
	 * 			  If the Event should start out being pending or not
	 * @throws Exception
	 *             If there was problem creating the Event
	 */
	public int createEvent(Position pos, String name, boolean isPending) throws Exception {
		// Make sure that active graph is a Cr Graph
		if (!this.isActiveGraphCr()) {
			throw new Exception("The active graph is not Cr Graph");
		}

		CrGraph crGraph = (CrGraph) this.graphs.get(ActiveGraphID);
		
		return crGraph.addEvent(pos, name, isPending);
	}

	/**
	 * Deletes a Cr Event
	 * 
	 * @param eventID
	 *            ID of the Event
	 * @throws Exception
	 *             If there was a problem deleting the Event
	 */
	public void deleteEvent(int eventID) throws Exception {
		// Make sure that active graph is a Cr Graph
		if (!this.isActiveGraphCr()) {
			throw new Exception("The active graph is not Cr Graph");
		}

		CrGraph crGraph = (CrGraph) this.graphs.get(ActiveGraphID);
		crGraph.deleteEvent(eventID);
	}

	/**
	 * Creates a Cr Response Relation
	 * 
	 * @param incomingID
	 *            ID of the incoming Event
	 * @param outgoingID
	 *            ID of the outgoing Event
	 * @throws Exception
	 *             If there was a problem creating the Response Relation
	 */
	public void createResponse(int incomingID, int outgoingID) throws Exception {
		// Make sure that active graph is a Cr Graph
		if (!this.isActiveGraphCr()) {
			throw new Exception("The active graph is not Cr Graph");
		}

		CrGraph crGraph = (CrGraph) this.graphs.get(ActiveGraphID);
		crGraph.addResponse(incomingID, outgoingID);
	}

	/**
	 * Deletes a Cr Response Relation
	 * 
	 * @param crObjID
	 *            ID of the Response Relation
	 * @throws Exception
	 *             If there was a problem deleting the Response Relation
	 */
	public void deleteResponse(int crObjID) throws Exception {
		// Make sure that active graph is a Cr Graph
		if (!this.isActiveGraphCr()) {
			throw new Exception("The active graph is not Cr Graph");
		}

		CrGraph crGraph = (CrGraph) this.graphs.get(ActiveGraphID);
		crGraph.deleteResponse(crObjID);
	}

	/**
	 * Creates a Cr Condition Relation
	 * 
	 * @param incomingID
	 *            ID of the incoming Event
	 * @param outgoingID
	 *            ID of the outgoing Event
	 * @throws Exception
	 *             If there was a problem creating the Condition Relation
	 */
	public void createCondition(int incomingID, int outgoingID) throws Exception {
		// Make sure that active graph is a Cr Graph
		if (!this.isActiveGraphCr()) {
			throw new Exception("The active graph is not Cr Graph");
		}

		CrGraph crGraph = (CrGraph) this.graphs.get(ActiveGraphID);
		crGraph.addCondition(incomingID, outgoingID);
	}

	/**
	 * Deletes a Cr Condition Relation
	 * 
	 * @param crObjID
	 *            ID of the Condition Relation
	 * @throws Exception
	 *             If there was a problem deleting the Condition Relation
	 */
	public void deleteCondition(int crObjID) throws Exception {
		// Make sure that active graph is a Cr Graph
		if (!this.isActiveGraphCr()) {
			throw new Exception("The active graph is not Cr Graph");
		}

		CrGraph crGraph = (CrGraph) this.graphs.get(ActiveGraphID);
		crGraph.deleteCondition(crObjID);
	}

	/**
	 * Get the current active graph
	 * 
	 * @return The activ Graph object
	 * @throws Exception If the ActiveGraphID does not exist
	 *             
	 */
	public Graph getActiveGraph() throws Exception {
		if (!this.graphs.containsKey(ActiveGraphID)) {
			throw new Exception("The ActiveGraphID does not exist");
		}

		return this.graphs.get(ActiveGraphID);
	}

	public BufferedImage draw() throws Exception {
		BufferedImage img;
		Graph graph;

		if (!this.graphs.containsKey(ActiveGraphID)) {
			throw new Exception("The ActiveGraphID does not exist");
		}
		graph = this.graphs.get(ActiveGraphID);

		if (this.isActiveGraphCr()) {
			CrGraph crgraph = (CrGraph) graph;
			img = crgraph.draw();

		} else {
			Petrinet petrinet = (Petrinet) graph;
			img = petrinet.draw();
		}

		return img;
	}
	
	/**
	 * Returns a List of possible Actions for the Active Graph
	 * 
	 * @return List of Action objects
	 * @throws Exception If the ActiveGraphID does not exist, or if there was a problem calling the Semantics interface
	 */
	public List<Action<?>> getPossibleActions() throws Exception {
		
		List<Action<?>> actions = new ArrayList<>();
		
		Graph graph;

		if (!this.graphs.containsKey(ActiveGraphID)) {
			throw new Exception("The ActiveGraphID does not exist");
		}
		graph = this.graphs.get(ActiveGraphID);

		if (this.isActiveGraphCr()) {
			CrGraph crgraph = (CrGraph) graph;
			List<Event> events = crgraph.getPossibleActions();
			// Create actions for each event
			for (Event e : events) {
				Action<Event> a = new Action<Event>(e.getName(), e.getID(), GraphTypes.CR, e);
				actions.add(a);
			}
		} else {
			Petrinet petrinet = (Petrinet) graph;
			List<Transition> trans = petrinet.getPossibleActions();
			// Create actions for each transition
			for (Transition t : trans) {
				Action<Transition> a = new Action<Transition>(t.getName(), t.getID(), GraphTypes.PETRI, t);
				actions.add(a);
			}
		}		
		
		return actions;		
	}
	
	/**
	 * Executes the given action on the active graph
	 * 
	 * @param action The action object to be executed
	 * @throws Exception If the ActiveGraphID does not exist, if the given 
	 * action and the active graph does not have matching types, or if there was a problem
	 * calling the Semantics interface
	 */
	public void executeAction(Action<?> action) throws Exception {
		Graph graph;

		if (!this.graphs.containsKey(ActiveGraphID)) {
			throw new Exception("The ActiveGraphID does not exist");
		}
		graph = this.graphs.get(ActiveGraphID);		
		
		if (action.getGraphType() == GraphTypes.CR && this.isActiveGraphCr()) {
			CrGraph crgraph = (CrGraph) graph;
			// Execute the event and update the graph entry in the hash map
			CrGraph updatedGraph = crgraph.executeAction((Event)action.getActionObject());
			this.graphs.put(ActiveGraphID, updatedGraph);
		}
		else if (action.getGraphType() == GraphTypes.PETRI && this.isActiveGraphPetri()) {
			Petrinet petri = (Petrinet) graph;
			// Execute the transition and update the graph entry in the hash map
			Petrinet updatedGraph = petri.executeAction((Transition)action.getActionObject());
			this.graphs.put(ActiveGraphID, updatedGraph);
		}
		else {
			throw new Exception("The given action and the active graph does not have matching types");
		}
		
	}	

	public Map<Integer, Graph> getGraphs() {
		return graphs;
	}
	
	
	/**
	 * Binds the given graph object with the given nested graph
	 * 
	 * @param graphObjectID ID of the graph object (Event or Transition)
	 * @param nestedGraphID ID of the nested graph (Petri Net or CR Graph)
	 * @throws Exception If there was a problem binding the graph object with the nested graph
	 */
	public void bindNestedGraph(int graphObjectID, int nestedGraphID) throws Exception {
		
		Graph graph;
		Graph nestedGraph;

		if (!this.graphs.containsKey(ActiveGraphID)) {
			throw new Exception("The ActiveGraphID does not exist");
		}
		if (!this.graphs.containsKey(nestedGraphID)) {
			throw new Exception("The nested graph ID does not exist");
		}
		
		graph = this.graphs.get(ActiveGraphID);
		nestedGraph = this.graphs.get(nestedGraphID);
		
		if (this.isActiveGraphCr()) {
			CrGraph crgraph = (CrGraph) graph;
			Event e = crgraph.getEvent(graphObjectID);
			if (e.getPetrinet() != null) {
				throw new Exception("Event: " + e.toString() + " already has a nested Petri net");
			}
			Petrinet nestedPetri = null;
			try {
				nestedPetri = (Petrinet) nestedGraph;
			}
			catch (Exception ex) {
				throw new Exception("The nested graph: " + nestedGraph.toString() + " is not a Petri Net");
			}
			// Finally set the nested graph
			e.setPetrinet(nestedPetri);
			nestedPetri.addParentEvent(e);
			
		} else {
			Petrinet petrinet = (Petrinet) graph;
			Transition t = petrinet.getTransition(graphObjectID);
			if (t.getCrGraph() != null) {
				throw new Exception("Transition: " + t.toString() + " already has a nested CR Graph");
			}
			CrGraph nestedCr = null;
			try {
				nestedCr = (CrGraph) nestedGraph;
			}
			catch (Exception ex) {
				throw new Exception("The nested graph: " + nestedGraph.toString() + " is not a CR Graph");
			}
			// Finally set the nested graph
			t.setCrGraph(nestedCr);
			nestedCr.addParentTransition(t);			
		}		
		
	}
	
	/**
	 * Unbinds the given graph object with a nested graph
	 * 
	 * @param graphObjectID ID of the graph object (Event or Transition)
	 * @param deleteNestedGraph if true, the nested graph gets deleted as well
	 * @throws Exception If the graph object does not have a nested graph, or some other problem occurred
	 */
	public int unbindNestedGraph(int graphObjectID, boolean deleteNestedGraph) throws Exception {
		Graph graph;
		Graph nestedGraph;
		
		int nestedGraphID = -1;
		
		if (!this.graphs.containsKey(ActiveGraphID)) {
			throw new Exception("The ActiveGraphID does not exist");
		}
		
		graph = this.graphs.get(ActiveGraphID);
				
		if (this.isActiveGraphCr()) {
			CrGraph crgraph = (CrGraph) graph;
			Event e = crgraph.getEvent(graphObjectID);
			if (e.getPetrinet() == null) {
				throw new Exception("Event: " + e.toString() + " does not have a nested Petri net");
			}
			Petrinet nestedPetri = e.getPetrinet();
			nestedGraph = nestedPetri;
			
			// Finally remove the nested graph
			e.setPetrinet(null);
			nestedPetri.removeParentEvent(e.getID());
			
		} else {
			Petrinet petrinet = (Petrinet) graph;
			Transition t = petrinet.getTransition(graphObjectID);
			if (t.getCrGraph() == null) {
				throw new Exception("Transition: " + t.toString() + " does not have a nested CR Graph");
			}
			CrGraph nestedCr = t.getCrGraph();
			nestedGraph = nestedCr;
			
			// Finally remove the nested graph
			t.setCrGraph(null);
			nestedCr.removeParentTransition(t.getID());
		}
		
		// Delete the nested graph if set
		if (deleteNestedGraph) {
			nestedGraphID = this.deleteGraph(nestedGraph.getID());
		}
		
		return nestedGraphID;
	}
	
	/**
	 * Deletes the graph with the given ID
	 * 
	 * @param graphID ID of the graph (Petri Net or CR Graph)
	 * @throws Exception If the graph ID does not exist
	 */
	public int deleteGraph(int graphID) throws Exception {
		if (!this.graphs.containsKey(graphID)) {
			throw new Exception("The given ID: " + graphID + " does not correspond to any existing graph");
		}
		
		Graph graph = this.graphs.get(graphID);
		
		// Remove from nested objects
		graph.deleteGraph();
		
		// Finally remove the graph from the hashmap
		this.graphs.remove(graphID);		
		
		return graphID;
	}
	
	/**
	 * Starts the simulation by creating a "safe point" for each graph
	 */
	public void startSimulation() {
		for (Graph g : this.graphs.values()) {
			g.startSimulation();
		}
	}
	
	/**
	 * Stops the simulation by restoring the latest "safe point" for each graph
	 */
	public void stopSimulation() {
		for (Graph g : this.graphs.values()) {
			g.stopSimulation();
		}
	}
	


};
