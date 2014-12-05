import javax.swing.JLabel;

@SuppressWarnings("serial")
public class ListItemGeneric extends ListItem {

	public ListItemGeneric(int id, String toBeAdded) {
		super(id);
		add(new JLabel(toBeAdded));
	}
}
