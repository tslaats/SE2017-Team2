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

    private static final int YJUMP = 10;
	private static final int MOVE_EVENT_X_RIGHT = 30;
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

	  private void drawConditional(Graphics2D g, Event e1, Event e2, int id) {
		  Color yellow = new Color(252,221,153);
		  drawArrow(g, e1, e2, yellow,""+id);
	  }
	  
	  private void drawResponse(Graphics2D g, Event e1, Event e2, int id) {
		  Color blue = new Color(147,192,235);
		  drawArrow(g, e1, e2, blue, ""+id);
	  }
	    
	  private void drawArrow(Graphics2D g, Event e1, Event e2, Color c, String id) {
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
		  Font font = new Font("Times New Roman", Font.BOLD, 18);
	      FontMetrics metrics = g.getFontMetrics(font);
	      // Determine the X coordinate for the text
	      // Determine the Y coordinate for the text (note we add the ascent, as in java 2d 0 is top of the screen)
	      // Set the font
	      g.setFont(font);
	      // Draw horizontal arrow starting in (0, 0)
	      g.drawLine(0, 0, len, 0);
	      g.fillPolygon(new int[] {len, len-ARR_SIZE, len-ARR_SIZE, len},
	                    new int[] {0, -ARR_SIZE, ARR_SIZE, 0}, 4);
	      //reset
	      g.setStroke(oldS);
	      //g.setColor(Color.BLACK);
	      g.drawString(id, (len/2)-metrics.stringWidth(id), -2);

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
	   * @param x Coordinate of a position
	   * @param y Coordinate of a position
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
		  drawCenteredString(g, "(" +event.getID()+")", EVENT_WIDTH, EVENT_HEIGHT,x,y+LINE_HEIGHT);
		  
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
