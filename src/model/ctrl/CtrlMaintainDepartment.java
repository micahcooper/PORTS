package model.ctrl;

import model.entity.*;
import model.entity.impl.*;
import java.util.List;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Iterator;
import persist.*;
import except.*;

public class CtrlMaintainDepartment{

	/**
	*  Restore a single Department object form the PORTS db.
	*
	* @param dept_id 		the primary id of a department
	*/
	public static Department restoreDepartment( int dept_id )
	{
		PersistenceModule pm = null;
		Department dept = null;

		try
		{
			pm = PersistenceModuleFactory.createPersistenceModule();
		
			dept = pm.restoreDepartment( dept_id );

			return dept;
		}
		catch( PortsException pe )
		{
			return null;
		}
	}
	/**
 	* Modifies the department object's name
 	*
 	* @param dept_id		the id of the department to change
 	* @param dept_name		the new name of the department
 	*/
	public static boolean modifyDepartment( int dept_id, String dept_name ) 
	{
		PersistenceModule pm = null;
		Department dept = null;

		try

		{
			pm = PersistenceModuleFactory.createPersistenceModule();
			//retrieve the department from db

			dept = pm.restoreDepartment( dept_id );
	   		//change the object's name
	   		if( dept == null )
				throw new PortsException( "Department could not be found." );
			else
				dept.setName( dept_name );
			//store to the databse
			pm.storeDepartment( dept );		
			
			return true;
		}
		catch( PortsException pe )
		{
			System.out.println("!!!(17)Problems modifying dept: " + pe );
			return false;
		}
	} 
	/**
 	* Creates a new Department object in memory and adds
 	*  a new entry in the PORTS Deparment table
 	*
 	* @param dept_name	name of the new department
 	*/ 
	public static int createDepartment( String dept_name ) 
	{
		PersistenceModule pm = null;
		Department dept = null;
		Iterator dept_iterator = null;

		try
		{
			pm = PersistenceModuleFactory.createPersistenceModule();
			dept_iterator = pm.restoreDepartments();

			while( dept_iterator.hasNext() )
			{
				dept = (Department) dept_iterator.next();
				if( dept.getName().equalsIgnoreCase( dept_name ) );
					throw new PortsException( "Department exists already" );
			}
			dept = ModelFactory.createDepartment( 0, dept_name );
		
			return pm.storeDepartment( dept );
		}
		catch( PortsException pe )
		{
			System.out.println( "!!!(22)Problem in create dept: " + pe );
			return -1;
		}
			

	}

	/**
 	* Removes from memory if instantiated the Department object
 	*  identified by its name; deletes the entry from PORTS table
 	*  Department.
 	*
 	* @param dept_name	the name of the department
 	*/ 
	public static boolean deleteDepartment ( int dept_id ) 
	{
		PersistenceModule pm = null;
		
		try
		{
			pm = PersistenceModuleFactory.createPersistenceModule();

			if( pm.departmentExists( dept_id ) )
			{
					pm.removeDepartment( dept_id );
					return true;			
			}
			else
				return false;
		}
		catch( PortsException pe )
		{
			return false;
		}
	}

	/**
 	* Retrieves a list of Department objects based on department's
 	*  name.
 	*
 	*  @param dept_name		the name of the department
 	*
 	*/ 
	public static List searchDepartments( String dept_name ) throws PortsException{
		//the database connection module
		PersistenceModule pm = PersistenceModuleFactory.createPersistenceModule();	
		//the list of found departments
		List foundDept = new ArrayList<Department>();
		//iterates over the found results in the query
		Iterator dept_iterator = null;
		//a deparment object found
		Department dept	= null;

		dept_iterator = pm.restoreDepartments( );


		if( dept_name.equals("") )
		{
			while( dept_iterator.hasNext() )
		  	{
				dept = (Department) dept_iterator.next();
				foundDept.add( dept );
			}
		}
		else
		{
		  	while( dept_iterator.hasNext() )
		  	{
				dept = (Department) dept_iterator.next();

				if( dept.getName().matches(".*"+dept_name+".*" ) )
					foundDept.add( dept );
			}
		}

		return foundDept;
	}
	/*
	* Retrieves the department name for primary id of Department.
	*
	* @param dept_id		the primary identifier for the department
	*/
	public static String getDepartmentName( int dept_id )
	{
		PersistenceModule pm = null;
		Department d = null;

		try
		{
			pm = PersistenceModuleFactory.createPersistenceModule();
			
			d = pm.restoreDepartment( dept_id );
		
			return d.getName();
		}
		catch( PortsException pe)
		{
			return null;
		}
	} 
	/*
	*  Retrieves the department for the unique identifier department name.
	*/
	public static int getDepartmentId( String dept_name )
	{
		PersistenceModule pm = null;
		Department d = null;
		Iterator departmentIterator = null;

		try
		{
			pm = PersistenceModuleFactory.createPersistenceModule();
			departmentIterator = pm.restoreDepartments();			

			while( departmentIterator.hasNext() )
			{
				d = (Department) departmentIterator.next();
System.out.println("!!! dept_name: "+dept_name+" against "+d.getName() );
				if( d.getName().equalsIgnoreCase( dept_name ) )
					return d.getId();				
			}
			
			throw new PortsException( "Could not locate the department" );
		}
		catch( PortsException pe)
		{
			System.out.println( "!!!(10)Could not retrieve department id in MaintainDepartment." + pe );
			return -1;
		}
	}
	
	/**
	*  Retrieves all the department names for the PORTS
	*	database.
	*/
	public static List getAllDepartmentNames()
	{
		PersistenceModule pm = null;
		Iterator departmentIterator = null;
		List deptNames = null;

		try
		{
			pm = PersistenceModuleFactory.createPersistenceModule();
			departmentIterator = pm.restoreDepartments();
			deptNames = new LinkedList<String>();				

			while( departmentIterator.hasNext() )
			{
				deptNames.add( ( ((Department)departmentIterator.next()).getName() ) );
			}

			return deptNames;
		}
		catch( PortsException pe )
		{
			System.out.println( "!!!(11)Could not getAllDepartmentNames in MaintainDepartment.");
			return null;
		}
	}
}
