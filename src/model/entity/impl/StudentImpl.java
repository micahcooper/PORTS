package model.entity.impl;

import model.entity.*;

public class StudentImpl implements Student{
	
	private int id;	private String name;	private String type;
	private String address;
		
	public StudentImpl( String name, String type, String address ){
		this.id = 0;
		this.name = name;
		this.type = type;
		this.address = address;
	}	
		
	public StudentImpl( int id, String name, String type, String address ){
		this.id = id;
		this.name = name;
		this.type = type;
		this.address = address;
	}
	
	public int getId(){ return id; }
	public String getName(){ return name; }
	public String getType(){ return type; }
	public String getAddress(){ return address; }
	public void setId(int id){ this.id = id; }	
	public void setName(String name){ this.name = name; }
	public void setType(String type){ this.type = type; }
	public void setAddress(String address){ this.address = address; }
	
	public boolean equals(StudentImpl s){
		return s.id == id;
	}	
}