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
import android.app.ActionBar;
import android.app.Activity;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class Result extends Activity {
	private static String SOAP_ACTION = "http://"+MainActivity.ip+"/Soap/get_all_user_server.php/getUser";
	private static String NAMESPACE = MainActivity.ip;
	private static String METHOD_NAME = "getUser";
	private static String URL = "http://"+MainActivity.ip+"/Soap/get_all_user_server.php";
	
	private static String SOAP_ACTION1 = "http://"+MainActivity.ip+"/Soap/friend_request_server.php/friendRequest";
	private static String NAMESPACE1 = MainActivity.ip;
	private static String METHOD_NAME1 = "friendRequest";
	private static String URL1 ="http://"+MainActivity.ip+"/Soap/friend_request_server.php";
	
	String webResponse;
	String webResponse1;
	JSONArray arr;
	JSONObject obj;
	 String receiver,sender;
	 Custom_adapter adapter;
	 ArrayList<Person> item=new ArrayList<Person>();
	ListView listSearch; 
	String split;
	SharedPreferences sharedpreferences;
	TextView result_count;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_result);
		sharedpreferences=getSharedPreferences(MainActivity.MyPREFERENCES,Context.MODE_PRIVATE);
		  ActionBar actionBar = getActionBar();
		  listSearch=(ListView) findViewById(R.id.listS);
		  adapter=new Custom_adapter(this,R.layout.notification_custom1,item);
			listSearch.setAdapter(adapter);
			
			result_count=(TextView) findViewById(R.id.result_count);
	        actionBar.setDisplayHomeAsUpEnabled(true);
		searchUser(getIntent());
		
	}
	
	public void searchUser(Intent intent)
	{
		
		final String name=intent.getStringExtra(SearchManager.QUERY);
		
		
		new Thread() {
			@Override
			public void run() {
				try {

					SoapObject request = new SoapObject(NAMESPACE,
							METHOD_NAME);
					request.addProperty("name",name);

					SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
					envelope.dotNet = true;
					envelope.setOutputSoapObject(request);
					HttpTransportSE androidHttpTransport = new HttpTransportSE(
							URL);
					
					androidHttpTransport.debug = true;

					androidHttpTransport.call(SOAP_ACTION, envelope);
					System.out.println(androidHttpTransport.requestDump);

					SoapObject response = (SoapObject) envelope.bodyIn;
					webResponse = response.toString();
					
					 split = webResponse.substring(webResponse.indexOf("=") + 1,webResponse.length() - 3);
					
					arr=new JSONArray(split);
					obj=arr.getJSONObject(0);
				
					
					for(int i=0; i<arr.length(); i++)
					{
						try {
							obj=arr.getJSONObject(i);
							item.add(new Person(obj.getString("id"),obj.getString("full_name"), obj.getString("latitude"), obj.getString("longitude")));
						
						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						
						
					}
					runOnUiThread(new Runnable() {
						
						@Override
						public void run() {
							// TODO Auto-generated method stub
							if(item.size()!=0)
							{
								result_count.setVisibility(View.GONE);
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
		
		}.start();
																			

		listSearch.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,long arg3) {
				
				try {
				
					obj=arr.getJSONObject(arg2);
					receiver=obj.getString("id");
					
				} catch (JSONException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				sender=Integer.toString(sharedpreferences.getInt("id",0));
			Thread thread=	new Thread() {
					@Override
					public void run() {
						try {

							SoapObject request = new SoapObject(NAMESPACE1,
									METHOD_NAME1);
							request.addProperty("sender",sender);
							request.addProperty("receiver",receiver);
							
							request.addProperty("Category","A");
							SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
							envelope.dotNet = true;
							envelope.setOutputSoapObject(request);
							HttpTransportSE androidHttpTransport = new HttpTransportSE(URL1);
							androidHttpTransport.debug = true;
							
							androidHttpTransport.call(SOAP_ACTION1, envelope);
						
							System.out.println(androidHttpTransport.requestDump);
							SoapObject response = (SoapObject) envelope.bodyIn;
							webResponse = response.toString();
							split = webResponse.substring(webResponse.indexOf("=") + 1,webResponse.length() - 3);
							 obj=new JSONObject(split);
							runOnUiThread(new Runnable() {

								@Override
								public void run() {
										try {
											Toast.makeText(getApplicationContext(),obj.getString("status"),Toast.LENGTH_SHORT).show();
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
						  Intent i=new Intent(getApplicationContext(),Working.class);
						  startActivity(i);
						  
					  }
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
		});
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.result, menu);
		return true;
	}

}
