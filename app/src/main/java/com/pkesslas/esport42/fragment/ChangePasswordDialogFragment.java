package com.pkesslas.esport42.fragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.pkesslas.esport42.Esport42;
import com.pkesslas.esport42.R;
import com.pkesslas.esport42.model.API.Esport42API;
import com.pkesslas.esport42.model.User;

import java.util.HashMap;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by Pierre-Elie on 14/05/15.
 */
public class ChangePasswordDialogFragment extends DialogFragment {

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		LayoutInflater inflater = getActivity().getLayoutInflater();

		final View rootView = inflater.inflate(R.layout.change_password_dialog, null);
		builder.setView(rootView)
				.setPositiveButton("Changer", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int id) {
						EditText password, repeatPassword;
						final Context context = getActivity();
						Esport42API esport42API = Esport42.restAdapter().create(Esport42API.class);

						password = (EditText) rootView.findViewById(R.id.password);
						repeatPassword = (EditText) rootView.findViewById(R.id.password_retype);

						int userID = getArguments().getInt("id");
						// TODO STRIIINNNGGG
						if (password.getText().toString().equals(repeatPassword.getText().toString())) {
							HashMap<String, String> data = new HashMap<>();
							data.put("id", Integer.toString(Esport42.userHelper().getUser().getId()));
							data.put("password", password.getText().toString());
							data.put("password_confirm", repeatPassword.getText().toString());
							esport42API.updateUser(userID, data, new Callback<User>() {
								@Override
								public void success(User user, Response response) {
									Toast.makeText(context, "Password have been changed", Toast.LENGTH_SHORT).show();
								}

								@Override
								public void failure(RetrofitError error) {
									Log.e("Error", "" + error);
									Toast.makeText(context, "Oops error", Toast.LENGTH_SHORT).show();
								}
							});
						} else {
							Toast.makeText(getActivity(), "Passwords doenst match", Toast.LENGTH_SHORT).show();
						}
					}
				})

				.setNegativeButton("Annuler", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {
					}
				});
		return builder.create();
	}
}
