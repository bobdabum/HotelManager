import java.util.*;

public class User {
	private ArrayList<Reservation> reservationList;
	private int userID;
	private String name;
	public User(ArrayList<Reservation> userReservations,int userID,String name){
		this.setUserID(userID);
		this.setName(name);
		this.reservationList = userReservations;
	}
	public ArrayList<Reservation> getUserReservations() {
		return reservationList;
	}
	public void addReservation(Reservation r){
		reservationList.add(r);
	}
	public Reservation removeReservation(int reservationID){
		for(int i =0; i< reservationList.size(); i++){
			if(reservationList.get(i).getReservationID()==reservationID){
				return reservationList.remove(i);				
			}
		}
		return null;
	}

	public int getUserID() {
		return userID;
	}
	public void setUserID(int userID) {
		this.userID = userID;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
}
