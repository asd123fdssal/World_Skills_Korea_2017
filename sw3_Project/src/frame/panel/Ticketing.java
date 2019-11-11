package frame.panel;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import frame.content.AbsTable;

public class Ticketing extends ContentPane {

	private JLabel lbl[];
	private JTextField tf[];
	private JButton btn;
	private String lblTitle[] = { "출발일자", "차량번호", "버스번호", "회원ID" };
	private JPanel center;
	private AbsTable table;
	private ResultSet rs;

	public Ticketing() {
		lbl = new JLabel[lblTitle.length];
		tf = new JTextField[lbl.length];
		center = new JPanel();
		center.setLayout(new FlowLayout());
		for (int i = 0; i < lbl.length; i++) {
			lbl[i] = new JLabel(lblTitle[i]);
			tf[i] = new JTextField(7);
			center.add(lbl[i]);
			center.add(tf[i]);
		}
		btn = new JButton("조회");
		center.add(btn);
		contentPane.add(center, BorderLayout.NORTH);
		Vector<Vector<Object>> vv = new Vector<>();
		for (int i = 0; i < 7; i++) {
			Vector<Object> v = new Vector<>();
			for (int j = 0; j < 3; j++) {
				v.add("");
			}
			vv.add(v);
		}

		btn.addActionListener(e -> {
			if (table != null) {
				contentPane.removeAll();
				contentPane.add(center, BorderLayout.NORTH);
				set();
			}
			if (tf[0].getText().equals("") || tf[1].getText().equals("") || tf[2].getText().equals("")
					|| tf[3].getText().equals("")) {
				o.war("발권할 승차권의 데이터를 입력해주시기 바랍니다.", "시");
				return;
			}
			rs = d.getRs(
					"select b.bDeparture ,b.bArrival,t.bPrice,t.bDate from TBL_Bus as b, tbl_ticket as t where t.bDate = ? and t.bNumber= ? and b.bNumber = t.bNumber and t.bNumber2 = ? and t.cID = ?",
					tf[0].getText(), tf[1].getText(), tf[2].getText(), tf[3].getText());
			try {
				table = new AbsTable() {

					@Override
					public Vector<Vector<Object>> getData() {
						return vv;
					}

					@Override
					public Vector<Object> getColumn() {
						return toVector("출발지", "→→→", "도착지");
					}
				};

				rs.last();
				int row = rs.getRow();
				if (row == 0) {
					o.war("조회한 정보가 존재하지 않습니다", "세");
					return;
				}
				if (table.getTable().getRowCount() == 0) {
					table.removeAll();
					return;
				}
				rs.beforeFirst();
				rs.next();
				table.getTable().setValueAt(rs.getString(1), 0, 0);
				table.getTable().setValueAt(rs.getString(2), 0, 2);

				table.getTable().setValueAt("운행요금", 2, 0);
				table.getTable().setValueAt("할인요금", 2, 1);
				table.getTable().setValueAt("영수액", 2, 2);

				table.getTable().setValueAt((rs.getInt(3) * row + "").replace(".0", ""), 3, 0);
				if (tf[3].getText().equals("비회원")) {
					table.getTable().setValueAt(0, 3, 1);
					table.getTable().setValueAt((rs.getInt(3) * row + "").replace(".0", ""), 3, 2);
				} else {
					table.getTable().setValueAt((rs.getInt(3) * row * 0.1 + "").replace(".0", ""), 3, 1);
					table.getTable().setValueAt((rs.getInt(3) * row * 0.9 + "").replace(".0", ""), 3, 2);
				}
				table.getTable().setValueAt("발권날짜", 5, 0);
				table.getTable().setValueAt("출발일자", 5, 2);
				table.getTable().setValueAt(new SimpleDateFormat("yyyy-MM-dd").format(new Date()), 6, 0);
				table.getTable().setValueAt(rs.getString(4), 6, 2);
				table.reload();
				table.getScrp().setPreferredSize(new Dimension(600, 235));
				contentPane.add(table, BorderLayout.CENTER);
				set();
				d.update(
						"update TBL_Ticket set bState = ? where bDate = ? and bNumber = ? and bNumber2 = ? and cID = ? ",
						"○", tf[0].getText(), tf[1].getText(), tf[2].getText(), tf[3].getText());
				o.war("승차권이 정상적으로 발권되었습니다.", "세");
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		});
		set();
	}
}
