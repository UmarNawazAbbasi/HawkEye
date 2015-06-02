package com.example.project1;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import org.xmlpull.v1.XmlPullParserException;

import android.app.AlertDialog;
import android.app.NotificationManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.project1.Notification_main;

import com.example.project1.Working;

/**
 * A simple {@link android.support.v4.app.Fragment} subclass.
 * 
 */
public class Sorry extends Fragment {
	private static String SOAP_ACTION = "http://"+MainActivity.ip+"/Soap/accept_request_server.php/acceptRequest";
	private static String NAMESPACE = MainActivity.ip;
	private static String METHOD_NAME = "acceptRequest";
	private static String URL = "http://"+MainActivity.ip+"/Soap/accept_request_server.php";
   static ListView List;
    ArrayAdapter<String>adapter;
    ArrayList<String> item=new ArrayList<String>();
    String webResponse;
    Notification_adapter a;
    int pos,sen,rec;
    SharedPreferences sharedpreferences;
	Editor editor;
	TextView Notification_count;

	public Sorry() {
		// Required empty public constructor
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		setHasOptionsMenu(true);
		// Inflate the layout for this fragment
	
		 View V = inflater.inflate(R.layout.fragment_sorry, container, false);
		 List=(ListView)V.findViewById(R.id.listSearch1);
		 Notification_count= (TextView) V.findViewById(R.id.Notification_count);
		// Notification_main.list.add(new Notification_information("notification", 1, 2, "A"));
		// Notification_main.list.add(new Notification_information("notification 2", 1, 2, "A"));
			ListView List=(ListView) V.findViewById(R.id.listSearch1);
			a=new Notification_adapter(getActivity(),R.layout.notification_cutom,Notification_main.list);
			List.setAdapter(a);
			Notification_count.setText("Notifications("+Notification_main.list.size()+")");
			
		 pos=-1;
		 List.setOnItemClickListener(new AdapterView.OnItemClickListener() {
		
		
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				if(Notification_main.list.get(arg2).category.equals("A"))
				{
					sen=(Notification_main.list.get(arg2)).Sender_id;
					rec=(Notification_main.list.get(arg2)).Receiver_id;
					Notification_main.list.remove(arg2);
					AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
					builder.setMessage("Confirmation of request?")
					.setPositiveButton("COnfirm", new DialogInterface.OnClickListener() {
					           public void onClick(DialogInterface dialog, int id) {
					        	   new Thread() {
										@Override
										public void run() {
											try {
	
												
												SoapObject request = new SoapObject(NAMESPACE,
														METHOD_NAME);
												request.addProperty("Sender",sen);
												request.addProperty("Receiver",rec);
	
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
											getActivity().runOnUiThread(new Runnable() {
												
												@Override
												public void run() {
													// TODO Auto-generated method stub
													Notification_count.setText("Notifications("+Notification_main.list.size()+")");
													
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
					a.notifyDataSetChanged();
					
					
				}
				else if(Notification_main.list.get(arg2).category.equals("B"))
				{
					Intent intent=new Intent(getActivity(),Post.class);
					intent.putExtra("status","receiver");
					intent.putExtra("receiver",(Notification_main.list.get(arg2)).Sender_id);
					Notification_main.list.remove(arg2);
					startActivity(intent);
					a.notifyDataSetChanged();
					Notification_count.setText("Notifications("+Notification_main.list.size()+")");
				}
				else if(Notification_main.list.get(arg2).category.equals("C"))
				{
					Intent intent=new Intent(getActivity(),Simple_Notification.class);
					intent.putExtra("message",(Notification_main.list.get(arg2)).message);
					intent.putExtra("sender",(Notification_main.list.get(arg2)).Sender_id);
					intent.putExtra("receiver",(Notification_main.list.get(arg2)).Receiver_id);
					intent.putExtra("cat","C");
					Notification_main.list.remove(arg2);
					startActivity(intent);
					a.notifyDataSetChanged();
					Notification_count.setText("Notifications("+Notification_main.list.size()+")");
				}
				else if(Notification_main.list.get(arg2).category.equals("D"))
				{
					Intent intent=new Intent(getActivity(),Simple_Notification.class);
					intent.putExtra("message",(Notification_main.list.get(arg2)).message);
					intent.putExtra("sender",(Notification_main.list.get(arg2)).Sender_id);
					intent.putExtra("receiver",(Notification_main.list.get(arg2)).Receiver_id);
					intent.putExtra("cat","D");
					Notification_main.list.remove(arg2);
					startActivity(intent);
					a.notifyDataSetChanged();
					Notification_count.setText("Notifications("+Notification_main.list.size()+")");
				}
				
			}
			
		});
		
		return V;
	}
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		if(item.toString().equals("Refresh"))
		{
			a.notifyDataSetChanged();
		}
		if(item.toString().equals("Logout"))
		{
			sharedpreferences = getActivity().getSharedPreferences(MainActivity.MyPREFERENCES,
					Context.MODE_PRIVATE);
			editor = sharedpreferences.edit();
			editor.clear();
			editor.commit();
			Intent intent = new Intent(getActivity(), Sign_signup.class);
			startActivity(intent);
			getActivity().finish();
		}
		else if (item.toString().equals(("Location"))) { //lOCATION .Jv
		Intent intent = new Intent(getActivity(), UserLocation.class);
			startActivity(intent);
			
		}
		return super.onOptionsItemSelected(item);
	}
	public void button()
	{
		
	}
	
	
}
