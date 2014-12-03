<<<<<<< HEAD
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
}
=======
import java.util.ArrayList;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class RoomManager {
	private ArrayList<Room> a;
	private ArrayList<Reservation> r;
	ArrayList<ChangeListener> listeners;
	
	public RoomManager(ArrayList<Room> a){
		this.a = a;
		listeners = new ArrayList<ChangeListener>();
		r = new ArrayList<Reservation>();
	}

	public ArrayList<Room> getRoomList() {
		return a;
	}

	public ArrayList<Reservation> getReservations() {
		return r;
	}
	
	public void addReservation(Reservation res){
		r.add(res);
	}
	
	public int test(){
		return a.get(2).getID();
	}	
	
	/**
	 * Attach a listener to the Model
	 * @param c the listener
	 */
	public void attach(ChangeListener c) {
		listeners.add(c);
	}
	
	/**
	 * Change the data in the model at a particular location
	 * @param location the index of the field to change
	 * @param value the new value
	 */
	public void update() {
		for (ChangeListener l : listeners) {
			l.stateChanged(new ChangeEvent(this));
		}
	}
}
>>>>>>> origin/JiHoon
