<html>
<head>
	<title>Create A User</title>
	<link rel="stylesheet" type="text/css" href="style/admin.css" />
</head>
<body>
  <h1>Create A User</h1>
  <form method=post>
	<p>Enter the User Name: <input name="user_name" type=text size=20> 
	<p>Enter the User Address: <input name="user_address" type=text size=20>
	<p>Select the User's Title
	<p><select name=user_title>
		<option value="undergraduate">Undergraduate</option>
		<option value="graduate">Graduate</option>
		<option value="professor">Professor</option>
		<option value="systems administrator">Systems Administrator</option>
	</select>
	<br><br>
	<p>User Login Information
	<p>Enter the user login name: <input name="auth_name" type=text size=20>
	<p>Enter the user password: <input name="auth_pass" type=password size=20>
    <input type=submit value="Add User!">
  </form>
  <br><br>

	${message!}
</body>
</html>
