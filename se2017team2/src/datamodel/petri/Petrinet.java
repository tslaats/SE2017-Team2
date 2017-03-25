// --------------------------------------------------------
// Code generated by Papyrus Java
// --------------------------------------------------------

package datamodel.petri;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import datamodel.Graph;
import datamodel.Semantics;
import datamodel.SimulationObject;
import datamodel.Visualization;
import datamodel.cr.Event;
import datamodel.Position;
import datamodel.petri.Place;
import datamodel.petri.Transition;
import petriVisualization.PetriDrawer;


/************************************************************/
/**
 * 
 */
public class Petrinet extends Graph implements Visualization, Semantics<Transition>, SimulationObject {
	
	// Default starting and end position of the initial places
	private final Position placeStartPos = new Position(100,250);
	
	private final Position placeEndPos = new Position(1000,250);
	
	private PetriDrawer petriDrawer;
	
	/**
	 * The start Place, which can't be deleted
	 */
	private Place start;
	/**
	 * The end Place, which can't be deleted
	 */
	private Place end;
	/**
	 * HashMap of transitions
	 */
	private Map<Integer, Transition> transitions = new HashMap<>();
	/**
	 * HashMap of places
	 */
	private Map<Integer, Place> places = new HashMap<>();

	/**
	 *  HashMap of Events which refer to this Petrinet
	 */
	private Map<Integer, Event> parentEvents = new HashMap<>();
	
	/**
	 * Creates a new Petri Net with a default start Place and end Place
	 * 
	 * @param name Name of the Petri Net
	 */
	public Petrinet(String name) {
		super(name);
		
		this.setGraphType(GraphTypes.PETRI);
		
		// Initialize start and end place
		this.start = new Place(placeStartPos);
		this.start.setCanBeDeleted(false);
		this.start.setToken(true);
		this.end = new Place(placeEndPos);
		this.end.setCanBeDeleted(false);
		
		// Now store them in the places HashMap
		this.places.put(this.start.id, this.start);
		this.places.put(this.end.id, this.end);
		
		initializePetridrawer();
	}
	
	private void initializePetridrawer(){
		petriDrawer = new PetriDrawer();
		petriDrawer.setZoom(0.8f);
	}
	
	public void addParentEvent(Event e) {
		this.parentEvents.put(e.getID(), e);
	}
	
	public void removeParentEvent(int eventID) {
		this.parentEvents.remove(eventID);
	}	
	
	/**
	 * Get Transition objects contained in the HashMap
	 * 
	 * @return Collection of Transition objects
	 */
	public Collection<Transition> getTransitionValues() {
		return this.transitions.values();
	}
	
	/**
	 * Get Place objects contained in the HashMap
	 * 
	 * @return Collection of Place objects
	 */
	public Collection<Place> getPlaceValues() {
		return this.places.values();
	}
	
	public Map<Integer, Transition> getTransitions() {
		return transitions;
	}
	
	public Transition getTransition(int transID) throws Exception {
		if (!this.transitions.containsKey(transID)) {
			throw new Exception("There does not exist a transition with ID: " + transID);
		}
		
		return this.transitions.get(transID);
	}

	public Map<Integer, Place> getPlaces() {
		return places;
	}

	/**
	 * Adds a new Place to the Petri Net
	 * 
	 * @return ID of the new Place
	 * @param pos Position of the new Place
	 */
	public int addPlace(Position pos) {
		
		// (Jacob - petri visualation team 2)
		// Update the pos to scale after the zoom of the petri window
		pos.setX( (int) Math.floor(pos.x()/petriDrawer.getZoom()));
		pos.setY( (int) Math.floor(pos.y()/petriDrawer.getZoom()));
		
		Place newPlace = new Place(pos);
		this.places.put(newPlace.getID(), newPlace);	
		return newPlace.getID();
	}
	
