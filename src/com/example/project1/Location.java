package com.example.project1;

import java.io.IOException;
import java.io.InputStream;
import java.security.PrivateKey;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.security.auth.PrivateCredentialPermission;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.internal.fn;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import com.example.project1.R;

import android.location.Criteria;

import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.Menu;
import android.widget.TextView;
import android.widget.Toast;

public class Location extends Activity implements LocationListener{

	String MyId;
	GPSTracker gps;
	double latitude=33.693605;
double longitude=73.064285;
	String fname;
    //LatLng islamabad = new LatLng(33.693605, 73.064285);
final static String ip="192.168.189.1";
	
	private GoogleMap googleMap;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_location);
		ActionBar action=getActionBar();
	
		
		action.setHomeButtonEnabled(true);
		Bundle bundle = getIntent().getExtras();
		fname	=bundle.getString("name","N/A");
		String lat = bundle.getString("latitude","N/A");
		String longi = bundle.getString("longitude","N/A");
		initilizeMap();
		MyId  =bundle.getString("ID","N/A");
		 Log.d("web","function start");
		
		 gps = new GPSTracker(this);
		 initilizeMap();
		 if(!lat.equals("N/A") && !longi.equals("N/A"))
			{
				latitude = Double.parseDouble(lat);
				longitude = Double.parseDouble(longi);
				AddMarker(latitude,longitude,fname);
			}
		 
		Toast.makeText(getBaseContext(), "My id id "+MyId, Toast.LENGTH_LONG).show(); 
	//put ur code here
	/*	 LocationManager lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

		   lm.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, this);

		googleMap = ((MapFragment) getFragmentManager().findFragmentById(R.id.map))
		    .getMap();
		//AddMarker(latitude, longitude);
		 //Function Add Marker
		 
		 /*
		 Log.d("web","gps object created");

			// check if GPS enabled		
	        if(gps.canGetLocation()){
	        	
	        	 latitude = gps.getLatitude();
	        	 longitude = gps.getLongitude();
	        	 
	        	Geocoder geo=new Geocoder(getApplicationContext(), Locale.getDefault());
	        	 Log.d("web","Geo object created");
	        	List<Address> addresses = null;
	        	
	            try {
	               addresses = geo.getFromLocation(latitude,longitude, 1);
	            } catch (IOException e1) {
	            	 Log.d("web","io excemptop");
	               
	            } catch (IllegalArgumentException e2) {
	              Log.d("web","i legla arguemnt");
	              
	            }
	            if(addresses != null &&  addresses.size()>0 )
	            {
		            Address address = addresses.get(0);
		            Log.d("web","address size "+addresses.size());
		            String addressText = String.format(
		                    "%s, %s, %s",
		                    // If there's a street address, add it
		                    address.getMaxAddressLineIndex() > 0 ?
		                    address.getAddressLine(0) : "",
		                    // Locality is usually a city
		                    address.getLocality(),
		                    // The country of the address
		                    address.getCountryName());
		        	
		        	
	            }
	            
	        }
	        else
	        {
	        	gps.showSettingsAlert();
	        	
	        }
	       */
		if(fname.equals("N/A"))
			getActionBar().setTitle("My Location");
		else
			getActionBar().setTitle(fname);
		
	       
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.location, menu);
		return true;
	}
private void AddMarker (double lat,double lon,String fan)
{
	
	//Toast.makeText(getApplicationContext(), "Hello its Add marker function", Toast.LENGTH_LONG).show();
	// create marker
	 MarkerOptions marker = new MarkerOptions().position(new LatLng(latitude,longitude)).title(fan);
	 marker.icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_launcher)); 
	 // adding marker
	 //googleMap.setMyLocationEnabled(true);
	 googleMap.addMarker(marker);
	 
}
	private void initilizeMap() {
        if (googleMap == null) {
        	googleMap = ((MapFragment) getFragmentManager().findFragmentById(
                    R.id.map)).getMap();
        	googleMap.setMyLocationEnabled(true);
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
	
	


	@Override
	public void onLocationChanged(android.location.Location location) {
		// TODO Auto-generated method stub
		
		//googleMap.clear();

		   MarkerOptions mp = new MarkerOptions();

		   mp.position(new LatLng(location.getLatitude(), location.getLongitude()));

		   mp.title("my position");
		   mp.icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_launcher));
			  
		   googleMap.addMarker(mp);
		   googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(
		    new LatLng(location.getLatitude(), location.getLongitude()), 16));
		   latitude=location.getLatitude();
		longitude=location.getLongitude();
//Toast.makeText(getBaseContext(), "latitude"+latitude+"longitude"+longitude, Toast.LENGTH_LONG).show();
try {

    InputStream is = null;
    List<NameValuePair> nvp = new ArrayList<NameValuePair>(1);
    nvp.add(new BasicNameValuePair("lati", Double.toString(latitude)));
    nvp.add(new BasicNameValuePair("longi", Double.toString(longitude)));

    try {
        HttpClient httpclient = new DefaultHttpClient();
        HttpPost httppost = new HttpPost(ip + "CurrentLoc.php");

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

}
