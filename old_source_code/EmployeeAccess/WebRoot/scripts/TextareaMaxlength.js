function checkMaxLength(field, maxLength) {
	if (field.value.length > maxLength) {
		ensureMaxLength(field,maxLength);
	}
}

function checkMaxLengthPaste(field, maxLength) {
	if ((field.value.length + window.clipboardData.getData("Text").length) > maxLength) {
		//ensureMaxLength(field,maxLength);
		window.clipboardData.setData("Text",window.clipboardData.getData("Text").substring(0,maxLength-field.value.length));
		showMaxLengthWarning(maxLength);
	}
}

function ensureMaxLength(field, maxLength)
{
	if(field.value.length > maxLength)
	{
		field.value = field.value.substring(0,maxLength);
		field.scrollTop = field.scrollHeight;
		return false;
	}
	
	return true;
}

function showMaxLengthWarning(maxLength)
{
	alert("Warning: You have exceeded the maximum number of characters (" + maxLength + ").\nThe input text has been truncated to fit.");
}