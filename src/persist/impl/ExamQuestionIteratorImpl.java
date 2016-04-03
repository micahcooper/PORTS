package persist.impl;

import java.util.*;
import java.sql.*;

import model.entity.*;
import except.*;


public class ExamQuestionIteratorImpl implements Iterator {

  ResultSet rs = null;
  boolean more = false;


  // Constructors
  
  // these two will be used to create a new object
  //
	public ExamQuestionIteratorImpl( ResultSet rs ) throws PortsException { 
		this.rs = rs;
		try {
			more = rs.next();
		}catch( Exception e ) {
			throw new PortsException( "Cannot create ExamQuestion iterator; cause: " + e );
		}
	}

  public boolean hasNext(){ 
    return more; 
  }

  public Object next() {
    int s_id = 0;
    String text = null;
    int answer = 0;

    if( more ) {
		try {
			s_id = rs.getInt( 1 );
			text = rs.getString( 2 );
			answer = rs.getInt( 3 );
			more = rs.next();
		}catch( Exception e ) {
			throw new NoSuchElementException( "No next ExamQuestion object; cause: " + e );
		}
      return ModelFactory.createExamQuestion( s_id, text, answer );
    } else {
      throw new NoSuchElementException( "No next ExamQuestion object" );
    }
  }

  public void remove()
  { throw new UnsupportedOperationException(); }

};
