<html>
<head>
	<title>Delete a Classroom</title>
	<style type="text/css">
		td {text-align:center}
	</style>
</head>
<body>
  <h1>Remove a Classroom</h1>
  <form method=post action="DeleteClassroom">
    <p>Enter Classroom ID: <input name="class_id" value=0 type=text size=20>
    <input type=submit>
  </form>
  <br><br>

	${message!}
</body>
</html>
