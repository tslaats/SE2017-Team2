package datamodel.cr.graphobject.relation;

import datamodel.cr.graphobject.event.Event;

public class Response extends Relation {
	
	public Response(Event in, Event out) {
		super(in, out);
	}
	
	@Override
	public Type getType() {
		return Type.Response;
	}
}
