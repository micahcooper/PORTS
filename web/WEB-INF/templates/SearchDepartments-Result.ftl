<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<title>Department Results</title>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<link rel="stylesheet" type="text/css" href="style/admin.css" />
	</head>

	<body>
		<h1>University Departments</h1>

  	<form method=post action="SearchDepartments">
		<p>Enter Department Name: <input name="dept_name" type=text size=20> 
    	<input type=submit value="Search!">
  	</form>
	
		<table width="70%"  border="0" cellpadding="1" cellspacing="2" >
			<tr>
				<td>${departmentId!}</td>
				<td>${departmentName!}</td>
			</tr>
	
			<#list departments! as department>
			<tr>
				<td>${department[0]}</td>
				<td>${department[1]}</td>
			</tr>
			</#list>
		</table>

	</body>
</html>
