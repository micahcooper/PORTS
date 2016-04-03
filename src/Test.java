import java.io.*;
import java.util.*;

import persist.*;
import except.*;
import model.ctrl.*;
import model.entity.*;
import model.entity.impl.*;

public class Test{

  public static void main(String[] args) {
  	
  	PersistenceModule pm = null;
  	try{
		pm = PersistenceModuleFactory.createPersistenceModule();
  	}catch(Exception e){
  		e.printStackTrace();
  	}
  	
  	/*Department d = new DepartmentImpl("Computer Science"),
  					d2 = new DepartmentImpl("Mathematics"),
  					d3 = new DepartmentImpl("Linguistics");
	
	Student stu = new StudentImpl( "test student", "undergraduate", "333 boyd lane" );
	Faculty f = new FacultyImpl( "test faculty", "professor", "333 boyd lane" );
	Administrator a = new AdministratorImpl( "test admin", "systems administrator", "333 boyd lane" );
	
	AccessAuthorization aa = new AccessAuthorizationImpl(1, "student", "password", 1);
	AccessAuthorization aa2 = new AccessAuthorizationImpl(2, "faculty", "password", 2);
	AccessAuthorization aa3 = new AccessAuthorizationImpl(3, "admin", "password", 3);
	
	Course c = new CourseImpl(1, "Software Engineering", "A course about software engineering"),
			c2 = new CourseImpl(1, "Networking", "A course about networking"),
			c3 = new CourseImpl(1, "Databases", "A course about databases"),
			c4 = new CourseImpl(2, "Algebra I", "A course about algebra"),
			c5 = new CourseImpl(2, "Statistics I", "A course about statistics"),
			c6 = new CourseImpl(2, "Calculus I", "A course about calculus"),
			c7 = new CourseImpl(3, "Grammar I", "A course about grammar"),
			c8 = new CourseImpl(3, "Syntax I", "A course about syntax"),
			c9 = new CourseImpl(3, "Parsing I", "A course about parsing");
			
	Section s = new SectionImpl(1, 2, "MWF", "1:00-2:15"),
			s2 = new SectionImpl(2, 2, "TR", "1:00-2:15"),
			s3 = new SectionImpl(3, 2, "MWF", "1:00-2:15"),
			s4 = new SectionImpl(4, 2, "TR", "1:00-2:15"),
			s5 = new SectionImpl(5, 2, "MWF", "1:00-2:15"),
			s6 = new SectionImpl(6, 2, "TR", "1:00-2:15"),
			s7 = new SectionImpl(7, 2, "MWF", "1:00-2:15"),
			s8 = new SectionImpl(8, 2, "TR", "1:00-2:15"),
			s9 = new SectionImpl(9, 2, "MWF", "1:00-2:15");
			
	Student stu1 = new StudentImpl("Delroy Cameron", "graduate", "333 boyd lane" );
	Student stu2 = new StudentImpl("Micah Cooper", "undergraduate", "333 boyd lane" );
	Student stu3 = new StudentImpl("Matthew Eavenson", "graduate", "333 boyd lane" );
	Student stu4 = new StudentImpl("Maciek Janik", "graduate", "333 boyd lane" );
	
	AccessAuthorization aa4 = new AccessAuthorizationImpl(4, "delroy", "password", 3);
	AccessAuthorization aa5 = new AccessAuthorizationImpl(5, "micah", "password", 3);
	AccessAuthorization aa6 = new AccessAuthorizationImpl(6, "matthew", "password", 3);
	AccessAuthorization aa7 = new AccessAuthorizationImpl(7, "maciej", "password", 3);*/
	
	Date d = new GregorianCalendar().getTime();
	
	Exam ex = new ExamImpl(1, "Final Exam", "genInstr", "speInstr", "dir", 80, d);
	
	QuestionChoice qc1 = new QuestionChoiceImpl("true");
	QuestionChoice qc2 = new QuestionChoiceImpl("false");
	
	ExamQuestion q1 = new ExamQuestionImpl("question 1", 1);
	ExamQuestion q2 = new ExamQuestionImpl("question 2", 2);
	ExamQuestion q3 = new ExamQuestionImpl("question 3", 1);
	ExamQuestion q4 = new ExamQuestionImpl("question 4", 2);
	ExamQuestion q5 = new ExamQuestionImpl("question 5", 1);
	ExamQuestion q6 = new ExamQuestionImpl("question 6", 2);
	ExamQuestion q7 = new ExamQuestionImpl("question 7", 1);
	ExamQuestion q8 = new ExamQuestionImpl("question 8", 2);
	ExamQuestion q9 = new ExamQuestionImpl("question 9", 1);
	ExamQuestion q10 = new ExamQuestionImpl("question 10", 2);
	
	try{
		/*pm.storeAccessAuthorization(aa);
		pm.storeAccessAuthorization(aa2);
		pm.storeAccessAuthorization(aa3);
		
		pm.storeStudent(stu);
		pm.storeFaculty(f);
		pm.storeAdministrator(a);
		
		pm.storeDepartment(d);
		pm.storeDepartment(d2);
		pm.storeDepartment(d3);
		
		pm.storeCourse(c);
		pm.storeCourse(c2);
		pm.storeCourse(c3);
		pm.storeCourse(c4);
		pm.storeCourse(c5);
		pm.storeCourse(c6);
		pm.storeCourse(c7);
		pm.storeCourse(c8);
		pm.storeCourse(c9);
		
		pm.storeSection(s);
		pm.storeSection(s2);
		pm.storeSection(s3);
		pm.storeSection(s4);
		pm.storeSection(s5);
		pm.storeSection(s6);
		pm.storeSection(s7);
		pm.storeSection(s8);
		pm.storeSection(s9);
		
		pm.storeStudent(stu1);
		pm.storeStudent(stu2);
		pm.storeStudent(stu3);
		pm.storeStudent(stu4);
		
		pm.storeAccessAuthorization(aa4);
		pm.storeAccessAuthorization(aa5);
		pm.storeAccessAuthorization(aa6);
		pm.storeAccessAuthorization(aa7);*/
		
		pm.storeExam(ex);
		
		pm.storeQuestionChoice(qc1);
		pm.storeQuestionChoice(qc2);
		
		pm.storeExamQuestion(q1);
		pm.storeExamQuestion(q2);
		pm.storeExamQuestion(q3);
		pm.storeExamQuestion(q4);
		pm.storeExamQuestion(q5);
		pm.storeExamQuestion(q6);
		pm.storeExamQuestion(q7);
		pm.storeExamQuestion(q8);
		pm.storeExamQuestion(q9);
		pm.storeExamQuestion(q10);
		
		pm.addChoiceToQuestion( qc1.getId(), q1.getId(), 1 );
		pm.addChoiceToQuestion( qc2.getId(), q1.getId(), 2 );
		pm.addChoiceToQuestion( qc1.getId(), q2.getId(), 1 );
		pm.addChoiceToQuestion( qc2.getId(), q2.getId(), 2 );
		pm.addChoiceToQuestion( qc1.getId(), q3.getId(), 1 );
		pm.addChoiceToQuestion( qc2.getId(), q3.getId(), 2 );
		pm.addChoiceToQuestion( qc1.getId(), q4.getId(), 1 );
		pm.addChoiceToQuestion( qc2.getId(), q4.getId(), 2 );
		pm.addChoiceToQuestion( qc1.getId(), q5.getId(), 1 );
		pm.addChoiceToQuestion( qc2.getId(), q5.getId(), 2 );
		pm.addChoiceToQuestion( qc1.getId(), q6.getId(), 1 );
		pm.addChoiceToQuestion( qc2.getId(), q6.getId(), 2 );
		pm.addChoiceToQuestion( qc1.getId(), q7.getId(), 1 );
		pm.addChoiceToQuestion( qc2.getId(), q7.getId(), 2 );
		pm.addChoiceToQuestion( qc1.getId(), q8.getId(), 1 );
		pm.addChoiceToQuestion( qc2.getId(), q8.getId(), 2 );
		pm.addChoiceToQuestion( qc1.getId(), q9.getId(), 1 );
		pm.addChoiceToQuestion( qc2.getId(), q9.getId(), 2 );
		pm.addChoiceToQuestion( qc1.getId(), q10.getId(), 1 );
		pm.addChoiceToQuestion( qc2.getId(), q10.getId(), 2 );
		
		pm.addQuestionToExam( q1.getId(), ex.getId(), 1 );
		pm.addQuestionToExam( q2.getId(), ex.getId(), 2 );
		pm.addQuestionToExam( q3.getId(), ex.getId(), 3 );
		pm.addQuestionToExam( q4.getId(), ex.getId(), 4 );
		pm.addQuestionToExam( q5.getId(), ex.getId(), 5 );
		pm.addQuestionToExam( q6.getId(), ex.getId(), 6 );
		pm.addQuestionToExam( q7.getId(), ex.getId(), 7 );
		pm.addQuestionToExam( q8.getId(), ex.getId(), 8 );
		pm.addQuestionToExam( q9.getId(), ex.getId(), 9 );
		pm.addQuestionToExam( q10.getId(), ex.getId(), 10 );
		
		
	}catch(Exception e){
		e.printStackTrace();
	}
  }
  
}

