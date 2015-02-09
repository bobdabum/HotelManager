import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

/**
 * Hotel Frame is the starting frame for the user.
 * @author Ji Hoon Yoon
 *
 */
public class HotelFrame{
	
	/**
	 * The constructor for the view
	 */
	public HotelFrame(RoomAndUserManager m, JFrame frame){
		this.m = m;
		this.frame = frame;
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
				frame.getContentPane().removeAll();
				UserFrame uFrame = new UserFrame(m, frame);
				frame.repaint();
				m.attachReservationListener(uFrame);
				m.attachRoomListener(uFrame);
			}
		});
		
		manager.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				frame.getContentPane().removeAll();
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
	Container pane;
	JFrame frame;
	RoomAndUserManager m;
}