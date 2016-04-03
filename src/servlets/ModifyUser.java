import java.io.*;
import java.util.*;
import java.lang.Integer;
import java.lang.Boolean;
import javax.servlet.*;
import javax.servlet.http.*;
import freemarker.template.*;

import except.*;
import model.ctrl.*;
import model.entity.*;

public class ModifyUser
	extends HttpServlet
{
	static  String         templateDir = "WEB-INF/templates";	
	private Configuration cfg;

	public void init()
	{
		cfg = new Configuration();
		cfg.setServletContextForTemplateLoading( getServletContext(),
							"WEB-INF/templates" );
	}
	
	/**
	*  doGET method
	*/
 	public void doGet( HttpServletRequest  req, HttpServletResponse res ) 
		throws ServletException, IOException 
	{  	
		Template resultTemplate	= null;
		BufferedWriter toClient = null;
		String resultTemplateName = "ModifyUser.ftl";

		boolean displaySearch = true;
		


    	// Load templates from the WEB-INF/templates directory of the Web app.
    	try {
    	  resultTemplate = cfg.getTemplate( resultTemplateName );
    	} 
    	catch (IOException e) {
    	  throw new ServletException( 
    	     "Can't load template in: " + templateDir + ": " + e.toString());
    	}


		//Prepare the Http response
		toClient = new BufferedWriter( new OutputStreamWriter( res.getOutputStream(), resultTemplate.getEncoding() ));
		res.setContentType("text/html; charset=" + resultTemplate.getEncoding() );

		try
		{
			resultTemplate = cfg.getTemplate( resultTemplateName );
		}
		catch( IOException e ) 
		{
			throw new ServletException( "Can't load template in: "+ templateDir +": " + e.toString() );	
		}

		try
		{
			Map root = new HashMap();	
			root.put( "found", false );
			root.put( "displaySearch", displaySearch );
			resultTemplate.process( root, toClient );

			toClient.flush();
		}
		catch( TemplateException e)
		{
			System.out.println( "!!!Error while processing FreeMarker template" + e.getMessage() );
		}

		toClient.close();
  	}
	
	/**
	*  doGET method
	*/
	public void doPost( HttpServletRequest req, HttpServletResponse res)
		throws ServletException, IOException
	{
		Template resultTemplate	= null;
		BufferedWriter toClient = null;
		String resultTemplateName = "ModifyUser.ftl";
		
		Map newValues = null;
		User u = null;
		AccessAuthorization aa = null;
		List userInfo = null;
		boolean found = false;
		boolean changed = false;
		int user_id = -1;
		String user_name = null;
		String user_title = null;
		String user_address = null;
		String auth_name = null;
		String auth_pass = null;
		String auth_role = null;
		String message = null;

		try
		{
			resultTemplate = cfg.getTemplate( resultTemplateName );
		}
		catch( IOException e ) 
		{
			throw new ServletException( "Can't load template in: "+ templateDir +
								": " + e.toString() );	
		}

		//Prepare the Http response
		toClient = new BufferedWriter(new OutputStreamWriter( res.getOutputStream(), resultTemplate.getEncoding() ));
		res.setContentType("text/html; charset=" + resultTemplate.getEncoding() );

		//  Get the parameters

		user_id = new Integer( req.getParameter( "user_id" ) ).intValue();
		user_title = req.getParameter( "user_title" );
		user_address = req.getParameter( "user_address" );
		auth_pass = req.getParameter( "auth_pass" );
		auth_role = req.getParameter( "auth_role" );
		changed = new Boolean( req.getParameter( "changed" ) ).booleanValue();

		// Setup the data-model
		Map root = new HashMap();

		// retrieve a Department from the db
		if( !changed )
		{
			u = CtrlMaintainUser.restoreUser( user_id );
			aa = CtrlMaintainUser.restoreAccessAuthorization( user_id );
		}

		//classroom was not found
		if( u == null && !changed )
		{
			message = "User was not found, please search again.";
			found = false;
		}
		//classroom was found 
		else if( u != null )
		{
			root.put( "user_id", u.getId() );
			root.put( "user_name", u.getName() );

			if( aa.getRole() == 1 )
			{
				root.put( "user_title", ((Student)u).getType() );
				root.put( "role1", "Student" );
				root.put( "role2", "Faculty" );
				root.put( "role3", "Administrator" );
				root.put( "value1", 1 );
				root.put( "value2", 2 );
				root.put( "value3", 3 );
			}
			if( aa.getRole() == 2 )
			{
				root.put( "user_title", ((Faculty)u).getTitle() );	
				root.put( "role1", "Faculty" );
				root.put( "role2", "Student" );
				root.put( "role3", "Administrator" );
				root.put( "value1", 2 );
				root.put( "value2", 1 );
				root.put( "value3", 3 );
			}
			if( aa.getRole() == 3 )
			{
				root.put( "user_title", ((Administrator)u).getTitle() );
				root.put( "role1", "Administrator" );
				root.put( "role2", "Faculty" );
				root.put( "role3", "Student" );
				root.put( "value1", 3 );
				root.put( "value2", 2 );
				root.put( "value3", 1 );
			}

			root.put( "user_address", u.getAddress() );
			root.put( "auth_name", aa.getUsername() );
			root.put( "auth_pass", aa.getPassword() ); 			
			found = true;

		}

		// The user object needs to be modified.
		if( changed )
		{		
			newValues = new HashMap();
			newValues.put( "user_id", user_id );
			newValues.put( "user_name", user_name );
			newValues.put( "user_address", user_address );
			newValues.put( "auth_pass", auth_pass );
			newValues.put( "auth_role", auth_role );

			if( CtrlMaintainUser.modifyUser( newValues ) )
			{
				message = "<p>Successfully updated <br>Name: " +user_name+ "<br>Title: " +user_title
							+"<br>Address: " +user_address+ "<br>Role: " +auth_role;
			}
			else
				message = "Did not successfully update the user, please try again.";
		}

		// build the data-model
		root.put( "message", message );
		root.put( "found", found );
		root.put( "displaySearch", false );
		
		try
		{	
			resultTemplate.process( root, toClient );
			toClient.flush();
		}
		catch( TemplateException e)
		{
			System.out.println( "!!!Error while processing ModifyUser template" + e.getMessage() );
		}

		toClient.close();
	}

	
}
