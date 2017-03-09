package tests;

import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.GridLayout;
import java.awt.image.BufferedImage;
import java.util.Arrays;
import java.util.List;

//ww w .  ja va2s  . com
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import cr.CRGraph;
import cr.graphobject.GraphObject;
import cr.graphobject.event.Event;
import cr.graphobject.relation.Conditional;
import cr.graphobject.relation.Response;
import main.Position;
import main.Visualization;

public class Main {

  public static void main(String[] args) {
    EventQueue.invokeLater(new Runnable() {
      @Override
      public void run() {
        JFrame f = new JFrame();
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.add(new MyPanel());
        f.pack();
        f.setVisible(true);
      }
    });
  }
}

@SuppressWarnings("serial")
class MyPanel extends JPanel {
  private static final int IMAGE_WIDTH = 1080;
  private static final int IMAGE_HEIGHT = 768;
  
  public MyPanel() {
    this.setLayout(new GridLayout());
    this.setPreferredSize(new Dimension(IMAGE_WIDTH, IMAGE_HEIGHT));
    Event e1 = new Event(new Position(5, 10), "First");
    Event e2 = new Event(new Position(250, 250), "Second",true,true,true);
    Event e3 = new Event(new Position(550, 250), "Third",true,false,false);
    Event e4 = new Event(new Position(560, 280), "Inside",true,false,false);
    Event e5 = new Event(new Position(540, 280), "LeftInside",true,false,true);
    GraphObject r1 = new Conditional(e1,e3);
    GraphObject r2 = new Response(e2,e3);
    GraphObject r3 = new Response(e1,e2);
    GraphObject r4 = new Response(e3,e1);
    GraphObject r5 = new Response(e4,e5);
    GraphObject r6 = new Conditional(e3,e4);


    // DRAW EVENTS BEFORE RELATIONS, SO NEW POSITION IS ALWAYS SET
    CRGraph graf = new CRGraph();
    List<GraphObject> list = Arrays.asList(e1,e2,r5,r6,e3,e4,e5,r1,r2,r3,r4);
    graf.graph.addAll(list);
    BufferedImage bi = Visualization.instance.draw(graf);
	System.out.println("Do it again");

    BufferedImage bi2 = Visualization.instance.draw(graf);
    this.add(new JLabel(new ImageIcon(bi2), JLabel.CENTER));
  }
}

