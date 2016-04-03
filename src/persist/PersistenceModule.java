package persist;

import java.util.*;
import model.entity.*;
import except.*;

public interface PersistenceModule {

  public void removeUser( int id ) throws PortsException;

  public Student 	restoreStudent( int id ) throws PortsException;
  public Iterator 	restoreStudents() throws PortsException;
  public int storeStudent( Student s ) throws PortsException;
  public void removeStudent( int id ) throws PortsException;

  public Faculty restoreFaculty( int id ) throws PortsException;
  public Iterator restoreFaculty() throws PortsException;
  public int	storeFaculty( Faculty f ) throws PortsException;
  public void removeFaculty( int id ) throws PortsException;
  
  public Administrator restoreAdministrator( int id ) throws PortsException;
  public Iterator restoreAdministrators() throws PortsException;
  public int	storeAdministrator( Administrator a ) throws PortsException;
  public void removeAdministrator( int id ) throws PortsException;
  
  public AccessAuthorization restoreAccessAuthorization( int id ) throws PortsException;
  public AccessAuthorization restoreAccessAuthorization( String username, String pass ) throws PortsException;
  public void storeAccessAuthorization( AccessAuthorization a ) throws PortsException;
  public void updateAccessAuthorization( AccessAuthorization a) throws PortsException;
  public void removeAccessAuthorization( int id ) throws PortsException;
  
  public Department restoreDepartment( int id ) throws PortsException;
  public boolean departmentExists(int id) throws PortsException;
  public Iterator restoreDepartments() throws PortsException;
  public int storeDepartment( Department d ) throws PortsException;
  public void removeDepartment( int id ) throws PortsException;
  
  public Course restoreCourse( int id ) throws PortsException;
  public Iterator restoreCourses() throws PortsException;
  public Iterator restoreDepartmentCourses(int deptId) throws PortsException;
  public int storeCourse( Course c ) throws PortsException;
  public void removeCourse( int id ) throws PortsException;
  
  public Section restoreSection( int id ) throws PortsException;
  public Iterator restoreSections() throws PortsException;
  public Iterator restoreCourseSections(int courseId) throws PortsException;
  public Iterator restoreFacultySections(int facultyId) throws PortsException;
  public int storeSection( Section s ) throws PortsException;
  public void removeSection( int id ) throws PortsException;
  
  public Exam restoreExam( int id ) throws PortsException;
  public Iterator restoreExams() throws PortsException;
  public int storeExam( Exam e ) throws PortsException;
  public void removeExam( int id ) throws PortsException;
  public void addQuestionToExam( int qId, int examId, int number ) throws PortsException;
  
  public ExamQuestion restoreExamQuestion( int id ) throws PortsException;
  public Iterator restoreExamQuestions() throws PortsException;
  public Iterator restoreExamQuestions(int examId) throws PortsException;
  public int storeExamQuestion( ExamQuestion e ) throws PortsException;
  public void addChoiceToQuestion( int choiceId, int qId, int answer ) throws PortsException;
  public void removeChoiceFromQuestion( int choiceId, int qId ) throws PortsException;
  public void removeExamQuestion( int id ) throws PortsException;
  
  public QuestionChoice restoreQuestionChoice( int id ) throws PortsException;
  public Iterator restoreQuestionChoices(int q_id) throws PortsException;
  public int storeQuestionChoice( QuestionChoice q ) throws PortsException;
  public void removeQuestionChoice( int id ) throws PortsException;
  
  public Classroom restoreClassroom( int id ) throws PortsException;
  public Iterator restoreClassrooms() throws PortsException;
  public int storeClassroom( Classroom c ) throws PortsException;
  public void removeClassroom( int id ) throws PortsException;
  
  public void removeRoll( int id ) throws PortsException;
  public Iterator restoreStudentsOnRoll(int rollId) throws PortsException;
  public Iterator restoreStudentSections(int stuId) throws PortsException;
  public void addStudentToRoll(int rollId, int stuId) throws PortsException;
  public boolean studentIsOnRoll(int rollId, int stuId) throws PortsException;
  public void removeStudentFromRoll(int rollId, int stuId) throws PortsException;
  
  public void removeExamResult( int stuId, int examId ) throws PortsException;
  public Iterator restoreExamResultAnswers( int stuId, int examId ) throws PortsException;
  public void addExamResultAnswer( int stuId, int examId, int qId, int answer ) throws PortsException;
  public void removeExamResultAnswer( int stuId, int examId, int qId ) throws PortsException;
  
};
