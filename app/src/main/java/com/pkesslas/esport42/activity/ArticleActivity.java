package com.pkesslas.esport42.activity;
import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LevelListDrawable;
import android.os.AsyncTask;
import android.support.v4.widget.DrawerLayout;
import android.os.Bundle;
import android.text.Html;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.ikimuhendis.ldrawer.ActionBarDrawerToggle;
import com.ikimuhendis.ldrawer.DrawerArrowDrawable;
import com.pkesslas.esport42.R;
import com.pkesslas.esport42.model.Posts;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ArticleActivity extends Activity implements Html.ImageGetter, View.OnClickListener {
	private DrawerLayout drawerLayout;
	private ListView drawerList;
	private ActionBarDrawerToggle drawerToggle;
	private DrawerArrowDrawable drawerArrow;

	private TextView author, date, text, title;
	private ImageView image;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_article);

		ActionBar ab = getActionBar();
		ab.setDisplayHomeAsUpEnabled(true);
		ab.setHomeButtonEnabled(true);

		findViewById(getResources().getIdentifier("action_bar_title", "id", "android")).setOnClickListener(this);
		findViewById(getResources().getIdentifier("home", "id", "android")).setOnClickListener(this);
		findViewById(getResources().getIdentifier("up", "id", "android")).setOnClickListener(this);

		buildArticle();
	}

	private void buildArticle() {
		Intent intent = getIntent();
		Posts post = new Gson().fromJson(intent.getStringExtra("posts"), Posts.class);
		setTitle(post.getTitle());

		author = (TextView) findViewById(R.id.author);
		date = (TextView) findViewById(R.id.date);
		text = (TextView) findViewById(R.id.content);
		title = (TextView) findViewById(R.id.title);
		image = (ImageView) findViewById(R.id.main_image);

		author.setText(post.getAuthor().getUsername());
		title.setText(post.getTitle());

		displayDate(post);

		displayHTMLContent(post);

		Picasso.with(this).load(post.getImage()).into(image);
	}

	private void displayDate(Posts post) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
		try {
			Date date = format.parse(post.getCreated_at());
			this.date.setText(new SimpleDateFormat("dd/MM/yyyy   HH:mm:ss").format(date));
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}

	private void displayHTMLContent(Posts post) {
		Spanned spanned = Html.fromHtml(post.getText(), this, null);
		text.setText(spanned);
		text.setMovementMethod(LinkMovementMethod.getInstance());
	}

	@Override
	public Drawable getDrawable(String source) {
		LevelListDrawable d = new LevelListDrawable();
		Drawable empty = getResources().getDrawable(R.drawable.ic_launcher);
		d.addLevel(0, 0, empty);
		d.setBounds(0, 0, empty.getIntrinsicWidth(), empty.getIntrinsicHeight());

		new LoadImage().execute(source, d);

		return d;
	}

	private void mFinish() {
		finish();
		overridePendingTransition(0, R.anim.push_right_out);
	}

	@Override
	public void onBackPressed() {
		mFinish();
	}

	@Override
	public void onClick(View v) {
		int id = v.getId();

		if (isHeader(id)) {
			mFinish();
		}
	}

	private boolean isHeader(int id) {
		return id == getResources().getIdentifier("action_bar_title", "id", "android")
				|| id == getResources().getIdentifier("up", "id", "android")
				|| id == getResources().getIdentifier("home", "id", "android");
	}

	class LoadImage extends AsyncTask<Object, Void, Bitmap> {

		private LevelListDrawable drawable;

		@Override
		protected Bitmap doInBackground(Object... params) {
			String source = (String) params[0];
			drawable = (LevelListDrawable) params[1];

			try {
				InputStream is = new URL(source).openStream();
				return BitmapFactory.decodeStream(is);
			} catch (IOException e) {
				e.printStackTrace();
			}
			return null;
		}

		@Override
		protected void onPostExecute(Bitmap bitmap) {
			if (bitmap != null) {
				BitmapDrawable d = new BitmapDrawable(bitmap);
				drawable.addLevel(1, 1, d);
				drawable.setBounds(0, 0, bitmap.getWidth(), bitmap.getHeight());
				drawable.setLevel(1);

				CharSequence t = text.getText();
				text.setText(t);
			}
		}
	}
}

