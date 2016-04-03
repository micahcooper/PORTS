<html>
	<head>
		<title>Student Registration</title>
		<script type="text/javascript" src="js/registration.js"></script>
		<script type="text/javascript" src="js/jquery.js"></script>
	</head>
	<body onload="getDepartments();getEnrolled();">
		<h3>Student Registration</h3>
		<br/>
		<div id="status"></div>
		<div>
			<form action="Registration" method="post">
				<select id="dept" name="dept" onchange="getCourses(this.options[this.selectedIndex].value);">
				
				</select>
				<br/>
				<select id="course" name="course" onchange="getSections(this.options[this.selectedIndex].value);">
				
				</select>
				<div id="courseInfo"></div>
				<select id="section" name="section" onchange="isEnrolled(this.options[this.selectedIndex].value);">
				
				</select>
				<div id="sectionInfo"></div>
				<input type="button" value="Enroll" id="enroll"/>
			</form>
		</div>
		<div id="classes"></div>
	</body>
</html>