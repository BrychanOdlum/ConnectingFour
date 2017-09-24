package net.brychan.connect4.Game;

public class Board {

	// TODO: Change this to a map of coords to players (and add a width and height field)
	private Player[][] cells = new Player[][] {
			{ null, null, null, null, null, null },
			{ null, null, null, null, null, null },
			{ null, null, null, null, null, null },
			{ null, null, null, null, null, null },
			{ null, null, null, null, null, null },
			{ null, null, null, null, null, null },
			{ null, null, null, null, null, null }
	};

	public boolean insert(int column, Player player) {
		for (int row = 0; row < cells[column].length; row++) {
			if (cells[column][row] == null) {
				cells[column][row] = player;
				return true;
			}
		}

		return false; // Error'd out? :'( Should probably just not use a for loop above...
	}

	public Player[][] getCells() {
		return cells;
	}

	public Player getAdjacentCell(int col, int row, int cdisp, int rdisp) {
		int ncol = col + cdisp;
		int nrow = row + rdisp;
		if (ncol < 0 || ncol >= cells.length) {
			return null;
		}
		if (nrow < 0 || nrow >= cells[0].length) {
			return null;
		}

		return cells[ncol][nrow];
	}

	public int countAdjacent(int col, int row) {
		Player player = cells[col][row];
		int maxCount = 0;
		int c, r, count;

		// Horizontal
		c = col;
		r = row;
		count = 1;
		while (getAdjacentCell(c, r, -1, 0) != null && getAdjacentCell(c, r, -1, 0) == player) {
			c--;
		}
		while (getAdjacentCell(c, r, 1, 0) != null && getAdjacentCell(c, r, 1, 0) == player) {
			c++;
			count++;
		}
		if (count > maxCount) {
			maxCount = count;
		}

		// Vertical
		c = col;
		r = row;
		count = 1;
		while (getAdjacentCell(c, r, 0, -1) != null && getAdjacentCell(c, r, 0, -1) == player) {
			r--;
		}
		while (getAdjacentCell(c, r, 0, 1) != null && getAdjacentCell(c, r, 0, 1) == player) {
			r++;
			count++;
		}
		if (count > maxCount) {
			maxCount = count;
		}

		// Diag1
		c = col;
		r = row;
		count = 1;
		while (getAdjacentCell(c, r, -1, -1) != null && getAdjacentCell(c, r, -1, -1) == player) {
			c--;
			r--;
		}
		while (getAdjacentCell(c, r, 1, 1) != null && getAdjacentCell(c, r, 1, 1) == player) {
			c++;
			r++;
			count++;
		}
		if (count > maxCount) {
			maxCount = count;
		}

		// Diag2
		c = col;
		r = row;
		count = 1;
		while (getAdjacentCell(c, r, -1, 1) != null && getAdjacentCell(c, r, -1, 1) == player) {
			c--;
			r++;
		}
		while (getAdjacentCell(c, r, 1, -1) != null && getAdjacentCell(c, r, 1, -1) == player) {
			c++;
			r--;
			count++;
		}
		if (count > maxCount) {
			maxCount = count;
		}

		return maxCount;
	}


}
