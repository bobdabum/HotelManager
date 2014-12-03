import java.awt.GridLayout;
import javax.swing.JLabel;

@SuppressWarnings("serial")
public class ListItemReceiptSum extends ListItem {
		public ListItemReceiptSum(Reservation myItem, int total, int position) {
			super(position);
			setLayout(new GridLayout(1,2));
			add(new JLabel("Total Cost:"));
			add(new JLabel("$"+total));
		}
}
