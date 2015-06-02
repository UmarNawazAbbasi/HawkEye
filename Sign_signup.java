package com.example.project1;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.Button;

public class Sign_signup extends Activity {
Button b;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_sign_signup);
	b=(Button) findViewById(R.id.btnSignUp);
	}
public void signup(View v) {
	Intent i =new Intent(this,Signup.class);
	
}
	

}
