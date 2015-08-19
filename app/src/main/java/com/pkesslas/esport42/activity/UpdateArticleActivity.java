package com.pkesslas.esport42.activity;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.pkesslas.esport42.Esport42;
import com.pkesslas.esport42.R;
import com.pkesslas.esport42.model.API.Esport42API;
import com.pkesslas.esport42.model.Posts;

import java.util.ArrayList;
import java.util.HashMap;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class UpdateArticleActivity extends Activity implements View.OnClickListener {
	private static final int SELECT_PHOTO = 2;
	private Context context;

	private EditText title, resume, content;
	private TextView choosePicture, submit;
	private Spinner spinner;
	private int	actualId = 0;
	private ArrayList<Posts> posts;
	ArrayList<String> titles = new ArrayList<>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_update_article);

		ActionBar ab = getActionBar();
		ab.setDisplayHomeAsUpEnabled(true);
		ab.setHomeButtonEnabled(true);

		setTitle(getResources().getString(R.string.article_create));
		findViewById(getResources().getIdentifier("action_bar_title", "id", "android")).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				mFinish();
			}
		});

		context = this;
		title = (EditText) findViewById(R.id.title_box);
		resume = (EditText) findViewById(R.id.resume_box);
		content = (EditText) findViewById(R.id.content_box);
		spinner = (Spinner) findViewById(R.id.article_title);
		submit = (TextView) findViewById(R.id.submit);
		submit.setOnClickListener(this);

		buildSpinner();
	}

	private void buildSpinner() {

		Esport42API esport42API = Esport42.restAdapter().create(Esport42API.class);
		esport42API.posts(new Callback<ArrayList<Posts>>() {
			@Override
			public void success(ArrayList<Posts> post, Response response) {
				posts = post;
				for (Posts p : posts) {
					titles.add(p.getTitle());
				}
				ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<>(context,
						android.R.layout.simple_spinner_item, titles);
				spinner.setAdapter(spinnerAdapter);
				spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
					@Override
					public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
						buildView(position);
					}

					@Override
					public void onNothingSelected(AdapterView<?> parentView) {
					}
				});
			}

			@Override
			public void failure(RetrofitError error) {
			}
		});
	}

	public void buildView(int pos) {
		Posts post = posts.get(pos);

		actualId = post.getId();
		title.setText(post.getTitle());
		resume.setText(post.getResume());
		content.setText(post.getText());
	}

	@Override
	public void onBackPressed() {
		mFinish();
	}

	private void mFinish() {
		finish();
		overridePendingTransition(0, R.anim.push_right_out);
	}

	private boolean formValid() {
		//TODO: String this shit
		if (title.getText().length() < 4) {
			Toast.makeText(this, "Title too short", Toast.LENGTH_SHORT).show();
			return false;
		} else if (resume.getText().length() < 6) {
			Toast.makeText(this, "Resume too short", Toast.LENGTH_SHORT).show();
			return false;
		} else if (content.getText().length() < 10) {
			Toast.makeText(this, "Content too short", Toast.LENGTH_SHORT).show();
			return false;
		}
		return true;
	}

	@Override
	public void onClick(View v) {
		int id = v.getId();

		if (id == R.id.submit && formValid()) {
			HashMap<String, String> data = new HashMap<String, String>();
			data.put("title", title.getText().toString());
			data.put("text", content.getText().toString());
			data.put("resume", resume.getText().toString());
			data.put("is_landing", "true");

			Esport42API esport42API = Esport42.restAdapter().create(Esport42API.class);

			esport42API.updatePosts(data, actualId, new Callback<Posts>() {
				@Override
				public void success(Posts posts, Response response) {
					Toast.makeText(context, "Article updated", Toast.LENGTH_LONG).show();
					mFinish();
				}

				@Override
				public void failure(RetrofitError error) {
					Toast.makeText(context, "Error " + error, Toast.LENGTH_LONG).show();
				}
			});
		}
	}
}

