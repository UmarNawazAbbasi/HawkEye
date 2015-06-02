package com.example.project1;

import org.json.JSONException;
import org.json.JSONObject;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import org.xmlpull.v1.XmlPullParserException;

import android.os.Bundle;
import android.os.TransactionTooLargeException;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {
	final static String ip="192.168.189.1";
	private static String SOAP_ACTION = "http://"+ip+"/Soap/login.php/login";
	private static String NAMESPACE = ip;
	private static String METHOD_NAME = "login";
	private static String URL = "http://"+ip+"/Soap/login.php";
	

	String webResponse;
	EditText user_name, password;
	Button sign_in;
	Button sign_up;
	TextView res;
	JSONObject obj;
	public static final String MyPREFERENCES = "myPfres";
	String Session_name = "user_name";
	String Session_pass = "pass";
	String Session_fName = "full_name";
	String Session_email = "email";
final static	String Session_id = "id";

	SharedPreferences sharedpreferences;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		sign_in = (Button) findViewById(R.id.button1);
		
		user_name = (EditText) findViewById(R.id.xmlEmail);
		password = (EditText) findViewById(R.id.xmlPass);
		res = new TextView(this);
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		sharedpreferences = getSharedPreferences(MyPREFERENCES,
				Context.MODE_PRIVATE);
		if (sharedpreferences.contains(Session_name)) {
			if (sharedpreferences.contains(Session_pass)) {
				Intent test=getIntent();
				Intent intent = new Intent(getApplicationContext(),
						Working.class);
				if(test!=null)
				{
					if(test.getExtras().getBoolean("GCMIService")==true)
					{
						 Intent i = new Intent("com.example.project1.DISPLAY_MESSAGE");
					        i.putExtra("message",getIntent().getExtras().getString("message"));
					        i.putExtra("sender",getIntent().getExtras().getString("sender"));
					        i.putExtra("category",getIntent().getExtras().getString("cat"));
					        getBaseContext().sendBroadcast(i);
						intent.putExtra("message",test.getExtras().getString("message",""));
						intent.putExtra("sender",test.getExtras().getString("sender",""));
						intent.putExtra("cat",test.getExtras().getString("cat",""));
					}
				}
				
				startActivity(intent);
				finish();
			}
		}
		super.onResume();
	}

	public void signin(View v) {
		if (user_name.getText().toString().isEmpty() == true
				|| password.getText().toString().isEmpty() == true) {
			Builder d = new AlertDialog.Builder(this);
			d.setTitle("Empty fields");
			d.setMessage("Please ensert the all field");
			d.setPositiveButton("OK", null);
			d.show();
		} else {

			new Thread() {
				@Override
				public void run() {
					try {

						SoapObject request = new SoapObject(NAMESPACE,
								METHOD_NAME);
						request.addProperty("name", user_name.getText()
								.toString());
						request.addProperty("password", password.getText()
								.toString());

						SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
								SoapEnvelope.VER11);
						envelope.dotNet = true;
						envelope.setOutputSoapObject(request);
						HttpTransportSE androidHttpTransport = new HttpTransportSE(
								URL);
						androidHttpTransport.debug = true;

						androidHttpTransport.call(SOAP_ACTION, envelope);
						System.out.println(androidHttpTransport.requestDump);

						SoapObject response = (SoapObject) envelope.bodyIn;
						webResponse = response.toString();
						String split = webResponse.substring(
								webResponse.lastIndexOf("=") + 1,
								webResponse.length() - 3);

						obj = new JSONObject(split);
						res.setText(obj.getString("status"));
						runOnUiThread(new Runnable() {

							@Override
							public void run() {
								// TODO Auto-generated method stub
								try {
									Toast.makeText(
											getApplicationContext(),obj.getString("status"),
											Toast.LENGTH_LONG).show();
								} catch (JSONException e1) {
									// TODO Auto-generated catch block
									e1.printStackTrace();
								}

							}
						});
						try {
							if (obj.getString("message").equals("0")) {
								Editor editor = sharedpreferences.edit();
								editor.putString(Session_name, user_name
										.getText().toString());
								editor.putString(Session_pass, password
										.getText().toString());
								editor.putString(Session_fName,
										obj.getString("fullName"));
								editor.putString(Session_email,
										obj.getString("email"));
								editor.putInt(Session_id,obj.getInt("id"));
								editor.commit();
								Intent intent = new Intent(
										getApplicationContext(), Working.class);
								startActivity(intent);
								finish();
							}
						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}

					}

					catch (NullPointerException e) {
						e.printStackTrace();
						// res.setText(e.toString());

					} catch (XmlPullParserException x) {

					} catch (Exception o) {
						// res.setText(o.toString());
					}

				}
			}.start();

		}
	}

	public void signUp(View v) {
		Intent i = new Intent(getApplicationContext(), Signup.class);
		startActivity(i);
	}
		

}
