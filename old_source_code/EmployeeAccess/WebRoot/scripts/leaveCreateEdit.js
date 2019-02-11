$.ajaxSetup({
	cache : false
});

$(document).ready(function() {

	$("#addRequestRowButton").text("Add Row").button({
		text : true
	});

	$("#deleteRequestRowButton").text("Delete Row").button({
		text : true
	});

	if ($("#resultsTable .case:checked").length > 0) {
		$("#editRequestsButton").prop("disabled", false);
		$("#deleteRequestsButton").prop("disabled", false);
	} else {
		$("#editRequestsButton").prop("disabled", true);
		$("#deleteRequestsButton").prop("disabled", true);
	}
	if ($("#createEditRequestsTable .case:checked").length > 0) {
		$("#deleteRequestRowButton").prop("disabled", false);
	} else {
		$("#deleteRequestRowButton").prop("disabled", true);
	}

	if ($("input[name*='supervisorEmpNumber']").val().length==0 || $("select[name*='selectedPayFrequencyCode'] option").length==0) {
//			|| 
//			$("#minutesHoursConversionTable tr.no_rows").length==1 || $("#hoursDaysConversionTable tr.no_rows").length==1) { 
		$("#selectall").prop('checked', false).prop("disabled", true);
		$("input[name*='selected'].case").prop('checked', false).prop("disabled", true);
		$("#createRequestsButton").prop("disabled", true);
		var errorText = '';
		if ($("input[name*='supervisorEmpNumber']").val().length==0) {
			errorText = 'Leave Requests cannot be entered.  No supervisor found.' + '<br/>';
		}
		if ($("select[name*='selectedPayFrequencyCode'] option").length==0) {
			errorText += 'Leave Requests cannot be entered.  No leave types assigned.' + '<br/>';
		}
//		if ($("#minutesHoursConversionTable tr.no_rows").length==1 || $("#hoursDaysConversionTable tr.no_rows").length==1) {
//			errorText += 'Leave Requests cannot be entered.  Units conversion tables are undefined.' + '<br/>';
//		}
		$("#errorDiv").find("div.error p").html(errorText);
	}
	
	$(window).trigger('resize');

	$(".wrap_field").focus();
});

function submitPayrollChange() {
	$('#unprocessedLeaveRequestsForm').attr('method', 'POST');
	$('#unprocessedLeaveRequestsForm').attr('action',
			'/EmployeeAccess/app/leave/createEditLeaveRequest/payrollChange');
	$('#unprocessedLeaveRequestsForm').submit();
}

function checkboxClicked(ancestorId) {
	// clearSaveMessage();
	var $selectAllCheckbox = $("#" + ancestorId).find("[id='selectall']");
	if ($("#" + ancestorId + " .case").length == $("#" + ancestorId
			+ " .case:checked").length) {
		// $("#selectall").prop("checked",true);
		$selectAllCheckbox.prop("checked", true);
	} else {
		// $("#selectall").prop("checked",false);
		$selectAllCheckbox.prop("checked", false);
	}
	if ($("#" + ancestorId + " .case:checked").length > 0) {
		if (ancestorId == 'resultsTableDiv') {
			$("#editRequestsButton").prop("disabled", false);
			$("#deleteRequestsButton").prop("disabled", false);
		} else {
			$("#deleteRequestRowButton").prop("disabled", false);
		}
	} else {
		if (ancestorId == 'resultsTableDiv') {
			$("#editRequestsButton").prop("disabled", true);
			$("#deleteRequestsButton").prop("disabled", true);
		} else {
			$("#deleteRequestRowButton").prop("disabled", true);
		}
	}
}

function toggleCheckboxes(ancestorId) {
	// clearSaveMessage();
	var $selectAllCheckbox = $("#" + ancestorId).find("[id='selectall']");
	$("#" + ancestorId + " .case").prop('checked',
			$selectAllCheckbox.prop("checked"));

	if ($("#" + ancestorId + " .case:checked").length > 0) {
		if (ancestorId == 'resultsTableDiv') {
			$("#editRequestsButton").prop("disabled", false);
			$("#deleteRequestsButton").prop("disabled", false);
		} else {
			$("#deleteRequestRowButton").prop("disabled", false);
		}
	} else {
		if (ancestorId == 'resultsTableDiv') {
			$("#editRequestsButton").prop("disabled", true);
			$("#deleteRequestsButton").prop("disabled", true);
		} else {
			$("#deleteRequestRowButton").prop("disabled", true);
		}
	}
}

