import java.awt.Color;

import javax.swing.JLabel;


@SuppressWarnings("serial")
public class ListItemRoom extends ListItem{
	public ListItemRoom(int id) {
		super(id);
		add(new JLabel("Room: #"+id));
	}
	public void onClicked(){
		super.onClicked();
		if(isSelected())
			setBackground(Color.LIGHT_GRAY);
		else
			setBackground(Color.WHITE);
		revalidate();
		repaint();
	}
}
