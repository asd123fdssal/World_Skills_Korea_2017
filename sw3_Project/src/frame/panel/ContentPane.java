package frame.panel;

import java.awt.BorderLayout;
import java.awt.Font;

import javax.swing.JPanel;

import frame.content.OPane;
import frame.ui.BaseFrame;
import jdbc.Dao;

public class ContentPane extends JPanel {

	protected JPanel contentPane;
	protected BasePanel bp = BasePanel.getInstance();
	protected Dao d = Dao.getInstance();
	protected OPane o = OPane.getInstance();
	protected Font font = new Font("맑은 고딕", Font.BOLD, 20);

	public ContentPane() {
		setLayout(new BorderLayout());
		contentPane = new JPanel();
		contentPane.setLayout(new BorderLayout());
		add(bp, BorderLayout.NORTH);
		add(contentPane, BorderLayout.CENTER);
		set();
	}

	public void set() {
		BaseFrame.getInstance().setContentPane(this);
	}

}
