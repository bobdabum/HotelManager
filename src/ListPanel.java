import java.util.ArrayList;

import javax.swing.BoxLayout;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class ListPanel<T> extends JPanel{
	private ListItemType<T> listItemType;
	public ListPanel(ListItemType<T> listItem_Type){
		this.setListItemType(listItem_Type);
		setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
	}
	public void populateList(ArrayList<T> myItems, ArrayList<Boolean> isSelected){
		removeAll();
		ArrayList<JPanel> myList = listItemType.populateList(myItems, isSelected);
		for(JPanel j:myList){
			add(j);
		}
		revalidate();
		repaint();
	}
	public void setListItemType(ListItemType<T> listItemType) {
		this.listItemType = listItemType;
	}
}