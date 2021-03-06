import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


public class Server {
	private static ServerSocket socket;
	private static List<Socket> users = new ArrayList<>();
	private static MazeGenerator maze = new MazeGenerator(30);
	private static boolean field[][];
	private static String binaryField;

	Server() {
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
