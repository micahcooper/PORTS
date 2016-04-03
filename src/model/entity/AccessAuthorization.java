package model.entity;

public interface AccessAuthorization {

	public int getId();
	public String getUsername();
	public String getPassword();
	public int getRole();	
	
	public void setId (int id);
	public void setUsername (String name);
	public void setPassword (String password);	public void setRole (int role);

}