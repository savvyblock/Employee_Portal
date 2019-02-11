$(document).ready(function() { 

	var oldPos = 0;

	$(".autoAdvance").keyup(function(event) {
		var length = $(this).val().length;
	    var maxlength = $(this).attr("maxlength");
	    var fieldId = $(this).attr("id");
	    var fieldName = fieldId.substring(0, fieldId.indexOf('_'));
	    var fieldNumber = parseInt(fieldId.substring(fieldId.indexOf('_')+ 1, fieldId.length));
	    var currentCtrl= document.getElementById(fieldName+"_"+fieldNumber);
		var pos = 0;
		var browser;
		     
		if (document.selection) {
			currentCtrl.focus ();
			var sel = document.selection.createRange();
			sel.moveStart ('character', -currentCtrl.value.length);
			pos = sel.text.length;
			browser = "IE";
		}     
		else if (currentCtrl.selectionStart || currentCtrl.selectionStart == '0')
		{
			pos = currentCtrl.selectionStart; 
			browser="Firefox";
		}
		 
		if(pos == maxlength)
		{
			fieldNumber = fieldNumber+1;
			var nextClass = document.getElementById(fieldName+"_"+fieldNumber);
		    oldPos = 0;
		    
		    if(browser == "Firefox")
		    {
			   	nextClass.selectionStart = 0;
				nextClass.selectionEnd = 0;
				nextClass.focus();
		   	}
		   	else
		   	{
		   		nextClass.focus();
		   	}
		}
		
		else if(pos == 0 && (currentCtrl.selectionStart == currentCtrl.selectionEnd && (event.keyCode==8 && oldPos == 0) ))
		{
			fieldNumber = fieldNumber-1;
			var nextClass = document.getElementById(fieldName+"_"+fieldNumber);
			oldPos = nextClass.maxLength;
		    
		    if(browser == "IE")
		    {
				nextClass.focus();
				var nextSel = document.selection.createRange();
				nextSel.moveStart ('character', -nextClass.value.length);
				nextSel.moveStart ('character', oldPos);
				nextSel.moveEnd ('character', 0);
				nextSel.select ();
		    }
		    else
		    {
		    	nextClass.focus();
		    }

		}
		else
		{
			oldPos= pos;
		}
		           
	});
	
	$(".autoAdvance").mouseup(function() 
	{
		var fieldId = $(this).attr("id");
	    var fieldName = fieldId.substring(0, fieldId.indexOf('_'));
	    var fieldNumber = parseInt(fieldId.substring(fieldId.indexOf('_')+ 1, fieldId.length));
	    var currentCtrl= document.getElementById(fieldName+"_"+fieldNumber);
	
		if (document.selection) {
			currentCtrl.focus ();
			var sel = document.selection.createRange();
			sel.moveStart ('character', -currentCtrl.value.length);
			oldPos = sel.text.length;
		}     
		else if (currentCtrl.selectionStart || currentCtrl.selectionStart == '0')
			oldPos = currentCtrl.selectionStart; 
	});
			
});