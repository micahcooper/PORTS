package persist.impl;

import java.util.*;
import java.sql.*;

import model.entity.*;
import except.*;


public class ExamResultIteratorImpl implements Iterator {

  ResultSet rs = null;
  boolean more = false;


  // Constructors
  
  // these two will be used to create a new object
  //
	public ExamResultIteratorImpl( ResultSet rs ) throws PortsException { 
		this.rs = rs;
		try {
			more = rs.next();
		}catch( Exception e ) {
			throw new PortsException( "Cannot create ExamResult iterator; cause: " + e );
		}
	}

  public boolean hasNext(){ 
    return more; 
  }

  public Object next() {
    int q_id = 0;
    int answer = 0;

    if( more ) {
		try {
			q_id = rs.getInt( 1 );
			answer = rs.getInt( 2 );
			more = rs.next();
		}catch( Exception e ) {
			throw new NoSuchElementException( "No next ExamResult object; cause: " + e );
		}
		int[] result = new int[2];
		result[0] = q_id;
		result[1] = answer;
    	return result;
    } else {
      throw new NoSuchElementException( "No next ExamResult object" );
    }
  }

  public void remove()
  { throw new UnsupportedOperationException(); }

};
