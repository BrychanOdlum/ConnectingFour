package net.brychan.connect4.Resources;

import com.fasterxml.jackson.databind.ObjectMapper;
import net.brychan.connect4.Game.Game;
import net.brychan.connect4.Game.Player;
import net.brychan.connect4.Json.CreateGame;
import net.brychan.connect4.Json.FetchGame;
import net.brychan.connect4.Json.JoinGame;
import net.brychan.connect4.Main;

import javax.ws.rs.*;

@Path("game")
public class GameResource {

	private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();
	private static String ERROR_JSON = "{\"Status\": -1}";

	@GET
	@Produces("application/json")
	public String rootRoute() {
		return "OK";
	}

	@GET
	@Path("create")
	@Produces("application/json")
	public String create() {
		Game game = Main.gameManager.createGame();

		try {
			CreateGame createGame = new CreateGame();
			createGame.setGame(game.getKey());
			createGame.setHost(game.getHost().getKey());

			String res = OBJECT_MAPPER.writeValueAsString(createGame);
			return res;
		} catch (Exception e) {
			// LOG HERE PLS BRYCHAN
			return ERROR_JSON;
		}
	}

	@GET
	@Path("join/{game}")
	@Produces("application/json")
	public String join(@PathParam("game") String gid) {
		Game game = Main.gameManager.getGame(gid);

		if (game == null) {
			return ERROR_JSON;
		}

		String guestKey = game.guestJoin();
		if (guestKey == null) {
			return ERROR_JSON;
		}

		try {
			JoinGame joinGame = new JoinGame();
			joinGame.setGuest(guestKey);

			String res = OBJECT_MAPPER.writeValueAsString(joinGame);
			return res;
		} catch (Exception e) {
			return ERROR_JSON;
		}
	}

	@GET
	@Path("place/{game}/{user}/{column}")
	@Produces("application/json")
	public String place(@PathParam("game") String gid, @PathParam("user") String uid, @PathParam("column") int cid) {
		Game game = Main.gameManager.getGame(gid);

		Player player = game.getPlayer(uid);
		if (player == null) {
			return ERROR_JSON;
		}

		if (game.insert(cid, player)) {
			return "{\"Status\": 1}";
		} else {
			return ERROR_JSON;
		}

	}

	@GET
	@Path("fetch/{game}/{user}")
	@Produces("application/json")
	public String fetch(@PathParam("game") String gid, @PathParam("user") String uid) {
		Game game = Main.gameManager.getGame(gid);

		Player player = game.getPlayer(uid);
		if (player == null) {
			return ERROR_JSON;
		}

		try {
			FetchGame fetchGame = new FetchGame();
			fetchGame.setGuest(game.getGuest() != null);
			fetchGame.setWinner(game.getWinner(), game.getHost());
			fetchGame.setTurn(game.getTurn() == player);
			fetchGame.setCells(game.getCells(), game.getHost());

			String res = OBJECT_MAPPER.writeValueAsString(fetchGame);
			return res;
		} catch (Exception e) {
			return ERROR_JSON;
		}
	}

	@GET
	@Path("check/{game}/{column}/{row}")
	@Produces("application/json")
	public String check(@PathParam("game") String gid, @PathParam("column") int col, @PathParam("row") int row) {
		Game game = Main.gameManager.getGame(gid);

		return game.detectVictory(col, row) ? "Win" : "Loss";
	}


}
