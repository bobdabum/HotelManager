import java.util.*;

/**
 * Room data model.
 * @author My Pc
 *
 */
public class Room {
	private int roomID;
	private RoomCost roomCost;
	private ArrayList<Reservation> reservationList;

	/**
	 * Constructor for room.
	 * @param roomID
	 * @param roomCost
	 */
	public Room(int roomID, RoomCost roomCost){
		this.roomID = roomID;
		this.roomCost = roomCost;
		reservationList = new ArrayList<Reservation>();
	}

	/**
	 * Gets room id.
	 * @return
	 */
	public int getID(){
		return roomID;
	}
	/**
	 * Gets room cost.
	 * @return
	 */
	public RoomCost getRoomCost(){
		return roomCost;
	}
	/**
	 * Gets price of the room.
	 * @return
	 */
	public int getPrice(){
		return roomCost.getCost();
	}
	/**
	 * Return the reservation on the day defined.
	 * @param date
	 * @return
	 */
	public Reservation getReservationOnDay(GregorianCalendar date){
		for(Reservation r: reservationList){
			if(date.compareTo(r.getStart())==0||date.compareTo(r.getEnd())==0||
					(date.after(r.getStart())&&date.before(r.getEnd())))
				return r;
		}
		return null;
	}
	/**
	 * Checks to see if the room can be reserved on the date range specified.
	 * @param start Starting date.
	 * @param end Ending date.
	 * @return true if possible, false otherwise
	 */
	public boolean hasCollision(GregorianCalendar start, GregorianCalendar end){
		for(Reservation r : reservationList){
			if(end.before(r.getStart())||start.after(r.getEnd()))
				continue;
			else
				return true;
		}
		return false;
	}
	/**
	 * Adds a reservation to the list.
	 * @param r
	 */
	public void addReservation(Reservation r){
		reservationList.add(r);
		Collections.sort(reservationList);
	}
	/**
	 * Removes the reservation specified by the ID.
	 * @param reservationID
	 */
	public void removeReservation(int reservationID){
		for(int i =0; i< reservationList.size(); i++){
			if(reservationList.get(i).getReservationID()==reservationID){
				reservationList.remove(i);
				break;
			}
		}
	}
}
