/***********************************************
* @(#) 			CtrlFacultyCreateExam.java
* @version		1.0 November 28, 2007
* @author		Delroy Cameron
*/

import except.*;
import persist.*;
//package model.ctrl;
import model.entity.*;
import model.entity.impl.*;

import java.util.*;

/*******************************************
* This class reads form data passed from 
* the servlet for creating an exam object
*/
public class CtrlFacultyCreateExam {

	/***********************************************************
	* This method creates an exam object in the database from
	* parameters entered by the Faculty during exam creation
	*/
public static Exam createExam(int exam_section, String exam_name, String exam_general_instructions, String exam_special_instructions, String exam_directions, int exam_length, Date exam_date) throws PortsException{

		PersistenceModule pm = null;
		Date estab = null;
		Exam exam = null;
		Iterator examIterator = null;

		//creates a database connection and creates accessibility to all entitiy objects	
		pm = PersistenceModuleFactory.createPersistenceModule();
		
		//create the new exam object
		exam = new ExamImpl(exam_section, exam_name, exam_general_instructions, exam_special_instructions, exam_directions, exam_length, exam_date);

		//get the exam id
		int exam_id = pm.storeExam(exam);
		exam = pm.restoreExam(exam_id);

		return exam;

	}//createExam

	public static Iterator getFacultySections(int facultyId) throws PortsException {
		PersistenceModule pm = PersistenceModuleFactory.createPersistenceModule();
		return pm.restoreFacultySections(facultyId);
  	}

}//class CtrlFacultyCreateExam
