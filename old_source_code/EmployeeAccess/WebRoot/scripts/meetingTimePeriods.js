function populateToDropDown(gradeRestrictionId, additionalGradeRestrictionId, minGrade, maxGrade){
	var ddl = document.getElementById(additionalGradeRestrictionId);
	ddl.disabled = false;
	ddl.setAttribute("class", "");
	
	$("#"+additionalGradeRestrictionId).find("option").remove();
	var g = $("#"+gradeRestrictionId).val();
	
	// invalid grade restriction, don't add any
	// additional grade restrictions
	if(g < minGrade || g > maxGrade)
	{
		return;
	}
	var value = parseInt(g);
	for(var i = value; i <= maxGrade; i++)
	{
		var text = "0" + i;
		$("#"+additionalGradeRestrictionId).append($("<option></option>").attr("value",text).text(text));
	}

}