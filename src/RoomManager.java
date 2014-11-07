import java.util.ArrayList;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class RoomManager {
	private ArrayList<Room> a;
	private ArrayList<Reservation> r;
	ArrayList<ChangeListener> listeners;
	
	public RoomManager(ArrayList<Room> a){
		this.a = a;
	}

	public ArrayList<Room> getRoom() {
		return a;
	}

	public void setRoom(ArrayList<Room> a) {
		this.a = a;
	}
	
	public void addReservation(ArrayList<Reservation> r){
		this.r = r;
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
	public void update(int location, double value) {
		for (ChangeListener l : listeners) {
			l.stateChanged(new ChangeEvent(this));
		}
	}
}