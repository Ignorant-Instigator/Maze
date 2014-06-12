import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;

class ServerFrame {
	JFrame frame;
	JButton start, stop;
	Thread thread;
	CreateServer server;

	ServerFrame() {
		setFrame();
		adjustThreads();
	}

	void adjustThreads() {
		thread = new Thread(new Runnable() {
			public void run() {
				server = new CreateServer();
			}
		});
	}

	void setFrame() {
		frame = new JFrame("Maze Server");
		frame.setLayout(new FlowLayout());
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(200, 120);
		frame.setResizable(false);
		frame.setLocationRelativeTo(null);
		start = new JButton("Start");
		stop = new JButton("Stop");
		stop.setEnabled(false);
		start.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				start.setEnabled(false);
				stop.setEnabled(true);
				thread.start();
			}
		});
		stop.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				start.setEnabled(true);
				stop.setEnabled(false);
				CreateServer.closeServer();
				thread.stop();
			}
		});
		frame.add(start);
		frame.add(stop);
		frame.setVisible(true);
	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				new ServerFrame();
			}
		});
	}
}