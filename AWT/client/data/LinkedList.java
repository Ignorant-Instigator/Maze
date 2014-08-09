package data;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class LinkedList<Item> implements Iterable<Item> {
	Node first = null;

	class Node {
		Item item;
		Node next;
		Node prev;
	}

	public void add(Item item) {
		Node oldFirst = first;
		first = new Node();
		first.item = item;
		first.next = oldFirst;
		if (oldFirst != null) {
			oldFirst.prev = first;
		}
	}

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
			if (current.prev == null) {
				first = current.next;
				return;
			}
			current.prev.next = current.next;
			if (current.prev.prev != null)
				current.prev.prev = current.next;
			else
				current.prev = null;
		}
	}
}