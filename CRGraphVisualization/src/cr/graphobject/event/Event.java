package cr.graphobject.event;

import cr.graphobject.GraphObject;
import main.Position;

/*
 * Following the class diagram
 */
public class Event implements GraphObject {
	
	private Position pos;
	private String name;
	private boolean pending;
	private boolean executed;
	private boolean petri;
	
	/**
	 * Creates a CR graph Event
	 * Default: 
	 * isPending = false
	 * isExecuted = false
	 * isPetri = false
	 * @param coords
	 * @param name
	 */
	public Event(final Position coords, final String name) {
		this.setPosition(coords);
		this.name = name;
		this.setPending(false);
		this.setExecuted(false);
		this.setPetri(false);
	}
	
	public Event(final Position coords, final String name, final boolean pending) {
		this.setPosition(coords);
		this.name = name;
		this.setPending(pending);
		this.setExecuted(false);
		this.setPetri(false);
		
	}
	
	public Event(final Position coords, final String name, 
					final boolean pending, final boolean checkmark) {
		this.setPosition(coords);
		this.name = name;
		this.setPending(pending);
		this.setExecuted(checkmark);
		this.setPetri(false);
	}

	public Event(final Position coords, final String name, final boolean pending,
					final boolean checkmark, final boolean isPetri) {
		this.setPosition(coords);
		this.name = name;
		this.setPending(pending);
		this.setExecuted(checkmark);
		this.setPetri(isPetri);
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

	public void setExecuted(boolean checkmark) {
		this.executed = checkmark;
	}

	public boolean isPetri() {
		return petri;
	}

	public void setPetri(boolean petri) {
		this.petri = petri;
	}

}
