<html>
	<head>
		<title>New Exam Summary</title>
	</head>

	<body>
		<form name="createExamQuestion" method="post">
			<h2>Success!!</h2><br>  
			<tr>
				<td>Thank you <b>${faculty_name!}</b>, your exam was successfully created. Please see details below.<p></td>
			</tr>
			<tr><td><hr></td></tr>
			<tr><td>&nbsp;<p></td></tr>
		
			<tr><td><b>Exam Name: </b>${exam_name!}<p></td></tr>
			<tr><td><b>Exam Section: </b>${exam_section_name!} and ${exam_section!}<p></td></tr>
			<tr><td><b>Exam Date: </b>${exam_date?date!}<p></td></tr>
			<tr><td><b>Exam Allotted Time: </b>${exam_length!} minutes (${hours!} hours, ${minutes!} minutes)<p></td></tr>
			<tr><td><b>Exam Directions</b><br>${exam_directions!}<p></td></tr>
			<tr><td><b>General Instructions<br></b>${exam_general_instructions!}</b><p></td></tr>
			<tr><td><b>Special Instructions</b></br>${exam_special_instructions!}<p></b></td></tr>
			<tr>
				<td>
	<input type="submit" value="Add Question" onclick=document.createExamQuestion.action="FacultyCreateExamQuestion";document.createExamQuestion.submit(); />
	<input type="submit" value="Edit Exam" onclick=document.createExamQuestion.action="FacultyEditExam";document.createExamQuestion.submit(); />
	<input type="submit" value="Main Menu" onclick=document.createExamQuestion.action="FacultyMenu";document.createExamQuestion.submit(); /><p>
				</td>
			</tr>
			<INPUT TYPE=HIDDEN NAME="edit" VALUE="">
		</form>
	</body>
</html>
