package model.entity;

import except.*; 

public interface Section{

	public int getId();
	public Course getCourse() throws PortsException;
	public int getCourseId();
	public Faculty getFaculty() throws PortsException;
	public int getFacultyId();
	public String getDays();
	public String getPeriod();

	public void setId( int id );
	public void setCourse( int course );
	public void setFaculty( int faculty );
	public void setDays( String days );
	public void setPeriod( String period );
}
