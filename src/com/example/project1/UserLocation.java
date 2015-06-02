package com.example.project1;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import com.example.project1.*;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import android.app.ActionBar;
import android.app.Activity;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.widget.Toast;

public class UserLocation extends Activity implements LocationListener {
	double latitude=33.693605;
double longitude=73.064285;
String MyId;
	  private GoogleMap googleMap;
		@Override
		protected void onCreate(Bundle savedInstanceState) {
			super.onCreate(savedInstanceState);
			setContentView(R.layout.activity_location);
			 		Toast.makeText(getApplicationContext(), "Hello its working", Toast.LENGTH_LONG).show();
			 		//googleMap=((SupportMapFragment)getSupportFragmentManager().findFragmentById(R.id.map))
					Bundle bundle = getIntent().getExtras();
			 		MyId  =bundle.getString("ID","N/A");
			       Toast.makeText(getApplicationContext(), " User Location is"+MyId, Toast.LENGTH_LONG).show();
			 
			 		initilizeMap();
			        			
			 		
		}
		private void initilizeMap() {
	        if (googleMap == null) {
	        	googleMap = ((MapFragment) getFragmentManager().findFragmentById(
	                    R.id.map)).getMap();
	        	if (googleMap != null) {
	                setUpMap();
	            }
	        }
	    }
		 private void setUpMap() {
			 googleMap.addMarker(new MarkerOptions().position(new LatLng(0, 0)).title("Marker"));
		        LatLng islamabad = new LatLng(33.693605, 73.064285);

		        googleMap.setMyLocationEnabled(true);
		        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(islamabad, 13));
		        //mMap.addMarker(new MarkerOptions().position(new LatLng(33.693605, 73.064285)).title("Marker"));
		    }
	    @Override
	    protected void onResume() {
	        super.onResume();
	        initilizeMap();
	        googleMap.setMyLocationEnabled(true);
	        LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
	        Criteria criteria = new Criteria();
	        String bestProvider = locationManager.getBestProvider(criteria, true);
	        Location location = locationManager.getLastKnownLocation(bestProvider);
	        if (location != null) {
	            onLocationChanged(location);
	        }
	        locationManager.requestLocationUpdates(bestProvider, 20000, 0, this);
	    }
	   

		@Override
		public boolean onCreateOptionsMenu(Menu menu) {
			// Inflate the menu; this adds items to the action bar if it is present.
			getMenuInflater().inflate(R.menu.main, menu);
			return true;
		}
		@Override
		public void onLocationChanged(Location location) {
			// TODO Auto-generated method stub
			  MarkerOptions mp = new MarkerOptions();

			   mp.position(new LatLng(location.getLatitude(), location.getLongitude()));

			   mp.title("my position");
			   mp.icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_launcher));
				  
			   googleMap.addMarker(mp);
			   googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(
			    new LatLng(location.getLatitude(), location.getLongitude()), 16));
			   latitude=location.getLatitude();
			longitude=location.getLongitude();
			try {

			    InputStream is = null;
			    List<NameValuePair> nvp = new ArrayList<NameValuePair>(1);
			    nvp.add(new BasicNameValuePair("lati", Double.toString(latitude)));
			    nvp.add(new BasicNameValuePair("longi", Double.toString(longitude)));
			    nvp.add(new BasicNameValuePair("ID", (MyId)));

				   
			    try {
			        HttpClient httpclient = new DefaultHttpClient();
			        HttpPost httppost = new HttpPost(MainActivity.ip + "Update_Longi.php");

			        httppost.setEntity(new UrlEncodedFormEntity(nvp));
			        HttpResponse response = httpclient.execute(httppost);

			        HttpEntity entity = response.getEntity();

			        is = entity.getContent();

			        String msg = "data added";
			        Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG).show();


			    } catch (ClientProtocolException e) {
			        Log.e("client protocol", e.getMessage());
			        e.printStackTrace();
			    } catch (IOException e) {
			        Log.e("io exception", e.getMessage());
			        e.printStackTrace();

			    }

			} catch (Exception ex) {
			    Log.e("doing background ", ex.getMessage());
			}

				}

		
		@Override
		public void onProviderDisabled(String provider) {
			// TODO Auto-generated method stub
			
		}
		@Override
		public void onProviderEnabled(String provider) {
			// TODO Auto-generated method stub
			
		}
		@Override
		public void onStatusChanged(String provider, int status, Bundle extras) {
			// TODO Auto-generated method stub
			
		}
		

}
