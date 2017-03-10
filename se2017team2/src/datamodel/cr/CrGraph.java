package datamodel.cr;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import datamodel.Graph;
import datamodel.Semantics;
import datamodel.Visualization;

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
	public BufferedImage draw() {
		return CrDrawing.instance.draw(this);
	}
};