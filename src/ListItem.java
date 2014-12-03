import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JPanel;


@SuppressWarnings("serial")
public abstract class ListItem extends JPanel{
	private boolean isSelected = false;
	private int id;
	public ListItem(int id){
		this.id = id;
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
