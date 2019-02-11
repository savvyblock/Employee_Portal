/**
 * 
 */

$.ajaxSetup({
	cache : false
});

$(document).ready(function() {

	if (Number($("#supervisorChainLevel").val()) > 0) {
		$("#prevLevel").prop("disabled", false);
	}
	if ($("#selectedDirectReportEmployeeNumber").val() != 0) {
		$("#nextLevel").prop("disabled", false);
	}
	
	initDatePicker("temporaryApprovers",false, false);
	// set current-value attributes for input date fields and min&max dates
	$("input[name*='fromDate'],input[name*='toDate']").each(			
			function(index, obj) {
				$(obj).keypress(  
						function(keyPressEvent) { 							
							// a keypress event is generated for only printable characters
							var validDateKey = keyPressDateEntry(keyPressEvent);
							if (validDateKey==false) {
								keyPressEvent.stopImmediatePropagation();
							}
							return validDateKey;
					});
				$(obj).keydown(
							function(keyDownEvent) {
								// a keydown event is generated for both printable and non-printable characters
								if (isBackspaceOrDeleteKey(keyDownEvent)) {
									// hide the datepicker if the backspace or Delete key is pressed
									$(keyDownEvent.target).datepicker("hide");
								}
								return true;
					});
				$(obj).blur(onBlurDateEntry);
				var dateText = $(obj).val();
				$(obj).attr('current-value',dateText);
				if (dateText!="" && dateText!="  -  -    ") {
					adjustDateMinMax (dateText, obj, false);
				}
			}
	);
	
	employeeAccessTempApproverAutoCompleteSetup(".employeeTempApproverAutoComplete:not(.ui-autocomplete-input)");
	
	if ($("tr.no_rows").length > 0) {
		$('#resultsTable').find('tbody').empty();
	}
	
	// the TxEIS paradigm is to always have a blank row at the bottom of the table
	// but only do this if the last row wasn't added during this editing session
	// (only add on a get ... as opposed to returning from a validation error)
	if ( $("#resultsTableTBody tr:last").find("input[id*='id']").val() != 0 ) {
		addTemporaryApproverRow();
	} else {
		// since not adding a row, set up the tabout for the first input of the last row
		setupLastRowTabOut();
	}
	
	// this resize is related to the processing in the noScrollTableHeader()
	// resizeFixed callback
	// to prvent the div horizontal scrollbar from appearing in favor of the
	// browser horizontal scrollbar;
	$(window).trigger('resize');

	// hide any open datepickers after data load and set focus
	window.onload = function(e){ 
		// if error, set focus to first error field ... otherwise, set to first field of last row
		if ($("input[id*='error_fieldName_']").size() > 0) {
			var firstErrorFieldId = $("input[id*='error_fieldName_']:first").value();
			$("#"+firstErrorFieldId).focus();
		} else {
			$("#resultsTable input[name*='autoCompleteString']:last").focus();
		}
		$("input.popupDatepicker").datepicker("hide");
	}
	
	displaySaveMessage();
});

function previousSupervisorLevel (event) {
	$('#leaveTemporaryApproversForm').attr('method', 'POST');
	$('#leaveTemporaryApproversForm').attr('action', '/EmployeeAccess/app/leave/leaveRequestTemporaryApprovers/prevSupervisorLevel');
	$('#leaveTemporaryApproversForm').submit();
	return false;
}

function nextSupervisorLevel (event) {
	$('#leaveTemporaryApproversForm').attr('method', 'POST');
	$('#leaveTemporaryApproversForm').attr('action', '/EmployeeAccess/app/leave/leaveRequestTemporaryApprovers/nextSupervisorLevel');
	$('#leaveTemporaryApproversForm').submit();
	return false;
}

function submitSave(event) {
	if (deleteRowQuery('resultsTable', event)==true) {
		$('#leaveTemporaryApproversForm').attr('method', 'POST');
		$('#leaveTemporaryApproversForm').attr('action', '/EmployeeAccess/app/leave/leaveRequestTemporaryApprovers/save');
		$('#leaveTemporaryApproversForm').submit();
	}
	return false;
}

function submitReset() {
	window.location.href = '/EmployeeAccess/app/leave/leaveRequestTemporaryApprovers';
	return false;
}

