import java.awt.GridLayout;
import javax.swing.JLabel;

/**
 * List Item for receipt sum
 * @author Linye Ouyang
 *
 */
@SuppressWarnings("serial")
public class ListItemReceiptSum extends ListItem {
	/**
	 * Constructor for Class
	 * @param total
	 * @param position ID for superclass.
	 */
		public ListItemReceiptSum(int total, int position) {
			super(position);
			setLayout(new GridLayout(1,2));
			add(new JLabel("Total Cost:"));
			add(new JLabel("$"+total));
		}
}
