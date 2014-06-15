import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class Player implements Runnable {
	private Socket mySocket;
	private Scanner in;
	private PrintWriter out;
	private String name;
	private boolean done = false;

	Player(Socket sock) {
		mySocket = sock;
	}

	public void run() {
		try {
			in = new Scanner(mySocket.getInputStream());
			out = new PrintWriter(mySocket.getOutputStream(), true);
			name = in.nextLine();
			out.println(CreateServer.getBinaryField());
			while (in.hasNextLine() && !done) {
				String tmp = in.nextLine();
				if (tmp.equals("-finished")) {
					CreateServer.generateNewField();

				} else if (tmp.equals("-leave")) {
					{
						done = true;
						CreateServer.notify("remove-" + name, mySocket);
						CreateServer.removeUser(mySocket);
					}
				} else
					CreateServer.notify("user-" + name + "-" + tmp, mySocket);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			in.close();
			out.close();
		}
	}

}

public class CreateServer {
	private static ServerSocket socket;
	private static List<Socket> users = new ArrayList<>();
	private static MazeGenerator maze = new MazeGenerator(30);
	private static boolean field[][];
	private static String binaryField;

	CreateServer() {
		maze.openSite();
		field = maze.getField();
		parseFieldToBinary();
		waitForNewUser();
	}

	static void notify(String coordinates, Socket exception) throws IOException {
		PrintWriter out;
		for (Socket sock : users) {
			if (sock != exception) {
				out = new PrintWriter(sock.getOutputStream(), true);
				out.println(coordinates);
			}
		}
	}

	static void generateNewField() {
		maze = new MazeGenerator(30);
		maze.openSite();
		field = maze.getField();
		parseFieldToBinary();
		try {
			notify("level-" + binaryField, null);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private static void parseFieldToBinary() {
		binaryField = "";
		for (int a = 0; a < 30; a++)
			for (int b = 0; b < 30; b++) {
				if (field[a][b])
					binaryField += "1";
				else
					binaryField += "0";
			}
	}

	static String getBinaryField() {
		return binaryField;
	}

	static void removeUser(Socket sock) {
		int index = users.indexOf(sock);
		users.remove(index);
	}

	private void addUser(Socket sock) {
		users.add(sock);
	}

	private void waitForNewUser() {
		try {
			socket = new ServerSocket(8189);
			while (true) {
				Socket user = socket.accept();
				addUser(user);
				Runnable r = new Player(user);
				Thread t = new Thread(r);
				t.start();
			}
		} catch (SocketException e) {
			System.err.println("Loop was interupted on: waitForUser()");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	static void closeServer() {
		try {
			for (Socket tmp : users) {
				PrintWriter temp = new PrintWriter(tmp.getOutputStream(), true);
				temp.println("closing");
				temp.close();
				tmp.close();
			}
			socket.close();
		} catch (SocketException e) {
			System.out.println("Server socket is now closed");
		} catch (IOException e) {

			e.printStackTrace();
		}
	}
}
