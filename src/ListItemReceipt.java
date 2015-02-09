import java.awt.Dimension;
import java.awt.GridLayout;
import java.text.SimpleDateFormat;

import javax.swing.*;

/**
 * ListItem for Receipt
 * @author Linye Ouyang
 *
 */
@SuppressWarnings("serial")
public class ListItemReceipt extends ListItem{
	/**
	 * Constructor for ListItemReceipt. ID does not matter for Receipt items.
	 * @param r
	 * @param position
	 */
		public ListItemReceipt(Reservation r,int position) {
			super(position);
			setPreferredSize(new Dimension(375,35));
			SimpleDateFormat sf = new SimpleDateFormat("MM/dd/YYYY");
			setLayout(new GridLayout(2,3));
			add(new JLabel("User ID: "+r.getUserID()));
			add(new JLabel("User Name: "+r.getUserName()));
			add(new JLabel("Room: #"+(r.getRoomID()+1)));
			add(new JLabel("Check-in: "+sf.format(r.getStart().getTime())));
			add(new JLabel("Check-out: "+sf.format(r.getEnd().getTime())));
			add(new JLabel("Cost: $"+r.getRoomCost().getCost()));
		}
		
	}
