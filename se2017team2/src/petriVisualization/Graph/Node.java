package petriVisualization.Graph;

import java.awt.Point;
import java.awt.Rectangle;
import java.util.ArrayList;

import datamodel.petri.Place;
import datamodel.petri.Transition;


public class Node {

	public final int nodeSize = 50; // don't change
	
	public Point point;
	public String type;
	public Rectangle b = new Rectangle();
	
	public Transition transition;
	public Place place;
	
	public ArrayList<Node> pointers = new ArrayList<Node>();

	// Initializes a Node with unspecified position
	public Node(String type) {
		this.type = type;
	}

	// Initializes a Node with specified position
	public Node(String type, Point p) {
		this.point = p;
		this.type = type;
		setBoundary();
	}
	
	// Initializes a Place Node with unspecified position
	public Node(Place place) {
		this.type = "Place";
		this.place = place;
	}

	// Initializes a Transition Node with unspecified position
	public Node(Place place, Point point) {
		this.type = "Place";
		this.place = place;
		this.point = point;
		setBoundary();
	}
	
	// Initializes a Transition Node with unspecified position
	public Node(Transition transition) {
		this.type = "Transition";
		this.transition = transition;
	}

	// Initializes a Transition Node with specified position
	public Node(Transition transition, Point point) {
		this.type = "Transition";
		this.transition = transition;
		this.point = point;
		setBoundary();
	}

	public void setPoint(int x, int y){
		point = new Point(x,y);
		setBoundary();
	}
	
	private void setBoundary() {
		b.setBounds(point.x - nodeSize, point.y - nodeSize, 2 * nodeSize, 2 * nodeSize);
	}
}
