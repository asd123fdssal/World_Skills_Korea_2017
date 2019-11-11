package frame.panel;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ButtonGroup;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JToggleButton;
import javax.swing.SwingConstants;

public class BasePanel extends JPanel implements ActionListener {
	private static BasePanel instance = new BasePanel();
	private JLabel lbl;
	protected JToggleButton tb[];
	protected ButtonGroup bg;
	private JPanel center, black;
	private String btnTitle[] = { "배차차량조회", "예약신청", "차량좌석조회", "승차권예매", "예약조회" };

	public static BasePanel getInstance() {
		return instance;
	}

	public BasePanel() {
		setLayout(new BorderLayout());
		lbl = new JLabel("고속버스예매 프로그램");
		lbl.setHorizontalAlignment(SwingConstants.CENTER);
		lbl.setFont(new Font("맑은 고딕", Font.BOLD, 20));
		add(lbl, BorderLayout.NORTH);
		center = new JPanel();
		center.setLayout(new FlowLayout());
		tb = new JToggleButton[5];
		bg = new ButtonGroup();
		for (int i = 0; i < tb.length; i++) {
			tb[i] = new JToggleButton(btnTitle[i]);
			center.add(tb[ i]);
			tb[i].addActionListener(this);
			bg.add(tb[i]);
		}
		add(center, BorderLayout.CENTER);
		black = new JPanel();
		black.setBackground(Color.BLACK);
		add(black, BorderLayout.SOUTH);

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == tb[0]) {
			new Table();
		} else if (e.getSource() == tb[1]) {
			new Reservation();
		} else if (e.getSource() == tb[2]) {
			new SetSeat();
		} else if (e.getSource() == tb[3]) {
			new Ticketing();
		} else if (e.getSource() == tb[4]) {
			new ReservationSearch();
		}
	}

}