function submitCreateLeaveRequests() {
	alert("submitCreateLeaveRequests");
	$('#unprocessedLeaveRequestsForm').attr('method', 'POST');
	$('#unprocessedLeaveRequestsForm').attr('action', 'createEditLeaveRequest/create');
	$('#unprocessedLeaveRequestsForm').submit();
}

function createLeaveRequests(event) {
	$("#createEditRequestsDialogDiv").find("div.error p").html("");
	$("#userAction").val("create");
	$("div.request_dialog_buttons_div").show();
	$(".createTableCol").show();

	var d = $('#createEditRequestsDialogDiv').dialog({
		title : "Create Leave Requests",
		width : 'auto',
		minWidth : '1290px',
		width : '1290px',
		modal : true,
		position : ({
			my : "center top",
			at : "center top+100px",
			of : window
		}),
		buttons : {
			OK : {
				text : "Submit for Approval",
				tabindex : "1",
				click : function() {
					var success = jsonSubmitCreateLeaveRequests('create');
					// submitCreateLeaveRequests();
					if (success) {
						submitPayrollChange();
						d.dialog('close');
					}
				}
			},
			Cancel : {
				text : "Cancel",
				tabindex : "1",
				click : function() {
					d.dialog('close');
				},
				keydown : function(event) {
					var charCode = (event.which) ? event.which : event.keyCode;
					if (charCode == 9 && !event.shiftKey) {
						$("#addRequestRowButton").focus();	
						return false;
					}
				}
			}
		}
	});

	d.dialog('open');

	// remove any rows that may be in the table
	$('#createEditRequestsTable').find('tbody').empty();
	addRequestRow();
	$('#createEditRequestsTable').find("input[name*='fromDate']:first").focus().datepicker("hide");	
}

