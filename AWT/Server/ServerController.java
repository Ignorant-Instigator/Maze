import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ServerController {
	private ServerModel model;
	private ServerView view;
	private Server server;

	public ServerController(ServerModel model, ServerView view) {
		this.model = model;
		this.view = view;
		attachListener();
	}

	private void attachListener() {
		view.stop.setEnabled(false);
		view.start.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Thread t = new Thread(new Runnable() {
					public void run() {
						server = new Server();
					}
				});
				t.start();
				view.start.setEnabled(false);
				view.stop.setEnabled(true);
			}
		});
		view.stop.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Server.closeServer();
				System.exit(0);
			}
		});
	}
}
