import java.io.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;
import freemarker.template.*;

import except.*;
import model.ctrl.*;
import model.entity.*;

public class CreateClassroom
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

		String resultTemplateName = "CreateClassroom.ftl";
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
		String resultTemplateName = "CreateClassroom.ftl";
		
		int class_id = -1;
		int dept_id = -1;
		List deptNames = null;
		String class_building = null;
		String class_room = null;
		String class_dept = null;
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
		class_building = req.getParameter( "class_building" );
		class_room = req.getParameter( "class_room" );
		class_dept = req.getParameter( "class_dept" );

		dept_id = CtrlMaintainDepartment.getDepartmentId( class_dept );

		// delete a Department from the db
		if( dept_id != -1 )
			class_id = CtrlMaintainClassroom.createClassroom( class_building, class_room, dept_id );
		else
			message = "Classroom could not be added, the department does exist.";

		if( class_id == -1 && dept_id != -1)
			message = "Classroom could not be added, duplicate name found.";
		else if( dept_id != -1 )
			message = "Classroom successfully added with ID: "+class_id;


		
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
