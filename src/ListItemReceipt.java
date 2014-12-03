import java.awt.GridLayout;
import java.text.SimpleDateFormat;

import javax.swing.*;

@SuppressWarnings("serial")
public class ListItemReceipt extends ListItem{
		public ListItemReceipt(Reservation r,int position) {
			super(position);
			SimpleDateFormat sf = new SimpleDateFormat("MM/DD/YYYY");
			setLayout(new GridLayout(2,3));
			add(new JLabel("User ID: "+r.getUserID()));
			add(new JLabel("User Name: "+r.getUserName()));
			add(new JLabel("Room ID: "+r.getRoomID()));
			add(new JLabel("Check-in: "+sf.format(r.getStart().getTime())));
			add(new JLabel("Check-out: "+sf.format(r.getEnd().getTime())));
			add(new JLabel("Cost: $"+r.getRoomCost().getCost()));
		}
		
	}
