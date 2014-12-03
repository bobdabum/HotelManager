import java.util.ArrayList;


public class ReceiptStrategyComprehensive implements ReceiptStrategy {

	public ArrayList<Reservation> getList(RoomAndUserManager myManager) {
		return myManager.getCurrentUserReservations();
	}
}
