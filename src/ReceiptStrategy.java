import java.util.ArrayList;

public interface ReceiptStrategy {
	public ArrayList<Reservation> getList(RoomAndUserManager myManager);
}
