import java.io.*;
import java.lang.Boolean;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;
import freemarker.template.*;

import except.*;
import model.ctrl.*;
import model.entity.*;

public class SearchCourses
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
		String resultTemplateName = "SearchCourses.ftl";

		List deptNames = null;

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
		toClient = new BufferedWriter( new OutputStreamWriter( res.getOutputStream(), resultTemplate.getEncoding() ));
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

  		//doPost(req, res);
  	}

	public void doPost( HttpServletRequest req, HttpServletResponse res)
		throws ServletException, IOException
	{
		Template resultTemplate	= null;
		BufferedWriter toClient = null;
		String resultTemplateName = "SearchCourses.ftl";

		List courseList = null;
		List deptNames = null;
		List courses = null;
		List course = null;
		Course c = null;
		String course_name = null;
		String course_desc = null;
		String dept_name = null;
		String message = null;
		int course_id = 0;
		boolean flag = false;


		try
		{
			resultTemplate = cfg.getTemplate( resultTemplateName );
		}
		catch( IOException e ) 
		{
			throw new ServletException( "Can't load template in: "+ templateDir + ": " + e.toString() );	
		}


		//Prepare the Http response
		toClient = new BufferedWriter( new OutputStreamWriter( res.getOutputStream(), resultTemplate.getEncoding() ));
		res.setContentType("text/html; charset=" + resultTemplate.getEncoding() );

		//  Get the parameters
		course_name = req.getParameter( "course_name" );
		course_desc = req.getParameter( "course_desc" );
		dept_name = req.getParameter( "dept_name" );
		flag = new Boolean( req.getParameter( "flag" ).toString() ).booleanValue();

		// search for the departments
		if(!flag)
			courseList = CtrlMaintainCourse.searchCourses( course_name, course_desc, "" );
		else
			courseList = CtrlMaintainCourse.searchCourses( "", "", dept_name );
		
		// Setup the data-model
		Map root = new HashMap();

		// build the data-model
		courses = new LinkedList();
		//add table headers
		root.put( "courseId", "<b>Course ID</b>" );
		root.put( "courseName", "<b>Course Name</b>" );
		root.put( "courseDept", "<b>Course Department</b>");
		root.put( "courseDesc", "<b>Course Description</b>");
		deptNames = CtrlMaintainDepartment.getAllDepartmentNames();
		root.put( "deptNames", deptNames );


		if( courseList == null )
		{
			message = "Courses could not be located, please try again.";		
		}
		else{
			for( int i=0; i < courseList.size(); i++ )
			{
				c = (Course) courseList.get( i );
				course = new LinkedList();
				course.add( c.getId() );
				course.add( c.getName() );
				course.add( CtrlMaintainDepartment.getDepartmentName( c.getDeptId() ) );
				course.add( c.getDesc() );
				courses.add( course );
			}

		}
		//add table data
		root.put( "courses", courses );
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
