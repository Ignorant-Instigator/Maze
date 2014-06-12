import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.List;

import javax.swing.AbstractAction;
import javax.swing.ActionMap;
import javax.swing.InputMap;
import javax.swing.JComponent;
import javax.swing.KeyStroke;

public class Portray extends JComponent {
	static boolean[][] field;
	private static int x, y, lx, ly;
	private Human me;
	private static OtherPlayers information;
	private String name;
	private static List<String> names = new ArrayList<>();
	private static List<Integer> xU = new ArrayList<>();
	private static List<Integer> yU = new ArrayList<>();

	Portray(String name) {
		this.name = name;
		setSize(305, 327); // crucial line!
		information = new OtherPlayers();
		me = new Human();
		System.out.println("Adjusting game field");
	}

	static void handInformation(String[] rawInfo) {
		int index;
		if (!names.contains(rawInfo[0])) {
			names.add(rawInfo[0]);
			xU.add(Integer.parseInt(rawInfo[1]));
			yU.add(Integer.parseInt(rawInfo[2]));
		} else {
			index = names.indexOf(rawInfo[0]);
			xU.set(index, Integer.parseInt(rawInfo[1]));
			yU.set(index, Integer.parseInt(rawInfo[2]));
		}
	}

	static void removeUser(String nick) {
		int index;
		if (names.contains(nick)) {
			index = names.indexOf(nick);
			names.remove(index);
			xU.remove(index);
			yU.remove(index);
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
		for (int a = 0; a < names.size(); a++) {
			g2.fill(new Rectangle2D.Double(xU.get(a), yU.get(a), 10, 10));
			g.drawString(names.get(a), xU.get(a) + 10, yU.get(a));

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
	}

	class Human implements Runnable {
		private int step = 10;
		private InputMap im = getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
		private ActionMap am = getActionMap();

		Human() {
			System.out.println("Human class is set");
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
		System.out.println("Recieving field");
		field = array;
		spawn();
	}
}
