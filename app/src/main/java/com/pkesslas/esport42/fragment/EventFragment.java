package com.pkesslas.esport42.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.pkesslas.esport42.Esport42;
import com.pkesslas.esport42.R;
import com.pkesslas.esport42.activity.ArticleActivity;
import com.pkesslas.esport42.activity.EventActivity;
import com.pkesslas.esport42.adapter.ListEventAdapter;
import com.pkesslas.esport42.adapter.ListHomeAdapter;
import com.pkesslas.esport42.model.API.Esport42API;
import com.pkesslas.esport42.model.Event;
import com.pkesslas.esport42.model.Posts;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class EventFragment extends Fragment {
	private View rootView;
	private Context context;
	private ListView listView;
	private ArrayList<Event> events;
	private boolean now = false;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_home, container, false);
		this.rootView = rootView;

		context = getActivity();
		listView = (ListView) rootView.findViewById(R.id.list);

		now = this.getArguments().getBoolean("now");
		Esport42API esport42API = Esport42.restAdapter().create(Esport42API.class);
		esport42API.getEvent(new Callback<ArrayList<Event>>() {
			@Override
			public void success(ArrayList<Event> event, Response response) {
				for (Iterator<Event> iter = event.listIterator(); iter.hasNext(); ) {
					Event a = iter.next();
					if (!dateIsValid(a)) {
						iter.remove();
					}
				}
				events = event;
				buildListView();
			}

			@Override
			public void failure(RetrofitError error) {
				Log.e("EventFragment", "Can't load event: " + error);
				Toast.makeText(context, "An error occurred", Toast.LENGTH_LONG).show();
			}
		});

		return (rootView);
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

	private boolean dateIsValid(Event event) {
		String date = getDate();
		if (now) {
			return (convertDate(date).getTime() >= convertDate(event.getStartEvent()).getTime()
					&& convertDate(date).getTime() <= convertDate(event.getEndEvent()).getTime());
		} else {
			return (convertDate(date).getTime() <= convertDate(event.getEndEvent()).getTime());
		}
	}

	private String getDate() {
		String date = new SimpleDateFormat("yyyy-MM-dd").format(new Date().getTime()).toString();
		return date;
	}

	private void buildListView() {
		listView.setAdapter(new ListEventAdapter(getActivity(), events));
		listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				Intent intent = new Intent(getActivity(), EventActivity.class);
				intent.putExtra("event", new Gson().toJson(events.get(position)));
				startActivity(intent);
				getActivity().overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
			}
		});
	}
}
