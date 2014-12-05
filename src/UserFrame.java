import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.event.*;

public class UserFrame implements RoomListener, ReservationListener{
	final RoomAndUserManager myManager;	
	final JFrame frame;
	final ListPanel myListPanel;
	final JPanel rightPanel;
	final UserControllerPanel topPanel;
	final JScrollPane listPanelContainer;
	public UserFrame(RoomAndUserManager myManager, JFrame frame){
		this.myManager = myManager;
		this.frame = frame;
		//set frame dimensions
		frame.setBounds(0, 0, 500, 500);
		frame.setLayout(new BorderLayout());

		myListPanel = new ListPanel();
		rightPanel = new JPanel();

		topPanel = new UserControllerPanel(myManager);
		topPanel.setBorder(new EmptyBorder(10,10,10,10));

		listPanelContainer = new JScrollPane(myListPanel);
		listPanelContainer.setBorder(new EmptyBorder(0,0,0,0));

		rightPanel.setLayout(new GridLayout(2,1));
		rightPanel.setBorder(new EmptyBorder(10,10,10,10));

		frame.add(topPanel,BorderLayout.PAGE_START);
		frame.add(listPanelContainer,BorderLayout.LINE_START);
		frame.add(rightPanel,BorderLayout.CENTER);

		myListPanel.setVisible(false);
		rightPanel.setVisible(false);
	}
	public void reservationsChanged(ChangeEvent e) {
		//fill in list panel
		myListPanel.clearList();
		ArrayList<Reservation> reservationList = myManager.getReservationList();
		if(reservationList.size()>0){
			myListPanel.populateList(new ListItemGeneric(-1,"Rooms Reserved"));
			for(Reservation r : reservationList)
				myListPanel.populateList(new ListItemReservation(r.getReservationID(), r, myListPanel));
		}
		else{
			myListPanel.populateList(new ListItemGeneric(0, "No Rooms reserved."));
		}
		//fill in right panel
		rightPanel.removeAll();

		//button removes selected reservation.
		JButton cancelButton = new JButton("Cancel selected reservations");
		cancelButton.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				int toBeDeleted = myListPanel.getSelected();
				myManager.removeReservation(toBeDeleted);
			}});

		JButton returnButton = new JButton("Return to user options");
		returnButton.addActionListener(new ActionListener() {			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				myListPanel.setVisible(false);
				rightPanel.setVisible(false);
				topPanel.changeToUserOptions();
			}
		});
		rightPanel.add(cancelButton);
		rightPanel.add(returnButton);

		myListPanel.setVisible(true);
		rightPanel.setVisible(true);
		rightPanel.revalidate();
		rightPanel.repaint();
	}
	public void roomsChanged(ChangeEvent e) {
		myListPanel.clearList();
		ArrayList<Room> roomList = myManager.getAvailableRooms();
		if(roomList.size()>0){
			myListPanel.populateList(new ListItemGeneric(-1,"Room Type: "+roomList.get(0).getRoomCost().toString()));
			for(Room r: roomList)
				myListPanel.populateList(new ListItemRoom(r.getID(),myListPanel));
		}
		else{
			myListPanel.populateList(new ListItemGeneric(0, "No Rooms available."));
		}

		rightPanel.removeAll();
		JButton confirmButton = new JButton("Confirm Reservation");
		JButton finishTransaction = new JButton("Finish Transaction");

		confirmButton.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if(myListPanel.getSelected()!=-1){
					myManager.addReservation(myListPanel.getSelected());
				}
				else
					RoomAndUserManager.throwDialogue(new Exception("No room selected!"), JOptionPane.ERROR_MESSAGE);
			}});
		finishTransaction.addActionListener(new ActionListener() {			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if(myManager.getReceiptList().size()>0){
					rightPanel.removeAll();
					JButton simpReceipt = new JButton("Print Simple Receipt");
					JButton compReceipt = new JButton("Print Comprehensive Receipt");

					simpReceipt.addActionListener(receiptFactory(new ReceiptStrategySimple()));
					compReceipt.addActionListener(receiptFactory(new ReceiptStrategyComprehensive()));

					rightPanel.add(simpReceipt);
					rightPanel.add(compReceipt);

					rightPanel.revalidate();
					rightPanel.repaint();
				}
				else
					RoomAndUserManager.throwDialogue(new Exception("No rooms reserved!"), JOptionPane.ERROR_MESSAGE);
			}
		});
		rightPanel.add(confirmButton);
		rightPanel.add(finishTransaction);
		rightPanel.revalidate();
		rightPanel.repaint();		
		myListPanel.setVisible(true);
		rightPanel.setVisible(true);
		frame.repaint();
	}
	private ActionListener receiptFactory(final ReceiptStrategy r){
		return new ActionListener() {						
			@Override
			public void actionPerformed(ActionEvent arg0) {
				ReceiptFrame rf = new ReceiptFrame(r,myManager);
				rf.draw();
				rightPanel.setVisible(false);
				myListPanel.setVisible(false);
				topPanel.changeToUserOptions();
				frame.repaint();
			}
		};
	}
}