import java.io.*;
import java.util.*;

import javax.servlet.*;
import javax.servlet.http.*;

import freemarker.template.*;

import except.*;
import model.ctrl.*;
import model.entity.*;
import model.entity.impl.*;

public class AdminEditExamQuestion extends HttpServlet{
	
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
		
		//<> FIELDS
		Template resultTemplate = null;
		BufferedWriter out = null;
		String resultTemplateName = "AdminCreateExamQuestion.ftl";	
		String error = "";
	
		//<> FORM FIELDS
		int exam_id = -1;
		int question_id = -1;
		int section_id = -1;
		String question_number = null;
		String question_answer = null;
			
		String exam_name = null;
		String exam_section = null;
		String exam_section_name = null;
		String question_text = null;

		int choice_a_id = 0;
		int choice_b_id = 0;
		int choice_c_id = 0;
		int choice_d_id = 0;
		int choice_e_id = 0;
		
		String question_choice_a = null;
		String question_choice_b = null;
		String question_choice_c = null;
		String question_choice_d = null;
		String question_choice_e = null;
			
		int choice_num_a = 1;
		int choice_num_b = 2;
		int choice_num_c = 3;
		int choice_num_d = 4;
		int choice_num_e = 5;

		HashMap<String, String> answerMap = new HashMap<String, String>();

		//GET ALL SESSION OBJECTS
		HttpSession session = req.getSession();
		Exam exam = (Exam) session.getAttribute( "exam" );
		
		//<> HTTP RESPONSE IDENTIFIERS
		Map results = new HashMap();	
	
		//<> GET FORM FIELDS
		try{
			exam_name = (String) exam.getName();
			exam_id = (int) exam.getId();

			//construct section name from Exam, Section, Course
			Section section = (Section)exam.getSection();
			String days = section.getDays();
			String period = section.getPeriod();	
			String course_name = ((Course)section.getCourse()).getName();				
			exam_section = Integer.toString(section.getId());
			exam_section_name = course_name+"-"+days+"-"+period;

			question_number = req.getParameter("question_number");
			question_text = req.getParameter("question_text");
			question_answer = req.getParameter("question_answer");	
	
			question_choice_a = req.getParameter("question_choice_a");
			question_choice_b = req.getParameter("question_choice_b");
			question_choice_c = req.getParameter("question_choice_c");
			question_choice_d = req.getParameter("question_choice_d");
			question_choice_e = req.getParameter("question_choice_e");
					
			
			answerMap.put("a", "1");	
			answerMap.put("b", "2");	
			answerMap.put("c", "3");	
			answerMap.put("d", "4");	
			answerMap.put("e", "5");		

			try{	
				results.put("exam_id", exam_id);
				results.put("question_id", question_id);
				results.put("exam_name", exam_name);
				results.put("exam_section", exam_section);
				results.put("exam_section_name", exam_section_name);
				results.put("question_number", question_number);
				results.put("question_text", question_text);
				results.put("question_answer", Integer.parseInt(answerMap.get(question_answer)));
				results.put("question_choice_a", question_choice_a);
				results.put("question_choice_b", question_choice_b);
				results.put("question_choice_c", question_choice_c);
				results.put("question_choice_d", question_choice_d);
				results.put("question_choice_e", question_choice_e);

				results.put("choice_num_a", 1);
				results.put("choice_num_b", 2);
				results.put("choice_num_c", 3);
				results.put("choice_num_d", 4);
				results.put("choice_num_e", 5);

			}catch(Exception e){
				error += "problems with the map <br>";
			}	

			//<> CHECK INPUT FORM FIELDS
			if(
				!question_text.equals("") && !question_choice_a.equals("") &&
				!question_choice_b.equals("") && !question_answer.equals("")
			){
				resultTemplateName = "AdminCreateExamQuestion-Results.ftl";
			}else{
				error = "There were errors submitting your information <p> "; 

				if(question_choice_a.equals(""))
					error += "Choice A not specified <br>";
				if(question_choice_b.equals(""))
					error = "Choice B not specified <br>";
				if(Integer.parseInt(question_answer) != -1)
					error += "Question Answer not specified <br>";

			}//if-else block

			results.put("error", error);

		}catch(Exception e){
			error = " error with input fields <br>";
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
			QuestionChoice choice_a = new QuestionChoiceImpl(choice_a_id, question_choice_a, Integer.parseInt(question_number));
			QuestionChoice choice_b = new QuestionChoiceImpl(choice_b_id, question_choice_b, Integer.parseInt(question_number));
			QuestionChoice choice_c = new QuestionChoiceImpl(choice_c_id, question_choice_c, Integer.parseInt(question_number));
			QuestionChoice choice_d = new QuestionChoiceImpl(choice_d_id, question_choice_d, Integer.parseInt(question_number));
			QuestionChoice choice_e = new QuestionChoiceImpl(choice_e_id, question_choice_e, Integer.parseInt(question_number));

			ExamQuestion examQuestion = CtrlFacultyCreateExamQuestion.createExamQuestion(exam_id, Integer.parseInt(question_number), question_text, Integer.parseInt(answerMap.get(question_answer)), choice_a, choice_b, choice_c, choice_d, choice_e, choice_num_a, choice_num_b, choice_num_c, choice_num_d, choice_num_e);
										
		}catch(Exception e){
			error = "error calling control class" + e;
		}

		//<> MERGE PORTS RESPONSE (CtrlClass) TO TEMPLATE (ui_class)
		try{
			resultTemplate.process(results, out);
			out.flush();
		}catch (TemplateException e) {
      			throw new ServletException( "Error while processing template", e);
    		}
    		out.close();
			
	}//doPost
}//class
