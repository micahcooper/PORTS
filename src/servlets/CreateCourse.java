import java.io.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;
import freemarker.template.*;

import except.*;
import model.ctrl.*;
import model.entity.*;

public class CreateCourse
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

		List deptNames = null;

		String resultTemplateName = "CreateCourse.ftl";
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
			deptNames = CtrlMaintainDepartment.getAllDepartmentNames();
			root.put( "deptNames", deptNames );	
			
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
	*  doPost method
	*/
	public void doPost( HttpServletRequest req, HttpServletResponse res)
		throws ServletException, IOException
	{
		Template resultTemplate	= null;
		BufferedWriter toClient = null;
		String resultTemplateName = "CreateCourse.ftl";
		
		int course_id = -1;
		int dept_id = -1;
		List deptNames = null;
		String course_name = null;
		String course_desc = null;
		String course_dept = null;
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
		course_name = req.getParameter( "course_name" );
		course_desc = req.getParameter( "course_desc" );
		course_dept = req.getParameter( "course_dept" );

		dept_id = CtrlMaintainDepartment.getDepartmentId( course_dept );
		course_id = CtrlMaintainCourse.createCourse( dept_id, course_name, course_desc );

		// create course a for the db
		if( course_id == -1 )
			message = "Course was not created, please try again.";
		else
			message = "<b>" + course_name + " was created with ID: " + course_id + "</b>";

		
		// Setup the data-model
		Map root = new HashMap();

		// build the data-model
		root.put( "message", message );
		deptNames = CtrlMaintainDepartment.getAllDepartmentNames();
		root.put( "deptNames", deptNames );

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
