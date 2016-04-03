<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<title>Classroom Results</title>
	<style type="text/css">td {background-color:#CCCCCC}</style>

	</head>

	<body>
		<h1>University Classrooms</h1>

  	<form method=post>
		<p>Enter Course Name: <input name="course_name" type=text size=20> 
		<p>Enter Course Description: <input name="course_desc" type=text size=20>
		<input type=hidden name=flag value=false>
		<input type=submit value="Search!">
	</form>	
	<br>
	<form method=post>
		<p>Enter Department Name:
		<select name="dept_name" >
			<#list deptNames as deptName>
			<option value="${deptName}">${deptName}</option>
			</#list>
		</select>
		<input type=hidden name=flag value=true>
    	<input type=submit value="Search!">
  	</form>
	
		<table width="70%"  border="0" cellpadding="1" cellspacing="2" >
			<tr>
				<td>${courseId!}</td>
				<td>${courseName!}</td>
				<td>${courseDept!}</td>
				<td>${courseDesc!}</td>
			</tr>
	
			<#list courses! as course>
			<tr>
				<td>${course[0]!}</td>
				<td>${course[1]!}</td>
				<td>${course[2]!}</td>
				<td>${course[3]!}</td>
			</tr>
			</#list>
		</table>

		${message!}

	</body>
</html>
