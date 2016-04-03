/***********************************************
* @(#) 			CtrlFacultyCreateExam.java
* @version		1.0 December 7, 2007
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
public class CtrlFacultyCreateExamQuestion {

	public static ExamQuestion createExamQuestion(int exam_id, int question_number, String question_text, int question_answer, QuestionChoice choice_a, QuestionChoice choice_b, QuestionChoice choice_c, QuestionChoice choice_d, QuestionChoice choice_e, int choice_num_a, int choice_num_b, int choice_num_c, int choice_num_d, int choice_num_e) throws PortsException{

		PersistenceModule pm = null;
		Date estab = null;
		ExamQuestion examQuestion = null;
		int question_id = 0;	
		Iterator examQuestionIterator = null;

		//creates a database connection and creates accessibility to all entitiy objects	
		pm = PersistenceModuleFactory.createPersistenceModule();

		for(Iterator iter = pm.restoreExamQuestions(); iter.hasNext();){
			examQuestion = (ExamQuestion) iter.next();
			if(examQuestion.getText().equalsIgnoreCase(question_text)){
				throw new PortsException("A Question with this number already exists: " + question_number);
			}//if
		}//if

		//update exam_question
		question_id = pm.storeExamQuestion(new ExamQuestionImpl(question_text, question_answer));
		examQuestion = pm.restoreExamQuestion(question_id);
		System.out.println(question_id);

		//update question_choice table
		int choice_id_a = pm.storeQuestionChoice(choice_a);
		int choice_id_b = pm.storeQuestionChoice(choice_b);
		int choice_id_c = pm.storeQuestionChoice(choice_c);
		int choice_id_d = pm.storeQuestionChoice(choice_d);
		int choice_id_e = pm.storeQuestionChoice(choice_e);

		//update choice_to_question table
		pm.addChoiceToQuestion(choice_id_a, question_id, choice_num_a);
		pm.addChoiceToQuestion(choice_id_b, question_id, choice_num_b);
		pm.addChoiceToQuestion(choice_id_c, question_id, choice_num_c);
		pm.addChoiceToQuestion(choice_id_d, question_id, choice_num_d);
		pm.addChoiceToQuestion(choice_id_e, question_id, choice_num_e);
	
		//update question_to_exam
		pm.addQuestionToExam(question_id, exam_id, question_number);

		return examQuestion;

	}//createExamQuestion
	
}//class
