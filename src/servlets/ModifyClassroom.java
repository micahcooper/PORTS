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

public class ModifyClassroom
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
		String resultTemplateName = "ModifyClassroom.ftl";
		
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
		String resultTemplateName = "ModifyClassroom.ftl";
		
		Classroom classroom = null;
		List classInfo = null;
		List deptNames = null;
		boolean found = false;
		boolean changed = false;
		int class_id = -1;
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

		class_id = new Integer( req.getParameter( "class_id" ) ).intValue();
		class_dept = req.getParameter( "class_dept" );
		changed = new Boolean( req.getParameter( "changed" ) ).booleanValue();

		// Setup the data-model
		Map root = new HashMap();

		// retrieve a Department from the db
		if( !changed )
			classroom = CtrlMaintainClassroom.restoreClassroom( class_id );

		//classroom was not found
		if( classroom == null && !changed )
		{
			message = "Classroom could not be modified, pleas try again.";
			found = false;
		}
		//classroom was found 
		else if( classroom != null )
		{
			root.put( "class_id", classroom.getId() );
			root.put( "class_building", classroom.getBuilding() );
			root.put( "class_room", classroom.getRoom() );	
			root.put( "class_dept", CtrlMaintainDepartment.getDepartmentName( classroom.getDeptId() ) );
			deptNames = CtrlMaintainDepartment.getAllDepartmentNames();
			root.put( "deptNames", deptNames );
			
			found = true;

		}

		// The Department name has been modified.
		if( changed )
		{			
				if( CtrlMaintainClassroom.modifyClassroom( class_id,  class_dept ) != -1 )
					message = "Successfully moved classroom: " + class_id + ",  to " + class_dept;
				else
					message = "Did not successfully change the classroom, please try again.";
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
