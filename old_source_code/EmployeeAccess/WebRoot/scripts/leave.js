/**
 * 
 */

$.ajaxSetup({
	cache : false
});

$(document).ready(function() {

	// indicate errors if any
	$("[id*='error_fieldName_']").each(function(index){
	
		var errorFieldNameValue = $(this).val();
		var errorFieldMessageId = $(this).attr("id").replace("error_fieldName_","error_defaultMessage_");
		var errorFieldMessageValue = $("[id='" + errorFieldMessageId + "']").val();
		var obj = $("[name='" + errorFieldNameValue + "']")[0];
		
		if (obj.tagName.trim().toUpperCase() == "SELECT") {
			$(obj).addClass("error");
			var parent = $(obj).parent()[0];
			if (parent.tagName.trim().toUpperCase() == "SPAN") {
				$(parent).attr("title", errorFieldMessageValue).tooltip({
					tooltipClass : "tooltipError"
				});
			} else {
				$(obj).attr("title", errorFieldMessageValue).tooltip({
					tooltipClass : "tooltipError"
				});
			}
		} else if ($(obj).attr("type")=='radio') {
			var parent = $(obj).parent()[0];
			if (parent.tagName.trim().toUpperCase() == "SPAN") {
				// add error class to the span... adding it to the radio makes no difference
				$(parent).addClass("error");
				$(parent).attr("title", errorFieldMessageValue).tooltip({
					tooltipClass : "tooltipError"
				});
			} else {
				$(obj).addClass("error");
				$(obj).attr("title", errorFieldMessageValue).tooltip({
					tooltipClass : "tooltipError"
				});
			}			
		} else {
			$(obj).addClass("error");
			$(obj).attr("title", errorFieldMessageValue).tooltip({
				tooltipClass : "tooltipError"
			});
		}

	});
	
	var supervisorEmployeeNumber = '';
	if (($("#supervisorViewDiv span:last").length > 0) && ($("#supervisorViewDiv span:last").find("input[id*='employeeNumber']").length > 0)) {
		supervisorEmployeeNumber = $("#supervisorViewDiv span:last").find("input[id*='employeeNumber']").val();
	}
	
	if ($('#calendarPlugin').length > 0) {
		$('#calendarPlugin').fullCalendar({
			theme: true,
			contentHeight: 550,
			handleWindowResize: true,
			eventLimit: true,
			eventBackgroundColor: '#B8E1F5',
			eventBorderColor: '#0093C7',
			eventTextColor: '#001F68',
			header: {
				left: '',
				center: 'title',
				right: 'prev next'
			},
			themeButtonIcons: {
				prev: 'carat-1-w',
				next: 'carat-1-e',
				prevYear: 'seek-prev',
				nextYear: 'seek-next'			
			},
			eventClick: function(calEvent, jsEvent, view) {
				$.ajax({
					url: "/EmployeeAccess/app/leave/leaveRequestCalendar/getDetails?id=" + calEvent.id,
					method: "GET",
					cache: false,
					dataType: "json"
				}).done(function(result) {
					$("#fromTime").html(result.fromTime);
					$("#toTime").html(result.toTime);
					$("#units").html(result.units);
					$("#leaveCd").html(result.leaveCd);
					$("#descr").html(result.descr);
					$("#reason").html(result.reason);
					$("#status").html(result.status);
					$("#approver").html(result.approver);
					$("#comments").html(result.comments);
					$("#appComments").html(result.appComments);
				}).fail(function(jqXHR, textStatus){
					alert("Request Failed:" + textStatus);
				});
				$("#detailDialog").dialog({ 
					modal:true,
					width:750,
					height:200, 
					draggable: true,
					resizable: false,
					position: {my: "bottom", at: "center"},
					title: calEvent.title + " - " + calEvent.start.format("MMM Do YYYY")
				});
			},
			eventMouseover: function(calEvent, jsEvent, view) {
				$(this).css('background', '#75C5EB');
				$(this).css('border-color', '#0093C7');
				$(this).css('color', '#001F68');
			},
			eventMouseout: function(calEvent, jsEvent, view) {
				$(this).css('background', '#B8E1F5');
				$(this).css('border-color', '#0093C7');
				$(this).css('color', '#001F68');
			},
			events: '/EmployeeAccess/app/leave/leaveRequestCalendar/getEvents?emp_nbr='+supervisorEmployeeNumber,
			displayEventEnd: true
		});
	}
	
});

function showCalendar(event) {
	var dialogTitle = 'Calendar';
	var supervisorLabel = $("#supervisorViewDiv span:last").text().trim();
	if (supervisorLabel != '') {
		dialogTitle = 'Calendar for supervisor ' + supervisorLabel;
	}
	var d = $('#calendarDiv').dialog({
		title : dialogTitle,
		width : 'auto',
		minWidth : '1270px',
		width : '1270px',
		modal : true,
		position : ({
			my : "center top",
			at : "center top",
			of : window
		}),
		buttons : {
			Cancel : {
				text : "Close",
				click : function() {
					d.dialog('close');
					calendarDialogClosingCleanup();
				}
			}
		}
	});

	$('#calendarDiv').off("dialogclose");
	$('#calendarDiv').on("dialogclose", function(event){calendarDialogClosingCleanup(); return true;});

	d.dialog('open');
	$('#calendarPlugin').fullCalendar('render');
}

