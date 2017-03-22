package gui;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * @author MultiPeden
 *
 */
public class InitPage extends JPanel implements ActionListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	protected JButton newCrButton, newPetriButton;
	private Menu crMenu;

	public InitPage(Menu crMenu) {
		this.crMenu = crMenu;

		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		JButton newPetriButton = new JButton("New Petri Net");
		JButton newCrButton = new JButton("New CR Graph");

		newCrButton.addActionListener(this);
		newCrButton.setActionCommand("new_cr");
		newCrButton.setAlignmentX(Component.CENTER_ALIGNMENT);

		newPetriButton.addActionListener(this);
		newPetriButton.setActionCommand("new_petri");
		newPetriButton.setAlignmentX(Component.CENTER_ALIGNMENT);

		JLabel statusLabel = new JLabel("Please create a new graph");
		statusLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
		// statusLabel.setLocation(150, 150);

		add(statusLabel);
		add(newCrButton);
		add(newPetriButton);

	}

	/* (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand() == "new_cr") {
			GuiController.ActiveGraphID  = crMenu.createGraph(true);
///			Main.updateUserMsg("Added a new CR Graph");
		} else {
			GuiController.ActiveGraphID = crMenu.createGraph(false);
	//		Main.updateUserMsg("Added a new Petri Net");
		}
	}

}
