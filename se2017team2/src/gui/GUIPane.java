package gui;

import javax.swing.JTabbedPane;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.List;

public class GUIPane extends JPanel implements ChangeListener {
	static JTabbedPane tabbedPane;

	private static List<GraphTab> graphTabs = new ArrayList<GraphTab>();;

	private static final long serialVersionUID = 1L;
	
	private ActionPane actPane;

	public GUIPane() {
		//super(new GridLayout(1, 2));
		super(new GridBagLayout());

        GridBagConstraints c = new GridBagConstraints();

		actPane = new ActionPane();
		
		
		
		//JScrollPane scroller = new JScrollPane(actPane);
		//this.add(actPane, c);
		//this.add(scroller, BorderLayout.WEST);
		
		tabbedPane = new JTabbedPane();
    	//c.fill = GridBagConstraints.HORIZONTAL;
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

	public void showActionPane(){
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
	
	public void removeActionPane(){
		this.remove(actPane);

	}
	
	// add a graph tab to the tabbed pane
	static void addGraphTab(String label, Boolean CrGraph) {
		GraphTab newTab = new GraphTab(CrGraph);
		graphTabs.add(newTab);
		// select the added tab
		tabbedPane.addTab(label, newTab.getIcon(), newTab.getpanel(), "Does nothing");
		int index = getTabNum();
		tabbedPane.setSelectedIndex(index - 1);
		// tabbedPane.setMnemonicAt(0, KeyEvent.VK_1);
	}

	public static int getTabNum() {
		int count = tabbedPane.getTabCount();
		return count;
	}

	@Override
	public void stateChanged(ChangeEvent e) {
		JTabbedPane sourceTabbedPane = (JTabbedPane) e.getSource();
		int index = sourceTabbedPane.getSelectedIndex();
		Main.updateUserMsg("Tab changed to: " + sourceTabbedPane.getTitleAt(index));
		GraphTab graphTab = graphTabs.get(index);
		// toggle menus for the correct graph type
		if (graphTab.getCrGraph()) {
			graphTab.updateImage("images/test.png");
			Menu.enableCRMenu();
			Menu.disablePetriMenu();
		} else {
			graphTab.updateImage("images/test2.png");
			Menu.enablePetriMenu();
			Menu.disableCRMenu();
		}
		sourceTabbedPane.setComponentAt(index, graphTab.getpanel());
	}
}