package model.ctrl;

import java.lang.Integer;

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

public class CtrlMaintainUser 
{

	/**
	* Returns a List of Student objects, based on the provided
	* 	search values.
	*
	*/
	public static List searchStudents( String user_name, String user_title )
	{
		PersistenceModule pm = null;
		List foundUser = null;
		Iterator userIterator = null;
		User u = null;

		try
		{
			pm = PersistenceModuleFactory.createPersistenceModule();
			foundUser = new LinkedList<User>();
			userIterator = pm.restoreStudents();


			while( userIterator.hasNext() )
			{
				u = (Student) userIterator.next();

				if( user_name.equals("") && user_title.equals("") )
					foundUser.add( u );
				if( !user_name.equals("") && u.getName().matches( ".*"+user_name+".*" ) && user_title.equals("") )
					foundUser.add( u );
				if( user_name.equals("") && user_title.equals( ((Student)u).getType())  )
					foundUser.add( u );
				if( !user_name.equals("") && u.getName().matches( ".*"+user_name+".*" ) && user_title.equals( ( ((Student)u).getType() )))
					foundUser.add( u );
			}

			return foundUser;
			
		}
		catch( PortsException pe )
		{
			System.out.println("!!!(02) Problems with retrieving students in MaintainUser, "+ pe);
			return null;
		}

	}//search students

	/**
	* Returns a List of Faculty objects, based on the provided
	* 	search values.
	*
	*/
	public static List searchFaculty( String user_name, String user_title )
	{
		PersistenceModule pm = null;
		List foundUser = null;
		Iterator userIterator = null;
		Faculty u = null;

		try
		{
			pm = PersistenceModuleFactory.createPersistenceModule();
			foundUser = new LinkedList<User>();
			userIterator = pm.restoreFaculty();

			while( userIterator.hasNext() )
			{
				u = (Faculty) userIterator.next();
			
				if( user_name.equals("") && user_title.equals("") )
					foundUser.add( u );
				if( !user_name.equals("") && u.getName().matches( ".*"+user_name+".*" ) && user_title.equals("") )
					foundUser.add( u );
				if( user_name.equals("") && user_title.equals( u.getTitle() ) )
					foundUser.add( u );
				if( !user_name.equals("") && u.getName().matches( ".*"+user_name+".*" ) && user_title.equals( u.getTitle() ) )
					foundUser.add( u );
			}

			return foundUser;
			
		}
		catch( PortsException pe )
		{
			System.out.println("!!!(03) Problems with retrieving faculty in MaintainUser, "+ pe);
			return null;
		}

	}//search faculty

	/**
	* Returns a List of Administrator objects, based on the provided
	* 	search values.
	*
	*/
	public static List searchAdministrators( String user_name, String user_title )
	{
		PersistenceModule pm = null;
		List foundUser = null;
		Iterator userIterator = null;
		Administrator u = null;

		try
		{
			pm = PersistenceModuleFactory.createPersistenceModule();
			foundUser = new LinkedList<User>();
			userIterator = pm.restoreAdministrators();


			while( userIterator.hasNext() )
			{
				u = (Administrator) userIterator.next();			

				if( user_name.equals("") && user_title.equals("") )
					foundUser.add( u );
				if( !user_name.equals("") && u.getName().matches( ".*"+user_name+".*" ) && user_title.equals("") )
					foundUser.add( u );
				if( user_name.equals("") && user_title.equals( u.getTitle() ) )
					foundUser.add( u );
				if( !user_name.equals("") && u.getName().matches( ".*"+user_name+".*" ) && user_title.equals( u.getTitle() ) )
					foundUser.add( u );
			}

			return foundUser;
			
		}
		catch( PortsException pe )
		{
			System.out.println("!!!(04) Problems with retrieving Administrator in MaintainUser, "+ pe);
			return null;
		}

	}//search faculty

	/**
	*  Removes an User from the PORTS database.
	*
	* @param user_id 		the primary id of the user
	*/
	public static boolean deleteUser( int user_id )
	{
		PersistenceModule pm = null;
		User u = null;
		Iterator userIterator = null;

		try	
		{
			pm = PersistenceModuleFactory.createPersistenceModule();

			pm.removeUser( user_id );
			return true;
		}
		catch( PortsException pe )
		{
			System.out.println( pe );
			return false;
		}
	}

