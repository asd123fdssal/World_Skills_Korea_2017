package frame.ui;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class BaseFrame {
	private static BaseFrame instance = new BaseFrame();

	public static BaseFrame getInstance() {
		return instance;
	}

	private JFrame frame;

	public void setContentPane(JPanel contentPane) {
		frame.setContentPane(contentPane);
		frame.revalidate();
		frame.repaint();
	}

	public BaseFrame() {
		frame = new JFrame("고속버스예매 프로그램");
		frame.setBounds(100, 100, 700, 700);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}
}
