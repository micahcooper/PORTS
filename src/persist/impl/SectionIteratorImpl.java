package persist.impl;

import java.util.*;
import java.sql.*;

import model.entity.*;
import except.*;


public class SectionIteratorImpl implements Iterator {

  ResultSet rs = null;
  boolean more = false;


  // Constructors
  
  // these two will be used to create a new object
  //
	public SectionIteratorImpl( ResultSet rs ) throws PortsException { 
		this.rs = rs;
		try {
			more = rs.next();
		}catch( Exception e ) {
			throw new PortsException( "Cannot create Section iterator; cause: " + e );
		}
	}

  public boolean hasNext(){ 
    return more; 
  }

  public Object next() {
    int s_id = 0;
    int course = 0;
    int faculty = 0;
    String days = null;
    String period = null;

    if( more ) {
		try {
			s_id = rs.getInt( 1 );
			course = rs.getInt( 2 );
			faculty = rs.getInt( 3 );
			days = rs.getString( 4 );
			period = rs.getString( 5 );
			more = rs.next();
		}catch( Exception e ) {
			throw new NoSuchElementException( "No next Section object; cause: " + e );
		}
      return ModelFactory.createSection( s_id, course, faculty, days, period );
    } else {
      throw new NoSuchElementException( "No next Section object" );
    }
  }

  public void remove()
  { throw new UnsupportedOperationException(); }

};
