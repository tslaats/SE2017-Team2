package gui;

import javax.swing.JTabbedPane;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.JPanel;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.util.ArrayList;
import java.util.List;

/**
 * @author MultiPeden
 *
 */
public class GUIPane extends JPanel implements ChangeListener {
	static JTabbedPane tabbedPane;

	private List<GraphTab> graphTabs = new ArrayList<GraphTab>();;

	private static final long serialVersionUID = 1L;

	private ActionPane actPane;

	/**
	 * 
	 */
	public GUIPane() {
		// super(new GridLayout(1, 2));
		super(new GridBagLayout());

		GridBagConstraints c = new GridBagConstraints();

		actPane = new ActionPane();

		// JScrollPane scroller = new JScrollPane(actPane);
		// this.add(actPane, c);
		// this.add(scroller, BorderLayout.WEST);

		tabbedPane = new JTabbedPane();
		// c.fill = GridBagConstraints.HORIZONTAL;
		c.fill = GridBagConstraints.BOTH;
		c.weightx = 0.5;
		c.weighty = 0.5;
		c.gridx = 1;
		c.gridy = 0;
		c.gridwidth = 1;

		// Add the tabbed pane to this panel.
		this.add(tabbedPane, c);

		// The following line enables to use scrolling tabs.
		tabbedPane.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);
		tabbedPane.addChangeListener(this);

		this.setSize(this.getWidth(), tabbedPane.getHeight());

		showActionPane();
		removeActionPane();

	}

	/**
	 * 
	 */
	public void showActionPane() {
		GridBagConstraints c = new GridBagConstraints();
		c.gridwidth = 1;
		c.gridx = 0;
		c.gridy = 0;
		c.weighty = 0;
		c.weightx = 0;
		c.fill = GridBagConstraints.VERTICAL;
		c.anchor = GridBagConstraints.NORTH;
		this.add(actPane, c);
	}

	/**
	 * 
	 */
	public void removeActionPane() {
		this.remove(actPane);
	}

	// add a graph tab to the tabbed pane
	/**
	 * @param label
	 * @param CrGraph
	 * @param ID
	 */
	public void addGraphTab(String label, Boolean CrGraph, Integer ID) {
		GraphTab newTab = new GraphTab(CrGraph, ID);
		graphTabs.add(newTab);
		// select the added tab
		tabbedPane.addTab(label, newTab.getIcon(), newTab.getpanel(), "Does nothing");
		int index = getTabNum();
		tabbedPane.setSelectedIndex(index - 1);
		// tabbedPane.setMnemonicAt(0, KeyEvent.VK_1);
	}

	/**
	 * @return
	 */
	public static int getTabNum() {
		int count = tabbedPane.getTabCount();
		return count;
	}

	/* (non-Javadoc)
	 * @see javax.swing.event.ChangeListener#stateChanged(javax.swing.event.ChangeEvent)
	 */
	@Override
	public void stateChanged(ChangeEvent e) {
		JTabbedPane sourceTabbedPane = (JTabbedPane) e.getSource();
		int index = sourceTabbedPane.getSelectedIndex();
		Main.updateUserMsg("Tab changed to: " + sourceTabbedPane.getTitleAt(index));
		GraphTab graphTab = graphTabs.get(index);
		try {
			GuiController.ActiveGraphID = graphTab.getId();
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		
		graphTab.updateImage();
		
		// toggle menus for the correct graph type
		if (graphTab.getCrGraph()) {
			Menu.enableCRMenu();
			Menu.disablePetriMenu();

		} else {
			Menu.enablePetriMenu();
			Menu.disableCRMenu();


		}
		Menu.enableNewMenubar();
		sourceTabbedPane.setComponentAt(index, graphTab.getpanel());
		sourceTabbedPane.repaint();
		
		
	}
	
	

	/**
	 * 
	 */
	public void updatePane() {

		int index = tabbedPane.getSelectedIndex();
		GraphTab graphTab = graphTabs.get(index);
		graphTab.updateImage();
		tabbedPane.setComponentAt(index, graphTab.getpanel());
		tabbedPane.repaint();
	}

	/**
	 * @return
	 */
	public GraphTab getCurrentTab() {
		int index = tabbedPane.getSelectedIndex();
		return graphTabs.get(index);
	}

	/**
	 * 
	 */
	public void disableTabs() {
		for (int i = 0; i < tabbedPane.getTabCount(); i++) {
			tabbedPane.setEnabledAt(i, false);
		}
	}

	/**
	 * 
	 */
	public void enableTabs() {
		for (int i = 0; i < tabbedPane.getTabCount(); i++) {
			tabbedPane.setEnabledAt(i, true);
		}
	}

	/**
	 * 
	 */
	public void updateActions() {
		this.actPane.updateActionPane();
	}

}