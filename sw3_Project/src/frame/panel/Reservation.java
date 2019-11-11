package frame.panel;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.Border;

import memory.Remember;

public class Reservation extends ContentPane {

	private JLabel lb, lbl;
	private JComboBox<Object> cmb[];
	private JButton bt, btn;
	private JPanel center;
	private String year, month, day;

	public Reservation() {
		lb = new JLabel("배차차량조회 및 예약하기");
		lb.setFont(font);
		contentPane.add(lb, BorderLayout.NORTH);
		center = new JPanel();
		center.setLayout(new FlowLayout(FlowLayout.LEFT));
		cmb = new JComboBox[5];
		lbl = new JLabel(" 차량정보 : ");
		center.add(lbl);
		for (int i = 0; i < cmb.length; i++) {
			cmb[i] = new JComboBox<>();
			center.add(cmb[i]);
		}
		bt = new JButton("좌석조회");
		btn = new JButton("메인으로");
		center.add(bt);
		center.add(btn);
		contentPane.add(center, BorderLayout.CENTER);
		ResultSet rs = d.getRs("select bNumber from TBL_Bus");
		try {
			while (rs.next()) {
				cmb[0].addItem(rs.getString(1));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		cmb[1].addItem("1호차");
		cmb[1].addItem("2호차");
		cmb[3].addItem("==월 추가==");
		cmb[4].addItem("==일 추가==");
		cmb[2].addItem(new SimpleDateFormat("yyyy").format(new Date()) + "년");
		for (int i = 0; i < 3; i++) {
			int month = Integer.parseInt(new SimpleDateFormat("MM").format(new Date())) + i;
			if (month < 13) {
				if (month < 10) {
					cmb[3].addItem("0" + month + "월");
				} else {
					cmb[3].addItem(month + "월");
				}
			}
		}
		cmb[3].addActionListener(e -> {
			Calendar cal = Calendar.getInstance();
			year = cmb[2].getSelectedItem().toString().substring(0, 4);
			month = cmb[3].getSelectedItem().toString().substring(0, 2);
			cal.set(Integer.parseInt(year), Integer.parseInt(month), 01);
			cmb[4].removeAllItems();
			cmb[4].addItem("==일 추가==");
			for (int i = 1; i < cal.getActualMaximum(cal.DAY_OF_MONTH); i++) {
				if (i < 10) {
					cmb[4].addItem("0" + i + "일");
				} else {
					cmb[4].addItem(i + "일");
				}
			}
		});
		btn.addActionListener(e -> {
			contentPane.removeAll();
			bp.bg.clearSelection();
			set();
		});
		bt.addActionListener(e -> {
			if (cmb[3].getSelectedIndex() == 0 || cmb[4].getSelectedIndex() == 0) {
				o.info("월 또는 일을 선택하여 주십시오.");
				return;
			}
			o.war("예약이 시작됩니다.", "시");
			day = cmb[4].getSelectedItem().toString().substring(0, 2);
			Remember.getInstance().setData(cmb[0].getSelectedItem(), year + "-" + month + "-" + day,
					cmb[1].getSelectedItem());
			bp.tb[2].doClick();
		});
		set();
	}

}
