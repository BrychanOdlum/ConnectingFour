package net.brychan.connect4.Game;

import net.brychan.connect4.Util;

import java.util.HashMap;
import java.util.Random;

public class GameManager {

	private static HashMap<String, Game> games;
	private static final Random Random = new Random();

	public GameManager() {
		games = new HashMap<>();
	}

	public Game createGame() {
		String code = null;
		while (code == null) {
			String cc = Util.generateKey();
			if (!games.containsKey(cc)) {
				code = cc;
			}
		}

		Game game = new Game(code);
		games.put(code, game);

		return game;
	}

	public Game getGame(String id) {
		return games.get(id);
	}

}