function addTemporaryApproverRow(event) {
	var $tbody = $('#resultsTable').find('tbody');
	var tbody = $tbody[0];
	var rows = tbody.rows;
	var numRows = rows.length;
	var newRowNum = $('#resultsTable').find('tbody')[0].rows.length;

	var $clonedTr = $('#emptyTemporaryApproverTable').find("tbody > tr").clone(true,
			true);

	var oldRowNum = 0;
	var trIndex = numRows;

	$clonedTr.find("input[id*='emptyTempApproverRow']").attr(
			"id",
			function(inputIndex, idVal) {
				return idVal.replace("emptyTempApproverRow",
						"temporaryApprovers").replace(oldRowNum, trIndex + "");
			});
	$clonedTr.find("input[name*='emptyTempApproverRow']").attr(
			"name",
			function(inputIndex, nameVal) {
				return nameVal.replace("emptyTempApproverRow",
						"temporaryApprovers").replace(oldRowNum, trIndex + "");
			});
	$clonedTr.find("select[id*='emptyTempApproverRow']").attr(
			"id",
			function(selectIndex, idVal) {
				return idVal.replace("emptyTempApproverRow",
						"temporaryApprovers").replace(oldRowNum, trIndex + "");
			});
	$clonedTr.find("select[name*='emptyTempApproverRow']").attr(
			"name",
			function(selectIndex, nameVal) {
				return nameVal.replace("emptyTempApproverRow",
						"temporaryApprovers").replace(oldRowNum, trIndex + "");
			});
	$clonedTr.find("span[id*='emptyTempApproverRowDeleteRowSpan_']").attr(
			"id",
			function(spanIndex, idVal) {
				return idVal.replace("emptyTempApproverRowDeleteRowSpan_0",
						"deleteRowSpan_"+trIndex);
			});

	$clonedTr.find(".row_number").text(trIndex+1);
	$clonedTr.find("input[name*='autoCompleteString']").addClass("employeeTempApproverAutoComplete");
	$('#resultsTable').find('tbody').append($clonedTr);
	employeeAccessTempApproverAutoCompleteSetup(".employeeTempApproverAutoComplete:not(.ui-autocomplete-input)");
	
	$clonedTr.find("input[name*='fromDate'],input[name*='toDate']").each(			
			function(index, obj) {
				$(obj).keypress(  
					function(keyPressEvent) { 							
						// a keypress event is generated for only printable characters
						var validDateKey = keyPressDateEntry(keyPressEvent);
						if (validDateKey==false) {
							keyPressEvent.stopImmediatePropagation();
						}
						return validDateKey;
				});
				$(obj).keydown(
						function(keyDownEvent) {
							// a keydown event is generated for both printable and non-printable characters
							if (isBackspaceOrDeleteKey(keyDownEvent)) {
								// hide the datepicker if the backspace or Delete key is pressed
								$(keyDownEvent.target).datepicker("hide");
							}
							return true;
					});
				EditMask.initializeObjectBehavior(obj);
				$(obj).blur(onBlurDateEntry);
				$(obj).attr('current-value','  -  -    ');
			}
		);
	initDatePicker("temporaryApprovers",true, false);

	setupLastRowTabOut();
	
	if (event) {
		// the presence of the event object indicates the function was called by the user clicking on the "Add" link 
		// ... as opposed to this function being called programatically; set the focus to the first field in the added row
		$("#resultsTable input[name*='autoCompleteString']:last").focus();
	}
}

// this will automatically add a row to the table if the user
// tabs out of the temporary approver field after entering data on the last row
function setupLastRowTabOut() {
	$("#resultsTable").find("input.lastRowTabOut").off("keydown",onKeydownLastRowTabOut).removeClass("lastRowTabOut");
	$("#resultsTable").find("input[name*='autoCompleteString']:last").addClass("lastRowTabOut");
	$('.lastRowTabOut').keydown(onKeydownLastRowTabOut);
	
}

function onKeydownLastRowTabOut(keyDownEvent) {
	var code = keyDownEvent.keyCode;
	if (keyDownEvent.keyCode == 9 && keyDownEvent.target.value > "") {
		addTemporaryApproverRow();
	}
}

function employeeAccessTempApproverAutoCompleteSetup (selector) {
	var supervisorEmployeeNumber = '';
	if (($("#supervisorViewDiv span:last").length > 0) && ($("#supervisorViewDiv span:last").find("input[id*='employeeNumber']").length > 0)) {
		supervisorEmployeeNumber = $("#supervisorViewDiv span:last").find("input[id*='employeeNumber']").val();
	}
	$(selector).autocomplete({
		source: function( request, response) {
			$.getJSON("autoComplete", {
					application: "employeeTempApproverSearch", excludeEmpNumber: supervisorEmployeeNumber, q: request.term
				}, function(data) {
						response($.map(data.results, function(item){
							return {
								label: item.code + " : " + item.descr,
								value: item.code + " : " + item.descr,
								empNbr: item.code
							}
						}));
				});
			},
			focus: function() {
				return false;
			},
			select: function(event, ui) {
				$(this).val(ui.item.value);
				// copy the employee number into the hidden field 
				$(this).closest("td").find("input[id*='employeeNumber']").val(ui.item.empNbr);
				// set the value into the text field
				var inputObj = $(this).closest("td").find("input[id*='autoCompleteString']")[0];
				
				// auto tab as long as the selection was made without the use of the tab key
				var charCode = (event.which) ? event.which : event.keyCode;
				if (charCode!=9) {
					if ($(inputObj).hasClass("lastRowTabOut")) {
						// this is the last row in the table... add a row
						addTemporaryApproverRow();
					}
					focusNext(inputObj);
				}
			}
	});

}

function toggleTempApproverDeleteRow(event) {
	var spanObj = event.target;
	var trObj = $(spanObj).closest("tr");
	
	if ($(spanObj).hasClass("deleteRowButton")) {
		$(spanObj).removeClass("deleteRowButton").addClass("deleteRowButtonSelected");
		$(trObj).addClass("deleteRowIndicated").find("input[id*='deleteIndicated']").val(true);
		
	} else {
		$(spanObj).removeClass("deleteRowButtonSelected").addClass("deleteRowButton");
		$(trObj).removeClass("deleteRowIndicated").find("input[id*='deleteIndicated']").val(false);
	}
}

function onBlurTempApproverEntry(event) {
	var trObj = $(event.target).closest("tr");
	var tempApproverEmployeeNumber = $(trObj).find("input[id*='employeeNumber']").val();
	var inputAutoCompleteString = $(trObj).find("input[id*='autoCompleteString']").val().trim();
	if (!inputAutoCompleteString.startsWith(tempApproverEmployeeNumber)) {
		$(trObj).find("input[id*='employeeNumber']").val(""); // validate the approver emp number entered on the server side
	}
}

function onBlurSetModified(event) {
	var trObj = $(event.target).closest("tr");
	$(trObj).find("input[id*='modified']").val(true);
}

