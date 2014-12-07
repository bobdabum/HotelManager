import java.util.ArrayList;

/**
 * Interface for the Receipt Strategy.
 * @author My Pc
 *
 */
public interface ReceiptStrategy {
	public ArrayList<Reservation> getList(RoomAndUserManager myManager);
}
