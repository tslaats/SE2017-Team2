package petriVisualization;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import datamodel.petri.Petrinet;

public class PetriDrawer {

	
	private PetriWindow petriWindow;
	private PetriConverter petriConverter;
	
	/**
	 * Initialise a PetriDrawer
	 */
	public PetriDrawer(){
		petriConverter = new PetriConverter();
		petriConverter.clearPetriConverter();
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
	 * Get the current s value of the petrinet
	 * 
	 * @return
	 */
	public float getZoom(){
		return petriWindow.scale;
	}
	
	public BufferedImage draw(Petrinet petrinet){
		petriConverter.clearPetriConverter();
		petriConverter.convertPetri(petrinet);
		petriWindow.updateGraph(petriConverter.nodes, petriConverter.edges);
		
		BufferedImage bi = (BufferedImage) petriWindow.createImage(petriWindow.getWidth(), petriWindow.getHeight());
		bi.createGraphics();
		Graphics g = bi.getGraphics();
		petriWindow.paint(g);
		return bi;					
	}
}