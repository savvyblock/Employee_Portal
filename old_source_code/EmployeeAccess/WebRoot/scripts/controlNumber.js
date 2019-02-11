function getStudents(id){
	Spring.remoting.submitForm(id, "mainForm", { _eventId:"retrieve" });
}

function getStudentsGradeLevel(id){
	Spring.remoting.submitForm(id, "mainForm", { _eventId:"retrieveGradeLevel" });
}

function getStudentsByCourse(id){	
	Spring.remoting.submitForm(id, "mainForm", { _eventId:"retrieveByCourse" });
	
}

function selectStudents(element){
	var checked = element.checked;
	if (checked)
		Spring.remoting.submitForm("selectAll", "mainForm", { _eventId:"select" });
	else
		Spring.remoting.submitForm("selectAll", "mainForm", { _eventId:"deselect" });
}