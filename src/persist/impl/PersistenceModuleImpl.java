package persist.impl;


import java.util.*;
import java.sql.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

import persist.*;
import model.entity.*;
import except.*;

public class PersistenceModuleImpl implements PersistenceModule {

  private Connection p_conn = null;
  private Statement  p_stmt = null;

  public PersistenceModuleImpl( Connection p_conn ) throws PortsException {
		this.p_conn = p_conn;
		try{
			// create a statement
			p_stmt = p_conn.createStatement();
		}catch(Exception e){
			throw new PortsException(e);
		}
	}

  public void removeUser( int id ) throws PortsException {
  		String sql = "delete from user where user_id="+id,
  		sql2 = "delete from roll where student_id="+id,
  		sql3 = "delete from exam_result where student_id="+id,
  		sql4 = "delete from access_authorization where auth_id="+id,
  		sql5 = "update section set section_faculty=0 where section_faculty="+id;
		try {
			p_stmt.execute( sql );
			p_stmt.execute( sql2 );
			p_stmt.execute( sql3 );
			p_stmt.execute( sql4 );
			p_stmt.execute( sql5 );
			return;
		}catch( Exception e ) {
			throw new PortsException( e );
		}
  }

/*****************************************Start Student*****************************************/
	public Student restoreStudent( int id ) throws PortsException {
		String sql = "select * from user where user_id="+id;
		int s_id = 0;
		String name = null;
		String type = null;
		String address = null;
		
		try {
			if( p_stmt.execute( sql ) ) {
				ResultSet r = p_stmt.getResultSet();
				while( r.next() ) {
					s_id = r.getInt( 1 );
					name = r.getString( 2 );
					type = r.getString( 3 );
					address = r.getString( 4 );
					return ModelFactory.createStudent( s_id, name, type, address );
				}
			}
		}catch( Exception e ) {
			throw new PortsException( e );
		}
		throw new PortsException( "Could not retrieve Student with id: " + id );
	}
  

	public Iterator restoreStudents() throws PortsException {
	    String sql = "select u.user_id, u.user_name, u.user_title, u.user_address "+
	    				" from access_authorization a, user u where a.auth_role=1 and a.auth_id=u.user_id";
		try {
			if( p_stmt.execute( sql ) ) {
				ResultSet r = p_stmt.getResultSet();
				return new StudentIteratorImpl( r );
			}
		}catch( Exception e ) {
			throw new PortsException( e );
		}
		throw new PortsException( "Could not retrieve Students" );
	  }

	public int storeStudent( Student s ) throws PortsException {
		String sql = null;
		if(s.getId() < 1){
			sql = "insert into user ( user_name, user_title, user_address ) values ( " +
	                        "'" + s.getName() + "', " +
	                        "'" + s.getType() + "', " +
	                        "'" + s.getAddress() + "' )";
		}else{
			sql = "update user set " +
	                        "user_name='" + s.getName() + "', " +
	                        "user_title='" + s.getType() + "', " +
	                        "user_address='" + s.getAddress() + "' where user_id="+s.getId();
		}
		int inscnt = 0;
		int id = 0;
		try {
			inscnt = p_stmt.executeUpdate( sql );
			if( inscnt == 1 ) {
				sql = "select last_insert_id()";
				if( p_stmt.execute( sql ) ) { // statement returned a result
					// retrieve the result
					ResultSet r = p_stmt.getResultSet();
					// we will use only the first row!
					while( r.next() ) {
						// retrieve the last insert auto_increment value
						id = r.getInt( 1 );
						if( id > 0 ) {
							s.setId( id );
							return id;
						}
					}
				}
			}
		}catch( Exception e ) {
			throw new PortsException( e );
		}
		return s.getId();
	}
	
	public void removeStudent( int id ) throws PortsException {
		removeUser(id);
	}
/*****************************************End Student*****************************************/
  
/*****************************************Start AccessAuthorization*****************************************/
	public AccessAuthorization restoreAccessAuthorization( int id ) throws PortsException {
		String sql = "select * from access_authorization where auth_id=" + id;
		int a_id = 0;
		String username = null;
		String password = null;
		int role = 0;
		
		try {
			if( p_stmt.execute( sql ) ) {
				ResultSet r = p_stmt.getResultSet();
				while( r.next() ) {
					a_id = r.getInt( 1 );
					username = r.getString( 2 );
					password = r.getString( 3 );
					role = r.getInt( 4 );
					return ModelFactory.createAccessAuthorization( a_id, username, password, role );
				}
			}
		}catch( Exception e ) {
			throw new PortsException( e );
		}
		throw new PortsException( "Could not retrieve AccessAuthorization with id: " + id );
	}
	
