package petriVisualization;

import java.awt.*;
import java.text.AttributedCharacterIterator;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;

import petriVisualization.Graph.Edge;
import petriVisualization.Graph.Node;


public class PetriWindow extends JComponent {

	private int width = 960;
	private int height = 640;
	private List<Node> nodes = new ArrayList<Node>();
	private List<Edge> edges = new ArrayList<Edge>();

	private int fontSize = 20;
	public float scale = 1f;
	private int nodeSize = 50; // don't change

	public PetriWindow(boolean testing) {
		JFrame f = new JFrame("Petri Model");
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.add(new JScrollPane(this), BorderLayout.CENTER);
		f.pack();
		f.setLocationByPlatform(true);
		f.setVisible(testing);
	}
	
	public void updateGraph(ArrayList<Node> inputNodes, ArrayList<Edge> inputEdges) {
		nodes = inputNodes;
		edges = inputEdges;
		repaint();
	}

	@Override
	public Dimension getPreferredSize() {
		return new Dimension(width, height);
	}

	@Override
	public void paintComponent(Graphics g) {
		g.setFont(new Font("TimesRoman", Font.PLAIN, Scale(fontSize)));
		g.setColor(new Color(0x00f0f0f0));

		for (Node node : nodes) {
			drawNode(node, g);
		}
		
		// (Paints edges on top of nodes to 		
		for (Edge edge : edges) {
			drawEdge(edge, g);
		}
		
	}

	private int Scale(int i) {
		return (int) (scale * ((float) i));
	}

	/**
	 * Draw this Place / Transition.
	 */
	private void drawNode(Node node, Graphics g) {
		g.setColor(Color.BLACK);
		int borderWidth = 5;
		int displaceY = 20;
		Rectangle b = node.b;

		// Draw Place
		if (node.type.equals("Place")) {
			g.fillOval(Scale(b.x), Scale(b.y), Scale(b.width), Scale(b.height));
			g.setColor(Color.WHITE);
			g.fillOval(Scale(b.x + borderWidth), Scale(b.y + borderWidth), Scale(b.width - borderWidth * 2),
					Scale(b.height - borderWidth * 2));
			
			g.setColor(Color.BLACK);
			
			if(node.place != null){
				String id = Integer.toString(node.place.getID());
				// Id of place
				g.drawString("Place ID: " + id, Scale(node.point.x - nodeSize), Scale(node.point.y + nodeSize + displaceY));
				
				if(node.place.hasToken()){
					
					int mul = 7;				
					g.fillOval(Scale(b.x + borderWidth * mul), Scale(b.y + borderWidth * mul), Scale(b.width - borderWidth * mul * 2),
							Scale(b.height - borderWidth * mul * 2));
				}
			}			
		} 
		// Draw Transition
		else if (node.type.equals("Transition")) {
			g.fillRect(Scale(b.x), Scale(b.y), Scale(b.width), Scale(b.height));
			
			
			if(node.transition != null && node.transition.getCrGraph() != null){
				g.setColor(Color.YELLOW);
				g.setColor(new Color(255,240,200));
			}
			else{
				g.setColor(Color.WHITE);
			}
			
			
			g.fillRect(Scale(b.x + borderWidth), Scale(b.y + borderWidth), Scale(b.width - borderWidth * 2),
					Scale(b.height - borderWidth * 2));
			
			g.setColor(Color.BLACK);
			
			if(node.transition != null){
				String id = Integer.toString(node.transition.getID());
				
					
				// Id of transition
				g.drawString("Transition ID: " + id, Scale(node.point.x - nodeSize), Scale(node.point.y + nodeSize + displaceY));		
				
				// Name of transition
				g.drawString(node.transition.getName(), Scale(node.point.x - nodeSize), Scale(node.point.y + nodeSize + displaceY + 20));
			
				if(node.transition.getCrGraph() != null){
					
					// CR graph ID
					g.drawString("CR graph ID: " + Integer.toString(node.transition.getCrGraph().getID()),
							Scale(node.point.x - nodeSize), Scale(node.point.y - nodeSize - 10));		
					
					// CR graph name
					g.drawString(node.transition.getCrGraph().getName(),
							Scale(node.point.x - nodeSize), Scale(node.point.y - nodeSize - 30));	
					
				}
			}
		}
	}

	private void drawEdge(Edge edge, Graphics g) {
		Point p1 = edge.n1.point;
		Point p2 = edge.n2.point;
		g.setColor(Color.BLACK);
		
		int displaceP1x = 0;
		int displaceP1y = 0;
		int displaceP2x = 0;
		int displaceP2y = 0;
		
		if(p1.x + nodeSize < p2.x){
			displaceP1x = nodeSize;
			displaceP2x = -nodeSize;
		}
		if (p1.x - nodeSize > p2.x){
			displaceP1x = -nodeSize;
			displaceP2x = nodeSize;
		}
		if(p1.y + nodeSize < p2.y){
			displaceP1y = nodeSize;
			displaceP2y = -nodeSize;
		}
		if (p1.y - nodeSize > p2.y){
			displaceP1y = -nodeSize;
			displaceP2y = nodeSize;
		}
		
		
//		if(edge.midPoints.size() > 0){
//			g.drawLine(
//					Scale(p1.x + displaceP1x),
//					Scale(p1.y + displaceP1y),
//					Scale(edge.midPoints.get(0).x),
//					Scale(edge.midPoints.get(0).y));
//			
//			for(int i = 0; i < edge.midPoints.size(); i++){
//				
//				if(i + 1 < edge.midPoints.size()){
//				
//					Point midPoint1 = edge.midPoints.get(i);
//					Point midPoint2 = edge.midPoints.get(i + 1);
//					
//					g.drawLine(
//							Scale(midPoint1.x),
//							Scale(midPoint1.y),
//							Scale(midPoint2.x),
//							Scale(midPoint2.y));			
//				}
//				else{
//					
//					Point midPoint1 = edge.midPoints.get(i);
//		 			g.drawLine(
//							Scale(midPoint1.x),
//							Scale(midPoint1.y),
//							Scale(p2.x + displaceP2x),
//							Scale(p2.y + displaceP2y));					
//				}
//			}		
//		}
//		else{
//			// Draws the line between the nodes
//		g.drawLine(
//				Scale(p1.x + displaceP1x),
//				Scale(p1.y + displaceP1y),
//				Scale(p2.x + displaceP2x),
//				Scale(p2.y + displaceP2y));			

		drawArrowLine(g,
				p1.x + displaceP1x,
				p1.y + displaceP1y,
				p2.x + displaceP2x,
				p2.y + displaceP2y,	15, 15);
	}
	
	private void drawArrowLine(Graphics g, int x1, int y1, int x2, int y2, int d, int h){
        int dx = x2 - x1, dy = y2 - y1;
        double D = Math.sqrt(dx*dx + dy*dy);
        double xm = D - d, xn = xm, ym = h, yn = -h, x;
        double sin = dy/D, cos = dx/D;

        x = xm*cos - ym*sin + x1;
        ym = xm*sin + ym*cos + y1;
        xm = x;

        x = xn*cos - yn*sin + x1;
        yn = xn*sin + yn*cos + y1;
        xn = x;

        int[] xpoints = {Scale(x2), Scale((int) xm), Scale((int) xn)};
        int[] ypoints = {Scale(y2), Scale((int) ym), Scale((int) yn)};

        g.drawLine(Scale(x1), Scale(y1), Scale(x2), Scale(y2));
        g.fillPolygon(xpoints, ypoints, 3);
     }
}