package persist.impl;

import java.util.*;
import java.sql.*;

import model.entity.*;
import except.*;


public class CourseIteratorImpl implements Iterator {

  ResultSet rs = null;
  boolean more = false;


  // Constructors
  
  // these two will be used to create a new object
  //
	public CourseIteratorImpl( ResultSet rs ) throws PortsException { 
		this.rs = rs;
		try {
			more = rs.next();
		}catch( Exception e ) {
			throw new PortsException( "Cannot create Course iterator; cause: " + e );
		}
	}

  public boolean hasNext(){ 
    return more; 
  }

  public Object next() {
    int s_id = 0;
    int dept = 0;
    String name = null;
    String desc = null;

    if( more ) {
		try {
			s_id = rs.getInt( 1 );
			dept = rs.getInt( 2 );
			name = rs.getString( 3 );
			desc = rs.getString( 4 );
			more = rs.next();
		}catch( Exception e ) {
			throw new NoSuchElementException( "No next Course object; cause: " + e );
		}
      return ModelFactory.createCourse(s_id, dept, name, desc);
    } else {
      throw new NoSuchElementException( "No next Course object" );
    }
  }

  public void remove()
  { throw new UnsupportedOperationException(); }

};
