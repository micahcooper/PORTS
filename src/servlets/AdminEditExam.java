import java.io.*;
import java.util.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

import javax.servlet.*;
import javax.servlet.http.*;

import freemarker.template.*;

import except.*;
import model.ctrl.*;
import model.entity.*;

public class AdminEditExam extends HttpServlet {

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
		String resultTemplateName = "AdminEditExam.ftl";	
		String error = "";
		
		//<> FORM FIELDS
		int exam_id = -1;
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

		String admin_name = null;
		String edit = "";
		
		//GET USER FROM SESSION
		
		HttpSession session = req.getSession();
		Administrator admin = (Administrator) session.getAttribute("admin");		
		Exam exam = (Exam) session.getAttribute("exam");
		
		//<> RESPONSE IDENTIFIERS
		Map results = new HashMap();
	
		try{		
			try{
				edit = req.getParameter("edit");
			}catch(Exception e){
				edit = "";
			}
					
			if(!edit.equals("") && edit != null){
				//<> PROCESS FORM (WITH CHANGES)	
				try{					
					Section e_section = (Section)exam.getSection();
					int section_id = e_section.getId();				
					exam_id = exam.getId();					
					String days = e_section.getDays();
					String period = e_section.getPeriod();	
					String course_name = ((Course)e_section.getCourse()).getName();	
					exam_section_name = course_name+"-"+days+"-"+period;	
					
					exam_section = Integer.toString(section_id);					
					exam_name = req.getParameter("exam_name");					
					admin_name = admin.getName();
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

					exam_directions = req.getParameter("exam_directions");
					exam_general_instructions = req.getParameter("exam_general_instructions");
					exam_special_instructions = req.getParameter("exam_special_instructions");
					
				}catch(Exception e){
					error = "internal error";
				}

				//UPDATE THE MAP
				try{
					results.put("admin_name", admin_name);				
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
					results.put("edit", edit);
				}catch(Exception e){
					error = "error with map";
				}
				resultTemplateName = "AdminEditExam-Results.ftl";
				error = "no error";
			}else{
				//<> PROCESS FORM (ON PAGE RELOAD)									
				admin_name = admin.getName();
				exam_id = exam.getId();
				exam_name = (String) exam.getName();
				exam_id = (int) exam.getId();			
				
				Section e_section = (Section)exam.getSection();
				int section_id = e_section.getId();				
				String days = e_section.getDays();
				String period = e_section.getPeriod();	
				String course_name = ((Course)e_section.getCourse()).getName();								
				exam_section_name = course_name+"-"+days+"-"+period;	
						
				exam_length = exam.getLength();				
				hours = Integer.toString((exam_length/60));
				minutes = Integer.toString(exam_length - (Integer.parseInt(hours)*60));
			
				DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				exam_date = exam.getDate();				
				String date = df.format(exam_date);
				exam_year = date.toString().split("-")[0].substring(2,4);
				exam_month = date.toString().split("-")[1];
				exam_dayOfMonth = date.toString().split("-")[2];

				exam_directions = exam.getDirections();
				exam_general_instructions = exam.getGeneralInstructions();
				exam_special_instructions = exam.getSpecialInstructions();
				
				//UPADATE MAP
				try{
					results.put("admin_name", admin_name);				
					results.put("exam_id", exam_id);
					results.put("exam_name", exam_name);
					results.put("exam_year", exam_year);
					results.put("exam_month", exam_month);
					results.put("exam_dayOfMonth", exam_dayOfMonth);
					results.put("exam_date", exam_date);
					results.put("exam_section", Integer.toString(section_id));
					results.put("hours", hours);
					results.put("minutes", minutes);
					results.put("exam_length", exam_length);
					results.put("exam_general_instructions", exam_general_instructions);
					results.put("exam_special_instructions", exam_special_instructions);		
					results.put("exam_directions", exam_directions);
					results.put("exam_section_name", exam_section_name);
					results.put("edit", edit);
				}catch(Exception e){
					error = "error with map";
				}		
						
				//error = "There were errors submitting your information <p> "; 
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
			edit = "edit";			
			error = "Please edit the appropriate fields below, then choose save. To keep the current options, simply save the existing information";
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
			//edit exam instead of creating it
			exam = CtrlFacultyEditExam.editExam(exam_id, Integer.parseInt(exam_section), exam_name, exam_general_instructions, exam_special_instructions, exam_directions, exam_length, exam_date);
			session = req.getSession();
			session.setAttribute( "exam", exam );	
			session.setAttribute( "admin", admin );	

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
