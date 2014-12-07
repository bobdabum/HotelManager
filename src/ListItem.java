import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

/**
 * ListItem is an abstract class that ListPanel uses to populate itself.
 * @author Linye Ouyang
 *
 */
@SuppressWarnings("serial")
public abstract class ListItem extends JPanel{
	private boolean isSelected = false;
	private int id;
	/**
	 * Each ListItem has a unique identifier. For rooms: the room id and for reservations: reservation ID.
	 * @param id
	 */
	public ListItem(int id){
		this.id = id;
		setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(Color.BLACK),
				 									BorderFactory.createEmptyBorder(5,5,5,5)));
		setPreferredSize(new Dimension(250,20));
		setBackground(Color.white);
		addMouseListener(new MouseListener(){
			@Override
			public void mouseClicked(MouseEvent arg0) {
				onClicked();
			}
			@Override
			public void mouseEntered(MouseEvent arg0) {
			}
			@Override
			public void mouseExited(MouseEvent arg0) {
			}
			@Override
			public void mousePressed(MouseEvent arg0) {
			}
			@Override
			public void mouseReleased(MouseEvent arg0) {
			}});
	}
	/**
	 * Makes isSelected true. Extended if the panel needs to change on click.
	 */
	protected void onClicked(){
		isSelected = !isSelected;
	}
	/**
	 * Returns true if panel is selected.
	 * @return
	 */
	public boolean isSelected(){
		return isSelected;
	}
	/**
	 * Returns the identifier for the panel.
	 * @return returns the ID.
	 */
	public int getID(){
		return id;
	}
}