	public AccessAuthorization restoreAccessAuthorization( String username, String password ) throws PortsException {
		String sql = "select * from access_authorization where "+
							" auth_name='"+username+"' and auth_password='"+password+"'";
		int a_id = 0;
		int role = 0;
		
		try {
			if( p_stmt.execute( sql ) ) {
				ResultSet r = p_stmt.getResultSet();
				while( r.next() ) {
					a_id = r.getInt( 1 );
					username = r.getString( 2 );
					password = r.getString( 3 );
					role = r.getInt( 4 );
					return ModelFactory.createAccessAuthorization( a_id, username, password, role );
				}
			}
		}catch( Exception e ) {
			throw new PortsException( e );
		}
		throw new PortsException( "Could not retrieve AccessAuthorization with username: " + username + " and password " + password );
	}
  
  	public void storeAccessAuthorization( AccessAuthorization a) throws PortsException{
		String sql = "insert into access_authorization (auth_id, auth_name, auth_password, auth_role)"+
							"values ( '"+a.getId()+"','"+a.getUsername()+"','"+a.getPassword()+"','"+a.getRole()+"')";
		
		try{				
			p_stmt.execute(sql);
		}catch(Exception e){
			throw new PortsException( e );
		}
	}
	
	public void updateAccessAuthorization( AccessAuthorization a) throws PortsException{
		String sql = "update access_authorization set " +
	                        "auth_name='" + a.getUsername() + "', " +
	                        "auth_pass='" + a.getPassword() + "', " +
	                        "auth_role='" + a.getRole() + "' where auth_id="+a.getId();
		
		try{				
			p_stmt.execute(sql);
		}catch(Exception e){
			throw new PortsException( e );
		}
	}
	
	public void removeAccessAuthorization( int id ) throws PortsException {
		String sql = "delete from access_authorization where auth_id="+id;		
		try {
			p_stmt.execute( sql );
			return;
		}catch( Exception e ) {
			throw new PortsException( e );
		}
	}
/*****************************************End AccessAuthorization*****************************************/

/*****************************************Start Faculty*****************************************/
    public Faculty restoreFaculty( int id ) throws PortsException {
  		String sql = "select * from user where user_id="+id;
		int s_id = 0;
		String name = null;
		String title = null;
		String address = null;
		
		try {
			if( p_stmt.execute( sql ) ) {
				ResultSet r = p_stmt.getResultSet();
				while( r.next() ) {
					s_id = r.getInt( 1 );
					name = r.getString( 2 );
					title = r.getString( 3 );
					address = r.getString( 4 );
					return ModelFactory.createFaculty( s_id, name, title, address );
				}
			}
		}catch( Exception e ) {
			throw new PortsException( e );
		}
		throw new PortsException( "Could not retrieve Faculty with id: " + id );
  	}
  	
  	public Iterator restoreFaculty( ) throws PortsException {
  		String sql = "select u.user_id, u.user_name, u.user_title, u.user_address "+
	    				" from access_authorization a, user u where a.auth_role=2 and a.auth_id=u.user_id";
		try {
			if( p_stmt.execute( sql ) ) {
				ResultSet r = p_stmt.getResultSet();
				return new FacultyIteratorImpl( r );
			}
		}catch( Exception e ) {
			throw new PortsException( e );
		}
		throw new PortsException( "Could not retrieve Faculty" );
  	}
  	
  	public int storeFaculty( Faculty f ) throws PortsException {
  		String sql = null;
  		
  		if(f.getId() < 1){
  				sql = "insert into user ( user_name, user_title, user_address ) values ( " +
	                        "'" + f.getName() + "', " +
	                        "'" + f.getTitle() + "', " +
	                        "'" + f.getAddress() + "' )";
  		}else{
				sql = "update user set " +
	                        "user_name='" + f.getName() + "', " +
	                        "user_title='" + f.getTitle() + "', " +
	                        "user_address='" + f.getAddress() + "' where user_id="+f.getId();
  		}
  		
		int inscnt = 0;
		int id = 0;
		try {
			inscnt = p_stmt.executeUpdate( sql );
			if( inscnt == 1 ) {
				sql = "select last_insert_id()";
				if( p_stmt.execute( sql ) ) { // statement returned a result
					// retrieve the result
					ResultSet r = p_stmt.getResultSet();
					// we will use only the first row!
					while( r.next() ) {
						// retrieve the last insert auto_increment value
						id = r.getInt( 1 );
						if( id > 0 ) {
							f.setId( id );
							return id;
						}
					}
				}
			}
		}catch( Exception e ) {
			throw new PortsException( e );
		}
		return f.getId();
  	}
  	
  	public void removeFaculty( int id ) throws PortsException {
		removeUser(id);
	}
/*****************************************End Faculty*****************************************/
  	
/*****************************************Start Administrator*****************************************/
  	public Administrator restoreAdministrator( int id ) throws PortsException{
  		String sql = "select * from user where user_id="+id;
		int s_id = 0;
		String name = null;
		String title = null;
		String address = null;
		
		try {
			if( p_stmt.execute( sql ) ) {
				ResultSet r = p_stmt.getResultSet();
				while( r.next() ) {
					s_id = r.getInt( 1 );
					name = r.getString( 2 );
					title = r.getString( 3 );
					address = r.getString( 4 );
					return ModelFactory.createAdministrator( s_id, name, title, address );
				}
			}
		}catch( Exception e ) {
			throw new PortsException( e );
		}
		throw new PortsException( "Could not retrieve Administrator with id: " + id );
  	}
  	
