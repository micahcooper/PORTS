package model.entity.impl;

import model.entity.*;
import except.*;
import persist.*;
import java.util.Iterator;

public class ExamResultImpl implements ExamResult{

	private int student;
	private int exam;

	public ExamResultImpl(int student, int exam){
		this.student = student;
		this.exam = exam;
	}
	
	public Student getStudent() throws PortsException{
		PersistenceModule pm = PersistenceModuleFactory.createPersistenceModule();
		return pm.restoreStudent(student);
	}
	public int getStudentId(){ return student; }
	public Exam getExam() throws PortsException{
		PersistenceModule pm = PersistenceModuleFactory.createPersistenceModule();
		return pm.restoreExam(exam);
	}
	public int getExamId(){ return exam; }
	public Iterator getAnswers() throws PortsException{
		PersistenceModule pm = PersistenceModuleFactory.createPersistenceModule();
		return pm.restoreExamResultAnswers(student, exam);
	}
	

	public void setStudent(int student){this.student = student;}
	public void setExam(int exam){this.exam = exam;}
	
	public void addAnswer(int question, int answer) throws PortsException{
		PersistenceModule pm = PersistenceModuleFactory.createPersistenceModule();
		pm.addExamResultAnswer(student, exam, question, answer);
	}
	
	public void removeAnswer(int question) throws PortsException{
		PersistenceModule pm = PersistenceModuleFactory.createPersistenceModule();
		pm.removeExamResultAnswer(student, exam, question);
	}
	

}
