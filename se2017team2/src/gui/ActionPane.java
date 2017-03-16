package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;

public class ActionPane extends JPanel implements ActionListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	List<Action<?>> posActions;

	private JPanel actions;
	private JScrollPane actionsScrollPane;

	public ActionPane() {
		this.setLayout(new BorderLayout());
		this.actions = new JPanel();
		setBorder(BorderFactory.createLineBorder(Color.black));

		JLabel label1 = new JLabel(" Possible Actions ", null, JLabel.CENTER);
		add(label1, BorderLayout.NORTH);

		actionsScrollPane = new JScrollPane(this.actions, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
				JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		this.actions.setLayout(new BoxLayout(this.actions, BoxLayout.Y_AXIS));
		this.add(actionsScrollPane, BorderLayout.CENTER);
		// this.add(this.actions);

		// this.add(this.panel);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		System.out.println("button event");
		System.out.println(e.getActionCommand());
		try {
			Main.guiControlller.executeAction(posActions.get(Integer.parseInt(e.getActionCommand())));
		} catch (Exception e1) {
			Main.updateUserMsg(e1.getMessage());
		}
		Main.guiPane.updatePane();
		updateActionPane();
	}

	/**
	 * 
	 */
	public void updateActionPane() {
		actions.removeAll();
		try {
			posActions = Main.guiControlller.getPossibleActions();
			System.out.println(posActions.size());
			String actionName;
			int i = 0;
			for (Action<?> posAction : posActions) {
				actionName = posAction.toString();
				System.out.println(actionName);
				JButton b1 = new JButton(actionName);
				b1.setHorizontalAlignment(SwingConstants.LEFT);
				b1.addActionListener(this);
				b1.setActionCommand(Integer.toString(i));
				b1.setMaximumSize(new Dimension(Integer.MAX_VALUE, b1.getMinimumSize().height));
				actions.add(b1);
				i++;
			}
			actions.revalidate();
			actions.repaint();
			this.repaint();

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Main.updateUserMsg(e.getMessage());
		}
	}
}
