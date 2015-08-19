package com.pkesslas.esport42.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.pkesslas.esport42.Esport42;
import com.pkesslas.esport42.R;
import com.pkesslas.esport42.activity.ArticleActivity;
import com.pkesslas.esport42.activity.PostArticleActivity;
import com.pkesslas.esport42.activity.UpdateArticleActivity;
import com.pkesslas.esport42.model.API.Esport42API;
import com.pkesslas.esport42.model.Posts;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class DashboardFragment extends Fragment implements View.OnClickListener {

	private View rootView;
	private ArrayList<Posts> result;
	private TextView lastValidateMatch, litigationMap, tickets, articleCreate, articleEdit, eventCreate, eventEdit;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_dashboard, container, false);
		this.rootView = rootView;

		lastValidateMatch = (TextView) rootView.findViewById(R.id.last_validate_match);
		lastValidateMatch.setOnClickListener(this);
		litigationMap = (TextView) rootView.findViewById(R.id.litigation_map);
		litigationMap.setOnClickListener(this);
		tickets = (TextView) rootView.findViewById(R.id.ticket);
		tickets.setOnClickListener(this);
		articleCreate = (TextView) rootView.findViewById(R.id.article_create);
		articleCreate.setOnClickListener(this);
		articleEdit = (TextView) rootView.findViewById(R.id.article_edit);
		articleEdit.setOnClickListener(this);

		DisplayMetrics displayMetrics = getActivity().getResources().getDisplayMetrics();
		int dpHeight = displayMetrics.heightPixels / 4;

		lastValidateMatch.setHeight(dpHeight);
		litigationMap.setHeight(dpHeight);
		tickets.setHeight(dpHeight);
		articleCreate.setHeight(dpHeight);
		articleEdit.setHeight(dpHeight);
		return (rootView);
	}

	private ArrayList<String> getTitle() {
		ArrayList<String> titles = new ArrayList<>();

		Esport42API esport42API = Esport42.restAdapter().create(Esport42API.class);
		esport42API.posts(new Callback<ArrayList<Posts>>() {
			@Override
			public void success(ArrayList<Posts> post, Response response) {
				result = post;
			}

			@Override
			public void failure(RetrofitError error) {
				Toast.makeText(getActivity(), "An error occurred", Toast.LENGTH_LONG).show();
			}
		});

		for (Posts post : result) {
			titles.add(post.getTitle());
		}
		return titles;
	}

	@Override
	public void onClick(View v) {
		if (v.getId() == R.id.litigation_map) {
		} else if (v.getId() == R.id.last_validate_match) {
		} else if (v.getId() == R.id.ticket) {
		} else if (v.getId() == R.id.article_create) {
			Intent intent = new Intent(getActivity(), PostArticleActivity.class);
			startActivity(intent);
			getActivity().overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
		} else if (v.getId() == R.id.article_edit) {
			Intent intent = new Intent(getActivity(), UpdateArticleActivity.class);
			startActivity(intent);
			getActivity().overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
		}
	}
}
