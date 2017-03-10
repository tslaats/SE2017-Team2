package gui;

import java.awt.Container;
import java.awt.GridLayout;

import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;


public class GraphTab {

	private Container panel;
	private ImageIcon icon;
	private ImageIcon image;
	private Boolean CrGraph;
	private JScrollPane scrPane;

	public GraphTab(Boolean CrGraph) {
		

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
		}
		
		this.scrPane = new JScrollPane(panel);
		//add(scrPane);


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

}
