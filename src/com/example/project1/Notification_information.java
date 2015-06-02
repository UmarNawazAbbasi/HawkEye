package com.example.project1;

import android.service.notification.NotificationListenerService;
import android.widget.ArrayAdapter;

public class Notification_information {
	public String message;
	public int Sender_id;
	public int Receiver_id;
	public String category;
	
	public Notification_information()
	{
		message="";
		category="";
		Sender_id=0;
		Receiver_id=0;
	}
	
	public Notification_information(String s,int sen, int rec,String c)
	{
		message=s;
		Sender_id=sen;
		Receiver_id=rec;
		category=c;
	}
	
}

