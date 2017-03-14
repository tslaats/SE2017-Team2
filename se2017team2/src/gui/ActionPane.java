package gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

public class ActionPane extends JPanel implements ActionListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private List<String> possibleActions;

	private JPanel actions;

	// GridLayout experimentLayout = new GridLayout(0, 1);

	public ActionPane() {

		this.actions = new JPanel();
		setBorder(BorderFactory.createLineBorder(Color.black));

		JLabel label1 = new JLabel("Possible Actions", null, JLabel.CENTER);
		add(label1);

		this.possibleActions = new ArrayList<String>();
		this.possibleActions.add("Ole");
		this.possibleActions.add("Ernst");
		this.possibleActions.add("Jodle");
		this.possibleActions.add("Birge");
		this.possibleActions.add("Birge");
		this.possibleActions.add("Birge");

		this.possibleActions.add("Birge");

		this.possibleActions.add("Birge");

		this.possibleActions.add("Birge");
		this.possibleActions.add("Birge");

		this.possibleActions.add("Birge");
		this.possibleActions.add("Birge");
		this.possibleActions.add("Birge");
		this.possibleActions.add("Birge");
		this.possibleActions.add("Birge");
		this.possibleActions.add("Birge");

		// this.panel.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

		// this.panel.setLayout(experimentLayout);
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

		for (String actions : possibleActions) {

			JButton b1 = new JButton(actions);
			// b1.setVerticalTextPosition(AbstractButton.CENTER);
			// b1.setHorizontalTextPosition(AbstractButton.EAST); //aka LEFT,
			// for left-to-right locales
			// b1.setMnemonic(KeyEvent.VK_D);
			b1.setActionCommand(actions);
			b1.setMaximumSize(new Dimension(Integer.MAX_VALUE, b1.getMinimumSize().height));
			this.actions.add(b1);

		}
		this.actions.setLayout(new BoxLayout(this.actions, BoxLayout.Y_AXIS));
		this.add(new JScrollPane(this.actions, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
				JScrollPane.HORIZONTAL_SCROLLBAR_NEVER));
		// this.add(this.actions);

		// this.add(this.panel);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub

	}

}
