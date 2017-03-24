package gui;

import java.awt.Component;
import java.awt.event.*;
import java.util.HashSet;
import java.util.Set;

import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JRadioButton;
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
	private Boolean isPending;
	private GUIPane guiPane;
	private Boolean nested;

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
		this.isPending = false;

		// Create the menu bar.
		menuBar = new JMenuBar();
		this.disabledMenus = new HashSet<JMenuItem>();
		this.clickArgument = "";

		// Build the first menu.
		menu = new JMenu("New");
		menu.setMnemonic(KeyEvent.VK_N);

		
		menu.getAccessibleContext().setAccessibleDescription("Menu for CR Graphs");
		menuBar.add(menu);

		// a group of JMenuItems
		menuItem = new JMenuItem("New CR Graph", KeyEvent.VK_R);
		// menuItem.setMnemonic(KeyEvent.VK_T); //used constructor instead
		menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_R, ActionEvent.ALT_MASK));
		menuItem.getAccessibleContext().setAccessibleDescription("Creates a new CR Graph");
		menuItem.addActionListener(this);
		menuItem.setActionCommand("new_cr");
		menu.add(menuItem);

		menuItem = new JMenuItem("New Petri Net", KeyEvent.VK_I);
		// menuItem.setMnemonic(KeyEvent.VK_T); //used constructor instead
		menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_I, ActionEvent.ALT_MASK));
		menuItem.getAccessibleContext().setAccessibleDescription("Creates a new Petri Net");
		menuItem.addActionListener(this);
		menuItem.setActionCommand("new_petri");
		menu.add(menuItem);

		// Build CR menu in the menu bar.
		menuCR = new JMenu("CR");
		menuCR.setMnemonic(KeyEvent.VK_C);
		menuCR.getAccessibleContext().setAccessibleDescription("This menu does nothing");
		menuCR.setEnabled(false);
		menuBar.add(menuCR);

		submenu = new JMenu("Event");
		submenu.setMnemonic(KeyEvent.VK_E);

		
		menuItem = new JMenuItem("Add");
		//menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_2, ActionEvent.ALT_MASK));
		menuItem.setMnemonic(KeyEvent.VK_A);
		menuItem.addActionListener(this);
		menuItem.setActionCommand("new_event");
		submenu.add(menuItem);

		menuItem = new JMenuItem("Delete");
		menuItem.addActionListener(this);
		menuItem.setActionCommand("delete_event");
		menuItem.setMnemonic(KeyEvent.VK_D);
		submenu.add(menuItem);

		menuItem = new JMenuItem("Add PetriNet to Event");
		// menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_2,
		// ActionEvent.ALT_MASK));
		menuItem.addActionListener(this);
		menuItem.setActionCommand("new_subpetri");
		menuItem.setMnemonic(KeyEvent.VK_P);
		submenu.add(menuItem);

		menuItem = new JMenuItem("Delete PetriNet from Event");
		// menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_2,
		// ActionEvent.ALT_MASK));
		menuItem.addActionListener(this);
		menuItem.setActionCommand("delete_subpetri");
		menuItem.setMnemonic(KeyEvent.VK_N);
		submenu.add(menuItem);
		menuCR.add(submenu);

		submenu = new JMenu("Condition");
		submenu.setMnemonic(KeyEvent.VK_C);

		menuItem = new JMenuItem("Add");
		// menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_2,
		// ActionEvent.ALT_MASK));
		menuItem.addActionListener(this);
		menuItem.setActionCommand("new_condition");
		menuItem.setMnemonic(KeyEvent.VK_A);
		submenu.add(menuItem);

		menuItem = new JMenuItem("Delete");
		menuItem.addActionListener(this);
		menuItem.setMnemonic(KeyEvent.VK_D);
		submenu.add(menuItem);
		menuItem.setActionCommand("delete_condition");
		menuCR.add(submenu);

		submenu = new JMenu("Response");
		submenu.setMnemonic(KeyEvent.VK_R);

		menuItem = new JMenuItem("Add");
		// menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_2,
		// ActionEvent.ALT_MASK));
		menuItem.addActionListener(this);
		menuItem.setActionCommand("new_response");
		menuItem.setMnemonic(KeyEvent.VK_A);
		submenu.add(menuItem);

		menuItem = new JMenuItem("Delete");
		menuItem.addActionListener(this);
		menuItem.setActionCommand("delete_response");
		menuItem.setMnemonic(KeyEvent.VK_D);
		submenu.add(menuItem);
		menuCR.add(submenu);

		// Build Petri menu in the menu bar.
		MenuPetri = new JMenu("Petri");
		MenuPetri.setMnemonic(KeyEvent.VK_P);
		MenuPetri.getAccessibleContext().setAccessibleDescription("This menu does nothing");
		MenuPetri.setEnabled(false);

		submenu = new JMenu("Place");
		submenu.setMnemonic(KeyEvent.VK_P);

		menuItem = new JMenuItem("Add");
		// menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_2,
		// ActionEvent.ALT_MASK));
		menuItem.addActionListener(this);
		menuItem.setActionCommand("new_place");
		menuItem.setMnemonic(KeyEvent.VK_A);
		submenu.add(menuItem);

		menuItem = new JMenuItem("Delete");
		menuItem.addActionListener(this);
		menuItem.setActionCommand("delete_place");
		menuItem.setMnemonic(KeyEvent.VK_D);
		submenu.add(menuItem);
		MenuPetri.add(submenu);

		submenu = new JMenu("Transition");
		submenu.setMnemonic(KeyEvent.VK_T);

		menuItem = new JMenuItem("Add");
		// menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_2,
		// ActionEvent.ALT_MASK));
		menuItem.addActionListener(this);
		menuItem.setActionCommand("new_transition");
		menuItem.setMnemonic(KeyEvent.VK_A);
		submenu.add(menuItem);

		menuItem = new JMenuItem("Delete");
		menuItem.addActionListener(this);
		menuItem.setActionCommand("delete_transition");
		menuItem.setMnemonic(KeyEvent.VK_D);
		submenu.add(menuItem);

		menuItem = new JMenuItem("Add CR Graph to Transition");
		// menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_2,
		// ActionEvent.ALT_MASK));
		menuItem.addActionListener(this);
		menuItem.setActionCommand("new_subcr");
		menuItem.setMnemonic(KeyEvent.VK_T);
		submenu.add(menuItem);

		menuItem = new JMenuItem("Delete CR Graph from Transition");
		// menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_2,
		// ActionEvent.ALT_MASK));
		menuItem.addActionListener(this);
		menuItem.setActionCommand("delete_subcr");
		menuItem.setMnemonic(KeyEvent.VK_S);
		submenu.add(menuItem);

		MenuPetri.add(submenu);

		submenu = new JMenu("Arc");
		submenu.setMnemonic(KeyEvent.VK_A);

		menuItem = new JMenuItem("Add");
		// menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_2,
		// ActionEvent.ALT_MASK));
		menuItem.addActionListener(this);
		menuItem.setActionCommand("new_arc");
		menuItem.setMnemonic(KeyEvent.VK_A);
		submenu.add(menuItem);

		menuItem = new JMenuItem("Delete");
		menuItem.addActionListener(this);
		menuItem.setActionCommand("delete_arc");
		menuItem.setMnemonic(KeyEvent.VK_D);
		submenu.add(menuItem);
		MenuPetri.add(submenu);

		menuBar.add(MenuPetri);

		// Build the first menu.
		menuSimulation = new JMenu("Simulation");
		menuSimulation.setMnemonic(KeyEvent.VK_S);
		menuSimulation.getAccessibleContext().setAccessibleDescription("Menu for Simulation");
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

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
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

			JTextField nameField = new JTextField();
			JRadioButton pending = new JRadioButton();
			JRadioButton nestedButton = new JRadioButton();
			Object[] message1 = { "Please enter the name of the new Event:", nameField, "Pending?:", pending,
					"Do you want to add a nested Petri Net", nestedButton };

			option = JOptionPane.showConfirmDialog(null, message1, "Add Event", JOptionPane.OK_CANCEL_OPTION);

			this.isPending = pending.isSelected();
			this.nested = nestedButton.isSelected();

			// prompt the user to enter the name of the new Event
			String nameEvent = nameField.getText();

			// if the name is successfully entered, add Event
			if (option == JOptionPane.OK_OPTION) {
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
						Main.updateUserWarning(e1.getMessage());
					}
				} catch (NumberFormatException e2) {
					Main.updateUserWarning(invID);
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
						Main.updateUserWarning(e1.getMessage());
					}
				} catch (NumberFormatException e2) {
					Main.updateUserWarning(invID);
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
						Main.updateUserWarning(e1.getMessage());
					}
				} catch (NumberFormatException e2) {
					Main.updateUserWarning(invID);
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
						Main.updateUserWarning(e1.getMessage());
					}
				} catch (NumberFormatException e2) {
					Main.updateUserWarning(invID);
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
						Main.updateUserWarning(e1.getMessage());
					}
				} catch (NumberFormatException e2) {
					Main.updateUserWarning(invID);
				}
			}
			break;
		case "new_place":
			// add Place
			disableMenubar();
			Main.disableTabs();
			Main.getActiveTab().activateClickListener();
			Main.updateUserMsg(String.format("Please click where you want to ad the Place"));
			clickType = ClickType.PLACE;
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
						Main.updateUserWarning(e1.getMessage());
					}
				} catch (NumberFormatException e2) {
					Main.updateUserWarning(invID);
				}
			}
			break;
		case "new_transition":

			// prompt the user to enter the name of the new Transition

			JTextField TransitionNameField = new JTextField();
			JRadioButton nestedCRButton = new JRadioButton();
			Object[] TransitionMessage = { "Please enter the name of the new Transition:", TransitionNameField,
					"Do you want to add a nested CR graph", nestedCRButton };

			option = JOptionPane.showConfirmDialog(null, TransitionMessage, "Add Transition",
					JOptionPane.OK_CANCEL_OPTION);

			this.nested = nestedCRButton.isSelected();

			// prompt the user to enter the name of the new Transition
			name = TransitionNameField.getText();

			// if the name is successfully entered, add Transition
			if (option == JOptionPane.OK_OPTION) {
				disableMenubar();
				GraphTab actGraphtab = Main.getActiveTab();
				Main.disableTabs();
				actGraphtab.activateClickListener();
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
						Main.updateUserWarning(e1.getMessage());
					}
				} catch (NumberFormatException e2) {
					Main.updateUserWarning(invID);
				}
			}
			break;
		case "start_simulation":
			Main.showPossibleActions();
			disableMenubar();
			menuSimulation.setEnabled(true);
			Main.disableTabs();
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
						Main.updateUserWarning(e1.getMessage());
					}
				} catch (NumberFormatException e2) {
					Main.updateUserWarning(invID);
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
						Main.updateUserWarning(e1.getMessage());
					}
				} catch (NumberFormatException e2) {
					Main.updateUserWarning(invID);
				}
			}
			break;
		case "new_subcr":

			CustomOptionPane pane = new CustomOptionPane("Add sub Graph to Transition",
					"Please enter ID of the Transition", "Create New CR Graph", "Add reference to existing CR Graph",
					"Petri Net");
			if (pane.getOption() == JOptionPane.OK_OPTION) {

				String graphIDString = pane.getGraphId();
				String transitionIDString = pane.getGraphObjId();
				if (graphIDString == null) {
					// int PetriID = createGraph(false);
					try {
						int transitiontID = Integer.parseInt(transitionIDString);
						addNestedGraph(transitiontID, true, -1);
					} catch (NumberFormatException e2) {

						Main.updateUserWarning(invID);
					}
				} else {
					try {
						int crID = Integer.parseInt(graphIDString);
						int transitionID = Integer.parseInt(transitionIDString);
						addNestedGraph(transitionID, true, crID);
					} catch (NumberFormatException e2) {
						Main.updateUserWarning(invID);
					}
				}
			}

			break;
		case "delete_subcr":

			DeleteSubGraphOptionPane CRpaneDel = new DeleteSubGraphOptionPane("Delete sub Graph from Transition",
					"Delete refence to CR Graph", "Delete CR Graph entirely(cannot be undone)");
			if (CRpaneDel.getOption() == JOptionPane.OK_OPTION) {
				String eventIDstring = CRpaneDel.getContent();
				try {
					int eventID = Integer.parseInt(eventIDstring);
					try {
						// delete reference to CR
						Main.guiControlller.unbindNestedGraph(eventID);
					} catch (Exception e1) {
						Main.updateUserWarning(e1.getMessage());
						break;
					}
					if (!CRpaneDel.deleteOnlyReference()) {
						// delete CR
						// todo Main.guiControlller.deleteGraph(graphID);
					}

				} catch (NumberFormatException e2) {
					Main.updateUserWarning(invID);
				}
			}

			Main.updateUserMsg("Delete sub CR Graph, Not implemented yet");
			break;
		case "new_subpetri":

			CustomOptionPane pane1 = new CustomOptionPane("Add sub Graph to Event", "Please enter ID of the Event",
					"Create New Petri Net", "Add reference to existing Petri Net", "CR Graph");
			if (pane1.getOption() == JOptionPane.OK_OPTION) {

				String graphIDString = pane1.getGraphId();
				String eventIDString = pane1.getGraphObjId();
				if (graphIDString == null) {
					// int PetriID = createGraph(false);
					try {
						int eventID = Integer.parseInt(eventIDString);
						addNestedGraph(eventID, false, -1);
					} catch (NumberFormatException e2) {

						Main.updateUserWarning(invID);
					}
				} else {
					try {
						int PetriID = Integer.parseInt(graphIDString);
						int eventID = Integer.parseInt(eventIDString);
						addNestedGraph(eventID, false, PetriID);
					} catch (NumberFormatException e2) {
						Main.updateUserWarning(invID);
					}
				}
			}
			break;
		case "delete_subpetri":

			DeleteSubGraphOptionPane PetripaneDel = new DeleteSubGraphOptionPane("Delete sub Graph from Event",
					"Delete refence to Petri Net", "Delete Petri Net entirely(cannot be undone)");
			if (PetripaneDel.getOption() == JOptionPane.OK_OPTION) {
				String eventIDstring = PetripaneDel.getContent();
				try {
					int eventID = Integer.parseInt(eventIDstring);
					try {
						// delete reference to petri
						Main.guiControlller.unbindNestedGraph(eventID);
					} catch (Exception e1) {
						Main.updateUserWarning(e1.getMessage());
						break;
					}
					if (!PetripaneDel.deleteOnlyReference()) {
						// delete petri
						// todo Main.guiControlller.deleteGraph(graphID);
					}

				} catch (NumberFormatException e2) {
					Main.updateUserWarning(invID);
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
	public int createGraph(Boolean CrGraph) {
		JFrame dialog = new JFrame("dialog");
		String name;
		int ID = -1;
		if (CrGraph) {
			// prompt the user to enter the name of the new graph
			name = JOptionPane.showInputDialog(dialog, "Please enter the name of the new CR graph");
		} else {
			name = JOptionPane.showInputDialog(dialog, "Please enter the name of the new Petri Net");
		}
		if (name != null) {
			try {
				int tabNum = GUIPane.getTabNum() + 1;
				if (CrGraph) {
					ID = Main.guiControlller.createGraph("name", Graph.GraphTypes.CR);
					GuiController.ActiveGraphID = ID;
					Main.updateUserMsg("Added a new CR Graph");
					guiPane.addGraphTab(name + " #" + tabNum, true, ID);
				} else {
					ID = Main.guiControlller.createGraph("name", Graph.GraphTypes.PETRI);
					GuiController.ActiveGraphID = ID;
					Main.updateUserMsg("Added a new Petri Graph");
					guiPane.addGraphTab(name + " #" + tabNum, false, ID);
				}
				Main.updateFrame();
			} catch (Exception e1) {
				Main.updateUserWarning(e1.getMessage());
			}
			// tabbedPane.setMnemonicAt(tabNum, KeyEvent.VK_1);
		}
		return ID;
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
	 * @return
	 */
	public int createEvent(Position position) {
		String nameEvent = clickArgument;
		int Id = -1;
		try {
			Id = Main.guiControlller.createEvent(position, nameEvent, this.isPending);
			Main.updateUserMsg(String.format("Added Event: %s", nameEvent));
		} catch (Exception e1) {
			Main.updateUserWarning(e1.getMessage());
			this.clickArgument = "";
			this.isPending = false;
		}
		this.isPending = false;
		return Id;
	}

	/**
	 * @param position
	 * @return
	 */
	public int createTransition(Position position) {
		String name = clickArgument;
		int transitionId = -1;
		try {
			transitionId = Main.guiControlller.createTransition(position, name);
			Main.updateUserMsg(String.format("Added Transition: %s", name));
		} catch (Exception e1) {
			Main.updateUserWarning(e1.getMessage());
			this.clickArgument = "";
		}
		return transitionId;
	}

	/**
	 * @param position
	 */
	public void createPlace(Position position) {
		String namePlace = clickArgument;
		try {
			Main.guiControlller.createPlace(position);
			Main.updateUserMsg(String.format("Added Place: %s", namePlace));
		} catch (Exception e1) {
			Main.updateUserWarning(e1.getMessage());
		}
	}

	/**
	 * Adds a nested graph to a graph-object
	 * 
	 * @param graphObjID
	 *            ID of the graph-object to be added to a graph
	 * @param crGraph
	 *            true if the graph should be a CR graph, false if it should be
	 *            a Petri Net
	 * @param graphId
	 *            ID of the graph the graph-object should be added to -1 if a
	 *            new graph should be created
	 */
	public void addNestedGraph(int graphObjID, boolean crGraph, int graphId) {
		int oldGraphID = GuiController.ActiveGraphID;

		try {
			if (graphId == -1) {
				graphId = createGraph(crGraph);
				GuiController.ActiveGraphID = oldGraphID;
				Main.guiControlller.bindNestedGraph(graphObjID, graphId);
				GuiController.ActiveGraphID = graphId;
			} else {
				Main.guiControlller.bindNestedGraph(graphObjID, graphId);
			}
		} catch (Exception e) {
			Main.updateUserWarning(e.getMessage());
			try {
				Main.guiControlller.deleteGraph(graphId);
			} catch (Exception e1) {
				// do nothing
			}
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
			int eventID = createEvent(position);
			if (this.nested) {
				addNestedGraph(eventID, false, -1);
			}
			this.nested = false;
			break;
		case TRANSITION:
			int transitionID = createTransition(position);
			if (this.nested) {
				addNestedGraph(transitionID, true, -1);
			}
			this.nested = false;

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