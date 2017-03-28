package petriVisualization.Tests;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Collection;

import datamodel.Position;
import datamodel.petri.PetriObject;
import datamodel.petri.Petrinet;
import datamodel.petri.Place;
import datamodel.petri.Transition;
import petriVisualization.PetriConverter;
import petriVisualization.PetriWindow;
import petriVisualization.Graph.Edge;
import petriVisualization.Graph.Node;

public class WhiteBoxTesting {

	
	public static void main(String args[]) throws Exception{
		
		//testPetriWindow();
		testPetriConverter();
	}
	
private static void testPetriWindow(){
		
		ArrayList<Node> nodes = new ArrayList<Node>();
		ArrayList<Edge> edges = new ArrayList<Edge>();
		
        Node place1 = new Node( "Place", new Point(100,100));
        Node transition1 = new Node("Transition", new Point(300,100));
        
        Node place2 = new Node( "Place", new Point(200,200));
        Node transition2 = new Node("Transition", new Point(200,400));
        
        Node place3 = new Node( "Place", new Point(500,500));
        Node transition3 = new Node("Transition", new Point(300,600));


        nodes.add(place1);
        nodes.add(transition1);
        
        nodes.add(place2);
        nodes.add(transition2);
        
        nodes.add(place3);
        nodes.add(transition3);

        edges.add(new Edge(place1, transition1));
        edges.add(new Edge(transition1, place2));
        edges.add(new Edge(place2, transition2));
        edges.add(new Edge(transition2, place3));
        edges.add(new Edge(place3, transition3));

        PetriWindow petriWindow = new PetriWindow(true);
        petriWindow.setVisible(true);
        petriWindow.updateGraph(nodes, edges);
	}
	
	private static void testPetriConverter() throws Exception{
		
		// instance of Petrinet defined by gui team
		// two places created by default have id 1 and id 2
		int p1 = 1;
		int p2 = 2;
		
		Petrinet petrinet = new Petrinet("Test - PetriVisualization");
		
		int t1 = petrinet.addTransition(new Position(200, 200), "Emil");
		
		petrinet.addArc(p1, t1);
		petrinet.addArc(t1, p2);
		
		PetriConverter petriConverter = new PetriConverter();
		petriConverter.convertPetri(petrinet);
        
        PetriWindow petriWindow = new PetriWindow(true);
        petriWindow.scale = 0.8f;
        petriWindow.updateGraph(petriConverter.nodes, petriConverter.edges);
	
	}
}
