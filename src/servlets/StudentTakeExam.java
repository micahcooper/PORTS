import java.io.*;
import java.util.*;

import javax.servlet.*;
import javax.servlet.http.*;

import freemarker.template.*;

import except.*;
import model.ctrl.*;
import model.entity.*;

public class StudentTakeExam extends HttpServlet {
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
    String resultTemplateName = "StudentTakeExam.ftl",
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
    
    String section = req.getParameter( "section" );
    String exam = req.getParameter( "exam" );
    String questions = req.getParameter( "questions" );
    String question = req.getParameter( "question" );
    String answer = req.getParameter( "answer" );
    String grade = req.getParameter( "grade" );
    
    HttpSession session = req.getSession();
	User u = (User) session.getAttribute( "user" );
	Exam ex = (Exam) session.getAttribute( "exam" );
    
    StringBuffer content = new StringBuffer();
    
	content.append("{\n");	content.append("  \"results\": {\n");	content.append("    \"bindings\": [\n\n");
    
   if(section != null){
    	try{
	    	for(Iterator iter = CtrlRegistration.studentSections(u.getId()); iter.hasNext();){
	    		Section s = (Section) iter.next();
	    		Faculty f = s.getFaculty();
	    		Course c = s.getCourse();
	    		String facName = f.getName();
	    		content.append("      {\n");				content.append("        \"section\": { \"id\": \""+s.getId()+"\", \"course\":\""+
										s.getCourseId()+"\", \"courseName\":\""+c.getName()+"\", \"facId\":\""+f.getId()+"\", \"facName\": \""+facName+"\", \"days\": \""+
										s.getDays()+"\", \"period\": \""+s.getPeriod()+"\"  }\n");				content.append("      } ,\n");
	    	}
	    	content.setCharAt(content.length()-2, ' ');
	    }catch(PortsException e){}
    }else if(exam != null){
    	try{
	    	for(Iterator iter = CtrlStudentTakeExam.sectionExams(Integer.parseInt(exam)); iter.hasNext();){
	    		Exam e = (Exam) iter.next();
	    		Section s = e.getSection();
	    		Course c = s.getCourse();
	    		Faculty f = s.getFaculty();
	    		
	    		content.append("      {\n");				content.append("        \"exam\": { \"id\": \""+e.getId()+"\", \"section\":\""+
										s.getId()+"\", \"courseName\":\""+c.getName()+"\", \"facName\": \""+f.getName()+"\", \"days\": \""+
										s.getDays()+"\", \"period\": \""+s.getPeriod()+"\", \"name\":\""+e.getName()+"\", "+
										"\"genInstr\":\""+e.getGeneralInstructions()+"\", \"speInstr\":\""+e.getSpecialInstructions()+"\", "+
										"\"directions\":\""+e.getDirections()+"\", \"length\":\""+e.getLength()+"\", \"date\":\""+e.getDate()+"\" }\n");				content.append("      } ,\n");
	    	}
	    	content.setCharAt(content.length()-2, ' ');
	    }catch(PortsException e){}
    }else if(questions != null){
    	try{
	    	for(Iterator iter = CtrlStudentTakeExam.examQuestions(Integer.parseInt(questions)); iter.hasNext();){
	    		ExamQuestion q = (ExamQuestion) iter.next();
	    		content.append("      {\n");				content.append("        \"question\": { \"id\": \""+q.getId()+"\", \"text\":\""+
										q.getText()+"\", \"answer\":\""+q.getAnswer()+"\", \"choices\": [ \n");
				
				for(Iterator iter2 = q.getChoices(); iter2.hasNext();){
					QuestionChoice qc = (QuestionChoice) iter2.next();
					content.append(" { \"text\":\""+qc.getText()+"\", \"number\":\""+qc.getNumber()+"\"  },\n");
				}
				content.setCharAt(content.length()-2, ' ');
										
				content.append("] }\n");				content.append("      } ,\n");
	    	}
	    	content.setCharAt(content.length()-2, ' ');
	    	Exam e = CtrlStudentTakeExam.getExam(Integer.parseInt(questions));
	    	session.setAttribute( "exam", e );
	    }catch(PortsException e){}
    }else if(answer != null && question != null){
    	content.append("      {\n");
    	try{
    		Exam e = (Exam) session.getAttribute("exam");
    		int stu = u.getId();
    		int _ex = e.getId();
    		int ques = Integer.parseInt(question);
    		int ans =  Integer.parseInt(answer);
	    	CtrlStudentTakeExam.recordAnswer(stu, _ex, ques, ans);
	    	content.append("        \"success\": \"true\"\n");
	    }catch(PortsException e){content.append("        \"success\": \"false\", \"error\":\""+e.toString()+"\"\n");}
	    content.append("      } ,\n");
    }else if(grade != null){
    	content.append("      {\n");
    	try{
    		Exam e = (Exam) session.getAttribute("exam");
    		int stu = u.getId();
    		int _ex = e.getId();
	    	double g = CtrlStudentTakeExam.calcGrade(stu, _ex);
	    	content.append("        \"grade\": \""+g+"\"\n");
	    }catch(PortsException e){content.append("        \"grade\": \"0\", \"error\":\""+e.toString()+"\"\n");}
	    content.append("      } ,\n");
    }
    content.append("    ]\n");	content.append("  }\n");	content.append("}\n");
    out.println(content.toString());
  }

}