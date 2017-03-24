package gui;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JRadioButton;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;

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
	private HintTextField eventIDField;
	private JLabel label;
	String graph;

	public CustomOptionPane(String head, String inputRequest, String opt1, String opt2, String graph) {
		super(new JFrame(), true);
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		this.graph = graph;
		// setLocationRelativeTo(null);
		this.setTitle(head);
		this.setLayout(new FlowLayout());
		this.newGraph = new JRadioButton(opt1);
		this.newGraph.setActionCommand("new_graph");
		this.newGraph.addActionListener(this);
		this.newGraph.setSelected(true);

		this.exsitingGraph = new JRadioButton(opt2);
		this.exsitingGraph.setActionCommand("exsisting_graph");
		this.exsitingGraph.addActionListener(this);

		this.eventIDField = new HintTextField(inputRequest);

		this.graphIDField = new HintTextField("Please enter ID of the existing " + graph);
		this.graphIDField.setForeground(Color.gray);
		this.graphIDField.setEnabled(false);

		this.okButton = new JButton("Enter");
		this.okButton.addActionListener(this);

		this.cancelButton = new JButton("Cancel");
		this.cancelButton.addActionListener(this);

		Object[] array = { inputRequest, eventIDField, newGraph, exsitingGraph, graphIDField };
		Object[] options = { this.okButton, this.cancelButton };

		optionPane = new JOptionPane(array, JOptionPane.QUESTION_MESSAGE, JOptionPane.YES_NO_OPTION, null, options,
				null);
		setContentPane(optionPane);

		label = new JLabel("Please select one of the options");
		this.label.setForeground(Color.red);
		this.label.setAlignmentX(RIGHT_ALIGNMENT);
		Border border = this.label.getBorder();
		Border margin = new EmptyBorder(10, 0, 0, 10);
		this.label.setBorder(new CompoundBorder(border, margin));
		this.label.setVisible(false);
		this.add(label);

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

	public String getGraphId() {
		if (newGraph.isSelected()) {
			return null;
		} else {
			return graphIDField.getText();
		}
	}

	public String getGraphObjId() {

		return eventIDField.getText();

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		switch (e.getActionCommand()) {
		case "new_graph":
			exsitingGraph.setSelected(false);
			graphIDField.setEnabled(false);
			break;
		case "exsisting_graph":
			newGraph.setSelected(false);
			graphIDField.setEnabled(true);
			break;
		case "Enter":
			if (eventIDField.getText() == "") {
				this.label.setText("Please enter the ID of the Event");
				// show warning
				this.label.setVisible(true);
				this.revalidate();
				this.pack();
				this.repaint();
			} else if (this.exsitingGraph.isSelected() && this.graphIDField.getText() == "") {
				this.label.setText("Please enter the ID of the " + graph);
				// show warning
				this.label.setVisible(true);
				this.revalidate();
				this.pack();
				this.repaint();

			} else {
				this.option = JOptionPane.OK_OPTION;
				clearAndHide();
			}

			break;
		case "Cancel":
			this.option = JOptionPane.CANCEL_OPTION;
			clearAndHide();
			break;
		default:

			break;

		}

	}

	public int getOption() {
		return this.option;
	}

}
