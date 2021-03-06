package datamodel.cr;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.Stroke;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Collection;
import datamodel.Position;
import datamodel.petri.Petrinet;

public class CrDrawing {
	/**
	 * Singleton accessor for visualization methods
	 */
	public static CrDrawing instance = new CrDrawing();
	
	// Empty constructor
	private CrDrawing() {}

	private static final int IMAGE_WIDTH = 960;
	private static final int IMAGE_HEIGHT = 640;
	private ArrayList<Position> relationPos;
	private ArrayList<Position> eventPos;
	
	private static final int EVENT_HEIGHT = 100;
	private static final int LINE_HEIGHT = EVENT_HEIGHT / 5;
	private static final int EVENT_WIDTH = EVENT_HEIGHT-LINE_HEIGHT;

    private static final int CIRCLE_RADIUS = 10;
	private static final int MOVE_EVENT_X_RIGHT = 50;
	private static final int MARGIN = 3;
	
	public BufferedImage draw(CrGraph crgraph) {
		relationPos = new ArrayList<Position>();
		eventPos = new ArrayList<Position>();
		BufferedImage bi = new BufferedImage(IMAGE_WIDTH, IMAGE_HEIGHT, BufferedImage.TYPE_INT_RGB);
		Graphics2D g = bi.createGraphics();
		// Setup background
		g.setColor(Color.WHITE);
		g.fillRect(0, 0, IMAGE_WIDTH, IMAGE_HEIGHT);
		Collection<CrObject> graf = crgraph.getCrObjectValues();
		for (CrObject o: graf) {
			if (o.getClass().equals(Event.class)) {
				Event e = (Event) o;
				this.drawEvent(g, e);
			}
		    if (o.getClass().equals(Conditional.class)) {
		    	Relation r = (Relation) o;
		    	drawConditional(g, r.getIn(), r.getOut(), r.getID());
		    } 
		    else if (o.getClass().equals(Response.class)) {
		    	Response r = (Response) o;
		    	drawResponse(g, r.getIn(), r.getOut(), r.getID());
		    }
		}
	    g.dispose();
	    return bi;
	}
	
	public int getEventWidth() {
		return EVENT_WIDTH;
	}
    
	public int getEventHeight() {
		return EVENT_HEIGHT;
	}
	
  /**
   * Gives a list of all possible positions that bounds the event
   * @param e
   * @return
   */
	private ArrayList<Position> bounds(Event e) {
		ArrayList<Position> list = new ArrayList<Position>();
		int xstart = e.getX();
		int ystart = e.getY();
		int jump = CIRCLE_RADIUS+(MARGIN*2);
		int nOfWidth = (EVENT_WIDTH/2) / jump;
		
		for (int i = 0; i <= nOfWidth;i++) {
			int m = xstart + (EVENT_WIDTH/2);
			list.add(new Position(m+(i*jump),ystart));
			list.add(new Position(m-(i*jump),ystart));
			list.add(new Position(m+(i*jump),ystart + EVENT_HEIGHT));
			list.add(new Position(m-(i*jump),ystart + EVENT_HEIGHT));
		}
		
		int nOfHeight = (EVENT_HEIGHT/2) / jump;
		for (int i = 0; i <= nOfHeight;i++) {
			int m = ystart + (EVENT_HEIGHT/2);
			list.add(new Position(xstart,m+(i*jump)));
			list.add(new Position(xstart,m-(i*jump)));
			list.add(new Position(xstart + EVENT_WIDTH,m+(i*jump)));
			list.add(new Position(xstart + EVENT_WIDTH,m-(i*jump)));
		}
		return list;
	}
	  
	/**
   	* A tuple class
   	* @param <X>
   	* @param <Y>
   	*/
	private class Tuple<X, Y> { 
	  	public final X f; 
	  	public final Y s; 
	  	public Tuple(X x, Y y) { 
		  	this.f = x; 
		  	this.s = y; 
	  	} 
  	} 
 
	/**
	 * Returns distance between two positions
	 * @param p1
	 * @param p2
	 * @return
	 */
	private double distance(Position p1, Position p2) {
		return Math.sqrt(Math.pow((p1.x()-p2.x()), 2) + Math.pow((p1.y()-p2.y()), 2));
  	}

