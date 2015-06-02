package com.example.project1;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import org.xmlpull.v1.XmlPullParserException;

import android.opengl.Visibility;
import android.os.Bundle;
import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class Simple_Notification extends Activity {
	private static String SOAP_ACTION = "http://"+MainActivity.ip+"/Soap/result_server.php/getResult";
	private static String NAMESPACE = MainActivity.ip;
	private static String METHOD_NAME = "getResult";
	private static String URL = "http://"+MainActivity.ip+"/Soap/result_server.php";
	
	private static String SOAP_ACTION1 = "http://"+MainActivity.ip+"/Soap/accept_request_server.php/acceptRequest";
	private static String NAMESPACE1 = MainActivity.ip;
	private static String METHOD_NAME1 = "acceptRequest";
	private static String URL1 = "http://"+MainActivity.ip+"/Soap/accept_request_server.php";

	String webResponse;
	TextView message;
	Button ok,cancel;
	String status;
	int sen,rec;
	SharedPreferences shared;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_simple__notification);
		ok=(Button) findViewById(R.id.ok_button);
		cancel= (Button)findViewById(R.id.cancel_button);
		message= (TextView) findViewById(R.id.show_notification);
		Log.d("web", getIntent().getExtras().getString("cat"));
		//Log.d("web",getIntent().getExtras().getString("title"));
		//message.setText(getIntent().getExtras().getString("cat"));
		ActionBar actionbar=getActionBar();
		actionbar.setHomeButtonEnabled(true);
		shared=getSharedPreferences(MainActivity.MyPREFERENCES,Context.MODE_PRIVATE);
		if(getIntent().getExtras().getString("cat").equals("D"))
		{
			message.setText(getIntent().getExtras().getString("message"));
			cancel.setVisibility(View.GONE);
			ok.setVisibility(View.GONE);
			
		}
		else if(getIntent().getExtras().getString("cat").equals("C"))
		{
			message.setText(getIntent().getExtras().getString("message"));
			
			
		}
		else if(getIntent().getExtras().getString("cat").equals("B"))
		{
			Log.d("web","its come here");
			message.setText("hahaha got u");
			Intent intent=new Intent(this,Post.class);
			intent.putExtra("status","receiver");
			intent.putExtra("receiver",getIntent().getExtras().getString("sender"));
			startActivity(intent);
		}
		
		else if(getIntent().getExtras().getString("cat").equals("A"))
		{
			Log.d("web","message is "+getIntent().getExtras().getString("message"));
			Log.d("web","sender id id "+ getIntent().getExtras().getString("sender"));
			sen=Integer.valueOf(getIntent().getExtras().getString("sender"));
			rec=shared.getInt("id",0);
			cancel.setVisibility(View.GONE);
			ok.setVisibility(View.GONE);
		
			AlertDialog.Builder builder = new AlertDialog.Builder(this);
			builder.setMessage("Confirmation of request?")
			.setPositiveButton("COnfirm", new DialogInterface.OnClickListener() {
			           public void onClick(DialogInterface dialog, int id) {
			        	   new Thread() {
								@Override
								public void run() {
									try {

										
										SoapObject request = new SoapObject(NAMESPACE1,
												METHOD_NAME1);
										request.addProperty("Sender",sen);
										request.addProperty("Receiver",rec);

										SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
										envelope.dotNet = true;
										envelope.setOutputSoapObject(request);
										HttpTransportSE androidHttpTransport = new HttpTransportSE(
												URL1);
										androidHttpTransport.debug = true;

										androidHttpTransport.call(SOAP_ACTION1, envelope);
										System.out.println(androidHttpTransport.requestDump);

										SoapObject response = (SoapObject) envelope.bodyIn;
										webResponse = response.toString();
									runOnUiThread(new Runnable() {
										
										@Override
										public void run() {
											// TODO Auto-generated method stub
											//Notification_count.setText("Notifications("+Notification_main.list.size()+")");
											
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

			           }
			           
			       })
			       .setNegativeButton("Discard", new DialogInterface.OnClickListener() {
			           public void onClick(DialogInterface dialog, int id) {
			                dialog.cancel();
			           }
			       });
			AlertDialog alert = builder.create();
			alert.show();
			//a.notifyDataSetChanged();
			Intent in=new Intent(this,Working.class);
			startActivity(in);
			
		}
		
	}
	public void OK(View v)
	{
		status="ok";
		sendResponce();
		Intent intent=new Intent(this,Working.class);
		startActivity(intent);
	}
	public void Cancel(View v)
	{
		status="cancel";
		sendResponce();
		Intent intent=new Intent(this,Working.class);
		startActivity(intent);
	}
	public void sendResponce()
	{
		Log.d("prob", "function start");
		sen=getIntent().getExtras().getInt("sender");
		//rec=getIntent().getExtras().getInt("receiver");
		rec=shared.getInt("id",0);
		Log.d("prob", "sender is "+ sen);
		Log.d("prob", "receiver is "+ rec);
		Log.d("prob", "status is "+ status);
	  	   new Thread() {
				@Override
				public void run() {
					try {

						Log.d("prob", "thread start");
						SoapObject request = new SoapObject(NAMESPACE,
								METHOD_NAME);
						request.addProperty("Sender",sen);
						request.addProperty("Receiver",rec);
						request.addProperty("Status",status);
						Log.d("prob", "property added");

						SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
						envelope.dotNet = true;
						envelope.setOutputSoapObject(request);
						HttpTransportSE androidHttpTransport = new HttpTransportSE(
								URL);
						androidHttpTransport.debug = true;

						androidHttpTransport.call(SOAP_ACTION, envelope);
						Log.d("prob", "call done");
						System.out.println(androidHttpTransport.requestDump);

						SoapObject response = (SoapObject) envelope.bodyIn;
						
					
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
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.simple__notification, menu);
		return true;
	}

}
