package model.entity;

import except.*;
import java.util.Iterator;

public interface ExamResult{
	
	public Student getStudent() throws PortsException;
	public int getStudentId();
	public Exam getExam() throws PortsException;
	public int getExamId();
	public Iterator getAnswers() throws PortsException;
	
	public void setStudent(int student);
	public void setExam(int exam);
	public void addAnswer(int question, int answer) throws PortsException;
	public void removeAnswer(int question) throws PortsException;
}
