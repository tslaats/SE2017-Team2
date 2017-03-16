package gui;

import java.awt.BorderLayout;
import java.awt.Container;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

import datamodel.Position;

/**
 * @author MultiPeden
 *
 */
/**
 * @author MultiPeden
 *
 */
public class Main {

	private static JFrame frame = new JFrame("Graph");
	private static InitPage initpage;
	public static GUIPane guiPane = new GUIPane();
	private static MessageField messageField;
	private static JPanel panel = new JPanel();
	public static GuiController guiControlller = new GuiController();
	private static Menu crMenu;

	public static void main(String[] args) {
		// Schedule a job for the event dispatch thread:
		// creating and showing this application's GUI.
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				// Turn off metal's use of bold fonts
				UIManager.put("swing.boldMetal", Boolean.FALSE);
				createAndShowGUI();
			}
		});
	}

	/**
	 * Create the GUI and show it. For thread safety, this method should be
	 * invoked from the event dispatch thread.
	 */
	private static void createAndShowGUI() {
		// Create and set up the window.
		frame.setLayout(new BorderLayout());
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		crMenu = new Menu(guiPane);
		frame.setJMenuBar(crMenu.getMenubar());

		Container contenpane = frame.getContentPane();
		contenpane.setComponentOrientation(java.awt.ComponentOrientation.RIGHT_TO_LEFT);

		// JPanel panel = new JPanel();
		panel.setLayout(new BorderLayout());

		// JScrollPane scroller = new JScrollPane(actPane);
		initpage = new InitPage(crMenu);
		panel.add(initpage, BorderLayout.CENTER);

		messageField = new MessageField();
		JPanel msgpanel = messageField.getMsgPanel();
		panel.add(msgpanel, BorderLayout.PAGE_END);

		contenpane.add(panel);

		// Display the window.
		// frame.pack();
		frame.setSize(400, 400);
		frame.setVisible(true);
	}

	
	/**
	 * 
	 */
	public void removeInitPage() {
		frame.remove(initpage);
	}

	/**
	 * 
	 */
	public static void updateFrame() {
		// if all tabs are removed, show
		// initial page and remove CR and Petri menus
		if (GUIPane.getTabNum() == 0) {
			Menu.disableCRMenu();
			Menu.disablePetriMenu();
			Menu.disableSimulationMenu();
			panel.remove(guiPane);
			panel.add(initpage);
			panel.setSize(400, 400);
		} else {
			// remove initpage and set guiPane with tabs
			System.out.println("remove initpage");
			Menu.enableSimulationMenu();
			panel.remove(initpage);
			panel.add(guiPane);

			frame.pack();
		}
		// update canvas
		frame.revalidate();
		frame.repaint();
	}

	/**
	 * @param msg
	 */
	public static void updateUserMsg(String msg) {
		messageField.setMsgText(msg);
	}

	/**
	 * 
	 */
	public static void showPossibleActions() {
		guiPane.updateActions();
		guiPane.showActionPane();
	}


	/**
	 * 
	 */
	public static void hidePossibleActions() {
		guiPane.removeActionPane();
	}

	/**
	 * @return
	 */
	public static GraphTab getActiveTab() {
		return guiPane.getCurrentTab();
	}

	/**
	 * 
	 */
	public static void disableTabs() {
		guiPane.disableTabs();
	}

	/**
	 * 
	 */
	public static void enableTabs() {
		guiPane.enableTabs();
	}

	/**
	 * @param position
	 */
	public static void imageClicked(Position position) {
		crMenu.canvasClicked(position);

	}

}
