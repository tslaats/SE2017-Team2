package gui;

import java.awt.Component;
import java.awt.event.*;
import java.util.HashSet;
import java.util.Set;

import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

import javax.swing.JTextField;
import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.KeyStroke;
import javax.swing.ImageIcon;

import datamodel.Graph;
import datamodel.Position;

/*
 */
/**
 * @author MultiPeden
 *
 */
public class Menu implements ActionListener {

	private JMenuBar menuBar;
	private JMenu menu, submenu;
	private static JMenu menuCR, MenuPetri, menuSimulation;
	private JMenuItem menuItem;
	private JFrame inputDialog = new JFrame("InputDialog");
	private String clickArgument;
	private Set<JMenuItem> disabledMenus;
	private ClickType clickType;
	private GUIPane guiPane;


	/**
	 * @author MultiPeden
	 *
	 */
	private enum ClickType {
		EVENT, TRANSITION, PLACE
	}

	/**
	 * @param guiPane
	 */
	public Menu(GUIPane guiPane) {

		this.guiPane = guiPane;

		// Create the menu bar.
		menuBar = new JMenuBar();
		this.disabledMenus = new HashSet<JMenuItem>();
		this.clickArgument = "";

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

		submenu = new JMenu("Arc");
		submenu.setMnemonic(KeyEvent.VK_A);

		menuItem = new JMenuItem("Add");
		// menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_2,
		// ActionEvent.ALT_MASK));
		menuItem.addActionListener(this);
		menuItem.setActionCommand("new_arc");
		submenu.add(menuItem);

		menuItem = new JMenuItem("Delete");
		menuItem.addActionListener(this);
		menuItem.setActionCommand("delete_arc");
		submenu.add(menuItem);
		MenuPetri.add(submenu);

		menuBar.add(MenuPetri);

		// Build the first menu.
		menuSimulation = new JMenu("Simulation");
		menu.setMnemonic(KeyEvent.VK_V);
		menu.getAccessibleContext().setAccessibleDescription("Menu for Simulation");
		menuBar.add(menuSimulation);

		menuItem = new JMenuItem("Start Simulation", KeyEvent.VK_S);
		// menuItem.setMnemonic(KeyEvent.VK_T); //used constructor instead
		// menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_2,
		// ActionEvent.ALT_MASK));
		menuItem.getAccessibleContext().setAccessibleDescription("Start Simulation");
		menuItem.addActionListener(this);
		menuItem.setActionCommand("start_simulation");
		menuSimulation.add(menuItem);

		menuItem = new JMenuItem("Stop Simulation", KeyEvent.VK_T);
		// menuItem.setMnemonic(KeyEvent.VK_T); //used constructor instead
		// menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_2,
		// ActionEvent.ALT_MASK));
		menuItem.getAccessibleContext().setAccessibleDescription("Stop Simulation");
		menuItem.addActionListener(this);
		menuItem.setActionCommand("stop_simulation");
		menuSimulation.add(menuItem);
		menuSimulation.setEnabled(false);

	}

	/**
	 * @return
	 */
	public JMenuBar getMenubar() {
		return menuBar;
	}

	/** Returns an ImageIcon, or null if the path was invalid. */
	/**
	 * @param path
	 * @return
	 */
	protected static ImageIcon createImageIcon(String path) {
		java.net.URL imgURL = Menu.class.getResource(path);
		if (imgURL != null) {
			return new ImageIcon(imgURL);
		} else {
			System.err.println("Couldn't find file: " + path);
			return null;
		}
	}

