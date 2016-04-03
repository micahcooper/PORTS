package model.entity.impl;

import persist.*;
import except.*;
import model.entity.*;

public class CourseImpl implements Course{

	private int id;
	private int dept;
	private String name;
	private String desc;
	
	public CourseImpl(int dept, String name, String desc){
		this.id = 0;
		this.dept = dept;
		this.name = name;
		this.desc = desc;
	}
	
	public CourseImpl(int id, int dept, String name, String desc){
		this.id = id;
		this.dept = dept;
		this.name = name;
		this.desc = desc;
	}
	
	public int getId(){return id;}
	public Department getDept() throws PortsException{
		PersistenceModule pm = PersistenceModuleFactory.createPersistenceModule();
		return pm.restoreDepartment(dept);
	}
	public int getDeptId(){ return dept; }
	public String getName(){return name;}
	public String getDesc(){return desc;}

	public void setId( int id ){this.id = id;}
	public void setDept( int dept ){this.dept = dept;}
	public void setName( String name ){this.name = name;}
	public void setDesc( String desc ){this.desc = desc;}
}
