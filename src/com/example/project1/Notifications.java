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

import android.app.Activity;
import android.app.SearchManager;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView.FindListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

/**
 * A simple {@link android.support.v4.app.Fragment} subclass. Activities that
 * contain this fragment must implement the
 * {@link Notifications.OnFragmentInteractionListener} interface to handle
 * interaction events. Use the {@link Notifications#newInstance} factory method
 * to create an instance of this fragment.
 * 
 */
public class Notifications extends Fragment {
	// TODO: Rename parameter arguments, choose names that match
	// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
	private static String SOAP_ACTION = "http://"+MainActivity.ip+"/Soap/get_feed_server.php/getFeeds";
	private static String NAMESPACE = MainActivity.ip;
	private static String METHOD_NAME = "getFeeds";
	private static String URL = "http://"+MainActivity.ip+"/Soap/get_feed_server.php";
	
	String webResponse;
	JSONArray arr;
	JSONObject obj;
	String split;
	 SharedPreferences sharedpreferences;
	
	TextView text,list_count;
	ListView list;
	ArrayList<String> item=new ArrayList<String>();
	Custom_adapter1 adapter;
	Editor editor;
	public Notifications() {

	}


	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		 setHasOptionsMenu(true);
		 View V = inflater.inflate(R.layout.fragment_notifications, container, false);
		 text=(TextView) V.findViewById(R.id.notification_textview);
		 list_count= (TextView) V.findViewById(R.id.Notificatoin_count);
		 list=(ListView) V.findViewById(R.id.listView1);
		 sharedpreferences = getActivity().getSharedPreferences(MainActivity.MyPREFERENCES,Context.MODE_PRIVATE);
		 text.setText("Welcome "+sharedpreferences.getString("full_name", ""));		
			new Thread() {
				@Override
				public void run() {
					try {

						SoapObject request = new SoapObject(NAMESPACE,
								METHOD_NAME);
						request.addProperty("name",sharedpreferences.getInt("id",0));

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
								item.add(obj.getString("news"));
							} catch (JSONException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							
							
						}

						
						
						getActivity().runOnUiThread(new Runnable() {

							@Override
							public void run() {
								// TODO Auto-generated method stub
								
									adapter=new Custom_adapter1(getActivity(),R.layout.notification_custom2,item);
									list.setAdapter(adapter);
									if(item.isEmpty()==true)
									{
										list_count.setText("No news feed");
									}
									else
									{
										list_count.setVisibility(View.GONE);
									
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

	
		 return V;
		
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
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
		else if (item.toString().equals(("Location"))) {

			Intent intent = new Intent(getActivity(), UserLocation.class);
			startActivity(intent);
			
		}
		return super.onOptionsItemSelected(item);
	}


}
