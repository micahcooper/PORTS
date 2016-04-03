package model.entity.impl;

import except.*;
import persist.*;
import model.entity.*;

public class ClassroomImpl implements Classroom{

	private int id;
	private String room;
	private String building;
	private int dept;

	public ClassroomImpl( String room, String building, int dept ){
		this.id = 0;
		this.room = room;
		this.building = building;
		this.dept = dept;
	}

	public ClassroomImpl( int id, String room, String building, int dept ){
		this.id = id;
		this.room = room;
		this.building = building;
		this.dept = dept;
	}

	public int getId(){ return id; }
	public String getRoom(){ return room; }
	public String getBuilding(){ return building; }
	public Department getDept() throws PortsException{
		PersistenceModule pm = PersistenceModuleFactory.createPersistenceModule();
		return pm.restoreDepartment(dept);
	}
	public int getDeptId(){ return dept; }

	public void setId( int id ){ this.id = id; }
	public void setRoom( String room ){ this.room = room; }
	public void setBuilding( String building ){ this.building = building; }
	public void setDept( int dept ){ this.dept = dept; }
}
