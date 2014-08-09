public class Users {
	private String name;
	private int x, y;

	public void setCoordinates(int x, int y) {
		if (x < 0 || y < 0)
			throw new IllegalArgumentException("Wrong coordinate");
		this.x = x;
		this.y = y;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public void setX(int x) {
		this.x = x;
	}

	public void setY(int y) {
		this.y = y;
	}

	public String getName() {
		return name;
	}

	public String toString() {
		String info = "Name: " + name + "| X:" + x + "| Y:" + y;
		return info;
	}
}
