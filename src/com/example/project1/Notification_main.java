package com.example.project1;

import java.io.IOException;
import java.util.ArrayList;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import org.xmlpull.v1.XmlPullParserException;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnCreateContextMenuListener;
import android.webkit.WebView.FindListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gcm.GCMRegistrar;

public class Notification_main extends Service {
	//static final String SERVER_URL = "http://"+MainActivity.ip+"/gcm_server_php/register.php";
	static final String DISPLAY_MESSAGE_ACTION = "com.example.project1.DISPLAY_MESSAGE";
	private static String SOAP_ACTION = "http://"+MainActivity.ip+"/Soap/get_notification_Server/getNotification";
	private static String NAMESPACE = MainActivity.ip;
	private static String METHOD_NAME = "getNotification";
	private static String URL = "http://"+MainActivity.ip+"/Soap/get_notification_Server.php";
	SharedPreferences sharedpreferences;
    private static final String TAG = "GCMIntentService";
    String webResponse;
	String regId;
	public static String Notification_message;
	public static int Notification_sender;
	public static String Notification_category;
	public static ArrayList<Notification_information> list=new ArrayList<Notification_information>();
	public static int [] Sender_resquet_id=new int[5000] ;
	public static int [] Receiver_resquet_id=new int [5000];


	static final String SENDER_ID = "703532425241";
	AsyncTask<Void, Void, Void> mRegisterTask;

	public void onCreate(Bundle savedInstanceState) {
		Toast.makeText(getApplicationContext(), "dadad",Toast.LENGTH_LONG).show();
	
		
	}

	/**
	 * Receiving push messages
	 * */
	private final BroadcastReceiver mHandleMessageReceiver = new BroadcastReceiver() {
		
		@Override
		public void onReceive(Context context, Intent intent) {
			Notification_message = intent.getExtras().getString("message");
			Notification_sender=Integer.valueOf(intent.getExtras().getString("sender"));
			Notification_category = intent.getExtras().getString("category");
		      SharedPreferences s=getSharedPreferences(MainActivity.MyPREFERENCES,Context.MODE_PRIVATE);
		    list.add(new Notification_information(Notification_message,Notification_sender,s.getInt("id",0),Notification_category));
			WakeLocker.acquire(getApplicationContext());
			WakeLocker.release();
		}
	};

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		// TODO Auto-generated method stub
		sharedpreferences=getSharedPreferences(MainActivity.MyPREFERENCES,Context.MODE_PRIVATE);
		GCMRegistrar.checkDevice(this);
		GCMRegistrar.checkManifest(this);
		registerReceiver(mHandleMessageReceiver, new IntentFilter(DISPLAY_MESSAGE_ACTION));

		regId = GCMRegistrar.getRegistrationId(this);

		if (regId.equals("")) {
			GCMRegistrar.register(this, SENDER_ID);
			GCMRegistrar.setRegisteredOnServer(this,true);
			regId = GCMRegistrar.getRegistrationId(this);
						Log.d("web", "Register kar raha hon ");
						Log.d("web", "Register ho "+regId);
		} else {

			// Device is already registered on GCM
			if (GCMRegistrar.isRegisteredOnServer(this)) {
				// Skips registration.
				Toast.makeText(getApplicationContext(),
						"Already registered with GCM", Toast.LENGTH_LONG)
						.show();
			}
		}
		//regId = GCMRegistrar.getRegistrationId(this);
		/*new Thread() {
			@Override
			public void run() {
		try {
			Log.d("web","service thread call");
			SoapObject request = new SoapObject(NAMESPACE,METHOD_NAME);
			Log.d("web","id "+ sharedpreferences.getInt("id",0));
		
			request.addProperty("Registration",Integer.toString(sharedpreferences.getInt("id",0)));
			SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
			envelope.dotNet = true;
			envelope.setOutputSoapObject(request);
			HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);
			Log.d("web", "before call of servrice call");
			androidHttpTransport.debug = true;

			androidHttpTransport.call(SOAP_ACTION, envelope);
			Log.d("web","done");
			System.out.println(androidHttpTransport.requestDump);

			SoapObject response = (SoapObject) envelope.bodyIn;

		}

		catch (NullPointerException o) {

		}
		catch (XmlPullParserException e)
		{
			
		}
		catch(IOException i)
		{
			
		}
		catch (Exception e) {
			// TODO: handle exception
			Log.d("web",e.toString());
		}
	}
	}.start();*/
		return super.onStartCommand(intent, flags, startId);
	}

	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}

}