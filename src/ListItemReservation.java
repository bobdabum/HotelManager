import java.awt.*;
import java.text.SimpleDateFormat;

import javax.swing.JLabel;


@SuppressWarnings("serial")
public class ListItemReservation extends ListItem{
	public ListItemReservation(int resID, Reservation r) {
		super(resID);
		SimpleDateFormat sf = new SimpleDateFormat("MM/DD/YYYY");
		setLayout(new GridLayout(2,2));
		add(new JLabel("Room: "+r.getRoomID()));
		add(new JLabel(""));
		add(new JLabel("Check-in: "+sf.format(r.getStart().getTime())));
		add(new JLabel("Check-out: "+sf.format(r.getEnd().getTime())));
		setOpaque(true);
	}
	@Override
	protected void onClicked(){
		super.onClicked();
		if(isSelected())
			setBackground(Color.LIGHT_GRAY);
		else
			setBackground(Color.white);
		revalidate();
		repaint();
	}
}
