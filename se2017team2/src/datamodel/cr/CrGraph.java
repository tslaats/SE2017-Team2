package datamodel.cr;

import java.util.ArrayList;
import java.util.List;

import datamodel.Graph;
import datamodel.Semantics;
import datamodel.Visualization;
import datamodel.cr.graphobject.CrObject;
import datamodel.cr.graphobject.event.Event;

public class CrGraph extends Graph implements Visualization, Semantics {

	private List<Event> trace;
	public ArrayList<CrObject> graph;
	
	public CrGraph() {
		this.setGraphType(GraphTypes.CR);
		this.graph = new ArrayList<CrObject>();
	}

	@Override
	public void getPossibleActions() {
		// TODO Auto-generated method stub
	}

	@Override
	public void executeAction() {
		// TODO Auto-generated method stub
	}

	@Override
	public void draw() {
		CrDrawing.instance.draw(this);	
	}
};