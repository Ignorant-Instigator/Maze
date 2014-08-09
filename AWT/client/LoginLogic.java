import java.awt.Event;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Observable;
import java.util.Observer;

public class LoginLogic extends Observable {
	LoginForm view;

	public LoginLogic(LoginForm view) {
		this.view = view;
		setListener();
	}

	private void setListener() {
		view.getInput().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String text = view.getText();
				if (vertifyText(text)) {
					view.setVisible(false);
					setChanged();
					notifyObservers(text);
				}
			}
		});
	}

	private boolean vertifyText(String text) {
		if (text.length() < 5)
			return false;
		return true;
	}

}
