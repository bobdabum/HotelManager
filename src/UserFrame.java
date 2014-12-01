import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.event.*;


public class UserFrame implements RoomListener, ReservationListener{
	RoomAndUserManager myManager;
	final JFrame frame;
	public UserFrame(RoomAndUserManager myManager){
		this.myManager = myManager;
		frame = new JFrame();
		frame.setLayout(new BorderLayout());
	}
	@Override
	public void reservationsChanged(ChangeEvent e) {
		
	}
	@Override
	public void roomsChanged(ChangeEvent e) {
		// TODO Auto-generated method stub
		
	}
}