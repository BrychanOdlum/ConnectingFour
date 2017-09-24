package net.brychan.connect4.Json;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

public class JoinGame {

	@JsonProperty("GuestID")
	private String guestID;

	@JsonIgnore
	public void setGuest(String guestID) {
		this.guestID = guestID;
	}

}
