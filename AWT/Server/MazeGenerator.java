public class MazeGenerator {
	private boolean field[][];
	private int S;
	private int lastM[][];
	private int cA, cB;
	private int moves[] = { -1, 1 };
	boolean north, east, south, west;
	int counter = 0;
	int end;

	MazeGenerator(int size) {
		field = new boolean[size][size];
		S = size;
		lastM = new int[S * S][2];
	}

	void openSite() {
		int b = (int) (Math.random() * field.length - 1);
		int a = (int) (Math.random() * field.length - 1);
		if (a == 0)
			a++;
		if (b == 0)
			b++;
		lastM[counter][0] = 0;
		lastM[counter][1] = b;
		field[a][b] = true;
		openSite(0, b);
		end();
	}

	private void openSite(int a, int b) {
		if (north && south && east && west) {
			if (counter < 1) {
				return;
			}
			a = lastM[counter - 1][0];
			b = lastM[counter - 1][1];
			counter--;
			north = east = south = west = false;
			openSite(a, b);
		}
		cA = a;
		cB = b;
		int choice = (int) (Math.random() * 2);
		if (choice == 0)
			a += moves[(int) (Math.random() * 2)];
		else
			b += moves[(int) (Math.random() * 2)];
		if (a > cA)
			south = true;
		else
			north = true;
		if (b > cB)
			east = true;
		else
			west = true;
		if (goodNeighbors(a, b)) {
			north = east = south = west = false;
			lastM[counter][0] = a;
			lastM[counter][1] = b;
			counter++;
			field[a][b] = true;
			openSite(a, b);
		} else {
			openSite(cA, cB);
		}
	}

	private void end() {
		int b = (int) (Math.random() * field.length - 1);
		while (!field[S - 2][b]) {
			b = (int) (Math.random() * field.length - 1);
		}
		field[S - 1][b] = true;
	}

	private boolean goodNeighbors(int a, int b) {
		if (!isAllowed(a, b))
			return false;
		if (a + 1 != cA)
			if (field[a + 1][b])
				return false;

		if (b + 1 != cB)
			if (field[a][b + 1])
				return false;

		if (a - 1 != cA)
			if (field[a - 1][b])
				return false;
		if (b - 1 != cB)
			if (field[a][b - 1])
				return false;
		return true;
	}

	boolean[][] getField() {
		return field;
	}

	private boolean isAllowed(int a, int b) {
		if (a < 1 || b < 1)
			return false;
		if (a >= S - 1 || b >= S - 1)
			return false;
		if (field[a][b])
			return false;
		return true;
	}
}
