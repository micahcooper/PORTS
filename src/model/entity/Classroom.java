package model.entity;

import except.*;

public interface Classroom{

	public int getId();
	public String getRoom();
	public String getBuilding();
	public Department getDept() throws PortsException;
	public int getDeptId();

	public void setId( int id );
	public void setRoom( String room );
	public void setBuilding( String building );
	public void setDept( int dept );

}
