<html>
<head>
	<title>Department Results</title>
	<style type="text/css">
		td {text-align:center}
	</style>
</head>
<body>
  <h1>Remove A Department</h1>
  <form method=post action="DeleteDepartment">
    <p>Enter Department ID: <input name="dept_id" value=0 type=text size=20>
    <input type=submit>
  </form>
  <br><br>

	${message!}
</body>
</html>