function calendarDialogClosingCleanup() {
	// close detail if it is open
	if ($("#detailDialog").hasClass('ui-dialog-content') == true && $("#detailDialog").dialog('isOpen') == true) {
		$("#detailDialog").dialog('close');
	}
	// set focus to direct report supervisors dropdown
	$("#selectedDirectReportEmployeeNumber").focus();
}

function initDatePicker(inputIdMatch, defaultInputs, doLeaveUnitsCalc) {
	// $('.popupDatepicker:not(.hasDatepicker)').val(" - - "); // this default
	// is not being applied after the clone() so set it here

	var $datepickerObjects = $("input[id*='"+inputIdMatch+"'].popupDatepicker:not(.hasDatepicker)");
	$("input[id*='"+inputIdMatch+"'].popupDatepicker:not(.hasDatepicker)")
			.datepicker(
					{
						changeMonth : true,
						changeYear : true,
						dateFormat : 'mm-dd-yy',
						numberOfMonths : 1,
						showOn: "none",
						onSelect : function(dateText, obj) {
							$(this).attr('current-value',dateText);
							adjustDateMinMax(dateText, this, false);
							if (doLeaveUnitsCalc) {
								jsonCalculateLeaveUnitsRequested(this);
							}
							focusNext(this);
						},
						onClose : function(dateText, obj) {
						}
					});
	if (defaultInputs) {
		// only set the date of those datepicker input fields which initially didn't have the class hasDatepicker
		$datepickerObjects.datepicker("setDate",null).val("  -  -    ");  // sets date value to null... but doesn't appear to be the case on subsequent call to adjustDateMinMax :-(
	}
}

function reInitDatePicker(trObj, doLeaveUnitsCalc) { 
	var fromDateObj = $(trObj).find("input[name*='fromDate']")[0];
	var toDateObj = $(trObj).find("input[name*='toDate']")[0];
	var fromDateValue = $(fromDateObj).val();
	var toDateValue = $(toDateObj).val();

	$(trObj).find(".popupDatepicker").removeClass("hasDatepicker").datepicker(
			{
				changeMonth : true,
				changeYear : true,
				dateFormat : 'mm-dd-yy',
				numberOfMonths : 1,
				showOn: "none",
				onSelect : function(dateText, obj) {
					// unsavedTracking.onElementChanged(null);
					$(this).attr('current-value',dateText);
					adjustDateMinMax(dateText, this, false);
					if (doLeaveUnitsCalc) {
						jsonCalculateLeaveUnitsRequested(this);
					}
					focusNext(this);
				},
				onClose : function(dateText, obj) {
				}
			});
	if (fromDateValue != "" && fromDateValue != "  -  -    ") {
		$(fromDateObj).datepicker("setDate", fromDateValue);
		$(toDateObj).datepicker("option", "minDate", fromDateValue);
	}
	if (toDateValue != "" && toDateValue != "  -  -    ") {
		$(toDateObj).datepicker("setDate", toDateValue);
		$(fromDateObj).datepicker("option", "maxDate", toDateValue);
	}
}

function keyPressDateEntry(keyPressEvent) {
	$(keyPressEvent.target).datepicker("hide");
	return isPosNumberKey(keyPressEvent);
}

function adjustDateMinMax (dateText, obj, keyboardInput) {
	// assume dateText is either a valid date string or null
	var name = $(obj).attr("name");
	if (name.indexOf("fromDate") != -1) {
			var toDateName = name.replace("fromDate", "toDate");
			var toDateString = $("input[name*='" + toDateName + "']").val();
			var toDate = $("input[name*='" + toDateName + "']").datepicker("getDate");
			if (toDateString=="" || toDateString=="  -  -    ") {
				// from date entered... to date unspecified ... set the to date to the from date
				// ... but don't do this if the field is in temp approvers or the filter field of overview
				if (name.indexOf("Filter")==-1 && name.indexOf("temporary")==-1) {
					toDateString = dateText;
					$("input[name*='" + toDateName + "']").datepicker("setDate",dateText);
					$(obj).datepicker("option", "maxDate", dateText);	
				}
			} else if (dateText!=null && toDate!=null && keyboardInput) {
				// if this input was typed in by the user, check if the from date still precedes 
				// the to date and adjust if necessary		
				var fromDate = $.datepicker.parseDate( 'mm-dd-yy', dateText);
				if (fromDate!=null && (toDate-fromDate)<0) {
					$("input[name*='" + toDateName + "']").datepicker("setDate",null);
					toDateString = "  -  -    ";
					$(obj).datepicker("option", "maxDate", null);
					$(obj).val(dateText).datepicker("setDate",dateText);
				}
			}
			$("input[name*='" + toDateName + "']").datepicker("option", "minDate", dateText);
			$("input[name*='" + toDateName + "']").val(toDateString);			
	} else {
			var fromDateName = name.replace("toDate", "fromDate");
			var fromDateString = $("input[name*='" + fromDateName + "']").val();
			var fromDate = $("input[name*='" + fromDateName + "']").datepicker("getDate");
			if (dateText!=null && fromDate!= null && keyboardInput) {
				// if this input was typed in by the user, check if the from date still precedes 
				// the to date and adjust if necessary		
				var toDate = $.datepicker.parseDate( 'mm-dd-yy', dateText);
				if (toDate!=null && (toDate-fromDate)<0) {
					$("input[name*='" + fromDateName + "']").datepicker("setDate",null);
					fromDateString = "  -  -    ";
					$(obj).datepicker("option", "minDate", null);
					$(obj).val(dateText).datepicker("setDate",dateText);
				}
			}
			$("input[name*='" + fromDateName + "']").datepicker("option", "maxDate", dateText);
			$("input[name*='" + fromDateName + "']").val(fromDateString);
	}
}

