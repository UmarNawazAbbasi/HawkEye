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
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class Custom_adapter extends BaseAdapter {
	Context context;
    int layoutResourceId;   
    List<Person> data;
    
    
    public Custom_adapter(Context context, int layoutResourceId,List<Person>data) {
        this.layoutResourceId = layoutResourceId;
        this.context = context;
        this.data = data;
    }
    
    public static class NotificationHolder
    {
    	ImageView im;
    	ImageView im1;
    	TextView text;
    }
        
	public List<Person> getData() {
		return data;
	}
	public void setData(List<Person> data) {
		this.data = data;
	}
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		
		return data.size();
	}
	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return data.get(arg0);
	}
	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return arg0;
	}
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		View row = convertView;
        NotificationHolder holder = null;
       
        if(row == null)
        {
            LayoutInflater inflater = ((Activity)context).getLayoutInflater();
            row = inflater.inflate(layoutResourceId, parent, false);
           
            holder = new NotificationHolder();
            holder.text = (TextView)row.findViewById(R.id.textNoti);
            holder.im=(ImageView)row.findViewById(R.id.imageNoti);
            
            holder.im1=(ImageView)row.findViewById(R.id.imageNoti);
           
           
            row.setTag(holder);
        }
        else
        {
            holder = (NotificationHolder)row.getTag();
        }
       
        String weather = data.get(position).get_fullName();
        
        holder.text.setText(weather);
        
       
       
        return row;
	}

/* important note
 * notification_custom1.xml use for layout fot that adapter
 */

}
