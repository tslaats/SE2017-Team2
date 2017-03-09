package cr.graphobject.relation;

import cr.graphobject.GraphObject;
import cr.graphobject.event.Event;

public abstract class Relation implements GraphObject {

	final Event incoming;
	final Event outgoing;
	
	Relation(Event in, Event out) {
		this.incoming = in;
		this.outgoing = out;
	}
	
	enum Type {
	    Conditional, Response 
	}
	
	abstract public Type getType();
	
	public Event getIn() {
		return this.incoming;
	}
	public Event getOut() {
		return this.outgoing;
	}

}
