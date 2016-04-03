package persist.impl;

import java.util.*;
import java.sql.*;

import model.entity.*;
import except.*;


public class FacultyIteratorImpl implements Iterator {

  ResultSet rs = null;
  boolean more = false;


  // Constructors
  
  // these two will be used to create a new object
  //
	public FacultyIteratorImpl( ResultSet rs ) throws PortsException { 
		this.rs = rs;
		try {
			more = rs.next();
		}catch( Exception e ) {
			throw new PortsException( "Cannot create Faculty iterator; cause: " + e );
		}
	}

  public boolean hasNext(){ 
    return more; 
  }

  public Object next() {
    int s_id = 0;
    String name = null;
    String title = null;
    String address = null;

    if( more ) {
		try {
			s_id = rs.getInt( 1 );
			name = rs.getString( 2 );
			title = rs.getString( 3 );
			address = rs.getString( 4 );
			more = rs.next();
		}catch( Exception e ) {
			throw new NoSuchElementException( "No next Faculty object; cause: " + e );
		}
      return ModelFactory.createFaculty( s_id, name, title, address );
    } else {
      throw new NoSuchElementException( "No next Faculty object" );
    }
  }

  public void remove()
  { throw new UnsupportedOperationException(); }

};
