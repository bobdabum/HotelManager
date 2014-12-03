import java.awt.*;
import java.awt.event.*;
import java.util.GregorianCalendar;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

@SuppressWarnings("serial")
public class UserControllerPanel extends JPanel{
	private RoomAndUserManager myManager;
	public UserControllerPanel(RoomAndUserManager myManager){
		this.myManager = myManager;
		changeToUserLogin();
	}
	private void changeToUserLogin(){
		removeAll();
		setLayout(new GridLayout(2,3));
		final JButton newUser = new JButton("Create new account");
		final JButton exisUser = new JButton("Login to existing account");

		final JTextField infoField = new JTextField();
		infoField.setVisible(false);
		final JLabel desc = new JLabel("Enter user ID:");
		desc.setVisible(false);
		final JButton confirmButton = new JButton("Confirm");
		confirmButton.setVisible(false);

		newUser.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				//set other components to be visible.
				desc.setText("Please enter your name:");
				infoField.setVisible(true);desc.setVisible(true);confirmButton.setVisible(true);

				//Change what confirm button does. Remove existing action listeners and add new one
				for(ActionListener a: confirmButton.getActionListeners())
					confirmButton.removeActionListener(a);
				confirmButton.addActionListener(new ActionListener(){
					@Override
					public void actionPerformed(ActionEvent arg0) {
						int userID = myManager.createUser(infoField.getText());
						throwDialogue(new Exception("Your user ID is: "+userID+". Please record this!"), JOptionPane.INFORMATION_MESSAGE);
						changeToUserOptions();
					}
				});
				revalidate();
				repaint();
			}});
		exisUser.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				//set other components to be visible
				desc.setText("Please enter your user ID:");
				infoField.setVisible(true);desc.setVisible(true);confirmButton.setVisible(true);

				//Change what confirm button does. Remove existing action listeners and add new one
				for(ActionListener a: confirmButton.getActionListeners())
					confirmButton.removeActionListener(a);
				confirmButton.addActionListener(new ActionListener(){
					@Override
					public void actionPerformed(ActionEvent arg0) {
						try{
							myManager.login(Integer.parseInt(infoField.getText()));
						}
						catch(NumberFormatException e){
							throwDialogue(new Exception("User ID must be numerical value!"), JOptionPane.ERROR_MESSAGE);
						}
						catch(Exception e){
							throwDialogue(e, JOptionPane.ERROR_MESSAGE);
						}
						changeToUserOptions();
					}});
				revalidate();
				repaint();
			}});

		add(newUser);add(exisUser);add(new JLabel(""));
		add(desc);add(infoField);
		revalidate();
		repaint();
	}
	private void changeToUserOptions(){
		removeAll();
		setLayout(new FlowLayout());
		JButton makeRes = new JButton("Make Reservation");
		JButton viewRes = new JButton("View/Cancel Reservation");
		makeRes.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				changeToMakeReservation();
			}
		});
		viewRes.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				changeToViewReservations();
			}			
		});
		add(makeRes);
		add(viewRes);
		revalidate();
		repaint();
	}
	private void changeToMakeReservation(){
		removeAll();
		final JTextField startField = new JTextField();
		final JTextField endField = new JTextField();
		final JLabel startLabel = new JLabel("Check-in(MM/DD/YYY):");
		final JLabel endLabel = new JLabel("Check-out(MM/DD/YYY):");
		final JLabel typeLabel = new JLabel("Room Type:");
		final JButton luxButton = new JButton("$200");
		final JButton econButton = new JButton("$80");

		luxButton.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {					
				GregorianCalendar start = createCalendar(startField.getText());
				GregorianCalendar end = createCalendar(endField.getText());
				if(start!=null&&end!=null){
					try{
						myManager.updateRoomCost(RoomCost.Luxury);
						myManager.updateRoomParams(start, end);
					}
					catch(Exception e){
						throwDialogue(e, JOptionPane.ERROR_MESSAGE);
					}
				}
				else
					throwDialogue(new Exception("Incorrectly formatted date! Use MM/DD/YYYY!"), JOptionPane.ERROR_MESSAGE);
			}
		});
		econButton.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {					
				GregorianCalendar start = createCalendar(startField.getText());
				GregorianCalendar end = createCalendar(endField.getText());
				if(start!=null&&end!=null){
					try{
						myManager.updateRoomCost(RoomCost.Economical);
						myManager.updateRoomParams(start, end);
					}
					catch(Exception e){
						throwDialogue(e, JOptionPane.ERROR_MESSAGE);
					}
				}
				else
					throwDialogue(new Exception("Incorrectly formatted date! Use MM/DD/YYYY!"), JOptionPane.ERROR_MESSAGE);
			}
		});

		startField.getDocument().addDocumentListener(new DocumentListener(){
			@Override
			public void changedUpdate(DocumentEvent arg0) {	
			}
			@Override
			public void insertUpdate(DocumentEvent arg0) {					
				GregorianCalendar start = createCalendar(startField.getText());
				GregorianCalendar end = createCalendar(endField.getText());
				if(start!=null&&end!=null){
					try{
						myManager.updateRoomCost(RoomCost.Economical);
						myManager.updateRoomParams(start, end);
					}
					catch(Exception e){
						throwDialogue(e, JOptionPane.ERROR_MESSAGE);
					}
				}
			}
			@Override
			public void removeUpdate(DocumentEvent arg0) {					
				GregorianCalendar start = createCalendar(startField.getText());
				GregorianCalendar end = createCalendar(endField.getText());
				if(start!=null&&end!=null){
					try{
						myManager.updateRoomCost(RoomCost.Economical);
						myManager.updateRoomParams(start, end);
					}
					catch(Exception e){
						throwDialogue(e, JOptionPane.ERROR_MESSAGE);
					}
				}	
			}});
		endField.getDocument().addDocumentListener(new DocumentListener(){
			@Override
			public void changedUpdate(DocumentEvent arg0) {	
			}
			@Override
			public void insertUpdate(DocumentEvent arg0) {					
				GregorianCalendar start = createCalendar(startField.getText());
				GregorianCalendar end = createCalendar(endField.getText());
				if(start!=null&&end!=null){
					try{
						myManager.updateRoomCost(RoomCost.Economical);
						myManager.updateRoomParams(start, end);
					}
					catch(Exception e){
						throwDialogue(e, JOptionPane.ERROR_MESSAGE);
					}
				}
			}
			@Override
			public void removeUpdate(DocumentEvent arg0) {					
				GregorianCalendar start = createCalendar(startField.getText());
				GregorianCalendar end = createCalendar(endField.getText());
				if(start!=null&&end!=null){
					try{
						myManager.updateRoomCost(RoomCost.Economical);
						myManager.updateRoomParams(start, end);
					}
					catch(Exception e){
						throwDialogue(e, JOptionPane.ERROR_MESSAGE);
					}
				}	
			}});

		setLayout(new GridLayout(4,2));
		add(startLabel);
		add(endLabel);
		add(startField);
		add(endField);
		add(typeLabel);
		add(new JLabel(""));
		add(luxButton);
		add(econButton);
		revalidate();
		repaint();
	}
	private void changeToViewReservations(){
		removeAll();
		myManager.getCurrentUserReservations();
		revalidate();
		repaint();
	}
	private void throwDialogue(Exception e, int optionPaneType){
		JOptionPane.showMessageDialog(null,
				e.getMessage(),
				"Error",
				optionPaneType);
	}
	private GregorianCalendar createCalendar(String date){
		GregorianCalendar returnCal = null;
		String[] startArray = date.split("/");
		if(startArray.length==3){
			try{
				int month = Integer.parseInt(startArray[0])-1;
				int day = Integer.parseInt(startArray[1]);
				int year = Integer.parseInt(startArray[2]);
				returnCal = new GregorianCalendar(year,month,day);
			}
			catch(NumberFormatException e){
			}
		}

		return returnCal;
	}
}
