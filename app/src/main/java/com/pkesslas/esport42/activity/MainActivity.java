package com.pkesslas.esport42.activity;

import android.app.ActionBar;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.ikimuhendis.ldrawer.ActionBarDrawerToggle;
import com.ikimuhendis.ldrawer.DrawerArrowDrawable;
import com.pkesslas.esport42.Esport42;
import com.pkesslas.esport42.fragment.AccountFragment;
import com.pkesslas.esport42.fragment.AccountSettingsFragment;
import com.pkesslas.esport42.fragment.DashboardFragment;
import com.pkesslas.esport42.fragment.EventFragment;
import com.pkesslas.esport42.fragment.HomeFragment;
import com.pkesslas.esport42.R;
import com.pkesslas.esport42.adapter.ListMenuAdapter;


public class MainActivity extends FragmentActivity {
	private Context context;
	private DrawerLayout drawerLayout;
	private ListView drawerList;
	private ActionBarDrawerToggle drawerToggle;
	private DrawerArrowDrawable drawerArrow;
	private String[] values;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		context = this;

		if (findViewById(R.id.fragment_container) != null) {
			if (savedInstanceState != null) {
				return;
			}

			HomeFragment homeFragment = new HomeFragment();
			homeFragment.setArguments(getIntent().getExtras());
			getSupportFragmentManager().beginTransaction().add(R.id.fragment_container, homeFragment).commit();
		}
		buildNavigationDrawer();
	}

	private void buildNavigationDrawer() {
		ActionBar ab = getActionBar();
		ab.setDisplayHomeAsUpEnabled(true);
		ab.setHomeButtonEnabled(true);

		drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
		drawerList = (ListView) findViewById(R.id.navdrawer);


		drawerArrow = new DrawerArrowDrawable(this) {
			@Override
			public boolean isLayoutRtl() {
				return false;
			}
		};

		drawerToggle = new ActionBarDrawerToggle(this, drawerLayout,
				drawerArrow, R.string.drawer_open,
				R.string.drawer_close) {

			public void onDrawerClosed(View view) {
				super.onDrawerClosed(view);
				invalidateOptionsMenu();
			}

			public void onDrawerOpened(View drawerView) {
				super.onDrawerOpened(drawerView);
				invalidateOptionsMenu();
			}
		};

		if (Esport42.userHelper().isAdmin()) {
			buildDrawerIfAdmin();
		} else {
			buildDrawerIfNOTAdmin();
		}

	}

	private void buildDrawerIfAdmin() {
		drawerLayout.setDrawerListener(drawerToggle);
		drawerToggle.syncState();

		values = new String[]{
				//TODO: STRING THAT SHIT
				"Accueil",
				"Événement à venir",
				"Événement en cours",
				"Déconnexion",
				"Paramètre du compte",
				"Administration",
		};

		drawerList.setAdapter(new ListMenuAdapter(this, values));
		drawerList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				if (position == 0) {
					changeFragment(new HomeFragment());
				} else if (position == 1 || position == 2) {
					Fragment fragment = new EventFragment();
					Bundle bundle = new Bundle();
					if (position == 1) {
						bundle.putBoolean("now", false);
					} else {
						bundle.putBoolean("now", true);
					}
					fragment.setArguments(bundle);
					changeFragment(fragment);
				} else if (position == 3) {
					Esport42.userHelper().LogoutUser();
					drawerLayout.closeDrawers();
					startActivity(new Intent(context, MainActivity.class));
					finish();
				} else if (position == 4) {
					changeFragment(new AccountSettingsFragment());
				} else if (position == 5) {
					changeFragment(new DashboardFragment());
				}
			}
		});
	}

	private void buildDrawerIfNOTAdmin() {
		drawerLayout.setDrawerListener(drawerToggle);
		drawerToggle.syncState();

		if (Esport42.userHelper().isLogged()) {
			values = new String[]{
					//TODO: STRING THAT SHIT
					"Accueil",
					"Événement à venir",
					"Événement en cours",
					"Déconnexion",
					"Paramètre du compte",
			};
		} else {
			values = new String[]{
					//TODO: STRING THAT SHIT
					"Accueil",
					"Événement à venir",
					"Événement en cours",
					"Connexion / inscription",
			};
		}
		drawerList.setAdapter(new ListMenuAdapter(this, values));
		drawerList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				if (position == 0) {
					changeFragment(new HomeFragment());
				} else if (position == 1 || position == 2) {
					Fragment fragment = new EventFragment();
					Bundle bundle = new Bundle();
					if (position == 1) {
						bundle.putBoolean("now", false);
					} else {
						bundle.putBoolean("now", true);
					}
					fragment.setArguments(bundle);
					changeFragment(fragment);
				} else if (position == 3 && !Esport42.userHelper().isLogged()) {
					changeFragment(new AccountFragment());
				} else if (position == 3 && Esport42.userHelper().isLogged()) {
					Esport42.userHelper().LogoutUser();
					drawerLayout.closeDrawers();
					startActivity(new Intent(context, MainActivity.class));
					finish();
				} else if (position == 4 && Esport42.userHelper().isLogged()) {
					changeFragment(new AccountSettingsFragment());
				}
			}
		});
	}

	private void changeFragment(Fragment fragment)
	{
		FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
		transaction.replace(R.id.fragment_container, fragment);
		transaction.addToBackStack(null);
		transaction.commit();
		drawerLayout.closeDrawers();
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if (item.getItemId() == android.R.id.home) {
			if (drawerLayout.isDrawerOpen(drawerList)) {
				drawerLayout.closeDrawer(drawerList);
			} else {
				drawerLayout.openDrawer(drawerList);
			}
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	protected void onPostCreate(Bundle savedInstanceState) {
		super.onPostCreate(savedInstanceState);
		drawerToggle.syncState();
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
		drawerToggle.onConfigurationChanged(newConfig);
	}
}
