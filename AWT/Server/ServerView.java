import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JComponent;

public class ServerView extends JComponent {
	private ServerModel model;
	public JButton start;
	public JButton stop;

	public ServerView(ServerModel model) {
		this.model = model;
		setLayout(new FlowLayout());
		addButtons();
	}

	private void addButtons() {
		start = new JButton("Start");
		stop = new JButton("Stop");
		start.setFocusable(false);
		stop.setFocusable(false);

		add(start);
		add(stop);
	}

}