function onBlurDateEntry(event) {

	if ($(event.target).datepicker("widget").is(":visible")) {
		return false;
	}
		
	var dateString = $(event.target).val();
	var dateStringTrimmed = dateString.trim();
	if (dateString=='  -  -    ' || dateString.trim()=='') {
		$(event.target).datepicker("setDate",null).val("  -  -    ");
		adjustDateMinMax(null, event.target, true);
	} 
	else {
		if (validDate(dateString)) {
//			before setting value, check if the from date still comes after 
//			the to date; if not, make the necessary adjustments
			adjustDateMinMax(dateString, event.target, true);
			$(event.target).datepicker("setDate",dateString);
			$(event.target).attr('current-value',dateString);
		} else {
			// date string isn't valid ... just restore the field to what had been there
			var currentDateString = $(event.target).attr('current-value');
			if (currentDateString==null) {
				$(event.target).val("  -  -    ").datepicker("setDate",null);
			} else {
				$(event.target).val(currentDateString);
			}
		}
	}
}

function validDateFormat(dateString) {
	valid = true;
	if (dateString!=null && dateString.length==10) {
		// check if valid date
		for (i=0; i<10 && valid; i++) {
			if (i==2 || i==5) {
				if (dateString.charAt(i)!='-') {
					valid = false;
				}
			} else {
				var char = dateString.charAt(i);
				if (char!='0' && char!='1' && char!='2' && char!='3' && char!='4' && char!='5' && char!='6' && char!='7' && char!='8' && char!='9' ) {
					valid = false;
				}
			}
		}
	} else {
		valid = false;
	}
	return valid;
}

function validDate(dateString) {
	var valid = validDateFormat(dateString);
	if (valid) {
		try {
			$.datepicker.parseDate( 'mm-dd-yy', dateString);
		} catch(err) {
			valid = false;
		}
	}
	return valid;
}

function isPosDecimalKey(keyPressEvent) {
	// in theory, a keyPress event should only get fired for printable characters although this is not always the case.
	// firefox, for example, will generate a keyPress event for the tab key (charCode=9).
	// return true for non-printable characters less than 31 which would include the tab key
	//
	// since this is a decimal number, also need to accept the decimal point (period) character
	// for the keyPress event, a value of 46 corresponds to a decimal point 
	// (note that for the keyDown event, a value of 46 corresponds to the DEL key while 190 would be the decimal point)
	var charCode = (keyPressEvent.which) ? keyPressEvent.which : keyPressEvent.keyCode;
	if (charCode > 31 && charCode != 46 && (charCode < 48 || charCode > 57)) {  // the character entered must be a number or a decimal point 46 
		return false;
	} else if (charCode <= 31) {
		return true;
	}
	
	if (charCode == 46) {
		// for a decimal number, allow only a single decimal point
		var inputValue = $(keyPressEvent.target).val();
		if (inputValue.indexOf(".") != -1) {
			return false;
		}
	}
	return true;
}

// the hour is a value between 
function isHourKey(keyPressEvent) {
	// in theory, a keyPress event should only get fired for printable characters although this is not always the case.
	// firefox, for example, will generate a keyPress event for the tab key (charCode=9).
	// return true for non-printable characters less than 31 which would include the tab key
	var charCode = (keyPressEvent.which) ? keyPressEvent.which : keyPressEvent.keyCode;
	if (charCode > 31 && (charCode < 48 || charCode > 57)) {  // the character entered must be a number
		return false;
	} else if (charCode <= 31) {
		return true;
	}
	
	
	var value = $(keyPressEvent.target).val().trim();
	var valueLength = value.length;
	var selStart = keyPressEvent.target.selectionStart;
	var selEnd = keyPressEvent.target.selectionEnd;
	var selLength = keyPressEvent.target.selectionEnd - keyPressEvent.target.selectionStart;
	if (value.length>0 && selLength<value.length) {
		if (selStart==0) {
			if (charCode!=48) { // if the character entered is not a zero
				if (charCode==49) {  // if the character entered is the number one
					var secondCharCode;
					if (value.length==1) {
						secondCharCode = value.charCodeAt(0);
					} else {
						secondCharCode = value.charCodeAt(1);
					}
					
					if (secondCharCode!=48 && secondCharCode!=49 && secondCharCode!=50) {
						return false;
					}
				} else {					
					return false;
				}
			}
		} else if (selStart==1) {
			var firstCharCode = value.charCodeAt(0);
			if (firstCharCode==48) { // if the first character is a zero
				if (charCode==48) {  // then do not accept a second zero
					return false;
				}
			} else if (firstCharCode==49) {  // if the first character is a one
				if (charCode!=48 && charCode!=49 && charCode!=50) {  // then the second char can only be 0, 1, or 2
					return false;
				}
			} else {  // the first character is a number between 2 and 9 ... no character is valid as the second char
				return false;
			}
		} else {
			return false;
		}
	}
	return true;
}

function onBlurHourEntry(event) {
	var value = $(event.target).val().trim();
	var newValue;
	if (value && value.length > 0 && value.length < 2) {
		newValue = "0"+value;
		if (newValue=="00") {
			newValue="";
		}
		$(event.target).val(newValue);
	}
} 