  	public Iterator restoreAdministrators() throws PortsException{
  		String sql = "select u.user_id, u.user_name, u.user_title, u.user_address "+
	    				" from access_authorization a, user u where a.auth_role=3 and a.auth_id=u.user_id";
		try {
			if( p_stmt.execute( sql ) ) {
				ResultSet r = p_stmt.getResultSet();
				return new AdministratorIteratorImpl( r );
			}
		}catch( Exception e ) {
			throw new PortsException( e );
		}
		throw new PortsException( "Could not retrieve Administrators" );
  	}
  	
  	public int storeAdministrator( Administrator a ) throws PortsException{
  		String sql = null;
  		
  		if(a.getId() < 1){
  				sql = "insert into user ( user_name, user_title, user_address ) values ( " +
	                        "'" + a.getName() + "', " +
	                        "'" + a.getTitle() + "', " +
	                        "'" + a.getAddress() + "' )";
  		}else{
				sql = "update user set " +
	                        "user_name='" + a.getName() + "', " +
	                        "user_title='" + a.getTitle() + "', " +
	                        "user_address='" + a.getAddress() + "' where user_id="+a.getId();
  		}
		int inscnt = 0;
		int id = 0;
		try {
			inscnt = p_stmt.executeUpdate( sql );
			if( inscnt == 1 ) {
				sql = "select last_insert_id()";
				if( p_stmt.execute( sql ) ) { // statement returned a result
					// retrieve the result
					ResultSet r = p_stmt.getResultSet();
					// we will use only the first row!
					while( r.next() ) {
						// retrieve the last insert auto_increment value
						id = r.getInt( 1 );
						if( id > 0 ) {
							a.setId( id );
							return id;
						}
					}
				}
			}
		}catch( Exception e ) {
			throw new PortsException( e );
		}
		return a.getId();
  	}
  	
  	public void removeAdministrator( int id ) throws PortsException {
		removeUser(id);
	}
/*****************************************End Administrator*****************************************/

/*****************************************Start Department*****************************************/
	public Department restoreDepartment( int id ) throws PortsException{
		String sql = "select * from department where dept_id="+id;
		int s_id = 0;
		String name = null;
		
		try {
			if( p_stmt.execute( sql ) ) {
				ResultSet r = p_stmt.getResultSet();
				while( r.next() ) {
					s_id = r.getInt( 1 );
					name = r.getString( 2 );
					return ModelFactory.createDepartment( s_id, name );
				}
			}
		}catch( Exception e ) {
			throw new PortsException( e );
		}
		throw new PortsException( "Could not retrieve Department with id: " + id );
	}
	
	public boolean departmentExists( int id ) throws PortsException{
		String sql = "select * from department where dept_id="+id;
		
		try {
			if( p_stmt.execute( sql ) ) {
				ResultSet r = p_stmt.getResultSet();
				if(r.next() ) {
					return true;
				}else return false;
			}
		}catch( Exception e ) {
			throw new PortsException( e );
		}
		return false;
	}
	
	public Iterator restoreDepartments() throws PortsException{
		String sql = "select * from department";
		try {
			if( p_stmt.execute( sql ) ) {
				ResultSet r = p_stmt.getResultSet();
				return new DepartmentIteratorImpl( r );
			}
		}catch( Exception e ) {
			throw new PortsException( e );
		}
		throw new PortsException( "Could not retrieve Departments" );
	}
	
	public int storeDepartment( Department d ) throws PortsException{
		String sql = null;

		if(d.getId() < 1){
  				sql = "insert into department ( dept_name ) values ( " +
	                        "'" + d.getName() + "' )";
  		}else{
				sql = "update department set " +
	                        "dept_name='" + d.getName() + "'"+
	                        " where dept_id="+d.getId();
  		}

		int inscnt = 0;
		int id = 0;
		try {
			inscnt = p_stmt.executeUpdate( sql );
			if( inscnt == 1 ) {
				sql = "select last_insert_id()";
				if( p_stmt.execute( sql ) ) { // statement returned a result
					// retrieve the result
					ResultSet r = p_stmt.getResultSet();
					// we will use only the first row!
					while( r.next() ) {
						// retrieve the last insert auto_increment value
						id = r.getInt( 1 );
						if( id > 0 ) {
							d.setId( id );
							return id;
						}
					}
				}
			}
		}catch( Exception e ) {
			throw new PortsException( e );
		}
		return d.getId();
	}
	
