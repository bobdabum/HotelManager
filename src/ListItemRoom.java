import java.awt.Color;

import javax.swing.JLabel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;


@SuppressWarnings("serial")
public class ListItemRoom extends ListItem{
	private ChangeListener cl;
	public ListItemRoom(int id, ChangeListener cl) {
		super(id);
		this.cl = cl;
		add(new JLabel("Room: #"+(id+1)));
	}
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
