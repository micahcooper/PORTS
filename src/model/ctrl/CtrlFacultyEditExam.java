/***********************************************
* @(#) 			CtrlFacultyCreateExam.java
* @version		1.0 Dec 10, 2007
* @author		Delroy Cameron
*/

import except.*;
import persist.*;
import model.entity.*;
import model.entity.impl.*;

import java.util.*;

/*******************************************
* This class reads form data passed from 
* the servlet for editing an exam object
*/
public class CtrlFacultyEditExam {

	/***********************************************************
	* This method edits an exam object in the database from
	* parameters entered by the Faculty during exam creation
	*/
	public static Exam editExam(int exam_id, int exam_section, String exam_name, String exam_general_instructions, String exam_special_instructions, String exam_directions, int exam_length, Date exam_date) throws PortsException{

		PersistenceModule pm = null;
		Date estab = null;
		Exam exam = null;
		Iterator examIterator = null;
		int id = 0;
		//creates a database connection and creates accessibility to all entitiy objects	
		pm = PersistenceModuleFactory.createPersistenceModule();
		
		
		//create an empty exam object
		for(Iterator iter = pm.restoreExams(); iter.hasNext();){
			exam = (Exam) iter.next();
			if(exam.getName()==exam_name){
				throw new PortsException("An Exam with this name already exists: " + exam_name);
			}//if
		}//if
		
		//create the new exam object
		Exam updated_exam = pm.restoreExam(exam_id);

		updated_exam.setId(exam_id);
		updated_exam.setDate(exam_date);		
		updated_exam.setName(exam_name);
		updated_exam.setSection(exam_section);
		updated_exam.setLength(exam_length);
		updated_exam.setDirections(exam_directions);
		updated_exam.setGeneralInstructions(exam_general_instructions);		
		updated_exam.setSpecialInstructions(exam_special_instructions);			

		pm.storeExam(updated_exam);

		Exam ex = new ExamImpl(exam_id, exam_section, exam_name, exam_general_instructions, exam_special_instructions, exam_directions, exam_length, exam_date);

		return ex;

	}//createExam

}//class CtrlFacultyEditExam
