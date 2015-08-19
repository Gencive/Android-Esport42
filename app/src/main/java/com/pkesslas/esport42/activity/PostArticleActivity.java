package com.pkesslas.esport42.activity;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LevelListDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.widget.DrawerLayout;
import android.text.Html;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.ikimuhendis.ldrawer.ActionBarDrawerToggle;
import com.ikimuhendis.ldrawer.DrawerArrowDrawable;
import com.pkesslas.esport42.Esport42;
import com.pkesslas.esport42.R;
import com.pkesslas.esport42.model.API.Esport42API;
import com.pkesslas.esport42.model.Posts;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;
import retrofit.mime.TypedFile;

public class PostArticleActivity extends Activity implements View.OnClickListener {
	private static final int SELECT_PHOTO = 2;

	private Context context;
	private EditText title, resume, content;
	private TextView choosePicture, submit;
	private Uri selectedPhoto = Uri.parse("null");


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_post_article);

		context = this;
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

		title = (EditText) findViewById(R.id.title_box);
		resume = (EditText) findViewById(R.id.resume_box);
		content = (EditText) findViewById(R.id.content_box);

		submit = (TextView) findViewById(R.id.submit);
		submit.setOnClickListener(this);
		choosePicture = (TextView) findViewById(R.id.choose_picture);
		choosePicture.setOnClickListener(this);
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
		} else if (resume.getText().length() < 7) {
			Toast.makeText(this, "Resume too short", Toast.LENGTH_SHORT).show();
			return false;
		} else if (content.getText().length() < 10) {
			Toast.makeText(this, "Content too short", Toast.LENGTH_SHORT).show();
			return false;
		} else if (selectedPhoto.getPath().equals("null")) {
			Toast.makeText(this, "You didn't choose a picture", Toast.LENGTH_SHORT).show();
			return false;
		}
		return true;
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent imageReturnedIntent) {
		super.onActivityResult(requestCode, resultCode, imageReturnedIntent);

		if (requestCode == SELECT_PHOTO) {
			if (resultCode == RESULT_OK) {
				Uri selectedImage = imageReturnedIntent.getData();
				selectedPhoto = selectedImage;
			}
		}
	}

	@Override
	public void onClick(View v) {
		int id = v.getId();

		if (id == R.id.choose_picture) {
			Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
			photoPickerIntent.setType("image/*");
			startActivityForResult(photoPickerIntent, SELECT_PHOTO)	;
		} else if (id == R.id.submit && formValid()) {
			//TODO STRING THIS SHIT
			TypedFile typedFile = new TypedFile("multipart/form-data", new File(getImagePath(selectedPhoto)));
			Esport42API esport42API = Esport42.restAdapter().create(Esport42API.class);
			esport42API.posts(typedFile, title.getText().toString(), content.getText().toString(),
					resume.getText().toString(), true, new Callback<Posts>() {
				@Override
				public void success(Posts posts, Response response) {
					Toast.makeText(context, "Success", Toast.LENGTH_SHORT).show();
					mFinish();
				}

				@Override
				public void failure(RetrofitError error) {
					Toast.makeText(context, "error " + error, Toast.LENGTH_SHORT).show();
				}
			});
		}
	}

	public String getImagePath(Uri uri) {
		String selectedImagePath = null;
		Cursor cursor = getContentResolver().query(
				uri, null, null, null, null);
		if (cursor == null) {
			selectedImagePath = uri.getPath();
		} else {
			cursor.moveToFirst();
			int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
			selectedImagePath = cursor.getString(idx);
		}
		return selectedImagePath;
	}
}

