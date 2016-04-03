import java.io.*;
import java.util.*;

import javax.servlet.*;
import javax.servlet.http.*;

import freemarker.template.*;

import except.*;
import model.ctrl.*;
import model.entity.*;

public class FacultyMenu extends HttpServlet {

	static  String templateDir = "WEB-INF/templates";
	private Configuration  cfg; 
	
	public void init() {
    		// Prepare the FreeMarker configuration;
	    	// Load templates from the WEB-INF/templates directory of the Web app.
    		cfg = new Configuration();
	    	cfg.setServletContextForTemplateLoading( getServletContext(), "WEB-INF/templates");
  	}//init
  
  	public void doGet( HttpServletRequest  req, HttpServletResponse res ) throws ServletException, IOException {
  		doPost(req, res);
  	}//doGet

	public void doPost( HttpServletRequest  req, HttpServletResponse res ) throws ServletException, IOException {
		Template resultTemplate = null;
		BufferedWriter out = null;
		String resultTemplateName = "FacultyMenu.ftl";	

		//<> RESPONSE IDENTIFIERS
		Map results = new HashMap();
		results.put("error", "");

		//<> LOAD TEMPLATES
		try{
			resultTemplate = cfg.getTemplate(resultTemplateName);
		}catch(IOException e){
			throw new ServletException("Cant load template in: " + templateDir + ": " + e.toString());
		}

		//<> CONSTRUCT HTTP RESPONSE 
		out = new BufferedWriter(new OutputStreamWriter(res.getOutputStream(), resultTemplate.getEncoding()));
		res.setContentType("text/html; charset=" + resultTemplate.getEncoding());
		
		//MERGE PORTS RESPONSE (CtrlClass) TO TEMPLATE (ui_class)
		try{
			resultTemplate.process(results, out);
			out.flush();
		}catch (TemplateException e) {
      			throw new ServletException( "Error while processing template", e);
    		}
    		out.close();
	}//doPost

}//FacultyMenu