	/* (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionPerformed(ActionEvent e) {

		String action = e.getActionCommand();
		String name;
		JTextField incomingIDs;
		JTextField outgoingIDs;
		int ID;
		int option;
		String invID = "ID's must be integers";

		switch (action) {
		case "new_cr":
			createGraph(true);
			break;
		case "new_petri":
			createGraph(false);
			break;
		case "new_event":

			// prompt the user to enter the name of the new Event
			String nameEvent = JOptionPane.showInputDialog(inputDialog, "Please enter the name of the new Event");

			// if the name is successfully entered, add Event
			if (nameEvent != null) {
				disableMenubar();
				GraphTab graphtab = Main.getActiveTab();
				Main.disableTabs();
				graphtab.activateClickListener();
				this.clickArgument = nameEvent;
				Main.updateUserMsg(String.format("Please click where you want to ad event: %s", nameEvent));
				clickType = ClickType.EVENT;

			}
			break;
		case "delete_event":
			// prompt the user to enter the name of the new Event
			name = JOptionPane.showInputDialog(inputDialog, "Please enter the ID of the Event you want to delete");
			// if the name is successfully entered, add Event
			if (name != null) {
				try {
					// event id
					ID = Integer.parseInt(name);
					try {
						Main.guiControlller.deleteEvent(ID);
						Main.updateUserMsg("Deleted eventID: " + ID);
					} catch (Exception e1) {
						Main.updateUserMsg(e1.getMessage());
					}
				} catch (NumberFormatException e2) {
					Main.updateUserMsg(invID);
				}
			}
			break;
		case "new_condition":

			incomingIDs = new JTextField();
			outgoingIDs = new JTextField();
			Object[] message = { "Incoming ID:", incomingIDs, "Outgoing ID:", outgoingIDs };

			option = JOptionPane.showConfirmDialog(null, message, "Add Condition", JOptionPane.OK_CANCEL_OPTION);
			if (option == JOptionPane.OK_OPTION) {
				try {
					int incomingID = Integer.parseInt(incomingIDs.getText());
					int outgoingID = Integer.parseInt(outgoingIDs.getText());
					try {
						Main.guiControlller.createCondition(incomingID, outgoingID);
						Main.updateUserMsg(String.format("Added condition from %d to %d", incomingID, outgoingID));
					} catch (Exception e1) {
						Main.updateUserMsg(e1.getMessage());
					}
				} catch (NumberFormatException e2) {
					Main.updateUserMsg(invID);
				}

			}
			break;
		case "delete_condition":
			// prompt the user to enter the id of the condition to delete
			name = JOptionPane.showInputDialog(inputDialog, "Please enter the ID of the Event you want to delete");
			// if the id is successfully entered, delete condition
			if (name != null) {
				// condition id

				try {
					ID = Integer.parseInt(name);

					try {
						Main.guiControlller.deleteCondition(ID);
						Main.updateUserMsg(String.format("Deleted condition %d", ID));
					} catch (Exception e1) {
						Main.updateUserMsg(e1.getMessage());
					}
				} catch (NumberFormatException e2) {
					Main.updateUserMsg(invID);
				}
			}

			break;
		case "new_response":

			incomingIDs = new JTextField();
			outgoingIDs = new JTextField();
			Object[] respMessage = { "Incoming ID:", incomingIDs, "Outgoing ID:", outgoingIDs };

			option = JOptionPane.showConfirmDialog(null, respMessage, "Add response", JOptionPane.OK_CANCEL_OPTION);
			if (option == JOptionPane.OK_OPTION) {
				try {
					int incomingID = Integer.parseInt(incomingIDs.getText());
					int outgoingID = Integer.parseInt(outgoingIDs.getText());

					try {
						Main.guiControlller.createResponse(incomingID, outgoingID);
						Main.updateUserMsg(String.format("Added response from %d to %d", incomingID, outgoingID));
					} catch (Exception e1) {
						Main.updateUserMsg(e1.getMessage());
					}
				} catch (NumberFormatException e2) {
					Main.updateUserMsg(invID);
				}
			}
			break;
		case "delete_response":
			// prompt the user to enter the id of the response to delete
			name = JOptionPane.showInputDialog(inputDialog, "Please enter the ID of the response you want to delete");
			// if the id is successfully entered, delete response
			if (name != null) {
				// response id
				try {
					ID = Integer.parseInt(name);
					try {
						Main.guiControlller.deleteResponse(ID);
						Main.updateUserMsg(String.format("Deleted response with ID %d", ID));
					} catch (Exception e1) {
						Main.updateUserMsg(e1.getMessage());
					}
				} catch (NumberFormatException e2) {
					Main.updateUserMsg(invID);
				}
			}
			break;
		case "new_place":

			// prompt the user to enter the name of the new Place
			name = JOptionPane.showInputDialog(inputDialog, "Please enter the name of the new Place");

			// if the name is successfully entered, add Place
			if (name != null) {
				disableMenubar();
				GraphTab graphtab = Main.getActiveTab();
				Main.disableTabs();
				graphtab.activateClickListener();
				this.clickArgument = name;
				Main.updateUserMsg(String.format("Please click where you want to ad Place: %s", name));
				clickType = ClickType.PLACE;
			}

			break;
		case "delete_place":
			// prompt the user to enter the id of the place to delete
			name = JOptionPane.showInputDialog(inputDialog, "Please enter the ID of the place you want to delete");
			// if the id is successfully entered, delete place
			if (name != null) {
				// place id
				try {
					ID = Integer.parseInt(name);
					try {
						Main.guiControlller.deletePlace(ID);
						Main.updateUserMsg(String.format("Deleted place with ID %d", ID));
					} catch (Exception e1) {
						Main.updateUserMsg(e1.getMessage());
					}
				} catch (NumberFormatException e2) {
					Main.updateUserMsg(invID);
				}
			}
			break;
		case "new_transition":

			// prompt the user to enter the name of the new Transition
			name = JOptionPane.showInputDialog(inputDialog, "Please enter the name of the new Transition");

			// if the name is successfully entered, add Transition
			if (name != null) {
				disableMenubar();
				GraphTab graphtab = Main.getActiveTab();
				Main.disableTabs();
				graphtab.activateClickListener();
				this.clickArgument = name;
				Main.updateUserMsg(String.format("Please click where you want to ad Transition: %s", name));
				clickType = ClickType.TRANSITION;
			}
			break;
		case "delete_transition":
			// prompt the user to enter the id of the transition to delete
			name = JOptionPane.showInputDialog(inputDialog, "Please enter the ID of the transition you want to delete");
			// if the id is successfully entered, delete transition
			if (name != null) {
				try {
					// transition id
					ID = Integer.parseInt(name);
					try {
						Main.guiControlller.deleteTransition(ID);
						Main.updateUserMsg(String.format("Deleted trasition with ID: %d", ID));
					} catch (Exception e1) {
						Main.updateUserMsg(e1.getMessage());
					}
				} catch (NumberFormatException e2) {
					Main.updateUserMsg(invID);
				}
			}
			break;
		case "start_simulation":
			Main.showPossibleActions();
			disableMenubar();
			menuSimulation.setEnabled(true);
			Main.disableTabs();
			Main.updateUserMsg("Started Simulation");
			break;
		case "stop_simulation":
			Main.hidePossibleActions();
			Main.updateUserMsg("Stopped Simulation");
			Main.enableTabs();
			enableMenubar();
			break;
		case "new_arc":
			incomingIDs = new JTextField();
			outgoingIDs = new JTextField();
			Object[] arcMessage = { "Incoming ID:", incomingIDs, "Outgoing ID:", outgoingIDs };

			option = JOptionPane.showConfirmDialog(null, arcMessage, "Add Arc", JOptionPane.OK_CANCEL_OPTION);
			if (option == JOptionPane.OK_OPTION) {
				try {
					int incomingID = Integer.parseInt(incomingIDs.getText());
					int outgoingID = Integer.parseInt(outgoingIDs.getText());

					try {
						Main.guiControlller.createArc(incomingID, outgoingID);
						Main.updateUserMsg(String.format("Added Arc from %d to %d", incomingID, outgoingID));
					} catch (Exception e1) {
						Main.updateUserMsg(e1.getMessage());
					}
				} catch (NumberFormatException e2) {
					Main.updateUserMsg(invID);
				}
			}

			break;
		case "delete_arc":
			incomingIDs = new JTextField();
			outgoingIDs = new JTextField();
			Object[] delarcMessage = { "Incoming ID:", incomingIDs, "Outgoing ID:", outgoingIDs };

			option = JOptionPane.showConfirmDialog(null, delarcMessage, "Delete Arc", JOptionPane.OK_CANCEL_OPTION);
			if (option == JOptionPane.OK_OPTION) {
				try {
					int incomingID = Integer.parseInt(incomingIDs.getText());
					int outgoingID = Integer.parseInt(outgoingIDs.getText());

					try {
						Main.guiControlller.deleteArc(incomingID, outgoingID);
						Main.updateUserMsg(String.format("Deleted Arc from %d to %d", incomingID, outgoingID));
					} catch (Exception e1) {
						Main.updateUserMsg(e1.getMessage());
					}
				} catch (NumberFormatException e2) {
					Main.updateUserMsg(invID);
				}
			}
			break;
		default:
			System.out.println(action);
			Main.updateUserMsg("Invalid Button pressed!");
			break;
		}
		Main.guiPane.updatePane();
	}


	/**
	 * @param CrGraph
	 */
	public void createGraph(Boolean CrGraph) {
		JFrame dialog = new JFrame("dialog");
		// prompt the user to enter the name of the new petri graph
		String name = JOptionPane.showInputDialog(dialog, "Please enter the name of the graph");
		if (name != null) {
			try {
				int tabNum = GUIPane.getTabNum() + 1;
				if (CrGraph) {
					int ID = Main.guiControlller.createGraph("name", Graph.GraphTypes.CR);
					GuiController.ActiveGraphID = ID;
					Main.updateUserMsg("Added a new CR Graph");
					guiPane.addGraphTab(name + " #" + tabNum, true, ID);
				} else {
					int ID = Main.guiControlller.createGraph("name", Graph.GraphTypes.PETRI);
					GuiController.ActiveGraphID = ID;
					Main.updateUserMsg("Added a new Petri Graph");
					guiPane.addGraphTab(name + " #" + tabNum, false, ID);
				}
				Main.updateFrame();
			} catch (Exception e1) {
				Main.updateUserMsg(e1.getMessage());
			}
			// tabbedPane.setMnemonicAt(tabNum, KeyEvent.VK_1);
		}

	}

