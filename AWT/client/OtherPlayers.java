import java.util.Iterator;
import java.util.NoSuchElementException;

/*
 * this class was supposed to be implementation of linkedlist algorithm, but I have no time 
 * for implementing it. I'll do it later 
 * 
 */
public class OtherPlayers {
	Linker first;
	int N;

	OtherPlayers() {
		first = null;
		N = 0;
	}

	class Linker {
		private String name;
		private int x, y;
		Linker nextPlayer;

		Linker(String name) {
			this.name = name;
		}

		void changeCoordinate(int a, int b) {
			x = a;
			y = b;
		}

		public String getName() {
			return name;
		}
	}

	Object[][] getAllInfo() {
		if (N == 0)
			return null;
		Object[][] arr = new Object[N - 1][3];
		Linker iterate = first;
		for (int a = 0; a < N; a++) {
			arr[a][0] = iterate.getName();
			arr[a][1] = iterate.x;
			arr[a][2] = iterate.y;
			iterate = iterate.nextPlayer;
		}
		return arr;
	}

	boolean playerExsists(String name) {
		Linker iterate = first;
		while (iterate != null) {
			if (iterate.getName().equals(name)) {
				return true;
			}
			iterate = iterate.nextPlayer;
		}
		return false;
	}

	void changeCoordinate(String name, int x, int y) {
		Linker iterate = first;
		while (!iterate.getName().equals(name) && iterate != null) {
			iterate = iterate.nextPlayer;
		}
		if (iterate == null) {
			System.out.println("no such player");
			return;
		}
		iterate.changeCoordinate(x, y);
	}

	void addNewPlayer(String name) {
		N++;
		Linker oldFirst = first;
		first = new Linker(name);
		first.nextPlayer = oldFirst;
	}

	void removePlayer(String name) {
		if (first.getName().equals(name)) {
			first = first.nextPlayer;
			return;
		}
		N--;
		Linker iterate = first;
		while (iterate != null && iterate.nextPlayer != null) {
			if (iterate.nextPlayer.getName() == name) {
				if (iterate.nextPlayer.nextPlayer != null)
					iterate = iterate.nextPlayer.nextPlayer;
				else
					iterate.nextPlayer = null;
			}
		}
	}

}