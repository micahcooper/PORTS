package model.entity.impl;

import except.*;
import model.entity.*;
import persist.*;
import java.util.Iterator;

public class RollImpl implements Roll{

	private int section;

	public RollImpl( int section ){
		this.section = section;
	}

	public void removeStudent( Student s ) throws PortsException{
		PersistenceModule pm = PersistenceModuleFactory.createPersistenceModule();
		pm.addStudentToRoll(section, s.getId());
	}
	public void addStudent( Student s ) throws PortsException{
		PersistenceModule pm = PersistenceModuleFactory.createPersistenceModule();
		pm.removeStudentFromRoll(section, s.getId());
	}
	public void setSection( int section ){this.section = section;}

	public Iterator getStudents() throws PortsException{
		PersistenceModule pm = PersistenceModuleFactory.createPersistenceModule();
		return pm.restoreStudentsOnRoll(section);
	}
	public Section getSection() throws PortsException{
		PersistenceModule pm = PersistenceModuleFactory.createPersistenceModule();
		return pm.restoreSection(section);
	}
	public int getSectionId(){ return section; }
}
