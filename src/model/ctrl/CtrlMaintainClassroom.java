package model.ctrl;

import java.util.List;
import java.util.ArrayList;
import java.util.Iterator;

import model.entity.*;
import model.entity.impl.*;

import except.*;

import persist.*;

public class CtrlMaintainClassroom{

	/**
 	* Returns a List of Classroom objects based on the building and room
 	*  parameters; the two parameters may be null.
 	*
 	* @param class_building 	the name of the buiding
 	* @param class_room 		the room number(possibly letters)
 	*/ 
	public static List searchClassrooms( String class_building, String class_room, String dept_name )
	{
		PersistenceModule pm = null;
		List foundClassroom = new ArrayList<Classroom>();
		Iterator classroomIterator = null;
		Iterator departmentIterator = null;
		Classroom classroom = null;
		Department d = null;
		int dept_id = 0;
		String room = null;
		String building = null;

		if( !dept_name.equals("") )
			dept_id = CtrlMaintainDepartment.getDepartmentId( dept_name );

		try
		{		
			pm = PersistenceModuleFactory.createPersistenceModule();
			classroomIterator = pm.restoreClassrooms();

			while( classroomIterator.hasNext() )
			{
				classroom = (Classroom) classroomIterator.next();
				room = classroom.getRoom();
				building = classroom.getBuilding();
	
				if( class_building.equals("") && class_room.equals("") )
					foundClassroom.add( classroom );
	
				if( class_building.equals("") && room.equalsIgnoreCase( class_room ) )
					foundClassroom.add( classroom );
	
				if( !class_building.equals("") && building.matches(".*"+class_building+".*" ) && class_room.equals("") )
					foundClassroom.add( classroom );

				if( classroom.getBuilding().matches( ".*"+class_building+".*" ) && room.equalsIgnoreCase( class_room ) )
					foundClassroom.add( classroom );
			}
			
			if( !dept_name.equals("") )
			{
				for( int i=0; i < foundClassroom.size(); i++)
					if( ((Classroom)foundClassroom.get(i)).getDeptId() != dept_id )
						foundClassroom.remove(i);
			}

			return foundClassroom;
		}
		catch( PortsException pe )
		{
			return null;
		}
	}//getClassroom

	/**
 	* Creates a new Classroom object and add a new entry
 	*  in the PORTS database.
 	*
 	* @param class_building 	the name of the buiding
 	* @param class_room 		the room number(possibly letters)
 	* @param dept_id 	the building's primary id
 	*/ 
	public static int createClassroom( String class_building, String class_room, int dept_id )
	{
		PersistenceModule pm = null;
		Classroom classroom = null;
		int c_id = 0;
		Iterator classroomIterator = null;



		try
		{
			if( CtrlMaintainDepartment.getDepartmentName( dept_id ) == null )
				throw new PortsException(" The department does not exist." );
			
			pm = PersistenceModuleFactory.createPersistenceModule();
			classroomIterator = pm.restoreClassrooms();
	
			while( classroomIterator.hasNext() )
			{
				classroom = (Classroom) classroomIterator.next();
				if( classroom.getRoom().equalsIgnoreCase( class_room ) &&
					classroom.getBuilding().equalsIgnoreCase( class_building ) )
					throw new PortsException( "This room already exists in the PORTS system.");
			}

			classroom = ModelFactory.createClassroom( 0, class_room, class_building, dept_id );		
		
			return pm.storeClassroom( classroom );
		}
		catch( PortsException pe )
		{
			System.out.println("!!!(23)Could not create a new classroom in MaintainClassroom" + pe);
			return -1;
		}
	}//addClassroom

	/**
 	* Removes the Classroom object based on the building
 	*  and room parameters; deletes classroom entry from the PORTS
 	*  database.
 	*  
 	* @param class_building 	the name of the buiding
 	* @param class_room 		the room number(possibly letters)
 	*/ 
	public static boolean deleteClassroom( int class_id  )
	{
		PersistenceModule pm = null;
				
		try
		{
			pm = PersistenceModuleFactory.createPersistenceModule();

			pm.removeClassroom( class_id );
			
			return true;
		}
		catch( PortsException pe )	
		{
			System.out.println( "!!!(02) Could not delete the classroom from PORTS db." );
			return false; 
		}

	}//deleteClassroom

	/**
 	* Changes the Classroom object attributes in the system and
 	*  modifies the entry in the PORTS Classroom table.
 	*
 	* @param class_id			the primary id of the Classroom object
 	* @param class_building 	the name of the buiding
 	* @param class_room 		the room number(possibly letters)
 	*/ 
	public static int modifyClassroom( int class_id, String class_dept )
	{
		PersistenceModule pm = null;
		Classroom classroom = null;
		String dept_name = null;
		int dept_id = 0;

		try
		{
			pm = PersistenceModuleFactory.createPersistenceModule();
			//retrieve the department for the PORTS db
			classroom = pm.restoreClassroom( class_id );

			if( classroom != null )
			{
				dept_id = CtrlMaintainDepartment.getDepartmentId( class_dept );

System.out.println("!!!DeptId " + dept_id );
		
				if( dept_id == -1 )
					throw new PortsException( "Department does not exist." );
				else
					classroom.setDept( dept_id );
			}

			return pm.storeClassroom( classroom );
		}
		catch( PortsException pe )
		{
			System.out.println( "!!!Could not modify the classroom object from MaintainClassroom." + pe );
			return -1;
		}
	}//modifyRoom

	/**
	*  Restores a Classroom object.
	*
	* @param class_id		the primary indentifier of the object
	*/
	public static Classroom restoreClassroom( int class_id )
	{
		PersistenceModule pm = null;
		Classroom c = null;

		try
		{
			pm = PersistenceModuleFactory.createPersistenceModule();

			return pm.restoreClassroom( class_id );			
		}
		catch( PortsException pe )
		{
			System.out.println( "!!!Could not restore classroom in MaintainClassroom." );
			return null;
		}
	}



}//crtl_AdminEditClassroom
