import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.GregorianCalendar;


public class HotelManager {
	/**
	 * Runs the program.
	 * @param args
	 */
	public static void main(String[] args) {
		final RoomAndUserManager myManager = loadFromFile();
		
	}
	private static RoomAndUserManager loadFromFile(){
		BufferedReader in;
		RoomAndUserManager myManager = null;
		
		ArrayList<Room> roomList = new ArrayList<Room>();
		for(int i=0;i<10;i++)
			roomList.add(new Room(i, RoomCost.Economical));
		for(int i=10;i<19;i++)
			roomList.add(new Room(i, RoomCost.Luxury));
		
		try {
			//read all users
			in = new BufferedReader(new FileReader("userInfo.txt"));
			String line = in.readLine();
			int curResID = Integer.parseInt(line);
			myManager = new RoomAndUserManager(roomList, curResID);
			
			line = in.readLine();
			while(line!=null){
				myManager.createUser(line);
			}
			in.close();
			
			//read all reservations
			//Lines are formatted as: roomID,userID,resID,start,end
			in = new BufferedReader(new FileReader("reservationList.txt"));			
			line = in.readLine();
			while(line!=null){
				String[] strArray = line.split(",");
				int roomID = Integer.parseInt(strArray[0]);
				int userID = Integer.parseInt(strArray[1]);
				int resID = Integer.parseInt(strArray[2]);
				String[] startArray = strArray[4].split("/");
				String[] endArray = strArray[5].split("/");
				GregorianCalendar start = new GregorianCalendar(Integer.parseInt(startArray[0]),Integer.parseInt(startArray[1]),Integer.parseInt(startArray[2]));
				GregorianCalendar end = new GregorianCalendar(Integer.parseInt(endArray[0]),Integer.parseInt(endArray[1]),Integer.parseInt(endArray[2]));
				
				myManager.addReservationFromFile(start, end, roomID, userID, resID);
			}
			in.close();
		}
		catch (FileNotFoundException e) {
			myManager = new RoomAndUserManager(roomList,0);
		}
		catch(IOException e){
			
		}
		return myManager;
	}
}