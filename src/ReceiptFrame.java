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
		lp.populateList(new ListItemReceipt(null, total));
		frame.add(lp);
		frame.pack();
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
}
