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
						throwErrorDialogue(e);
					}
				}
				else
					throwErrorDialogue(new Exception("Incorrectly formatted date! Use MM/DD/YYYY!"));
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
						throwErrorDialogue(e);
					}
				}
				else
					throwErrorDialogue(new Exception("Incorrectly formatted date! Use MM/DD/YYYY!"));
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
						throwErrorDialogue(e);
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
						throwErrorDialogue(e);
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
						throwErrorDialogue(e);
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
						throwErrorDialogue(e);
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
		revalidate();
		repaint();
	}
	private void throwErrorDialogue(Exception e){
		JOptionPane.showMessageDialog(null,
				e.getMessage(),
				"Error",
				JOptionPane.ERROR_MESSAGE);
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
