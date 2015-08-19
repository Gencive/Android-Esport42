package com.pkesslas.esport42.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.dd.processbutton.iml.ActionProcessButton;
import com.pkesslas.esport42.Esport42;
import com.pkesslas.esport42.R;
import com.pkesslas.esport42.activity.MainActivity;
import com.pkesslas.esport42.model.API.Esport42API;
import com.pkesslas.esport42.model.User;

import java.util.HashMap;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class RegisterFragment extends Fragment implements View.OnClickListener {
	private EditText username, password, passwordCheck, email;
	private ActionProcessButton registerButton;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.fragment_register, container, false);

		username = (EditText) v.findViewById(R.id.username);
		password = (EditText) v.findViewById(R.id.password);
		passwordCheck = (EditText) v.findViewById(R.id.password_retype);
		email = (EditText) v.findViewById(R.id.email);
		registerButton = (ActionProcessButton) v.findViewById(R.id.btn_register);
		registerButton.setMode(ActionProcessButton.Mode.ENDLESS);
		registerButton.setOnClickListener(this);

		return v;
	}


	private boolean validForm() {
		if (username.getText().length() == 0) {
			username.setError(getResources().getString(R.string.invalid_field));
			return false;
		} else if (password.getText().length() == 0) {
			password.setError(getResources().getString(R.string.invalid_field));
			return false;
		} else if (passwordCheck.getText().length() == 0 && !passwordCheck.getText().equals(password.getText())) {
			password.setError(getResources().getString(R.string.invalid_field));
			return false;
		} else if (email.getText().length() == 0 && !email.getText().toString().contains("@")) {
			password.setError(getResources().getString(R.string.invalid_field));
			return false;
		}
		return true;
	}

	@Override
	public void onClick(View v) {
		int id = v.getId();

		if (id == R.id.btn_register) {
			if (!validForm()) {
				return ;
			}
			registerButton.setProgress(1);
			HashMap<String, String> data = new HashMap<>();
			Esport42API esport42API = Esport42.restAdapter().create(Esport42API.class);

			data.put("username", username.getText().toString());
			data.put("email", email.getText().toString());
			data.put("password", password.getText().toString());
			data.put("password_confirm", password.getText().toString());

			esport42API.signin(data, new Callback<User>() {
				@Override
				public void success(User user, Response response) {
					Log.i("RegisterFragment", "Register success " + response);
					Esport42.userHelper().logUser(user);
					registerButton.setProgress(100);
					try {
						Thread.sleep(1000);
					} catch (InterruptedException ignored) {
					}
					startActivity(new Intent(getActivity(), MainActivity.class));
					getActivity().finish();
				}

				@Override
				public void failure(RetrofitError error) {
					Log.i("RegisterFragment", "Register failed " + error);
					registerButton.setProgress(0);
					Toast.makeText(getActivity(), "ERROR", Toast.LENGTH_SHORT).show();
				}
			});
		}
	}
}
