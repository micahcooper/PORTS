import java.io.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;
import freemarker.template.*;

import except.*;
import model.ctrl.*;
import model.entity.*;

public class SearchDepartments
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

		String resultTemplateName = "SearchDepartments-Result.ftl";
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
		List dbDepts = null;
		List departments = null;
		List department = null;
		Department d = null;
		String dept_name = null;
		String resultTemplateName = "SearchDepartments-Result.ftl";

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
		toClient = new BufferedWriter( 
			new OutputStreamWriter( res.getOutputStream(), resultTemplate.getEncoding() ));
	
		res.setContentType("text/html; charset=" + resultTemplate.getEncoding() );

		//  Get the parameters
		dept_name = req.getParameter( "dept_name" );

		try
		{
			// search for the departments
			dbDepts = CtrlMaintainDepartment.searchDepartments( dept_name );
		}
		catch( Exception e )
		{
			
		}
		
		// Setup the data-model
		Map root = new HashMap();

		// build the data-model
		departments = new LinkedList();
		//add table headers
		root.put( "departmentId", "Department ID" );
		root.put( "departmentName", "Department Name" );


		if( dbDepts.size() <= 0 )
		{
			System.out.println("!!!(servlet)Did not find any departments.");
			department = new LinkedList();
			department.add( "" );
			department.add( dept_name+" does not exist" );
			departments.add( department );
			
		}
		else{
			for( int i=0; i < dbDepts.size(); i++ )
			{
				d = (Department) dbDepts.get( i );
				department = new LinkedList();
				department.add( d.getId() );
				department.add( d.getName() );
				departments.add( department );
			}

		}
		//add table data
		root.put( "departments", departments );


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