	public void removeDepartment( int id ) throws PortsException {
		String sql = "delete from department where dept_id="+id;		
		try {
			p_stmt.execute( sql );
			return;
		}catch( Exception e ) {
			throw new PortsException( e );
		}
	}

/*****************************************End Department*****************************************/	

/*****************************************Start Course*****************************************/
	public Course restoreCourse( int id ) throws PortsException{
		String sql = "select * from course where course_id="+id;
		int s_id = 0;
		int dept = 0;
		String name = null;
		String desc = null;
		
		try {
			if( p_stmt.execute( sql ) ) {
				ResultSet r = p_stmt.getResultSet();
				while( r.next() ) {
					s_id = r.getInt( 1 );
					dept = r.getInt( 2 );
					name = r.getString( 3 );
					desc = r.getString( 4 );
					return ModelFactory.createCourse(s_id, dept, name, desc);
				}
			}
		}catch( Exception e ) {
			throw new PortsException( e );
		}
		throw new PortsException( "Could not retrieve Course with id: " + id );
	}
	
	public Iterator restoreCourses() throws PortsException{
		String sql = "select * from course";
		try {
			if( p_stmt.execute( sql ) ) {
				ResultSet r = p_stmt.getResultSet();
				return new CourseIteratorImpl( r );
			}
		}catch( Exception e ) {
			throw new PortsException( e );
		}
		throw new PortsException( "Could not retrieve Courses" );
	}
	
	public Iterator restoreDepartmentCourses(int deptId) throws PortsException{
		String sql = "select * from course where course_dept="+deptId;
		try {
			if( p_stmt.execute( sql ) ) {
				ResultSet r = p_stmt.getResultSet();
				return new CourseIteratorImpl( r );
			}
		}catch( Exception e ) {
			throw new PortsException( e );
		}
		throw new PortsException( "Could not retrieve Courses" );
	}
	
	public int storeCourse( Course c ) throws PortsException{
		String sql = null;

		if(c.getId() < 1){
  				sql = "insert into course ( course_dept, course_name, course_desc ) values ( " +
	                        "'" + c.getDeptId() + "', " +
	                        "'" + c.getName() + "', " +
	                        "'" + c.getDesc() + "' )";
  		}else{
				sql = "update course set " +
	                        "course_dept='" + c.getDeptId() + "', " +
	                        "course_name='" + c.getName() + "', " +
	                        "course_desc='" + c.getDesc() + "' where course_id="+c.getId();
  		}


		int inscnt = 0;
		int id = 0;
		try {
			inscnt = p_stmt.executeUpdate( sql );
			if( inscnt == 1 ) {
				sql = "select last_insert_id()";
				if( p_stmt.execute( sql ) ) { // statement returned a result
					// retrieve the result
					ResultSet r = p_stmt.getResultSet();
					// we will use only the first row!
					while( r.next() ) {
						// retrieve the last insert auto_increment value
						id = r.getInt( 1 );
						if( id > 0 ) {
							c.setId( id );
							return id;
						}
					}
				}
			}
		}catch( Exception e ) {
			throw new PortsException( e );
		}
		return c.getId();
	}
	
	public void removeCourse( int id ) throws PortsException {
		String sql = "delete from course where course_id="+id;	
		try {
			p_stmt.execute( sql );
			return;
		}catch( Exception e ) {
			throw new PortsException( e );
		}
	}
/*****************************************End Course*****************************************/

/*****************************************Start Section*****************************************/
	public Section restoreSection( int id ) throws PortsException{
		String sql = "select * from section where section_id="+id;
		int s_id = 0;
		int course = 0;
		int faculty = 0;
		String days = null;
		String period = null;
		
		try {
			if( p_stmt.execute( sql ) ) {
				ResultSet r = p_stmt.getResultSet();
				while( r.next() ) {
					s_id = r.getInt( 1 );
					course = r.getInt( 2 );
					faculty = r.getInt( 3);
					days = r.getString( 4 );
					period = r.getString( 5 );
					return ModelFactory.createSection(s_id, course, faculty, days, period);
				}
			}
		}catch( Exception e ) {
			throw new PortsException( e );
		}
		throw new PortsException( "Could not retrieve Section with id: " + id );
	}
	
	public Iterator restoreSections() throws PortsException{ 
		String sql = "select * from section";
		try {
			if( p_stmt.execute( sql ) ) {
				ResultSet r = p_stmt.getResultSet();
				return new SectionIteratorImpl( r );
			}
		}catch( Exception e ) {
			throw new PortsException( e );
		}
		throw new PortsException( "Could not retrieve Sections" );
	}
	
	public Iterator restoreCourseSections(int courseId) throws PortsException{ 
		String sql = "select * from section where section_course="+courseId;
		try {
			if( p_stmt.execute( sql ) ) {
				ResultSet r = p_stmt.getResultSet();
				return new SectionIteratorImpl( r );
			}
		}catch( Exception e ) {
			throw new PortsException( e );
		}
		throw new PortsException( "Could not retrieve Sections" );
	}
	
	public Iterator restoreFacultySections(int facultyId) throws PortsException{ 
		String sql = "select * from section where section_faculty="+facultyId;
		try {
			if( p_stmt.execute( sql ) ) {
				ResultSet r = p_stmt.getResultSet();
				return new SectionIteratorImpl( r );
			}
		}catch( Exception e ) {
			throw new PortsException( e );
		}
		throw new PortsException( "Could not retrieve Sections" );
	}
	
