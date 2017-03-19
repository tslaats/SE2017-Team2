package petriVisualization;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import datamodel.petri.Petrinet;

public class PetriDrawer {

	
	private PetriWindow petriWindow;
	
	/**
	 * Initialise a PetriDrawer
	 */
	public PetriDrawer(){
		PetriConverter.clearPetriConverter();
		petriWindow = new PetriWindow(false);
	}

	/**
	 * zoom = 1f by default. zoom = 2f will make everything twice as big.
	 * zoom = 0.5f will make everything twice as small
	 * 
	 * @param zoom
	 */
	public void setZoom(float zoom){
		petriWindow.scale = zoom;
	}
	
	/**
	 * Get the current some value of the petrinet
	 * 
	 * @return
	 */
	public float getZoom(){
		return petriWindow.scale;
	}
	
	public BufferedImage draw(Petrinet petrinet){
		PetriConverter.convertPetri(petrinet);
		petriWindow.updateGraph(PetriConverter.nodes, PetriConverter.edges);
		
		BufferedImage bi = (BufferedImage) petriWindow.createImage(petriWindow.getWidth(), petriWindow.getHeight());
		bi.createGraphics();
		Graphics g = bi.getGraphics();
		petriWindow.paint(g);
		return bi;					
	}
	
	/**
	 * Modify the @arc going from place to @transition. The initial and end point
	 * of the arc is set. Adds a set of points, which the arc will pass through
	 * in between.
	 * 
	 * @param place
	 * @param transition
	 * @param points
	 */
//	public void changeArc(Place place, Transition transition, ArrayList<Integer> points){
//				
//		if(points.size() % 2 == 1){
//			System.out.println("Error - odd numbers of integers given as input");
//			System.out.println("Nothing happened");
//			return;
//		}
//
//		PetriConverter.changeArc(place, transition, points);
//	}
}