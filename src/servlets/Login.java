import java.io.*;
import java.util.*;

import javax.servlet.*;
import javax.servlet.http.*;

import freemarker.template.*;

import except.*;
import model.ctrl.*;
import model.entity.*;

public class Login extends HttpServlet {
  static  String templateDir = "WEB-INF/templates";
  private Configuration  cfg; 

  public void init() {
    // Prepare the FreeMarker configuration;
    // - Load templates from the WEB-INF/templates directory of the Web app.
    //
    cfg = new Configuration();
    cfg.setServletContextForTemplateLoading( getServletContext(), "WEB-INF/templates");
  }
  
  public void doGet( HttpServletRequest  req, HttpServletResponse res ) throws ServletException, IOException {
  	doPost(req, res);
  }

  public void doPost( HttpServletRequest  req, HttpServletResponse res ) throws ServletException, IOException {
    Template resultTemplate = null;
    BufferedWriter out = null;
    User u = null;
    String resultTemplateName = "Login.ftl",
    		username = null,
    		password = null, 
    		logout = null,
    		error = null;
	HttpSession session = req.getSession();
    // Get the parameters
    //
    username = req.getParameter( "username" );
    password = req.getParameter( "password" );
    logout = req.getParameter("logout");

    if( username != null && password != null ) {
    	
	    try {
	      u = CtrlLogin.login( username, password );
	      // store object into the session:
	      session.setAttribute( "user", u );
	      if(u instanceof Student)
	      	resultTemplateName = "StudentMenu.ftl";
	      else if(u instanceof Faculty)
	      	resultTemplateName = "FacultyMenu.ftl";
	      else if(u instanceof Administrator)
	      	resultTemplateName = "AdminMenu.ftl";
	    }catch ( Exception e ) {
	      error = "Invalid username or password"; //"Error logging in";
	    }
    }else if(logout != null){
    	session.invalidate();
    }else if(username == null && password == null){
    	try{
    		u = (User) session.getAttribute("user");
    		if(u instanceof Student)
	      	resultTemplateName = "StudentMenu.ftl";
	      else if(u instanceof Faculty)
	      	resultTemplateName = "FacultyMenu.ftl";
	      else if(u instanceof Administrator)
	      	resultTemplateName = "AdminMenu.ftl";
    	}catch(Exception e){};
    	error = "";
    }else error = "Invalid username or password";
    
    // Setup the data-model
    //
    Map vars = new HashMap();

    // Build the data-model
    //
    if(username != null)
    	vars.put( "username", username );
	if(error != null)
    	vars.put("error", error);


    // Load templates from the WEB-INF/templates directory of the Web app.
    //
    try {
      resultTemplate = cfg.getTemplate( resultTemplateName );
    }catch (IOException e) {
      throw new ServletException("Can't load template in: " + templateDir + ": " + e.toString());
    }

    // Prepare the HTTP response:
    // - Use the charset of template for the output
    // - Use text/html MIME-type
    //
    out = new BufferedWriter(new OutputStreamWriter( res.getOutputStream(), resultTemplate.getEncoding() ));
    res.setContentType("text/html; charset=" + resultTemplate.getEncoding());


    // Merge the data-model and the template
    //
    try {
      resultTemplate.process( vars, out );
      out.flush();
    }catch (TemplateException e) {
      throw new ServletException( "Error while processing template", e);
    }
    out.close();
  }
}

