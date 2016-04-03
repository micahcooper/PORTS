package model.ctrl;

import java.util.*;

import except.*;
import model.entity.*;
import persist.*;

public class CtrlRegistration {

  public static Iterator getDepartments( ) throws PortsException {
    PersistenceModule 	pm = PersistenceModuleFactory.createPersistenceModule();
	return pm.restoreDepartments();
  }
  
  public static Iterator getCourses( int deptId ) throws PortsException {
    PersistenceModule 	pm = PersistenceModuleFactory.createPersistenceModule();
	return pm.restoreDepartmentCourses(deptId);
  }
  
  public static Iterator getSections( int courseId ) throws PortsException {
    PersistenceModule 	pm = PersistenceModuleFactory.createPersistenceModule();
	return pm.restoreCourseSections(courseId);
  }
  
  public static boolean studentOnRoll( int sectionId, int studentId ) throws PortsException {
    PersistenceModule 	pm = PersistenceModuleFactory.createPersistenceModule();
	return pm.studentIsOnRoll(sectionId, studentId);
  }

  public static void enrollStudent( int sectionId, int studentId ) throws PortsException {
    PersistenceModule 	pm = PersistenceModuleFactory.createPersistenceModule();
	pm.addStudentToRoll(sectionId, studentId);
  }
  
  public static void unEnrollStudent( int sectionId, int studentId ) throws PortsException {
    PersistenceModule 	pm = PersistenceModuleFactory.createPersistenceModule();
	pm.removeStudentFromRoll(sectionId, studentId);
  }
  
  public static Faculty getFaculty( int facId ) throws PortsException {
    PersistenceModule 	pm = PersistenceModuleFactory.createPersistenceModule();
	return pm.restoreFaculty(facId);
  }
  
  public static Course getCourse( int cId ) throws PortsException {
    PersistenceModule 	pm = PersistenceModuleFactory.createPersistenceModule();
	return pm.restoreCourse(cId);
  }

  public static Iterator studentSections( int studentId ) throws PortsException {
    PersistenceModule 	pm = PersistenceModuleFactory.createPersistenceModule();
	return pm.restoreStudentSections(studentId);
  }

};