function isMinuteKey(keyPressEvent) {
	// in theory, a keyPress event should only get fired for printable characters although this is not always the case.
	// firefox, for example, will generate a keyPress event for the tab key (charCode=9).
	// return true for non-printable characters less than 31 which would include the tab key
	var charCode = (keyPressEvent.which) ? keyPressEvent.which : keyPressEvent.keyCode;
	if (charCode > 31 && (charCode < 48 || charCode > 57)) {  // the character entered must be a number
		return false;
	} else if (charCode <= 31) {
		return true;
	}

	var value = $(keyPressEvent.target).val().trim();
	var valueLength = value.length;
	var selStart = keyPressEvent.target.selectionStart;
	var selEnd = keyPressEvent.target.selectionEnd;
	var selLength = keyPressEvent.target.selectionEnd - keyPressEvent.target.selectionStart;
	if (value.length>0 && selLength<value.length) {
		if (selStart==0) {
			if (charCode>53) { // the first character may not be greater than five
				return false;
			}
		} else if (selStart==1) {
			var firstCharCode = value.charCodeAt(0);
			if (firstCharCode>53) {  // the first character is a number greater than 5 ... no character is valid as the second char
				return false;
			}
		} else {
			return false;
		}
	}
	return true;
}

function onBlurMinuteEntry(event) {
	var value = $(event.target).val().trim();
	if (value && value.length > 0 &&  value.length < 2) {
		$(event.target).val("0"+value);
	}
} 

function isPosNumberKey(keyPressEvent) {
	// in theory, a keyPress event should only get fired for printable characters although this is not always the case.
	// firefox, for example, will generate a keyPress event for the tab key (charCode=9).
	// return true for non-printable characters less than 31 which would include the tab key
	var charCode = (keyPressEvent.which) ? keyPressEvent.which : keyPressEvent.keyCode;
	if (charCode > 31 && (charCode < 48 || charCode > 57)) {
		return false;
	}
	return true;
}

function isBackspaceOrDeleteKey(keyDownEvent) {
	// a keydown event is generated for both printable and non-printable characters
	// on a keydown event, 8 corresponds to backspace while 46 is the DEL key
	var charCode = (keyDownEvent.which) ? keyDownEvent.which : keyDownEvent.keyCode;
	if (charCode==8 || charCode==46) {
		return true;
	}
	return false;
}


function closingCommentDialog(event, required, hiddenInput) {
	$("#commentTextarea").removeClass("error").removeAttr("title");
	var comment = $("#commentTextarea").val();
	if (required && $(hiddenInput).val().trim().length==0) {
		$("#commentTextarea").addClass("error").attr("title", 'A comment is required').tooltip({tooltipClass : "tooltipError"});
//		event.stopImmediatePropagation();
		return false;
	} 
	if ($('#createEditRequestsDialogDiv').dialog("instance") && $('#createEditRequestsDialogDiv').dialog("isOpen")==true) {
		$("#disableDivOverlay").remove();
		$('#createEditRequestsDialogDiv').closest(".ui-dialog").fadeTo('fast',1.0);
		$('#createEditRequestsDialogDiv').closest(".ui-dialog").find('.ui-dialog-buttonpane button').prop('disabled', false);
		$('#createEditRequestsDialogDiv').closest(".ui-dialog").find('button.ui-dialog-titlebar-close').prop('disabled', false);
	}
	return true;
}

function editComment(event, dialogTitle, required) {
	if (!$('#editCommentDialogDiv').dialog("instance") || $('#editCommentDialogDiv').dialog("isOpen")==false) {
		
		// turn off modal property of create/edit dialog if it is open
		var dialogInstance = $('#createEditRequestsDialogDiv').dialog("instance");
		if ($('#createEditRequestsDialogDiv').dialog("instance") && $('#createEditRequestsDialogDiv').dialog("isOpen")==true) {
				$('#createEditRequestsDialogDiv').closest(".ui-dialog").fadeTo('fast',.6);
				$('#createEditRequestsDialogDiv').closest(".ui-dialog").find('.ui-dialog-buttonpane button').prop('disabled', true);
				$('#createEditRequestsDialogDiv').closest(".ui-dialog").find('button.ui-dialog-titlebar-close').prop('disabled', true);
				$('#createEditRequestsDialogDiv').append('<div id="disableDivOverlay" style="position: absolute;top:0;left:0;width: 100%;height:100%;z-index:2;opacity:0.4;filter: alpha(opacity = 50)"></div>');
		}
		
		var $commentInput = $(event.target).closest("td").find("input");
		var commentButton = $(event.target);
		$("#commentTextarea").val("").val($commentInput.val());
		setCaretToPos($("#commentTextarea")[0], 0);
		
		var d = $('#editCommentDialogDiv').dialog({
			title : dialogTitle,
			width : 'auto',
			minWidth : '60em',
			modal : true,
			position : ({
				my : "center top",
				at : "center top+100px",
				of : window
			}),
			buttons : {
				OK : function() {
					$("#commentTextarea").removeClass("error").removeAttr("title");
					var comment = $("#commentTextarea").val();
					var doClose = true;
					if (comment.trim().length==0) {
						if (required) {
							doClose = false;
							$("#commentTextarea").addClass("error").attr("title", 'A comment is required').tooltip({tooltipClass : "tooltipError"});
						} else {
							comment = "";
							if ($(commentButton).find("img").length > 0) {
								$(commentButton).find("img").remove();
								$(commentButton).find("span.commentLabelSpacer").remove();
							}
						}
					} else {
						if ($(commentButton).find("img").length == 0) {
							// add paperclip to button
							var commentImage = $("#commentEnteredImage").clone().css("display","inline");
							$(commentButton).prepend("<span class='commentLabelSpacer'>&nbsp;</span>").prepend(commentImage);
						}
					}
					if (doClose) {
						$commentInput.val(comment);
						d.dialog('close');
						if ($('#createEditRequestsDialogDiv').dialog("instance") && $('#createEditRequestsDialogDiv').dialog("isOpen")==true) {
							$("#disableDivOverlay").remove();
							$('#createEditRequestsDialogDiv').closest(".ui-dialog").fadeTo('fast',1.0);
							$('#createEditRequestsDialogDiv').closest(".ui-dialog").find('.ui-dialog-buttonpane button').prop('disabled', false);
							$('#createEditRequestsDialogDiv').closest(".ui-dialog").find('button.ui-dialog-titlebar-close').prop('disabled', false);
						}
					}
				},
				Cancel : function() {
					$("#commentTextarea").removeClass("error").removeAttr("title");
					var comment = $("#commentTextarea").val();
					if (required && $commentInput.val().trim().length==0) {
						$("#commentTextarea").addClass("error").attr("title", 'A comment is required').tooltip({tooltipClass : "tooltipError"});
					} else {
						d.dialog('close');
						if ($('#createEditRequestsDialogDiv').dialog("instance") && $('#createEditRequestsDialogDiv').dialog("isOpen")==true) {
							$("#disableDivOverlay").remove();
							$('#createEditRequestsDialogDiv').closest(".ui-dialog").fadeTo('fast',1.0);
							$('#createEditRequestsDialogDiv').closest(".ui-dialog").find('.ui-dialog-buttonpane button').prop('disabled', false);
							$('#createEditRequestsDialogDiv').closest(".ui-dialog").find('button.ui-dialog-titlebar-close').prop('disabled', false);
						}
					}
					
				}
			}
		});
		$('#editCommentDialogDiv').off("dialogbeforeclose");
		$('#editCommentDialogDiv').on("dialogbeforeclose", function(event, ui){return closingCommentDialog(event, required, $commentInput[0]);});
		
		d.dialog('open');
	}
}

