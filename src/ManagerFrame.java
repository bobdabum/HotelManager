import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.GregorianCalendar;

import javax.swing.BorderFactory;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import java.text.DateFormatSymbols;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import javax.swing.*;

public class ManagerFrame{
	/**
	 * creates the Frame with the calendar and Manager options
	 * @param m
	 */
	@SuppressWarnings("serial")
	public ManagerFrame(final RoomAndUserManager m, JFrame frame) {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
			e.printStackTrace();
		}
		this.frame = frame;
		frame.setName("Calendar");
		frame.setBounds(20, 20, 630, 350);
		Container pane = frame.getContentPane();
		pane.setLayout(null);
		frame.setResizable(false);
		frame.setVisible(true);

		rooms = m.getAvailableRooms();
		reservations = m.getReservationList();
		rm = m;

		CalendarTabler = new DefaultTableModel() {
			public boolean isCellEditable(int rowIndex, int mColIndex) {
				return false;
			}
		};
		CalendarTable = new JTable(CalendarTabler);
		CalendarPanel = new JPanel(null);
		monthDropDown = new JComboBox<String>(monthList);
		Date date = new Date();
		SpinnerDateModel sm = new SpinnerDateModel(date, null, null, Calendar.YEAR);
		yearDropDown = new JSpinner(sm);
		JSpinner.DateEditor de = new JSpinner.DateEditor(yearDropDown, "yyyy");
		de.getTextField().setEditable(false);
		yearDropDown.setEditor(de);
		
