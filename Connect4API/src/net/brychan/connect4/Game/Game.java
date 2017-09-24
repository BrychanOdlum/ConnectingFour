package net.brychan.connect4.Game;

import net.brychan.connect4.Util;

public class Game {

	private String key;

	private Player host;
	private Player guest;

	private Player turn;
	private Player winner;

	private Board board;

	public Game(String key) {
		this.key = key;
		this.board = new Board();

		String hostKey = Util.generateKey();
		host = new Player(hostKey);

		turn = host;
	}

	public String guestJoin() {
		if (guest != null) {
			return null;
		}

		String guestKey = Util.generateKey();
		guest = new Player(guestKey);

		return guestKey;
	}

	public boolean insert(int column, Player player) {
		if (winner != null) {
			return false;
		}

		if (player != getTurn()) {
			return false;
		}

		board.insert(column, player);

		if (turn == host) {
			turn = guest;
		} else {
			turn = host;
		}

		for (int r = board.getCells()[column].length - 1; r >= 0; r--) {
			Player cell = board.getCells()[column][r];

			if (cell == player) {

				if (board.countAdjacent(column, r) >= 4) {
					winner = player;
				}

				break;
			}

		}

		return true;
	}

	public boolean detectVictory(int column, int row) {

		return board.countAdjacent(column, row) >= 4;
	}

	public String getKey() {
		return key;
	}

	public Player getHost() {
		return host;
	}

	public Player getGuest() {
		return guest;
	}

	public Player getTurn() {
		return turn;
	}

	public Player getWinner() {
		return winner;
	}

	public Player getPlayer(String key) {
		if (host.getKey().equals(key)) {
			return host;
		} else if (guest.getKey().equals(key)) {
			return guest;
		}
		return null;
	}

	public Player[][] getCells() {
		return board.getCells();
	}

}
