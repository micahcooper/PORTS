<html>
<head>
	<title>Modify A Department</title>
	<script type="text/javascript" src="js/micah.js"></script>
</head>
<body>
  <h1>Modify A Department</h1>

	<#if displaySearch>
	<form method=post action="ModifyDepartment">
		<p>Enter Department ID: <input name="dept_id" type=text size=20> 
    	<input type=submit value="Retrieve Department">
  	</form>
	</#if>

	<#if found>
		<form name=updateDept method=post action="ModifyDepartment" onSubmit="return checkModifyDept()">
			<p>Deparment ID: ${dept_id}
			<p>Department Name: <input type=text name=dept_name value="${dept_name}" size=20>
			<input type=hidden name=changed value=true>
			<input type=hidden name=dept_id value=${dept_id}>
			<input type=Submit value="Modify Name">
		</form>
	</#if>

  	<br><br>
	${message!}

</body>
</html>
