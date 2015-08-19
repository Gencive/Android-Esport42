package com.pkesslas.esport42.activity;

import android.app.ActionBar;
import android.app.Activity;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.pkesslas.esport42.R;

public class TeamListActivity extends Activity implements View.OnClickListener {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.fragment_home);

		ActionBar ab = getActionBar();
		ab.setDisplayHomeAsUpEnabled(true);
		ab.setHomeButtonEnabled(true);

		findViewById(getResources().getIdentifier("action_bar_title", "id", "android")).setOnClickListener(this);
		findViewById(getResources().getIdentifier("home", "id", "android")).setOnClickListener(this);
		findViewById(getResources().getIdentifier("up", "id", "android")).setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		int id = v.getId();

		if (isHeader(id)) {
			this.finish();
		}
	}

	private boolean isHeader(int id) {
		return id == getResources().getIdentifier("action_bar_title", "id", "android")
				|| id == getResources().getIdentifier("up", "id", "android")
				|| id == getResources().getIdentifier("home", "id", "android");
	}
}