$(function() {
	$('.openDialog').click(function() {
		var d = $('#' + this.getAttribute('popuptarget')).dialog({
			title : this.getAttribute('dialogTitle'),
			width : 'auto',
			minWidth : this.getAttribute('width'),
			modal : true,
			position : ({
				my : "center top",
				at : "center top+100px",
				of : window
			}),
			buttons : {
				OK : function() {
					d.dialog('close');
				},
				Cancel : function() {
					d.dialog('close');
				}
			}
		});

		d.dialog('open');
	});
});

function showCommentDiv (event) {
	if ($("div.hoverCommentDiv:hover").length == 0) {
		var offsetObj = $(event.target).offset();
		var td = $(event.target).closest("td");
		var commentVal = $(td).find("pre").html();
		$("div.hoverCommentDiv").empty().html(commentVal).offset({ top: offsetObj.top, left: offsetObj.left+20 }).css("display","block");
		// .scrollTop(0).scrollLeft(0)
	}
	
}

function hideCommentDiv (event) {
	if ($("div.hoverCommentDiv:hover").length == 0) {
		$("div.hoverCommentDiv").empty().offset({ top: 1000, left: 0 });
	}
}

function setSelectionRange(input, selectionStart, selectionEnd) {
	if (input.setSelectionRange) {
		input.focus();
		if (navigator.userAgent.search("Firefox") < 0) {
			// this is not working in firefox ... need to find a work around
			input.setSelectionRange(selectionStart, selectionEnd);
		}
	}
	else if (input.createTextRange) {
		var range = input.createTextRange();
		range.collapse(true);
		range.moveEnd('character', selectionEnd);
		range.moveStart('character', selectionStart);
		range.select();
	}
}

function setCaretToPos (input, pos) {
	setSelectionRange(input, pos, pos);
}

function adjustLeaveUnits(leaveUnitsSelectObjectId, row) {
	var $selectedLeaveUnitsOption = $("#" + leaveUnitsSelectObjectId
			+ " option:selected");
	var selectedLeaveUnit = $selectedLeaveUnitsOption.prop("label");
	if (selectedLeaveUnit.toUpperCase() == "D") {
		unitLabel = "DAYS";
	} else if (selectedLeaveUnit.toUpperCase() == "H") {
		unitLabel = "HOURS";
	} else {
		unitLabel = "";
	}
	$("#editLeaveRequestsLeaveUnitSpan" + row).text(unitLabel);
}

function adjustLeaveTypeAbsenceReasonOptions(event) {
	jsonAdjustLeaveTypeAbsenceReasonOptions(event.target, true);
}

