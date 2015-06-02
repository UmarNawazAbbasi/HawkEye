package com.example.project1;


import java.io.ObjectInputStream.GetField;

import android.os.Bundle;
import android.app.Activity;
import android.app.SearchManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

public class Sign_signup extends Activity {
	SharedPreferences sharedpreferences;
Button b;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//registerReceiver(mHandleMessageReceiver, new IntentFilter("com.example.project1.DISPLAY_MESSAGE"));
		setContentView(R.layout.activity_sign_signup);
	b=(Button) findViewById(R.id.btnSignUp);
	ImageView image=(ImageView) findViewById(R.id.imageView1);
	Animation animation=AnimationUtils.loadAnimation(getApplicationContext(),R.anim.fade);
	image.startAnimation(animation);
	}
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		
		sharedpreferences = getSharedPreferences(MainActivity.MyPREFERENCES,Context.MODE_PRIVATE);
		if (sharedpreferences.contains("user_name")) {
			if (sharedpreferences.contains("pass")) {
				
			
				Intent intent = new Intent(getApplicationContext(),Working.class);
				
				startActivity(intent);
				finish();
			}
		}
	//	registerReceiver(mHandleMessageReceiver, new IntentFilter("com.example.project1.DISPLAY_MESSAGE"));
		super.onResume();
	}

public void signup_activity(View v) {
	Intent i =new Intent(this,Signup.class);
	startActivity(i);
}
public void signin_activity(View v) {
	Intent i =new Intent(this,MainActivity.class);
	startActivity(i);
}
private final BroadcastReceiver mHandleMessageReceiver = new BroadcastReceiver() {
	
	@Override
	public void onReceive(Context context, Intent intent) {
		 Log.d("web","broadcaster run");
		String newMessage = intent.getExtras().getString("message");
		WakeLocker.acquire(getApplicationContext());
		Toast.makeText(getApplicationContext(),
				"New Message: " + newMessage, Toast.LENGTH_LONG).show();
		WakeLocker.release();
	}
};


}
