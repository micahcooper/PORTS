package persist.impl;

import java.util.*;
import java.sql.*;

import model.entity.*;
import except.*;


public class ExamIteratorImpl implements Iterator {

  ResultSet rs = null;
  boolean more = false;


  // Constructors
  
  // these two will be used to create a new object
  //
	public ExamIteratorImpl( ResultSet rs ) throws PortsException { 
		this.rs = rs;
		try {
			more = rs.next();
		}catch( Exception e ) {
			throw new PortsException( "Cannot create Exam iterator; cause: " + e );
		}
	}

  public boolean hasNext(){ 
    return more; 
  }

  public Object next() {
    int s_id = 0;
    int section = 0;
    String name = null;
    String general_instructions = null;
    String special_instructions = null;
    String directions = null;
    int length = 0;
    java.util.Date edate = null;

    if( more ) {
		try {
			s_id = rs.getInt( 1 );
			section = rs.getInt( 2 );
			name = rs.getString( 3 );
			general_instructions = rs.getString( 4 );
			special_instructions = rs.getString( 5 );
			directions = rs.getString( 6 );
			length = rs.getInt( 7 );
			edate = rs.getDate( 8 );
			more = rs.next();
		}catch( Exception e ) {
			throw new NoSuchElementException( "No next Exam object; cause: " + e );
		}
      return ModelFactory.createExam( s_id, section, name, general_instructions, special_instructions, directions, length, edate );
    } else {
      throw new NoSuchElementException( "No next Exam object" );
    }
  }

  public void remove()
  { throw new UnsupportedOperationException(); }

};
