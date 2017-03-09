package cr.graphobject.relation;

import cr.graphobject.event.Event;

public class Conditional extends Relation {
	
	private Type type;
	
	public Conditional(Event in, Event out) {
		super(in, out);
		this.type = Type.Conditional;
	}

	@Override
	public Type getType() {
		return this.type;
	}
}
