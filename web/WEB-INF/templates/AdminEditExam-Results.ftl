<html>
	<head>
		<title>Edited Exam</title>
	</head>

	<body>
		<form name="editExam" method="post" action="AdminCreateExamQuestion">
			<h2>Success!!</h2><br>  
			<tr>
				<td>Thank you <b>${admin_name}</b>, your exam was successfully edited. Please see details below. <p></td>
			</tr>
			<tr><td><hr></td></tr>
			<tr><td>&nbsp;<p></td></tr>
		
			<tr><td><b>Exam Name: </b>${exam_name!} and ${exam_id}<p></td></tr>
			<tr><td><b>Exam Section: </b>${exam_section_name!}<p></td></tr>
			<tr><td><b>Exam Date: </b>${exam_date?date!}<p></td></tr>
			<tr><td><b>Exam Allotted Time: </b>${exam_length!} minutes (${hours!} hours, ${minutes!} minutes)<p></td></tr>
			<tr><td><b>Exam Directions</b><br>${exam_directions!}<p></td></tr>
			<tr><td><b>General Instructions<br></b>${exam_general_instructions!}</b><p></td></tr>
			<tr><td><b>Special Instructions</b></br>${exam_special_instructions!}<p></b></td></tr>
			<tr>
				<td>
			<input type="submit" value="Add Question" onclick=document.editExam.submit(); />
			<input type="submit" value="Main Menu" onclick=document.editExam.action=AdminMenu;document.createExamQuestion.submit(); /><p>
			<input type="hidden" name="edit" value="" />
				</td>
			</tr>
		</form>
	</body>
</html>
