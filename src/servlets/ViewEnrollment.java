import java.io.*;
import java.util.*;

import javax.servlet.*;
import javax.servlet.http.*;

import freemarker.template.*;

import except.*;
import model.ctrl.*;
import model.entity.*;

public class ViewEnrollment extends HttpServlet {
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
 	Template resultTemplate = null;
    BufferedWriter out = null;
    User u = null;
    String resultTemplateName = "ViewEnrollment.ftl",
    		error = null;

    HttpSession session = req.getSession();
	u = (User) session.getAttribute( "user" );
    
    // Setup the data-model
    //
    Map vars = new HashMap();

    // Build the data-model
    //
    if(u == null) resultTemplateName = "Login.ftl";


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

  public void doPost( HttpServletRequest  req, HttpServletResponse res ) throws ServletException, IOException {
  	PrintWriter out = res.getWriter();
    res.setContentType("text/html; charset=UTF-8");
    
    String sections = req.getParameter( "sections" );
    String selected = req.getParameter( "selected" );

    HttpSession session = req.getSession();
	User u = (User) session.getAttribute( "user" );
    
    StringBuffer content = new StringBuffer();
    
	content.append("{\n");	content.append("  \"results\": {\n");	content.append("    \"bindings\": [\n\n");    
    
	if(sections != null){
    	try{
	    	for(Iterator iter = CtrlViewEnrollment.getFacultySections(u.getId()); iter.hasNext();){
	    		Section s = (Section) iter.next();
	    		Course c = CtrlRegistration.getCourse(s.getCourseId());
	    		String courseName = c.getName();
	    		content.append("      {\n");				content.append("        \"section\": { \"id\": \""+s.getId()+"\", \"course\":\""+
										courseName+"\", \"faculty\": \""+s.getFacultyId()+"\", \"days\": \""+
										s.getDays()+"\", \"period\": \""+s.getPeriod()+"\"  }\n");				content.append("      } ,\n");
	    	}
	    	content.setCharAt(content.length()-2, ' ');
	    }catch(PortsException e){}
    }else if(selected != null){
    	try{
	    	for(Iterator iter = CtrlViewEnrollment.getStudentsOnRoll(Integer.parseInt(selected)); iter.hasNext();){
	    		Student s = (Student) iter.next();
	    		content.append("      {\n");				content.append("        \"student\": { \"id\": \""+s.getId()+"\", \"name\":\""+
										s.getName()+"\", \"type\": \""+s.getType()+"\", \"address\": \""+
										s.getAddress()+"\"  }\n");				content.append("      } ,\n");
	    	}
	    	content.setCharAt(content.length()-2, ' ');
	    }catch(PortsException e){}
    }
     content.append("    ]\n");	content.append("  }\n");	content.append("}\n");
    out.println(content.toString());
  }
}