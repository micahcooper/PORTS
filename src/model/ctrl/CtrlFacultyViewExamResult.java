/********************************************************
* @(#)			CtrlFacultyViewExamResult.java
* @version		1.0 December 9, 2007
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
* the servlet for viewing the exam results
* for a Student 
*/
public class CtrlFacultyViewExamResult {

	/***********************************************************
	* This method creates an exam object in the database from
	* parameters entered by the Faculty during exam creation
	*/
	public static ExamResult getExamResult(int student_id, int exam_id) throws PortsException{
		
		PersistenceModule pm = null;
		Date estab = null;
		ExamResult examResult = null;
		int id = 0;	
		Iterator examResultsIterator = null;
		
		//creates a database connection and creates accessibility to all entitiy objects	
		pm = PersistenceModuleFactory.createPersistenceModule();

		List questions = null;
		List answers = null;
		
		//get the Exam Result object
		examResult = ModelFactory.createExamResult(student_id, exam_id, questions, answers);		
	
		return examResult;	

	}//getResults

}//class