function jsonAdjustLeaveTypeAbsenceReasonOptions(selectObj, asyncTrueFalse) {
	var payFrequencyCode = $("select[id*='selectedPayFrequencyCode']").val();
	var employeeNumber = $("#userEmpNumber").val();
	if (!employeeNumber || employeeNumber=='') {
		employeeNumber =  $("#selectedDirectReportEmployeeNumber").val();
	}
	var row = $(selectObj).closest("tr").find("input.rowNumber").val();
	var leaveTypeSelectObj;
	var absenceReasonSelectObj;
	if ($(selectObj).attr("id").indexOf("leaveType") != -1) {
		leaveTypeSelectObj = selectObj;
		var absReasonObjectId = $(selectObj).attr("id").replace("leaveType",
				"absenceReason").replace(".", "\\.");
		absenceReasonSelectObj = $("#" + absReasonObjectId)[0];
	} else {
		absenceReasonSelectObj = selectObj;
		var leaveTypeObjectId = $(selectObj).attr("id").replace(
				"absenceReason", "leaveType").replace(".", "\\.");
		leaveTypeSelectObj = $("#" + leaveTypeObjectId)[0];
	}
	var selectedLvType = leaveTypeSelectObj.value;
	var selectedAbsReason = absenceReasonSelectObj.value;

	var leaveUnitsSelectObjectId = $(leaveTypeSelectObj).attr("id").replace(
			"leaveType", "leaveUnits").replace(".", "\\.");
	var leaveUnitsSelectObject = $("#" + leaveUnitsSelectObjectId)[0];
	$(leaveUnitsSelectObject).val($(leaveTypeSelectObj).val());
	adjustLeaveUnits(leaveUnitsSelectObjectId, row);

	$.ajax({
				url : "/EmployeeAccess/app/leave/leaveUtilities/adjustLeaveTypeAbsenceReason",
				type : "GET",
				async : asyncTrueFalse,
				data : {
					leaveType : selectedLvType,
					absenceReason : selectedAbsReason,
					payFrequency : payFrequencyCode,
					employeeNumber : employeeNumber
				},
				dataType : "json",
				contentType : 'application/json; charset=utf-8',
				cache : false,
				success : function(modelMap) {
					var options = '<option value=""></option>';
					$.each(modelMap.leaveTypes, function(
							index, value) {
						if (value.lvType.toUpperCase() == selectedLvType
								.toUpperCase()) {
							options += '<option value="' + value.lvType
									+ '" selected="selected">'
									+ value.lvTypeDescription + '</option>';
						} else {
							options += '<option value="' + value.lvType + '">'
									+ value.lvTypeDescription + '</option>';
						}
					});
					$(leaveTypeSelectObj).html(options);

					options = '<option value=""></option>';
					$
							.each(
									modelMap.absenceReasons,
									function(index, value) {
										if (value.absRsn.toUpperCase() == selectedAbsReason
												.toUpperCase()) {
											options += '<option value="'
													+ value.absRsn
													+ '" selected="selected">'
													+ value.absRsnDescription
													+ '</option>';
										} else {
											options += '<option value="'
													+ value.absRsn + '">'
													+ value.absRsnDescription
													+ '</option>';
										}
									});
					$(absenceReasonSelectObj).html(options);
				},
				error : function(xhr, textStatus, errorThrown) {
					alert("adjustLeaveTypeAbsenceReason request failed "
							+ errorThrown);
				}
			});
}

function calculateLeaveUnitsRequested(event) {
	jsonCalculateLeaveUnitsRequested(event.target);
}

function jsonCalculateLeaveUnitsRequested(obj) {
	var requireUserInput = $("input[name*='requireLeaveHoursRequestedEntry']").val();
	if (requireUserInput=="true") {
		// call ajax to compute the number of days and and the total leave units requested
		// but don't compute the daily hours requested
		calculateLeaveUnitsRequestedFromDaily(obj);
	} else {
		// call ajax to compute the number of days, the daily hours requested, and the total leave units requested
		var tr = $(obj).closest("tr")[0];
		var $leaveRequestedSpan = $(tr).find("span[id*='LeaveRequestedSpan']");
		var $leaveNumberDays = $(tr).find("input[name*='leaveNumberDays']");
		var $leaveHoursDaily = $(tr).find("input[name*='leaveHoursDaily']");

		var workdayHours = $("input[name*='standardHours']").val();
		var mealBreakHours = $("input[name*='mealBreakHours']").val();
		
		// get start and end times
		var startDate = $(tr).find("input[name*='fromDate']").val().trim();
		var startHour = $(tr).find("input[name*='fromHour']").val().trim();
		var startMinute = $(tr).find("input[name*='fromMinute']").val().trim();
		var startAmPm = $(tr).find("select[name*='fromAmPm']").val().trim();
		var endDate = $(tr).find("input[name*='toDate']").val().trim();
		var endHour = $(tr).find("input[name*='toHour']").val().trim();
		var endMinute = $(tr).find("input[name*='toMinute']").val().trim();
		var endAmPm = $(tr).find("select[name*='toAmPm']").val().trim();

		var payFrequency = $("select[id*='selectedPayFrequencyCode']").val();
		var leaveType = $(tr).find("select[name*='leaveType']").val().trim();
		var leaveUnitsDorH = $(tr).find("select[name*='leaveUnits']").find("option:selected").prop("label").toUpperCase();
		var leaveHoursDaily = $(tr).find("input[name*='leaveHoursDaily']").val().trim();

		var startTime = startHour + ":" + startMinute + startAmPm;
		var endTime = endHour + ":" + endMinute + endAmPm;

		var minutesToHoursConversionArray = new Array();
//		var $rows = $('#minutesHoursConversionTable').find('tbody tr:not(".no_rows")');
//		var numRows = $rows.length;
//		for (var i = 0; i < numRows; i++) {
//			var minutesToHoursConversionObject = new Object();
//			var row = $rows[i];
//			minutesToHoursConversionObject.unitType = 'H';
//			minutesToHoursConversionObject.fractionalAmount = $(row).find("input[id*='FractionalHour']").val();
//			minutesToHoursConversionObject.fromUnit = $(row).find("input[id*='FromMin']").val();
//			minutesToHoursConversionObject.toUnit = $(row).find("input[id*='ToMin']").val();		
//			minutesToHoursConversionArray.push(minutesToHoursConversionObject);
//		}
	
		var hoursToDaysConversionArray = new Array();
//		$rows = $('#hoursDaysConversionTable').find('tbody tr:not(".no_rows")');
//		numRows = $rows.length;
//		for (var i = 0; i < numRows; i++) {
//			var hoursToDaysConversionObject = new Object();
//			var row = $rows[i];
//			hoursToDaysConversionObject.unitType = 'D';
//			hoursToDaysConversionObject.fractionalAmount = $(row).find("input[id*='FractionalDay']").val();
//			hoursToDaysConversionObject.fromUnit = $(row).find("input[id*='FromHour']").val();
//			hoursToDaysConversionObject.toUnit = $(row).find("input[id*='ToHour']").val();		
//			hoursToDaysConversionArray.push(hoursToDaysConversionObject);
//		}

		$.ajax({
				url : "/EmployeeAccess/app/leave/leaveUtilities/calculateLeaveUnitsRequested",
				type : "POST",
				async : false,
				data : JSON.stringify({
					m2hConversionRecs : minutesToHoursConversionArray,
					h2dConversionRecs : hoursToDaysConversionArray,
					startDate : startDate,
					startTime : startTime,
					endDate : endDate,
					endTime : endTime,
					leaveHoursDailyStr: leaveHoursDaily,
					leaveUnitsDorH : leaveUnitsDorH,
					workdayHoursStr : workdayHours,
					mealBreakHoursStr : mealBreakHours,
					leaveType : leaveType,
					payFrequency : payFrequency,
				}),
				dataType : "json",
				contentType : 'application/json; charset=utf-8',
				cache : false,
				success : function(modelMap) {
					var leaveHoursDaily = modelMap.leaveHoursDaily;
					var leaveUnitsRequested = modelMap.leaveUnitsRequested;
					var leaveNumberDays = modelMap.leaveNumberDays;
					$leaveHoursDaily.val(leaveHoursDaily);
					$leaveRequestedSpan.text(leaveUnitsRequested);
					$leaveNumberDays.val(leaveNumberDays);
				},
				error : function(xhr, textStatus, errorThrown) {
					alert("calculateLeaveUnits request failed "
							+ errorThrown);
				}
			});
	}
}

