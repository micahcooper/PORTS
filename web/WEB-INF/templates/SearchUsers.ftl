<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<title>User Results</title>
		<link rel="stylesheet" type="text/css" href="style/admin.css" />

	</head>

	<body>
		<h1>University Users</h1>

  	<form method=post>
		<p>Enter User Full Name: <input name="user_name" type=text size=20> 
		<p>Enter User Title/Type: <input name="user_title" type=text size=20>
		<p><select name="user_type">
			<option value=1>Student</option>
			<option value=2>Faculty</option>
			<option value=3>Administrator</option>
		</select>
    	<input type=submit value="Search!">
  	</form>
	
		<table width="70%"  border="0" cellpadding="1" cellspacing="2" >
			<tr>
				<th>${userId!}</th>
				<th>${userName!}</th>
				<th>${userTitle!}</th>
				<th>${userAddress!}</th>
			</tr>
	
			<#list users! as user>
			<tr>
				<td>${user[0]!}</td>
				<td>${user[1]!}</td>
				<td>${user[2]!}</td>
				<td>${user[3]!}</td>
			</tr>
			</#list>
		</table>

		${message!}

	</body>
</html>
