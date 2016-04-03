<html>
<head>
	<title>Modify A Classroom</title>
	<link rel="stylesheet" type="text/css" href="style/admin.css" />
</head>
<body>
  <h1>Modify A Classroom</h1>

	<#if displaySearch>
	<form method=post action="ModifyClassroom">
		<p>Enter Classroom ID: <input name="class_id" value=0 type=text size=20> 
    	<input type=submit value="Retrieve Classroom">
  	</form>
	</#if>

	<#if found>
		<form method=post action="ModifyClassroom">
			<p>Classroom ID: ${class_id}
			<p>Classroom Building: ${class_building}
			<p>Classroom Room No: ${class_room}
			<p>Used by Department: ${class_dept}
			<p><br>Modify the Department
			<p><select name=class_dept>
				<#list deptNames as deptName>
					<option value="${deptName}">${deptName}</option>
				</#list>
			</select>
			<input type=hidden name=changed value=true>
			<input type=hidden name=class_id value=${class_id} >
			<input type=Submit value="Modify Classroom">
		</form>
	</#if>

  	<br><br>
	${message!}

</body>
</html>
