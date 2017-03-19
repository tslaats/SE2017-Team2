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

/**
 * 
 * @author Hjalte
 *
 */
public class CrDrawing {
	/**
	 * Singleton accessor for visualization methods
	 */
	protected static CrDrawing instance = new CrDrawing();
	
	// Empty constructor
	private CrDrawing() {}

	private static final int IMAGE_WIDTH = 800;
	private static final int IMAGE_HEIGHT = 600;
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
		  }
	      for (CrObject o: graf) {
	    	  if (o.getClass().equals(Conditional.class)) {
	    		Relation r = (Relation) o;
	    		drawConditional(g, r.getIn(), r.getOut(), r.getID());
	    	  } else if (o.getClass().equals(Response.class)) {
	    		Response r = (Response) o;
	    		drawResponse(g, r.getIn(), r.getOut(), r.getID());
	    	  }
	    }
	    g.dispose();
	    return bi;
	  }
	
	  /**
	   * Gives a list of 40 position that bounds the event
	   * @param e
	   * @return
	   */
	  private ArrayList<Position> bounds(Event e) {
		  ArrayList<Position> list = new ArrayList<Position>();
		  int xstart = e.getX();
		  int ystart = e.getY();
			
		  for (int i = xstart; i <= xstart + EVENT_WIDTH;i += CIRCLE_RADIUS) {
			  list.add(new Position(i,ystart));
			  list.add(new Position(i,ystart + EVENT_HEIGHT));
		  }
		  for (int i = ystart; i <= ystart + EVENT_HEIGHT;i += CIRCLE_RADIUS) {
			  list.add(new Position(xstart,ystart));
			  list.add(new Position(xstart + EVENT_WIDTH,ystart));
		  }
		  return list;
	  }
	  private class Tuple<X, Y> { 
		  public final X f; 
		  public final Y s; 
		  public Tuple(X x, Y y) { 
		    this.f = x; 
		    this.s = y; 
		  } 
		} 
	  private double distance(Position p1, Position p2) {
		  return Math.sqrt(Math.pow((p1.x()-p2.x()), 2) + Math.pow((p1.y()-p2.y()), 2));
	  }
	  
	  private int closer(int v1, int v2) {
		  return v2-v1;
	  }

	  private Tuple<Position, Position> shortest(Event e1, Event e2) {
		  ArrayList<Position> boundsE1 = bounds(e1);
		  ArrayList<Position> boundsE2 = bounds(e2);
		  Tuple<Position, Position> shortest = new Tuple<Position, Position>(e1.getPosition(), e2.getPosition());
		  Double dis = Double.MAX_VALUE;
		  for (Position p1 : boundsE1) {
			  for (Position p2 : boundsE2) { 
				  if (distance(p1,p2) <= dis && !relationPos.contains(p1)&& !relationPos.contains(p2)) {
					  if (distance(p1,p2) == dis) {
						  // check if closer to middle
						  System.out.println("same distance");
					  } else {
						  dis = distance(p1,p2);
						  shortest = new Tuple<Position, Position>(p1, p2);
					  }
				  }
			  } 
		  }
		  return shortest;
	  }

	  private void drawConditional(Graphics2D g, Event e1, Event e2, int id) {
		  //Color yellow = new Color(252,221,153);
		  drawArrow(g, e1, e2, Color.ORANGE,""+id, true);
	  }
	  
	  private void drawResponse(Graphics2D g, Event e1, Event e2, int id) {
		  //Color blue = new Color(147,192,235);
		  drawArrow(g, e1, e2, Color.BLUE, ""+id, false);
	  }
	    
	  private void drawArrow(Graphics2D g, Event e1, Event e2, Color c, String id, boolean isConditional) {
		  Tuple<Position, Position> tuple = shortest(e1, e2);
		  Position p1 = tuple.f;
		  Position p2 = tuple.s;
		  int x1 = p1.x();
		  int y1 = p1.y();
		  int x2 = p2.x();
		  int y2 = p2.y();
		  relationPos.add(p1); 
		  relationPos.add(p2); 
		  AffineTransform oldXForm = g.getTransform();
		  g.setColor(c);
		  int ARR_SIZE = 8;
		  Stroke oldS = g.getStroke();
		  int strokeSize = ARR_SIZE/4;
		  Stroke newS = new BasicStroke(strokeSize);
		  g.setStroke(newS);

	      double dx = x2 - x1, dy = y2 - y1;
	      double angle = Math.atan2(dy, dx);
	      int len = (int) Math.sqrt(dx*dx + dy*dy);
	      AffineTransform at = AffineTransform.getTranslateInstance(x1, y1);
	      at.concatenate(AffineTransform.getRotateInstance(angle));
	      g.transform(at);
		  Font font = new Font("Times New Roman", Font.BOLD, 18);
	      FontMetrics metrics = g.getFontMetrics(font);
	      g.setFont(font);

	      // Draw horizontal arrow starting in (0, 0)
	      if (isConditional) {
		      g.drawString(id, (len/2)-metrics.stringWidth(id), -strokeSize);
		      len -= CIRCLE_RADIUS;
		      g.drawLine(0, 0, len-ARR_SIZE, 0);
	    	  g.fillRoundRect(len, -(CIRCLE_RADIUS/2), CIRCLE_RADIUS, CIRCLE_RADIUS, CIRCLE_RADIUS, CIRCLE_RADIUS);
	      }
	      else {
		      g.drawString(id, CIRCLE_RADIUS+(len/2)-metrics.stringWidth(id), -strokeSize);

		      g.drawLine(CIRCLE_RADIUS, 0, len-ARR_SIZE, 0);
	    	  g.fillRoundRect(0, -(CIRCLE_RADIUS/2), CIRCLE_RADIUS, CIRCLE_RADIUS, CIRCLE_RADIUS, CIRCLE_RADIUS);
	      }
	      g.fillPolygon(new int[] {len, len-ARR_SIZE, len-ARR_SIZE, len},
	                    new int[] {0, -ARR_SIZE, ARR_SIZE, 0}, 4);
	      // Set the font
	      //reset
	      g.setStroke(oldS);
	      //g.setColor(Color.BLACK);
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
		  Font font = new Font("Serif", Font.TRUETYPE_FONT, 12);
	      FontMetrics metrics = g.getFontMetrics(font);
	      
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
	      else {
	    	  String[] words = text.split(" ");
	          String currentLine = words[0];
	          for(int i = 1; i < words.length; i++) {
	              if(metrics.stringWidth(currentLine+words[i]) < lineWidth) {
	                  currentLine += " "+words[i];
	              } else {
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
		  return x < p.x() + EVENT_WIDTH && x + EVENT_WIDTH > p.x() && y < p.y() + EVENT_HEIGHT && y + EVENT_HEIGHT > p.y();
	  }
	  
	  private void drawEvent(Graphics2D g, Event event) {
		  int x = event.getX();
		  int y = event.getY();
		  for (Position p: eventPos) {
			  while (overlaps(p, x, y)) {
				  x += EVENT_WIDTH;
				  if (x + EVENT_WIDTH >= IMAGE_WIDTH) {
					  y += EVENT_HEIGHT + 30;
					  x = 10;
				  }
			  }
			  if (overlaps(p,x-1,y)) 
				  x += EVENT_WIDTH + p.x() - x + MOVE_EVENT_X_RIGHT;
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
		  drawCenteredString(g, event.getName(), EVENT_WIDTH, EVENT_HEIGHT,x,y);
		  drawCenteredString(g, "(" +event.getID()+")", EVENT_WIDTH, EVENT_HEIGHT,x,y-LINE_HEIGHT);
		  
		  // draw Petri
		  if (event.getPetrinet() != null) {
			  g.setColor(borderColor);
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
