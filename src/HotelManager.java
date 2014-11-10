import java.util.ArrayList;


public class HotelManager {
	public static void main(String[] args){
		ArrayList<Room> m = new ArrayList<Room>();
		m.add(new Room(0, 0));
		m.add(new Room(1, 0));
		m.add(new Room(2, 1));
		m.add(new Room(3, 1));
		
		RoomManager room = new RoomManager(m);
		ManagerFrame f = new ManagerFrame(room);
		f.textFrame();
	//	room.attach(f);
	}
}