	public int storeSection( Section s ) throws PortsException{
		String sql = null;

		if(s.getId() < 1){
  				sql = "insert into section ( section_course, section_faculty, section_days, section_period ) values ( " +
	                        "'" + s.getCourseId() + "', " +
	                        "'" + s.getFacultyId() + "', " +
	                        "'" + s.getDays() + "', " +
	                        "'" + s.getPeriod() + "' )";
  		}else{
				sql = "update section set " +
	                        "section_course='" + s.getCourseId() + "', " +
	                        "section_faculty='" + s.getFacultyId() + "', " +
	                        "section_days='" + s.getDays() + "', " +
	                        "section_period='" + s.getPeriod() + "' where section_id="+s.getId();
  		}	
  		
		int inscnt = 0;
		int id = 0;
		try {
			inscnt = p_stmt.executeUpdate( sql );
			if( inscnt == 1 ) {
				sql = "select last_insert_id()";
				if( p_stmt.execute( sql ) ) { // statement returned a result
					// retrieve the result
					ResultSet r = p_stmt.getResultSet();
					// we will use only the first row!
					while( r.next() ) {
						// retrieve the last insert auto_increment value
						id = r.getInt( 1 );
						if( id > 0 ) {
							s.setId( id );
							return id;
						}
					}
				}
			}
		}catch( Exception e ) {
			throw new PortsException( e );
		}
		return s.getId();
	}
	
	public void removeSection( int id ) throws PortsException {
		String sql = "delete from section where section_id="+id;	
		try {
			p_stmt.execute( sql );
			return;
		}catch( Exception e ) {
			throw new PortsException( e );
		}
	}
/*****************************************End Section*****************************************/

/*****************************************Start Exam*****************************************/
	public Exam restoreExam( int id ) throws PortsException{
		String sql = "select * from exam where exam_id="+id;
		int s_id = 0;
		int section = 0;
		String name = null;
		String general_instructions = null;
		String special_instructions = null;
		String directions = null;
		int length = 0;
		java.util.Date edate = null;
		
		try {
			if( p_stmt.execute( sql ) ) {
				ResultSet r = p_stmt.getResultSet();
				while( r.next() ) {
					s_id = r.getInt( 1 );
					section = r.getInt( 2 );
					name = r.getString( 3 );
					general_instructions = r.getString( 4 );
					special_instructions = r.getString( 5 );
					directions = r.getString( 6 );
					length = r.getInt( 7 );
					edate = r.getDate( 8 );
					return ModelFactory.createExam( s_id, section, name, general_instructions, special_instructions, directions, length, edate);
				}
			}
		}catch( Exception e ) {
			throw new PortsException( e );
		}
		throw new PortsException( "Could not retrieve Exam with id: " + id );
	}
	
	public Iterator restoreExams() throws PortsException{
	String sql = "select * from exam";
		try {
			if( p_stmt.execute( sql ) ) {
				ResultSet r = p_stmt.getResultSet();
				return new ExamIteratorImpl( r );
			}
		}catch( Exception e ) {
			throw new PortsException( e );
		}
		throw new PortsException( "Could not retrieve Exams" );
	}
	
	public int storeExam( Exam ex ) throws PortsException{
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String sql = null;

		if(ex.getId() < 1){
  				sql = "insert into exam ( exam_section, exam_name, exam_general_instructions, "+
						"exam_special_instructions, exam_directions, exam_length, exam_date ) values ( " +
	                        "'" + ex.getSectionId() + "', " +
	                        "'" + ex.getName() + "', " +
	                        "'" + ex.getGeneralInstructions() + "', " +
	                        "'" + ex.getSpecialInstructions() + "', " +
	                        "'" + ex.getDirections() + "', " +
	                        "'" + ex.getLength() + "', " +
	                        "'" + df.format(ex.getDate()) + "' )";
  		}else{
				sql = "update exam set " +
	                        "exam_section='" + ex.getSectionId() + "', " +
	                        "exam_name='" + ex.getName() + "', " +
	                        "exam_general_instructions='" + ex.getGeneralInstructions() + "', " +
	                        "exam_special_instructions='" + ex.getSpecialInstructions() + "', " +
	                        "exam_directions='" + ex.getDirections() + "', " +
	                        "exam_length='" + ex.getLength() + "', " +
	                        "exam_date='" + df.format(ex.getDate()) + "' where exam_id="+ex.getId();
  		}

		int inscnt = 0;
		int id = 0;
		try {
			inscnt = p_stmt.executeUpdate( sql );
			if( inscnt == 1 ) {
				sql = "select last_insert_id()";
				if( p_stmt.execute( sql ) ) { // statement returned a result
					// retrieve the result
					ResultSet r = p_stmt.getResultSet();
					// we will use only the first row!
					while( r.next() ) {
						// retrieve the last insert auto_increment value
						id = r.getInt( 1 );
						if( id > 0 ) {
							ex.setId( id );
							return id;
						}
					}
				}
			}
		}catch( Exception e ) {
			throw new PortsException( e );
		}
		return ex.getId();
	}
	
