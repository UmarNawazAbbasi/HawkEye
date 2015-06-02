package com.example.project1;

import java.util.ArrayList;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import android.os.Bundle;
import android.app.Activity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.webkit.WebSettings.LayoutAlgorithm;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import android.app.AlertDialog;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.util.Log;
 
import com.example.project1.MainActivity;
import com.example.project1.R;
import com.example.project1.R.drawable;
import com.example.project1.R.string;
import com.google.android.gcm.GCMBaseIntentService;
import com.google.android.gcm.GCMRegistrar;
 
import static com.example.project1.Notification_main.SENDER_ID;

 
public class GCMIntentService extends GCMBaseIntentService {
	static final String SERVER_URL = "http://"+MainActivity.ip+"/gcm_server_php/register.php";
	static final String DISPLAY_MESSAGE_ACTION = "com.androidhive.pushnotifications.DISPLAY_MESSAGE";
	private static String SOAP_ACTION = "http://"+MainActivity.ip+"/Soap/regis_key_server.php/regis_key";
	private static String NAMESPACE = MainActivity.ip;
	private static String METHOD_NAME = "regis_key";
	private static String URL = "http://"+MainActivity.ip+"/Soap/regis_key_server.php";
	SharedPreferences sharedpreferences;
    private static final String TAG = "GCMIntentService";
    String webResponse;
     String regID;
 
    public GCMIntentService() {
    	
        super(SENDER_ID);
       
    }
 
    /**
     * Method called on device registered
     **/
    @Override
    protected void onRegistered(Context context, String registrationId) {
    	sharedpreferences = getSharedPreferences(MainActivity.MyPREFERENCES,
				Context.MODE_PRIVATE);
    	regID=registrationId;
    	Log.d("web","registration id "+regID);
    	new Thread() {
			@Override
			public void run() {
				try {
					SoapObject request = new SoapObject(NAMESPACE,
							METHOD_NAME);
					request.addProperty("id", sharedpreferences.getInt(
							MainActivity.Session_id, 0));
					
					request.addProperty("Registration",regID);
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

				}

				catch (Exception o) {

				}

			}
		}.start();

        GCMRegistrar.setRegisteredOnServer(this,true);
       // ServerUtilities.register(context, MainActivity.name, MainActivity.email, registrationId);
    }
 
    /**
     * Method called on device un registred
     * */
    @Override
    protected void onUnregistered(Context context, String registrationId) {
        Log.i(TAG, "Device unregistered");
        Log.d("web","GCM Onunregister");
        GCMRegistrar.setRegisteredOnServer(this,false);
       // ServerUtilities.unregister(context, registrationId);
    }
 
    /**
     * Method called on Receiving a new message
     * */
    @Override
    protected void onMessage(Context context, Intent intent) {
        Log.i(TAG, "Received message");
        String [] atiq={"Atiq","Rehsman"};
        String message = intent.getExtras().getString("msg");
        Intent i = new Intent("com.example.project1.DISPLAY_MESSAGE");
        i.putExtra("message",message);
        i.putExtra("sender",intent.getExtras().getString("sender"));
        i.putExtra("category",intent.getExtras().getString("cat"));
       // Log.d("web","on message receive "+intent.getExtras().getString("sender"));
        context.sendBroadcast(i);
        generateNotification(context, message,intent.getExtras().getString("sender"),intent.getExtras().getString("cat"),intent.getExtras().getString("title"));
    }
 
    /**
     * Method called on receiving a deleted message
     * */
    @Override
    protected void onDeletedMessages(Context context, int total) {
        Log.i(TAG, "Received deleted messages notification");
        Log.d("web","GCM Ondelete msg");
    }
 
    /**
     * Method called on Error
     * */
    @Override
    public void onError(Context context, String errorId) {
        Log.i(TAG, "Received error: " + errorId);
        Log.d("web","GCM Onerro");
       
    }
 
    @Override
    protected boolean onRecoverableError(Context context, String errorId) {
        // log message
        Log.i(TAG, "Received recoverable error: " + errorId);
        Log.d("web","GCM Onrecoverable");
        return super.onRecoverableError(context, errorId);
    }
 
    /**
     * Issues a notification to inform the user that server has sent a message.
     */
    private void generateNotification(Context context, String message,String sender,String cat,String message_title) {
    	 
    	int icon = R.drawable.ic_launcher;
        long when = System.currentTimeMillis();
        NotificationManager notificationManager = (NotificationManager)
                context.getSystemService(Context.NOTIFICATION_SERVICE);
        Notification notification = new Notification(icon, message, when);
       // Log.d("web",cat);
        String title = "Sorry Messenger";
         
        Intent notificationIntent = new Intent(context,Simple_Notification.class);
        notificationIntent.putExtra("message",message);
        notificationIntent.putExtra("sender",sender);
        notificationIntent.putExtra("cat",cat);
        notificationIntent.putExtra("title",message_title);
        notificationIntent.putExtra("GCMIService",true);
      
        
        // set intent so it does not start a new activity
        notificationIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |
                Intent.FLAG_ACTIVITY_SINGLE_TOP);
        PendingIntent intent =
                PendingIntent.getActivity(context, 0, notificationIntent, 0);
        notification.setLatestEventInfo(context, title,message, intent);
        notification.flags |= Notification.FLAG_AUTO_CANCEL;
         
        // Play default notification sound
        notification.defaults |= Notification.DEFAULT_SOUND;
         
        // Vibrate if vibrate is enabled
        notification.defaults |= Notification.DEFAULT_VIBRATE;
        notificationManager.notify(0, notification);   
        

    }

  
}