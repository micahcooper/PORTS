<html>
<head>
	<title>Modify A User</title>
	<link rel="stylesheet" type="text/css" href="style/admin.css" />
	<script type="text/javascript" src="js/micah.js"></script>
</head>
<body>
  <h1>Modify A User</h1>

	<#if displaySearch>
	<form method=post >
		<p>Enter User ID: <input name="user_id" value=1 type=text size=20> 
    	<input type=submit value="Retrieve User">
  	</form>
	</#if>

	<#if found>
		<form name=updateUser method=post onSubmit="return checkModifyUser()" >
			<p>User ID: ${user_id}
			<p>User Name: <input name="user_name" value="${user_name}" type=text size=20>
			<p>User Title: <input name=user_title value="${user_title}" type=text size=20>
			<p>User Address: <input name=user_address value="${user_address}" type=text size=20>
			<p>Login Name: ${auth_name}
			<p>Password: <input name=auth_pass value="${auth_pass}" type=password size=20>
			<p>Change Access Role
			<p><select name=auth_role>	
				<option value=${value1}>${role1}</option>
				<option value=${value2}>${role2}</option>
				<option value=${value3}>${role3}</option>
			</select>	
			<input type=hidden name=changed value=true>
			<input type=hidden name=user_id value=${user_id} >
			<input type=Submit value="Update User">
		</form>
	</#if>

  	<br><br>
	${message!}

</body>
</html>
