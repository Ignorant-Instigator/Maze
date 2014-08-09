import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Observable;
import java.util.Observer;

public class Controller implements Observer {
	private Interactions interact;
	private Model model;
	private View view;
	private long lastUPD;

	public Controller(Model model, View view) {
		this.model = model;
		this.view = view;
		attachListener();
	}

	private void attachListener() {
		view.setFocusable(true);
		view.addKeyListener(new KeyListener() {

			public void keyPressed(KeyEvent e) {
				long callTime = System.currentTimeMillis();
				if (callTime - lastUPD < 20) {
					return;
				}
				model.moveTo(e.getKeyCode());
				sendQuery(model.getX() + "-" + model.getY());
				lastUPD = System.currentTimeMillis();
			}

			public void keyReleased(KeyEvent e) {
				// TODO Auto-generated method stub
			}

			public void keyTyped(KeyEvent e) {
				// TODO Auto-generated method stub
			}
		});
	}

	private void sendQuery(String line) {
		interact.handMessage(line);
	}

	public void update(Observable o, Object arg) {
		if (arg instanceof String && interact == null) {
			view.setVisible(true);
			interact = new Interactions();
			interact.addObserver(this);
			interact.handMessage((String) arg);
			model.setName((String) arg);
			return;
		}
		if (arg instanceof String[]) {
			String arr[] = (String[]) arg;
			if (arr[0].equals("level")) {
				model.setField(arr[1]);
				return;
			}
			if (arr[0].equals("remove")) {
				Users user = new Users();
				user.setName(arr[1]);
				model.removeUser(user);
				return;
			}
			if (arr[0].equals("user")) {
				Users user = new Users();
				user.setName(arr[1]);
					int x = Integer.parseInt(arr[2]);
					int y = Integer.parseInt(arr[3]);

					user.setCoordinates(x, y);
					model.addUser(user);
				return;
			}
		}
	}
}