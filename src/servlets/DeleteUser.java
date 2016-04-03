import java.io.*;
import java.util.*;
import java.lang.Integer;
import javax.servlet.*;
import javax.servlet.http.*;
import freemarker.template.*;

import except.*;
import model.ctrl.*;
import model.entity.*;

public class DeleteUser
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

	/*******************************doGet****************************/

	/**
	*  the get method for the servlet
	*
	* @param req
	* @param res
	*/
 	public void doGet( HttpServletRequest  req, HttpServletResponse res ) 
		throws ServletException, IOException 
	{  	
		Template resultTemplate	= null;
		BufferedWriter toClient = null;

		String resultTemplateName = "DeleteUser.ftl";
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
         	new OutputStreamWriter( res.getOutputStream(), resultTemplate.getEncoding() ));
	
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
			resultTemplate.process( root, toClient );

			toClient.flush();
		}
		catch( TemplateException e)
		{
			System.out.println( "!!!Error while processing FreeMarker template" + e.getMessage() );
		}

		toClient.close();
  	}
	
	/********************************doPost*****************************************/


	/**
	*  The post action for the servlet.
	*
	* @param req		the Http request object
	* @param res		the Http response object
	*/
	public void doPost( HttpServletRequest req, HttpServletResponse res)
		throws ServletException, IOException
	{
		Template resultTemplate	= null;
		BufferedWriter toClient = null;
		String resultTemplateName = "DeleteUser.ftl";

		int user_id = -1;
		String message = null;
		String user_name = null;
		boolean removed = false;

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

		//Prepare the Http response
		toClient = new BufferedWriter(new OutputStreamWriter( res.getOutputStream(), resultTemplate.getEncoding() ));
		res.setContentType("text/html; charset=" + resultTemplate.getEncoding() );

		//  Get the parameters
		user_id = new Integer( req.getParameter( "user_id" ) ).intValue();
System.out.println("!!! User ID:  "+user_id);
		// delete a Department from the db
		user_name = CtrlMaintainUser.getUserName( user_id );
		removed = CtrlMaintainUser.deleteUser( user_id );
System.out.println("!!! User deleted?: " + removed );	

		if( removed )
			message = user_name + " with ID: " + user_id + " successfully deleted";
		else
			message = "User could not be found, please check the ID.";

		
		// Setup the data-model
		Map root = new HashMap();

		// build the data-model
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
