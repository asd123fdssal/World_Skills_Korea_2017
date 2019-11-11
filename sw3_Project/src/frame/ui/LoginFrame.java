package frame.ui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import frame.content.OPane;
import frame.panel.ContentPane;
import jdbc.Dao;

public class LoginFrame extends JFrame {
	private JRadioButton r, rb;
	private JLabel l, lb, lbl;
	private JTextField tf;
	private JPasswordField pwf;
	private JButton bt, btn;
	private ButtonGroup bg;
	private JPanel top, left, center, right, bottom, contentPane;
	private OPane o = OPane.getInstance();
	public static String ID;

	public LoginFrame() {
		setTitle("회원 로그인");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		r = new JRadioButton("회원 로그인");
		rb = new JRadioButton("비회원 로그인");
		bg = new ButtonGroup();
		bg.add(rb);
		bg.add(r);
		l = new JLabel("로그인");
		l.setHorizontalAlignment(SwingConstants.CENTER);
		l.setFont(new Font("맑은 고딕", Font.BOLD, 20));
		lb = new JLabel("회원 번호 : ");
		lbl = new JLabel("비밀 번호 : ");
		bt = new JButton("확인");
		btn = new JButton("취소");
		tf = new JTextField(8);
		pwf = new JPasswordField(8);
		top = new JPanel();
		top.setLayout(new FlowLayout());
		left = new JPanel();
		left.setLayout(new GridLayout(2, 1, 0, 10));
		center = new JPanel();
		center.setLayout(new GridLayout(2, 1, 0, 10));
		right = new JPanel();
		right.setLayout(new GridLayout(2, 1));
		bottom = new JPanel();
		bottom.setLayout(new FlowLayout());
		contentPane = new JPanel();
		contentPane.setLayout(new BorderLayout());
		top.add(r);
		top.add(rb);
		left.add(lb);
		left.add(lbl);
		center.add(tf);
		center.add(pwf);
		right.add(bt);
		right.add(btn);
		bottom.add(left);
		bottom.add(center);
		bottom.add(right);
		contentPane.add(top, BorderLayout.NORTH);
		contentPane.add(l, BorderLayout.CENTER);
		contentPane.add(bottom, BorderLayout.SOUTH);
		contentPane.setBorder(new EmptyBorder(3, 3, 3, 3));
		setContentPane(contentPane);
		r.setSelected(true);
		r.addActionListener(e -> {
			tf.setText("");
			pwf.setText("");
			tf.setEditable(true);
			pwf.setEditable(true);
		});
		rb.addActionListener(e -> {
			tf.setText("비회원");
			pwf.setText("비회원");
			tf.setEditable(false);
			pwf.setEditable(false);
		});
		bt.addActionListener(e -> {
			if (r.isSelected()) {
				ResultSet rs = Dao.getInstance().getRs("select * from TBL_Customer where cID = ? and cPW = ?",
						tf.getText(), new String(pwf.getPassword()));
				try {
					if (rs.next()) {
						o.info("로그인 완료");
						dispose();
						new ContentPane();
						ID = tf.getText();
					} else {
						o.info("없는 회원입니다. 다시 확인하여 주십시오.");
					}
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			} else {
				o.info("비회원으로 로그인 합니다.");
				dispose();
				new ContentPane();
				ID = tf.getText();
			}
		});
		btn.addActionListener(e -> {
			System.exit(0);
		});
		pack();
		setVisible(true);
	}
}
