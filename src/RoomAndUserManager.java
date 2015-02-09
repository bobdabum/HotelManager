import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;

import javax.swing.JOptionPane;
import javax.swing.event.*;

/**
 * Manages both room and user lists. Is the model in the MVC design pattern.
 * @author Linye Ouyang
 *
 */
public class RoomAndUserManager {
	//userList and roomList are core data models.
	private ArrayList<Room> roomList;
	private ArrayList<User> userList;

	//these arrayLists reflect query results.
	private ArrayList<Room> availableRooms;
	private ArrayList<Reservation> reservationList, receiptList;
	private ArrayList<RoomListener> roomListeners;
	private ArrayList<ReservationListener> reservationListeners;

	//Saved session parameters
	private User currentUser;
	private int curResID;
	private GregorianCalendar curStart, curEnd;

	/**
	 * Model for hotel manager program. Contains listeners for both room listeners and reservation lisiteners.
	 * @param roomList Contains all the rooms managed by the hotel.
	 * @param curResID Counter for the unique identifier "reservationID" for the 
	 * reservation class. Counter increased every time a reservation is created. 
	 */
	public RoomAndUserManager(ArrayList<Room> roomList, int curResID){
		this.roomList=roomList;
		userList = new ArrayList<User>();
		availableRooms = new ArrayList<Room>();
		reservationList = new ArrayList<Reservation>();
		receiptList = new ArrayList<Reservation>();
		roomListeners = new ArrayList<RoomListener>();
		reservationListeners = new ArrayList<ReservationListener>();
		curStart = new GregorianCalendar();
		curEnd = new GregorianCalendar();
		this.curResID = curResID;
	}

	/**
	 * Returns available rooms in specified time frame. Throws exception if conditions are not met.
	 * @param start Start date of reservation.
	 * @param end End date of reservation.
	 * @return
	 */
	public void updateRoomParams(GregorianCalendar start, GregorianCalendar end, RoomCost rc) throws Exception{
		//check to make sure input dates are correct.
		GregorianCalendar now = new GregorianCalendar();		
		if(start.after(end))
			throw new Exception("Start date cannot be after end date.");
		if(now.after(start))
			throw new Exception("Start date cannot be before today.");
		if(daysBetweenDates(start, end)>60)
			throw new Exception("Can not stay for more than 60 days.");
		//update availableRooms
		availableRooms.clear();
		for(Room r:roomList){
			if(!r.hasCollision(start, end) && r.getRoomCost().equals(rc))
				availableRooms.add(r);
		}
		curStart = start; curEnd = end;
		notifyRoomListeners();
	}

	/**
	 * Private method for counting the number of days between two days.
	 * @param start Starting date.
	 * @param end Ending date.
	 * @return Number of days between the two dates.
	 */
	private int daysBetweenDates(GregorianCalendar start, GregorianCalendar end){
		int numDays = 0;
		GregorianCalendar tempStart = (GregorianCalendar) start.clone();
		while(tempStart.before(end)){
			tempStart.add(GregorianCalendar.DAY_OF_MONTH, 1);
			numDays++;
		}
		return numDays;
	}

	/**
	 * Updates the reservationList filtered by day selected.
	 * @param day The date of interest.
	 */
	public void updateCalendarDay(GregorianCalendar day){
		//gets reservationList on date of interest
		reservationList.clear();
		for(Room r:roomList){
			Reservation temp = r.getReservationOnDay(day);
			if(temp!=null)
				reservationList.add(temp);
		}
		notifyReservationListeners();
	}

	/**
	 * Loads the current user's reservations for consumption by MVC.
	 * @param userID
	 */
	public void loadUserReservations(){
		reservationList = currentUser.getUserReservations();
		notifyReservationListeners();
	}

	/**
	 * Gets current user Reservations.
	 * @return the ArrayList of reservations.
	 */
	public ArrayList<Reservation> getCurrentUserReservations(){
		return currentUser.getUserReservations();
	}

	/**
	 * Creates new user and returns the userID.
	 * @param name
	 * @return
	 */
	public int createUser(String name){
		currentUser = new User(new ArrayList<Reservation>(),userList.size(),name);
		userList.add(currentUser);
		return userList.size()-1;
	}

	/**
	 * Sets the current user to the one specified by the user ID.
	 * @param userID Identifier for the user.
	 * @throws Exception Returns exception if user can not be found.
	 */
	public void login(int userID) throws Exception{
		if(userID < userList.size() && userID>=0){
			currentUser = userList.get(userID);
		}
		else
			throw new Exception("Can not find User ID");
	}

