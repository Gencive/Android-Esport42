package com.pkesslas.esport42.helper;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.pkesslas.esport42.Esport42;
import com.pkesslas.esport42.model.User;

import java.util.Date;

/**
 * Created by Pierre-Elie on 24/02/15.
 */
public class UserHelper {

	private static final int PRIVATE_MODE = 0;
	private static final String PREF_KEY = "user";
	private static final String ID_KEY = "id";
	private static final String IS_LOGGED = "is_logged";
	private static final String FIRSTNAME_KEY = "firstname";
	private static final String LASTNAME_KEY = "lastname";
	private static final String ADDRESS_KEY = "address";
	private static final String BIRTHDAY_KEY = "birthday";
	private static final String NATIONALITY_KEY = "nationality";
	private static final String ADMIN_KEY = "admin";
	private static final String STAFF_KEY = "staff";
	private static final String USERNAME_KEY = "username";
	private static final String TOKEN_KEY = "token";
	private static final String EMAIL_KEY = "email";
	private static final String PHONE_KEY = "number";

	private Context context;
	private SharedPreferences preferences;
	private SharedPreferences.Editor editor;

	private User user;

	public UserHelper(Context context) {
		this.context = context;
		preferences = context.getSharedPreferences(PREF_KEY, PRIVATE_MODE);
		editor = preferences.edit();
	}

	public void logUser(int id, String username, String email, String firstName, String lastName,
						String address, Date birthday, String nationality, String phone, String token, boolean admin, boolean staff)  {
		editor.putInt(ID_KEY, id);
		editor.putString(USERNAME_KEY, username);
		editor.putString(EMAIL_KEY, email);
		editor.putString(PHONE_KEY, phone);
		editor.putString(FIRSTNAME_KEY, firstName);
		editor.putString(LASTNAME_KEY, lastName);
		editor.putString(ADDRESS_KEY, address);
		editor.putString(BIRTHDAY_KEY, birthday.toString());
		editor.putString(NATIONALITY_KEY, nationality);
		editor.putString(TOKEN_KEY, token);
		editor.putBoolean(ADMIN_KEY, admin);
		editor.putBoolean(STAFF_KEY, staff);
		editor.putBoolean(IS_LOGGED, true);
		editor.commit();
	}

	public void logUser(User user) {
		this.user = user;
		editor.putInt(ID_KEY, user.getId());
		editor.putString(USERNAME_KEY, user.getUsername());
		editor.putString(EMAIL_KEY, user.getEmail());
		editor.putString(PHONE_KEY, user.getPhone());
		editor.putString(FIRSTNAME_KEY, user.getFirstName());
		editor.putString(LASTNAME_KEY, user.getLastName());
		editor.putString(ADDRESS_KEY, user.getAddress());
//		editor.putString(BIRTHDAY_KEY, user.getBirthday().toString());
		editor.putString(NATIONALITY_KEY, user.getNationality());
		editor.putString(TOKEN_KEY, user.getToken());
		editor.putBoolean(ADMIN_KEY, user.getAdmin());
		editor.putBoolean(STAFF_KEY, user.getStaff());
		editor.putBoolean(IS_LOGGED, true);
		editor.commit();
	}

	public User getUser() {
		if (user != null) {
			return user;
		}
		user = new User(
				preferences.getInt(ID_KEY, 0),
				preferences.getString(USERNAME_KEY, null),
				preferences.getString(EMAIL_KEY, null),
				preferences.getString(FIRSTNAME_KEY, null),
				preferences.getString(LASTNAME_KEY, null),
				preferences.getString(ADDRESS_KEY, null),
				null, // TODO: get date
				preferences.getString(NATIONALITY_KEY, null),
				preferences.getString(PHONE_KEY, null),
				preferences.getString(TOKEN_KEY, null),
				preferences.getBoolean(ADMIN_KEY, false),
				preferences.getBoolean(STAFF_KEY, false)
		);
		return user;
	}

	public boolean isLogged() {
		return preferences.getBoolean(IS_LOGGED, false);
	}

	public boolean isAdmin() {
		return preferences.getBoolean(ADMIN_KEY, false);
	}

	public void LogoutUser() {
		user = null;
		editor.clear();
		editor.commit();
	}
}
