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

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

/**
 * A simple {@link android.support.v4.app.Fragment} subclass.
 * 
 */
public class Messages extends Fragment {
	
	private static String SOAP_ACTION = "http://"+MainActivity.ip+"/Soap/get_friend_server.php/getFriend";
	private static String NAMESPACE = MainActivity.ip;
	private static String METHOD_NAME = "getFriend";
	private static String URL = "http://"+MainActivity.ip+"/Soap/get_friend_server.php";
	
	ListView friendsList;
	Custom_adapter adapter;
	ArrayList<Person> item=new ArrayList<Person>();
	String webResponse;
	JSONArray friend_json_arr;
	JSONObject friend_json_obj;
	SharedPreferences sharedpreferences;
	Editor editor;
	TextView friend_count;
	
	public Messages() {
		// Required empty public constructor
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		setHasOptionsMenu(true);
		View v=inflater.inflate(R.layout.fragment_messages,null);
		friendsList=(ListView) v.findViewById(R.id.friendsList);
		friend_count= (TextView) v.findViewById(R.id.friend_count);
	
		new Thread() {
			@Override
			public void run() {
				try {

					SoapObject request = new SoapObject(NAMESPACE,
							METHOD_NAME);
					SharedPreferences pre=getActivity().getSharedPreferences(MainActivity.MyPREFERENCES,Context.MODE_PRIVATE);
					request.addProperty("name",pre.getInt("id",0));

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
					String split = webResponse.substring(webResponse.indexOf("=") + 1,webResponse.length() - 3);
					friend_json_arr=new JSONArray(split);
					friend_json_obj=friend_json_arr.getJSONObject(0);
					for(int i=0; i<friend_json_arr.length(); i++)
					{
						try {
							friend_json_obj=friend_json_arr.getJSONObject(i);
							item.add(new Person(friend_json_obj.getString("id"),friend_json_obj.getString("full_name"), friend_json_obj.getString("latitude"), friend_json_obj.getString("longitude")));
						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						
						
					}
					getActivity().runOnUiThread(new Runnable() {
						
						@Override
						public void run() {
							if(item.isEmpty()==true)
							{
								friend_count.setVisibility(View.VISIBLE);
								friend_count.setText("No Friend List");
								
							}else
							{
								friend_count.setVisibility(View.GONE);
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
		
		adapter=new Custom_adapter(getActivity(),R.layout.notification_custom1,item);
		friendsList.setAdapter(adapter);
		friendsList.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
					

				try {
					Intent a= new Intent(getActivity(),Post.class);
					//friend_json_obj=friend_json_arr.getJSONObject(arg2);
					Bundle bundle = new Bundle();

					bundle.putString("ID",item.get(arg2).get_Id());
					bundle.putString("receiver",item.get(arg2).get_fullName());
					bundle.putString("longitude",item.get(arg2).get_longitude());
					bundle.putString("latitude",item.get(arg2).get_latitude());
					bundle.putString("status","sender");
					
					a.putExtras(bundle);
					
					startActivity(a);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
					
					
					
				
			}
		
		});
		return v;
	}

		

	@Override
	public boolean onOptionsItemSelected(MenuItem Item) {
		// TODO Auto-generated method stubRe
			if(Item.toString().equals("Refresh"))
			{
				
				button();
				
			}
			
			if(Item.toString().equals("Logout"))
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
			else if (Item.toString().equals(("Location"))) {

				Intent intent = new Intent(getActivity(), Location.class);
				startActivity(intent);
				
			}
		return super.onOptionsItemSelected(Item);
	}
	public void button()
	{
		item.clear();
		
		adapter.notifyDataSetChanged();
			new Thread() {
				@Override
				public void run() {
					try {

						SoapObject request = new SoapObject(NAMESPACE,
								METHOD_NAME);
						SharedPreferences pre=getActivity().getSharedPreferences(MainActivity.MyPREFERENCES,Context.MODE_PRIVATE);
						request.addProperty("name",pre.getInt("id",0));

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
						String split = webResponse.substring(webResponse.indexOf("=") + 1,webResponse.length() - 3);
					
						friend_json_arr=new JSONArray(split);
						friend_json_obj=friend_json_arr.getJSONObject(0);
						
						
						for(int i=0; i<friend_json_arr.length(); i++)
						{
							try {
								friend_json_obj=friend_json_arr.getJSONObject(i);
								item.add(new Person(friend_json_obj.getString("id"),friend_json_obj.getString("full_name"), friend_json_obj.getString("latitude"), friend_json_obj.getString("longitude")));
								
							} catch (JSONException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							
							
						}
						adapter.setData(item);
						
						getActivity().runOnUiThread(new Runnable() {

							@Override
							public void run() {
								if(item.isEmpty()==true)
								{
									Toast.makeText(getActivity(),"done",Toast.LENGTH_LONG).show();
									friend_count.setVisibility(View.VISIBLE);
									friend_count.setText("No Friend List");
									
								}else
								{
									friend_count.setVisibility(View.GONE);
								}
								adapter.notifyDataSetChanged();
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
}
