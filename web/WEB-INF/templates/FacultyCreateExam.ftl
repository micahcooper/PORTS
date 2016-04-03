<html>
	<head>
		<title>Faculty Create Exam</title>
		<script type="text/javascript" src="js/createExam.js"></script>
		<script type="text/javascript" src="js/jquery.js"></script>
	</head>
	
	<body onload="getSections();">
	
		<div class="container">
			<div class="header"></div>
			<div class="menu"></div>
			<div class="content">
				<table align="left" border=0 width=100%>
						
					<form name="createExam" method="post" action="FacultyCreateExam">
						<tr align="center"><td colspan=2><h1>Create New Exam</h1></td></tr>
						<tr><td colspan=2><font color="#ff0000"><small>${error!}</font></small><br></td></tr>
						<tr><td colspan=2><hr></td></tr>
						<tr><td colspan=2>&nbsp;<p></td></tr>
					
						<tr>
							<td width=200>Section:<small>&nbsp;(required)</small></td>
							<td>
								<select name="section_list" id="section_list">
									<option name="exam_section" value="${exam_section!}"></option>
								</select><br>
							</td>
						</tr>
			
						<tr>
							<td width=200>Exam Name:<small>&nbsp;(required)</small></td>
							<td><input type="text" name="exam_name" value="${exam_name!}"/><br></td>
						</tr>
						
						<tr>	

							<td width=200>
								Exam Date:<small>&nbsp;(required)</small>
							<td> 
								<input type="text" size=2 maxlength=2 name="exam_month" value="${exam_month!}" />
								<input type="text" size=2 maxlength=2 name="exam_dayOfMonth" value="${exam_dayOfMonth!}" />
								<input type="text" size=2 maxlength=2 name="exam_year" value="${exam_year!}" /> 
								<small>&nbsp;(mm/dd/yy)</small>
							</td>
						</tr>			
						
						<tr>
							<td width=200>Exam Length:</td>
							<td>
								<input type="text" size=1 maxlength=1 name="hours" value="${hours!}"/><small>hours</small>
								<input type="text" size=2 maxlength=2 name="minutes" value="${minutes!}"/><small>minutes</small><br>

							</td>	
						</tr>
						
						<tr><td colspan=2><small> (e.g. 90mins =1h 30mins)</small></td></tr>
						<tr><td>&nbsp;<p></td></tr>
							
						<tr>
							<td colspan=2>
								<b>General Instructions</b><small>&nbsp;(required)</small><br>
								<textarea cols=80 rows=15 name="exam_general_instructions" value="${exam_general_instructions!}">${exam_general_instructions!}</textarea><p>
							</td>
						</tr>
						
						<tr><td>&nbsp;<p></td></tr>
						
						<tr>
							<td colspan=2>
								<b>Special Instructions</b><small> (e.g. equations, formulae etc)</small><br>
								<textarea cols=80 rows=15 name="exam_special_instructions" value="${exam_special_instructions!}">${exam_special_instructions!}</textarea><p>
							</td>
						</tr>
						
						<tr><td>&nbsp;<p></td></tr>
						
						<tr>
							<td colspan=2>
								<b>Exam Directions</b><small>&nbsp;(required)</small><br>
								<textarea cols=80 rows=15 name="exam_directions" value="${exam_directions!}">${exam_directions!}</textarea><p>
							</td>
						</tr>
						
						<tr><td>&nbsp;<p></td></tr>
	 
						<tr>
							<td colspan=2><input type="submit" value="Next"> <input type="reset" value="Clear"></td>
						</tr>
						<tr><td colspan=2>&nbsp;<p></td></tr>
					</form>
				</table>
			</div>
		</div>
	</body>
</html>
