package gui;

import javax.swing.JTabbedPane;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.JPanel;

import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.List;

public class GUIPane extends JPanel implements ChangeListener {
	static JTabbedPane tabbedPane;

	private static List<GraphTab> graphTabs = new ArrayList<GraphTab>();;

	private static final long serialVersionUID = 1L;

	public GUIPane() {
		super(new GridLayout(1, 1));

		tabbedPane = new JTabbedPane();

		// Add the tabbed pane to this panel.
		add(tabbedPane);

		// The following line enables to use scrolling tabs.
		tabbedPane.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);
		tabbedPane.addChangeListener(this);
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