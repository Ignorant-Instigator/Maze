import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class View extends JComponent implements Observer {
	private Model model;

	public View(Model model) {

		model.addObserver(this);
		this.model = model;
	}

	public void paint(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
		boolean field[][] = model.getField();
		int figureSize = model.getFigureSize();
		if (field == null)
			return;
		for (int a = 0; a < field.length; a++)
			for (int b = 0; b < field[a].length; b++) {
				if (field[a][b])
					g2.setColor(new Color(107, 107, 107));
				else
					g2.setColor(new Color(43, 43, 43));
				g2.fill(new Rectangle2D.Double(b * figureSize, a * figureSize,
						figureSize, figureSize));
			}

		for (Users player : model.getUsers()) {
			g2.setColor(Color.RED);
			g2.fill(new Rectangle2D.Double(player.getX(), player.getY(),
					figureSize, figureSize));
			g2.setColor(Color.WHITE);
			g.drawString(player.getName(), player.getX() + figureSize,
					player.getY());
		}

		Users me = model.getInformation();
		g2.drawString(me.getName(), me.getX() + figureSize, me.getY());
		g2.setColor(Color.GREEN);
		Rectangle2D myFigure = new Rectangle2D.Double(me.getX(), me.getY(),
				figureSize, figureSize);
		g2.fill(myFigure);
	}

	public void update(Observable o, Object arg) {
		repaint();
	}
}
