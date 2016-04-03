package model.ctrl;

import java.util.*;

import except.*;
import model.entity.*;
import persist.*;

public class CtrlAdminEditRoll {

  public static Iterator getStudentsOnRoll(int sectionId) throws PortsException {
    PersistenceModule 	pm = PersistenceModuleFactory.createPersistenceModule();
	return pm.restoreStudentsOnRoll(sectionId);
  }

  public static Iterator getStudents(int sectionId, String criteria) throws PortsException {
    PersistenceModule 	pm = PersistenceModuleFactory.createPersistenceModule();
    List<Student> students = new ArrayList<Student>();
    for(Iterator iter = pm.restoreStudents(); iter.hasNext();){
    	Student s = (Student) iter.next();
    	if(s.getName().toLowerCase().contains(criteria.toLowerCase()))
    		students.add(s);
    }
    for(Iterator iter = pm.restoreStudentsOnRoll(sectionId); iter.hasNext();){
    	Student s = (Student) iter.next();
    	for(int i=0; i<students.size(); i++){
    		if(s.getId() == students.get(i).getId()) students.remove(i);
    	}
    	
    }
	return students.iterator();
  }

};