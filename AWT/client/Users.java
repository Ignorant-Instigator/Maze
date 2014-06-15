public class Users {
	private String name;
	private int x, y;

	void setCoordinates(int x, int y) {
		if (x < 0 || y < 0)
			throw new IllegalArgumentException("Wrong coordinate");
		this.x = x;
		this.y = y;
	}

	void setName(String name) {
		this.name = name;
	}

	int getX() {
		return x;
	}

	int getY() {
		return y;
	}

	String getName() {
		return name;
	}
}
