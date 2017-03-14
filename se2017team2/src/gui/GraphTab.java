package gui;

import java.awt.Container;
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

	public GraphTab(Boolean CrGraph, Integer id) {
		this.setId(id);
		this.clickListenerActive = false;
		this.CrGraph = CrGraph;
		if (CrGraph) {
			this.icon = createImageIcon("images/crIcon.gif");
		} else {
			this.icon = createImageIcon("images/PIcon.gif");
		}

		this.image = createImageIcon("images/test.png");
		if (this.image == null) {
			this.panel = makeTextPanel("Unable to load graph first");
		} else {
			this.panel = new JLabel(image);
			this.panel.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					if (clickListenerActive) {
						System.out.println(e.getPoint());
						// System.out.println(e.getX());
						Main.imageClicked(new Position(e.getX(), e.getY()));
					}
				}
			});
		}

		this.scrPane = new JScrollPane(panel);
		// add(scrPane);

	}

	public Container getpanel() {
		return scrPane;
	}

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

	public void updateImage(String path) {

		this.panel.removeAll();

		this.image = createImageIcon(path);
		if (this.image == null) {
			System.out.println("Unable to load graph");
			String workingDir = System.getProperty("user.dir");
			System.out.println("Current working directory : " + workingDir);
		} else {
			this.panel = new JLabel(image);
			System.out.println("new img loaded");
		}
	}

	/**
	 * @return the crGraph
	 */
	public Boolean getCrGraph() {
		return CrGraph;
	}

	public void activateClickListener() {
		this.clickListenerActive = true;
	}

	public void deactivateClickListener() {
		this.clickListenerActive = false;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

}
