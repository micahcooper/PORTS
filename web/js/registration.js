var courses = {};
var sections = {};

var base = "Registration?";

function getDepartments(){
	$("#status")[0].innerHTML = "";
	$("#enroll")[0].value = "Enroll";
	$("#enroll")[0].onclick = function(){ alert("Select a Department!"); };
	var url=base+"dept";
	
	var callback = function(data) {
		var json = parseJSON(data);
		var b = json["results"]["bindings"];
		var html = "<option value='-1'>--</option>";
		for(var i=0; i<b.length; i++){
			var dept = b[i]["department"];
			html += "<option value='"+dept.id+"'>"+dept.name+"</option>\n";
		}
		$("#dept")[0].innerHTML = html;
	};
	
	$.post(url, callback);
}

function getCourses(deptId){
	$("#status")[0].innerHTML = "";
	$("#enroll")[0].value = "Enroll";
	$("#enroll")[0].onclick = function(){ alert("Select a Course!"); };
	if(deptId != -1){
		var url=base+"course="+deptId;
		
		var callback = function(data) {	
			var json = parseJSON(data);
			var b = json["results"]["bindings"];
			var html = "<option value='-1'>--</option>";
			for(var i=0; i<b.length; i++){
				var course = b[i]["course"];
				courses[course.id] = course.desc;
				html += "<option value='"+course.id+"'>"+course.name+"</option>\n";
			}
			$("#course")[0].innerHTML = html;
			$("#courseInfo")[0].innerHTML = "";
		};
		
		$.post(url, callback);
	}
}

function getSections(courseId){
	$("#status")[0].innerHTML = "";
	$("#enroll")[0].value = "Enroll";
	$("#enroll")[0].onclick = function(){ alert("Select a Section!"); };
	if(courseId != -1){
		$("#courseInfo")[0].innerHTML = courses[courseId];
		var url=base+"section="+courseId;
		
		var callback = function(data) {
			var json = parseJSON(data);
			var b = json["results"]["bindings"];
			var html = "<option value='-1'>--</option>";
			for(var i=0; i<b.length; i++){
				var section = b[i]["section"];
				sections[section.id] = section.faculty;
				html += "<option value='"+section.id+"'>"+section.days+"-"+section.period+"</option>\n";
			}
			$("#section")[0].innerHTML = html;
			$("#sectionInfo")[0].innerHTML = "";
		};
		
		$.post(url, callback);
	}
}

function getEnrolled(){
	
	var url=base+"enrolled";
	
	var callback = function(data) {
		var json = parseJSON(data);
		var b = json["results"]["bindings"];
		
		var html = "<table><tbody><tr><th>Course</th><th>Faculty</th><th>Days</th><th>Period</th></tr>";
		for(var i=0; i<b.length; i++){
			var section = b[i]["section"];
			html += "<tr><td>"+section.course+"</td><td>"+section.faculty+"</td><td>"+
			section.days+"</td><td>"+section.period+"</td>"+
			"<td><input type='button' value='Remove' onclick='unEnroll("+section.id+")'/></td></td></tr>\n";
		}
		html +="</table>";
		$("#classes")[0].innerHTML = html;
	};
	
	$.post(url, callback);
}

function isEnrolled(sectionId){
	if(sectionId != -1){
		$("#sectionInfo")[0].innerHTML = "Taught by: " + sections[sectionId];
		var url=base+"selected="+sectionId;
		var callback = function(data) {
			var json = parseJSON(data);
			var b = json["results"]["bindings"];
			if(b[0]["enrolled"] == "true"){
				$("#enroll")[0].value = "Unenroll";
				$("#enroll")[0].onclick = function(){
					var section = $("#section")[0];
					unEnroll(section.options[section.selectedIndex].value);
				};
			}else{
				$("#enroll")[0].value = "Enroll";
				$("#enroll")[0].onclick = function(){
					var section = $("#section")[0];
					enroll(section.options[section.selectedIndex].value);
				};
			}
		};
		$.post(url, callback);
	}
}

function enroll(sectionId){
	if(sectionId != -1){
		var url=base+"enroll="+sectionId;
		var callback = function(data) {
			var json = parseJSON(data);
			var b = json["results"]["bindings"];
			var success = b[0]["success"]=="true"?"Enrollment Successful": "Enrollment Unsuccessful";
			$("#status")[0].innerHTML = success;
			$("#enroll")[0].value = "Unenroll";
			$("#enroll")[0].onclick = function(){
				var section = $("#section")[0];
				unEnroll(section.options[section.selectedIndex].value);
			};
			getEnrolled();
		};
		$.post(url, callback);
	}
}

function unEnroll(sectionId){
	if(sectionId != -1){
		var url=base+"unenroll="+sectionId;
		var callback = function(data) {
			var json = parseJSON(data);
			var b = json["results"]["bindings"];
			var success = b[0]["success"]=="true"?"Unenrollment Successful": "Unenrollment Unsuccessful";
			$("#status")[0].innerHTML = success;
			$("#enroll")[0].value = "Enroll";
			$("#enroll")[0].onclick = function(){
				var section = $("#section")[0];
				enroll(section.options[section.selectedIndex].value);
			};
			getEnrolled();
		};
		$.post(url, callback);
	}
}

function parseJSON(json){
	return eval("("+json+")");
}