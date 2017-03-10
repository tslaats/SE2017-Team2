package datamodel.cr;

import datamodel.cr.Relation.Type;

public class Conditional extends Relation {

	public Conditional(Event in, Event out) {
		super(in, out);
	}

	@Override
	public Type getType() {
		return Type.Conditional;
	}
}