	/**
	 * Adds a reservation specified by the room id.
	 * @param roomID
	 */
	public void addReservation(int roomID){
		int userID = currentUser.getUserID();
		Reservation temp = new Reservation(curStart,curEnd,roomID,roomList.get(roomID).getRoomCost(),
				userID,userList.get(userID).getName(), curResID);
		//updates users and room reservations to reflect new reservations.
		userList.get(userID).addReservation(temp);
		roomList.get(roomID).addReservation(temp);

		//remove room from available rooms assuming no change in period of interest
		for(int i=0;i<availableRooms.size();i++){
			if(availableRooms.get(i).getID()==roomID)
			{
				availableRooms.remove(i);
				break;
			}
		}
		notifyRoomListeners();

		//increments unique reservation id counter
		curResID++;
		//add to receiptList.
		receiptList.add(temp);
	}

	/**
	 * Adds reservations to model from file.
	 * @param start Start date.
	 * @param end End date.
	 * @param roomID Room ID.
	 * @param userID User ID.
	 * @param resID Reservation ID.
	 */
	public void addReservationFromFile(GregorianCalendar start, GregorianCalendar end, int roomID, int userID, int resID){
		Reservation temp = new Reservation(start,end,roomID,roomList.get(roomID).getRoomCost(),
				userID,userList.get(userID).getName(), resID);
		//updates users and room reservations to reflect new reservations.
		userList.get(userID).addReservation(temp);
		roomList.get(roomID).addReservation(temp);		
	}

	/**
	 * Removes reservation specified by the reservation ID.
	 * @param reservationID
	 */
	public void removeReservation(int reservationID){
		Reservation removedReservation = currentUser.removeReservation(reservationID);
		if(removedReservation!=null)
			roomList.get(removedReservation.getRoomID()).removeReservation(reservationID);
		notifyReservationListeners();
	}

	/**
	 * Attach RoomListener to listener array
	 * @param c 
	 */
	public void attachRoomListener(RoomListener c)
	{
		roomListeners.add(c);
	}

	/**
	 * Attach ReservationListener to listener array.
	 * @param c
	 */
	public void attachReservationListener(ReservationListener c)
	{
		reservationListeners.add(c);
	}

	/**
	 * Notifies all RoomListeners.
	 */
	private void notifyRoomListeners(){
		//inform listeners
		for(RoomListener l:roomListeners){
			l.roomsChanged(new ChangeEvent(this));
		}		
	}

	/**
	 * Notifies all Reservation Listeners.
	 */
	private void notifyReservationListeners(){
		//inform listeners
		for(ReservationListener l:reservationListeners){
			l.reservationsChanged(new ChangeEvent(this));
		}		
	}

	/**
	 * Returns reservations of interest. 
	 * @return
	 */
	public ArrayList<Reservation> getReservationList() {
		return reservationList;
	}

	/**
	 * Returns receipts of interest.
	 * @return
	 */
	public ArrayList<Reservation> getReceiptList(){
		return receiptList;
	}

	/**
	 * Returns all reservations reserved
	 */
	public void clearReceipt(){
		receiptList.clear();
	}
	/**
	 * Returns rooms of interest.
	 * @return
	 */
	public ArrayList<Room> getAvailableRooms() {
		return availableRooms;
	}
	
	/**
	 * Method used for catching exceptions throw during the execution of the program.
	 * @param e
	 * @param optionPaneType
	 */
	public static void throwDialogue(Exception e, int optionPaneType){
		JOptionPane.showMessageDialog(null,
				e.getMessage(),
				"Message",
				optionPaneType);
	}

	/**
	 * Saves to file.
	 */
	public void saveAll(){
		try{
			File userFile = new File("userList.txt");
			File reservationFile = new File("reservationList.txt");
			FileWriter userWriter = new FileWriter(userFile);
			FileWriter reservationWriter = new FileWriter(reservationFile);

			//users
			userWriter.write(String.format(curResID+"%n"));
			for(User u: userList){
				userWriter.write(String.format(u.getName() + "%n"));
			}	

			//reservations
			SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/YYYY");

			for(Reservation r: reservationList){
				reservationWriter.write(String.format((r.getRoomID() + "," + r.getUserID() + "," + r.getReservationID()
						+ "," + sdf.format(r.getStart().getTime()) + "," + 
						sdf.format(r.getEnd().getTime())+"%n")));
			}
			userWriter.close();
			reservationWriter.close();
		}
		catch(IOException e){

		}
	}
}