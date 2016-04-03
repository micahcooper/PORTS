<html>
<head>
	<title>Create A Department</title>
	<style type="text/css">
		td {text-align:center}
	</style>
	<script type="text/javascript" src="js/micah.js"></script>
</head>
<body>
  <h1>Create A Department</h1>
  <form name=createDepartment method=post onSubmit="return checkCreateDept()">
	<p>Enter Department Name: <input name="dept_name" type=text size=20> 
    <input type=submit value="Add Department">
  </form>
  <br><br>

	${message!}
</body>
</html>
