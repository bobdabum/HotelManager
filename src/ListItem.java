import java.awt.*;
import java.awt.event.*;

import javax.swing.*;


@SuppressWarnings("serial")
public abstract class ListItem extends JPanel{
	private boolean isSelected = false;
	private int id;
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
	protected void onClicked(){
		isSelected = !isSelected;
	}
	public boolean isSelected(){
		return isSelected;
	}
	public int getID(){
		return id;
	}
}
