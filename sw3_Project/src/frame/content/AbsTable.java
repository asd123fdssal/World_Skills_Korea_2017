package frame.content;

import java.util.Vector;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;

public abstract class AbsTable extends JPanel {

	private JTable table;
	private JScrollPane scrp;

	public JTable getTable() {
		return table;
	}

	public JScrollPane getScrp() {
		return scrp;
	}

	public AbsTable() {
		table = new JTable();
		scrp = new JScrollPane(table);
		add(scrp);
		table.setRowHeight(30);
		reload();
	}

	public void reload() {
		table.setModel(new DefaultTableModel(getData(),getColumn()) {
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		});
		TableColumnModel model = table.getColumnModel();
		DefaultTableCellRenderer dtcr = new DefaultTableCellRenderer();
		dtcr.setHorizontalAlignment(SwingConstants.CENTER);
		for (int i = 0; i < table.getColumnCount(); i++) {
			model.getColumn(i).setCellRenderer(dtcr);
		}
	}

	public abstract Vector<Vector<Object>> getData();

	public abstract Vector<Object> getColumn();

	public Vector<Object> toVector(Object... objects) {
		Vector<Object> v = new Vector<>();
		for (int i = 0; i < objects.length; i++) {
			v.add(objects[i]);
		}
		return v;
	}

}
