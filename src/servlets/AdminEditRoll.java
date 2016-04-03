import java.io.*;
import java.util.*;

import javax.servlet.*;
import javax.servlet.http.*;

import freemarker.template.*;

import except.*;
import model.ctrl.*;
import model.entity.*;

public class AdminEditRoll extends HttpServlet {
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
    String resultTemplateName = "AdminEditRoll.ftl",
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
    res.setContentType("text/plain; charset=UTF-8");
    
    String dept = req.getParameter( "dept" );
    String course = req.getParameter( "course" );
    String section = req.getParameter( "section" );
    String selected = req.getParameter( "selected" );
    String enroll = req.getParameter( "enroll" );
    String student = req.getParameter( "student" );
    String unenroll = req.getParameter( "unenroll" );
    String search = req.getParameter( "search" );
    
    HttpSession session = req.getSession();
	User u = (User) session.getAttribute( "user" );
    
    StringBuffer content = new StringBuffer();
    
	content.append("{\n");	content.append("  \"results\": {\n");	content.append("    \"bindings\": [\n\n");
    
    if(dept != null){
    	try{
	    	for(Iterator iter = CtrlRegistration.getDepartments(); iter.hasNext();){
	    		Department d = (Department) iter.next();
	    		content.append("      {\n");				content.append("        \"department\": { \"id\": \""+d.getId()+"\", \"name\": \""+d.getName()+"\"  }\n");				content.append("      } ,\n");
	    	}
	    	content.setCharAt(content.length()-2, ' ');
    	}catch(PortsException e){}
    }else if(course != null){
    	try{
	    	for(Iterator iter = CtrlRegistration.getCourses(Integer.parseInt(course)); iter.hasNext();){
	    		Course c = (Course) iter.next();
	    		content.append("      {\n");				content.append("        \"course\": { \"id\": \""+c.getId()+"\", \"dept\": \""+
											c.getDeptId()+"\", \"name\": \""+c.getName()+"\", \"desc\": \""+c.getDesc()+"\" }\n");				content.append("      } ,\n");
	    	}
	    	content.setCharAt(content.length()-2, ' ');
	    }catch(PortsException e){}
    }else if(search != null && section != null){
    	try{
	    	for(Iterator iter = CtrlAdminEditRoll.getStudents(Integer.parseInt(section), search); iter.hasNext();){
	    		Student s = (Student) iter.next();
	    		content.append("      {\n");				content.append("        \"student\": { \"id\": \""+s.getId()+"\", \"name\":\""+
										s.getName()+"\", \"type\": \""+s.getType()+"\", \"address\": \""+
										s.getAddress()+"\"  }\n");				content.append("      } ,\n");
	    	}
	    	content.setCharAt(content.length()-2, ' ');
	    }catch(PortsException e){}
    }else if(section != null){
    	try{
	    	for(Iterator iter = CtrlRegistration.getSections(Integer.parseInt(section)); iter.hasNext();){
	    		Section s = (Section) iter.next();
	    		Faculty f = CtrlRegistration.getFaculty(s.getFacultyId());
	    		String facName = f.getName();
	    		content.append("      {\n");				content.append("        \"section\": { \"id\": \""+s.getId()+"\", \"course\":\""+
										s.getCourseId()+"\", \"faculty\": \""+facName+"\", \"days\": \""+
										s.getDays()+"\", \"period\": \""+s.getPeriod()+"\"  }\n");				content.append("      } ,\n");
	    	}
	    	content.setCharAt(content.length()-2, ' ');
	    }catch(PortsException e){}
    }else if(selected != null){
    	try{
	    	for(Iterator iter = CtrlAdminEditRoll.getStudentsOnRoll(Integer.parseInt(selected)); iter.hasNext();){
	    		Student s = (Student) iter.next();
	    		content.append("      {\n");				content.append("        \"student\": { \"id\": \""+s.getId()+"\", \"name\":\""+
										s.getName()+"\", \"type\": \""+s.getType()+"\", \"address\": \""+
										s.getAddress()+"\"  }\n");				content.append("      } ,\n");
	    	}
	    	content.setCharAt(content.length()-2, ' ');
    	}catch(PortsException e){}
    }else if(enroll != null && student != null){
    	content.append("      {\n");
    	try{
	    	int sectionId = Integer.parseInt(enroll);
	    	int studentId = Integer.parseInt(student);
	    	CtrlRegistration.enrollStudent(sectionId, studentId);
	    	content.append("        \"success\": \"true\"\n");
	    }catch(PortsException e){content.append("        \"success\": \"false\"\n");}
	    content.append("      } ,\n");
    }else if(unenroll != null && student != null){
    	content.append("      {\n");
    	try{
	    	int sectionId = Integer.parseInt(unenroll);
	    	int studentId = Integer.parseInt(student);
	    	CtrlRegistration.unEnrollStudent(sectionId, studentId);
	    	content.append("        \"success\": \"true\"\n");
	    }catch(PortsException e){content.append("        \"success\": \"false\"\n");}
	    content.append("      } ,\n");
    }
    content.append("    ]\n");	content.append("  }\n");	content.append("}\n");
    out.println(content.toString());
  }

}