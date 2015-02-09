import java.util.ArrayList;

/**
 * Implements comprehensive receipt for strategy.
 * @author Linye Ouyang
 *
 */
public class ReceiptStrategyComprehensive implements ReceiptStrategy {

	public ArrayList<Reservation> getList(RoomAndUserManager myManager) {
		return myManager.getCurrentUserReservations();
	}
}
