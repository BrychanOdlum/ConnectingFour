package net.brychan.connect4;

import net.brychan.connect4.Game.GameManager;
import net.brychan.connect4.Resources.GameResource;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;
import java.util.HashSet;
import java.util.Set;

@ApplicationPath("/")
public class Main extends Application {

	public static GameManager gameManager;

	public Main() {
		gameManager = new GameManager();
	}

	@Override
	public Set<Class<?>> getClasses() {
		HashSet h = new HashSet<Class<?>>();

		h.add(GameResource.class);

		return h;
	}

}
