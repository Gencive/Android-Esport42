package com.pkesslas.esport42.model.API;

import com.pkesslas.esport42.model.Event;
import com.pkesslas.esport42.model.Posts;
import com.pkesslas.esport42.model.User;

import java.util.ArrayList;
import java.util.HashMap;

import retrofit.Callback;
import retrofit.http.Body;
import retrofit.http.DELETE;
import retrofit.http.FieldMap;
import retrofit.http.FormUrlEncoded;
import retrofit.http.GET;
import retrofit.http.Multipart;
import retrofit.http.PATCH;
import retrofit.http.POST;
import retrofit.http.Part;
import retrofit.http.Path;
import retrofit.mime.TypedFile;

/**
 * Created by Pierre-Elie on 24/02/15.
 */
public interface Esport42API {

	@POST("/login")
	void login(@Body HashMap<String, String> data, Callback<User> user);

	@POST("/accounts")
	void signin(@Body HashMap<String, String> data, Callback<User> user);

	@PATCH("/accounts/{id}")
	void updateUser(@Path("id") int id, @Body HashMap<String, String> data, Callback<User> user);

	@GET("/tournoi")
	void getEvent(Callback<ArrayList<Event>> event);

	@GET("/tournoi/{id}")
	void getEventById(@Path("id") int id, Callback<Event> event);

	@POST("/logout")
	void logout(Callback<String> callback);

	@GET("/posts")
	void posts(Callback<ArrayList<Posts>> posts);

	@Multipart
	@POST("/posts")
	void posts(@Part("image") TypedFile image, @Part("title") String title,
			   @Part("text") String text, @Part("resume") String resume,
			   @Part("is_landing") boolean isLanding, Callback<Posts> posts);

	@DELETE("/posts/{id}")
	void postsDelete(@Path("id") int id);

	@PATCH("/posts/{id}")
	void updatePosts(@Body HashMap<String, String> data, @Path("id") int id, Callback<Posts> post);
}
