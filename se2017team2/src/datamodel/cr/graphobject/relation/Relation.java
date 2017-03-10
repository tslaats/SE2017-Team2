package datamodel.cr.graphobject.relation;

import datamodel.cr.graphobject.CrObject;
import datamodel.cr.graphobject.event.Event;

public abstract class Relation extends CrObject {

	private final Event incoming;
	private final Event outgoing;
	
	protected Relation(Event in, Event out) {
		this.incoming = in;
		this.outgoing = out;
	}
	
	public Event getIn() {
		return this.incoming;
	}
	
	public Event getOut() {
		return this.outgoing;
	}
	
	enum Type {
	    Conditional, Response 
	}
	
	abstract public Type getType();
}
