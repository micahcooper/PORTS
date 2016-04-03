var base = "AdminEditRoll?";

function getDepartments(){
	$("#status")[0].innerHTML = "";
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
	if(deptId != -1){
		var url=base+"course="+deptId;
		
		var callback = function(data) {	
			var json = parseJSON(data);
			var b = json["results"]["bindings"];
			var html = "<option value='-1'>--</option>";
			for(var i=0; i<b.length; i++){
				var course = b[i]["course"];
				html += "<option value='"+course.id+"'>"+course.name+"</option>\n";
			}
			$("#course")[0].innerHTML = html;
		};
		
		$.post(url, callback);
	}
}

function getSections(courseId){
	$("#status")[0].innerHTML = "";
	if(courseId != -1){
		var url=base+"section="+courseId;
		
		var callback = function(data) {
			var json = parseJSON(data);
			var b = json["results"]["bindings"];
			var html = "<option value='-1'>--</option>";
			for(var i=0; i<b.length; i++){
				var section = b[i]["section"];
				html += "<option value='"+section.id+"'>"+section.days+"-"+section.period+"</option>\n";
			}
			$("#section")[0].innerHTML = html;
		};
		
		$.post(url, callback);
	}
}

function getEnrolledStudents(sectionId){
	var url=base+"selected="+sectionId;
	
	var callback = function(data) {
		var json = parseJSON(data);
		var b = json["results"]["bindings"];
		
		var html = "<table><tbody><tr><th>Id</th><th>Name</th><th>Type</th><th>Address</th></tr>";
		for(var i=0; i<b.length; i++){
			var student = b[i]["student"];
			html += "<tr><td>"+student.id+"</td><td>"+student.name+"</td><td>"+
						student.type+"</td><td>"+student.address+"</td>"+
						"<td><input type='button' value='Remove' onclick='unEnroll("+student.id+")'/></td></tr>\n";
		}
		html +="</table>";
		$("#enrolled")[0].innerHTML = html;
	};
	
	$.post(url, callback);
}

function searchStudents(stuName){
	var section = $("#section")[0];
	if(section.selectedIndex != -1 && section.options[section.selectedIndex].value != -1){
		var url=base+"search="+stuName+"&section="+section.options[section.selectedIndex].value;
		
		var callback = function(data) {
			var json = parseJSON(data);
			var b = json["results"]["bindings"];
			
			var html = "<table><tbody><tr><th>Id</th><th>Name</th><th>Type</th><th>Address</th></tr>";
			for(var i=0; i<b.length; i++){
				var student = b[i]["student"];
				html += "<tr><td>"+student.id+"</td><td>"+student.name+"</td><td>"+
				student.type+"</td><td>"+student.address+"</td>"+
				"<td><input type='button' value='Add' onclick='enroll("+student.id+")'/></td></tr>\n";
			}
			html +="</table>";
			$("#students")[0].innerHTML = html;
		};
		
		$.post(url, callback);
	}else{
		alert("Select a Section!");
	}
}

function enroll(studentId){
	var section = $("#section")[0];
	if(section.selectedIndex != -1 && section.options[section.selectedIndex].value != -1){
		
		var url=base+"enroll="+section.options[section.selectedIndex].value+"&student="+studentId;
		var callback = function(data) {
			var json = parseJSON(data);
			var b = json["results"]["bindings"];
			var success = b[0]["success"]=="true"?"Addition Successful": "Addition Unsuccessful";
			$("#status")[0].innerHTML = success;
			getEnrolledStudents(section.options[section.selectedIndex].value)
		};
		$.post(url, callback);
	}
}

function unEnroll(studentId){
	var section = $("#section")[0];
	if(section.selectedIndex != -1 && section.options[section.selectedIndex].value != -1){
		var url=base+"unenroll="+section.options[section.selectedIndex].value+"&student="+studentId;
		var callback = function(data) {
			var json = parseJSON(data);
			var b = json["results"]["bindings"];
			var success = b[0]["success"]=="true"?"Deletion Successful": "Deletion Unsuccessful";
			$("#status")[0].innerHTML = success;
			getEnrolledStudents(section.options[section.selectedIndex].value)
		};
		$.post(url, callback);
	}
}

function parseJSON(json){
	return eval("("+json+")");
}