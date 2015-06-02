package com.example.project1;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import org.xmlpull.v1.XmlPullParserException;


import android.app.ActionBar.Tab;
import android.app.ActionBar.TabListener;
import android.app.LauncherActivity.ListItem;
import android.app.AlertDialog;
import android.app.FragmentTransaction;
import android.app.ListActivity;
import android.app.SearchManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;


public class Working extends FragmentActivity implements TabListener {
	// android.app.ActionBar ab;
	

	String webResponse;
	String webResponse1;
	JSONArray arr;
	JSONObject obj;
	 String receiver,sender;
	ViewPager view = null;
	android.app.ActionBar ab;
	ArrayList<String> item=new ArrayList<String>();
	
	SharedPreferences sharedpreferences;
	Editor editor;
	  ListView listSearch,Notification_list;
	   EditText text;
	   String split;
	   View pop_view;
	    PopupWindow pwindo;
	    LayoutInflater lay;
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
	
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.working);
		Intent intent=new Intent(getBaseContext(),Notification_main.class);
		
		startService(intent);
		
		sharedpreferences=getSharedPreferences(MainActivity.MyPREFERENCES,Context.MODE_PRIVATE);
		view=(ViewPager) findViewById(R.id.Location);
		view = (ViewPager) findViewById(R.id.pager);
		FragmentManager frm = getSupportFragmentManager();
		view.setAdapter(new adapteor(frm));
		view.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

			@Override
			public void onPageScrollStateChanged(int arg0) {
				if (arg0 == ViewPager.SCROLL_STATE_DRAGGING) {
				}
				if (arg0 == ViewPager.SCROLL_STATE_IDLE) {
				}
				if (arg0 == ViewPager.SCROLL_STATE_SETTLING) {
				}
			}

			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
			}

			@Override
			public void onPageSelected(int arg0) {
				ab.setSelectedNavigationItem(arg0);
			}

		});

		ab = getActionBar();
		ab.setNavigationMode(android.app.ActionBar.NAVIGATION_MODE_TABS);
		android.app.ActionBar.Tab tab1 = ab.newTab();
		tab1.setText("Main");
		tab1.setTabListener(this);
		Tab tab2 = ab.newTab();
		tab2.setText("My Contacts");
		tab2.setTabListener(this);
		Tab tab3 = ab.newTab();
		tab3.setText("Notification");
		tab3.setTabListener(this);
		ab.addTab(tab1);
		ab.addTab(tab2);
		ab.addTab(tab3);
		
	}
	
	@Override
	public void onTabSelected(Tab tab, FragmentTransaction ft) {
		view.setCurrentItem(tab.getPosition());

	}
	  
	public void goNotification(View v)
	{
		Intent noti=new Intent(getApplicationContext(),Notification_main.class);
		startActivity(noti);
	}
	
	
	public boolean onCreateOptionsMenu(Menu menu) {
	    MenuInflater inflater = getMenuInflater();
	    inflater.inflate(R.menu.working, menu);

	    // Associate searchable configuration with the SearchView
	    SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
	    SearchView searchView = (SearchView) menu.findItem(R.id.Search)
	            .getActionView();
	    searchView.setSearchableInfo(searchManager
	            .getSearchableInfo(getComponentName()));
	    Log.d("search","sign up on create menu");
	    return super.onCreateOptionsMenu(menu);
	}
	
	public void logout(View v) {
		sharedpreferences = getSharedPreferences(MainActivity.MyPREFERENCES,
				Context.MODE_PRIVATE);
		editor = sharedpreferences.edit();
		editor.clear();
		editor.commit();
		Intent intent = new Intent(this, Sign_signup.class);
		startActivity(intent);
		finish();
	}
	@Override
	public void onTabUnselected(Tab tab, FragmentTransaction ft) {

	}

	@Override
	public void onTabReselected(Tab tab, FragmentTransaction ft) {

	}
	

}

class adapteor extends FragmentStatePagerAdapter {

	public adapteor(FragmentManager fm) {

		super(fm);
	}

	@Override
	public Fragment getItem(int i) {
		Fragment fr = null;
		if (i == 0) {
			fr = new Notifications();
		}
		if (i == 1) {
			fr = new Messages();
		}
		if (i == 2) {
			fr = new Sorry();
		}

		return fr;
	}

	@Override
	public int getCount() {

		return 3;
	}
	
	

}
