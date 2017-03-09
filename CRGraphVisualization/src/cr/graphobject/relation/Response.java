package cr.graphobject.relation;

import cr.graphobject.event.Event;

public class Response extends Relation {

	private Type type;
	
	public Response(Event in, Event out) {
		super(in, out);
		this.type = Type.Response;
	}
	
	@Override
	public Type getType() {
		return this.type;
	}

}
