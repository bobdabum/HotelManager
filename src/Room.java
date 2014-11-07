import java.util.*;

public class Room {
	private int roomID;
	private int roomType;
	private ArrayList<Reservation> reservationList;

	public Room(int roomID, int roomType){
		this.roomID = roomID;
		this.roomType = roomType;
		reservationList = new ArrayList<Reservation>();
	}

	public int getID(){
		return roomID;
	}
	public int getPrice(){
		if(roomType==0)
			return 80;
		else
			return 200;
	}
	public ArrayList<Reservation> getReservationOnDay(GregorianCalendar date){
		return reservationList;
	}
	public boolean hasCollision(GregorianCalendar start, GregorianCalendar end){
		Iterator<Reservation> iter = reservationList.iterator();
		boolean collision = false;
		while(iter.hasNext()){
			Reservation temp = iter.next();
			if(end.before(temp.getStart())||start.after(temp.getStart()))
				continue;
			else{
				collision = true;
				break;
			}
		}
		return collision;
	}
	public void addReservation(Reservation r){
		reservationList.add(r);
	}
}
