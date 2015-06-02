package com.example.project1;

import android.R.integer;

public class Person {
	private String _Id;

	private String _fullName;
	private String _latitude;
	private String _longitude;
	
	public Person(String id,String _fullName, String _latitude, String _longitude) {
		this._Id=id;
		this._fullName = _fullName;
		this._latitude = _latitude;
		this._longitude = _longitude;
	}
	public String get_Id() {
		return _Id;
	}
	public void set_Id(String _Id) {
		this._Id = _Id;
	}
	public Person() {
		
		this._fullName = "N/A";
		this._latitude = "N/A";
		this._longitude = "N/A";
	}
	public String get_fullName() {
		return _fullName;
	}
	public void set_fullName(String _fullName) {
		this._fullName = _fullName;
	}
	public String get_latitude() {
		return _latitude;
	}
	public void set_latitude(String _latitude) {
		this._latitude = _latitude;
	}
	public String get_longitude() {
		return _longitude;
	}
	public void set_longitude(String _longitude) {
		this._longitude = _longitude;
	}
	
	
	
}
