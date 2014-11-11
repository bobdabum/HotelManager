import java.awt.BorderLayout;
import java.awt.Color;
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
import javax.swing.table.DefaultTableModel;

import java.text.DateFormatSymbols;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import javax.swing.*;


public class ManagerFrame{
	JFrame frame;
	JPanel CalendarPanel, panel;
	JTable CalendarTable;
	DefaultTableModel CalendarTabler;  
	JSpinner  yearDropDown;
	JComboBox<String> monthDropDown;
	JTextArea text;
	int day, month, year, currMonth, currYear;
	ArrayList<Room> rooms;
	ArrayList<Reservation> reservations;
	RoomManager rm;
	String textFill;
	
	/**
	 * creates the Frame with the calendar and Manager options
	 * @param m
	 */
	@SuppressWarnings("serial")
	public ManagerFrame(final RoomManager m){	
		frame = new JFrame("Calendar");	
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setBounds(20, 20, 630, 355);
		Container pane = frame.getContentPane();
		pane.setLayout(null);
		frame.setResizable(false);
		frame.setVisible(true);
		
		rooms = m.getRoomList();
		reservations = m.getReservations();
		rm = m;
		
		String[] monthList = {"January", "February", "March", "April", "May", "June", "July", "August", 
				"September", "October", "November", "December"};
		CalendarTabler = new DefaultTableModel(){public boolean isCellEditable(int rowIndex, int mColIndex){return false;}};
		CalendarTable = new JTable(CalendarTabler);
		CalendarPanel = new JPanel(null);
		monthDropDown = new JComboBox<String>(monthList);
		Date date = new Date();
		SpinnerDateModel sm = new SpinnerDateModel(date, null, null, Calendar.YEAR);
		yearDropDown = new JSpinner(sm);
		JSpinner.DateEditor de = new JSpinner.DateEditor(yearDropDown, "yyyy");
		de.getTextField().setEditable( false );
		yearDropDown.setEditor(de);
		
		ActionListener listen = new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				updateCalendar();
			}
		};
		
		ChangeListener listen2 = new ChangeListener(){
			public void stateChanged(ChangeEvent arg0) {
				updateCalendar();
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
		CalendarPanel.setBounds(3, 4, 320, 318);
		monthDropDown.setBounds(20, 25, 100, 25);
		yearDropDown.setBounds(200, 25, 100, 25);
		table.setBounds(12, 62, 295, 245);
		
		GregorianCalendar tempCal = new GregorianCalendar();
		day = tempCal.get(GregorianCalendar.DAY_OF_MONTH);
		month = tempCal.get(GregorianCalendar.MONTH);
		year = tempCal.get(GregorianCalendar.YEAR);
		
		String[] headers = {"S", "M", "T", "W", "T", "F", "S"};
		for (int i=0; i<7; i++){
			CalendarTabler.addColumn(headers[i]);
		}
		
		CalendarTable.setColumnSelectionAllowed(true);
		CalendarTable.setRowSelectionAllowed(true);
		CalendarTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		CalendarTable.setRowHeight(38);
		CalendarTabler.setColumnCount(7);
		CalendarTabler.setRowCount(6);
		
		//fill the grid
		GregorianCalendar firstDayCalendar = new GregorianCalendar(year, month, 1);
		int daysInMonth = firstDayCalendar.getActualMaximum(GregorianCalendar.DAY_OF_MONTH);
		int dayOfWeek = firstDayCalendar.get(GregorianCalendar.DAY_OF_WEEK);
		DateFormatSymbols s = new DateFormatSymbols();
		monthDropDown.setSelectedItem(s.getMonths()[month]); 
		for(int i = 0; i < 6; i ++){
			for(int j = 0; j < 7; j++){
				CalendarTabler.setValueAt("", i, j);
			}
		}
		for (int i=1; i<=daysInMonth; i++){
			int row = (i + dayOfWeek-2) / 7;
			int column = (i + dayOfWeek - 2) % 7;
			CalendarTabler.setValueAt(" " + i, row, column);
		}

		ListSelectionListener ls = new ListSelectionListener(){
			public void valueChanged(ListSelectionEvent e) {
				if(e.getValueIsAdjusting() == false){
					displayFrame();
				}
			}
		};
		CalendarTable.getSelectionModel().addListSelectionListener(ls);
		CalendarTable.getColumnModel().getSelectionModel() .addListSelectionListener(ls);
		
		SwingUtilities.invokeLater(new Runnable(){public void run(){
		    CalendarTable.repaint();
		}});
	}
	
	/**
	 * update calendar
	 */
	public void updateCalendar(){
		String selectedMonth = (String) monthDropDown.getSelectedItem();
		Calendar cal = Calendar.getInstance();
		try {
			cal.setTime(new SimpleDateFormat("MMM").parse(selectedMonth));
		} catch (ParseException a) {
			a.printStackTrace();
		}
		month = cal.get(Calendar.MONTH) + 1;
		Date date = (Date) yearDropDown.getValue();
		SimpleDateFormat form = new SimpleDateFormat("yyyy");
		String yearTemp = form.format(date);
		year = Integer.parseInt(yearTemp);
		GregorianCalendar firstDayCalendar = new GregorianCalendar(year, month - 1, 1);
		int daysInMonth = firstDayCalendar.getActualMaximum(GregorianCalendar.DAY_OF_MONTH);
		int dayOfWeek = firstDayCalendar.get(GregorianCalendar.DAY_OF_WEEK);
		for(int i = 0; i < 6; i ++){
			for(int j = 0; j < 7; j++){
				CalendarTabler.setValueAt("", i, j);
			}
		}
		for (int i=1; i<=daysInMonth; i++){
			int row = (i + dayOfWeek-2) / 7;
			int column = (i + dayOfWeek - 2) % 7;
			CalendarTabler.setValueAt(" " + i, row, column);
		}
	}
	
	/**
	 * the other frame
	 */
	public void displayFrame(){
        frame.getContentPane().setLayout(null);
		JScrollPane scroller = new JScrollPane();
        scroller.setBounds(330, 4, 285, 317);
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
        
        int x = CalendarTable.getSelectedRow();
		int y = CalendarTable.getSelectedColumn();
		String selected;
		if(x >= 0 && y >= 0) selected = (String) CalendarTable.getValueAt(x, y);
		else selected = "";
		int day;
		if(selected != "") day = Integer.parseInt(selected.substring(1));
		else day = 0;
        
        GregorianCalendar tempCal = new GregorianCalendar();
		tempCal.set(Calendar.YEAR, year);
		tempCal.set(Calendar.MONTH, month - 1);
		tempCal.set(Calendar.DAY_OF_MONTH, day);
		
		ArrayList<Reservation> tempRes = new ArrayList<Reservation>();
		for(int i = 0; i < reservations.size(); i++){
			GregorianCalendar start = reservations.get(i).getStart();
			GregorianCalendar end = reservations.get(i).getEnd();
			if(start.before(tempCal) || (start.get(Calendar.YEAR) == tempCal.get(Calendar.YEAR) && 
					start.get(Calendar.DAY_OF_YEAR) == tempCal.get(Calendar.DAY_OF_YEAR))){ 
				if(end.after(tempCal) || (end.get(Calendar.YEAR) == tempCal.get(Calendar.YEAR) && 
						end.get(Calendar.DAY_OF_YEAR) == tempCal.get(Calendar.DAY_OF_YEAR)))	
					tempRes.add(reservations.get(i));
			}
		}
		
		Collections.sort(tempRes, ReservationComparator());
		
        for(int i = 0; i < tempRes.size(); i++) {
            JTextArea rows = new JTextArea();
            rows.setBackground(Color.LIGHT_GRAY);
            rows.setPreferredSize(new Dimension(300,50));
            borderPanel2.add(rows);
            rows.setLayout(null);
            rows.setEditable(false);
            Font font = new Font("Arial", Font.BOLD, 12);
            rows.setFont(font);
            
            textFill = " Room: " + tempRes.get(i).getRoomID() + "  \n User: " +
					tempRes.get(i).getUserID() + "  \n Price: " + 
					rooms.get(tempRes.get(i).getRoomID()).getPrice();
            
            rows.setText(textFill);
            
            if(i % 2 == 1)
                rows.setBackground(SystemColor.inactiveCaptionBorder);
        }
	}
	
	public static Comparator<Reservation> ReservationComparator(){
		return new Comparator<Reservation>(){
			public int compare(Reservation a, Reservation b) {
				if(a.getRoomID() < b.getRoomID()) return -1;
				else return 1;
			}
		};
	}
}