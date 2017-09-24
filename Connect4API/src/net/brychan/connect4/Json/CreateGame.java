package net.brychan.connect4.Json;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

public class CreateGame {

	@JsonProperty("GameID")
	private String gameID;

	@JsonProperty("HostID")
	private String hostID;

	@JsonIgnore
	public void setGame(String gameID) {
		this.gameID = gameID;
	}

	@JsonIgnore
	public void setHost(String hostID) {
		this.hostID = hostID;
	}

}
