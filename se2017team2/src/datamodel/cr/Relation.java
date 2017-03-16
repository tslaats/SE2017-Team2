package datamodel.cr;

public abstract class Relation extends CrObject {

	private final Event incoming;
	private final Event outgoing;
	
	protected Relation(Event in, Event out) {
		super();
		this.incoming = in;
		this.outgoing = out;
	}
	
	public Event getIn() {
		return this.incoming;
	}
	
	public Event getOut() {
		return this.outgoing;
	}
	
	public enum Type {
	    Conditional, Response 
	}
	
	abstract public Type getType();
}
