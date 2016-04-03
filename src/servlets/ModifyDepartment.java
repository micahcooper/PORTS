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

public class ModifyDepartment
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
		String resultTemplateName = "ModifyDepartment.ftl";
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
		String resultTemplateName = "ModifyDepartment.ftl";
		
		Department department = null;
		List deptInfo = null;
		boolean found = false;
		boolean changed = false;
		int dept_id = -1;
		String dept_name = null;
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
		dept_id = new Integer( req.getParameter( "dept_id" ) ).intValue();
		dept_name = req.getParameter( "dept_name" );
		changed = new Boolean( req.getParameter( "changed" ) ).booleanValue();

		// Setup the data-model
		Map root = new HashMap();

		// retrieve a Department from the db
		if( !changed )
			department = CtrlMaintainDepartment.restoreDepartment( dept_id );

		if( department == null && !changed )
		{
			message = "Department could not be modified.";
			found = false;
		}
		else if( department != null || dept_name.length() < 4 )
		{
			root.put( "dept_name", department.getName() );	
			root.put( "dept_id", dept_id );
			found = true;
		}

		// The Department name has been modified.
		if( changed )
		{			
			if( dept_name.length() <= 4 )
			{
				changed = false;
				message = "Length of Department does not contain enough characters.";
			}
			else
			{
				CtrlMaintainDepartment.modifyDepartment( dept_id,  dept_name );
				message = "Successfully modified " + dept_name;
			}
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
			System.out.println( "!!!Error while processing CreateDepartment template" + e.getMessage() );
		}

		toClient.close();
	}

	
}
