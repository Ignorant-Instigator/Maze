import java.util.Iterator;
import java.util.NoSuchElementException;

/*
 * this class was supposed to be implementation of linkedlist algorithm, but I have no time 
 * for implementing it. I'll do it later 
 * 
 */
public class LinkedList<Item> implements Iterable<Item> {
	int S;
	Node first;

	LinkedList() {
		S = 0;
		first = null;
	}

	class Node {
		// private String name;
		// private int x, y;
		Item item;
		Node next;

	}

	//
	// Object[][] getAllInfo() {
	// if (S == 0)
	// return null;
	// Object[][] arr = new Object[S - 1][3];
	// Node iterate = first;
	// for (int a = 0; a < S; a++) {
	// arr[a][0] = iterate.getName();
	// arr[a][1] = iterate.x;
	// arr[a][2] = iterate.y;
	// iterate = iterate.nextPlayer;
	// }
	// return arr;
	// }

	// boolean playerExsists(String name) {
	// Node iterate = first;
	// while (iterate != null) {
	// if (iterate.getName().equals(name)) {
	// return true;
	// }
	// iterate = iterate.nextPlayer;
	// }
	// return false;
	// }
	//
	// void changeCoordinate(String name, int x, int y) {
	// Node iterate = first;
	// while (!iterate.getName().equals(name) && iterate != null) {
	// iterate = iterate.nextPlayer;
	// }
	// if (iterate == null) {
	// System.out.println("no such player");
	// return;
	// }
	// iterate.changeCoordinate(x, y);
	// }
	//
	// void addNewPlayer(String name) {
	// S++;
	// Node oldFirst = first;
	// first = new Node(name);
	// first.nextPlayer = oldFirst;
	// }
	//
	// void removePlayer(String name) {
	// if (first.getName().equals(name)) {
	// first = first.nextPlayer;
	// return;
	// }
	// S--;
	// Node iterate = first;
	// while (iterate != null && iterate.nextPlayer != null) {
	// if (iterate.nextPlayer.getName() == name) {
	// if (iterate.nextPlayer.nextPlayer != null)
	// iterate = iterate.nextPlayer.nextPlayer;
	// else
	// iterate.nextPlayer = null;
	// }
	// }
	// }

	public Iterator<Item> iterator() {
		return new ListIterator();
	}

	class ListIterator implements Iterator<Item> {
		Node current = first;

		public boolean hasNext() {
			return current != null;
		}

		public Item next() {
			if (!hasNext())
				throw new NoSuchElementException();
			Item item = current.item;
			current = current.next;
			return item;
		}

		public void remove() {
			throw new UnsupportedOperationException();
		}

	}
}