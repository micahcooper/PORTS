package persist.impl;

import java.util.*;
import java.sql.*;

import model.entity.*;
import except.*;


public class QuestionChoiceIteratorImpl implements Iterator {

  ResultSet rs = null;
  boolean more = false;


  // Constructors
  
  // these two will be used to create a new object
  //
	public QuestionChoiceIteratorImpl( ResultSet rs ) throws PortsException { 
		this.rs = rs;
		try {
			more = rs.next();
		}catch( Exception e ) {
			throw new PortsException( "Cannot create QuestionChoice iterator; cause: " + e );
		}
	}

  public boolean hasNext(){ 
    return more; 
  }

  public Object next() {
    int s_id = 0;
    String text = null;
    int number = 0;

    if( more ) {
		try {
			s_id = rs.getInt( 1 );
			text = rs.getString( 2 );
			number = rs.getInt( 3 );
			more = rs.next();
		}catch( Exception e ) {
			throw new NoSuchElementException( "No next QuestionChoice object; cause: " + e );
		}
      return ModelFactory.createQuestionChoice( s_id, text, number );
    } else {
      throw new NoSuchElementException( "No next QuestionChoice object" );
    }
  }

  public void remove()
  { throw new UnsupportedOperationException(); }

};