	public void removeExam( int id ) throws PortsException {
		String sql = "delete from exam where exam_id="+id;		
		try {
			p_stmt.execute( sql );
			return;
		}catch( Exception e ) {
			throw new PortsException( e );
		}
	}
	
	public void addQuestionToExam( int qId, int examId, int number ) throws PortsException{
		String sql = "insert into question_to_exam ( question_id, exam_id, question_number ) values ( " +
	                        "'" + qId + "', " +
	                        "'" + examId + "', " +
	                        "'" + number + "' )";
		int inscnt = 0;
		try {
			inscnt = p_stmt.executeUpdate( sql );
		}catch( Exception e ) {
			throw new PortsException( e );
		}
	}

/*****************************************End Exam*****************************************/

/*****************************************Start ExamQuestion*****************************************/
	public ExamQuestion restoreExamQuestion( int id ) throws PortsException{
		String sql = "select * from exam_question where question_id="+id;
		int s_id = 0;
		String text = null;
		int answer = 0;
		
		try {
			if( p_stmt.execute( sql ) ) {
				ResultSet r = p_stmt.getResultSet();
				while( r.next() ) {
					s_id = r.getInt( 1 );
					text = r.getString( 2 );
					answer = r.getInt( 3 );
					return ModelFactory.createExamQuestion(s_id, text, answer);
				}
			}
		}catch( Exception e ) {
			throw new PortsException( e );
		}
		throw new PortsException( "Could not retrieve ExamQuestion with id: " + id );
	}
	
	public Iterator restoreExamQuestions() throws PortsException{
		String sql = "select * from exam_question";
		try {
			if( p_stmt.execute( sql ) ) {
				ResultSet r = p_stmt.getResultSet();
				return new ExamQuestionIteratorImpl( r );
			}
		}catch( Exception e ) {
			throw new PortsException( e );
		}
		throw new PortsException( "Could not retrieve Exam Questions" );
	}
	
	public Iterator restoreExamQuestions(int examId) throws PortsException{
		String sql = "select q.question_id, q.question_text, q.question_answer "+
						"from exam_question q, question_to_exam e "+
						"where q.question_id=e.question_id and e.exam_id="+examId;
		try {
			if( p_stmt.execute( sql ) ) {
				ResultSet r = p_stmt.getResultSet();
				return new ExamQuestionIteratorImpl( r );
			}
		}catch( Exception e ) {
			throw new PortsException( e );
		}
		throw new PortsException( "Could not retrieve Exam Questions" );
	}
	
	public int storeExamQuestion( ExamQuestion q ) throws PortsException{
		String sql = null;

		if(q.getId() < 1){
  				sql = "insert into exam_question ( question_text, question_answer ) values ( " +
	                        "'" + q.getText() + "', " +
	                        "'" + q.getAnswer() + "' )";
  		}else{
				sql = "update exam_question set " +
	                        "question_text='" + q.getText() + "', " +
	                        "question_answer='" + q.getAnswer() + "' where question_id="+q.getId();
  		}

		int inscnt = 0;
		int id = 0;
		try {
			inscnt = p_stmt.executeUpdate( sql );
			if( inscnt == 1 ) {
				sql = "select last_insert_id()";
				if( p_stmt.execute( sql ) ) { // statement returned a result
					// retrieve the result
					ResultSet r = p_stmt.getResultSet();
					// we will use only the first row!
					while( r.next() ) {
						// retrieve the last insert auto_increment value
						id = r.getInt( 1 );
						if( id > 0 ) {
							q.setId( id );
							return id;
						}
					}
				}
			}
		}catch( Exception e ) {
			throw new PortsException( e );
		}
		return q.getId();
	}
	public void addChoiceToQuestion( int choice_id, int q_id, int number ) throws PortsException{
		String sql = "insert into choice_to_question ( choice_id, question_id, choice_option ) values ( " +
	                        "'" + choice_id + "', " +
	                        "'" + q_id + "', " +
	                        "'" + number + "' )";
		int inscnt = 0;
		try {
			inscnt = p_stmt.executeUpdate( sql );
		}catch( Exception e ) {
			throw new PortsException( e );
		}
	}
 	public void removeChoiceFromQuestion( int choice_id, int q_id ) throws PortsException{
 		String sql = "delete from choice_to_question where choice_id="+choice_id+" and question_id="+q_id;
		try {
			p_stmt.execute( sql );
		}catch( Exception e ) {
			throw new PortsException( e );
		}
 	}
	
