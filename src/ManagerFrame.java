import java.awt.*;
import java.awt.event.*;
import java.util.*;

import javax.swing.*;
import javax.swing.event.*;
import javax.swing.table.*;

import java.text.*;

/**
 * Manages everything for Manager view.
 * @author Ji Hoon Yoon
 *
 */
public class ManagerFrame{
	/**
	 * creates the Frame with the calendar and Manager options
	 * @param m the RoomAndUserManager
	 * @param frame the JFrame being used
	 */
	@SuppressWarnings("serial")
	public ManagerFrame(final RoomAndUserManager m, final JFrame frame) {
		/*try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
			e.printStackTrace();
		}*/
		scroller = new JScrollPane();
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
					frame.getContentPane().remove(scroller);
					frame.repaint();
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
		save();
		quit();
	}

	/**
	 * Updates the Calendar to another month or year
	 * every time the calendar is changed (goes to another month/year), updateCalendar is called
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
	 * Displays all the rooms available and also which rooms are taken
	 */
	public void displayFrame() {
		scroller = new JScrollPane();
		scroller.setBounds(330, 4, 287, 253);
		frame.add(scroller);

		JPanel borderPanel = new JPanel();

		scroller.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		scroller.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		scroller.setViewportView(borderPanel);
		scroller.setBorder(BorderFactory.createTitledBorder("Reservations"));
		borderPanel.setLayout(new GridLayout(0, 1, 0, 1));

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
		
		GregorianCalendar tempCal = new GregorianCalendar(year, month-1, day);

		rm.updateCalendarDay(tempCal);
		ArrayList<Reservation> rl = rm.getReservationList();
		
		for(int i = 0; i < 20; i++){
			JTextArea row = new JTextArea();
			row.setBackground(new Color(188, 255, 199));
			row.setPreferredSize(new Dimension(300, 50));
			borderPanel.add(row);
			row.setLayout(null);
			row.setEditable(false);
			row.setFont(new Font("Arial", Font.BOLD, 12));
			
			textFill = " Room: " + (i+1) + "          Type: $" + (i<10?RoomCost.Economical.getCost():RoomCost.Luxury.getCost());
			for(Reservation r:rl)
			if(r.getRoomID() == i){
				row.setBackground(new Color(255, 205, 205));
				textFill = " Room: " + (r.getRoomID()+1) + "          Type: " + r.getRoomCost()
						+ "\n User: " + r.getUserName() + "           UserID: " + r.getUserID()
						+ "\n Price:  $" + r.getRoomCost().getCost();
			}

			row.setText(textFill);
		}

		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			   public void run() { 
			       scroller.getVerticalScrollBar().setValue(0);
			   }
		});
	}
	
	/**
	 * Colors the cells and sets the default selected cell
	 * Also colors in the current date and the selected date
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
	
	/**
	 * Saves reservations and user information to a txt file
	 */
	public void save(){
		JButton saveButton = new JButton("Save");
		saveButton.setBounds(334, 267, 137, 45);
		saveButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				rm.saveAll();
				JOptionPane.showMessageDialog(frame, "Saved!");
			}
		});
		frame.add(saveButton);
	}
	
	/**
	 * Quits the hotel reservation program
	 */
	public void quit(){
		JButton quitButton = new JButton("Quit");
		quitButton.setBounds(475, 267, 137, 45);
		quitButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
			}
		});
		frame.add(quitButton);
	}

	JScrollPane scroller;
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