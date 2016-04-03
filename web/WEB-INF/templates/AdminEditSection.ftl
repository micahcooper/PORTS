<html>
	<head>
		<title>Section Creation/Modification/Deletion</title>
		<script type="text/javascript" src="js/adminEditSection.js"></script>
		<script type="text/javascript" src="js/jquery.js"></script>
	</head>
	<body onload="getDepartments();">
		<div>
			<h3>Create/Modify/Delete Section</h3>
			<div id="status"></div>
			<form action="AdminEditSection" method="post">
				<select id="deptMenu" name="dept" onchange="getCourses(this.options[this.selectedIndex].value);">
				</select><br/>
				<select id="courseMenu" name="course" onchange="getSections(this.options[this.selectedIndex].value);">
				</select><br/>
				<select id="sectionMenu" name="section" onchange="displaySectionInfo(this.options[this.selectedIndex].value)">
				</select>
			</form>
			<input type="button" value="Delete Section" onclick="deleteSection();"/>
		</div>
		<table>
			<tbody>
				<tr><th>Id</th><th>Course</th><th>Faculty</th><th>Days</th><th>Period</th></tr>
				<tr>
					<td>
						<input type="hidden" id="section"/>
						<div id="sectionInfo"></div>
					</td>
					<td>
						
						<div id="courseInfo"></div>
						<input type="hidden" id="course"/>
					</td>
					<td>
						<input type="hidden" id="faculty"/>
						<div id="facultyInfo"></div>
					</td>
					<td>
						<input type="text" id="days" length="5"/>
					</td>
					<td>
						<input type="text" id="period"/>
					</td>
				</tr>
			</tbody>
		</table>
		<div>
			<input type="button" id="action" value="Create New Section" onclick="createSection();"/>
			<input type="button" value="Clear Form" onclick="clearForm();"/>
		</div>
		<br/>
		<div>
			<input type="text" id="search"/>
			<input type="button" value="Get Faculty" onclick="searchFaculty($('#search')[0].value);"/>
			<div id="facultyResults"></div>
		</div>
	</body>
</html>