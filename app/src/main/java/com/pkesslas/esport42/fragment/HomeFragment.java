package com.pkesslas.esport42.fragment;

import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.pkesslas.esport42.Esport42;
import com.pkesslas.esport42.R;
import com.pkesslas.esport42.activity.ArticleActivity;
import com.pkesslas.esport42.adapter.ListHomeAdapter;
import com.pkesslas.esport42.model.API.Esport42API;
import com.pkesslas.esport42.model.Posts;
import com.pkesslas.esport42.model.User;

import java.util.ArrayList;
import java.util.HashMap;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class HomeFragment extends Fragment {
	private View rootView;
	private Context context;
	private ListView listView;
	private ArrayList<Posts> posts;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_home, container, false);
		this.rootView = rootView;

		context = getActivity();
		listView = (ListView) rootView.findViewById(R.id.list);

		Esport42API esport42API = Esport42.restAdapter().create(Esport42API.class);
		esport42API.posts(new Callback<ArrayList<Posts>>() {
			@Override
			public void success(ArrayList<Posts> post, Response response) {
				posts = post;
				buildListView();
			}

			@Override
			public void failure(RetrofitError error) {
				Log.e("HomeFragment", "Can't load article: " + error);
				Toast.makeText(context, "An error occurred", Toast.LENGTH_LONG).show();
			}
		});

		return (rootView);
	}

	private void buildListView() {
		listView.setAdapter(new ListHomeAdapter(getActivity(), posts));
		listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				Intent intent = new Intent(getActivity(), ArticleActivity.class);
				intent.putExtra("posts", new Gson().toJson(posts.get(position)));
				startActivity(intent);
				getActivity().overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
			}
		});
	}
}
