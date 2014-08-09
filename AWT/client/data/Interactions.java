package data;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Observable;
import java.util.Scanner;

public class Interactions extends Observable implements Runnable {
	private PrintWriter out;
	private Scanner in;
	private Socket s;
	private int port = 8189;
	private String hostname = "127.0.0.1";

	public Interactions() {
		Thread s = new Thread(this);
		s.start();
	}

	public void run() {
		establishConnect();
			while (true) {
				try {
					Thread.sleep(50);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				checkUpdates();
			}
	}

	private void establishConnect() {
		try {
			s = new Socket(hostname, port);
			in = new Scanner(s.getInputStream());
			out = new PrintWriter(s.getOutputStream(), true);
		} catch (IOException exc) {
			exc.printStackTrace();
		}
	}

	private void checkUpdates() {
		String line;
		if (in.hasNextLine()) {
			line = in.nextLine();
			String[] keywords = line.split("-");
			setChanged();
			notifyObservers(keywords);
		}
	}

	public void handMessage(String msg) {
		while (s == null || s.isClosed()) {
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		out.println(msg);
	}
	// private Socket s;
	// private static Scanner in;
	// private static PrintWriter out;
	// private int port = 8189;
	// private String hostname = "127.0.0.1";
	// private boolean established;
	//
	// Interactions() {
	// Thread s = new Thread(this);
	// s.start();
	// }
	//
	// public void run() {
	// establishConnect();
	// if (!established)
	// return;
	// handMaze();
	// while (true) {
	// try {
	// Thread.sleep(50);
	// } catch (InterruptedException e) {
	// e.printStackTrace();
	// }
	// checkUpdates();
	// }
	// }
	//
	// private void establishConnect() {
	// try {
	// s = new Socket(hostname, port);
	// in = new Scanner(s.getInputStream());
	// out = new PrintWriter(s.getOutputStream(), true);
	// out.println(ClientFrame.getName());
	// established = true;
	// ClientFrame.hideActuators();
	// ClientFrame.openGameField();
	// } catch (IOException exc) {
	// exc.printStackTrace();
	// }
	// }
	//
	// private void checkUpdates() {
	// String line;
	// if (in.hasNextLine()) {
	// line = in.nextLine();
	// String[] keywords = line.split("-");
	// if (keywords[0].equals("level")) {
	// handMaze(keywords[1]);
	// return;
	// }
	// if (keywords[0].equals("remove")) {
	// Users user = new Users();
	// user.setName(keywords[1]);
	// Portray.removeUser(user);
	// return;
	// }
	// if (keywords[0].equals("user")) {
	// Users user = new Users();
	// user.setName(keywords[1]);
	// int x = Integer.parseInt(keywords[2]);
	// int y = Integer.parseInt(keywords[3]);
	// user.setCoordinates(x, y);
	// Portray.handInformation(user);
	// return;
	// }
	// }
	// }
	//
	// static void handMessage(String msg) {
	// out.println("-" + msg);
	// }
	//
	// static void handCoordinates(int x, int y) {
	// out.println(x + "-" + y);
	// }
	//
	// private void handMaze() {
	// String line = "";
	// int size = 30;
	// boolean field[][] = new boolean[size][size];
	// if (in.hasNextLine())
	// line = in.nextLine();
	//
	// for (int a = 0; a < size; a++)
	// for (int b = 0; b < size; b++)
	// if (line.charAt((size * a) + b) == '1')
	// field[a][b] = true;
	// else
	// field[a][b] = false;
	// Portray.setField(field);
	// }
	//
	// private void handMaze(String line) {
	// Portray.field = null;
	// int size = 30;
	// boolean field[][] = new boolean[size][size];
	// for (int a = 0; a < size; a++)
	// for (int b = 0; b < size; b++)
	// if (line.charAt((size * a) + b) == '1')
	// field[a][b] = true;
	// else
	// field[a][b] = false;
	// Portray.setField(field);
	// }

}
