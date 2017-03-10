package gui;

import java.awt.event.*;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.KeyStroke;
import javax.swing.ImageIcon;

/*
 */
public class Menu implements ActionListener, ItemListener {
	private String newline = "\n";

	private JMenuBar menuBar;
	private JMenu menu, submenu;
	private static JMenu menuCR, MenuPetri, menuVis;
	private JMenuItem menuItem;

	public JMenuBar createMenuBar() {

		// Create the menu bar.
		menuBar = new JMenuBar();

		// Build the first menu.
		menu = new JMenu("New");
		menu.setMnemonic(KeyEvent.VK_A);
		menu.getAccessibleContext().setAccessibleDescription("Menu for CR-graphs");
		menuBar.add(menu);

		// a group of JMenuItems
		menuItem = new JMenuItem("New CR-graph", KeyEvent.VK_T);
		// menuItem.setMnemonic(KeyEvent.VK_T); //used constructor instead
		menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_1, ActionEvent.ALT_MASK));
		menuItem.getAccessibleContext().setAccessibleDescription("Creates a new CR-graph");
		menuItem.addActionListener(this);
		menuItem.setActionCommand("new_cr");
		menu.add(menuItem);

		menuItem = new JMenuItem("New Petri-graph", KeyEvent.VK_T);
		// menuItem.setMnemonic(KeyEvent.VK_T); //used constructor instead
		menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_2, ActionEvent.ALT_MASK));
		menuItem.getAccessibleContext().setAccessibleDescription("Creates a new Petri-graph");
		menuItem.addActionListener(this);
		menuItem.setActionCommand("new_petri");
		menu.add(menuItem);

		// Build CR menu in the menu bar.
		menuCR = new JMenu("CR");
		menuCR.setMnemonic(KeyEvent.VK_N);
		menuCR.getAccessibleContext().setAccessibleDescription("This menu does nothing");
		menuCR.setEnabled(false);
		menuBar.add(menuCR);

		submenu = new JMenu("Event");
		submenu.setMnemonic(KeyEvent.VK_S);

		menuItem = new JMenuItem("Add");
		menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_2, ActionEvent.ALT_MASK));
		menuItem.addActionListener(this);
		menuItem.setActionCommand("new_event");
		submenu.add(menuItem);

		menuItem = new JMenuItem("Delete");
		menuItem.addActionListener(this);
		menuItem.setActionCommand("delete_event");
		submenu.add(menuItem);
		menuCR.add(submenu);

		submenu = new JMenu("Condition");
		submenu.setMnemonic(KeyEvent.VK_S);

		menuItem = new JMenuItem("Add");
		// menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_2,
		// ActionEvent.ALT_MASK));
		menuItem.addActionListener(this);
		menuItem.setActionCommand("new_condition");
		submenu.add(menuItem);

		menuItem = new JMenuItem("Delete");
		menuItem.addActionListener(this);
		submenu.add(menuItem);
		menuItem.setActionCommand("delete_condition");
		menuCR.add(submenu);

		submenu = new JMenu("Response");
		submenu.setMnemonic(KeyEvent.VK_S);

		menuItem = new JMenuItem("Add");
		// menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_2,
		// ActionEvent.ALT_MASK));
		menuItem.addActionListener(this);
		menuItem.setActionCommand("new_response");
		submenu.add(menuItem);

		menuItem = new JMenuItem("Delete");
		menuItem.addActionListener(this);
		menuItem.setActionCommand("delete_response");
		submenu.add(menuItem);
		menuCR.add(submenu);

		// Build Petri menu in the menu bar.
		MenuPetri = new JMenu("Petri");
		MenuPetri.setMnemonic(KeyEvent.VK_N);
		MenuPetri.getAccessibleContext().setAccessibleDescription("This menu does nothing");
		MenuPetri.setEnabled(false);

		submenu = new JMenu("Place");
		submenu.setMnemonic(KeyEvent.VK_P);

		menuItem = new JMenuItem("Add");
		// menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_2,
		// ActionEvent.ALT_MASK));
		menuItem.addActionListener(this);
		menuItem.setActionCommand("new_place");
		submenu.add(menuItem);

		menuItem = new JMenuItem("Delete");
		menuItem.addActionListener(this);
		menuItem.setActionCommand("delete_place");
		submenu.add(menuItem);
		MenuPetri.add(submenu);

		submenu = new JMenu("Transition");
		submenu.setMnemonic(KeyEvent.VK_T);

		menuItem = new JMenuItem("Add");
		// menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_2,
		// ActionEvent.ALT_MASK));
		menuItem.addActionListener(this);
		menuItem.setActionCommand("new_transition");
		submenu.add(menuItem);

		menuItem = new JMenuItem("Delete");
		menuItem.addActionListener(this);
		menuItem.setActionCommand("delete_transition");
		submenu.add(menuItem);
		MenuPetri.add(submenu);

		menuBar.add(MenuPetri);
		
		

		// Build the first menu.
		menuVis = new JMenu("Visualization");
		menu.setMnemonic(KeyEvent.VK_V);
		menu.getAccessibleContext().setAccessibleDescription("Menu for Visualization");
		menuBar.add(menuVis);
		
		
		
		
		menuItem = new JMenuItem("Start Visualization", KeyEvent.VK_S);
		// menuItem.setMnemonic(KeyEvent.VK_T); //used constructor instead
		//menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_2, ActionEvent.ALT_MASK));
		menuItem.getAccessibleContext().setAccessibleDescription("Start Visualization");
		menuItem.addActionListener(this);
		menuItem.setActionCommand("start_visualization");
		menuVis.add(menuItem);
		
		menuItem = new JMenuItem("Stop Visualization", KeyEvent.VK_T);
		// menuItem.setMnemonic(KeyEvent.VK_T); //used constructor instead
		//menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_2, ActionEvent.ALT_MASK));
		menuItem.getAccessibleContext().setAccessibleDescription("Stop Visualization");
		menuItem.addActionListener(this);
		menuItem.setActionCommand("stop_visualization");
		menuVis.add(menuItem);
		
		
		

		
		

		return menuBar;
	}

	/** Returns an ImageIcon, or null if the path was invalid. */
	protected static ImageIcon createImageIcon(String path) {
		java.net.URL imgURL = Menu.class.getResource(path);
		if (imgURL != null) {
			return new ImageIcon(imgURL);
		} else {
			System.err.println("Couldn't find file: " + path);
			return null;
		}
	}

	public void actionPerformed(ActionEvent e) {

		String action = e.getActionCommand();

		switch (action) {
		case "new_cr":
			newCR();
			Main.updateUserMsg("Added a new CR Graph");
			// tabbedPane.setMnemonicAt(tabNum, KeyEvent.VK_1);

			break;
		case "new_petri":
			newPetri();
			Main.updateUserMsg("Added a new Petri Net");
			break;

		case "new_event":
			Main.updateUserMsg("Add event, not implemented yet");
			break;
		case "delete_event":
			Main.updateUserMsg("Delete event, not implemented yet");
			break;
		case "new_condition":
			Main.updateUserMsg("Add condition, not implemented yet");
			break;
		case "delete_condition":
			Main.updateUserMsg("Delete condition, not implemented yet");
			break;
		case "new_response":
			Main.updateUserMsg("Add response, not implemented yet");
			break;
		case "delete_response":
			Main.updateUserMsg("Delete response, not implemented yet");
			break;
		case "new_place":
			Main.updateUserMsg("Add place, not implemented yet");
			break;
		case "delete_place":
			Main.updateUserMsg("Delete place, not implemented yet");
			break;
		case "new_transition":
			Main.updateUserMsg("Add transition, not implemented yet");
			break;
		case "delete_transition":
			Main.updateUserMsg("Delete transition, not implemented yet");
			break;
		case "start_visualization":
			Main.showPossibleActions();
			Main.updateUserMsg("Started visualization");
			break;
		case "stop_visualization":
			Main.hidePossibleActions();
			Main.updateUserMsg("Stopped visualization");
			break;
		default:
			System.out.println(action);
			Main.updateUserMsg("Invalid Button pressed!");
			break;
		}


	}

	public void itemStateChanged(ItemEvent e) {
		JMenuItem source = (JMenuItem) (e.getSource());
		String s = "Item event detected." + newline + "    Event source: " + source.getText() + " (an instance of "
				+ getClassName(source) + ")" + newline + "    New state: "
				+ ((e.getStateChange() == ItemEvent.SELECTED) ? "selected" : "unselected");
		source.getAction();
		System.out.println(s);
	}


	protected String getClassName(Object o) {
		String classString = o.getClass().getName();
		int dotIndex = classString.lastIndexOf(".");
		return classString.substring(dotIndex + 1);
	}

	public static void newCR() {
		JFrame frameCR = new JFrame("InputDialog");
		// prompt the user to enter the name of the new petri graph
		String nameCR = JOptionPane.showInputDialog(frameCR, "Please enter the name of the new CR graph");
		if (nameCR != null) {
			int tabNum = GUIPane.getTabNum() + 1;
			GUIPane.addGraphTab(nameCR + " #" + tabNum, true);
			Main.updateFrame();
		}

	}

	public static void newPetri() {

		JFrame framePetri = new JFrame("InputDialog");
		// prompt the user to enter the name of the new petri graph
		String namePetri = JOptionPane.showInputDialog(framePetri, "Please enter the name of the new Petri graph");
		if (namePetri != null) {
			GUIPane.addGraphTab(namePetri + " #" + (GUIPane.getTabNum() + 1), false);
			Main.updateFrame();

		}

	}

	public static void enableCRMenu() {
		menuCR.setEnabled(true);

	}

	public static void disableCRMenu() {
		menuCR.setEnabled(false);

	}

	public static void enablePetriMenu() {
		MenuPetri.setEnabled(true);

	}

	public static void disablePetriMenu() {
		MenuPetri.setEnabled(false);

	}
	
	public static void enableVisMenu() {
		menuVis.setEnabled(true);

	}

	public static void disableVisMenu() {
		menuVis.setEnabled(false);

	}

}