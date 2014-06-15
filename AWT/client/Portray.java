import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.geom.Rectangle2D;
import java.util.Iterator;

import javax.swing.AbstractAction;
import javax.swing.ActionMap;
import javax.swing.InputMap;
import javax.swing.JComponent;
import javax.swing.KeyStroke;

public class Portray extends JComponent {
	static boolean[][] field;
	private static int x, y, lx, ly;
	private Human me;
	private static LinkedList<Users> collection;
	private String name;

	Portray(String name) {
		this.name = name;
		setSize(305, 327); // crucial line!
		collection = new LinkedList<Users>();
		me = new Human();
	}

	static void handInformation(Users info) {
		String name = info.getName();
		for (Users user : collection) {
			if (user.getName().equals(name)) {
				user.setCoordinates(info.getX(), info.getY());
				return;
			}
		}
		collection.add(info);
	}

	static void removeUser(Users info) {
		String name = info.getName();
		Iterator itr = collection.iterator();
		for (Users user : collection) {
			if (user.getName().equals(name)) {
				itr.remove();
				return;
			} else
				itr.next();
		}
	}

	public void paint(Graphics g) {
		if (field == null)
			return;
		Graphics2D g2 = (Graphics2D) g;
		for (int a = 0; a < field.length; a++)
			for (int b = 0; b < field[0].length; b++) {
				if (field[a][b])
					g2.setColor(new Color(107, 107, 107));
				else
					g2.setColor(new Color(43, 43, 43));
				g2.fill(new Rectangle2D.Double(b * 10, a * 10, 10, 10));
			}
		if (field[y / 10][x / 10]) {
			Rectangle2D man = new Rectangle2D.Double(x, y, 10, 10);
			g.setColor(Color.GREEN);
			g.drawString(name, x + 10, y);
			g2.setColor(Color.WHITE);
			g2.fill(man);
			lx = x;
			ly = y;
		} else {
			x = lx;
			y = ly;
		}
		g2.setColor(Color.RED);
		for (Users player : collection) {
			g2.fill(new Rectangle2D.Double(player.getX(), player.getY(), 10, 10));
			g.drawString(player.getName(), player.getX() + 10, player.getY());

		}
	}

	private static void spawn() {
		lx = ly = 1;
		do {
			lx = (int) (Math.random() * 30) * 10;
			ly = (int) (Math.random() * 30) * 10;
		} while (!field[ly / 10][lx / 10]);
		if (ly / 10 == 0)
			ly += 10;
		if (lx / 10 == 0)
			lx += 10;
		x = lx;
		y = ly;
		Interactions.handCoordinates(x, y);
	}

	class Human implements Runnable {
		private int step = 10;
		private InputMap im = getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
		private ActionMap am = getActionMap();

		Human() {
			bindKeys();
			Thread s = new Thread(this);
			s.start();
		}

		public void run() {
			while (true) {
				if (ly == 290) {
					Interactions.handMessage("finished");
					spawn();
				}
				repaint();
				try {
					Thread.sleep(16);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}

		}

		private void bindKeys() {
			im.put(KeyStroke.getKeyStroke("UP"), "UP");
			im.put(KeyStroke.getKeyStroke("RIGHT"), "RIGHT");
			im.put(KeyStroke.getKeyStroke("DOWN"), "DOWN");
			im.put(KeyStroke.getKeyStroke("LEFT"), "LEFT");
			am.put("UP", new Arrows("UP"));
			am.put("RIGHT", new Arrows("RIGHT"));
			am.put("DOWN", new Arrows("DOWN"));
			am.put("LEFT", new Arrows("LEFT"));
		}

		private class Arrows extends AbstractAction {
			private String cmd;

			public Arrows(String cmd) {
				this.cmd = cmd;
			}

			public void actionPerformed(ActionEvent e) {
				if (cmd == "UP") {
					y -= step;
					if (field[y / 10][x / 10])
						Interactions.handCoordinates(x, y);
					return;
				}
				if (cmd == "RIGHT") {
					x += step;
					if (field[y / 10][x / 10])
						Interactions.handCoordinates(x, y);
					return;
				}
				if (cmd == "DOWN") {
					y += step;
					if (field[y / 10][x / 10])
						Interactions.handCoordinates(x, y);
					return;
				}
				if (cmd == "LEFT") {
					x -= step;
					if (field[y / 10][x / 10])
						Interactions.handCoordinates(x, y);
					return;
				}
			}
		}
	}

	public static void setField(boolean array[][]) {
		field = array;
		spawn();
	}
}