	/**
	 * Deletes the Place with the given ID
	 * 
	 * @param placeID ID of the place to be deleted
	 * @throws Exception If the ID does not exist or if it can't be deleted
	 */
	public void deletePlace(int placeID) throws Exception {
		if (!this.places.containsKey(placeID)) {
			throw new Exception("The given placeID: " + placeID + " does not exist");
		}
		
		Place p = this.places.get(placeID);
		if (!p.getCanBeDeleted()) {
			throw new Exception("The given placeID: " + placeID + " can't be deleted");
		}
		this.places.remove(placeID);
		p.removeFromNeighbors();
		p = null;
	}
	
	/**
	 * Sets whether or not the given Place has a token or not
	 * 
	 * @param placeID ID of the Place
	 * @param hasToken If true then the Place gets a token, if false then it does not
	 * @throws Exception If the ID does not correspond to a existing Place
	 */
	public void changePlaceToken(int placeID, boolean hasToken) throws Exception {
		if (!this.places.containsKey(placeID)) {
			throw new Exception("The given placeID: " + placeID + " does not exist");
		}
		
		Place p = this.places.get(placeID);
		p.setToken(hasToken);
	}
	
	/**
	 * Adds a new Transition to the Petri Net
	 * 
	 * @return Id of the new Transition
	 * @param pos Position of the new Transition
	 * @param name Name of the new Transition
	 */
	public int addTransition(Position pos, String name) {
		
		// (Jacob - petri visualation team 2)
		// Update the pos to scale after the zoom of the petri window
		pos.setX( (int) Math.floor(pos.x()/petriDrawer.getZoom()));
		pos.setY( (int) Math.floor(pos.y()/petriDrawer.getZoom()));
		
		Transition t = new Transition(pos, name);
		
		this.transitions.put(t.getID(), t);
		
		return t.getID();
	}
	
	/**
	 * Deletes the Transition with the given ID
	 * 
	 * @param transitionID ID of the transition to be deleted
	 * @throws Exception If the ID does not exist or if it can't be deleted
	 */
	public void deleteTranistion(int transitionID) throws Exception {
		if (!this.transitions.containsKey(transitionID)) {
			throw new Exception("The given transitionID: " +transitionID+ " does not exist");
		}
		
		Transition t = this.transitions.get(transitionID);
		if (!t.getCanBeDeleted()) {
			throw new Exception("The given transitionID: " + transitionID + " can't be deleted");
		}
		this.transitions.remove(transitionID);
		t.removeFromNeighbors();
		t = null;
	}
	
	/**
	 * Adds an Arc between the given IDs by modifying the incoming PetriObject's outgoing list
	 * and the outgoing PetriObject's incoming list.
	 * 
	 * @param incomingID ID of the Arc origin PetriObject
	 * @param outgoingID ID of the Arc destination PetriObject
	 * @throws Exception If one or both IDs does not exist, or if an Arc is trying to be created
	 * between the same PetriObject type.
	 */
	public void addArc(int incomingID, int outgoingID) throws Exception {				
		
		// If the incoming ID matches a Place
		if (this.places.containsKey(incomingID)) {
			// Check that outgoing ID corresponds to Transition
			if (!this.transitions.containsKey(outgoingID)) {
				throw new Exception("The outgoingID: " +outgoingID+ " does not match any existing Transition");
			}
			// Finally add the Arc (Place -> Transition)
			this.addArc(this.places.get(incomingID), this.transitions.get(outgoingID));
		}
		// If the incoming ID matches a Transition
		else if (this.transitions.containsKey(incomingID)) {
			// Check that outgoing ID corresponds to Place
			if (!this.places.containsKey(outgoingID)) {
				throw new Exception("The outgoingID: " +outgoingID+ " does not match any existing Place");
			}
			// Finally add the Arc (Transition -> Place)
			this.addArc(this.transitions.get(incomingID), this.places.get(outgoingID));
		}
		// If the incoming ID matches none
		else {
			throw new Exception("The incomingID: " +incomingID+ " does not match with any existing PetriObjects");
		}		
	}
	
	private void addArc(Place p, Transition t) {
		p.addOutgoingObject(t);
		t.addIncomingObject(p);
	}
	
