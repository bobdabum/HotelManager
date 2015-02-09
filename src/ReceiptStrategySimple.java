import java.util.ArrayList;

/**
 * Implements simple Receipt for the strategy.
 * @author Linye Ouyang
 *
 */
public class ReceiptStrategySimple implements ReceiptStrategy {

	public ArrayList<Reservation> getList(RoomAndUserManager myManager) {
		return myManager.getReceiptList();
	}

}
