package gui;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import datamodel.Position;

public class GraphTab {

	private Container panel;
	private ImageIcon icon;
	private ImageIcon image;
	private Boolean CrGraph;
	private JScrollPane scrPane;
	private boolean clickListenerActive;
	private Integer id;

	/**
	 * @param CrGraph
	 * @param id
	 */
	public GraphTab(Boolean CrGraph, Integer id) {

		this.setId(id);
		this.clickListenerActive = false;
		this.CrGraph = CrGraph;
		if (CrGraph) {
			this.icon = createImageIcon("images/crIcon.gif");
		} else {
			this.icon = createImageIcon("images/PIcon.gif");
		}

		// this.image = createImageIcon("images/test.png");
		try {
			this.image = new ImageIcon(Main.guiControlller.draw());
			
			
			this.panel = new JLabel(image);
			
			this.panel.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					if (clickListenerActive) {
						Main.imageClicked(new Position(e.getX(), e.getY()));
					}
				}
			});
		} catch (Exception e1) {
			this.panel = makeTextPanel("Unable to load graph first");
			Main.updateUserMsg(e1.getMessage());
		}

		// add to nested panel to avoid resizing of jlabel containing 
		// the image icen in order to get the correct position of
		// the MouseListener
		JPanel mainPanel = new JPanel(new BorderLayout());
		this.panel.setMinimumSize(this.panel.getSize());
		JPanel extraPanel = new JPanel(new FlowLayout());
		extraPanel.add(this.panel );
		
		
        // Now add the extra panel instead of l
        mainPanel.add(extraPanel, BorderLayout.CENTER);
		
	
		this.scrPane = new JScrollPane(extraPanel);
		// add(scrPane);

	}

	/**
	 * @return
	 */
	public Container getpanel() {
		return scrPane;
	}

	/**
	 * @return
	 */
	public ImageIcon getIcon() {
		return icon;
	}

	/** Returns an ImageIcon, or null if the path was invalid. */
	protected static ImageIcon createImageIcon(String path) {
		java.net.URL imgURL = GUIPane.class.getResource(path);
		if (imgURL != null) {
			return new ImageIcon(imgURL);
		} else {
			System.err.println("Couldn't find file: " + path);
			return null;
		}
	}

	private static JComponent makeTextPanel(String text) {
		JPanel panel = new JPanel(false);
		JLabel filler = new JLabel(text);
		filler.setHorizontalAlignment(JLabel.CENTER);
		panel.setLayout(new GridLayout(1, 1));
		panel.add(filler);
		return panel;
	}

	public void updateImage() {
		try {
			this.image.setImage(Main.guiControlller.draw());
		} catch (Exception e) {
			Main.updateUserWarning("Unable to load graph");
		}
	}

	/**
	 * @return the crGraph
	 */
	public Boolean getCrGraph() {
		return CrGraph;
	}

	/**
	 * 
	 */
	public void activateClickListener() {
		this.clickListenerActive = true;
	}

	/**
	 * 
	 */
	public void deactivateClickListener() {
		this.clickListenerActive = false;
	}

	/**
	 * @return
	 */
	public Integer getId() {
		return id;
	}

	/**
	 * @param id
	 */
	public void setId(Integer id) {
		this.id = id;
	}

}
