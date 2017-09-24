package net.brychan.connect4.Json;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import net.brychan.connect4.Game.Player;

public class FetchGame {

	@JsonProperty("GuestPlayer")
	private boolean guestPlayer;

	@JsonProperty("Turn")
	private boolean ourTurn;

	@JsonProperty("Winner")
	private int winner;

	@JsonProperty("Cells")
	private int[][] cells;

	@JsonIgnore
	public void setGuest(boolean guestPlayer) {
		this.guestPlayer = guestPlayer;
	}

	@JsonIgnore
	public void setWinner(Player winner, Player host) {
		this.winner = winner == host ? 2 : winner != null ? 1 : 0;
	}

	@JsonIgnore
	public void setTurn(boolean ourTurn) {
		this.ourTurn = ourTurn;
	}

	@JsonIgnore
	public void setCells(Player[][] cells, Player host) {
		int[][] intCells = new int[cells.length][];
		for (int c = 0; c < cells.length; c++) {
			Player[] column = cells[c];
			int[] intColumn = new int[column.length];
			for (int r = 0; r < cells[c].length; r++) {
				intColumn[r] = column[r] == host ? 2 : column[r] != null ? 1 : 0;
			}
			intCells[c] = intColumn;
		}
		this.cells = intCells;
	}

}
