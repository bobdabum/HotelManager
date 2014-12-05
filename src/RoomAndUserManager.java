import java.util.*;

import javax.swing.JOptionPane;
import javax.swing.event.*;


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
	 * Returns available rooms in specified time frame. 
	 * @param start
	 * @param end
	 * @return
	 */
	public void updateRoomParams(GregorianCalendar start, GregorianCalendar end, RoomCost rc) throws Exception{
		//check to make sure input dates are correct.
		GregorianCalendar now = new GregorianCalendar();		
		if(start.after(end))
			throw new Exception("Start date cannot be after end date.");
		if(now.after(start))
			throw new Exception("Start date cannot be before today.");
		if(!start.after(end)&&!end.after(start))
			throw new Exception("Need to book atleast 1 day.");
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
	 * @param day
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
	 * Updates reservationList filtered by userID.
	 * @param userID
	 */
	public void loadUserReservations(){
		reservationList = currentUser.getUserReservations();
		notifyReservationListeners();
	}
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
	public void login(int userID) throws Exception{
		if(userID < userList.size() && userID>=0){
			currentUser = userList.get(userID);
		}
		else
			throw new Exception("Can not find User ID");
	}
	/**
	 * Adds reservation to both user and room. Throws error if input parameters are invalid.
	 * @param start
	 * @param end
	 * @param roomID
	 * @param user
	 * @throws Exception
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
	public void addReservationFromFile(GregorianCalendar start, GregorianCalendar end, int roomID, int userID, int resID){
		Reservation temp = new Reservation(start,end,roomID,roomList.get(roomID).getRoomCost(),
				userID,userList.get(userID).getName(), resID);
		//updates users and room reservations to reflect new reservations.
		userList.get(userID).addReservation(temp);
		roomList.get(roomID).addReservation(temp);		
	}
	public void removeReservation(int reservationID){
		Reservation removedReservation = currentUser.removeReservation(reservationID);
		if(removedReservation!=null)
			roomList.get(removedReservation.getRoomID()).removeReservation(reservationID);
		notifyReservationListeners();
	}
	/**
	 * Attach ChangeListener to listener array
	 * @param c 
	 */
	public void attachRoomListener(RoomListener c)
	{
		roomListeners.add(c);
	}
	public void attachReservationListener(ReservationListener c)
	{
		reservationListeners.add(c);
	}
	private void notifyRoomListeners(){
		//inform listeners
		for(RoomListener l:roomListeners){
			l.roomsChanged(new ChangeEvent(this));
		}		
	}
	private void notifyReservationListeners(){
		//inform listeners
		for(ReservationListener l:reservationListeners){
			l.reservationsChanged(new ChangeEvent(this));
		}		
	}
	public ArrayList<Reservation> getReservationList() {
		return reservationList;
	}
	public ArrayList<Reservation> getReceiptList(){
		return receiptList;
	}
	public void clearReceipt(){
		receiptList.clear();
	}
	public ArrayList<Room> getAvailableRooms() {
		return availableRooms;
	}
	public static void throwDialogue(Exception e, int optionPaneType){
		JOptionPane.showMessageDialog(null,
				e.getMessage(),
				"Message",
				optionPaneType);
	}
}