function displaySaveMessage() {
	var saveMessage = $("#saveMessage").text();
	if (saveMessage === "Save Successful" || saveMessage === "No changes made" || saveMessage == "Delete Successful") {
		if (saveMessage === "No changes made") {
			$("#saveMessage").removeClass('saveMessage');
			$("#saveMessage").addClass('noChangesMessage');
		} else {
			$("#saveMessage").addClass('saveMessage');
			$("#saveMessage").removeClass('noChangesMessage');
		}
		$("#saveMessage").show().delay(5000).fadeOut('slow');
	}
}

function deleteRowQuery(tableID, e) {
	if ($('#' + tableID + ' tr').is('.deleteRowIndicated')) {
		var result = confirm("Are you sure you want to delete the row? \n\n" +
			"Press OK to continue, or Cancel to stay on the current page.");	
		if(result == false){ 
			e.preventDefault();
		}
		return result;
	}
	return true; 
}

function onClickDateField(clickEvent) {
	$(clickEvent.target).datepicker("show");
}

function calculateLeaveUnitsRequestedFromDailyEH(event) {
	calculateLeaveUnitsRequestedFromDaily(event.target);
}

// ajax call to compute the number of days
function calculateLeaveUnitsRequestedFromDaily(rowObject) {
	var tr = $(rowObject).closest("tr")[0];
	var $leaveRequestedSpan = $(tr).find("span[id*='LeaveRequestedSpan']");
	var $leaveNumberDays = $(tr).find("input[name*='leaveNumberDays']");

	var workdayHours = $("input[name*='standardHours']").val();
	
	var startDate = "";
	var $fromDateInput = $(tr).find("input[name*='fromDate']");
	var fromDateInput = null;
	var ll = $fromDateInput.length;
	if (ll > 0) {
		fromDateInput = $fromDateInput[0];
	}
	if (typeof $(tr).find("input[name*='fromDate']").val() != 'undefined') {
		startDate = $(tr).find("input[name*='fromDate']").val().trim();
	}
	var endDate = "";
	if (typeof $(tr).find("input[name*='toDate']").val() != 'undefined') {
		endDate = $(tr).find("input[name*='toDate']").val().trim();
	}

	if (startDate=="" || startDate=="-  -" || endDate=="" || endDate=="-  -") {
		$leaveRequestedSpan.text("0.000");
		$leaveNumberDays.val("0");
	} else {
		var payFrequency = $("select[id*='selectedPayFrequencyCode']").val();
		var leaveType = $(tr).find("select[name*='leaveType']").val().trim();
		var leaveUnitsDorH = $(tr).find("select[name*='leaveUnits']").find("option:selected").prop("label").toUpperCase();
		var leaveHoursDaily = $(tr).find("input[name*='leaveHoursDaily']").val().trim();
		
		var hoursToDaysConversionArray = new Array();
//		var $rows = $('#hoursDaysConversionTable').find('tbody tr:not(".no_rows")');
//		var numRows = $rows.length;
//		for (var i = 0; i < numRows; i++) {
//			var hoursToDaysConversionObject = new Object();
//			var row = $rows[i];
//			hoursToDaysConversionObject.unitType = 'D';
//			hoursToDaysConversionObject.fractionalAmount = $(row).find("input[id*='FractionalDay']").val();
//			hoursToDaysConversionObject.fromUnit = $(row).find("input[id*='FromHour']").val();
//			hoursToDaysConversionObject.toUnit = $(row).find("input[id*='ToHour']").val();		
//			hoursToDaysConversionArray.push(hoursToDaysConversionObject);
//		}

		$.ajax({
			url : "/EmployeeAccess/app/leave/leaveUtilities/calculateLeaveUnitsRequestedFromDaily",
			type : "POST",
			async : false,
			data : JSON.stringify({
				h2dConversionRecs : hoursToDaysConversionArray,
				startDate : startDate,
				endDate : endDate,
				leaveHoursDailyStr: leaveHoursDaily,
				leaveUnitsDorH : leaveUnitsDorH,
				workdayHoursStr : workdayHours,
				leaveType : leaveType,
				payFrequency : payFrequency,
			}),
			dataType : "json",
			contentType : 'application/json; charset=utf-8',
			cache : false,
			success : function(modelMap) {
				var leaveUnitsRequested = modelMap.leaveUnitsRequested;
				var leaveNumberDays = modelMap.leaveNumberDays;
				$leaveRequestedSpan.text(leaveUnitsRequested);
				$leaveNumberDays.val(leaveNumberDays);
			},
			error : function(xhr, textStatus, errorThrown) {
				alert("calculateLeaveUnitsRequestedFromDaily request failed " + errorThrown);
			}
		});		
	}
	
}

