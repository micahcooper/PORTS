
var base = "ViewEnrollment?";

function getSections(){
	$("#status")[0].innerHTML = "";
	var url=base+"sections";
	
	var callback = function(data) {
		var json = parseJSON(data);
		var b = json["results"]["bindings"];
		var html = "<option value='-1'>--</option>";
		for(var i=0; i<b.length; i++){
			var section = b[i]["section"];
			html += "<option value='"+section.id+"'>"+section.course+"-"+section.days+"-"+section.period+"</option>\n";
		}
		$("#section")[0].innerHTML = html;
	};
	
	$.post(url, callback);
}

function viewEnrolled(sectionId){
	var url=base+"selected="+sectionId;
	
	var callback = function(data) {
		var json = parseJSON(data);
		var b = json["results"]["bindings"];
		
		var html = "<table><tbody><tr><th>Id</th><th>Name</th><th>Type</th><th>Address</th></tr>";
		for(var i=0; i<b.length; i++){
			var student = b[i]["student"];
			html += "<tr><td>"+student.id+"</td><td>"+student.name+"</td><td>"+
						student.type+"</td><td>"+student.address+"</td></tr>\n";
		}
		html +="</table>";
		$("#students")[0].innerHTML = html;
	};
	
	$.post(url, callback);
}

function parseJSON(json){
	return eval("("+json+")");
}