	/**
	 * 
	 */
	public static void enableCRMenu() {
		menuCR.setEnabled(true);

	}

	/**
	 * 
	 */
	public static void disableCRMenu() {
		menuCR.setEnabled(false);

	}

	/**
	 * 
	 */
	public static void enablePetriMenu() {
		MenuPetri.setEnabled(true);

	}

	/**
	 * 
	 */
	public static void disablePetriMenu() {
		MenuPetri.setEnabled(false);

	}

	/**
	 * 
	 */
	public static void enableSimulationMenu() {
		menuSimulation.setEnabled(true);

	}

	/**
	 * 
	 */
	public static void disableSimulationMenu() {
		menuSimulation.setEnabled(false);

	}

	/**
	 * @param position
	 */
	public void createEvent(Position position) {
		String nameEvent = clickArgument;
		try {
			Main.guiControlller.createEvent(position, nameEvent);
			Main.updateUserMsg(String.format("Added Event: %s", nameEvent));
		} catch (Exception e1) {
			Main.updateUserMsg(e1.getMessage());
			this.clickArgument = "";
		}

	}

	/**
	 * @param position
	 */
	public void createTransition(Position position) {
		String name = clickArgument;
		try {
			Main.guiControlller.createTransition(position, name);
			Main.updateUserMsg(String.format("Added Transition: %s", name));
		} catch (Exception e1) {
			Main.updateUserMsg(e1.getMessage());
			this.clickArgument = "";
		}

	}

