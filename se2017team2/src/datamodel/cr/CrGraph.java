package datamodel.cr;

import java.awt.image.BufferedImage;
import java.util.*;

import datamodel.Graph;
import datamodel.Position;
import datamodel.Semantics;
import datamodel.Visualization;

public class CrGraph extends Graph implements Visualization, Semantics<Event> {

	private List<Event> trace;
	//public ArrayList<CrObject> graph;
	
	private Map<Integer, CrObject> crObjects = new HashMap<>();
	
	public CrGraph(String name) {
		super(name);
		
		this.setGraphType(GraphTypes.CR);
		this.trace = new ArrayList<>();
		//this.graph = new ArrayList<CrObject>();
	}

	/**
	 * Get CrObjects contained in the HashMap
	 * 
	 * @return Collection of CrObjects objects
	 */
	public Collection<CrObject> getCrObjectValues() {
		return this.crObjects.values();
	}
	
	
	public Map<Integer, CrObject> getCrObjects() {
		return crObjects;
	}
	
	/**
	 * Adds an Event to the Cr Graph
	 * 
	 * @return The id of the added Event
	 * @param pos Position of the Event
	 * @param name Name of the Event
	 */
	public int addEvent(Position pos, String name) {
		Event e = new Event(pos, name);
		int id = e.getID();
		this.crObjects.put(id, e);
		return id;
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
	 * @return The id of the added Condition relation
	 * @param incomingEventID ID of the incoming Event
	 * @param outgoingEventID ID of the outgoing Event
	 * @throws Exception If the IDs are identical, the IDs does not exists, or if the IDs does not
	 * correspond to Events.
	 */
	public int addCondition(int incomingEventID, int outgoingEventID) throws Exception {
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
		int id = c.getID();
		this.crObjects.put(id, c);	
		return id;
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
	 * @return The id of the added Response relation
	 * @param incomingEventID ID of the incoming Event
	 * @param outgoingEventID ID of the outgoing Event
	 * @throws Exception If the IDs are identical, the IDs does not exists, or if the IDs does not
	 * correspond to Events.
	 */
	public int addResponse(int incomingEventID, int outgoingEventID) throws Exception {
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
		int id = r.getID();
		this.crObjects.put(id, r);	
		return id;
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

    /**
     * @return list of pending events for the CR graph
     */
	public List<Event> getPendingEvents() {
	    List<Event> pendingList = new ArrayList<>();
        for (CrObject o : this.crObjects.values()) {
            if (o instanceof Event && ((Event) o).isPending()) {
                pendingList.add((Event) o);
            }
        }
        return pendingList;
    }

    /**
     * @return current CR Graph trace
     */
    public List<Event> getTrace() {
        return this.trace;
    }

    /**
     * @return List of legal, executable events
     */
	@Override
	public List<Event> getPossibleActions() {
		Set<Event> blocked = new HashSet<>();
		Set<Event> visited = new HashSet<>();

        // Iterate Conditionals to determine other, if any, possible events we can fire
		for (CrObject o : this.crObjects.values()) {
            if (o instanceof Conditional) {
                Event A = ((Conditional) o).getIn();
                Event B = ((Conditional) o).getOut();
                visited.add(A);
                visited.add(B);

                if (!A.isExecuted()) {
                    blocked.add(B);
                }
            }

            if (o instanceof Event) {
                visited.add((Event) o);
            }
        }

        // Remove all blocked events
        visited.removeAll(blocked);
        return new ArrayList<>(visited);
    }

    /**
     * Executes given event and returns updated CrGraph
     * @param event to be executed
     * @return CrGraph
     * @throws IllegalArgumentException is the passed event isn't executable
     */
    @Override
    public CrGraph executeAction(Event event) throws IllegalArgumentException {
	    if (!this.getPossibleActions().contains(event)) {
	        throw new IllegalArgumentException("Event not executable");
        }

        if (event.getPetrinet() != null && !event.getPetrinet().isDone()) {
	        throw new IllegalArgumentException("Event's Petri net has not reached end place yet");
        }

	    // Execute event
        event.setExecuted(true);
        event.setPending(false);
        this.crObjects.put(event.getID(), event);

        // Set pending for any response related events
        for (CrObject o : this.crObjects.values()) {
            if (o instanceof Response && ((Response) o).getIn().getID() == event.getID()) {
                Event pendingEvent = ((Response) o).getOut();
                pendingEvent.setPending(true);
                this.crObjects.put(pendingEvent.getID(), pendingEvent);
            }
        }

        // Add event to trace
        this.trace.add(event);
        return this;
    }

    @Override
	public BufferedImage draw() {
		return CrDrawing.instance.draw(this);
	}

    /**
     * @return true if graph is 'done' (has no more pending events)
     */
    @Override
    public boolean isDone() {
        return this.getPendingEvents().size() == 0;
    }
};