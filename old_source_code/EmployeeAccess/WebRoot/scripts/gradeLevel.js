// sets the options in the select with 
// id additionalGradeRestrictionId
// based on the value in gradeRestrictionId 
// and the campus min/max grades

function setAdditionalGradeRestriction(gradeRestrictionId, additionalGradeRestrictionId, minGrade, maxGrade)
{
	$("#"+additionalGradeRestrictionId).find("option").remove();
	var g = $("#"+gradeRestrictionId).val();
	
	// invalid grade restriction, don't add any
	// additional grade restrictions
	if(g < minGrade || g > maxGrade)
	{
		return;
	}
	
	for(var i = 0; i <= maxGrade - g; i++)
	{
		$("#"+additionalGradeRestrictionId).append($("<option></option>").attr("value",i).text(i));
	}
}