		//disable the keyboard
		InputMap input = CalendarTable.getInputMap(JTable.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
		input.put(KeyStroke.getKeyStroke("DOWN"), "none");
		input.put(KeyStroke.getKeyStroke("UP"), "none");
		input.put(KeyStroke.getKeyStroke("LEFT"), "none");
		input.put(KeyStroke.getKeyStroke("RIGHT"), "none");

		ActionListener listen = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				firstDayCalendar = new GregorianCalendar(year, month - 1, 1);
				daysInMonth = firstDayCalendar.getActualMaximum(GregorianCalendar.DAY_OF_MONTH);
				dayOfWeek = firstDayCalendar.get(GregorianCalendar.DAY_OF_WEEK);
				firstDayCalendar.add(Calendar.MONTH, -1);
				daysInPrevMonth = firstDayCalendar.getActualMaximum(GregorianCalendar.DAY_OF_MONTH);

				String selectedMonth = (String) monthDropDown.getSelectedItem();
				Calendar cal = Calendar.getInstance();
				try {
					cal.setTime(new SimpleDateFormat("MMM").parse(selectedMonth));
				} catch (ParseException a) {
					a.printStackTrace();
				}
				month = cal.get(Calendar.MONTH) + 1;
				updateCalendar();
			}
		};

		ChangeListener listen2 = new ChangeListener() {
			public void stateChanged(ChangeEvent arg0) {
				firstDayCalendar = new GregorianCalendar(year, month - 1, 1);
				daysInMonth = firstDayCalendar.getActualMaximum(GregorianCalendar.DAY_OF_MONTH);
				dayOfWeek = firstDayCalendar.get(GregorianCalendar.DAY_OF_WEEK);
				firstDayCalendar.add(Calendar.MONTH, -1);
				daysInPrevMonth = firstDayCalendar.getActualMaximum(GregorianCalendar.DAY_OF_MONTH);
				
				Date date = (Date) yearDropDown.getValue();
				SimpleDateFormat form = new SimpleDateFormat("yyyy");
				String yearTemp = form.format(date);
				year = Integer.parseInt(yearTemp);
				updateCalendar();
			}
		};

		ListSelectionListener ls = new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent e) {
				if (e.getValueIsAdjusting() == false && changedSelection == false) {
					firstDayCalendar = new GregorianCalendar(year, month - 1, 1);
					daysInMonth = firstDayCalendar.getActualMaximum(GregorianCalendar.DAY_OF_MONTH);
					dayOfWeek = firstDayCalendar.get(GregorianCalendar.DAY_OF_WEEK);
					firstDayCalendar.add(Calendar.MONTH, -1);
					daysInPrevMonth = firstDayCalendar.getActualMaximum(GregorianCalendar.DAY_OF_MONTH);
					updateCalendar();
					displayFrame();
				}
			}
		};
		
		yearDropDown.addChangeListener(listen2);
		monthDropDown.addActionListener(listen);

		pane.add(CalendarPanel);
		CalendarPanel.add(yearDropDown);
		CalendarPanel.add(monthDropDown);

		Container table = new Container();
		table.setLayout(new BorderLayout());
		table.add(CalendarTable.getTableHeader(), BorderLayout.PAGE_START);
		table.add(CalendarTable, BorderLayout.CENTER);
		CalendarPanel.add(table);

		CalendarTable.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		CalendarTable.getTableHeader().setBorder(BorderFactory.createLineBorder(Color.BLACK));
		CalendarPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		CalendarPanel.setBounds(3, 4, 320, 312);
		monthDropDown.setBounds(20, 13, 100, 25);
		yearDropDown.setBounds(200, 13, 100, 25);
		table.setBounds(12, 50, 295, 253);

		GregorianCalendar tempCal = new GregorianCalendar();
		day = tempCal.get(GregorianCalendar.DAY_OF_MONTH);
		month = tempCal.get(GregorianCalendar.MONTH);
		year = tempCal.get(GregorianCalendar.YEAR);

		String[] headers = { "S", "M", "T", "W", "T", "F", "S" };
		for (int i = 0; i < 7; i++) {
			CalendarTabler.addColumn(headers[i]);
		}

		CalendarTable.setColumnSelectionAllowed(true);
		CalendarTable.setRowSelectionAllowed(true);
		CalendarTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		CalendarTable.setRowHeight(38);
		CalendarTabler.setColumnCount(7);
		CalendarTabler.setRowCount(6);

		// fill the grid
		GregorianCalendar firstDayCalendar = new GregorianCalendar(year, month, 1);
		int daysInMonth = firstDayCalendar.getActualMaximum(GregorianCalendar.DAY_OF_MONTH);
		int dayOfWeek = firstDayCalendar.get(GregorianCalendar.DAY_OF_WEEK);
		firstDayCalendar.add(Calendar.MONTH, -1);
		int daysInPrevMonth = firstDayCalendar.getActualMaximum(GregorianCalendar.DAY_OF_MONTH);
		DateFormatSymbols s = new DateFormatSymbols();
		monthDropDown.setSelectedItem(s.getMonths()[month]);

		int prevMonth = daysInPrevMonth - (1 + dayOfWeek - 2) % 7 + 1;
		for (int i = 0; i < (1 + dayOfWeek - 2) % 7; i++, prevMonth++) {
			CalendarTabler.setValueAt(" " + prevMonth, 0, i);
		}
		int tempI = (daysInMonth + dayOfWeek - 2) / 7;
		int nextMonth = 1;
		if (tempI == 4) {
			for (int i = tempI; i < 6; i++) {
				if (i == 4) {
					int temp1 = (daysInMonth + dayOfWeek - 2) % 7 + 1;
					for (int j = temp1; j < 7; j++, nextMonth++) {
						CalendarTabler.setValueAt(" " + nextMonth, i, j);
					}
				} else {
					for (int j = 0; j < 7; j++, nextMonth++) {
						CalendarTabler.setValueAt(" " + nextMonth, i, j);
					}
				}
			}
		} else {
			for (int i = (daysInMonth + dayOfWeek - 2) % 7 + 1; i < 7; i++, nextMonth++) {
				CalendarTabler.setValueAt(" " + nextMonth, 5, i);
			}
		}
		Calendar todayCal = new GregorianCalendar();
		int todayDay = todayCal.get(Calendar.DAY_OF_MONTH);

		for (int i = 1; i <= daysInMonth; i++) {
			int row = (i + dayOfWeek - 2) / 7;
			int column = (i + dayOfWeek - 2) % 7;
			if (i == todayDay) CalendarTable.changeSelection(row, column, false, false);
			CalendarTabler.setValueAt(" " + i, row, column);
		}

		// attach the listeners to grid
		CalendarTable.getSelectionModel().addListSelectionListener(ls);
		CalendarTable.getColumnModel().getSelectionModel().addListSelectionListener(ls);
	}

	/**
	 * Updates the Calendar to another month or year
	 */
	public void updateCalendar() {
		int x = CalendarTable.getSelectedRow();
		int y = CalendarTable.getSelectedColumn();
		boolean changeSelectionPrev = false;
		boolean changeSelectionNext = false;
		int tempInt = 0;

		// Calendar changes when you select a date from the prev/next month
		// when you select a day from the previous month
		if (x >= 0 && y >= 0 && (x == 0 && (y < (1 + dayOfWeek - 2) % 7))) {
			String temp = (String) CalendarTable.getValueAt(x, y);
			tempInt = Integer.parseInt(temp.substring(1));
			month--;
			if (month == 0) {
				month = 12;
				year--;
			}
			changeSelectionPrev = true;
		}

		// when you select a day from the next month
		else if (x >= 0 && y >= 0 && ((x == (daysInMonth + dayOfWeek - 2) / 7 && 
				(y >= (daysInMonth + dayOfWeek - 2) % 7 + 1)) || (x > (daysInMonth + dayOfWeek - 2) / 7))) {
			String temp = (String) CalendarTable.getValueAt(x, y);
			tempInt = Integer.parseInt(temp.substring(1));
			month++;
			if (month == 13) {
				month = 1;
				year++;
			}
			changeSelectionNext = true;
		}

		firstDayCalendar = new GregorianCalendar(year, month - 1, 1);
		daysInMonth = firstDayCalendar.getActualMaximum(GregorianCalendar.DAY_OF_MONTH);
		dayOfWeek = firstDayCalendar.get(GregorianCalendar.DAY_OF_WEEK);
		firstDayCalendar.add(Calendar.MONTH, -1);
		daysInPrevMonth = firstDayCalendar.getActualMaximum(GregorianCalendar.DAY_OF_MONTH);

		// fill the Calendar
		// previous calendar peek
		int prevMonth = daysInPrevMonth - (1 + dayOfWeek - 2) % 7 + 1;
		for (int i = 0; i < (1 + dayOfWeek - 2) % 7; i++, prevMonth++) {
			CalendarTabler.setValueAt(" " + prevMonth, 0, i);
		}
		// next calendar peek
		int nextMonth = 1;
		if ((daysInMonth + dayOfWeek - 2) / 7 == 4) {
			for (int i = (daysInMonth + dayOfWeek - 2) / 7; i < 6; i++) {
				if (i == 4) 
					for (int j = (daysInMonth + dayOfWeek - 2) % 7 + 1; j < 7; j++, nextMonth++)
						CalendarTabler.setValueAt(" " + nextMonth, i, j);
				if (i == 5) 
					for (int j = 0; j < 7; j++, nextMonth++)
						CalendarTabler.setValueAt(" " + nextMonth, i, j);
			}
		} 
		//february 2015
		else if(daysInMonth == 28 && (daysInMonth+dayOfWeek-2)/7==3){
			for(int i = 4; i < 6; i++){
				for(int j = 0; j < 7; j++,nextMonth++)
					CalendarTabler.setValueAt(" " + nextMonth, i, j);
			}
		}
		else {
			for (int j = (daysInMonth + dayOfWeek - 2) % 7 + 1; j < 7; j++, nextMonth++)
				CalendarTabler.setValueAt(" " + nextMonth, 5, j);
		}

		// main calendar values
		for (int i = 1; i <= daysInMonth; i++) {
			int row = (i + dayOfWeek - 2) / 7;
			int column = (i + dayOfWeek - 2) % 7;
			CalendarTabler.setValueAt(" " + i, row, column);
		}

		// changeSelection AFTER you have changed the calendar to match the new
		// month
		// prev month
		if (changeSelectionPrev) {
			for (int i = 4; i < 6; i++) {
				for (int j = 0; j < 7; j++) {
					String prevEquiv = (String) CalendarTable.getValueAt(i, j);
					int prevEquivInt = Integer.parseInt(prevEquiv.substring(1));
					if (prevEquivInt == tempInt) {
						changedSelection = true;
						CalendarTable.changeSelection(i, j, false, false);
						break;
					}
				}
			}
		}
		// next month
		if (changeSelectionNext) {
			for (int i = 0; i < 2; i++) {
				for (int j = 0; j < 7; j++) {
					String afterEquiv = (String) CalendarTable.getValueAt(i, j);
					int afterEquivInt = Integer.parseInt(afterEquiv.substring(1));
					if (afterEquivInt == tempInt) {
						changedSelection = true;
						CalendarTable.changeSelection(i, j, false, false);
						break;
					}
				}
			}
		}
		monthDropDown.setSelectedItem(monthList[month - 1]);
		
		Calendar yearCal = new GregorianCalendar();
		yearCal.set(Calendar.YEAR, year);
		Date funk = yearCal.getTime();
		yearDropDown.setValue(funk);
		
		changedSelection = false;
		CalendarTable.setDefaultRenderer(CalendarTable.getColumnClass(0), new DecorateTable());
	}

	/**
	 * The display frame, where the reservations are shown.
	 */
	public void displayFrame() {
		frame.getContentPane().setLayout(null);
		JScrollPane scroller = new JScrollPane();
		scroller.setBounds(330, 4, 285, 314);
		frame.getContentPane().add(scroller);

		JPanel borderPanel = new JPanel();
		JPanel borderPanel2 = new JPanel();

		scroller.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		scroller.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		scroller.setViewportView(borderPanel);
		borderPanel.setLayout(new BorderLayout(0, 0));
		scroller.setBorder(BorderFactory.createTitledBorder("Reservations"));
		borderPanel.add(borderPanel2, BorderLayout.NORTH);
		borderPanel2.setLayout(new GridLayout(0, 1, 0, 1));

		GregorianCalendar firstDayCalendar = new GregorianCalendar(year, month - 1, 1);
		int daysInMonth = firstDayCalendar.getActualMaximum(GregorianCalendar.DAY_OF_MONTH);
		int dayOfWeek = firstDayCalendar.get(GregorianCalendar.DAY_OF_WEEK);
		firstDayCalendar.add(Calendar.MONTH, -1);

		//get info from the selected cell
		int x = CalendarTable.getSelectedRow();
		int y = CalendarTable.getSelectedColumn();
		String selected;
		if (x >= 0 && y >= 0 && !(x == 0 && (y <= (1 + dayOfWeek - 2) % 7))
				&& !(x == 5 && (y >= (daysInMonth + dayOfWeek - 2) % 7 + 1))) 
					selected = (String) CalendarTable.getValueAt(x, y);
		else selected = "";
		int day;
		if (selected != "") day = Integer.parseInt(selected.substring(1));
		else day = 0;

		GregorianCalendar tempCal = new GregorianCalendar();
		tempCal.set(Calendar.YEAR, year);
		tempCal.set(Calendar.MONTH, month - 1);
		tempCal.set(Calendar.DAY_OF_MONTH, day);

		//make a copy of the reservations that fit the description
		ArrayList<Reservation> tempRes = new ArrayList<Reservation>();
		for (int i = 0; i < reservations.size(); i++) {
			GregorianCalendar start = reservations.get(i).getStart();
			GregorianCalendar end = reservations.get(i).getEnd();
			if (start.before(tempCal)
					|| (start.get(Calendar.YEAR) == tempCal.get(Calendar.YEAR) && start.get(Calendar.DAY_OF_YEAR) == tempCal
							.get(Calendar.DAY_OF_YEAR))) {
				if (end.after(tempCal)
						|| (end.get(Calendar.YEAR) == tempCal.get(Calendar.YEAR) && end.get(Calendar.DAY_OF_YEAR) == tempCal
								.get(Calendar.DAY_OF_YEAR))) tempRes.add(reservations.get(i));
			}
		}
		
		//sort the reservations
		Collections.sort(tempRes, ReservationComparator());

		for(int i = 0; i < tempRes.size(); i++){
			JTextArea rows = new JTextArea();
			rows.setBackground(Color.LIGHT_GRAY);
			rows.setPreferredSize(new Dimension(300, 50));
			borderPanel2.add(rows);
			rows.setLayout(null);
			rows.setEditable(false);
			Font font = new Font("Arial", Font.BOLD, 12);
			rows.setFont(font);

			textFill = " Room: " + tempRes.get(i).getRoomID() + "  \n User: " + tempRes.get(i).getUserID()
					+ "  \n Price: " + rooms.get(tempRes.get(i).getRoomID()).getPrice();

			rows.setText(textFill);

			if (i % 2 == 1) rows.setBackground(SystemColor.inactiveCaptionBorder);
		}
	}

	/**
	 * the comparator to compare reservations
	 * @return
	 */
	public static Comparator<Reservation> ReservationComparator() {
		return new Comparator<Reservation>() {
			public int compare(Reservation a, Reservation b) {
				if (a.getRoomID() < b.getRoomID()) return -1;
				else return 1;
			}
		};
	}
	
	/**
	 * Colors the cells and sets the default selected cell (today)
	 */
	@SuppressWarnings("serial")
	class DecorateTable extends DefaultTableCellRenderer {
		public Component getTableCellRendererComponent(JTable t, Object o, boolean selected, boolean focus, int r, int c) {
			setBorder(null);
			setForeground(Color.black);
			super.getTableCellRendererComponent(t, o, selected, focus, r, c);
			if (c == 0 || c == 6) setBackground(new Color(230, 230, 255));
			else setBackground(new Color(255, 255, 255));

			GregorianCalendar today = new GregorianCalendar();
			String hex = (String) o;
			
			Font font = new Font("Arial", Font.BOLD, 12);
			setFont(font);
			Font font2 = new Font("Arial", Font.PLAIN, 12);
			
			if (r >= 0 && c >= 0 && (r == 0 && (c < (1 + dayOfWeek - 2) % 7))) {
				setFont(font2);
				super.setForeground(Color.GRAY);
			}

			else if (r >= 0 && c >= 0 && ((r == (daysInMonth + dayOfWeek - 2) / 7 && 
					(c >= (daysInMonth + dayOfWeek - 2) % 7 + 1)) || (r > (daysInMonth + dayOfWeek - 2) / 7))) {
				setFont(font2);
				super.setForeground(Color.GRAY);
			}
			
			if (selected) {
				setForeground(CalendarTable.getSelectionForeground());
				super.setBackground(Color.LIGHT_GRAY);
			}
			if (hex != "" && hex != null) {
				if (Integer.parseInt(hex.substring(1)) == today.get(Calendar.DAY_OF_MONTH)
						&& Integer.parseInt(hex.substring(1)) > 20
						&& r > 1
						&& year == today.get(Calendar.YEAR) && month == today.get(Calendar.MONTH) + 1) {
							setBackground(new Color(150, 200, 200));
				}
				else if (Integer.parseInt(hex.substring(1)) == today.get(Calendar.DAY_OF_MONTH)
						&& Integer.parseInt(hex.substring(1)) < 15
						&& r < 4
						&& year == today.get(Calendar.YEAR) && month == today.get(Calendar.MONTH) + 1) {
							setBackground(new Color(150, 200, 200));
				}
			}

			return this;
		}
	}

	JFrame frame;
	String[] monthList = { "January", "February", "March", "April", "May", "June", "July", "August", "September",
			"October", "November", "December" };
	JPanel CalendarPanel, panel;
	JTable CalendarTable;
	DefaultTableModel CalendarTabler;
	JSpinner yearDropDown;
	JComboBox<String> monthDropDown;
	JTextArea text;
	int day, month, year, currMonth, currYear;
	ArrayList<Room> rooms;
	ArrayList<Reservation> reservations;
	RoomAndUserManager rm;
	String textFill;
	GregorianCalendar firstDayCalendar;
	int daysInMonth, dayOfWeek, daysInPrevMonth;
	boolean changedSelection = false;
}