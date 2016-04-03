package model.entity.impl;

import persist.*;
import except.*;
import model.entity.*;

public class SectionImpl implements Section{

	private int id;
	private int course;
	private int faculty;
	private String days;
	private String period;

	public SectionImpl( int course, int faculty, String days, String period ){
		this.id = 0;
		this.course = course;
		this.faculty = faculty;
		this.days = days;
		this.period = period;
	}

	public SectionImpl( int id, int course, int faculty, String days, String period ){
		this.id = id;
		this.course = course;
		this.faculty = faculty;
		this.days = days;
		this.period = period;
	} 
	

	public int getId(){ return id; }
	public Course getCourse() throws PortsException{
		PersistenceModule pm = PersistenceModuleFactory.createPersistenceModule();
		return pm.restoreCourse(course);
	}
	public int getCourseId(){ return course; }
	public Faculty getFaculty() throws PortsException{
		PersistenceModule pm = PersistenceModuleFactory.createPersistenceModule();
		return pm.restoreFaculty(faculty);
	}
	public int getFacultyId(){ return faculty; }
	public String getDays(){ return days; }
	public String getPeriod(){ return period; }

	public void setId( int id ){ this.id = id; }
	public void setCourse( int course ){ this.course  = course; }
	public void setFaculty( int faculty ){ this.faculty  = faculty; }
	public void setDays( String days ){ this.days = days; }
	public void setPeriod( String period ){ this.period = period; }
}
