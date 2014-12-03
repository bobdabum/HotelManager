import java.util.*;
public class Reservation implements Comparable<Reservation>{
	private GregorianCalendar start, end;
	private int roomID;
	private int userID;
	private int reservationID;
	private String userName;
	private RoomCost roomCost;
	public Reservation(GregorianCalendar start, GregorianCalendar end, int roomID,
						RoomCost rc, int userID, String userName, int reservationID){
		setStart(start);
		setEnd(end);
		setRoomID(roomID);
		setUserID(userID);
		setUserName(userName);
		setRoomCost(rc);
		setReservationID(reservationID);
	}
	public GregorianCalendar getStart() {
		return start;
	}
	public void setStart(GregorianCalendar start) {
		this.start = start;
	}
	public GregorianCalendar getEnd() {
		return end;
	}
	public void setEnd(GregorianCalendar end) {
		this.end = end;
	}
	public int getRoomID() {
		return roomID;
	}
	public void setRoomID(int roomID) {
		this.roomID = roomID;
	}
	public int getUserID() {
		return userID;
	}
	public void setUserID(int userID) {
		this.userID = userID;
	}
	public int compareTo(Reservation r) {
		if(start.after(r.getStart()))
			return 1;
		else if(start.before(r.getStart()))
			return -1;
		else
			return 0;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public RoomCost getRoomCost() {
		return roomCost;
	}
	public void setRoomCost(RoomCost roomCost) {
		this.roomCost = roomCost;
	}
	public int getReservationID() {
		return reservationID;
	}
	public void setReservationID(int reservationID) {
		this.reservationID = reservationID;
	}
}