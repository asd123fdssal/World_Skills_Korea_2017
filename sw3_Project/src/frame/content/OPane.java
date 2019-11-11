package frame.content;

import javax.swing.JOptionPane;

public class OPane {
	private static OPane instance = new OPane();

	public static OPane getInstance() {
		return instance;
	}

	public void info(String text) {
		JOptionPane.showMessageDialog(null, text);
	}

	public void war(String text, String siso) {
		JOptionPane.showMessageDialog(null, text, "웹 페이지 메" + siso + "지", JOptionPane.WARNING_MESSAGE);
	}
}
