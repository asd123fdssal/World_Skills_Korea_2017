package frame.panel;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import frame.content.AbsTable;

public class ReservationSearch extends ContentPane {

	private JLabel lbl, lb;
	private JTextField tf;
	private JButton btn;
	private JPanel center, centerP;
	private AbsTable table;

	public ReservationSearch() {
		lb = new JLabel("고객티켓예약조회");
		lb.setFont(font);
		lbl = new JLabel(" 고객ID");
		tf = new JTextField(7);
		btn = new JButton("조회");
		center = new JPanel();
		center.setLayout(new FlowLayout(FlowLayout.LEFT));
		centerP = new JPanel();
		centerP.setLayout(new BorderLayout());
		center.add(lbl);
		center.add(tf);
		center.add(btn);
		contentPane.add(lb, BorderLayout.NORTH);
		centerP.add(center, BorderLayout.NORTH);
		contentPane.add(centerP, BorderLayout.CENTER);
		set();
		btn.addActionListener(e -> {
			if (table != null) {
				centerP.removeAll();
				centerP.add(center, BorderLayout.NORTH);
				table.removeAll();
				set();
			}
			table = new AbsTable() {

				@Override
				public Vector<Vector<Object>> getData() {
					String sql = "select c.cName, t.bDate,t.bNumber,t.bSeat,t.bPrice,t.bState from tbl_customer as c, tbl_ticket as t where t.cid = ? and t.cid = c.cid";
					if (tf.getText().equals("비회원")) {
						sql = "select '', t.bDate,t.bNumber,t.bSeat,t.bPrice,t.bState from tbl_customer as c, tbl_ticket as t where t.cid = ?";
					}
					return d.getDoubleVector(d.getRs(sql, tf.getText()));
				}

				@Override
				public Vector<Object> getColumn() {
					return toVector("성명", "예약일", "차량번호", "좌석번호", "운임금액", "발권상태");
				}
			};
			if (table.getTable().getRowCount() == 0) {
				table.removeAll();
				return;
			}
			centerP.add(table, BorderLayout.CENTER);
			table.setLayout(new FlowLayout(FlowLayout.LEFT));
			table.getTable().setAutoResizeMode(0);
			table.getScrp().setPreferredSize(new Dimension(500, 400));
			set();
		});
	}

}
