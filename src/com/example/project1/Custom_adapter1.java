package com.example.project1;

import java.util.ArrayList;
import java.util.List;

import com.example.project1.Notification_adapter.NotificationHolder;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class Custom_adapter1 extends ArrayAdapter<String> {
	Context context;
    int layoutResourceId;   
    List<String> data;


/* important note
 * notification_custom2.xml use for layout fot that adapter
 */

public Custom_adapter1(Context context, int layoutResourceId,List<String>data) {
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
        holder.text = (TextView)row.findViewById(R.id.textNoti1);
        holder.im=(ImageView)row.findViewById(R.id.imageNoti1);
 
       
       
        row.setTag(holder);
    }
    else
    {
        holder = (NotificationHolder)row.getTag();
    }
   
    String weather = data.get(position);
    
    holder.text.setText(weather);
    
   
   
    return row;
}
public static class NotificationHolder
{
	ImageView im;
	TextView text;
}
}
