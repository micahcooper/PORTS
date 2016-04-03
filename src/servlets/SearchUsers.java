import java.io.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;
import freemarker.template.*;

import except.*;
import model.ctrl.*;
import model.entity.*;

public class SearchUsers
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

 	public void doGet( HttpServletRequest  req, HttpServletResponse res ) 
		throws ServletException, IOException 	
	{
		Template resultTemplate	= null;
		BufferedWriter toClient = null;

		String resultTemplateName = "SearchUsers.ftl";
    	// Load templates from the WEB-INF/templates directory of the Web app.
	    //
    	try {
    	  resultTemplate = cfg.getTemplate( resultTemplateName );
    	} 
    	catch (IOException e) {
    	  throw new ServletException( 
    	     "Can't load template in: " + templateDir + ": " + e.toString());
    	}


		//Prepare the Http response
		toClient = new BufferedWriter(
         new OutputStreamWriter( res.getOutputStream(), resultTemplate.getEncoding() )
	 			 );
	
		res.setContentType("text/html; charset=" + resultTemplate.getEncoding() );

		try
		{
			//System.out.println( this.getServletContext().getRealPath("/") );
			resultTemplate = cfg.getTemplate( resultTemplateName );
		}
		catch( IOException e ) 
		{
			throw new ServletException( "Can't load template in: "+ templateDir +
								": " + e.toString() );	
		}

		Map root = new HashMap();

		try
		{	
			resultTemplate.process( root, toClient );

			toClient.flush();
		}
		catch( TemplateException e)
		{
			System.out.println( "!!!Error while processing FreeMarker template" + e.getMessage() );
		}

		toClient.close();

  		//doPost(req, res);
  	}

	public void doPost( HttpServletRequest req, HttpServletResponse res)
		throws ServletException, IOException
	{
		Template resultTemplate	= null;
		BufferedWriter toClient = null;
		String resultTemplateName = "SearchUsers.ftl";

		List userList = null;
		List users = null;
		List user = null;
		User u = null;
		String user_name = null;
		String user_title = null;
		String user_type = null;
		String user_address = null;
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
		toClient = new BufferedWriter( 
			new OutputStreamWriter( res.getOutputStream(), resultTemplate.getEncoding() ));
	
		res.setContentType("text/html; charset=" + resultTemplate.getEncoding() );

		//  Get the parameters
		user_name = req.getParameter( "user_name" );
		user_title = req.getParameter( "user_title" );
		user_type = req.getParameter( "user_type" );

		// search for the departments
		if( user_type.equals("1") )
			userList = CtrlMaintainUser.searchStudents( user_name, user_title );
		if( user_type.equals("2") )
			userList = CtrlMaintainUser.searchFaculty( user_name, user_title );
		if( user_type.equals("3") )
			userList = CtrlMaintainUser.searchAdministrators( user_name, user_title );
		
		// Setup the data-model
		Map root = new HashMap();

		// build the data-model
		users = new LinkedList();
		//add table headers
		root.put( "userId", "<b>User ID</b>" );
		root.put( "userName", "<b>User Name</b>" );
		root.put( "userTitle", "<b>User Title</b>");
		root.put( "userAddress", "<b>User Address</b>");


		if( userList == null )
		{
			message = "No users could be located, please try again.";		
		}
		else{
			for( int i=0; i < userList.size(); i++ )
			{
				u = (User) userList.get( i );
				user = new LinkedList();
				user.add( u.getId() );
				user.add( u.getName() );
				if( user_type.equals("1") )
					user.add( ((Student)u).getType() );
				if( user_type.equals("2") )
					user.add( ((Faculty)u).getTitle() );
				if( user_type.equals("3") )
					user.add( ((Administrator)u).getTitle() );
				user.add( u.getAddress() );
				users.add( user );
			}

		}
		//add table data
		root.put( "users", users );
		root.put( "message", message );

		try
		{	
			resultTemplate.process( root, toClient );

			toClient.flush();
		}
		catch( TemplateException e)
		{
			System.out.println( "!!!Error while processing FreeMarker template" + e.getMessage() );
		}

		toClient.close();
	}

	
}
