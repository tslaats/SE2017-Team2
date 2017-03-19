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

	
	public static void main(String args[]){
		
		testPetriWindow();
	}
	
	private static void testPetriWindow(){
		
		ArrayList<Node> nodes = new ArrayList<Node>();
		ArrayList<Edge> edges = new ArrayList<Edge>();
		
        Node place1 = new Node( "Place", new Point(100,100));
        Node transition1 = new Node("Transition", new Point(300,100));

        nodes.add(place1);
        nodes.add(transition1);

        edges.add(new Edge(place1, transition1));

        PetriWindow petriWindow = new PetriWindow(true);
        petriWindow.setVisible(true);
        petriWindow.updateGraph(nodes, edges);
	}
	
	private static void testPetriConverter(){
		
		Petrinet petrinet = new Petrinet("Test - PetriVisualization");
        PetriConverter.convertPetri(petrinet);
        
        PetriWindow petriWindow = new PetriWindow(true);
        petriWindow.setVisible(true);
        petriWindow.updateGraph(PetriConverter.nodes, PetriConverter.edges);
	
	}
}