function jsonSubmitCreateLeaveRequests(createEditFlag) {
	var payFrequencyCode = $("select[id*='selectedPayFrequencyCode']").val();
	var rows = $('#createEditRequestsTable').find('tbody')[0].rows;
	var numRows = rows.length;
	var leaveRequestsArray = new Array();

	for (var i = 0; i < numRows; i++) {
		var leaveRequestObject = new Object();

		var row = rows[i];
		leaveRequestObject.id = $(row).find("input[name*='id']").val();
		leaveRequestObject.fromDate = $(row).find("input[name*='fromDate']").val();
		leaveRequestObject.fromDateString = $(row).find("input[name*='fromDate']").val();
		leaveRequestObject.fromHour = $(row).find("input[name*='fromHour']")
				.val();
		leaveRequestObject.fromMinute = $(row).find(
				"input[name*='fromMinute']").val();
		leaveRequestObject.fromAmPm = $(row).find("select[name*='fromAmPm']")
				.val();
		leaveRequestObject.toDate = $(row).find("input[name*='toDate']").val();
		leaveRequestObject.toDateString = $(row).find("input[name*='toDate']").val();
		leaveRequestObject.toHour = $(row).find("input[name*='toHour']").val();
		leaveRequestObject.toMinute = $(row).find("input[name*='toMinute']")
				.val();
		leaveRequestObject.toAmPm = $(row).find("select[name*='toAmPm']").val();
		leaveRequestObject.leaveType = $(row).find("select[name*='leaveType']")
				.val();
		leaveRequestObject.leaveUnits = $(row).find("select[name*='leaveUnits'] option:selected").prop("label");
		leaveRequestObject.absenceReason = $(row).find("select[name*='absenceReason']").val();
		leaveRequestObject.absenceReasonDescription = $(row).find("select[name*='absenceReason'] option:selected").prop("label");
		leaveRequestObject.leaveHoursDaily = $(row).find("input[name*='leaveHoursDaily']").val().trim();
		if (leaveRequestObject.leaveHoursDaily == "") {
			leaveRequestObject.leaveHoursDaily = "0.000";
		}
		leaveRequestObject.leaveNumberDays = $(row).find("input[name*='leaveNumberDays']").val().trim();
		if (leaveRequestObject.leaveNumberDays == "") {
			leaveRequestObject.leaveNumberDays = "0";
		}
		leaveRequestObject.leaveRequested = $(row).find(
				"span[id*='LeaveRequestedSpan']").text().trim();
		if (leaveRequestObject.leaveRequested == "") {
			leaveRequestObject.leaveRequested = "0.000";
		}
		leaveRequestObject.requestComment = $(row).find(
				"input[name*='requestComment']").val();
		leaveRequestsArray.push(leaveRequestObject);

	}

	var result = false;
	if (createEditFlag == 'create') {
		ajaxUrl = '/EmployeeAccess/app/leave/createEditLeaveRequest/createRequests';
	} else {
		ajaxUrl = '/EmployeeAccess/app/leave/createEditLeaveRequest/editRequests';
	}
	$.ajax({
		url : ajaxUrl,
		type : "POST",
		async : false,
		data : JSON.stringify({
			leaveRequests : leaveRequestsArray,
			payFrequency : payFrequencyCode}),
		dataType : "json",
		contentType : 'application/json; charset=utf-8',
		cache : false,
		success : function(modelMap) {
			result = true;
			$("#createEditRequestsTable > tbody").find("input,select,div,span,button.tableButton")
					.removeClass("error").removeClass("buttonError").removeAttr("title");
			$("#createEditRequestsDialogDiv").find("div.error p").html("");
			
			if (modelMap.hasFieldErrors=="true") {
				result = false;
				var errors = modelMap.fielderrors;
				$.each(modelMap.fielderrors, function(index, value) {
					if (index.indexOf('requestComment') != -1) {
						var $buttonObj = $("[name='" + index + "']").closest("td").find("button.tableButton");
						$buttonObj.addClass("buttonError").attr("title", value).tooltip({
							tooltipClass : "tooltipError"
						});						
					} else {
						var obj = $("[name='" + index + "']")[0];
						$(obj).addClass("error");
						if (obj.tagName.trim().toUpperCase() == "SELECT") {
							var parent = $(obj).parent()[0];
							if (parent.tagName.trim().toUpperCase() == "SPAN") {
								$(parent).attr("title", value).tooltip({
									tooltipClass : "tooltipError"
								});
							} else {
								$(obj).attr("title", value).tooltip({
									tooltipClass : "tooltipError"
								});
							}
						} else {
							$(obj).attr("title", value).tooltip({
								tooltipClass : "tooltipError"
							});
						}
					}
				});
			}
			if (modelMap.hasGlobalErrors=="true") {
				result = false;
				var errors = modelMap.globalerrors;
				var errorText="";
				$.each(modelMap.globalerrors, function(index, value) {
					errorText = errorText + value + '<br/>';
				});
				$("#createEditRequestsDialogDiv").find("div.error p").html(errorText);
			}
		},
		error : function(xhr, textStatus, errorThrown) {
			alert("request failed " + errorThrown);
			result = false;
		}
	});
	return result;
}

