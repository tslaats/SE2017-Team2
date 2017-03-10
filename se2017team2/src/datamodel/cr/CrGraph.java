package datamodel.cr;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import datamodel.Graph;
import datamodel.Position;
import datamodel.Semantics;
import datamodel.Visualization;

public class CrGraph extends Graph implements Visualization, Semantics {

	private List<Event> trace;
	//public ArrayList<CrObject> graph;
	
	private Map<Integer, CrObject> crObjects = new HashMap<>();
	
	public CrGraph(String name) {
		super(name);
		
		this.setGraphType(GraphTypes.CR);
		//this.graph = new ArrayList<CrObject>();
	}

	/**
	 * Get CrObjects contained in the HashMap
	 * 
	 * @return ArrayList of objects CrObjects
	 */
	public ArrayList<CrObject> getCrObjects() {
		return (ArrayList<CrObject>) this.crObjects.values();
	}
	
	/**
	 * Adds an Event to the Cr Graph
	 * 
	 * @param pos Position of the Event
	 * @param name Name of the Event
	 */
	public void addEvent(Position pos, String name) {
		Event e = new Event(pos, name);
		
		this.crObjects.put(e.getID(), e);
	}
	
	/**
	 * Deletes the Event with the given ID
	 * 
	 * @param eventID ID of the Event to be deleted
	 * @throws Exception If the Event does not exist, or if the ID does not correspond to an Event
	 */
	public void deleteEvent(int eventID) throws Exception {
		if (!this.crObjects.containsKey(eventID)) {
			throw new Exception("There exists no Event with ID: " + eventID);
		}
		
		Event e = null;
		try {
			e = (Event) this.crObjects.get(eventID);
		}
		catch (Exception ex) {
			throw new Exception("The given ID: " +eventID+ " does not correpond to an Event");
		}
		
		this.crObjects.remove(eventID);
		e = null; // Set the Event as null		
	}
	
	/**
	 * Adds a condition relation between the two Event IDs
	 * 
	 * @param incomingEventID ID of the incoming Event
	 * @param outgoingEventID ID of the outgoing Event
	 * @throws Exception If the IDs are identical, the IDs does not exists, or if the IDs does not
	 * correspond to Events.
	 */
	public void addCondition(int incomingEventID, int outgoingEventID) throws Exception {
		if (incomingEventID == outgoingEventID) {
			throw new Exception("A condition can't be added between the same Event");
		}
		
		if (!this.crObjects.containsKey(incomingEventID)) {
			throw new Exception("There does not exist an Event with ID: " + incomingEventID);
		}
		
		if (!this.crObjects.containsKey(outgoingEventID)) {
			throw new Exception("There does not exist an Event with ID: " + outgoingEventID);
		}		
		
		Event in = null;
		Event out = null;
		
		try {
			in = (Event) this.crObjects.get(incomingEventID);
			out = (Event) this.crObjects.get(outgoingEventID);
		}
		catch (Exception e) {
			throw new Exception("One or more of the given IDs does not correspond to Events");
		}
		
		// Finally create a Condition Relation between the two Events
		Conditional c = new Conditional(in, out);
		this.crObjects.put(c.getID(), c);		
	}
	
	/**
	 * Deletes the Condition with the given ID
	 * 
	 * @param conID ID of the Condition to be deleted
	 * @throws Exception If the Condition does not exist, or if the ID does not correspond to an Condition
	 */
	public void deleteCondition(int conID) throws Exception {
		if (!this.crObjects.containsKey(conID)) {
			throw new Exception("There exists no Condition with ID: " + conID);
		}
		
		Conditional c = null;
		try {
			c = (Conditional) this.crObjects.get(conID);
		}
		catch (Exception ex) {
			throw new Exception("The given ID: " +conID+ " does not correpond to an Condition");
		}
		
		this.crObjects.remove(conID);
		c = null; // Set the Condition as null		
	}
	
	/**
	 * Adds a response relation between the two Event IDs
	 * 
	 * @param incomingEventID ID of the incoming Event
	 * @param outgoingEventID ID of the outgoing Event
	 * @throws Exception If the IDs are identical, the IDs does not exists, or if the IDs does not
	 * correspond to Events.
	 */
	public void addResponse(int incomingEventID, int outgoingEventID) throws Exception {
		if (incomingEventID == outgoingEventID) {
			throw new Exception("A response can't be added between the same Event");
		}
		
		if (!this.crObjects.containsKey(incomingEventID)) {
			throw new Exception("There does not exist an Event with ID: " + incomingEventID);
		}
		
		if (!this.crObjects.containsKey(outgoingEventID)) {
			throw new Exception("There does not exist an Event with ID: " + outgoingEventID);
		}		
		
		Event in = null;
		Event out = null;
		
		try {
			in = (Event) this.crObjects.get(incomingEventID);
			out = (Event) this.crObjects.get(outgoingEventID);
		}
		catch (Exception e) {
			throw new Exception("One or more of the given IDs does not correspond to Events");
		}
		
		// Finally create a Response Relation between the two Events
		Response r = new Response(in, out);
		this.crObjects.put(r.getID(), r);		
	}
	
	/**
	 * Deletes the Response with the given ID
	 * 
	 * @param resID ID of the Response to be deleted
	 * @throws Exception If the Response does not exist, or if the ID does not correspond to an Response
	 */
	public void deleteResponse(int resID) throws Exception {
		if (!this.crObjects.containsKey(resID)) {
			throw new Exception("There exists no Response with ID: " + resID);
		}
		
		Response r = null;
		try {
			r = (Response) this.crObjects.get(resID);
		}
		catch (Exception ex) {
			throw new Exception("The given ID: " +resID+ " does not correpond to an Response");
		}
		
		this.crObjects.remove(resID);
		r = null; // Set the Response as null		
	}
	
	
	@Override
	public void getPossibleActions() {
		// TODO Auto-generated method stub
	}

	@Override
	public void executeAction() {
		// TODO Auto-generated method stub
	}

	@Override
	public BufferedImage draw() {
		return CrDrawing.instance.draw(this);
	}
};