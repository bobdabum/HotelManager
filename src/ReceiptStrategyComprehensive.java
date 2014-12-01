import java.util.ArrayList;


public class ReceiptStrategyComprehensive implements ReceiptStrategy {

	@Override
	public ArrayList<Reservation> getList(RoomAndUserManager myManager) {
		return myManager.getCurrentUserReservations();
	}
}
