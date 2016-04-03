package model.ctrl;

import java.lang.Integer;

import java.util.ArrayList;
import java.util.List;
import java.util.LinkedList;
import java.util.Iterator;
import java.util.Map;
import java.util.HashMap;

import model.entity.*;
import model.entity.impl.*;

import except.*;

import persist.*;

/**
* This class maintains the operations for interacting with
*  user objects and the ui interface.
*/
public class CtrlMaintainCourse
{

	/**
	*  Creates a new Course object and stores the object
	*	in the database.
	*
	* @param dept_id		the department's primary id
	* @param course_name	the name of the course 
	* @param course_desc	the description of the course
	*/
	public static int createCourse( int dept_id, String course_name, String course_desc )
	{
		PersistenceModule pm = null;
		Course c = null;
		Iterator courseIterator = null;

		try
		{
			pm = PersistenceModuleFactory.createPersistenceModule();
			courseIterator = pm.restoreCourses();

			while( courseIterator.hasNext() )
			{
				c = (Course) courseIterator.next();

				if( c.getName().equalsIgnoreCase( course_name ) )
					throw new PortsException( "Course already exists in the PORTS db.");							
			}

			c = ModelFactory.createCourse( 0, dept_id, course_name, course_desc );

			return pm.storeCourse( c );
		}
		catch( PortsException pe )
		{
			System.out.println( pe );
			return -1;
		}

	}

	/**
	*
	*
	*/
	public static List searchCourses( String course_name, String course_desc, String dept_name )
	{
		PersistenceModule pm = null;
		List foundCourse = new ArrayList<Course>();
		List foundCourse2 = new ArrayList<Course>();
		Iterator courseIterator = null;
		Iterator departmentIterator = null;
		Course course = null;
		Department d = null;
		int dept_id = 0;
		boolean flag = false;

		if( !dept_name.equals("") )
			dept_id = CtrlMaintainDepartment.getDepartmentId( dept_name );

		try
		{		
			pm = PersistenceModuleFactory.createPersistenceModule();
			courseIterator = pm.restoreCourses();

			while( courseIterator.hasNext() )
			{
				course = (Course) courseIterator.next();
	
				if( course_name.equals("") && course_desc.equals("") )
				{
					foundCourse.add( course );

				}
	
				if( course_name.equals("") && !course_desc.equals("") && course.getDesc().matches( ".*"+course_desc+".*" ) )
					foundCourse.add( course );
	
				if( !course_name.equals("") && course.getName().matches(".*"+course_name+".*" ) && course_desc.equals("") )
					foundCourse.add( course );

				if( !course_name.equals("") && course.getName().matches( ".*"+course_name+".*" ) 
						&& !course_desc.equals("") && course.getDesc().matches( ".*"+course_desc+".*" ) )
					foundCourse.add( course );
			}
			
			if( dept_id != 0 )
			{

				flag = true;
				for( int i=0; i < foundCourse.size(); i++)
				{


					if( ((Course)foundCourse.get(i)).getDeptId() == dept_id )
					{

System.out.println( "MaintainCourse.searchClassrooms: " + ((Course)foundCourse.get(i)).getName() 
					+", ID: "+ ((Course)foundCourse.get(i)).getId() +" against: " +  dept_id);

						foundCourse2.add( foundCourse.get(i) );
					}
				}
			}
			
			if( !flag )
				return foundCourse;
			else
				return foundCourse2;
		}
		catch( PortsException pe )
		{
			System.out.println("!!!(08) Exception building course list.  " + pe );
			return null;
		}
	}

	/**
	*
	*
	*/
	public static boolean deleteCourse( int course_id )
	{
		PersistenceModule pm = null;
		
		try
		{
			pm = PersistenceModuleFactory.createPersistenceModule();

			pm.removeCourse( course_id );
			return true;
		}
		catch( PortsException pe )
		{
			System.out.println( "!!! Could not remove the course: " + pe );
			return false;
		}
	}
}
