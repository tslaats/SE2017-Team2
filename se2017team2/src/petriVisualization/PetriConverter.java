package petriVisualization;

import java.awt.Point;
import java.util.ArrayList;

import datamodel.petri.PetriObject;
import datamodel.petri.Petrinet;
import datamodel.petri.Place;
import datamodel.petri.Transition;
import petriVisualization.Graph.Edge;
import petriVisualization.Graph.Node;


public class PetriConverter {

	static private ArrayList<Place> places = new ArrayList<Place>();
	static private ArrayList<Transition> transitions = new ArrayList<Transition>();

	static public ArrayList<Node> nodes = new ArrayList<Node>();
	static public ArrayList<Edge> edges = new ArrayList<Edge>();

	static private ArrayList<Integer> columnCounter = new ArrayList<Integer>();

	static private Node startNode;

	public static void clearPetriConverter(){
		places.clear();
		transitions.clear();
		nodes.clear();
		edges.clear();
		columnCounter.clear();
		startNode = null;
	}
	
	public static void convertPetri(Petrinet petrinet) {

		places.clear();
		for (PetriObject place : petrinet.getPlaces().values()){
		
			places.add((Place) place);	
		}
					
		for (PetriObject transition : petrinet.getTransitions().values()){	
			
			transitions.add((Transition) transition);		
		}
		
		initializeGraph();
		
		if (places.size() > 0 && startNode == null) {
			startNode = nodes.get(0);
		}
		
		//setPositionsForNodes();
	}
	
	public static void convertPetri(ArrayList<Place> inputPlaces, ArrayList<Transition> inputTransitions) {

		places = inputPlaces;
		transitions = inputTransitions;

		initializeGraph();
		
		if (inputPlaces.size() > 0 && startNode == null) {
			startNode = nodes.get(0);
		}
		
		setPositionsForNodes();
	}
	
	public static void convertPetri(ArrayList<Place> inputPlaces, ArrayList<Transition> inputTransitions, Place startPlace) {

		places = inputPlaces;
		transitions = inputTransitions;

		initializeGraph();

		startNode = getNode(startPlace);
		
		//setPositionsForNodes();
	}
	

	private static void initializeGraph() {

		nodes = new ArrayList<Node>();
		edges = new ArrayList<Edge>();
		
		for (Place place : places) {
			
			Node node = new Node(place);			
			if(place.getPos() != null){
				int x = place.getPos().x();
				int y = place.getPos().y();
				setPosition(node,x,y);

			}
			nodes.add(node);
		}

		for (Transition transition : transitions) {
			Node node = new Node(transition);			
			if(transition.getPos() != null){
				int x = transition.getPos().x();
				int y = transition.getPos().y();
				setPosition(node,x,y);

			}
			nodes.add(node);
		}

		for (Place place : places) {
			Node node = getNode(place);

			for (PetriObject transition : place.getOutgoing().values()) {

				Node pointedNode = getNode((Transition) transition);
				node.pointers.add(pointedNode);
				edges.add(new Edge(node, pointedNode));
			}
		}

		for (Transition transition : transitions) {
			Node node = getNode(transition);

			for (PetriObject place : transition.getOutgoing().values()) {

				Node pointedNode = getNode((Place) place);
				node.pointers.add(pointedNode);
				edges.add(new Edge(node, pointedNode));
			}
		}
	}

	private static void setPositionsForNodes(){
		
		setPositionsForNodesRecursive(startNode, 1, 1);
		
		for(Node node: nodes){
			
			if(node.point == null){
				// if the node's position is not already set
				setPositionsForNodesRecursive(node, columnCounter.size(), 1);
			}	
		}
	}
	
	private static void setPositionsForNodesRecursive(Node node, int x, int y) {

		setPosition(node, x, y);

		// Increment columnCounter if necessary
		while (columnCounter.size() <= x) {
			columnCounter.add(0);
		}

		x++; // jump to next column

		for (Node pointedNode : node.pointers) {
			y = incrementColumnCounterAndGetY(x);
			setPositionsForNodesRecursive(pointedNode, x, y);
		}
	}

	private static void setPosition(Node node, int x, int y) {

		if (node.point == null) {
			node.setPoint(x, y);
		}
	}

	private static Node getNode(Place place) {

		for (Node node : nodes) {

			if (node.place == place) {
				return node;
			}
		}
		System.out.println("Error - Place not found in node array in PetriConverter");

		return null;
	}

	private static Node getNode(Transition transition) {

		for (Node node : nodes) {

			if (node.transition == transition) {
				return node;
			}
		}
		System.out.println("Error - Transition not found in node array in PetriConverter");

		return null;
	}

	private static int incrementColumnCounterAndGetY(int x) {
		columnCounter.set(x-1, columnCounter.get(x-1) + 1);
		return columnCounter.get(x-1);
	}

	// ----------- Methods called from PetriDrawer -------------
	
	public static void changeArc(Place place, Transition transition, ArrayList<Integer> points){
		
		if(getNode(place) == null){
			// with id etc.
			System.out.println("Place not detected in input petri");
			System.out.println("Nothing happened");
		}
		if(getNode(transition) == null){
			// with id etc.
			System.out.println("Place not detected in input petri");
			System.out.println("Nothing happened");
		}
		
		changeArc(getNode(place),getNode(transition), points);
	}
	
	public static void changeArc(Transition transition, Place place, ArrayList<Integer> points){
		
		if(getNode(transition) == null){
			// with id etc.
			System.out.println("Place not detected in input petri");
			System.out.println("Nothing happened");
		}
		if(getNode(place) == null){
			// with id etc.
			System.out.println("Place not detected in input petri");
			System.out.println("Nothing happened");
		}
		
		changeArc(getNode(transition),getNode(place), points);
	}
	
	private static void changeArc(Node n1, Node n2, ArrayList<Integer> points){
		
		ArrayList<Point> arcPoints = new ArrayList<Point>();
		
		for(Edge edge: edges){
			if(edge.n1 == n1 && edge.n2 == n2){
				
				for(int i = 0; i < points.size()/2; i++){
				
					int x = points.get(i*2);
					int y = points.get(i*2 + 1);
					
					arcPoints.add(new Point(x,y));		
				}
				edge.midPoints = arcPoints;
				System.out.println("Arc sucessfully changed");
				return;
			}
		}
		System.out.println("Error - Edge going from input1 to input2 not found");
		System.out.println("Nothing happened");
	}
}