function showLeaveTypeDetail(event) {
	var offsetObj = $(event.target).offset();
		var leaveType = $(event.target).closest("div").find("select").val().trim();	
		if (leaveType!="") {
			$.ajax({
				url : "/EmployeeAccess/app/leave/leaveUtilities/getLeaveTypeNotes",
				type : "GET",
				async : false,
				data : {
					leaveType : leaveType
				},
				dataType : "json",
				contentType : 'application/json; charset=utf-8',
				cache : false,
				success : function(modelMap) {
					var notes = modelMap.notes;
					if (notes.trim()=="") {
						$("div.leaveTypeDetailDiv").empty().html("no notes").offset({ top: offsetObj.top-5, left: offsetObj.left-5 }).css("visibility", "visible");
					} else {
						$("#leaveTypeDetail").empty().html(notes).offset({ top: offsetObj.top-5, left: offsetObj.left-5 }).css("visibility", "visible");
					}
				},
				error : function(xhr, textStatus, errorThrown) {
					alert("getLeaveTypeNotes for leave type: " + leaveType + " failed. " + errorThrown);
				}
			});		
		} else {
			$("#leaveTypeDetail").empty().html("no notes").offset({ top: offsetObj.top-5, left: offsetObj.left-5 }).css("visibility", "visible");
		}
		return false;
}

function hideDetailDiv(event, id) {
	$obj = $("#"+id);
	$obj.css("visibility", "hidden");
	return false;
}

function selectDirectReportSupervisor(event) {
	if ($("#selectedDirectReportEmployeeNumber").val() == 0) {
		$("#nextLevel").prop("disabled", true);
		$("input[name*='selectDirectReportSelectOptionLabel']").val("");
	} else {
		$("#nextLevel").prop("disabled", false);
		var selectOptionLabel = $("#selectedDirectReportEmployeeNumber option:selected").text().trim();
		// set value of hidden form field to the direct report employee selected (the option label)
		$("input[name*='selectDirectReportSelectOptionLabel']").val(selectOptionLabel);
	}
}

function isInputChar (char) {
	if ((char >= 48 && char <= 57) ||		// number
			(char >= 65 && char <= 90) ||	// alphabetic
			(char == 46))					// decimal point
		return true;
	else
		return false;
}

function checkAutoTab(event, inputLength) {
	var charCode = (event.which) ? event.which : event.keyCode;
	if (isInputChar(charCode)) {
		var val = $(event.target).val();
		if (val.length ==inputLength) {
			focusNext(event.target);
		}
	}
	return false;
}

function checkAutoTabDate(event) {
	var charCode = (event.which) ? event.which : event.keyCode;
	if (isInputChar(charCode)) {
		var dateString = $(event.target).val();
		if (validDate(dateString)) {
			focusNext(event.target)
		}
	}
	return false;
}

function checkAutoTabDecimal(event, decimalPlaces) {
	var charCode = (event.which) ? event.which : event.keyCode;
	if (isInputChar(charCode)) {
		var val = $(event.target).val();
		var numberPlaces = 0;
		var index = val.indexOf(".");
		if (index!=-1) {
			numberPlaces = val.length - (index+1);
		}
		if (numberPlaces == decimalPlaces) {
			focusNext(event.target);
		}
	}
	return false;
}

function focusNext(obj) {
	var browser='';
	if (document.selection) {
		obj.focus ();
		var sel = document.selection.createRange();
		sel.moveStart ('character', -obj.value.length);
		browser = "IE";
	} else if (obj.selectionStart || obj.selectionStart == '0') {
		browser="Firefox";
	}

	var inputs = $(':input:visible');
	var indexx = inputs.index(obj);
	var nextInput = inputs.get(indexx + 1);
    if (nextInput) {
		if(browser == "Firefox")
	    {
		   	nextInput.selectionStart = 0;
			nextInput.selectionEnd = 0;
	   	}
	   	nextInput.focus();
    }
}
