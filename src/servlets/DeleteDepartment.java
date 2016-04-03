import java.io.*;
import java.util.*;
import java.lang.Integer;
import javax.servlet.*;
import javax.servlet.http.*;
import freemarker.template.*;

import except.*;
import model.ctrl.*;
import model.entity.*;

public class DeleteDepartment
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

		String resultTemplateName = "DeleteDepartment.ftl";
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

	public void doPost( HttpServletRequest req, HttpServletResponse res)
		throws ServletException, IOException
	{
		Template resultTemplate	= null;
		BufferedWriter toClient = null;
		int dept_id = -1;
		String resultTemplateName = "DeleteDepartment.ftl";
		String message = null;
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
		toClient = new BufferedWriter( 
			new OutputStreamWriter( res.getOutputStream(), resultTemplate.getEncoding() ));
	
		res.setContentType("text/html; charset=" + resultTemplate.getEncoding() );

		//  Get the parameters
		dept_id = new Integer( req.getParameter( "dept_id" ) ).intValue();

		// delete a Department from the db
		removed = CtrlMaintainDepartment.deleteDepartment( dept_id );

		if( removed )
			message = "Department successfully deleted";
		else
			message = "Department could not be found, please check the ID.";

		
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
