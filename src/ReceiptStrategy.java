import java.util.ArrayList;

/**
 * Interface for the Receipt Strategy.
 * @author Linye Ouyang
 *
 */
public interface ReceiptStrategy {
	public ArrayList<Reservation> getList(RoomAndUserManager myManager);
}
