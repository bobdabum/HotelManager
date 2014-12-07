import java.util.ArrayList;

/**
 * Implements comprehensive receipt for strategy.
 * @author My Pc
 *
 */
public class ReceiptStrategyComprehensive implements ReceiptStrategy {

	public ArrayList<Reservation> getList(RoomAndUserManager myManager) {
		return myManager.getCurrentUserReservations();
	}
}
