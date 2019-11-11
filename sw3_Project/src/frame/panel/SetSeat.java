package frame.panel;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.StringTokenizer;
import java.util.Vector;
import java.util.regex.Pattern;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import frame.content.AbsTable;
import frame.ui.LoginFrame;
import memory.Remember;

public class SetSeat extends ContentPane {

	private JLabel lbl[], lb;
	private JTextField tf[];
	private JButton bt, btn;
	private JPanel centerP, center, south;
	private String lblTitle[] = { "차량번호", "출발일자", "호차번호", "출발일자", "차량번호", "좌석번호", "회원ID" };
	private AbsTable table;

	public SetSeat() {
		lb = new JLabel("좌석표 선택");
		lb.setFont(font);
		contentPane.add(lb, BorderLayout.NORTH);
		lbl = new JLabel[lblTitle.length];
		tf = new JTextField[lbl.length];
		center = new JPanel();
		center.setLayout(new FlowLayout(FlowLayout.LEFT));
		south = new JPanel();
		south.setLayout(new FlowLayout(FlowLayout.LEFT));
		centerP = new JPanel();
		centerP.setLayout(new BorderLayout());
		for (int i = 0; i < lbl.length; i++) {
			lbl[i] = new JLabel(lblTitle[i]);
			tf[i] = new JTextField(7);
			if (i < 3) {
				center.add(lbl[i]);
				center.add(tf[i]);
			} else {
				south.add(lbl[i]);
				south.add(tf[i]);
			}
		}
		tf[0].setText(Remember.getInstance().data1);
		tf[1].setText(Remember.getInstance().data2);
		tf[2].setText(Remember.getInstance().data3);
		tf[6].setText(LoginFrame.ID);
		tf[6].setEditable(false);
		Remember.getInstance().setData("", "", "");
		bt = new JButton("좌석조회");
		btn = new JButton("예약");
		center.add(bt);
		south.add(btn);
		centerP.add(center, BorderLayout.NORTH);
		contentPane.add(centerP, BorderLayout.CENTER);
		contentPane.add(south, BorderLayout.SOUTH);
		set();
		bt.addActionListener(e -> {
			if (table != null) {
				centerP.removeAll();
				centerP.add(center, BorderLayout.NORTH);
				set();
			}
			if (!Pattern.matches("^[A][0-9]{3}$", tf[0].getText())) {
				o.info("올바르지 못한 좌석정보입니다.");
				return;
			}
			if (!Pattern.matches("^[1-2]호차$", tf[2].getText())) {
				o.info("올바르지 못한 좌석정보입니다.");
				return;
			}
			tf[3].setText(tf[1].getText());
			tf[4].setText(tf[0].getText());
			tf[3].setEnabled(false);
			tf[4].setEnabled(false);
			table = new AbsTable() {

				@Override
				public Vector<Vector<Object>> getData() {
					String sql = "select %s,%s,'---',%s,%s from TBL_Ticket, (select @a1 := -3) A,(select @a2 := -2) B,(select @a3 := -1) C,(select @a4 := -0) D limit 5";
					String smt = "@a%s:=(@a%s + 4),(select if (bState is null,'','○') from TBL_Ticket where bNumber = '%s' and bDate = '%s' and bNumber2 = '%s' and bSeat = @a%s)";
					sql = String.format(sql, toStm(smt, 1), toStm(smt, 2), toStm(smt, 3), toStm(smt, 4));
					System.out.println(sql);
					return d.getDoubleVector(d.getRs(sql));
				}

				@Override
				public Vector<Object> getColumn() {
					return toVector("좌측창가", "예약", "좌측통로", "예약", "통로", "우측창가", "예약", "우측통로", "예약");
				}
			};
			table.setLayout(new FlowLayout(FlowLayout.LEFT));
			table.getScrp().setPreferredSize(new Dimension(530, 400));
			centerP.add(table, BorderLayout.CENTER);
			set();
		});
		btn.addActionListener(e -> {
			if (tf[5].getText().equals("")) {
				o.info("좌석번호를 입력해주십시오.");
			}
			StringTokenizer st = new StringTokenizer(tf[5].getText(), ",| | ,|, ");
			HashSet<String> set = new HashSet<>();
			ArrayList<String> list = new ArrayList<>();
			while (st.hasMoreTokens()) {
				String token = st.nextToken();
				list.add(token);
				set.add(token);
			}
			if (list.size() != set.size()) {
				Iterator<String> it = set.iterator();
				while (it.hasNext()) {
					list.remove(list.indexOf(it.next()));
				}
				set = new HashSet<>();
				for (int i = 0; i < list.size(); i++) {
					set.add(list.get(i));
				}
				o.info("좌석번호 " + set.toString().replaceAll("\\]|\\[", "") + "이(가) 중복입력되었습니다.");
				return;
			}
			String isNum = "";
			for (String lists : list) {
				try {
					int row = (Integer.parseInt(lists) - 1) / 4;
					int col = (Integer.parseInt(lists) - 1) % 4;
					if (table.getTable().getValueAt(row, (col == 0) ? 1 : (col == 1) ? 3 : (col + 1) * 2) != null) {
						isNum = isNum + "," + lists;
					}
					if (!isNum.equals("")) {
						o.info("좌석번호 " + isNum.replace(",", "") + "은 이미 예약되어있는 좌석입니다.");
						return;
					}
				} catch (NumberFormatException e1) {
					o.info("올바르지 못한 좌석정보입니다.");
				}
			}
			String text = "차량번호 [" + tf[4].getText() + "]\n예약일자[" + tf[3].getText() + "]\n좌석번호" + set.toString()
					+ "\n고객번호[" + tf[6].getText() + "]\n";
			int msg = JOptionPane.showConfirmDialog(null, text + "예약하시겠습니까?", "웹 페이지 메시지", JOptionPane.YES_NO_OPTION);
			if (msg == JOptionPane.YES_OPTION) {
				for (int i = 0; i < list.size(); i++) {
					ResultSet rs = d.getRs("select bprice from TBL_Bus where bNumber = ?", tf[4].getText());
					try {
						rs.next();
						d.update("insert into TBL_Ticket values(?,?,?,?,?,?,?)", tf[3].getText(), tf[4].getText(),
								tf[2].getText(), list.get(i), tf[6].getText(), rs.getString(1), "Ⅹ");
					} catch (SQLException e1) {
						e1.printStackTrace();
					}
				}
				o.war(text + "예약되었습니다.", "시");
				bp.tb[3].doClick();
			}
		});
	}

	public String toStm(String stm, int i) {
		return String.format(stm, i, i, tf[0].getText(), tf[1].getText(), tf[2].getText(), i);
	}
}