	private void addArc(Transition t, Place p) {
		t.addOutgoingObject(p);
		p.addIncomingObject(t);
	}

	/**
	 * Removes the Arc between the given PetriObject IDs by modifying the incoming PetriObject's outgoing list
	 * and the outgoing PetriObject's incoming list.
	 * 
	 * @param incomingID ID of the Arc origin PetriObject
	 * @param outgoingID ID of the Arc destination PetriObject
	 * @throws Exception If one or both IDs does not exist, or if the Arc does not exist.
	 */
	public void deleteArc(int incomingID, int outgoingID) throws Exception {
		// If the incoming ID matches a Place
		if (this.places.containsKey(incomingID)) {
			// Check that outgoing ID corresponds to Transition
			if (!this.transitions.containsKey(outgoingID)) {
				throw new Exception("The outgoingID: " +outgoingID+ " does not match any existing Transition");
			}
			// Finally remove the Arc (Place -> Transition)
			this.places.get(incomingID).removeOutgoingObject(outgoingID);
			this.transitions.get(outgoingID).removeIncomingObject(incomingID);
		}
		// If the incoming ID matches a Transition
		else if (this.transitions.containsKey(incomingID)) {
			// Check that outgoing ID corresponds to Place
			if (!this.places.containsKey(outgoingID)) {
				throw new Exception("The outgoingID: " +outgoingID+ " does not match any existing Place");
			}
			// Finally remove the Arc (Transition -> Place)
			this.transitions.get(incomingID).removeOutgoingObject(outgoingID);
			this.places.get(outgoingID).removeIncomingObject(incomingID);
		}
		// If the incoming ID matches none
		else {
			throw new Exception("The incomingID: " +incomingID+ " does not match with any existing PetriObjects");
		}	
	}
	
	@Override
	public List<Transition> getPossibleActions() {
		ArrayList<Transition> possibleActions = new ArrayList<Transition>();
		ArrayList<Transition> transitions = new ArrayList<Transition>(getTransitionValues());
		
		for(Transition transition : transitions) {
			boolean canSimulate = true;
			for(PetriObject place : transition.getIncoming().values()){
				Place p = (Place) place;
				if(!p.hasToken()){
					canSimulate = false;
					break;
				}
				
				
			}
			if(canSimulate) possibleActions.add(transition);
		}
		
		return possibleActions;
	}
	
	@Override
	public Petrinet executeAction(Transition object) throws IllegalArgumentException{
		boolean canSimulate = true;
		for(PetriObject place : object.getIncoming().values()){
			Place p = (Place) place;
			if(!p.hasToken()){
				canSimulate = false;
				break;
			}
		}
		
		if(object.getCrGraph() != null && !object.getCrGraph().isDone()) {
			canSimulate = false;
		}
		
		if(canSimulate){
			for(PetriObject place : object.getIncoming().values()){
				Place p = (Place) place;
				p.setToken(false);
			}
			
			for(PetriObject place : object.getOutgoing().values()){
				Place p = (Place) place;
				p.setToken(true);
			}
		} else throw new IllegalArgumentException("Attempted to simulate an illegal transition!");
        return this;
	}

	@Override
	public BufferedImage draw() {
		// TODO Auto-generated method stub
		return petriDrawer.draw(this);
		
	}

    @Override
    public boolean isDone() {
        return this.end.hasToken();
    }
    
    public Place getStart() {
		return start;
	}

	public Place getEnd() {
		return end;
	}

	@Override
	public void deleteGraph() {
	    // Remove this graph from all parent Events
		for (Event e : this.parentEvents.values()) {
			if (e != null) {
				if (e.getPetrinet() != null) {
					if (e.getPetrinet().getID() == this.getID()) {
						e.setPetrinet(null);
					}
				}
			}
		}
		
	}

	@Override
	public void startSimulation() {
		for (Place p : this.places.values()) {
			p.startSimulation();
		}
	}

	@Override
	public void stopSimulation() {
		for (Place p : this.places.values()) {
			p.stopSimulation();
		}		
	}

};
