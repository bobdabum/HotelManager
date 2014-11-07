import java.util.*;
public class Reservation implements Comparable<Reservation>{
	private GregorianCalendar start, end;
	private int roomID, userID;
	
	public Reservation(GregorianCalendar start, GregorianCalendar end, int roomID, int userID)
			throws Exception{
		GregorianCalendar now = new GregorianCalendar();
		
		if(start.after(end))
			throw new Exception("Start date cannot be after end date.");
		if(now.after(start))
			throw new Exception("Start date cannot be before today.");
		
		this.setStart(start);
		this.setEnd(end);
		this.setRoomID(roomID);
		this.setUserID(userID);
	}

	public GregorianCalendar getStart() {
		return start;
	}

	public void setStart(GregorianCalendar start) {
		this.start = start;
	}

	public GregorianCalendar getEnd() {
		return end;
	}

	public void setEnd(GregorianCalendar end) {
		this.end = end;
	}

	public int getRoomID() {
		return roomID;
	}

	public void setRoomID(int roomID) {
		this.roomID = roomID;
	}

	public int getUserID() {
		return userID;
	}

	public void setUserID(int userID) {
		this.userID = userID;
	}

	@Override
	public int compareTo(Reservation r) {
		if(start.after(r.getStart()))
			return 1;
		else if(start.before(r.getStart()))
			return -1;
		else
			return 0;
	}
}
