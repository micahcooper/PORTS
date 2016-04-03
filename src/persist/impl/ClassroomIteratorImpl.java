package persist.impl;

import java.util.*;
import java.sql.*;

import model.entity.*;
import except.*;


public class ClassroomIteratorImpl implements Iterator {

  ResultSet rs = null;
  boolean more = false;


  // Constructors
  
  // these two will be used to create a new object
  //
	public ClassroomIteratorImpl( ResultSet rs ) throws PortsException { 
		this.rs = rs;
		try {
			more = rs.next();
		}catch( Exception e ) {
			throw new PortsException( "Cannot create Classroom iterator; cause: " + e );
		}
	}

  public boolean hasNext(){ 
    return more; 
  }

  public Object next() {
    int s_id = 0;
    String room = null;
    String building = null;
    int dept = 0;

    if( more ) {
		try {
			s_id = rs.getInt( 1 );
			room = rs.getString( 2 );
			building = rs.getString( 3 );
			dept = rs.getInt( 4 );
			more = rs.next();
		}catch( Exception e ) {
			throw new NoSuchElementException( "No next Classroom object; cause: " + e );
		}
      return ModelFactory.createClassroom( s_id, room, building, dept );
    } else {
      throw new NoSuchElementException( "No next Classroom object" );
    }
  }

  public void remove()
  { throw new UnsupportedOperationException(); }

};
