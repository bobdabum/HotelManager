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
		ListPanel<Reservation> lp = new ListPanel<Reservation>(new ListItemReceipt());
		lp.populateList(myList, null);
		frame.add(lp);
		frame.pack();
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
}
