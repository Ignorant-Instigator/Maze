import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
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
			out.println("level-"+CreateServer.getBinaryField());
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