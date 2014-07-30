import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ServerController {
	private ServerModel model;
	private ServerView view;
	private CreateServer server;

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
						server = new CreateServer();
					}
				});
				t.start();
				view.start.setEnabled(false);
				view.stop.setEnabled(true);
			}
		});
		view.stop.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				CreateServer.closeServer();
				System.exit(0);
			}
		});
	}
}
