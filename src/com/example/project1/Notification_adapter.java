package com.example.project1;

import java.util.List;
import java.util.Objects;

import android.app.Activity;
import android.content.Context;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class Notification_adapter extends ArrayAdapter<Notification_information> {

	 Context context;
	    int layoutResourceId;   
	    List<Notification_information> data;
	   /* important note
	    * notification_custom.xml file use for that adapter
	    */
	
 public Notification_adapter(Context context, int layoutResourceId,List<Notification_information>data) {
	 super(context, layoutResourceId, data);
     this.layoutResourceId = layoutResourceId;
     this.context = context;
     this.data = data;
 }

 @Override
 public View getView(int position, View convertView, ViewGroup parent) {
     View row = convertView;
     NotificationHolder holder = null;
    
     if(row == null)
     {
         LayoutInflater inflater = ((Activity)context).getLayoutInflater();
         row = inflater.inflate(layoutResourceId, parent, false);
        
         holder = new NotificationHolder();
         holder.text = (TextView)row.findViewById(R.id.tvName);
         holder.im=(ImageView)row.findViewById(R.id.tvImage);
         
        
        
         row.setTag(holder);
     }
     else
     {
         holder = (NotificationHolder)row.getTag();
     }
    
     Notification_information weather = data.get(position);
     
     holder.text.setText(weather.message);
     
    
    
     return row;
 }
 public void Remove(int position)
 {
	 data.remove(position);
 }
	public static class NotificationHolder
	{
		ImageView im;
		TextView text;
	}

}