	/**
	 * @param position
	 */
	public void createPlace(Position position) {
		String nameEvent = clickArgument;
		try {
			Main.guiControlller.createPlace(position);
			Main.updateUserMsg(String.format("Added Place: %s", nameEvent));
		} catch (Exception e1) {
			Main.updateUserMsg(e1.getMessage());
		}
	}

	/**
	 * @param position
	 */
	public void canvasClicked(Position position) {

		switch (clickType) {
		case PLACE:
			createPlace(position);
			break;
		case EVENT:
			createEvent(position);
			break;
		case TRANSITION:
			createTransition(position);
			break;
		default:
			Main.updateUserMsg("Invalid ClickEvent");
			break;
		}

		GraphTab graphtab = Main.getActiveTab();
		graphtab.deactivateClickListener();
		Main.enableTabs();
		enableMenubar();
		this.clickArgument = "";

		Main.guiPane.updatePane();

	}

	/**
	 * 
	 */
	public void disableMenubar() {
		for (Component menuItem : menuBar.getComponents()) {
			if (menuItem.isEnabled()) {
				menuItem.setEnabled(false);
				disabledMenus.add((JMenuItem) menuItem);
			}
		}
	}

	/**
	 * 
	 */
	public void enableMenubar() {
		for (JMenuItem menuItem : disabledMenus) {
			menuItem.setEnabled(true);
		}
	}

}