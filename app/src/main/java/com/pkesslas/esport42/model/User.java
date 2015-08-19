package com.pkesslas.esport42.model;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

/**
 * Created by Pierre-Elie on 26/02/15.
 */
public class User {
	@SerializedName("id")
	private int id;
	@SerializedName("username")
	private String username;
	@SerializedName("email")
	private String email;
	@SerializedName("first_name")
	private String firstName;
	@SerializedName("last_name")
	private String lastName;
	@SerializedName("address")
	private String address;
	@SerializedName("birthday")
	private Date birthday;
	@SerializedName("nationality")
	private String nationality;
	@SerializedName("phone")
	private String phone;
	@SerializedName("token")
	private String token;
	@SerializedName("is_admin")
	private Boolean admin = false;
	@SerializedName("is_staff")
	private Boolean staff = false;

	public User() {

	}

	public User(int id, String username, String email, String firstName, String lastName,
				String address, Date birthday, String nationality, String phone, String token, boolean admin, boolean staff) {
		this.id = id;
		this.username = username;
		this.nationality = nationality;
		this.email = email;
		this.firstName = firstName;
		this.lastName = lastName;
		this.address = address;
		this.birthday = birthday;
		this.token = token;
		this.phone = phone;
		this.admin = admin;
		this.staff = staff;
	}

	public int getId() {
		return this.id;
	}

	public String getUsername() {
		return this.username;
	}

	public String getEmail() {
		return this.email;
	}

	public String getFirstName() {
		return this.firstName;
	}

	public String getLastName() {
		return this.lastName;
	}

	public Date getBirthday() {
		return this.birthday;
	}

	public String getAddress() {
		return this.address;
	}

	public String getNationality() {
		return this.nationality;
	}

	public String getPhone() {
		return this.phone;
	}

	public String getToken() {
		return token;
	}

	public Boolean getAdmin() {
		return admin;
	}

	public Boolean getStaff() {
		return staff;
	}
}
