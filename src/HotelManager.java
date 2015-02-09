import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.*;
import java.util.ArrayList;
import java.util.GregorianCalendar;

import javax.swing.JFrame;

/**
 * Contains main method.
 * @author Ji Hoon Yoon
 *
 */
public class HotelManager {
	/**
	 * Runs the program.
	 * @param args
	 */
	public static void main(String[] args) {
		final RoomAndUserManager myManager = loadFromFile();
		final JFrame frame = new JFrame("Hotel Manager");
		frame.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e){
				myManager.saveAll();
			}
		});
		HotelFrame hf = new HotelFrame(myManager, frame);
		hf.run();
	}
	/**
	 * Tries to load reservation/ user information from file.
	 * @return RoomAndUserManager model.
	 */
	private static RoomAndUserManager loadFromFile(){
		BufferedReader in;
		RoomAndUserManager myManager = null;
		
		ArrayList<Room> roomList = new ArrayList<Room>();
		for(int i=0;i<10;i++)
			roomList.add(new Room(i, RoomCost.Economical));
		for(int i=10;i<=19;i++)
			roomList.add(new Room(i, RoomCost.Luxury));
		
		try {
			//read all users
			in = new BufferedReader(new FileReader("userList.txt"));
			String line = in.readLine();
			int curResID = Integer.parseInt(line);
			myManager = new RoomAndUserManager(roomList, curResID);
			
			line = in.readLine();
			while(line!=null){
				myManager.createUser(line);
				line = in.readLine();
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
				String[] startArray = strArray[3].split("/");
				String[] endArray = strArray[4].split("/");
				GregorianCalendar start = new GregorianCalendar(Integer.parseInt(startArray[2]),Integer.parseInt(startArray[0])-1,Integer.parseInt(startArray[1]));
				GregorianCalendar end = new GregorianCalendar(Integer.parseInt(endArray[2]),Integer.parseInt(endArray[0])-1,Integer.parseInt(endArray[1]));
				
				myManager.addReservationFromFile(start, end, roomID, userID, resID);
				line = in.readLine();
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