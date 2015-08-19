package com.pkesslas.esport42.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.pkesslas.esport42.R;
import com.pkesslas.esport42.model.Event;
import com.pkesslas.esport42.model.Posts;
import com.squareup.picasso.Picasso;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Pierre-Elie on 17/02/15.
 */
public class ListEventAdapter extends ArrayAdapter<Event> {
	private final Context context;
	private final ArrayList<Event> events;

	private TextView game, title, startDate, endDate;

	public ListEventAdapter(Context context, ArrayList<Event> events) {
		super(context, R.layout.list_menu, events);
		this.context = context;
		this.events = events;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View rowView = inflater.inflate(R.layout.list_events, parent, false);

		Event event = events.get(position);

		title = (TextView) rowView.findViewById(R.id.title);
		game = (TextView) rowView.findViewById(R.id.game);
		endDate = (TextView) rowView.findViewById(R.id.end_date_event);
		startDate = (TextView) rowView.findViewById(R.id.start_date_event);

		game.setText("[" + event.getTag() + "] " + event.getGameName());
		title.setText(event.getName());
		startDate.setText(event.getStartEvent());
		endDate.setText(event.getEndEvent());
		return rowView;
	}
}