	/**
	*  Retrieves the user name for a user object based on 
	*    the primary user id.
	*
	* @param user_id 		the primary id of a user
	*/
	public static String getUserName( int user_id )
	{
		PersistenceModule pm = null;
		Iterator userIterator = null;
		User u = null;

		try
		{
			pm = PersistenceModuleFactory.createPersistenceModule();

			userIterator = pm.restoreStudents();
			while( userIterator.hasNext() )
			{
				u = (Student) userIterator.next();

				if( u.getId() == user_id )
					return u.getName();
			}

			userIterator = pm.restoreFaculty();
			while( userIterator.hasNext() )
			{
				u = (Faculty) userIterator.next();
			
				if( u.getId() == user_id )
					return u.getName();
			}
	
			userIterator = pm.restoreAdministrators();
			while( userIterator.hasNext() )
			{
				u = (Administrator) userIterator.next();
	
				if( u.getId() == user_id )
					return u.getName();
			}
			
			throw new PortsException( "getUserName() did not locate a user with id: "+user_id );
		}
		catch( PortsException pe )
		{
			System.out.println("!!! " + pe );
			return null;
		}
	}//getUserName()

	/**
	*  Creates and stores a new User object
	*
	* @param user_name 		the name of the user
	* @param user_title		the type of the user
	* @param user_address	the address of the user
	*/
	public static int createUser( String user_name, String user_title, String user_address, String auth_name, String auth_pass )
	{
		PersistenceModule pm = null;
		User u = null;
		int user_id = 0;

		try
		{
			pm = PersistenceModuleFactory.createPersistenceModule();
			
			if( user_title.equals("undergraduate") )
			{
				u = ModelFactory.createStudent( 0, user_name, user_title, user_address );
				user_id = pm.storeStudent( (Student) u );				
				u.setId( user_id );
				addToAccessAuthorization( u, auth_name, auth_pass, 1 );
				return user_id;
			}
			if( user_title.equals("graduate") )
			{
				u = ModelFactory.createStudent( 0, user_name, user_title, user_address );
				user_id = pm.storeStudent( (Student) u );
				u.setId( user_id );
				addToAccessAuthorization( u, auth_name, auth_pass, 1 );
				return user_id;
			}
			if( user_title.equals("professor") )
			{
				u = ModelFactory.createFaculty( 0, user_name, user_title, user_address );
				user_id = pm.storeFaculty( (Faculty) u );
				u.setId( user_id );
				addToAccessAuthorization( u, auth_name, auth_pass, 2 );
				return user_id;
			}
			if( user_title.equals( "systems administrator" ) )
			{
				u = ModelFactory.createAdministrator( 0, user_name, user_title, user_address );
				user_id = pm.storeAdministrator( (Administrator) u );
				u.setId( user_id );
				addToAccessAuthorization( u, auth_name, auth_pass, 3 );
				return user_id;
			}
		
			throw new PortsException( "Could not store the user in the control class." );
		}
		catch( PortsException pe )
		{
			System.out.println("!!! Problems saving user: " + pe );
			return -1;
		}
	}

	/**
	*  Add a new user to the Access Authorizaton table
	* 	in the PORTS db.
	*
	* @param u 				the newly created user object 
	* @param auth_name 		the login name for a user
	* @param auth_pass		the password for logging in
	* @param user_role		the access role for the user
	*/
	public static void addToAccessAuthorization( User u, String auth_name, String auth_pass, int user_role )
	{
		PersistenceModule pm = null;
		AccessAuthorization aa = null;
		
		try
		{
			pm = PersistenceModuleFactory.createPersistenceModule();
			aa = ModelFactory.createAccessAuthorization( u.getId(), auth_name, auth_pass, user_role );

			pm.storeAccessAuthorization( aa );
			
 		}
		catch( PortsException pe )
		{
			System.out.println( pe );
		}
	}//addToAccessAuthorization

