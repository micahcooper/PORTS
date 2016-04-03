package model.entity.impl;

import persist.*;
import except.*;
import model.entity.*;
import java.util.Date;

public class ExamImpl implements Exam{
	
	private int id;
	private int section;
	private String name;
	private String genInstr;
	private String speInstr;
	private String dir;
	private int length;
	private Date edate;

	public ExamImpl(int id, int section, String name, String genInstr, String speInstr, String dir, int length, Date edate){
		this.id = id;
		this.section = section;
		this.name = name;
		this.genInstr = genInstr;
		this.speInstr = speInstr;
		this.dir = dir;
		this.length = length;
		this.edate = edate;
	}
	
	public ExamImpl(int section, String name, String genInstr, String speInstr, String dir, int length, Date edate){
		this.id = 0;
		this.section = section;
		this.name = name;
		this.genInstr = genInstr;
		this.speInstr = speInstr;
		this.dir = dir;
		this.length = length;
		this.edate = edate;
	}
	
	public Date getDate(){return edate;}
	public int getId(){return id;}
	public String getName(){return name;}
	public Section getSection() throws PortsException{
		PersistenceModule pm = PersistenceModuleFactory.createPersistenceModule();
		return pm.restoreSection(section);
	}
	public int getSectionId(){ return section; }
	public int getLength(){return length;}
	public String getDirections(){return dir;}
	public String getGeneralInstructions(){return genInstr;}
	public String getSpecialInstructions(){return speInstr;}
	
	public void setDate(Date edate){this.edate = edate;}
	public void setId(int id){this.id = id;}
	public void setName(String name){this.name = name;}
	public void setSection(int section){this.section = section;}
	public void setLength(int length){this.length = length;}
	public void setDirections(String dir){this.dir = dir;}
	public void setGeneralInstructions(String genInstr){this.genInstr = genInstr;}
	public void setSpecialInstructions(String speInstr){this.speInstr = speInstr;}
	
}