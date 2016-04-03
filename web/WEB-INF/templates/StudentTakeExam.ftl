<html>
	<head>
		<title>Take Exam</title>
		<script type="text/javascript" src="js/studentTakeExam.js"></script>
		<script type="text/javascript" src="js/jquery.js"></script>
	</head>
	<body onload="getSections();">
		<h3>Take Exam</h3>
		<hr/>
		<div id="selectExam">
			<div>
				<form action="StudentTakeExam" method="post">
					<select id="sectionMenu" name="section" onchange="displayExams(this.options[this.selectedIndex].value)">
					</select><br/>
					<select id="examMenu" name="exam" onchange="startExam(this.options[this.selectedIndex].value)">
					</select>
				</form>
			</div>
			<div id="exams"></div>
		</div>
		<div id="examInfo" style="display:none">
			<h3 id="examName"></h3>
			<p id="courseInfo"></p>
			<h4>General Instructions</h4>
			<p id="genInstr"></p>
			<h4>Special Instructions</h4>
			<p id="speInstr"></p>
			<h4>Directions</h4>
			<p id="directions"></p>
			<input type="button" id="start" value="Start Exam"/>
		</div>
		<div id="question" style="display:none">
			
			<p id="qText"></p>
			
			<div style="list-style:none" id="choices">
			
			</div>
			<input type="button" id="nextQues" value="Next"/>
			<input type="hidden" id="answer"/>
			<input type="hidden" id="qId"/>
		</div>
	</body>
</html>