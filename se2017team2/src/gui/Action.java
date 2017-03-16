package gui;

import datamodel.Graph.GraphTypes;

public class Action<T> {

	private String name;
	private int id;
	
	private GraphTypes graphtype;
	
	private T actionObject;
	
	public Action(String name, int id, GraphTypes graphtype, T actionObject) {
		this.name = name;
		this.id = id;
		this.graphtype = graphtype;
		this.actionObject = actionObject;
	}
		
	@Override
	public String toString() {
		return "(" + this.getId() + ") " + this.name;
	}

	public int getId() {
		return id;
	}
	
	public String getName() {
		return this.name;
	}
	
	public T getActionObject() {
		return this.actionObject;
	}
	
	public GraphTypes getGraphType() {
		return this.graphtype;
	}
	
}
