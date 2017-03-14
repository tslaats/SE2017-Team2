package gui;

import java.awt.BorderLayout;
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

	List<Action<?>> posActions;

	private JPanel actions;
	private JScrollPane actionsScrollPane;
	// GridLayout experimentLayout = new GridLayout(0, 1);

	public ActionPane() {
		this.setLayout(new BorderLayout());
		this.actions = new JPanel();
		setBorder(BorderFactory.createLineBorder(Color.black));

		JLabel label1 = new JLabel(" Possible Actions ", null, JLabel.CENTER);
		add(label1,  BorderLayout.NORTH);


		actionsScrollPane = new JScrollPane(this.actions, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		this.actions.setLayout(new BoxLayout(this.actions, BoxLayout.Y_AXIS));
		this.add(actionsScrollPane,  BorderLayout.CENTER);
		// this.add(this.actions);

		// this.add(this.panel);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		System.out.println("button event" );
		System.out.println(e.getActionCommand());
		try {
			Main.guiControlller.ExecuteAction(posActions.get(Integer.parseInt(e.getActionCommand())));
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		Main.guiPane.updatePane();
		//updateActionPane();
	//	Main.showPossibleActions();
	//	Main.guiPane.removeActionPane();
	//	Main.guiPane.showActionPane();
		updateActionPane();
	}
	
	public void updateActionPane(){
		actions.removeAll();
	//	this.removeAll();
		try {
			posActions = Main.guiControlller.getPossibleActions();
			System.out.println(posActions.size());
			String actionName;
			int i = 0;
			for (Action<?> posAction : posActions){
				actionName = posAction.toString();
				System.out.println(actionName);
				JButton b1 = new JButton(actionName);
				b1.addActionListener(this);
				b1.setActionCommand(Integer.toString(i));
				b1.setMaximumSize(new Dimension(Integer.MAX_VALUE, b1.getMinimumSize().height));
				actions.add(b1);
				i++;
				
			}
		//	this.add(actions);
			actions.revalidate();
			actions.repaint();
			this.repaint();

//			actionsScrollPane.setSize(Integer.MAX_VALUE, 50);
//			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

		}
		
	}

}
