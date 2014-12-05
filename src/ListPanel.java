import java.awt.Color;
import java.util.ArrayList;

import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

@SuppressWarnings("serial")
public class ListPanel extends JPanel implements ChangeListener{
	private ArrayList<ListItem> myListItems;
	public ListPanel(){
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		myListItems = new ArrayList<ListItem>();
		setBackground(Color.white);
	}
	public void populateList(ListItem myItem){
		add(myItem);
		myListItems.add(myItem);
		revalidate();
		repaint();
	}
	public void clearList(){
		myListItems.clear();
		removeAll();
	}
	public int getSelected(){
		for(ListItem li:myListItems){
			if(li.isSelected())
				return li.getID();
		}
		return -1;
	}
	@Override
	public void stateChanged(ChangeEvent e) {
		ListItem source = (ListItem) e.getSource();
		for(ListItem li:myListItems){
			if(li.isSelected()&& li.getID() != source.getID())
				li.onClicked();
		}
	}
}