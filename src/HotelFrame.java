import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Panel;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JToggleButton;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.View;

public class HotelFrame{
	
	/**
	 * The constructor for the view
	 */
	public HotelFrame(RoomManager m){
		this.m = m;
		frame = new JFrame("Hotel Reservation");
		pane = frame.getContentPane();
	}
	
	/**
	 * runs HotelView
	 */
	public void run(){
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		JButton guest = new JButton("Guest");
		JButton manager = new JButton("Manager");
		guest.setBounds(6,6, 304, 306);
		manager.setBounds(315,6, 304, 306);
		Font font = new Font("Arial", Font.BOLD, 14);
		guest.setFont(font);
		manager.setFont(font);
		
		guest.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				LogIn();
			}
		});
		
		manager.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				pane.removeAll();
				frame.repaint();
				ManagerFrame mf = new ManagerFrame(m, frame);
				mf.displayFrame();
			}
		});

		pane.add(manager);
		pane.add(guest);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setBounds(20, 20, 630, 350);
		pane.setLayout(null);
		frame.setResizable(false);
		frame.setVisible(true);
	}
	
	public void LogIn(){
		pane.removeAll();
		frame.repaint();	
		
		JLabel label = new JLabel("Enter Your User ID:");
		label.setBounds(195, 80, 300, 20);
		Font font = new Font("Copperplate Gothic Bold", Font.PLAIN, 20);
		label.setFont(font);
		
		final JTextArea text = new JTextArea();
		text.setBounds(195, 110, 230, 21);
		text.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		Font font2 = new Font("Arial", Font.PLAIN, 14);
		text.setFont(font2);
		text.setLineWrap(true);
		text.setWrapStyleWord(true);		
		
		final JLabel error = new JLabel("Sorry, we cannot find this ID in our system. Please try again.");
		
		text.getDocument().addDocumentListener(new DocumentListener(){
			public void insertUpdate(DocumentEvent e) {
				View view = text.getUI().getRootView(text).getView(0);
				int height1 = (int)view.getPreferredSpan(View.Y_AXIS);
				int height2 = text.getFontMetrics(text.getFont()).getHeight();
				if(height1/height2 == 2) {
				    Runnable setText = new Runnable() {
				        public void run(){
				            text.setText(text.getText().substring(0, text.getText().length()-2));
				        }
				    };
					SwingUtilities.invokeLater(setText);
				}
			}
			public void removeUpdate(DocumentEvent e) {}
			public void changedUpdate(DocumentEvent e){}
		});
		text.addKeyListener(new KeyAdapter(){
			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode() == KeyEvent.VK_ENTER){
					//if the ID matches, go on, if not, error.
					error.setBounds(160, 180, 300, 21);
				}
			}		
		});
		
		if(text.getRows() == 2){
			String temp = text.getText();
			String oneLine = temp.substring(temp.length()-1);
			text.setText(oneLine);
		}
		
		JButton submit = new JButton("Submit");
		JButton create = new JButton("Create Account");
		submit.setBounds(195, 140, 110, 25);
		create.setBounds(315, 140, 110, 25);
		
		submit.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				//if the ID matches, go on, if not, error.
				error.setBounds(160, 180, 300, 21);
			}
		});
		
		create.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				pane.removeAll();
				frame.repaint();
				Font nameFont = new Font("Monotype Corsiva", Font.BOLD, 29);
				JLabel name = new JLabel("Please enter your name: ");
				name.setFont(nameFont);
				name.setBounds(175, 80, 300, 20);
				
				final JTextArea nameArea = new JTextArea();
				nameArea.setBorder(BorderFactory.createLineBorder(Color.BLACK));
				nameArea.setBounds(210, 117, 210, 20);
				
				final JButton submitName = new JButton("Submit");
				submitName.addActionListener(new ActionListener(){
					public void actionPerformed(ActionEvent arg0) {
						JOptionPane.showMessageDialog(frame, "Thanks for registering, " + 
							nameArea.getText() + ". Your ID is: " + 3 + ".");
						pane.removeAll();
						frame.repaint();
						reservationScreen();
					}
				});
				nameArea.addKeyListener(new KeyAdapter(){
					public void keyPressed(KeyEvent e) {
						if(e.getKeyCode() == KeyEvent.VK_ENTER){
							JOptionPane.showMessageDialog(frame, "Thanks for registering, " + 
								nameArea.getText() + "\nYour ID is: " + 3 + ".");
							pane.removeAll();
							frame.repaint();
							reservationScreen();
						}
					}
				});
				submitName.setBounds(242, 148, 140, 27);
				
				pane.add(submitName);
				pane.add(name);
				pane.add(nameArea);
				
				//Add user info to usermanager
			}
		});
		
		pane.add(error);		
		pane.add(submit);
		pane.add(create);
		pane.add(label);
		pane.add(text);
	}
	
	/**
	 * shows the screen where guests can reserve a room or view their reservations
	 */
	public void reservationScreen(){
		//the view for the reservations creen
		//the left side of the screen
		JPanel reservationPanel = new JPanel();
		reservationPanel.setLayout(null);
		reservationPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		reservationPanel.setBounds(3, 4, 320, 312);
		
		//the right side where it shows which rooms are available
		final JScrollPane scroller = new JScrollPane();
		scroller.setBounds(330, 4, 285, 314);
		pane.add(scroller);
		JPanel borderPanel = new JPanel();
		JPanel borderPanel2 = new JPanel();

		scroller.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		scroller.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		scroller.setViewportView(borderPanel);
		borderPanel.setLayout(new BorderLayout(0, 0));
		scroller.setBorder(BorderFactory.createTitledBorder("Available Rooms"));
		borderPanel.add(borderPanel2, BorderLayout.NORTH);
		borderPanel2.setLayout(new GridLayout(0, 1, 0, 1));
		
		for (int i = 0; i < 20; i++) {
			JTextArea rows = new JTextArea();
			rows.setBackground(Color.LIGHT_GRAY);
			rows.setPreferredSize(new Dimension(300, 50));
			borderPanel2.add(rows);
			rows.setLayout(null);
			rows.setEditable(false);
			Font font = new Font("Arial", Font.BOLD, 12);
			rows.setFont(font);
			rows.setText("sup");

			if (i % 2 == 1) rows.setBackground(SystemColor.inactiveCaptionBorder);
			scroller.getVerticalScrollBar().setValue(0);
		}
		//sets the scroller to the top
		SwingUtilities.invokeLater(new Runnable() {
		      public void run() {
		              scroller.getViewport().setViewPosition(new java.awt.Point(0, 0));
		      }
		});
		pane.add(reservationPanel);
		
		//the "Make Reservation" sign
		JLabel makeReservation = new JLabel("Make a Reservation");
		Font font = new Font("Bookman Old Style", Font.BOLD, 22);
		makeReservation.setFont(font);
		makeReservation.setBounds(40, 40, 250, 40);
		reservationPanel.add(makeReservation);
		
		//Input for the left side
		JLabel checkIn = new JLabel("Check-In (mm/dd/yy):");
		JLabel checkOut = new JLabel("Check-Out (mm/dd/yy):");
		JTextArea checkInText = new JTextArea();
		final JTextArea checkOutText = new JTextArea();	
		
		checkIn.setBounds(35, 100, 150, 20);
		checkInText.setBounds(155, 100, 140, 20);
		checkInText.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		checkOut.setBounds(35, 130, 150, 20);
		checkOutText.setBounds(155, 130, 140, 20);
		checkOutText.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		checkInText.addKeyListener(new KeyAdapter(){
			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode() == KeyEvent.VK_TAB || e.getKeyCode() == KeyEvent.VK_ENTER){
				}
			}
		});
		checkOutText.addKeyListener(new KeyAdapter(){
			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode() == KeyEvent.VK_TAB || e.getKeyCode() == KeyEvent.VK_ENTER){
				}
			}
		});
		
		//Room type
		JLabel roomType = new JLabel("Room Type:");
		JLabel regularLabel = new JLabel("Regular");
		JLabel deluxeLabel = new JLabel("Deluxe");
		roomType.setBounds(35, 170, 200, 20);
		regularLabel.setBounds(64, 198, 200, 20);
		deluxeLabel.setBounds(200, 198, 200, 20);
		Font roomFont = new Font("Monotype Corsiva", Font.ITALIC, 17);
		regularLabel.setFont(roomFont);
		deluxeLabel.setFont(roomFont);
		reservationPanel.add(roomType);
		reservationPanel.add(regularLabel);
		reservationPanel.add(deluxeLabel);

		ButtonGroup prices = new ButtonGroup();
		JToggleButton regular = new JToggleButton("$80");
		JToggleButton deluxe = new JToggleButton("$200");
		prices.add(regular);
		prices.add(deluxe);
		regular.setBounds(40, 220, 100, 30);
		deluxe.setBounds(170, 220, 100, 30);
		regular.setActionCommand("regular");
		deluxe.setActionCommand("deluxe");
		
		ActionListener buttons = new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				if(e.getActionCommand() == "regular") roomInt = 0;
				if(e.getActionCommand() == "deluxe") roomInt = 1;
			}
		};

		regular.addActionListener(buttons);
		deluxe.addActionListener(buttons);
		
		reservationPanel.add(regular);
		reservationPanel.add(deluxe);
		reservationPanel.add(checkIn);
		reservationPanel.add(checkOut);
		reservationPanel.add(checkInText);
		reservationPanel.add(checkOutText);
	}
	
	int roomInt = -1;
	Container pane;
	JFrame frame;
	RoomManager m;
}