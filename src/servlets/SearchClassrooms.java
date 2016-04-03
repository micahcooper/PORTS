import java.io.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;
import freemarker.template.*;

import except.*;
import model.ctrl.*;
import model.entity.*;

public class SearchClassrooms
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

		String resultTemplateName = "SearchClassrooms.ftl";
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
		String resultTemplateName = "SearchClassrooms.ftl";

		List roomList = null;
		List classrooms = null;
		List classroom = null;
		Classroom c = null;
		String class_building = null;
		String class_room = null;
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
		class_building = req.getParameter( "class_building" );
		class_room = req.getParameter( "class_room" );
		dept_name = req.getParameter( "dept_name" );

		try
		{
			// search for the departments
			roomList = CtrlMaintainClassroom.searchClassrooms( class_building, class_room, dept_name );
		}
		catch( Exception e )
		{
			
		}
		
		// Setup the data-model
		Map root = new HashMap();

		// build the data-model
		classrooms = new LinkedList();
		//add table headers
		root.put( "classroomId", "Classroom ID" );
		root.put( "building", "Building" );
		root.put( "room", "Room");
		root.put( "department", "Department");


		if( roomList == null )
		{
			message = "Classroom could not be located, please try again.";		
		}
		else{
			for( int i=0; i < roomList.size(); i++ )
			{
				c = (Classroom) roomList.get( i );
				classroom = new LinkedList();
				classroom.add( c.getId() );
				classroom.add( c.getBuilding() );
				classroom.add( c.getRoom() );
				classroom.add( CtrlMaintainDepartment.getDepartmentName( c.getDeptId() ) );
				classrooms.add( classroom );
			}

		}
		//add table data
		root.put( "classrooms", classrooms );
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
