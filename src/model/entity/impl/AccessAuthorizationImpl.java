package model.entity.impl;

import model.entity.*;

public class AccessAuthorizationImpl implements AccessAuthorization {

	private int id;
	private String name;
	private String pass;
	private int role;
	
	public AccessAuthorizationImpl(int id, String name, String pass, int role){
		this.id = id;
		this.name = name;
		this.pass = pass;
		this.role = role;
	}
	
	public int getId(){ return id; }
	public String getUsername(){ return name; }
	public String getPassword(){ return pass; }
	public int getRole(){ return role; }
		
	public void setId (int id){ this.id = id; }
	public void setUsername (String name){ this.name = name; }
	public void setPassword (String pass){ this.pass = pass; }	public void setRole (int role){ this.role = role; }

}