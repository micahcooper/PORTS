import java.io.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;
import freemarker.template.*;

import except.*;
import model.ctrl.*;
import model.entity.*;

public class CreateDepartment
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

		String resultTemplateName = "CreateDepartment.ftl";
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
	
	/**
	*  doGET method
	*/
	public void doPost( HttpServletRequest req, HttpServletResponse res)
		throws ServletException, IOException
	{
		Template resultTemplate	= null;
		BufferedWriter toClient = null;
		
		int dept_id = -1;
		String dept_name = null;
		String resultTemplateName = "CreateDepartment.ftl";
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
		dept_name = req.getParameter( "dept_name" );

		// delete a Department from the db
		dept_id = CtrlMaintainDepartment.createDepartment( dept_name );

		if( dept_id == -1 )
			message = "Department could not be added, duplicate name found.";
		else
			message = dept_name+" successfully added with ID: "+dept_id;


		
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
			System.out.println( "!!!Error while processing CreateDepartment template" + e.getMessage() );
		}

		toClient.close();
	}

	
}
