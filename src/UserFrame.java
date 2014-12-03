import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.*;
import javax.swing.event.*;


public class UserFrame implements RoomListener, ReservationListener{
	final RoomAndUserManager myManager;	
	final JFrame frame;
	final ListPanel myListPanel;
	final JPanel rightPanel;
	final UserControllerPanel topPanel;
	public UserFrame(RoomAndUserManager myManager, JFrame frame){
		this.myManager = myManager;
		this.frame = frame;
		frame.setLayout(new BorderLayout());
		
		myListPanel = new ListPanel();
		rightPanel = new JPanel();
		topPanel = new UserControllerPanel(myManager);
		
		frame.add(topPanel,BorderLayout.NORTH);
		frame.add(myListPanel,BorderLayout.WEST);
		frame.add(rightPanel,BorderLayout.EAST);
		
		myListPanel.setVisible(false);
		rightPanel.setVisible(false);
	}
	@Override
	public void reservationsChanged(ChangeEvent e) {
		//fill in list panel
		myListPanel.clearList();
		ArrayList<Reservation> reservationList = myManager.getReservationList();
		for(Reservation r : reservationList)
			myListPanel.populateList(new ListItemReservation(r.getReservationID(), r));
		
		//fill in right panel
		rightPanel.removeAll();
		rightPanel.setLayout(new GridLayout(2,1));
		
		//button removes selected reservation.
		JButton cancelButton = new JButton("Cancel selected reservations");
		cancelButton.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				ArrayList<Integer> toBeDeleted = myListPanel.getSelected();
				for(int i : toBeDeleted)
					myManager.removeReservation(i);
			}});

		JButton returnButton = new JButton("Return to user options");
		rightPanel.add(cancelButton);
		rightPanel.add(returnButton);
		
		myListPanel.setVisible(true);
		rightPanel.setVisible(true);
		frame.repaint();
	}
	@Override
	public void roomsChanged(ChangeEvent e) {
		myListPanel.clearList();
		ArrayList<Room> roomList = myManager.getAvailableRooms();
		for(Room r: roomList)
			myListPanel.populateList(new ListItemRoom(r.getID()));
	}
}