<html>
<head>
	<title>Create A Course</title>
	<link rel="stylesheet" type="text/css" href="style/admin.css" />
	<script type="text/javascript" src="js/micah.js"></script>
</head>
<body>
  <h1>Create A Classroom</h1>
  	<form name=createCourse method=post onSubmit="return checkCreateCourse()">
		<p>Enter the Course Name: <input name="course_name" type=text size=20> 
		<p>Enter the Description: <input name="course_desc" type=text size=20>
		<p>Select the Course's Department: 
		<p><select name=course_dept>
		<#list deptNames as deptName>
			<option value=${deptName}>${deptName}</option>
		</#list>
		</select>
    	<input type=submit value="Add Course">
  	</form>

  <br><br>

	${message!}

</body>
</html>
