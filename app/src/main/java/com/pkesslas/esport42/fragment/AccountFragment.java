package com.pkesslas.esport42.fragment;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.pkesslas.esport42.R;

public class AccountFragment extends Fragment implements View.OnClickListener, ViewPager.OnPageChangeListener {
	private static final int NUM_PAGES = 2;

	private View rootView;
	private ViewPager viewPager;
	private PagerAdapter pagerAdapter;
	private TextView logginTab, registerTab;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_account, container, false);
		this.rootView = rootView;

		viewPager = (ViewPager) rootView.findViewById(R.id.pager);
		pagerAdapter = new ScreenSlidePagerAdapter(getChildFragmentManager());
		viewPager.setAdapter(pagerAdapter);
		viewPager.setOnPageChangeListener(this);

		logginTab = (TextView) rootView.findViewById(R.id.btn_connection);
		registerTab = (TextView) rootView.findViewById(R.id.btn_register);
		logginTab.setOnClickListener(this);
		registerTab.setOnClickListener(this);
		return (rootView);
	}

	public void onPageScrollStateChanged(int arg0) {
		int pos = getCurrentSelectedFragmentPosition();
		if (pos == 0) {
			logginTab.setBackground(getResources().getDrawable(R.drawable.button_tab_activate));
			registerTab.setBackground(getResources().getDrawable(R.drawable.button_tab));
		} else if (pos == 1) {
			logginTab.setBackground(getResources().getDrawable(R.drawable.button_tab));
			registerTab.setBackground(getResources().getDrawable(R.drawable.button_tab_activate));
		}
	}

	@Override
	public void onClick(View v)
	{
		if (v.getId() == R.id.btn_register) {
			setPagePosition(1);
		} else if (v.getId() == R.id.btn_connection) {
			setPagePosition(0);
		}
	}

	public void onPageSelected(int arg0) {
	}

	public void onPageScrolled(int arg0, float arg1, int arg2) {
	}

	public int getCurrentSelectedFragmentPosition() {
		return viewPager.getCurrentItem();
	}

	public void setPagePosition(int position) {
		viewPager.setCurrentItem(position);
	}

	private class ScreenSlidePagerAdapter extends FragmentStatePagerAdapter {
		public ScreenSlidePagerAdapter(FragmentManager fm) {
			super(fm);
		}

		@Override
		public Fragment getItem(int position) {
			if (position == 0)
				return new LoginFragment();
			else if (position == 1)
				return new RegisterFragment();
			else
				return new LoginFragment();
		}

		@Override
		public int getCount() {
			return NUM_PAGES;
		}
	}
}
