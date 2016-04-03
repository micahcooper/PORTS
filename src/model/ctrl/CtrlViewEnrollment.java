package model.ctrl;

import java.util.*;

import except.*;
import model.entity.*;
import persist.*;

public class CtrlViewEnrollment {

  public static Iterator getStudentsOnRoll(int sectionId) throws PortsException {
    PersistenceModule 	pm = PersistenceModuleFactory.createPersistenceModule();
	return pm.restoreStudentsOnRoll(sectionId);
  }

  public static Iterator getFacultySections(int facultyId) throws PortsException {
    PersistenceModule 	pm = PersistenceModuleFactory.createPersistenceModule();
	return pm.restoreFacultySections(facultyId);
  }

};