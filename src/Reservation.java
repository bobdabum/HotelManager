import java.util.*;
/**
 * Data model for reservation.
 * @author My Pc
 *
 */
public class Reservation implements Comparable<Reservation>{
	private GregorianCalendar start, end;
	private int roomID;

	private int userID;
	private int reservationID;
	private String userName;
	private RoomCost roomCost;
	
	/**
	 * Constructs a reservation object.
	 * @param start Start date for the reservation.
	 * @param end End date for the reservation.
	 * @param roomID ID of the room selected.
	 * @param rc Cost of the room.
	 * @param userID ID of the user reserving the room.
	 * @param userName Name of the user.
	 * @param reservationID Unique identifier for the reservation.
	 */
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
	/**
	 * Gets start date.
	 * @return
	 */
	public GregorianCalendar getStart() {
		return start;
	}
	/**
	 * Sets start date.
	 * @param start
	 */
	public void setStart(GregorianCalendar start) {
		this.start = start;
	}
	/**
	 * Gets end date.
	 * @return
	 */
	public GregorianCalendar getEnd() {
		return end;
	}
	/**
	 * Sets end date.
	 * @param end
	 */
	public void setEnd(GregorianCalendar end) {
		this.end = end;
	}
	/**
	 * Gets room id.
	 * @return
	 */
	public int getRoomID() {
		return roomID;
	}
	/**
	 * Sets room id.
	 * @param roomID
	 */
	public void setRoomID(int roomID) {
		this.roomID = roomID;
	}
	/**
	 * Gets user id.
	 * @return
	 */
	public int getUserID() {
		return userID;
	}
	/**
	 * Sets user ID.
	 * @param userID
	 */
	public void setUserID(int userID) {
		this.userID = userID;
	}
	/**
	 * Allows reservations to be compared by date.
	 */
	public int compareTo(Reservation r) {
		if(start.after(r.getStart()))
			return 1;
		else if(start.before(r.getStart()))
			return -1;
		else
			return 0;
	}
	/**
	 * Gets user name.
	 * @return
	 */
	public String getUserName() {
		return userName;
	}
	/**
	 * Sets user name.
	 * @param userName
	 */
	public void setUserName(String userName) {
		this.userName = userName;
	}
	/**
	 * Gets room cost.
	 * @return
	 */
	public RoomCost getRoomCost() {
		return roomCost;
	}
	/**
	 * Sets room cost.
	 * @param roomCost
	 */
	public void setRoomCost(RoomCost roomCost) {
		this.roomCost = roomCost;
	}
	/**
	 * Gets reservation ID
	 * @return
	 */
	public int getReservationID() {
		return reservationID;
	}
	/**
	 * Sets reservation ID.
	 * @param reservationID
	 */
	public void setReservationID(int reservationID) {
		this.reservationID = reservationID;
	}
}
