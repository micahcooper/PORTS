<html>
	<head>
		<title>View Enrollment</title>
		<script type="text/javascript" src="js/enrollment.js"></script>
		<script type="text/javascript" src="js/jquery.js"></script>
	</head>
	<body onload="getSections();">
		<h3>View Enrollment</h3>
		<br/>
		<div id="status"></div>
		<div>
			<form action="ViewEnrollment" method="post">
				<select id="section" name="section" onchange="viewEnrolled(this.options[this.selectedIndex].value);">
				</select>
				<div id="sectionInfo"></div>
			</form>
		</div>
		<div id="students"></div>
	</body>
</html>