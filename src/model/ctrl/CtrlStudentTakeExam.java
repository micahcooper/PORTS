package model.ctrl;

import java.util.*;

import except.*;
import model.entity.*;
import persist.*;

public class CtrlStudentTakeExam {


  public static Iterator sectionExams(int sectionId) throws PortsException {
    PersistenceModule 	pm = PersistenceModuleFactory.createPersistenceModule();
    List<Exam> exams = new ArrayList<Exam>();
    for(Iterator iter = pm.restoreExams(); iter.hasNext();){
    	Exam ex = (Exam) iter.next();
    	if(ex.getSectionId() == sectionId) exams.add(ex);
    }
  	return exams.iterator();
  }
  
  public static Iterator examQuestions(int examId) throws PortsException {
    PersistenceModule 	pm = PersistenceModuleFactory.createPersistenceModule();
    return pm.restoreExamQuestions(examId);
  }
  
  public static Exam getExam(int examId) throws PortsException {
  	PersistenceModule 	pm = PersistenceModuleFactory.createPersistenceModule();
    return pm.restoreExam(examId);
  }
  
  public static void recordAnswer(int stuId, int examId, int qId, int answer) throws PortsException {
  	PersistenceModule pm = PersistenceModuleFactory.createPersistenceModule();
  	pm.addExamResultAnswer( stuId, examId, qId, answer );
  }
  
  public static double calcGrade(int stuId, int examId) throws PortsException {
  	double right = 0;
  	double total = 0;
  	
  	PersistenceModule pm = PersistenceModuleFactory.createPersistenceModule();
  	PersistenceModule pm2 = PersistenceModuleFactory.createPersistenceModule();
  	for(Iterator iter = pm.restoreExamResultAnswers( stuId, examId ); iter.hasNext();){
  		int[] arr = (int[]) iter.next();
  		ExamQuestion q = pm2.restoreExamQuestion(arr[0]);
  		System.out.println("Q ANSWER: " + q.getAnswer() + " :: " + arr[1]);
  		if(q.getAnswer() == arr[1] ) right++;
  		total++;
  	}
  	return (right/total)*100;
  }

};