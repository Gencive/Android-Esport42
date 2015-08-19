package com.pkesslas.esport42.activity;

import android.app.ActionBar;
import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.ComponentName;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.google.gson.Gson;
import com.pkesslas.esport42.R;
import com.pkesslas.esport42.model.Event;
import com.pkesslas.esport42.model.Posts;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class EventActivity extends Activity implements View.OnClickListener {
	private Event event;
	private TextView title, game, startDate, endDate, btnRegister, btnStream, btnTree;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_event);


		ActionBar ab = getActionBar();
		ab.setDisplayHomeAsUpEnabled(true);
		ab.setHomeButtonEnabled(true);

		findViewById(getResources().getIdentifier("action_bar_title", "id", "android")).setOnClickListener(this);
		findViewById(getResources().getIdentifier("home", "id", "android")).setOnClickListener(this);
		findViewById(getResources().getIdentifier("up", "id", "android")).setOnClickListener(this);

		getEvent();
		buildEvent();
	}

	private void getEvent() {
		Intent intent = getIntent();
		event = new Gson().fromJson(intent.getStringExtra("event"), Event.class);
	}

	private void buildEvent() {
		title = (TextView) findViewById(R.id.title);
		game = (TextView) findViewById(R.id.game);
		startDate = (TextView) findViewById(R.id.start_event);
		endDate = (TextView) findViewById(R.id.end_event);
		btnRegister = (TextView) findViewById(R.id.btn_register);
		btnStream = (TextView) findViewById(R.id.btn_stream);
		btnTree = (TextView) findViewById(R.id.btn_tree);

		title.setText(event.getName());
		game.setText("[" + event.getTag() + "] " + event.getGameName());
		startDate.setText(startDate.getText() + " " + event.getStartEvent());
		endDate.setText(endDate.getText() + " " + event.getEndEvent());
		btnTree.setOnClickListener(this);
		btnStream.setOnClickListener(this);

		if (registerOver(event)) {
			btnRegister.setVisibility(View.INVISIBLE);
		} else if (!eventStarted(event)) {
			btnTree.setVisibility(View.INVISIBLE);
			btnStream.setVisibility(View.INVISIBLE);
		}
		setTitle(event.getName());
	}

	private boolean registerOver(Event event) {
		String date = getDate();
		return (convertDate(date).getTime() > convertDate(event.getEndReg()).getTime());
	}

	private boolean eventStarted(Event event) {
		String date = getDate();
		return (convertDate(date).getTime() > convertDate(event.getStartEvent()).getTime());
	}

	private String getDate() {
		String date = new SimpleDateFormat("yyyy-MM-dd").format(new Date().getTime()).toString();
		return date;
	}

	private Date convertDate(String dates) {
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();
		try {
			date = format.parse(dates);
		} catch (ParseException ignored) {
		}
		return date;
	}

	private void mFinish() {
		finish();
		overridePendingTransition(0, R.anim.push_right_out);
	}

	@Override
	public void onClick(View v) {
		int id = v.getId();

		if (isHeader(id)) {
			mFinish();
		} else if (id == R.id.btn_tree) {
			Intent intent = new Intent(this, TeamListActivity.class);
			intent.putExtra("event", new Gson().toJson(event.getId()));
			startActivity(intent);
		} else if (id == R.id.btn_stream) {
			Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://twitch.tv/esport42/"));
			startActivity(browserIntent);
		}
	}

	private boolean isHeader(int id) {
		return id == getResources().getIdentifier("action_bar_title", "id", "android")
				|| id == getResources().getIdentifier("up", "id", "android")
				|| id == getResources().getIdentifier("home", "id", "android");
	}
}