	public void removeExamQuestion( int id ) throws PortsException {
		String sql = "delete from exam_question where question_id="+id;		
		try {
			p_stmt.execute( sql );
			return;
		}catch( Exception e ) {
			throw new PortsException( e );
		}
	}
/*****************************************End ExamQuestion*****************************************/

/*****************************************Start QuestionChoice*****************************************/
	public QuestionChoice restoreQuestionChoice( int id ) throws PortsException{
		String sql = "select * from question_choice where choice_id="+id;
		int s_id = 0;
		String text = null;
		
		try {
			if( p_stmt.execute( sql ) ) {
				ResultSet r = p_stmt.getResultSet();
				while( r.next() ) {
					s_id = r.getInt( 1 );
					text = r.getString( 2 );
					return ModelFactory.createQuestionChoice(s_id, text);
				}
			}
		}catch( Exception e ) {
			throw new PortsException( e );
		}
		throw new PortsException( "Could not retrieve QuestionChoice with id: " + id );
	}
	
	public Iterator restoreQuestionChoices(int q_id) throws PortsException{
		String sql = "select q.choice_id, q.choice_text, c.choice_option "+
						"from question_choice q, choice_to_question c "+
						"where c.choice_id=q.choice_id and c.question_id="+q_id+" order by c.choice_option asc";
		try {
			if( p_stmt.execute( sql ) ) {
				ResultSet r = p_stmt.getResultSet();
				return new QuestionChoiceIteratorImpl( r );
			}
		}catch( Exception e ) {
			throw new PortsException( e );
		}
		throw new PortsException( "Could not retrieve Question Choices" );
	}
	
	public int storeQuestionChoice( QuestionChoice q ) throws PortsException{
		String sql = null;

		if(q.getId() < 1){
 			sql = "insert into question_choice ( choice_text ) values ( " +
	                        "'" + q.getText() + "' )";
   		}else{
			sql = "update question_choice set " +
                        "choice_text='" + q.getText() + "' where choice_id="+q.getId();
  		}
  		
		int inscnt = 0;
		int id = 0;
		try {
			inscnt = p_stmt.executeUpdate( sql );
			if( inscnt == 1 ) {
				sql = "select last_insert_id()";
				if( p_stmt.execute( sql ) ) { // statement returned a result
					// retrieve the result
					ResultSet r = p_stmt.getResultSet();
					// we will use only the first row!
					while( r.next() ) {
						// retrieve the last insert auto_increment value
						id = r.getInt( 1 );
						if( id > 0 ) {
							q.setId( id );
							return id;
						}
					}
				}
			}
		}catch( Exception e ) {
			throw new PortsException( e );
		}
		return q.getId();
	}
	
	public void removeQuestionChoice( int id ) throws PortsException {
		String sql = "delete from question_choice where choice_id="+id,	
				sql2 = "delete from choice_to_question where choice_id="+id;	
		try {
			p_stmt.execute( sql );
			p_stmt.execute( sql2 );
			return;
		}catch( Exception e ) {
			throw new PortsException( e );
		}
	}
/*****************************************End QuestionChoice*****************************************/

/*****************************************Start Classroom*****************************************/
	public Classroom restoreClassroom( int id ) throws PortsException{
		String sql = "select * from classroom where class_id="+id;
		int s_id = 0;
		String room = null;
		String building = null;
		int dept = 0;
		
		try {
			if( p_stmt.execute( sql ) ) {
				ResultSet r = p_stmt.getResultSet();
				while( r.next() ) {
					s_id = r.getInt( 1 );
					room = r.getString( 2 );
					building = r.getString( 3 );
					dept = r.getInt( 4 );
					return ModelFactory.createClassroom(s_id, building, room, dept);
				}
			}
		}catch( Exception e ) {
			throw new PortsException( e );
		}
		throw new PortsException( "Could not retrieve Classroom with id: " + id );
	
	}
	
	public Iterator restoreClassrooms() throws PortsException{
		String sql = "select * from classroom";
		try {
			if( p_stmt.execute( sql ) ) {
				ResultSet r = p_stmt.getResultSet();
				return new ClassroomIteratorImpl( r );
			}
		}catch( Exception e ) {
			throw new PortsException( e );
		}
		throw new PortsException( "Could not retrieve Classrooms" );
	}
	
	public int storeClassroom( Classroom c ) throws PortsException{
		String sql = null;

		if(c.getId() < 1){
  				sql = "insert into classroom ( class_room, class_building, class_dept ) values ( " +
	                        "'" + c.getRoom() + "', " +
	                        "'" + c.getBuilding() + "', " +
	                        "'" + c.getDeptId() + "' )";
  		}else{
				sql = "update classroom set " +
	                        "class_room='" + c.getRoom() + "', " +
	                        "class_building='" + c.getBuilding() + "', " +
	                        "class_dept='" + c.getDeptId() + "' where class_id="+c.getId();
  		}
  		
		int inscnt = 0;
		int id = 0;
		try {
			inscnt = p_stmt.executeUpdate( sql );
			if( inscnt == 1 ) {
				sql = "select last_insert_id()";
				if( p_stmt.execute( sql ) ) { // statement returned a result
					// retrieve the result
					ResultSet r = p_stmt.getResultSet();
					// we will use only the first row!
					while( r.next() ) {
						// retrieve the last insert auto_increment value
						id = r.getInt( 1 );
						if( id > 0 ) {
							c.setId( id );
							return id;
						}
					}
				}
			}
		}catch( Exception e ) {
			throw new PortsException( e );
		}
		return c.getId();
	}
	
