package datamodel.cr;

import datamodel.Position;
import datamodel.petri.Petrinet;

/*
 * Following the class diagram
 */
public class Event extends CrObject {
	
	private Position pos;
	private String name;
	private boolean pending;
	private boolean executed;
	private Petrinet petrinet;
	
	/**
	 * Basic Event constructor using default parameters
	 */
	public Event(Position pos, String name) {
		this.pos = pos;
		this.name = name;
		this.pending = false;
		this.executed = false;
	}
	
	/**
	 * Basic Event constructor using with custom pending
	 */
	public Event(Position pos, String name, boolean isPending) {
		this.pos = pos;
		this.name = name;
		this.pending = isPending;
		this.executed = false;
	}
	
	/**
	 * Creates a CR graph Event
	 * Default: 
	 * isPending = false
	 * isExecuted = false
	 * petri, null or a valid petrinet
	 * @param coords
	 * @param name
	 */
	public Event(final Position coords, final String name, final boolean pending,
					final boolean checkmark, final Petrinet petri) {
		super();
		this.setPosition(coords);
		this.name = name;
		this.setPending(pending);
		this.setExecuted(checkmark);
		this.setPetrinet(petri);
	}

	public Position getPosition() {
		return this.pos;
	}
	
	/**
	 * Gets the x coordinate.
	 * @return x coordinate
	 */
	public int getX() {
		return this.getPosition().x();
	}
	
	/**
	 * Gets the y coordinate.
	 * @return y coordinate
	 */
	public int getY() {
		return this.getPosition().y();
	}
	public void setPosition(Position coordinates) {
		this.pos = coordinates;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String eventName) {
		this.name = eventName;
	}

	public boolean isPending() {
		return pending;
	}

	public void setPending(boolean pending) {
		this.pending = pending;
	}

	public boolean isExecuted() {
		return executed;
	}

	public void setExecuted(final boolean checkmark) {
		this.executed = checkmark;
	}

	public Petrinet getPetrinet() {
		return this.petrinet;
	}

	public void setPetrinet(Petrinet petri) {
		this.petrinet = petri;
	}

}
