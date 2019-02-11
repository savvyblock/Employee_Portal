function setSelectedPage(optionId, inputId) {
	var selected_index = document.getElementById(optionId).selectedIndex;
	document.getElementById(inputId).value = document.getElementById(optionId).options[selected_index].value;	
}
