package model;
import java.util.Iterator;
import java.util.Observable;

import data.LinkedList;
import data.Users;

public class Model extends Observable {
	private LinkedList<Users> users;
	private boolean field[][];
	private int size = 30;
	private int figureSize = 10;
	private Users me;

	public Model() {
		me = new Users();
		me.setX(10);
		me.setY(10);
		users = new LinkedList<Users>();
	}

	public boolean[][] getField() {
		return field;
	}

	public void setField(String binary) {
		field = new boolean[size][size];
		for (int a = 0; a < size; a++)
			for (int b = 0; b < size; b++)
				if (binary.charAt((size * a) + b) == '1')
					field[a][b] = true;
				else
					field[a][b] = false;
		setChanged();
		notifyObservers();
	}

	public void addUser(Users user) {
		String name = user.getName();
		for (Users dweller : users) {
			if (dweller.getName().equals(name)) {
				dweller.setCoordinates(user.getX(), user.getY());
				setChanged();
				notifyObservers();
				return;
			}
		}
		users.add(user);
		setChanged();
		notifyObservers();
	}

	public void removeUser(Users user) {
		String name = user.getName();
		Iterator<Users> itr = users.iterator();
		for (Users dweller : users) {
			if (dweller.getName().equals(name)) {
				itr.remove();
				return;
			} else
				itr.next();
		}
		setChanged();
		notifyObservers();
	}

	public LinkedList<Users> getUsers() {
		return users;
	}

	public Users getInformation() {
		return me;
	}

	public int getFigureSize() {
		return figureSize;
	}

	public int getSize() {
		return size;
	}

	public void moveTo(int keyCode) {
		System.out.println(keyCode);
		int x = me.getX();
		int y = me.getY();
		if (keyCode == 37) {
			x -= figureSize;
		}
		if (keyCode == 38) {
			y -= figureSize;
		}
		if (keyCode == 39) {
			x += figureSize;
		}
		if (keyCode == 40) {
			y += figureSize;
		}
		if (!isAplicable(x, y))
			return;
		me.setX(x);
		me.setY(y);
		setChanged();
		notifyObservers();
		return;
	}

	public void setName(String name) {
		me.setName(name);
	}

	public int getX() {
		return me.getX();
	}

	public int getY() {
		return me.getY();
	}

	private boolean isAplicable(int x, int y) {
		if (x < figureSize || x >= size * figureSize)
			return false;
		if (y < figureSize || y >= size * figureSize)
			return false;
		if (field[y / figureSize][x / figureSize])
			return true;
		return false;
	}
}
