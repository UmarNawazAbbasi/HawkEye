package com.example.project1;

import java.util.zip.Inflater;

import org.json.JSONException;
import org.json.JSONObject;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import org.xmlpull.v1.XmlPullParserException;

import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class Signup extends Activity {
	private static String SOAP_ACTION = "http://"+MainActivity.ip+"/Soap/signup_server.php/signup";
	private static String NAMESPACE = MainActivity.ip;
	private static String METHOD_NAME = "signup";
	private static String URL = "http://"+MainActivity.ip+"/Soap/signup_server.php";
	String webResponse;
	EditText name, user, email, pass;
	JSONObject obj;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_signup);
		name = (EditText) findViewById(R.id.xmlName);
		user = (EditText) findViewById(R.id.xmlUser);
		email = (EditText) findViewById(R.id.xmlEmail);
		pass = (EditText) findViewById(R.id.xmlPass);
		
	}

	public void doSignUp(View v) {
		if (name.getText().toString().isEmpty() == true
				|| user.getText().toString().isEmpty() == true
				|| email.getText().toString().isEmpty() == true) {
			Builder d = new AlertDialog.Builder(this);
			d.setTitle("Empty fields");
			d.setMessage("Please ensert the all field");
			d.setPositiveButton("ok", null);
			d.show();
		}

		else {

			Thread thread=new Thread() {
				@Override
				public void run() {
					try {

						SoapObject request = new SoapObject(NAMESPACE,
								METHOD_NAME);
						PropertyInfo fromProp = new PropertyInfo();
						request.addProperty("name", name.getText().toString());
						request.addProperty("user_name", user.getText()
								.toString());
						request.addProperty("email", email.getText().toString());
						request.addProperty("password", pass.getText()
								.toString());

						SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
								SoapEnvelope.VER11);
						envelope.dotNet = true;
						envelope.setOutputSoapObject(request);
						HttpTransportSE androidHttpTransport = new HttpTransportSE(
								URL);
						Log.d("web", "before call");
						androidHttpTransport.debug = true;

						androidHttpTransport.call(SOAP_ACTION, envelope);
						System.out.println(androidHttpTransport.requestDump);

						SoapObject response = (SoapObject) envelope.bodyIn;
						webResponse = response.toString();
						Log.d("web", "before response");
						String split = webResponse.substring(
								webResponse.lastIndexOf("=") + 1,
								webResponse.length() - 3);

						 obj = new JSONObject(split);
						
						  runOnUiThread(new Runnable() {
						  
						  @Override public void run() {  
							  try {
								
								  Toast.makeText(getApplicationContext(),obj.getString("status"), Toast.LENGTH_LONG).show();
								 
							  } catch (JSONException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
								
						  } 
						  });
						 
						 
					}

					catch (NullPointerException e) {
						e.printStackTrace();
						// res.setText(e.toString());

					} catch (XmlPullParserException x) {

					} catch (Exception o) {
						// res.setText(o.toString());
					}
				}
			};
			
			thread.start();
			try {
				thread.join();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				if(obj.getString("message").equals("0"))
				  {
					  Intent intent=new Intent(this,MainActivity.class);
					  startActivity(intent);
					  this.finish();
				  }
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
	}

}
