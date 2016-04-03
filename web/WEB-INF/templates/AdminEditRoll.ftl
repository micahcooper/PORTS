<html>
	<head>
		<title>Edit Section Roll</title>
		<script type="text/javascript" src="js/adminEditRoll.js"></script>
		<script type="text/javascript" src="js/jquery.js"></script>
	</head>
	<body onload="getDepartments();">
		<h3>Edit Section Roll</h3>
		<br/>
		<div id="status"></div>
		<div>
			<form action="AdminEditRoll" method="post">
				<select id="dept" name="dept" onchange="getCourses(this.options[this.selectedIndex].value);">
				</select><br/>
				<select id="course" name="course" onchange="getSections(this.options[this.selectedIndex].value);">
				</select><br/>
				<select id="section" name="section" onchange="getEnrolledStudents(this.options[this.selectedIndex].value);">
				</select><br/>
			</form>
		</div>
		<table>
			<tbody>
				<tr><th>Currently Enrolled</th><th>Not Enrolled</th></tr>
				<tr>
					<td></td>
					<td>
						<input type="text" id="search"/>
						<input type="button" value="Get Students" onclick="searchStudents($('#search')[0].value);"/>
					</td>
				</tr>
				<tr>
					<td><div id="enrolled"></div></td><td><div id="students"></div></td>
				</tr>
			</tbody>
		</table>
		
	</body>
</html>