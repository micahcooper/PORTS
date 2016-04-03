package model.ctrl;

import java.util.*;

import except.*;
import model.entity.*;
import persist.*;

public class CtrlLogin {

  public static User login( String username, String pass ) throws PortsException { 
    PersistenceModule 	pm = PersistenceModuleFactory.createPersistenceModule();
    AccessAuthorization a = pm.restoreAccessAuthorization(username, pass);
	if(a.getRole() == 1)
		return pm.restoreStudent(a.getId());
	else if(a.getRole() == 2)
		return pm.restoreFaculty(a.getId());
	else if(a.getRole() == 3)
		return pm.restoreAdministrator(a.getId());
	
	return null;
  }

};

