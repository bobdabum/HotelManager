import java.awt.*;
import java.awt.event.*;
import java.util.GregorianCalendar;

import javax.swing.*;

/**
 * User controller panel is what the user interacts with to change between functionalities.
 * @author Linye Ouyang
 *
 */
@SuppressWarnings("serial")
public class UserControllerPanel extends JPanel{
	private RoomAndUserManager myManager;
	private UserFrame parent;
	
	/**
	 * Constructor for Panel.
	 * @param myManager
	 */
	public UserControllerPanel(RoomAndUserManager myManager, UserFrame parent){
		this.myManager = myManager;
		changeToUserLogin();
		this.parent = parent;
	}
	
	/**
	 * Changes to User login display.
	 */
	private void changeToUserLogin(){
		removeAll();
		setLayout(new GridLayout(2,3));
		final JButton newUser = new JButton("Create new account");
		final JButton exisUser = new JButton("Login to existing account");
		final JButton exitUserFrame = new JButton("Exit to manager");

		final JTextField infoField = new JTextField();
		infoField.setVisible(false);
		final JLabel desc = new JLabel("Enter user ID:");
		desc.setVisible(false);
		final JButton confirmButton = new JButton("Confirm");
		confirmButton.setVisible(false);

		newUser.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				//set other components to be visible.
				desc.setText("Please enter your name:");
				infoField.setVisible(true);desc.setVisible(true);confirmButton.setVisible(true);

				//Change what confirm button does. Remove existing action listeners and add new one
				for(ActionListener a: confirmButton.getActionListeners())
					confirmButton.removeActionListener(a);
				confirmButton.addActionListener(new ActionListener(){
					public void actionPerformed(ActionEvent arg0) {
						if(infoField.getText().length()>0)
						{
							int userID = myManager.createUser(infoField.getText());
							RoomAndUserManager.throwDialogue(new Exception("Your user ID is: "+userID+". Please record this!"), JOptionPane.INFORMATION_MESSAGE);
							changeToUserOptions();
						}
						else
							RoomAndUserManager.throwDialogue(new Exception("Please enter a name!"), JOptionPane.ERROR_MESSAGE);
					}
				});
				revalidate();
				repaint();
			}});
		exisUser.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				//set other components to be visible
				desc.setText("Please enter your user ID:");
				infoField.setVisible(true);desc.setVisible(true);confirmButton.setVisible(true);

				//Change what confirm button does. Remove existing action listeners and add new one
				for(ActionListener a: confirmButton.getActionListeners())
					confirmButton.removeActionListener(a);
				confirmButton.addActionListener(new ActionListener(){
					public void actionPerformed(ActionEvent arg0) {
						try{
							myManager.login(Integer.parseInt(infoField.getText()));
							changeToUserOptions();
						}
						catch(NumberFormatException e){
							RoomAndUserManager.throwDialogue(new Exception("User ID must be numerical value!"), JOptionPane.ERROR_MESSAGE);
						}
						catch(Exception e){
							RoomAndUserManager.throwDialogue(e, JOptionPane.ERROR_MESSAGE);
						}
					}});
				revalidate();
				repaint();
			}});
		exitUserFrame.addActionListener(new ActionListener() {			
			public void actionPerformed(ActionEvent arg0) {
				parent.exitUser();
			}
		});
		
		add(newUser);add(exisUser);add(exitUserFrame);
		add(desc);add(infoField);add(confirmButton);
		revalidate();
		repaint();
	}

	/**
	 * Changes to user option display.
	 */
	public void changeToUserOptions(){
		removeAll();
		setLayout(new FlowLayout());
		JButton makeRes = new JButton("Make Reservation");
		JButton viewRes = new JButton("View/Cancel Reservation");
		JButton logOut = new JButton("Log out");
		makeRes.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				changeToMakeReservation();
			}
		});
		viewRes.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				changeToViewReservations();
			}			
		});
		logOut.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				changeToUserLogin();
			}
		});
		add(makeRes);
		add(viewRes);
		add(logOut);
		revalidate();
		repaint();
	}

	/**
	 * Changes to make reservation display.
	 */
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
			public void actionPerformed(ActionEvent arg0) {					
				GregorianCalendar start = createCalendar(startField.getText());
				GregorianCalendar end = createCalendar(endField.getText());
				if(start!=null&&end!=null){
					try{
						myManager.updateRoomParams(start, end,RoomCost.Luxury);
					}
					catch(Exception e){
						RoomAndUserManager.throwDialogue(e, JOptionPane.ERROR_MESSAGE);
					}
				}
				else
					RoomAndUserManager.throwDialogue(new Exception("Incorrectly formatted date! Use MM/DD/YYYY!"), JOptionPane.ERROR_MESSAGE);
			}
		});
		econButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {					
				GregorianCalendar start = createCalendar(startField.getText());
				GregorianCalendar end = createCalendar(endField.getText());
				if(start!=null&&end!=null){
					try{
						myManager.updateRoomParams(start, end, RoomCost.Economical);
					}
					catch(Exception e){
						RoomAndUserManager.throwDialogue(e, JOptionPane.ERROR_MESSAGE);
					}
				}
				else
					RoomAndUserManager.throwDialogue(new Exception("Incorrectly formatted date! Use MM/DD/YYYY!"), JOptionPane.ERROR_MESSAGE);
			}
		});

		setLayout(new GridLayout(4,2));
		add(startLabel);
		add(endLabel);
		add(startField);
		add(endField);
		add(typeLabel);
		add(new JLabel(""));
		add(econButton);
		add(luxButton);
		revalidate();
		repaint();
	}

	/**
	 * Changes to reservation view display.
	 */
	private void changeToViewReservations(){
		removeAll();
		myManager.loadUserReservations();
		revalidate();
		repaint();
	}

	/**
	 * Creates a GregorianCalendar from a date String.
	 * @param date
	 * @return
	 */
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
