package model.entity;

import except.*;

public interface Course{

	public int getId();
	public Department getDept() throws PortsException;
	public int getDeptId();
	public String getName();
	public String getDesc();

	public void setId( int course_id );
	public void setDept( int course_dept );
	public void setName( String course_name );
	public void setDesc( String course_desc );
}
