import java.util.*;

import javax.swing.event.*;


public class RoomManager {
	private ArrayList<Room> roomList;
	private ArrayList<Room> availableRooms;
	private ArrayList<Reservation> reservationList;
	private ArrayList<ChangeListener> myRoomListeners,myReservationListeners;
	public RoomManager(ArrayList<Room> roomList){
		this.roomList=roomList;
	}
	/**
	 * Returns available rooms in specified time frame. 
	 * @param start
	 * @param end
	 * @return
	 */
	public void updatePeriod(GregorianCalendar start, GregorianCalendar end){
		for(ChangeListener l:myRoomListeners){
			//TODO update roomList.
			l.stateChanged(new ChangeEvent(this));
		}
	}
	public void updateCalendarDay(GregorianCalendar day){
		//TODO update reservationList.
		for(ChangeListener l:myReservationListeners){
			l.stateChanged(new ChangeEvent(this));
		}
	}
	public void getUserReservation(User user){
		//TODO set ReservationList to user reservations
	}
	public void addReservation(GregorianCalendar start, GregorianCalendar end, int roomID, User user){
		//TODO stuff
	}
	public void attachRoomListener(ChangeListener c)
	{
		myRoomListeners.add(c);
	}
	public void attachReservationListener(ChangeListener c)
	{
		myReservationListeners.add(c);
	}
	public ArrayList<Reservation> getReservationList() {
		return reservationList;
	}
	public ArrayList<Room> getAvailableRooms() {
		return availableRooms;
	}
}
