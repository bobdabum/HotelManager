import java.awt.Color;

import javax.swing.JLabel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 * ListItem for displaying rooms. Contains ChangeListener for mouse click events.
 * @author Linye Ouyang
 *
 */
@SuppressWarnings("serial")
public class ListItemRoom extends ListItem{
	private ChangeListener cl;
	
	/**
	 * ListItem for room. 
	 * @param id Room ID is the id of interest for Rooms.
	 * @param cl ChangeListener for the ListItem.
	 */
	public ListItemRoom(int id, ChangeListener cl) {
		super(id);
		this.cl = cl;
		add(new JLabel("Room: #"+(id+1)));
	}
	/**
	 * Overrides onClicked Changes color when clicked.
	 */
	public void onClicked(){
		super.onClicked();
		if(isSelected()){
			setBackground(Color.LIGHT_GRAY);
			cl.stateChanged(new ChangeEvent(this));
		}
		else
			setBackground(Color.WHITE);
		revalidate();
		repaint();
	}
}
