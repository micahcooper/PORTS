package persist.impl;

import java.util.*;
import java.sql.*;

import model.entity.*;
import except.*;


public class DepartmentIteratorImpl implements Iterator {

  ResultSet rs = null;
  boolean more = false;


  // Constructors
  
  // these two will be used to create a new object
  //
	public DepartmentIteratorImpl( ResultSet rs ) throws PortsException {
		this.rs = rs;
		try {
			more = rs.next();
		}catch( Exception e ) {
			throw new PortsException( "Cannot create Department iterator; cause: " + e );
		}
	}

  public boolean hasNext(){ 
    return more; 
  }

  public Object next() {
    int s_id = 0;
    String name = null;

    if( more ) {
		try {
			s_id = rs.getInt( 1 );
			name = rs.getString( 2 );
			more = rs.next();
		}catch( Exception e ) {
			throw new NoSuchElementException( "No next Department object; cause: " + e );
		}
      return ModelFactory.createDepartment( s_id, name );
    } else {
      throw new NoSuchElementException( "No next Department object" );
    }
  }

  public void remove()
  { throw new UnsupportedOperationException(); }

};
