import java.io.*;
import java.util.*;
import java.text.DateFormat;

import javax.servlet.*;
import javax.servlet.http.*;

import freemarker.template.*;

import except.*;
import model.ctrl.*;
import model.entity.*;
import model.entity.impl.*;

public class FacultyCreateExam extends HttpServlet {

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
  	}

	public void doPost( HttpServletRequest  req, HttpServletResponse res ) throws ServletException, IOException {
		
		//<> FIELDS
		Template resultTemplate = null;
		BufferedWriter out = null;
		String resultTemplateName = "FacultyCreateExam.ftl";	
		String error = "";
		
		//<> FORM FIELDS
		int exam_id = 0;
		String exam_section = null;
		String exam_section_name = null;
		
		String section = null;
		String exam_year = null; 
		String exam_month = null; 
		String exam_dayOfMonth = null;
		Date exam_date = null;
		GregorianCalendar calendar =  null;
		
		String hours = null;
		String minutes = null;
		int exam_length = -1;
		
		String exam_name = null;
		String exam_directions = null;
		String exam_general_instructions = null;
		String exam_special_instructions = null;

		String faculty_name = null;
		
		//GET USER FROM SESSION
		HttpSession session = req.getSession();
		Faculty faculty = (Faculty) session.getAttribute("user");		

		//<> RESPONSE IDENTIFIERS
		Map results = new HashMap();
	
		try{	
			//<> GET FORM FIELDS	
			
			try{
				exam_name = req.getParameter("exam_name");
				faculty_name = faculty.getName();
				exam_section = req.getParameter("section_list");
				exam_month = req.getParameter("exam_month");	
				exam_dayOfMonth = req.getParameter("exam_dayOfMonth");	
				exam_year = req.getParameter("exam_year");
		
				if(!exam_year.equals("") && !exam_month.equals("") && !exam_dayOfMonth.equals("")){
					calendar = new GregorianCalendar((int)Integer.parseInt(exam_year)+2000, (int)(Integer.parseInt(exam_month)-1), (int)(Integer.parseInt(exam_dayOfMonth)));
					exam_date = calendar.getTime();		
				}//if
			
				hours = req.getParameter("hours");	
				minutes = req.getParameter("minutes");	
				if(!hours.equals("") && (hours != null) && !minutes.equals("") && minutes != null){
					exam_length = Integer.parseInt(hours)*60 + Integer.parseInt(minutes);		
				}
			}catch(Exception e){
				error = "internal error";
			}

			exam_directions = req.getParameter("exam_directions");
			exam_general_instructions = req.getParameter("exam_general_instructions");
			exam_special_instructions = req.getParameter("exam_special_instructions");
			
			Exam exam = new ExamImpl(Integer.parseInt(exam_section), exam_name, exam_general_instructions, exam_special_instructions, exam_directions, exam_length, exam_date);
			Section e_section = (Section)exam.getSection();
			exam_id = exam.getId();
			int section_id = e_section.getId();				
			String days = e_section.getDays();
			String period = e_section.getPeriod();	
			String course_name = ((Course)e_section.getCourse()).getName();								
			exam_section_name = course_name+"-"+days+"-"+period;

			//<> CHECK FORM FIELDS
			try{
				results.put("faculty_name", faculty_name);				
				results.put("exam_id", exam_id);
				results.put("exam_name", exam_name);
				results.put("exam_year", exam_year);
				results.put("exam_month", exam_month);
				results.put("exam_dayOfMonth", exam_dayOfMonth);
				results.put("exam_date", exam_date);
				results.put("exam_section", exam_section);
				results.put("exam_section_name", exam_section_name);
				results.put("hours", hours);
				results.put("minutes", minutes);
				results.put("exam_length", exam_length);
				results.put("exam_general_instructions", exam_general_instructions);
				results.put("exam_special_instructions", exam_special_instructions);		
				results.put("exam_directions", exam_directions);
			}catch(Exception e){
				error = "error with map";
			}
			
			if(
				(!exam_name.equals("")) && (!exam_section.equals("")) && (exam_date != null) && (exam_length != -1) && 
				(!exam_general_instructions.equals("")) && (!exam_directions.equals(""))
			){
				error = "exam_section_name: " + exam_section_name;	
				results.put("error", error);			
				resultTemplateName = "FacultyCreateExam-Results.ftl";

			}else{
				error = "There were errors submitting your information <p> "; 
				if(exam_name.equals(""))
					error += "Exam Name not specified <br>";
				if(exam_section.equals(""))
					error += "Exam Section not specified <br>";
				if(exam_year.equals("")){
					error += "Exam Year not specified <br>";
				}else if(Integer.parseInt(exam_year) < 7 || Integer.parseInt(exam_year) > 8){
					error = "Invalid Exam Year. <i> (enter: 2007-2008)</i><br>";
				}
				if(exam_month.equals("")){
					error += "Exam Month not specified <br> ";
				}else if(Integer.parseInt(exam_month) <-1 || Integer.parseInt(exam_month) > 12){
					error += "Invalid Exam Month. <i> (enter: 1-12)</i> <br>";
				}
				if(exam_dayOfMonth.equals("")){
					error += "Exam Day not specified <br> ";
				}else if(Integer.parseInt(exam_dayOfMonth) > 31 || Integer.parseInt(exam_dayOfMonth) < 0){
					error += "Invalid Exam Day. <i> (enter: 1-31)</i><br>";
				}
				if (exam_general_instructions.equals(""))
					error += "General Exam Instructions must specified <br> ";
				if(exam_directions.equals(""))
					error += "Exam Directions not specified <br> ";
					
			}//if-else block
				
			results.put("error", error);
		
		}catch(Exception e){
			error = "Please specify all required fields";
			results.put("error", error);
		}

		//<> LOAD TEMPLATES
		try{
			resultTemplate = cfg.getTemplate(resultTemplateName);
		}catch(IOException e){
			throw new ServletException("Cant load template in: " + templateDir + ": " + e.toString());
		}

		//<> CONSTRUCT HTTP RESPONSE 
		out = new BufferedWriter(new OutputStreamWriter(res.getOutputStream(), resultTemplate.getEncoding()));
		res.setContentType("text/html; charset=" + resultTemplate.getEncoding());
		
		//<> ACTIVATE CONTROL CLASS
		try{
			Exam exam = CtrlFacultyCreateExam.createExam(Integer.parseInt(exam_section), exam_name, exam_general_instructions, exam_special_instructions, exam_directions, exam_length, exam_date);
			
			Section e_section = (Section)exam.getSection();
			exam_id = exam.getId();
			int section_id = e_section.getId();				
			String days = e_section.getDays();
			String period = e_section.getPeriod();	
			String course_name = ((Course)e_section.getCourse()).getName();								
			exam_section_name = course_name+"-"+days+"-"+period;
			
			results.put("exam_section", Integer.toString(section_id));
			results.put("exam_id", exam_id);
			results.put("exam_section_name", exam_section_name);
			results.put("faculty_name", faculty_name);				
			results.put("exam_id", exam_id);
			results.put("exam_name", exam_name);
			results.put("exam_year", exam_year);
			results.put("exam_month", exam_month);
			results.put("exam_dayOfMonth", exam_dayOfMonth);
			results.put("exam_date", exam_date);
			results.put("hours", hours);
			results.put("minutes", minutes);
			results.put("exam_length", exam_length);
			results.put("exam_general_instructions", exam_general_instructions);
			results.put("exam_special_instructions", exam_special_instructions);		
			results.put("exam_directions", exam_directions);			
			results.put("edit", "");

			session = req.getSession();
			session.setAttribute( "exam", exam );	
			session.setAttribute( "faculty", faculty);	

		}catch(Exception e){
			error = "error calling control class";
		}

		//MERGE PORTS RESPONSE (CtrlClass) TO TEMPLATE (ui_class)
		try{
			resultTemplate.process(results, out);
			out.flush();
		}catch (TemplateException e) {
      			throw new ServletException( "Error while processing template", e);
    		}
    		out.close();
  	}//doPost
}//class
