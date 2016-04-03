package model.entity.impl;

import model.entity.*;

public class DepartmentImpl implements Department{
	
	private int id;
	private String name;
	
	public DepartmentImpl( String name ){
		id = 0;
		this.name = name;
	} 	

	public DepartmentImpl( int id, String name){
		this.id = id;
		this.name = name;	
	}

	public String getName(){return name;}
	public int getId(){return id;}

	public void setId( int id ){this.id = id;}
	public void setName( String name ){this.name = name;}
}
