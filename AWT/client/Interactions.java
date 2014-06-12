import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class Interactions implements Runnable {
	private Socket s;
	private static Scanner in;
	private static PrintWriter out;
	private int port = 8189;
	private String hostname = "194.213.104.67";
	private boolean established;

	Interactions() {
		Thread s = new Thread(this);
		s.start();
	}

	public void run() {
		establishConnect();
		if (!established)
			return;
		handMaze();
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
			System.out.println("Connecting to server");
			s = new Socket(hostname, port);
			in = new Scanner(s.getInputStream());
			out = new PrintWriter(s.getOutputStream(), true);
			out.println(ClientFrame.getName());
			established = true;
			System.out.println("Successfully connected");
			ClientFrame.hideActuators();
			ClientFrame.openGameField();
		} catch (IOException exc) {
			exc.printStackTrace();
		}
	}

	private void checkUpdates() {
		String line;
		if (in.hasNextLine()) {
			line = in.nextLine();
			String[] keywords = line.split("-");
			if (keywords[0].equals("remove")) {
				Portray.removeUser(keywords[1]);
				return;
			}
			if (keywords[0].equals("level"))
				handMaze(keywords[1]);
			else
				ClientFrame.handInfo(keywords);
		}
	}

	static void handMessage(String msg) {
		out.println("-" + msg);
	}

	static void handCoordinates(int x, int y) {
		out.println(x + "-" + y);
	}

	private void handMaze() {
		String line = "";
		int size = 30;
		boolean field[][] = new boolean[size][size];
		if (in.hasNextLine())
			line = in.nextLine();

		for (int a = 0; a < size; a++)
			for (int b = 0; b < size; b++)
				if (line.charAt((size * a) + b) == '1')
					field[a][b] = true;
				else
					field[a][b] = false;
		System.out.println("Handing field");
		Portray.setField(field);
	}

	private void handMaze(String line) {
		Portray.field = null;
		int size = 30;
		boolean field[][] = new boolean[size][size];
		for (int a = 0; a < size; a++)
			for (int b = 0; b < size; b++)
				if (line.charAt((size * a) + b) == '1')
					field[a][b] = true;
				else
					field[a][b] = false;
		System.out.println("Handing field");
		Portray.setField(field);
	}

}
