import java.util.ArrayList;
import javax.swing.*;

public class ReceiptFrame {
	private ReceiptStrategy rs;
	private RoomAndUserManager myManager;
	public ReceiptFrame(ReceiptStrategy rs, RoomAndUserManager myManager){
		this.rs = rs;
		this.myManager = myManager;
	}
	public void draw(){
		JFrame frame = new JFrame();
		ArrayList<Reservation> myList = rs.getList(myManager);
		ListPanel lp = new ListPanel();
		int total = 0;
		for(Reservation r:myList){
			lp.populateList(new ListItemReceipt(r,0));
			total+=r.getRoomCost().getCost();
		}
		frame.getContentPane().setBounds(0,0,375,500);
		lp.populateList(new ListItemReceiptSum(null, total, 0));
		JScrollPane listPanelContainer = new JScrollPane(lp);
		frame.add(listPanelContainer);
		frame.pack();
		frame.setVisible(true);
		myManager.clearReceipt();
	}
}