	public void removeClassroom( int id ) throws PortsException {
		String sql = "delete from classroom where class_id="+id;		
		try {
			p_stmt.execute( sql );
			return;
		}catch( Exception e ) {
			throw new PortsException( e );
		}
	}
/*****************************************End Classroom*****************************************/

/*****************************************Start Roll*****************************************/
	public void removeRoll( int id ) throws PortsException {
		String sql = "delete from roll where section_id="+id;		
		try {
			p_stmt.execute( sql );
			return;
		}catch( Exception e ) {
			throw new PortsException( e );
		}
	}
	public Iterator restoreStudentsOnRoll(int rollId) throws PortsException{
		String sql = "select u.user_id, u.user_name, u.user_title, u.user_address "+
						"from user u, roll r "+
						"where u.user_id=r.student_id and r.section_id="+rollId;
		try {
			if( p_stmt.execute( sql ) ) {
				ResultSet r = p_stmt.getResultSet();
				return new StudentIteratorImpl( r );
			}
		}catch( Exception e ) {
			throw new PortsException( e );
		}
		throw new PortsException( "Could not retrieve Students" );
	}
	public Iterator restoreStudentSections(int stu_Id) throws PortsException{
		String sql = "select s.section_id, s.section_course, s.section_faculty, s.section_days, s.section_period "+
						"from section s, roll r "+
						"where s.section_id=r.section_id and r.student_id="+stu_Id;
		try {
			if( p_stmt.execute( sql ) ) {
				ResultSet r = p_stmt.getResultSet();
				return new SectionIteratorImpl( r );
			}
		}catch( Exception e ) {
			throw new PortsException( e );
		}
		throw new PortsException( "Could not retrieve Students" );
	}
	public boolean studentIsOnRoll(int rollId, int stu_id) throws PortsException{
		String sql = "select section_id, student_id "+
						"from roll "+
						"where section_id="+rollId+" and student_id="+stu_id;
		try {
			if( p_stmt.execute( sql ) ) {
				ResultSet r = p_stmt.getResultSet();
				if(r.next()) return true;
				else return false;
			}
		}catch( Exception e ) {
			throw new PortsException( e );
		}
		throw new PortsException( "Could not retrieve Students" );
	}
	public void addStudentToRoll(int rollId, int stu_id) throws PortsException{
		String sql = "insert into roll ( section_id, student_id ) values ( " +
	                        "'" + rollId + "', " +
	                        "'" + stu_id + "' )";
		int inscnt = 0;
		try {
			inscnt = p_stmt.executeUpdate( sql );
		}catch( Exception e ) {
			throw new PortsException( e );
		}
	}
 	public void removeStudentFromRoll(int rollId, int stu_id) throws PortsException{
 		String sql = "delete from roll where section_id="+rollId+" and student_id="+stu_id;		
		try {
			p_stmt.execute( sql );
			return;
		}catch( Exception e ) {
			throw new PortsException( e );
		}
 	}
/*****************************************End Roll*****************************************/

/*****************************************Start ExamResult*****************************************/
		
	public void removeExamResult( int stu_id, int exam_id ) throws PortsException {
		String sql = "delete from exam_result where student_id="+stu_id+" and exam_id="+exam_id;	
		try {
			p_stmt.execute( sql );
			return;
		}catch( Exception e ) {
			throw new PortsException( e );
		}
	}
	
	public Iterator restoreExamResultAnswers( int stu_id, int exam_id ) throws PortsException{
		String sql = "select question_id, student_answer "+
						"from exam_result "+
						"where student_id="+stu_id+" and exam_id="+exam_id;
		try {
			if( p_stmt.execute( sql ) ) {
				ResultSet r = p_stmt.getResultSet();
				return new ExamResultIteratorImpl( r );
			}
		}catch( Exception e ) {
			throw new PortsException( e );
		}
		throw new PortsException( "Could not retrieve ExamResults" );
	}
	public void addExamResultAnswer( int stu_id, int exam_id, int q_id, int answer ) throws PortsException{
		String sql = "insert into exam_result ( student_id, exam_id, question_id, student_answer ) values ( " +
	                        "'" + stu_id + "', " +
	                        "'" + exam_id + "', " +
	                        "'" + q_id + "', " +
	                        "'" + answer + "' )";
		int inscnt = 0;
		try {
			inscnt = p_stmt.executeUpdate( sql );
		}catch( Exception e ) {
			throw new PortsException( e );
		}
	}
    public void removeExamResultAnswer( int stu_id, int exam_id, int q_id ) throws PortsException{
    	String sql = "delete from exam_result where student_id="+stu_id+" and exam_id="+stu_id+" and question_id="+q_id;		
		try {
			p_stmt.execute( sql );
			return;
		}catch( Exception e ) {
			throw new PortsException( e );
		}
    }
/*****************************************End ExamResult*****************************************/

};
