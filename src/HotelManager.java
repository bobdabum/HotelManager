import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;


public class HotelManager {
	public static void main(String[] args) throws Exception{
		ArrayList<Room> m = new ArrayList<Room>();
		m.add(new Room(0, 0));
		m.add(new Room(1, 0));
		m.add(new Room(2, 1));
		m.add(new Room(3, 1));
		
		RoomManager room = new RoomManager(m);
		GregorianCalendar start = new GregorianCalendar();
		start.set(Calendar.YEAR, 2014);
		start.set(Calendar.MONTH, 12 - 1);
		start.set(Calendar.DAY_OF_MONTH, 20);
		GregorianCalendar end = new GregorianCalendar();
		end.set(Calendar.YEAR, 2014);
		end.set(Calendar.MONTH, 12 - 1);
		end.set(Calendar.DAY_OF_MONTH, 30);
		
		room.addReservation(new Reservation(start, end, 3, 9210));
		room.addReservation(new Reservation(start, end, 2, 2389));
		
		HotelFrame hotel = new HotelFrame(room);
		hotel.run();
	}
}
