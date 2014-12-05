import java.util.*;

public class Room {
	private int roomID;
	private RoomCost roomCost;
	private ArrayList<Reservation> reservationList;

	public Room(int roomID, RoomCost roomCost){
		this.roomID = roomID;
		this.roomCost = roomCost;
		reservationList = new ArrayList<Reservation>();
	}

	public int getID(){
		return roomID;
	}
	public RoomCost getRoomCost(){
		return roomCost;
	}
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
	public boolean hasCollision(GregorianCalendar start, GregorianCalendar end){
		for(Reservation r : reservationList){
			if(end.before(r.getStart())||start.after(r.getEnd()))
				continue;
			else
				return true;
		}
		return false;
	}
	public void addReservation(Reservation r){
		reservationList.add(r);
		Collections.sort(reservationList);
	}
	public void removeReservation(int reservationID){
		for(int i =0; i< reservationList.size(); i++){
			if(reservationList.get(i).getReservationID()==reservationID){
				reservationList.remove(i);
				break;
			}
		}
	}
}
