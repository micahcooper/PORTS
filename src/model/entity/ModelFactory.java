package model.entity;

//import java.util.Date;

import model.entity.impl.*;
import java.util.*;

public class ModelFactory {

	public static Student createStudent(int id, String name, String type, String address)
	{ return new StudentImpl(id, name, type, address); }
	
	public static Faculty createFaculty(int id, String name, String title, String address)
	{ return new FacultyImpl(id, name, title, address); }
	
	public static Administrator createAdministrator(int id, String name, String title, String address)
	{ return new AdministratorImpl(id, name, title, address); }

	public static AccessAuthorization createAccessAuthorization(int id, String name, String pass, int role)
	{ return new AccessAuthorizationImpl(id, name, pass, role); }
	
	public static Department createDepartment(int id, String name)
	{ return new DepartmentImpl(id, name); }
	
	public static Course createCourse(int id, int dept, String name, String desc)
	{ return new CourseImpl(id, dept, name, desc); }
	
	public static Section createSection(int id, int course, int faculty, String days, String period)
	{ return new SectionImpl(id, course, faculty, days, period); }

	public static Exam createExam(int id, int section, String exam_name, String general_instructions, String special_instructions, String directions, int length, Date edate)
	{ return new ExamImpl(id, section, exam_name, general_instructions, special_instructions, directions, length, edate); }
	
	public static ExamQuestion createExamQuestion(int id, String text, int answer)
	{ return new ExamQuestionImpl(id, text, answer); }
	
	public static QuestionChoice createQuestionChoice(int id, String text)
	{ return new QuestionChoiceImpl(id, text); }
	
	public static QuestionChoice createQuestionChoice(int id, String text, int number)
	{ return new QuestionChoiceImpl(id, text, number); }
	
	public static Classroom createClassroom(int id, String building, String room, int dept)
	{ return new ClassroomImpl(id, building, room, dept); }

	public static Roll createRoll(int id)
	{ return new RollImpl(id); }
	
	public static ExamResult createExamResult(int student, int exam, List questions, List answers)
	{ return new ExamResultImpl(student, exam); }

};

  
