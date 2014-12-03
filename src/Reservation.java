import java.util.*;
public class Reservation implements Comparable<Reservation>{
	private GregorianCalendar start, end;
	private int roomID;
	private User user;
	
<<<<<<< HEAD
	public Reservation(GregorianCalendar start, GregorianCalendar end, int roomID, User user)
			throws Exception{
=======
	public Reservation(GregorianCalendar start, GregorianCalendar end, int roomID, int userID) throws Exception{
>>>>>>> origin/JiHoon
		GregorianCalendar now = new GregorianCalendar();
		
		if(start.after(end)) throw new Exception("Start date cannot be after end date.");
		if(now.after(start)) throw new Exception("Start date cannot be before today.");
		
		this.setStart(start);
		this.setEnd(end);
		this.setRoomID(roomID);
		this.setUser(user);
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

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public int compareTo(Reservation r) {
		if(start.after(r.getStart()))
			return 1;
		else if(start.before(r.getStart()))
			return -1;
		else
			return 0;
	}
}