	/**
	 * Returns a tuple of the two Positions with the shortest distance between two events
	 * @param e1
	 * @param e2
	 * @return
	 */
	private Tuple<Position, Position> shortest(Event e1, Event e2) {
		ArrayList<Position> boundsE1 = bounds(e1);
		ArrayList<Position> boundsE2 = bounds(e2);
		Tuple<Position, Position> shortest = new Tuple<Position, Position>(e1.getPosition(), e2.getPosition());
		Double dis = Double.MAX_VALUE;
		for (Position p1 : boundsE1) {
			if (relationPos.contains(p1)) {
				continue;
			}
			for (Position p2 : boundsE2) { 
				if (distance(p1,p2) < dis && distance(p1,p2) > MOVE_EVENT_X_RIGHT) {
					dis = distance(p1,p2);
					shortest = new Tuple<Position, Position>(p1, p2);
				}
			} 
		}
		if (shortest.f.equals(e1.getPosition())) {
			for (Position p2 : boundsE2) { 
				if (distance(e1.getPosition(),p2) < dis && distance(e1.getPosition(),p2) > MOVE_EVENT_X_RIGHT) {
					dis = distance(e1.getPosition(),p2);
					shortest = new Tuple<Position, Position>(e1.getPosition(), p2);
				}
			} 
		}
		return shortest;
	}
	  
	/**
	 * Wrapper for drawing a Conditional relation
	 * @param g
	 * @param e1
	 * @param e2
	 * @param id
	 */
	private void drawConditional(Graphics2D g, Event e1, Event e2, int id) {
		drawArrow(g, e1, e2, Color.ORANGE,""+id, true);
	}
	  
	/**
	 * Wrapper for drawing a Response relation
	 * @param g
	 * @param e1
	 * @param e2
	 * @param id
	 */
	private void drawResponse(Graphics2D g, Event e1, Event e2, int id) {
		drawArrow(g, e1, e2, Color.BLUE, ""+id, false);
	}
	    
	/**
	 * Draws an arrow representing a relation
	 * @param g
	 * @param e1
	 * @param e2
	 * @param c
	 * @param id
	 * @param isConditional
	 */
	private void drawArrow(Graphics2D g, Event e1, Event e2, Color c, String id, boolean isConditional) {
		Tuple<Position, Position> tuple = shortest(e1, e2);
		Position p1 = tuple.f;
		Position p2 = tuple.s;
		relationPos.add(p1); 
		relationPos.add(p2); 
		AffineTransform oldXForm = g.getTransform();
		g.setColor(c);
		int ARR_SIZE = 8;
		Stroke oldS = g.getStroke();
		int strokeSize = ARR_SIZE/4;
		Stroke newS = new BasicStroke(strokeSize);
		g.setStroke(newS);
			
		double dx = p2.x() - p1.x(), dy = p2.y() - p1.y();
		double angle = Math.atan2(dy, dx);
		
		int len = (int) Math.sqrt(dx*dx + dy*dy);
		AffineTransform at = AffineTransform.getTranslateInstance(p1.x(), p1.y());
		at.concatenate(AffineTransform.getRotateInstance(angle));
		g.transform(at);
		Font fontUp = new Font("Times New Roman", Font.BOLD, 18);
		Font fontDw = new Font("Times New Roman", Font.BOLD, -18);
		FontMetrics mUp = g.getFontMetrics(fontUp);
		if (isConditional) {
			if (p1.x() > p2.x()) {
				g.setFont(fontDw);
				g.drawString(id, (len/2), -strokeSize+MARGIN);
			}
			else {
				g.setFont(fontUp);
				g.drawString(id, (len/2)-mUp.stringWidth(id), -strokeSize);
			}
			len -= CIRCLE_RADIUS;
			g.drawLine(0, 0, len-ARR_SIZE, 0);
			g.fillRoundRect(len, -(CIRCLE_RADIUS/2), CIRCLE_RADIUS, CIRCLE_RADIUS, CIRCLE_RADIUS, CIRCLE_RADIUS);
		}
		else {
			if (p1.x() > p2.x()) {
				g.setFont(fontDw);
				g.drawString(id, CIRCLE_RADIUS+(len/2), -strokeSize+MARGIN);
			}
			else {
				g.setFont(fontUp);
				g.drawString(id, CIRCLE_RADIUS+(len/2)-mUp.stringWidth(id), -strokeSize);
			}
			g.drawLine(CIRCLE_RADIUS, 0, len-ARR_SIZE, 0);
			g.fillRoundRect(0, -(CIRCLE_RADIUS/2), CIRCLE_RADIUS, CIRCLE_RADIUS, CIRCLE_RADIUS, CIRCLE_RADIUS);
		}
		g.fillPolygon(new int[] {len, len-ARR_SIZE, len-ARR_SIZE, len},
	                    new int[] {0, -ARR_SIZE, ARR_SIZE, 0}, 4);
		//reset
		g.setStroke(oldS);
		g.setTransform(oldXForm);
	}
	  
