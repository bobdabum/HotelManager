import java.util.*;

/**
 * Data model for users.
 * @author Linye Ouyang
 *
 */
public class User {
	private ArrayList<Reservation> reservationList;
	private int userID;
	private String name;
	
	/**
	 * Constructor for User class.
	 * @param userReservations
	 * @param userID
	 * @param name
	 */
	public User(ArrayList<Reservation> userReservations,int userID,String name){
		this.setUserID(userID);
		this.setName(name);
		this.reservationList = userReservations;
	}
	/**
	 * Returns user reservations.
	 * @return
	 */
	public ArrayList<Reservation> getUserReservations() {
		return reservationList;
	}
	/**
	 * Adds reservation for user.
	 * @param r
	 */
	public void addReservation(Reservation r){
		reservationList.add(r);
	}
	/**
	 * Removes reservation specified by reservation id.
	 * @param reservationID
	 * @return
	 */
	public Reservation removeReservation(int reservationID){
		for(int i =0; i< reservationList.size(); i++){
			if(reservationList.get(i).getReservationID()==reservationID){
				return reservationList.remove(i);				
			}
		}
		return null;
	}

	/**
	 * Gets user ID.
	 * @return
	 */
	public int getUserID() {
		return userID;
	}
	/**
	 * Sets the user ID.
	 * @param userID
	 */
	public void setUserID(int userID) {
		this.userID = userID;
	}
	/**
	 * Gets the name of the user.
	 * @return
	 */
	public String getName() {
		return name;
	}
	/**
	 * Sets the name of the user.
	 * @param name
	 */
	public void setName(String name) {
		this.name = name;
	}
}
