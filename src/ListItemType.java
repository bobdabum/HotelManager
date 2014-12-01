import java.util.ArrayList;

import javax.swing.JPanel;
import javax.swing.event.ChangeListener;


public interface ListItemType<T>{
	public ArrayList<JPanel> populateList(ArrayList<T> myItems, ArrayList<Boolean> isSelected);
}