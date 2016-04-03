<html>
	<head>
		<title>Faculty Create Exam Question</title>
		<script type="text/javascript" src="js/createExam.js"></script>
		<script type="text/javascript" src="js/jquery.js"></script>
	</head>

	<body onload="getSections();">
		<div class="container">
			<div class="header"></div>
			<div class="menu"></div>
			<div class="content">
				<form name="createExamQuestion" method="post" action="FacultyCreateExamQuestion">
					<table align="left" border=0 width=100%>						
						<tr><td colspan=2><h1>Create New Exam Question</h1></td></tr>
						<tr><td colspan=2><font color="#ff0000"><font color="#ff0000"><small>${error!}</font></small></font><br></td></tr>
						<tr><td colspan=2><hr></td></tr>
					
						<tr>	
							<td width=150>Exam Section: </td>
							<td>${exam_section_name!}</td>
						</tr>
						
						<tr>	
							<td width=150>Exam Name: </td>
							<td>${exam_name!}</td>
						</tr>

						<tr>
							<td width=150>Question Number:<small>(required)</small> </td>
							<td><input type="text" name="question_number" size=3 maxlength=3 value="${question_number!}"/></td>
						</tr>
						
						<tr><td>&nbsp;</td></tr>
						
						<tr>
                                                        <td colspan=2>
                                                                <b>Enter Exam Question</b><small>(required)</small><br>
                                                                <textarea cols=80 rows=15 name="question_text" value="${question_text!}">${question_text!}</textarea><p>
                                                        </td>
                                        	</tr>
	
						<tr><td>&nbsp;</td></tr>
				
						<tr>
							<td colspan=2>
								<b>Enter Question Choice:</b> <small>(Enter at least two (2) Question Choices)</small><br> 
							</td>
						</tr>

						<tr>
							<td align="center"><small>(required)</small> a) </td>
							<td><textarea name="question_choice_a" rows=2 cols=60 value="${question_choice_a!}">${question_choice_a!}</textarea><br></td>
						</tr>
						<tr>
							<td align="center"><small>(required)</small> b) </td>
							<td><textarea name="question_choice_b" rows=2 cols=60 value="${question_choice_b!}">${question_choice_b!}</textarea><br></td>
						</tr>
						<tr>
							<td align="center">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;c) </td>
							<td><textarea name="question_choice_c" rows=2 cols=60 value="${question_choice_c!}">${question_choice_c!}</textarea><br>
							</td>
						</tr>

						<tr>
							<td align="center">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;d) </td>
							<td><textarea name="question_choice_d" rows=2 cols=60 value="${question_choice_d!}">${question_choice_d!}</textarea><br>
							</td>
						</tr>
						<tr>
							<td align="center">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; e) </td>
							<td><textarea name="question_choice_e" rows=2 cols=60 value="${question_choice_e!}">${question_choice_e!}</textarea><br>
							</td>
						</tr>
						
						<tr><td>&nbsp;</td></tr>
						
	<tr><td colspan=2>Question Answer Option: <input type="text" name="question_answer" size=1 maxlength=1 value="${question_answer!}"/> <small> (e.g. a, b)</td></tr>
						<tr><td>&nbsp;</td></tr>
						<tr><td colspan=2>
							<input type="submit" value="Add"> 
							<input type="reset" value="Clear">
<input type="submit" value="Main Menu" onclick=document.createExamQuestion.action="FacultyMenu";document.createExamQuestion.submit(); />
						</td></tr>
						<tr><td>&nbsp;</td></tr>
					</table>				
				</form>
			</div>
		</div>
	</body>
</html>
