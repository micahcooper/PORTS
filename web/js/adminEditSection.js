var sections = {};
var courses = {};

var base = "AdminEditSection?";

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
		$("#deptMenu")[0].innerHTML = html;
	};
	
	$.post(url, callback);
}

function getCourses(deptId){
	$("#status")[0].innerHTML = "";
	$("#section")[0].innerHTML = "";
	if(deptId != -1){
		var url=base+"course="+deptId;
		
		var callback = function(data) {
			courses = {};
			var json = parseJSON(data);
			var b = json["results"]["bindings"];
			var html = "<option value='-1'>--</option>";
			for(var i=0; i<b.length; i++){
				var course = b[i]["course"];
				html += "<option value='"+course.id+"'>"+course.name+"</option>\n";
				courses[course.id] = {"dept":course.dept, "name":course.name, "desc":course.desc};
			}
			$("#courseMenu")[0].innerHTML = html;
		};
		
		$.post(url, callback);
	}
}

function getSections(courseId){
	if(courseId != -1){
		var url=base+"section="+courseId;
		
		var callback = function(data) {
			sections = {};
			var json = parseJSON(data);
			var b = json["results"]["bindings"];
			var html = "<option value='-1'>--</option>";
			for(var i=0; i<b.length; i++){
				var section = b[i]["section"];
				html += "<option value='"+section.id+"'>"+section.facName+": "+section.days+"-"+section.period+"</option>\n";
				sections[section.id] = {"course": section.course, "courseName": section.courseName,"facId": section.facId, "facName": section.facName, "days": section.days, "period": section.period};
			}
			$("#sectionMenu")[0].innerHTML = html;
			$("#courseInfo")[0].innerHTML = section.courseName;
		};
		
		$.post(url, callback);
	}
}

function searchFaculty(facName){
	var url=base+"search="+facName;
	
	var callback = function(data) {
		var json = parseJSON(data);
		var b = json["results"]["bindings"];
		
		var html = "<table><tbody><tr><th>Id</th><th>Name</th><th>Title</th><th>Address</th></tr>";
		for(var i=0; i<b.length; i++){
			var faculty = b[i]["faculty"];
			html += "<tr><td>"+faculty.id+"</td><td>"+faculty.name+"</td><td>"+
			faculty.type+"</td><td>"+faculty.address+"</td>"+
			"<td><input type='button' value='Assign' onclick=\"assignFaculty("+faculty.id+", '"+faculty.name+"')\"/></td></tr>\n";
		}
		html +="</table>";
		$("#facultyResults")[0].innerHTML = html;
	};
	$.post(url, callback);
}

function assignFaculty(facId, facName){
	$("#faculty")[0].value = facId;
	$("#facultyInfo")[0].innerHTML = facName;
}

function deleteSection(){
	var section = $("#section")[0];
	var course = $("#course")[0];
	if(section.value != ""){
		var url=base+"delete="+section.value;
		var callback = function(data) {
			var json = parseJSON(data);
			var b = json["results"]["bindings"];
			var success = b[0]["success"]=="true"?"Deletion Successful": "Deletion Unsuccessful";
			$("#status")[0].innerHTML = success;
			var courseMenu = $("#courseMenu")[0];
			getSections(courseMenu.options[courseMenu.selectedIndex].value);
			clearForm();
		};
		$.post(url, callback);
	}
}

function displaySectionInfo(sectionId){
	if(sectionId != -1){
		var section = sections[sectionId];
		
		$("#section")[0].value = sectionId;
		$("#course")[0].value = section.course;
		$("#faculty")[0].value = section.facId;
		$("#days")[0].value = section.days;
		$("#period")[0].value = section.period;
		$("#courseInfo")[0].innerHTML = section.courseName;
		$("#facultyInfo")[0].innerHTML = section.facName;
		$("#sectionInfo")[0].innerHTML = sectionId;
		
		$("#action")[0].value = "Modify Section";
		$("#action")[0].onclick = modifySection;
	}
}

function modifySection(){
	var section = $("#section")[0];
	var course = $("#course")[0];
	var faculty = $("#faculty")[0];
	var days = $("#days")[0];
	var period = $("#period")[0];
	if(section.value != "" && course.value != "" && faculty.value != "" && days.value != "" && period.value != ""){
		var url=base+"modify="+section.value+"&course="+course.value +
						"&faculty="+faculty.value+"&days="+days.value+"&period="+period.value;
		var callback = function(data) {
			var json = parseJSON(data);
			var b = json["results"]["bindings"];
			var success = b[0]["success"]=="true";
			var message = success?"Modification Successful": "Modification Unsuccessful";
			$("#status")[0].innerHTML = message;
			if(success) clearForm();
			var courseMenu = $("#courseMenu")[0];
			getSections(courseMenu.options[courseMenu.selectedIndex].value);
		};
		$.post(url, callback);
	}else{
		alert("Specify all fields to modify section");
	}
}

function createSection(){
	var course = $("#course")[0];
	var faculty = $("#faculty")[0];
	var days = $("#days")[0];
	var period = $("#period")[0];
	if(course.value != "" && faculty.value != "" && days.value != "" && period.value != ""){
		var url=base+"create&course="+course.value+
						"&faculty="+faculty.value+"&days="+days.value+"&period="+period.value;
		var callback = function(data) {
			var json = parseJSON(data);
			var b = json["results"]["bindings"];
			
			var success = b[0]["success"]=="true"
			var message = success?"Creation Successful": "Creation Unsuccessful";
			$("#status")[0].innerHTML = message;
			if(success) clearForm();
			var courseMenu = $("#courseMenu")[0];
			getSections(courseMenu.options[courseMenu.selectedIndex].value);
		};
		$.post(url, callback);
	}else{
		alert("Specify all fields to create section");
	}
}

function clearForm(){
	$("#section")[0].value = "";
	$("#faculty")[0].value = "";
	$("#days")[0].value = "";
	$("#period")[0].value = "";
	//$("#courseInfo")[0].innerHTML = "";
	$("#facultyInfo")[0].innerHTML = "";
	$("#sectionInfo")[0].innerHTML = "";
	
	//var courseMenu = $("#courseMenu")[0];
	//$("#course")[0].value = courseMenu.options[courseMenu.selectedIndex];
	
	$("#action")[0].value = "Create New Section";
	$("#action")[0].onclick = createSection;
}

function parseJSON(json){
	return eval("("+json+")");
}