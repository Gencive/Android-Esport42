package com.pkesslas.esport42.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Pierre-Elie on 13/05/15.
 */
public class Posts {
	@SerializedName("id")
	private int id;
	@SerializedName("author")
	private User author;
	@SerializedName("title")
	private String title;
	@SerializedName("text")
	private String text;
	@SerializedName("resume")
	private String resume;
	@SerializedName("created_at")
	private String created_at;
	@SerializedName("updated_at")
	private String updated_at;
	@SerializedName("image")
	private String image;
	@SerializedName("is_landing")
	private boolean is_landing;

	public Posts () {
	}

	public int getId() {
		return id;
	}

	public User getAuthor() {
		return author;
	}

	public String getTitle() {
		return title;
	}

	public String getResume() {
		return resume;
	}

	public String getText() {
		return text;
	}

	public String getCreated_at() {
		return created_at;
	}

	public String getUpdated_at() {
		return updated_at;
	}

	public String getImage() {
		return image;
	}

	public boolean isIs_landing() {
		return is_landing;
	}
}
