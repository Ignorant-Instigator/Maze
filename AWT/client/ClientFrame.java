import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

public class ClientFrame {
	private static JFrame frame;
	private static JTextField input;
	private static JLabel announce;
	private static Portray game;
	private static String name;

	ClientFrame() {
		setFrame();
		attachActuators();
	}

	private void setFrame() {
		frame = new JFrame("Maze");
		frame.setLayout(new GridBagLayout());
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setBackground(Color.GRAY);
		frame.setResizable(false);
		frame.setSize(305, 327);
		frame.setFocusable(true);
		frame.setLocationRelativeTo(null);
		frame.addWindowListener(new WindowListener() {

			public void windowActivated(WindowEvent arg0) {
				// TODO Auto-generated method stub

			}

			@Override
			public void windowClosed(WindowEvent arg0) {
				// TODO Auto-generated method stub

			}

			@Override
			public void windowClosing(WindowEvent arg0) {
				try {
					Interactions.handMessage("leave");
				} catch (NullPointerException exc) {
				}
			}

			@Override
			public void windowDeactivated(WindowEvent arg0) {
				// TODO Auto-generated method stub

			}

			@Override
			public void windowDeiconified(WindowEvent arg0) {
				// TODO Auto-generated method stub

			}

			@Override
			public void windowIconified(WindowEvent arg0) {
				// TODO Auto-generated method stub

			}

			@Override
			public void windowOpened(WindowEvent arg0) {
				// TODO Auto-generated method stub

			}
		});
		frame.setVisible(true);
	}

	private void attachActuators() {
		GridBagConstraints c = new GridBagConstraints();
		c.gridx = 1;
		c.gridy = 1;
		announce = new JLabel("Input your name and press enter");
		frame.add(announce, c);
		c.gridy = 2;
		input = new JTextField(20);
		input.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Interactions begin = new Interactions();
				name = input.getText();
			}
		});

		frame.add(input, c);

	}

	static void hideActuators() {
		input.setVisible(false);
		announce.setVisible(false);
	}

	static void handField(boolean[][] field) {
		Portray.setField(field);
	}

	static void openGameField() {
		frame.add(new Portray(name));
	}

	static String getName() {
		return name;
	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				new ClientFrame();
			}
		});

	}

}