	/**
	 * Draw a String centered in the middle of a Rectangle.
	 *
	 * @param g The Graphics instance.
	 * @param text The String to draw.
	 * @param rect The Rectangle to center the text in.
	 */
	private void drawCenteredString(Graphics2D g, String text, int X, int Y, Font f) {
		// Get the FontMetrics
		Font font = f;
		if (f == null) {
			font = new Font("Serif", Font.TRUETYPE_FONT, 12);
		}
		FontMetrics metrics = g.getFontMetrics(font);
		int width = EVENT_WIDTH;
		// Determine the X coordinate for the text
		int x = ((width - metrics.stringWidth(text)) / 2) + X;
		// Determine the Y coordinate for the text (note we add the ascent, as in java 2d 0 is top of the screen)
		// Set the font
		g.setColor(Color.BLACK);
		g.setFont(font);
		// Draw the String
		int lineWidth = EVENT_WIDTH;
		int y = Y+metrics.getHeight() + LINE_HEIGHT;
		if(metrics.stringWidth(text) < lineWidth) {
			g.drawString(text, x, y);
		}
		// draws on multiple lines
		else {
			String[] words = text.split(" ");
			String currentLine = words[0];
			for(int i = 1; i < words.length; i++) {
				if(metrics.stringWidth(currentLine+words[i]) < lineWidth) {
					currentLine += " "+words[i];
				} 
				else {
					x = ((width - metrics.stringWidth(currentLine)) / 2)+X;
					g.drawString(currentLine, x, y);
					y += metrics.getHeight();
					currentLine = words[i];
				}
			}
			if(currentLine.trim().length() > 0) {
				x = ((width - metrics.stringWidth(currentLine)) / 2)+X;
				g.drawString(currentLine, x, y);
			}
		}
	}
	  
	/**
	 * Checks if a Position of an Event overlaps another an Event with Position(x,y)
	 * @param p Position of an Event
	 * @param x Coordinate of a position
	 * @param y Coordinate of a position
	 * @return
	 */
	private boolean overlaps(Position p, int x, int y) {
		return x < p.x()+MOVE_EVENT_X_RIGHT + EVENT_WIDTH && x + EVENT_WIDTH > p.x() && y < p.y() + EVENT_HEIGHT && y + EVENT_HEIGHT > p.y();
	}	
	  
	/**
	 * Function that draws a single event
	 * @param g
	 * @param event
	 */
	private void drawEvent(Graphics2D g, Event event) {
		int x = event.getX();
		int y = event.getY();
		for (Position p: eventPos) {
			if (overlaps(p, x, y)) {
				x = p.x() + EVENT_WIDTH + MOVE_EVENT_X_RIGHT;
				if (x + EVENT_WIDTH >= IMAGE_WIDTH) {
					y += EVENT_HEIGHT + MOVE_EVENT_X_RIGHT;
					x = 10;
				}
			}
		}
		// SHOULD THE NEW POSITION BE SAVED IN THE GRAPHOBJECT?
		Position newPos = new Position(x,y);
		this.eventPos.add(newPos);
		event.setPosition(newPos);
		Color borderColor = new Color(229,228,222);
		g.setColor(borderColor);
		g.drawRect(x-1, y-1, EVENT_WIDTH+1, EVENT_HEIGHT+1);
		Color backColor = new Color(249,246,237);
		g.setColor(backColor);
		g.fillRect(x, y, EVENT_WIDTH, EVENT_HEIGHT);
		g.setColor(borderColor);
		// DRAWS LINE		  
		g.drawLine(x, y+LINE_HEIGHT, x+EVENT_WIDTH, y+LINE_HEIGHT);
		// draw texts under line
		drawCenteredString(g, event.getName(),x,y, null);
		drawCenteredString(g, "(" +event.getID()+")",x,y-LINE_HEIGHT, null);
		
		// draw Petrinet
		Petrinet petri = event.getPetrinet();
		if (petri != null) {
			g.setColor(borderColor);
			g.drawRect(x+MARGIN, y+LINE_HEIGHT+MARGIN, EVENT_WIDTH-(MARGIN*2), 
					EVENT_HEIGHT-LINE_HEIGHT-(MARGIN*2));
			drawCenteredString(g, petri.getName() + "(" +petri.getID()+")",x,
					y+(EVENT_HEIGHT-(LINE_HEIGHT*2)-(MARGIN*2)), new Font("Serif", Font.ITALIC, 12));
		}
		// draw executed
		g.setFont(new Font("Serif", Font.BOLD, 16));
		if (event.isExecuted()) {
			g.setColor(Color.GREEN);
		  	g.drawString("\u2713", x+MARGIN*2, (y+EVENT_HEIGHT)-LINE_HEIGHT+MARGIN);
		}
		  // draw pending
		if (event.isPending()) {
			g.setColor(Color.BLUE);
		  	g.drawString("\u0021", x + EVENT_WIDTH - (MARGIN*3), (y+EVENT_HEIGHT)-LINE_HEIGHT+MARGIN);
		}  
	}
}	
