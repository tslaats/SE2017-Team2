package petriVisualization.Tests;

import java.util.ArrayList;

import datamodel.Position;
import datamodel.petri.Petrinet;
import petriVisualization.PetriDrawer;

public class BlackBoxTesting {

	public static void main(String[] args) throws Exception{
	
		testPetriDrawer();		
		
	}
	
	private static void testPetriDrawer() throws Exception{
		
		Petrinet petrinet = new Petrinet("Test - Petri Drawer");
		int t1 = petrinet.addTransition(new Position(3,3),"t1");
		petrinet.addArc(petrinet.getStart().getID(), t1);
		petrinet.addArc(petrinet.getEnd().getID(),t1);
		
		PetriDrawer petriDrawer = new PetriDrawer();
		
		
		
		
	}
}
