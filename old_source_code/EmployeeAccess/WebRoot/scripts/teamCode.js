function getStudents(controlNumber){
	Spring.remoting.submitForm("frField", "mainForm", { _eventId:"retrieve" });
}

function getStudent(studentId){	
	studentId[studentId.selectedIndex].text = studentId.value;	
	Spring.remoting.submitForm("studentIdDdl", "mainForm", { _eventId:"retrieve" });
	
}

function selectStudents(element){
	var checked = element.checked;
	if (checked)
		Spring.remoting.submitForm("selectAll", "mainForm", { _eventId:"select" });
	else
		Spring.remoting.submitForm("selectAll", "mainForm", { _eventId:"deselect" });
}

function setLabel(ddl){
	var studentId = document.getElementById(ddl);
	studentId[studentId.selectedIndex].text = studentId.value;
}

function updateDropDown(dropdown, input){
	var ddl = document.getElementById(dropdown);
	var inputValue = document.getElementById(input).value;
	var options = ddl.options;
	
	var match = false;
	for (i=0; i <= options.length - 1;i++)
	{
		var option = options[i];
		if (option.value == inputValue)
			match = true;
			
	}
	if (!match)
    	ddl.options.add(new Option(inputValue, inputValue));
    else
    	alert("Team code already exists");	
    
}
