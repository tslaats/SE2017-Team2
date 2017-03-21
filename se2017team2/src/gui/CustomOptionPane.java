package gui;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JRadioButton;

public class CustomOptionPane extends JDialog implements ActionListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JRadioButton newGraph;
	private JRadioButton exsitingGraph;
	private HintTextField graphIDField;
	private JButton okButton;
	private JButton cancelButton;
	private JOptionPane optionPane;
	private int option;

	public CustomOptionPane(String head, String opt1, String opt2) {
		super(new JFrame(), true);
		// setLocationRelativeTo(null);

		this.setLayout(new FlowLayout());
		this.newGraph = new JRadioButton(opt1);
		this.newGraph.setActionCommand("new_graph");
		this.newGraph.addActionListener(this);
		this.newGraph.setSelected(true);

		this.exsitingGraph = new JRadioButton(opt2);
		this.exsitingGraph.setActionCommand("exsisting_graph");
		this.exsitingGraph.addActionListener(this);

		this.graphIDField = new HintTextField("Please enter ID of the existing graph   ");
		this.graphIDField.setForeground(Color.gray);
		this.graphIDField.setEnabled(false);

		this.okButton = new JButton("Enter");
		this.okButton.addActionListener(this);

		this.cancelButton = new JButton("Cancel");
		this.cancelButton.addActionListener(this);

		Object[] array = { head, newGraph, exsitingGraph, graphIDField };
		Object[] options = { this.okButton, this.cancelButton };

		optionPane = new JOptionPane(array, JOptionPane.QUESTION_MESSAGE, JOptionPane.YES_NO_OPTION, null, options,
				options[0]);
		setContentPane(optionPane);

		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent we) {
				/*
				 * Instead of directly closing the window, we're going to change
				 * the JOptionPane's value property.
				 */
				optionPane.setValue(new Integer(JOptionPane.CLOSED_OPTION));
			}
		});

		this.pack();
		this.setVisible(true);

	}

	private void clearAndHide() {
		// graphIDField.setText(null);
		setVisible(false);
	}

	public String getContent() {
		if (newGraph.isSelected()) {
			return null;
		} else {
			return graphIDField.getText();
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		switch (e.getActionCommand()) {
		case "new_graph":
			exsitingGraph.setSelected(false);
			// System.out.println(graphIDField.getText());
			graphIDField.setEnabled(false);
			break;
		case "exsisting_graph":
			newGraph.setSelected(false);
			graphIDField.setEnabled(true);
			break;
		case "Enter":
			this.option = JOptionPane.OK_OPTION;
			clearAndHide();
			break;
		case "Cancel":
			this.option = JOptionPane.CANCEL_OPTION;
			clearAndHide();
			break;
		default:
			System.out.println(e.getActionCommand() + " not supported");
			break;

		}


	}

	public int getOption() {
		return this.option;
	}

}
