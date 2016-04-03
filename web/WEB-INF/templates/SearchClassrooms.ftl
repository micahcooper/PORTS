<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<title>Classroom Results</title>
		<link rel="stylesheet" type="text/css" href="style/admin.css" />

	</head>

	<body>
		<h1>University Classrooms</h1>

  	<form method=post action="SearchClassrooms">
		<p>Enter Classroom Building: <input name="class_building" type=text size=20> 
		<p>Enter Class Room Number: <input name="class_room" type=text size=20>
		<p>Enter Department Name: <input name="dept_name" type=text size=20>
    	<input type=submit value="Search!">
  	</form>
	
		<table width="70%"  border="0" cellpadding="1" cellspacing="2" >
			<tr>
				<td>${classroomId!}</td>
				<td>${building!}</td>
				<td>${room!}</td>
				<td>${department!}</td>
			</tr>
	
			<#list classrooms! as classroom>
			<tr>
				<td>${classroom[0]}</td>
				<td>${classroom[1]}</td>
				<td>${classroom[2]}</td>
				<td>${classroom[3]}</td>
			</tr>
			</#list>
		</table>

		${message!}

	</body>
</html>
