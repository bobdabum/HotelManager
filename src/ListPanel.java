import java.util.ArrayList;

import javax.swing.BoxLayout;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class ListPanel extends JPanel{
	private ArrayList<ListItem> myListItems;
	public ListPanel(){
		setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
	}
	public void populateList(ListItem myItem){
		add(myItem);
		myListItems.add(myItem);
	}
	public void clearList(){
		myListItems.clear();
		removeAll();
	}
	public ArrayList<Integer> getSelected(){
		ArrayList<Integer> returnList = new ArrayList<Integer>();
		for(ListItem li:myListItems){
			if(li.isSelected())
				returnList.add(li.getID());
		}
		return returnList;
	}
}