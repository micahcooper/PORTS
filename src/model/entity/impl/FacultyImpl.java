package model.entity.impl;

import model.entity.*;

public class FacultyImpl implements Faculty{
	
	private int id;	private String name;	private String title;
	private String address;
		
	public FacultyImpl( String name, String title, String address ){
		this.id = 0;
		this.name = name;
		this.title = title;
		this.address = address;
	}

	public FacultyImpl( int id, String name, String title, String address ){
		this.id = id;
		this.name = name;
		this.title = title;
		this.address = address;
	}
	
	public int getId(){ return id; }
	public String getName(){ return name; }
	public String getTitle(){ return title; }
	public String getAddress(){ return address; }
	public void setId(int id){ this.id = id; }
	public void setName(String name){ this.name = name; }
	public void setTitle(String title){ this.title = title; }
	public void setAddress(String address){ this.address = address; }
	
}