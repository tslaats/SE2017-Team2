package main;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.Stroke;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import cr.CRGraph;
import cr.graphobject.GraphObject;
import cr.graphobject.event.Event;
import cr.graphobject.relation.Conditional;
import cr.graphobject.relation.Relation;
import cr.graphobject.relation.Response;

public class Visualization {
	/**
	 * Singleton accessor for visualization methods
	 */
	public static Visualization instance = new Visualization();
	
	// Empty constructor
	private Visualization() {}

	private static final int IMAGE_WIDTH = 800;
	private static final int IMAGE_HEIGHT = 600;
	private ArrayList<Position> relationPos;
	private ArrayList<Position> eventPos;
	
	private static final int EVENT_HEIGHT = 100;
	private static final int LINE_HEIGHT = EVENT_HEIGHT / 5;
	private static final int EVENT_WIDTH = EVENT_HEIGHT-LINE_HEIGHT;

    private static final int YJUMP = 10;
	private static final int MOVE_EVENT_X_RIGHT = 30;
	private static final int MARGIN = 3;
    
	public BufferedImage draw(CRGraph crgraph) {
		relationPos = new ArrayList<Position>();
		eventPos = new ArrayList<Position>();
		  BufferedImage bi = new BufferedImage(IMAGE_WIDTH, IMAGE_HEIGHT, BufferedImage.TYPE_INT_RGB);
		  Graphics2D g = bi.createGraphics();
		  // Setup background
		  g.setColor(Color.WHITE);
		  g.fillRect(0, 0, IMAGE_WIDTH, IMAGE_HEIGHT);
		  ArrayList<GraphObject> graf = crgraph.graph;
		  for (GraphObject o: graf) {
		    	if (o.getClass().equals(Event.class)) {
		    		Event e = (Event) o;
		    		this.drawEvent(g, e);
		    	}
		    }
		    for (GraphObject o: graf) {
		    	if (o.getClass().equals(Conditional.class)) {
		    		Relation r = (Relation) o;
		    		drawConditional(g, r.getIn(), r.getOut());
		    	} else if (o.getClass().equals(Response.class)) {
		    		Response r = (Response) o;
		    		drawResponse(g, r.getIn(), r.getOut());
		    	}
		    	
		    }
		    g.dispose();
		    return bi;
	  }

	  private void drawConditional(Graphics2D g, Event e1, Event e2) {
		  drawArrow(g, e1, e2, Color.BLUE);
	  }
	  
	  private void drawResponse(Graphics2D g, Event e1, Event e2) {
		  drawArrow(g, e1, e2, Color.ORANGE);
	  }
	    
	  private void drawArrow(Graphics2D g, Event e1, Event e2, Color c) {
		  int x1 = e1.getX();
		  int y1 = e1.getY();
		  int x2 = e2.getX();
		  int y2 = e2.getY();
		  if (x2 > x1) {
			  // Right side on e2
			  x1 += EVENT_WIDTH;
		  }
		  else {
			  // left side on e1
			  x2 += EVENT_WIDTH;
		  }
		  Position pnew1 = new Position(x1, y1);
		  /// GET THE TRANSOFRMED x2,y2
		  Position pnew2 = new Position(x2, y2);
		  // UPDATE THE POSITION WITH NEW X values
		  while(relationPos.contains(pnew1)) {
			  y1 += YJUMP;
			  pnew1 = new Position(x1, y1);
		  } 
		  while(relationPos.contains(pnew2)) {
			  y2 += YJUMP;
			  pnew2 = new Position(x2, y2);
		  } 
		  relationPos.add(pnew1); 
		  relationPos.add(pnew2); 
		  AffineTransform oldXForm = g.getTransform();
		  g.setColor(c);
		  int ARR_SIZE = 8;
		  Stroke oldS = g.getStroke();
		  g.setStroke(new BasicStroke(ARR_SIZE/3));

	      double dx = x2 - x1, dy = y2 - y1;
	      double angle = Math.atan2(dy, dx);
	      int len = (int) Math.sqrt(dx*dx + dy*dy);
	      AffineTransform at = AffineTransform.getTranslateInstance(x1, y1);
	      at.concatenate(AffineTransform.getRotateInstance(angle));
	      g.transform(at);
	      // Draw horizontal arrow starting in (0, 0)
	      g.drawLine(0, 0, len, 0);
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
	  private void drawCenteredString(Graphics2D g, String text, int width, int height, int X, int Y) {
	      // Get the FontMetrics
		  Font font = new Font("Serif", Font.BOLD, 12);
		  g.setColor(Color.BLACK);
	      FontMetrics metrics = g.getFontMetrics(font);
	      // Determine the X coordinate for the text
	      int x = (width - metrics.stringWidth(text)) / 2;
	      // Determine the Y coordinate for the text (note we add the ascent, as in java 2d 0 is top of the screen)
	      // Set the font
	      g.setFont(font);
	      // Draw the String
	      g.drawString(text, x+X, Y+metrics.getHeight() + LINE_HEIGHT);
	  }
	  
	  /**
	   * Checks if a Position of an Event overlaps another an Event with Position(x,y)
	   * @param p Position of an Event
	   * @param x Coordinat of a position
	   * @param y Coordinat of a position
	   * @return
	   */
	  private boolean overlaps(Position p, int x, int y) {
		  return x < p.x() + EVENT_WIDTH && x + EVENT_WIDTH > p.x() && y < p.y() + EVENT_HEIGHT && y + EVENT_WIDTH > p.y();
	  }
	  
	  private void drawEvent(Graphics2D g, Event event) {
		  int x = event.getX();
		  int y = event.getY();
		  for (Position p: eventPos) {
			  if (overlaps(p, x, y)) {
				  System.out.println(event.getName());

				  x += EVENT_WIDTH + MOVE_EVENT_X_RIGHT;
			  }
		  }
		  // SHOULD THE NEW POSITION BE SAVED IN THE GRAPHOBJECT?
		  Position newPos = new Position(x,y);
		  this.eventPos.add(newPos);
		  event.setPosition(newPos);

		  g.setColor(Color.BLACK);
		  g.drawRect(x-1, y-1, EVENT_WIDTH+1, EVENT_HEIGHT+1);
		  g.setColor(Color.LIGHT_GRAY);
		  // draw event name
		  g.fillRect(x, y, EVENT_WIDTH, EVENT_HEIGHT);
		  drawCenteredString(g, event.getName(), EVENT_WIDTH, EVENT_HEIGHT,x,y);
		  // draw line under text
		  g.setColor(Color.BLACK);
		  // DRAWS LINE		  
		  g.drawLine(x, y+LINE_HEIGHT, x+EVENT_WIDTH, y+LINE_HEIGHT);
		  
		  // draw Petri
		  if (event.isPetri()) {
			  g.setColor(Color.BLACK);
			  g.drawRect(x+MARGIN, y+LINE_HEIGHT+MARGIN, EVENT_WIDTH-(MARGIN*2), 
					  EVENT_HEIGHT-LINE_HEIGHT-(MARGIN*2));
		  }
		  // draw checkmark
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
