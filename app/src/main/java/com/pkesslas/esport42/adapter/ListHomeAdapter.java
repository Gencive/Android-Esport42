package com.pkesslas.esport42.adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.pkesslas.esport42.R;
import com.pkesslas.esport42.model.Posts;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by Pierre-Elie on 17/02/15.
 */
public class ListHomeAdapter extends ArrayAdapter<Posts> {
	private final Context context;
	private final ArrayList<Posts> posts;

	private TextView resume, title;
	private ImageView image;

	public ListHomeAdapter(Context context, ArrayList<Posts> posts) {
		super(context, R.layout.list_menu, posts);
		this.context = context;
		this.posts = posts;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View rowView = inflater.inflate(R.layout.list_article_left, parent, false);

		Posts post = posts.get(position);

		title = (TextView) rowView.findViewById(R.id.title);
		resume = (TextView) rowView.findViewById(R.id.resume);
		image = (ImageView) rowView.findViewById(R.id.image);

		title.setText(post.getTitle());
		resume.setText(post.getResume());

		Picasso.with(context).load(post.getImage()).into(image);
		return rowView;
	}
}
