<html>
<head>
	<title>Modify A Course</title>
	<link rel="stylesheet" type="text/css" href="style/admin.css" />
</head>
<body>
  <h1>Modify A Course</h1>

	<#if displaySearch>
	<form method=post>
		<p>Enter Course ID: <input name="class_id" value=0 type=text size=20> 
    	<input type=submit value="Retrieve Classroom">
  	</form>
	</#if>

	<#if found>
		<form method=post action="ModifyClassroom">
			<p>Course ID: ${course_id!}
			<p>Course Name: <input name=coure_name type=text value="${course_name!}" size=20>
			<p>Course Description: <input name=course_desc type=text value="${course_desc!}" size=20>
			<p>Department: ${course_dept!}
			<p><br>Change the Department
			<p><select name=class_dept>
				<#list deptNames as deptName>
					<option value="${deptName}">${deptName}</option>
				</#list>
			</select>
			<input type=hidden name=changed value=true>
			<input type=hidden name=course_id value=${course_id} >
			<input type=Submit value="Modify Course">
		</form>
	</#if>

  	<br><br>
	${message!}

</body>
</html>
