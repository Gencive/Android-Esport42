package com.pkesslas.esport42.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Pierre-Elie on 13/05/15.
 */
public class Event {
	@SerializedName("id")
	private int id;
	@SerializedName("name")
	private String name;
	@SerializedName("nbteams")
	private int nbteams;
	@SerializedName("player_per_team")
	private int playerPerTeam;
	@SerializedName("max_player")
	private int maxPlayer;
	@SerializedName("start_reg")
	private String startReg;
	@SerializedName("end_reg")
	private String endReg;
	@SerializedName("start_tou")
	private String startEvent;
	@SerializedName("end_tou")
	private String endEvent;
	@SerializedName("price")
	private int cashPrice;
	@SerializedName("game_name")
	private String game_name;
	@SerializedName("tag")
	private String tag;
	@SerializedName("place")
	private String place;

	public Event() {
	}

	public int getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public int getNbteams() {
		return nbteams;
	}

	public int getPlayerPerTeam() {
		return playerPerTeam;
	}

	public int getMaxPlayer() {
		return maxPlayer;
	}

	public String getStartReg() {
		return startReg;
	}

	public String getEndReg() {
		return endReg;
	}

	public String getStartEvent() {
		return startEvent;
	}

	public String getEndEvent() {
		return endEvent;
	}

	public int getCashPrice() {
		return cashPrice;
	}

	public String getGameName() {
		return game_name;
	}

	public String getTag() {
		return tag;
	}

	public String getPlace() {
		return place;
	}
}
