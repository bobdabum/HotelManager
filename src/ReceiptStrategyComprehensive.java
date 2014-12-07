import java.util.ArrayList;

/**
 * Strategy for receipt frame. Changes
 * @author My Pc
 *
 */
public class ReceiptStrategyComprehensive implements ReceiptStrategy {

	public ArrayList<Reservation> getList(RoomAndUserManager myManager) {
		return myManager.getCurrentUserReservations();
	}
}
