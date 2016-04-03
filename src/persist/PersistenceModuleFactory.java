package persist;

import java.sql.*;
import except.*;
import persist.impl.*;

public class PersistenceModuleFactory {

  private static final String url = "jdbc:mysql://localhost:3306/ports";
  private static final String user = "root";
  private static final String pass = "";
  
  private static Connection p_conn;
  
  static{
  	if(p_conn == null){
  		try {
			// create the driver for MySQL
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			// establish the databse connection
			p_conn = DriverManager.getConnection( url, user, pass );
		}catch( Exception e ) {	// just in case...
			e.printStackTrace();
		}
  	}
  }

  public static PersistenceModule createPersistenceModule() throws PortsException{
  		return new PersistenceModuleImpl( p_conn );
  }

};
