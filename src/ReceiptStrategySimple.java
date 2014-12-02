import java.util.ArrayList;


public class ReceiptStrategySimple implements ReceiptStrategy {

	public ArrayList<Reservation> getList(RoomAndUserManager myManager) {
		return myManager.getReceiptList();
	}

}
