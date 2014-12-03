import java.awt.GridLayout;
import java.text.SimpleDateFormat;

import javax.swing.*;

<<<<<<< HEAD
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
=======

public class ListItemReceipt implements ListItemType<Reservation> {
	public ArrayList<JPanel> populateList(ArrayList<Reservation> myItems, ArrayList<Boolean> isSelected) {
		ArrayList<JPanel> returnList = new ArrayList<JPanel>();
		int total = 0;
		SimpleDateFormat sf = new SimpleDateFormat("MM/DD/YYYY");
		for(Reservation r: myItems){
			total += r.getRoomCost().getCost();
			JPanel newPanel = new JPanel();
			newPanel.setLayout(new GridLayout(2,3));
			newPanel.add(new JLabel("User ID: "+r.getUserID()));
			newPanel.add(new JLabel("User Name: "+r.getUserName()));
			newPanel.add(new JLabel("Room ID: "+r.getRoomID()));
			newPanel.add(new JLabel("Check-in: "+sf.format(r.getStart().getTime())));
			newPanel.add(new JLabel("Check-out: "+sf.format(r.getEnd().getTime())));
			newPanel.add(new JLabel("Cost: $"+r.getRoomCost().getCost()));
			returnList.add(newPanel);
>>>>>>> origin/Linye
		}
		
	}
