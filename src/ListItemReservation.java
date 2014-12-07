import java.awt.*;
import java.text.SimpleDateFormat;

import javax.swing.JLabel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 * ListItem for displaying reservations. Contains ChangeListener for mouse click events.
 * @author Linye Ouyang
 *
 */
@SuppressWarnings("serial")
public class ListItemReservation extends ListItem{
	private ChangeListener cl;
	/**
	 * Constructor for ListItem. ID of interest is the reservation ID.
	 * @param resID
	 * @param r
	 * @param cl ChangeListener for ListItem. The change lsitener is the ListPanel.
	 */
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
	/**
	 * Overrides super class method. Changes panel color when the panel is selected.
	 */
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
