package model.entity;

public interface User {

	public int getId();
	public String getName();
	public String getAddress();
	
	public void setId(int id);
	public void setName(String name);
	public void setAddress(String address);
	
}