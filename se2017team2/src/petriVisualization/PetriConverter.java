package petriVisualization;

import java.util.ArrayList;

import datamodel.petri.PetriObject;
import datamodel.petri.Petrinet;
import datamodel.petri.Place;
import datamodel.petri.Transition;
import petriVisualization.Graph.Edge;
import petriVisualization.Graph.Node;


public class PetriConverter {

	private ArrayList<Place> places = new ArrayList<Place>();
	private ArrayList<Transition> transitions = new ArrayList<Transition>();

	public ArrayList<Node> nodes = new ArrayList<Node>();
	public ArrayList<Edge> edges = new ArrayList<Edge>();

	public void clearPetriConverter(){
		places.clear();
		transitions.clear();
		nodes.clear();
		edges.clear();
	}
	
	public void convertPetri(Petrinet petrinet) {

		places.clear();
		for (PetriObject place : petrinet.getPlaces().values()){
		
			places.add((Place) place);	
		}
					
		for (PetriObject transition : petrinet.getTransitions().values()){	
			
			transitions.add((Transition) transition);		
		}
		
		initializeGraph();

	}
	
	public void convertPetri(ArrayList<Place> inputPlaces, ArrayList<Transition> inputTransitions) {

		places = inputPlaces;
		transitions = inputTransitions;

		initializeGraph();
	}
	
	public void convertPetri(ArrayList<Place> inputPlaces, ArrayList<Transition> inputTransitions, Place startPlace) {

		places = inputPlaces;
		transitions = inputTransitions;

		initializeGraph();

	}
	

	private void initializeGraph() {

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

	
	private static void setPosition(Node node, int x, int y) {

		if (node.point == null) {
			node.setPoint(x, y);
		}
	}

	private Node getNode(Place place) {

		for (Node node : nodes) {

			if (node.place == place) {
				return node;
			}
		}
		System.out.println("Error - Place not found in node array in PetriConverter");

		return null;
	}

	private Node getNode(Transition transition) {

		for (Node node : nodes) {

			if (node.transition == transition) {
				return node;
			}
		}
		System.out.println("Error - Transition not found in node array in PetriConverter");

		return null;
	}
}