function addRequestRow(event) {
	var $tbody = $('#createEditRequestsTable').find('tbody');
	var tbody = $tbody[0];
	var rows = tbody.rows;
	var numRows = rows.length;
	var newRowNum = $('#createEditRequestsTable').find('tbody')[0].rows.length;

	var $clonedTr = $('#emptyLeaveRequestTable').find("tbody > tr").clone(true,
			true);

	var oldRowNum = 0;
	var trIndex = numRows;

	$clonedTr.find("input[id*='emptyLeaveRequestRow']").attr(
			"id",
			function(inputIndex, idVal) {
				return idVal.replace("emptyLeaveRequestRow",
						"editLeaveRequests").replace(oldRowNum, trIndex + "");
			});
	$clonedTr.find("input[name*='emptyLeaveRequestRow']").attr(
			"name",
			function(inputIndex, nameVal) {
				return nameVal.replace("emptyLeaveRequestRow",
						"editLeaveRequests").replace(oldRowNum, trIndex + "");
			});
	$clonedTr.find("select[id*='emptyLeaveRequestRow']").attr(
			"id",
			function(selectIndex, idVal) {
				return idVal.replace("emptyLeaveRequestRow",
						"editLeaveRequests").replace(oldRowNum, trIndex + "");
			});
	$clonedTr.find("select[name*='emptyLeaveRequestRow']").attr(
			"name",
			function(selectIndex, nameVal) {
				return nameVal.replace("emptyLeaveRequestRow",
						"editLeaveRequests").replace(oldRowNum, trIndex + "");
			});
	$clonedTr.find("span[id*='emptyLeaveRequestRowLeaveRequestedSpan']").attr(
			"id",
			function(spanIndex, idVal) {
				return idVal.replace("emptyLeaveRequestRow",
						"editLeaveRequests").replace(oldRowNum, trIndex + "");
			});
	$clonedTr.find("span[id*='emptyLeaveRequestRowLeaveUnitSpan']").attr(
			"id",
			function(spanIndex, idVal) {
				return idVal.replace("emptyLeaveRequestRow",
						"editLeaveRequests").replace(oldRowNum, trIndex + "");
			});
	
	$clonedTr.find("input.rowNumber").val(trIndex);
	
	
	$('#createEditRequestsTable').find('tbody').append($clonedTr);

	$clonedTr.find("input[name*='fromDate'],input[name*='toDate']").each(			
			function(index, obj) {
				var obj2 = this;

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
	initDatePicker("editLeaveRequests",true, true);

	$( "#createEditRequestsDialogDiv" ).dialog( "open" );
	if (event) {
		// the presense of the event object indicates the function was called by the user clicking on the "Add Row" button 
		// ... as opposed to this function being called programatically; set the focus to the start date in the added row
		$('#createEditRequestsTable').find("input[name*='fromDate']:last").focus().delay(500).datepicker("hide");
	}
}

function deleteRequestRow(event) {
	var zobj = $("#deleteRequestRowButton")[0];
	if ($("#deleteRequestRowButton").prop("disabled") == false) {

		$('#createEditRequestsTable tbody tr')
				.filter(':has(:checkbox:checked)').remove();
		$("#createEditRequestsTable").find("[id='selectall']").prop("checked",
				false);
		$("#deleteRequestRowButton").prop("disabled", true);
		if ($('#createEditRequestsTable tbody tr').length == 0) {
			addRequestRow();
		} else {
			// adjust row numbers
			$("#createEditRequestsTable > tbody > tr").each(
					function(trIndex) {
						var oldRowNum = $(this).find("input.rowNumber").val();
						$(this).find("input[id*='editLeaveRequests']").attr(
								"id",
								function(inputIndex, idVal) {
									return idVal.replace(oldRowNum, trIndex
											+ "");
								});
						$(this).find("input[name*='editLeaveRequests']").attr(
								"name",
								function(inputIndex, nameVal) {
									return nameVal.replace(oldRowNum, trIndex
											+ "");
								});
						$(this).find("select[id*='editLeaveRequests']").attr(
								"id",
								function(selectIndex, idVal) {
									return idVal.replace(oldRowNum, trIndex
											+ "");
								});
						$(this).find("select[name*='editLeaveRequests']").attr(
								"name",
								function(selectIndex, nameVal) {
									return nameVal.replace(oldRowNum, trIndex
											+ "");
								});
						$(this).find(
							"span[id*='editLeaveRequestsLeaveRequestedSpan']")
							.attr(
								"id",
								function(spanIndex, idVal) {
									return idVal.replace(oldRowNum,
											trIndex + "");
								});
						$(this).find(
								"span[id*='editLeaveRequestsLeaveUnitSpan']")
								.attr(
										"id",
										function(spanIndex, idVal) {
											return idVal.replace(oldRowNum,
													trIndex + "");
										});
						$(this).find("input.rowNumber").val(trIndex);

						// re-initialize datepicker for from/to dates
						reInitDatePicker(this, true);
					});
		}
	}
}

function editLeaveRequests(event) {

	if ($("#resultsTable .case:checked").length > 0) {
		$("#createEditRequestsDialogDiv").find("div.error p").html("");
		$("#userAction").val("edit");
		$("div.request_dialog_buttons_div").hide();
		$(".createTableCol").hide();
		var d = $('#createEditRequestsDialogDiv').dialog({
			title : "Edit Leave Requests",
			width : 'auto',
			minWidth : '1270px',
			width : '1270px',
			modal : true,
			open : function() {
				// on the edit dialog, the first field has a datepicker which
				// automatically shows the datepicker widget ...
				// need to hide it
				$(".hasDatepicker").datepicker("hide");
			},
			position : ({
				my : "center top",
				at : "center top+100px",
				of : window
			}),
			buttons : {
				OK : {
					text : "Resubmit for Approval",
					tabindex : "1",
					click : function() {
						var success = jsonSubmitCreateLeaveRequests('edit');
						// submitCreateLeaveRequests();
						if (success) {
							submitPayrollChange();
							d.dialog('close');
						}
					}
				},
				Cancel : {
					text : "Cancel",
					tabindex : "1",
					click : function() {
						d.dialog('close');
					},
					keydown : function(event) {
						var charCode = (event.which) ? event.which : event.keyCode;
						if (charCode == 9 && !event.shiftKey) {
							$("input[name='editLeaveRequests[0].fromDate']").focus();	
							return false;
						}
					}
				}
			}
		});

		// remove any rows that may be in the table
		$('#createEditRequestsTable').find('tbody').empty();

		// insert rows to edit corresponding to selected rows in the
		// resultsTable table
		$('#resultsTable tbody tr').filter(':has(:checkbox:checked)').each(
				function(trIndex) {
					var tr = this;
					addRequestRow();
					var requestId = $(tr).find("input[id*='id']").val();
					var fromDate = $(tr).find("td:nth-child(2)").text().trim()
							.replace(/\//g, "-");
					var toDate = $(tr).find("td:nth-child(3)").text().trim()
							.replace(/\//g, "-");
					var fromTime = $(tr).find("td:nth-child(4)").text().trim();
					var fromHour = fromTime
							.substr(fromTime.indexOf(":") - 2, 2);
					var fromMinute = fromTime.substr(fromTime.indexOf(":") + 1,
							2);
					var toTime = $(tr).find("td:nth-child(5)").text().trim();
					var toHour = toTime.substr(toTime.indexOf(":") - 2, 2);
					var toMinute = toTime.substr(toTime.indexOf(":") + 1, 2);

					var leaveType = $(tr).find("input[id*='leaveType']").val();
					var absenceReason = $(tr).find("input[id*='absenceReason']").val();
					var leaveHoursDaily = $(tr).find("input[id*='leaveHoursDaily']").val();
					var leaveNumberDays = $(tr).find("input[id*='leaveNumberDays']").val()
					var leaveRequestedAndUnitsStr = $(tr).find("td:nth-child(8)").text().trim();
					var splitStr = leaveRequestedAndUnitsStr.split(" ");
					var leaveRequested = splitStr[0].trim();
					// use splitStr.length-1 as the index for the units instead of the number 1 since 
					// there might be multiple spaces between the leave requested decimal number and the units
					var leaveUnitsDescr = splitStr[splitStr.length-1].trim(); // use 
//					var leaveRequested = $(tr).find("td:nth-child(8)").text()
//							.trim();
//					var leaveUnitsDescr = $(tr).find("td:nth-child(9)").text()
//							.trim();
					var requestComment = $(tr).find(
							"input[id*='requestComment']").val();
					var $editTr = $('#createEditRequestsTable tr:nth-child('
							+ (trIndex + 1) + ')');

					$editTr.find("input[name*='id']").val(requestId);
					$editTr.find("input[name*='fromDate']").attr('current-value',fromDate).datepicker(
							"setDate", fromDate).datepicker("option",
							"maxDate", toDate).datepicker("hide");
					$editTr.find("input[name*='fromHour']").val(fromHour);
					$editTr.find("input[name*='fromMinute']").val(fromMinute);
					$editTr.find("select[name*='fromAmPm']").val(
							(fromTime.indexOf("AM") != -1) ? "AM" : "PM");
					$editTr.find("input[name*='toDate']").attr('current-value',toDate).datepicker("setDate",
							toDate).datepicker("option", "minDate", fromDate)
							.datepicker("hide");
					$editTr.find("input[name*='toHour']").val(toHour);
					$editTr.find("input[name*='toMinute']").val(toMinute);
					$editTr.find("select[name*='toAmPm']").val(
							(toTime.indexOf("AM") != -1) ? "AM" : "PM");
					$editTr.find("select[name*='leaveType']").val(leaveType);
					// $editTr.find("select[name*='leaveType']").trigger("change");
					// jsonAdjustLeaveTypeAbsenceReasonOptions($editTr.find("select[name*='leaveType']")[0],
					// false);
					$editTr.find("select[name*='absenceReason']").val(
							absenceReason);
					// $editTr.find("select[name*='absenceReason']").trigger("change");
					var absenceReasonSelectObj = $editTr
							.find("select[name*='absenceReason']")[0];
					jsonAdjustLeaveTypeAbsenceReasonOptions(
							absenceReasonSelectObj, false);
					$editTr.find("input[name*='leaveHoursDaily']").val(
							leaveHoursDaily);
					$editTr.find("input[name*='leaveNumberDays']").val(
							leaveNumberDays);
					$editTr.find("span[id*='LeaveRequestedSpan']").text(
							leaveRequested);
					$editTr.find("span[id*='LeaveUnitSpan']").text(
							leaveUnitsDescr);
					$editTr.find("input[name*='requestComment']").val(
							requestComment);
					if (requestComment!=null && requestComment!="") {
						// add paperclip to button label
						var commentImage = $("#commentEnteredImage").clone().css("display","inline");
						$editTr.find(".tableButton").prepend("<span class='commentLabelSpacer'>&nbsp;</span>").prepend(commentImage);
					}
				});

		d.dialog('open');
		$('#createEditRequestsTable').find("input[name*='fromDate']:first").focus().datepicker("hide");;
	}

}

function deleteLeaveRequests(event) {
	if ($("#resultsTable .case:checked").length > 0) {
		var d = $('#deleteConfirmationDiv')
				.dialog(
						{
							title : "Confirm Delete",
							width : 'auto',
							minWidth : '200px',
							modal : true,

							position : ({
								my : "center top",
								at : "center top+100px",
								of : window
							}),
							buttons : {
								"Delete Requests" : function() {
									var leaveRequestsArray = new Array();
									var $checkedTrRows = $(
											"#resultsTable .case:checked")
											.closest("tr");
									$checkedTrRows.each(function(index) {
										var leaveRequestObject = new Object();
										leaveRequestObject.id = $(this).find(
												"input[name*='id']").val();
										leaveRequestObject.fromDateString = $(this).find(
												"input[name*='fromDateString']").val();
										leaveRequestObject.toDateString = $(this).find(
												"input[name*='toDateString']").val();
										leaveRequestObject.fromTimeString = $(this).find(
												"input[name*='fromTimeString']").val();
										leaveRequestObject.toTimeString = $(this).find(
												"input[name*='toTimeString']").val();
										leaveRequestObject.status = $(this).find(
												"input[name*='status']").val();
										leaveRequestsArray
												.push(leaveRequestObject);
									});

									$.ajax({
												url : '/EmployeeAccess/app/leave/createEditLeaveRequest/deleteRequests',
												type : "POST",
												async : false,
												data : JSON
														.stringify(leaveRequestsArray),
												dataType : "json",
												contentType : 'application/json; charset=utf-8',
												cache : false,
												success : function(modelMap) {
													/* Using global error instead of field error since deletes are entire rows, can't use individual fields. */
													if (modelMap.hasGlobalErrors=="true") {
														var errors = modelMap.globalerrors;
														var errorText="";
														$.each(modelMap.globalerrors, function(index, value) {
															errorText = errorText + value + '<br/>';
														});
														$("#errorDiv").find("div.error p").html(errorText);
														$("#resultsTableTHead").addClass("hidden");
													}
													else {
														submitPayrollChange();
													}
													
													d.dialog('close');
													
												},
												error : function(xhr,
														textStatus, errorThrown) {
													d.dialog('close');
													alert("Delete request failed "
															+ errorThrown);
												}
											});
								},
								Cancel : function() {
									d.dialog('close');
								}
							}
						});

		d.dialog('open');

	}
}

function tabToFirstCheckbox(event) {
	$("#resultsTableTBody input[name*='selected']:first").focus();
	return false;		
	
}

function tabToWrapField(event) {
	var count = $("#resultsTableTBody input[name*='selected']").length;
	
	if (count>0) {
		if (event.target.tagName.trim().toUpperCase() == "INPUT") {
			$(".wrap_field").focus();
			return false;
		} else {
			return true;
		}
	} else {
		// user is tabbing from the add(create) request button... since the count is zero, always wrap
//		if ($(event.target).prop("id")=='createRequestsButton') {
//			if ($("#editRequestsButton").prop("disabled")) {
				$(".wrap_field").focus();
				return false;
//			} else {
//				return true;
//			}
//		}	
	}
	return true;
}

