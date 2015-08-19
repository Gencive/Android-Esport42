package com.pkesslas.esport42.fragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.pkesslas.esport42.Esport42;
import com.pkesslas.esport42.R;
import com.pkesslas.esport42.model.API.Esport42API;
import com.pkesslas.esport42.model.User;

import java.util.HashMap;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class AccountSettingsFragment extends Fragment implements View.OnClickListener {

	private View rootView;
	private Context context;
	private EditText username, lastname, firstname, email, adress, phone;
	private TextView changePassword, save;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_account_settings, container, false);
		this.rootView = rootView;
		context = getActivity();

		User user = Esport42.userHelper().getUser();

		username = (EditText) rootView.findViewById(R.id.username);
		lastname = (EditText) rootView.findViewById(R.id.lastname);
		firstname = (EditText) rootView.findViewById(R.id.firstname);
		email = (EditText) rootView.findViewById(R.id.email);
		adress = (EditText) rootView.findViewById(R.id.address);
		phone = (EditText) rootView.findViewById(R.id.phone);

		username.setText(user.getUsername());
		lastname.setText(user.getLastName());
		firstname.setText(user.getFirstName());
		email.setText(user.getEmail());
		adress.setText(user.getAddress());
		phone.setText(user.getPhone());

		changePassword = (TextView) rootView.findViewById(R.id.change_password);
		save = (TextView) rootView.findViewById(R.id.save);
		changePassword.setOnClickListener(this);
		save.setOnClickListener(this);
		return (rootView);
	}

	@Override
	public void onClick(View v) {
		int id = v.getId();

		if (id == R.id.change_password) {
			ChangePasswordDialogFragment changePasswordDialogFragment = new ChangePasswordDialogFragment();
			Bundle args = new Bundle();
			args.putInt("id", Esport42.userHelper().getUser().getId());
			changePasswordDialogFragment.setArguments(args);
			changePasswordDialogFragment.show(this.getActivity().getFragmentManager(), "ChangePassword");
		} else if (id == R.id.save) {
			HashMap<String, String> data = new HashMap<>();
			data.put("id", Integer.toString(Esport42.userHelper().getUser().getId()));
			if (username.getText().length() > 0) {
				data.put("username", username.getText().toString());
			} if (lastname.getText().length() > 0) {
				data.put("last_name", lastname.getText().toString());
			} if (firstname.getText().length() > 0) {
				data.put("first_name", firstname.getText().toString());
			} if (email.getText().length() > 0) {
					data.put("email", email.getText().toString());
			} if (adress.getText().length() > 0) {
				data.put("address", adress.getText().toString());
			} if (username.getText().length() > 0) {
				data.put("phone", username.getText().toString());
			}
			Esport42API esport42API = Esport42.restAdapter().create(Esport42API.class);
			esport42API.updateUser(Esport42.userHelper().getUser().getId(), data, new Callback<User>() {
				@Override
				public void success(User user, Response response) {
					// TODO STRINGG
					Toast.makeText(context, "User updated", Toast.LENGTH_LONG).show();
				}

				@Override
				public void failure(RetrofitError error) {
					Log.e("AccountSettingsFragment", "" + error);
					Toast.makeText(context, "Oops error", Toast.LENGTH_LONG).show();
				}
			});

		}
	}
}
