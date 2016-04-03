package model.ctrl;

import java.util.*;

import except.*;
import model.entity.*;
import model.entity.impl.*;
import persist.*;

public class CtrlAdminEditSection {

  public static Iterator getFaculty(String criteria) throws PortsException {
    PersistenceModule 	pm = PersistenceModuleFactory.createPersistenceModule();
    List<Faculty> faculty = new ArrayList<Faculty>();
    for(Iterator iter = pm.restoreFaculty(); iter.hasNext();){
    	Faculty f = (Faculty) iter.next();
    	if(f.getName().toLowerCase().contains(criteria.toLowerCase()))
    		faculty.add(f);
    }
   	return faculty.iterator();
  }
  
  public static void createSection(int courseId, int facultyId, String days, String period) throws PortsException {
    PersistenceModule 	pm = PersistenceModuleFactory.createPersistenceModule();
    Section s = new SectionImpl(courseId, facultyId, days, period);
    pm.storeSection(s);
  }
  
  public static void modifySection(int sectionId, int courseId, int facultyId, String days, String period) throws PortsException {
    PersistenceModule 	pm = PersistenceModuleFactory.createPersistenceModule();
    Section s = new SectionImpl(sectionId, courseId, facultyId, days, period);
    pm.storeSection(s);
  }
  
  public static void deleteSection(int sectionId) throws PortsException {
    PersistenceModule 	pm = PersistenceModuleFactory.createPersistenceModule();
    pm.removeSection(sectionId);
  }

};