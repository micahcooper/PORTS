<html>
	
	<body>
		<form name="createExamQuestion" method="post" action="FacultyCreateExamQuestion">
			<tr><tr><h2>Your Question has been successfully created</h2><p></td></tr>  	
		<tr><td><font color="#ff0000"><font color="#ff0000"><small>${error!}</font></small></font><br></td></tr>			
			<tr><td>Question ${question_number!}.<p>${question_text!}<p></td></tr>
			<tr><td>a) ${question_choice_a!}<br></td></tr>
			<tr><td>b) ${question_choice_b!}<br></td></tr>
			<tr><td>c) ${question_choice_c!}<br></td></tr>
			<tr><td>d) ${question_choice_d!}<br></td></tr>
			<tr><td>e) ${question_choice_e!}<br></td></tr>
			<tr><td>&nbsp;<p></td></tr>
			<tr><td><input type="submit" value="Add Another Exam Question" onclick="document.createExamQuestion.submit();" /></td></tr>
			<!--tr><td><input type="submit" value="View Entire Exam" /></td></tr-->
			<tr><td><input type="submit" value="Faculty Main Menu" onclick=document.createExamQuestion.action="FacultyMenu";document.createExamQuestion.submit(); /><p></td></tr>
		</form>
	</body>

</html>
