import java.awt.*;
import java.text.SimpleDateFormat;

import javax.swing.JLabel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;


@SuppressWarnings("serial")
public class ListItemReservation extends ListItem{
	private ChangeListener cl;
	public ListItemReservation(int resID, Reservation r, ChangeListener cl) {
		super(resID);
		this.cl = cl;
		SimpleDateFormat sf = new SimpleDateFormat("MM/dd/YYYY");
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
		if(isSelected()){
			setBackground(Color.LIGHT_GRAY);
			cl.stateChanged(new ChangeEvent(this));
		}
		else
			setBackground(Color.white);
		revalidate();
		repaint();
	}
}
