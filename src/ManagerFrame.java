import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import javax.swing.BorderFactory;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
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
	JPanel CalendarPanel;
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
		
		GregorianCalendar calendar = new GregorianCalendar();
		day = calendar.get(GregorianCalendar.DAY_OF_MONTH);
		month = calendar.get(GregorianCalendar.MONTH);
		year = calendar.get(GregorianCalendar.YEAR);
		
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
					int x = CalendarTable.getSelectedRow();
					int y = CalendarTable.getSelectedColumn();
					String selected = (String) CalendarTable.getValueAt(x, y);
					int selectedInt;
					if(selected != "") selectedInt = Integer.parseInt(selected.substring(1));
					else selectedInt = 0;
					textFill = "TESTING: " + selected;
					textFrame();
				}
			}
		};
		CalendarTable.getSelectionModel().addListSelectionListener(ls);
		CalendarTable.getColumnModel().getSelectionModel() .addListSelectionListener(ls);
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
	public void textFrame(){
		text = new JTextArea();
		frame.add(text);
		text.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		text.setBounds(329, 4 ,290, 318);
		text.setEditable(false);
		
		text.setText(textFill);
	}
}