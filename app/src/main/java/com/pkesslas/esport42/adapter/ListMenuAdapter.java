package com.pkesslas.esport42.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.pkesslas.esport42.Esport42;
import com.pkesslas.esport42.R;

/**
 * Created by Pierre-Elie on 17/02/15.
 */
public class ListMenuAdapter extends ArrayAdapter<String> {
	private final Context context;
	private final String[] values;

	public ListMenuAdapter(Context context, String[] values) {
		super(context, R.layout.list_menu, values);
		this.context = context;
		this.values = values;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View rowView = inflater.inflate(R.layout.list_menu, parent, false);

		TextView icon = (TextView) rowView.findViewById(R.id.icon);
		TextView menuElem = (TextView) rowView.findViewById(R.id.menu_elements);
		menuElem.setText(values[position]);
		if (position == 0) {
			icon.setBackground(context.getResources().getDrawable(R.drawable.home));
		} else if (position == 1) {
			icon.setBackground(context.getResources().getDrawable(R.drawable.event_comming));
		} else if (position == 2) {
			icon.setBackground(context.getResources().getDrawable(R.drawable.event_available));
		} else if (position == 3 && Esport42.userHelper().isLogged()) {
			icon.setBackground(context.getResources().getDrawable(R.drawable.close));
		} else if (position == 3 && !Esport42.userHelper().isLogged()) {
			icon.setBackground(context.getResources().getDrawable(R.drawable.login));
		} else if (position == 4 && Esport42.userHelper().isLogged()) {
			icon.setBackground(context.getResources().getDrawable(R.drawable.settings));
		} else if (position == 5) {
			icon.setBackground(context.getResources().getDrawable(R.drawable.dashboard));
		}
		return rowView;
	}
}