	/**
	*  Retrieves from the PORTS db the user properties and returns
	* 	a User object.
	*
	* @param user_id 		the id of the user object
	*/
	public static User restoreUser( int user_id )
	{
		PersistenceModule pm = null;
		Iterator userIterator = null;
		User u = null;

		try
		{
			pm = PersistenceModuleFactory.createPersistenceModule();

			userIterator = pm.restoreStudents();
			while( userIterator.hasNext() )
			{
				u = (Student) userIterator.next();

				if( u.getId() == user_id )
					return pm.restoreStudent( user_id );
			}

			userIterator = pm.restoreFaculty();
			while( userIterator.hasNext() )
			{
				u = (Faculty) userIterator.next();
			
				if( u.getId() == user_id )
					return pm.restoreFaculty( user_id );
			}
	
			userIterator = pm.restoreAdministrators();
			while( userIterator.hasNext() )
			{
				u = (Administrator) userIterator.next();
	
				if( u.getId() == user_id )
					return pm.restoreAdministrator( user_id );
			}
			
			throw new PortsException( "restorUser() did not locate a user with id: "+user_id );
		}
		catch( PortsException pe )
		{
			System.out.println("!!! " + pe );
			return null;
		}
	}

	/**
	*  Retrieves the access role from the PORTS db table AccessAuthorization
	*
	* @param user_id 		primary of id of the user
	*/
	public static int getUserRole( int user_id )
	{
		PersistenceModule pm = null;
		AccessAuthorization aa = null;
		Iterator roles = null;

		try
		{
			pm = PersistenceModuleFactory.createPersistenceModule();
			
			aa = pm.restoreAccessAuthorization( user_id );

			return aa.getRole();
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
	public static AccessAuthorization restoreAccessAuthorization( int user_id )
	{
		PersistenceModule pm = null;
		
		try
		{
			pm = PersistenceModuleFactory.createPersistenceModule();
		
			return pm.restoreAccessAuthorization( user_id );
		}	
		catch( PortsException pe )
		{
			return null;
		}
	}

	/*
	*  Modify a User object and update Access Authorizations object.
	*
	* @param user_id 		the primary id of the user
	*/
	public static boolean modifyUser( Map newValues )
	{
		PersistenceModule pm = null;
		User u = null;
		AccessAuthorization aa = null;
		int user_id = 0;
		String user_name = null;
		String user_title = null;
		String user_address = null;
		String auth_pass = null;
		int auth_role = 0;
		
		try
		{
			pm = PersistenceModuleFactory.createPersistenceModule();
			user_id = new Integer( (newValues.get( "user_id" )).toString() ).intValue() ;
			user_name = newValues.get( "user_name" ).toString();
			user_title = newValues.get( "user_title" ).toString();
			user_address = newValues.get( "user_address" ).toString();
			auth_pass = newValues.get( "auth_pass" ).toString();
			auth_role = new Integer( (newValues.get( "auth_role" )).toString() ).intValue();

			if( getUserRole( user_id ) == 1 )
			{
				u = pm.restoreStudent( user_id );
				((Student)u).setType( user_title );
				u.setName( user_name );
				u.setAddress( user_address );

				pm.storeStudent( (Student) u );
			}
			if( getUserRole( user_id ) == 2 )
			{	
				u = pm.restoreFaculty( user_id );
				((Faculty)u).setTitle( user_title );
				u.setName( user_name );
				u.setAddress( user_address );

				pm.storeFaculty( (Faculty) u );
			}
			if( getUserRole( user_id ) == 3 )
			{
				u = pm.restoreAdministrator( user_id );
				((Administrator)u).setTitle( user_title );
				u.setName( user_name );
				u.setAddress( user_address );

				pm.storeAdministrator( (Administrator) u );
			}

			aa = pm.restoreAccessAuthorization( user_id );
			aa.setPassword( auth_pass );
			aa.setRole( auth_role );
		
			pm.updateAccessAuthorization( aa );

			return true;
		}
		catch( PortsException pe )
		{
			System.out.println("!!!(32)Could not modify user in MaintainUser");
			return false;
		}
	}
}
