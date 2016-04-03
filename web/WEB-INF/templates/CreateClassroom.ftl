<html>
<head>
	<title>Create A Classroom</title>
	<link rel="stylesheet" type="text/css" href="style/admin.css" />
	<script type="text/javascript" src="js/micah.js"></script>
</head>
<body>
  <h1>Create A Classroom</h1>
  <form method=post>
	<p>Enter the Classroom building: <input name="class_building" type=text size=20> 
	<p>Enter the Class room: <input name=class_room type=text size=20>
	<p>Select the Classroom's Department: 
	<p><select name=class_dept>
		<#list deptNames as deptName>
			<option value=${deptName}>${deptName}</option>
		</#list>
	</select>
    <input type=submit value="Add Classroom">
  </form>
  <br><br>

	${message!}
</body>
</html>
