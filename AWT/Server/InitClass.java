import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;

public class InitClass {
	private JFrame frame;

	public InitClass() {
		setFrame();
		setMVC();
		frame.setVisible(true);
	}

	private void setFrame() {
		frame = new JFrame("Maze Server");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(200, 120);
		frame.getContentPane().setBackground(Color.GRAY);
		frame.setLocationRelativeTo(null);
	}

	private void setMVC() {
		ServerModel model = new ServerModel();
		ServerView view = new ServerView(model);
		ServerController controller = new ServerController(model, view);
		frame.add(view);
	}
	
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				new InitClass();
			}
		});
	}

}
