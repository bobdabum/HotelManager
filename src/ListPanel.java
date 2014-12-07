import java.awt.Color;
import java.util.ArrayList;

import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 * Container for ListItem.
 * @author My Pc
 *
 */
@SuppressWarnings("serial")
public class ListPanel extends JPanel implements ChangeListener{
	private ArrayList<ListItem> myListItems;
	/**
	 * Constructor initializes layout and ListItem array.
	 */
	public ListPanel(){
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		myListItems = new ArrayList<ListItem>();
		setBackground(Color.white);
	}
	/**
	 * Adds item to list and redraws the ListPanel.
	 * @param myItem
	 */
	public void populateList(ListItem myItem){
		add(myItem);
		myListItems.add(myItem);
		revalidate();
		repaint();
	}
	/**
	 * Clears array list and view.
	 */
	public void clearList(){
		myListItems.clear();
		removeAll();
	}
	/**
	 * Gets the selected item of the panel.
	 * @return The ID of the list item.
	 */
	public int getSelected(){
		for(ListItem li:myListItems){
			if(li.isSelected())
				return li.getID();
		}
		return -1;
	}
	/**
	 * Unselects every ListItem except the one specified by the ChangeEvent.
	 * @param e
	 */
	@Override
	public void stateChanged(ChangeEvent e) {
		ListItem source = (ListItem) e.getSource();
		for(ListItem li:myListItems){
			if(li.isSelected()&& li.getID() != source.getID())
				li.onClicked();
		}
	}
}