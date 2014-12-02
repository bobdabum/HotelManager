import java.awt.GridLayout;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import javax.swing.*;


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
		}
		JPanel newPanel = new JPanel();
		newPanel.setLayout(new GridLayout(1,2));
		newPanel.add(new JLabel("Total Cost:"));
		newPanel.add(new JLabel("$"+total));
		returnList.add(newPanel);
		return returnList;
	}
}
