import javax.swing.JLabel;

/**
 * ListItem with only a string.
 * @author Linye Ouyang
 *
 */
@SuppressWarnings("serial")
public class ListItemGeneric extends ListItem {

	/**
	 * Creates ListItem with string.
	 * @param id
	 * @param toBeAdded String of interest.
	 */
	public ListItemGeneric(int id, String toBeAdded) {
		super(id);
		add(new JLabel(toBeAdded));
	}
}
