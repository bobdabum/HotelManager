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