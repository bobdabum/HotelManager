import javax.swing.event.ChangeEvent;

/**
 * Listener for reservation changes for MVC pattern.
 * @author Linye Ouyang
 *
 */
public interface ReservationListener {
	public void reservationsChanged(ChangeEvent e);
}
