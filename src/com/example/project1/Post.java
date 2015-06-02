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

import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class Post extends Activity {
	private static String SOAP_ACTION = "http://"+MainActivity.ip+"/Soap/send_post_server.php/sendPost";
	private static String NAMESPACE = MainActivity.ip;
	private static String METHOD_NAME = "sendPost";
	private static String URL = "http://"+MainActivity.ip+"/Soap/send_post_server.php";
	String webResponse;
	String split;
	
	private static String SOAP_ACTION1 = "http://"+MainActivity.ip+"/Soap/result_server.php/getResult";
	private static String NAMESPACE1 = MainActivity.ip;
	private static String METHOD_NAME1 = "getResult";
	private static String URL1 = "http://"+MainActivity.ip+"/Soap/result_server.php";
	String webResponse1;
	String MyId;
	JSONObject obj;
	String split1;
	Spinner sp;
	EditText title;
	int percen;
	String post_title;
	String _lati;
	String _longi;
	SharedPreferences sharedpreferences;
	 int sender,receiver,percentage;
	 String status;
	 Post _instance;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_post); 
		sp=(Spinner) findViewById(R.id.spinner1);
		title=(EditText) findViewById(R.id.post_title);
		ArrayList<Integer> data=new ArrayList<Integer>();
		
		_instance = this;
		
		Bundle bundle = getIntent().getExtras();
		
		MyId  =bundle.getString("ID","N/A");
		
		post_title  =bundle.getString("receiver","N/A");
		_lati = bundle.getString("latitude","N/A");
		_longi = bundle.getString("longitude","N/A");
		
		for(int i=0; i<100; i++)
		{
			data.add(i+1);
		}
		
		
		ArrayAdapter<Integer> adap=new ArrayAdapter<Integer>(getBaseContext(),android.R.layout.simple_spinner_dropdown_item,data);
		//adap.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		sp.setAdapter(adap);
		
		class listner implements OnItemSelectedListener
		{

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				// TODO Auto-generated method stub
				percen=(Integer) sp.getItemAtPosition(arg2);
				Toast.makeText(getApplicationContext(),"id is "+percen,Toast.LENGTH_LONG).show();
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub
				
			}

		
			
		}
		sp.setOnItemSelectedListener(new listner());
		
		getActionBar().setTitle(post_title);
		
		Button btn = (Button) findViewById(R.id.showLocation);
		
		btn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				
				Intent a= new Intent(_instance,Location.class);
				Intent b=new Intent(_instance,UserLocation.class);
				//friend_json_obj=friend_json_arr.getJSONObject(arg2);
				Bundle bundle = new Bundle();
				bundle.putString("ID",MyId);
				
				bundle.putString("name",post_title);
				bundle.putString("longitude",_longi);
				bundle.putString("latitude",_lati );
				
				a.putExtras(bundle);
				b.putExtras(bundle);
				startActivity(b);
				startActivity(a);
				
				
				
			}
		});
		
	}
	
	public void sendPost(View v)
	{
		Log.d("web","fnction statr");
		//Toast.makeText(getApplicationContext(), "star6 function", Toast.LENGTH_LONG).show();
		sharedpreferences=getSharedPreferences(MainActivity.MyPREFERENCES,Context.MODE_PRIVATE);
		
		percentage=percen;
		sender=sharedpreferences.getInt("id",0);
		receiver=getIntent().getExtras().getInt("receiver");
		status=getIntent().getExtras().getString("status");
		post_title=title.getText().toString();
		
	Thread thread=	new Thread() {
			@Override
			public void run() {
				try {
					Log.d("web","thread statr");
					SoapObject request = new SoapObject(NAMESPACE,
							METHOD_NAME);
					request.addProperty("title",title.getText().toString());
					request.addProperty("percentage",percentage);
					request.addProperty("sender",sender);
					request.addProperty("receiver",receiver);
					request.addProperty("status",status);
					Log.d("web","property added");
				
					SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
					envelope.dotNet = true;
					envelope.setOutputSoapObject(request);
					HttpTransportSE androidHttpTransport = new HttpTransportSE(
							URL);
					Log.d("web", "before call");
					androidHttpTransport.debug = true;

					androidHttpTransport.call(SOAP_ACTION, envelope);
					Log.d("web","after call of post");
					System.out.println(androidHttpTransport.requestDump);

					SoapObject response = (SoapObject) envelope.bodyIn;
					webResponse = response.toString();
					Log.d("web", "before response");
					 split = webResponse.substring(webResponse.indexOf("=") + 1,webResponse.length() - 3);
					 obj=new JSONObject(split);
					runOnUiThread(new Runnable() {

						@Override
						public void run() {
							Toast.makeText(getApplicationContext(),split,Toast.LENGTH_LONG).show();
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
				  Intent i=new Intent(getApplicationContext(),Working.class);
				  startActivity(i);
				  
			  }
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
		catch(Exception ee)
		{
			Log.d("web","my exception become");
		}
		
	}

	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.post, menu);
		return true;
	}

}
