import java.util.*;


public class User {
	private ArrayList<Reservation> userReservations;
	private int userID;
	private String name;
	public User(ArrayList<Reservation> userReservations,int userID,String name){
		this.setUserID(userID);
		this.setName(name);
		this.userReservations = userReservations;
	}
	public ArrayList<Reservation> getUserReservations() {
		return userReservations;
	}
	public void addReservation(Reservation r){
		userReservations.add(r);
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
