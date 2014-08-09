import java.awt.CardLayout;
import java.awt.Color;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.Observable;
import java.util.Observer;

import javax.swing.*;

import model.Model;
import controller.Controller;
import controller.LoginLogic;
import view.LoginForm;
import view.View;

public class MainClass implements Observer {
	private JFrame frame;
	private View view;
	private Model model;
	private JPanel card;

	MainClass() {
		setFrame();
		setActuators();
		frame.setVisible(true);
	}

	private void setFrame() {
		frame = new JFrame("View");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(305, 327);
		frame.setResizable(false);
		frame.setFocusable(true);
		frame.setLocationRelativeTo(null);
		frame.getContentPane().setBackground(Color.GRAY);
	}

	private void setActuators() {
		card = new JPanel(new CardLayout());
		model = new Model();
		view = new View(model);
		Controller control = new Controller(model, view);
		LoginForm login = new LoginForm();
		LoginLogic logLogic = new LoginLogic(login);
		logLogic.addObserver(control);
		logLogic.addObserver(this);
		card.add(login, "login");
		card.add(view, "view");
		frame.add(card);
	}

	public void update(Observable arg0, Object arg1) {
		card.setFocusable(true);
		CardLayout cl = (CardLayout) (card.getLayout());
		cl.show(card, "view");
		// frame.setSize(model.getSize() * 10 + 5, model.getSize() * 10 + 27);
		// frame.add(view);
		// frame.setVisible(true);
	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				new MainClass();
			}
		});
	}

}
