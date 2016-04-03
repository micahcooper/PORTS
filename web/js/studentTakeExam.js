var exams = {};
var questions = {};

var base = "TakeExam?";

function getSections(){
	var url=base+"section";
	
	var callback = function(data) {
		var json = parseJSON(data);
		var b = json["results"]["bindings"];
		var html = "<option value='-1'>--</option>";
		for(var i=0; i<b.length; i++){
			var section = b[i]["section"];
			html += "<option value='"+section.id+"'>"+section.facName+": "+section.days+"-"+section.period+"</option>\n";
		}
		$("#sectionMenu")[0].innerHTML = html;
	};
	$.post(url, callback);
}

function displayExams(sectionId){
	if(sectionId != -1){
		var url=base+"exam="+sectionId;
		
		var callback = function(data) {
			exams = {};
			var json = parseJSON(data);
			var b = json["results"]["bindings"];
			var html = "<option value='-1'>--</option>";
			for(var i=0; i<b.length; i++){
				var exam = b[i]["exam"];
				html += "<option value='"+exam.id+"'>"+exam.name+"</option>\n";
				exams[exam.id] = exam;
			}
			$("#examMenu")[0].innerHTML = html;
		};
		$.post(url, callback);
	}
}

function startExam(examId){
	if(examId != -1){
		var exam = exams[examId];
		$("#selectExam").hide();
		$("#examInfo").show();
		$("#examName")[0].innerHTML = exam.name;
		$("#courseInfo")[0].innerHTML = exam.courseName + ": " + exam.facName + " - " + exam.days + "-" + exam.period;
		$("#genInstr")[0].innerHTML = exam.genInstr;
		$("#speInstr")[0].innerHTML = exam.speInstr;
		$("#directions")[0].innerHTML = exam.directions;
		$("#start")[0].onclick = function(){
			getQuestions(examId);
		};
	}
}

function getQuestions(examId){
	if(examId != -1){
		var url=base+"questions="+examId;
		
		var callback = function(data) {
			questions = {};
			var json = parseJSON(data);
			var b = json["results"]["bindings"];
			for(var i=0; i<b.length; i++){
				questions[i] = b[i]["question"];
			}
			displayQuestion(1);
		};
		$.post(url, callback);
	}
}

function recordAnswer(num){
	var url=base+"answer="+$("#answer")[0].value+"&question="+$("#qId")[0].value;	
	var callback = function(data) {
		var json = parseJSON(data);
		var b = json["results"]["bindings"];
		if(b[0]["success"] == "true") displayQuestion(num);
		else displayQuestion(num-1);
	};
	$.post(url, callback);
}

function displayQuestion(num){
	
		var q = questions[num-1];
		if(q){
			$("#examInfo").hide();
			$("#question").show();
			$("#qText")[0].innerHTML = q.text;
			$("#qId")[0].value = q.id;
			var choices = q.choices;
			var html = "";
			for(var i=0; i<choices.length; i++){
				html += "<input type='radio' name='answers' value='"+(i+1)+"' onclick=\"$('#answer')[0].value=this.value;\">"+choices[i].text+"<br/>";			}
			$("#choices")[0].innerHTML = html;
			$("#nextQues")[0].onclick = function(){
				recordAnswer(num+1);
			};
		}else{
			displayDone();	
		}
}

function displayDone(){
	var url=base+"grade";	
	var callback = function(data) {
		var json = parseJSON(data);
		var b = json["results"]["bindings"];
		$("#question")[0].innerHTML = "<h1>Exam Finished</h1>"+
												"<h3>You made "+b[0]["grade"]+"%</h3>"+
												"<a href='Login'>Return to Menu</a>";
	};
	$.post(url, callback);
}

function parseJSON(json){
	return eval("("+json+")");
}