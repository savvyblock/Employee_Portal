/**
 * 
 */

$.ajaxSetup({
	cache : false
});

$(document).ready(function() {

//	$(".hover-container").mouseenter(function() {
//		$(".hover-content").scrollTop(0);
//		$(".hover-content").scrollLeft(0);
//	});

	if (Number($("#supervisorChainLevel").val()) > 0) {
		$("#prevLevel").prop("disabled", false);
	}
	$("#supervisorViewDiv span:last").addClass("important");
	
	if ($("input:checked").hasClass("radioapprove") || $("input:checked").hasClass("radioreject")) {
		$("#saveApproverActionsButton").removeAttr("disabled");
	} else {
		$("#saveApproverActionsButton").attr("disabled", "disabled");
	}
	
	// add paper clip to supervisor comment buttons if a comment exists
	$("button.tableButton").each(function(index){
		if ($(this).closest("td").find("input").val().length > 0) {
			var commentImage = $("#commentEnteredImage").clone().css("display","inline");
			$(this).prepend("<span class='commentLabelSpacer'>&nbsp;</span>").prepend(commentImage);
		}
	});
	
	// this resize is related to the processing in the noScrollTableHeader()
	// resizeFixed callback
	// to prvent the div horizontal scrollbar from appearing in favor of the
	// browser horizontal scrollbar;
	$(window).trigger('resize');

	$(".radionoaction:last").addClass("last_field");
	$(".wrap_field").focus();

});

function previousSupervisorLevel (event) {
	$('#leaveRequestSubmittalsForm').attr('method', 'POST');
	$('#leaveRequestSubmittalsForm').attr('action', '/EmployeeAccess/app/leave/leaveRequestSubmittals/prevSupervisorLevel');
	$('#leaveRequestSubmittalsForm').submit();
	return false;
}

function nextSupervisorLevel (event) {
	$('#leaveRequestSubmittalsForm').attr('method', 'POST');
	$('#leaveRequestSubmittalsForm').attr('action', '/EmployeeAccess/app/leave/leaveRequestSubmittals/nextSupervisorLevel');
	$('#leaveRequestSubmittalsForm').submit();
	return false;
}

function approverActionClicked(event) {
	if ($(event.target).hasClass("radioapprove")) {
		$("#saveApproverActionsButton").removeAttr("disabled");
		// remove error class and title from comment button ... enable the button since Approve was selected
		$(event.target).closest("tr").find("button.tableButton").removeAttr("disabled").removeClass("buttonError").removeAttr("title");
		// remove error class and title from the disapprove span
		$(event.target).closest("td").find("span.disapproveSpan").removeClass("error").removeAttr("title");
	} else if ($(event.target).hasClass("radioreject")) {
		$("#saveApproverActionsButton").removeAttr("disabled");
		$(event.target).closest("tr").find("button.tableButton").removeAttr("disabled");
//		$(event.target).closest("tr").find("button.tableButton").click();  ... do not force the comment dialog to appear
		// remove error class and title from the approve span
		$(event.target).closest("td").find("span.approveSpan").removeClass("error").removeAttr("title");
	} else if ($(event.target).hasClass("radionoaction")) {
		var $commentButton = $(event.target).closest("tr").find("button.tableButton");
		// remove error class and title from comment button ... disable the button since No Action selected
		$commentButton.removeClass("buttonError").removeAttr("title").attr("disabled", "disabled");
		// remove error class and title from the approve and disapprove spans
		$(event.target).closest("td").find("span.approveSpan").removeClass("error").removeAttr("title");
		$(event.target).closest("td").find("span.disapproveSpan").removeClass("error").removeAttr("title");
		// clear any comments and remove paper clip image
		$(event.target).closest("tr").find("input[id*='approverComment']").val("");
		if ($commentButton.find("img").length > 0) {
			$commentButton.find("img").remove();
			$commentButton.find("span.commentLabelSpacer").remove();
		}
		
//		$(event.target).closest("tr").find("button.tableButton").attr("disabled", "disabled");
		
		// check if Save button should be enabled/disabled
		if ($("input:checked").hasClass("radioapprove") || $("input:checked").hasClass("radioreject")) {
			$("#saveApproverActionsButton").removeAttr("disabled");
		} else {
			$("#saveApproverActionsButton").attr("disabled", "disabled");
		}
	}
}

function editApproverComment(event) {
	var required = false;	
// 
// for now... don't force supervisor to enter a comment if the request was disapproved
//	if ($(event.target).closest("tr").find(".radioreject:checked").length > 0) {
//		required = true;
//	}
	editComment(event, 'Supervisor Comment', required);

	// remove error class and title from button if any ... in this situation, clicking on the Button, had to use the plugin "destroy" method
	if ($(event.target).tooltip("instance")) {
		$(event.target).tooltip("destroy");
	}
	$(event.target).removeClass("buttonError").removeAttr("title");
	
//	$(event.target).closest("tr").find("button.tableButton").removeClass("buttonError").attr("title","");
}

function submitLeaveRequestSubmittalsSave(event) {
	var doSave = true;
	$("div.error").find("p").empty();

	// verify comments have been entered for disapproved requests
	$(".radioreject:checked").each(
		function(index) {
			var comment = $(this).closest("tr").find("input[name*='approverComment']").val().trim();
			if (comment=="") {
				doSave = false;
				var buttonObj = $(this).closest("tr").find("button.tableButton")[0];
				$(buttonObj).addClass("buttonError").attr("title", "A comment must be entered for disapproved requests.").tooltip({
					tooltipClass : "tooltipError"
				});
				// aldo display the error at the top of the page
				if ($("div.error").find("p").length==0) {
					$("div.error").append("<p></p>");
				} 
				$("div.error").find("p").append("A comment must be entered for disapproved requests.<br/>");
			}
		}
	);
	
	if (doSave) {
		$('#leaveRequestSubmittalsForm').attr('method', 'POST');
		$('#leaveRequestSubmittalsForm').attr('action', '/EmployeeAccess/app/leave/leaveRequestSubmittals/save');
		$('#leaveRequestSubmittalsForm').submit();
	}
	return false;
}

function showLeaveBalancesDetail(event) {
	var offsetObj = $(event.target).offset();
	var $tr = $(event.target).closest("tr");
	var empNumber = $tr.find("input[name*='employeeNumber']").val();
	var empLabel = $tr.find("input[name*='employeeLabel']").val();
	var payFrequency = $tr.find("input[name*='payFrequency']").val();
	$('#leaveBalancesDetail').find("span#employeeLabel").text(empLabel);
	$('#leaveBalancesDetail').find("span#payFrequency").text(payFrequency);
	
	if (empNumber!="") {
		$.ajax({
			url : "/EmployeeAccess/app/leave/leaveUtilities/getLeaveBalances",
			type : "GET",
			async : false,
			data : {
				employeeNumber : empNumber,
				payFrequency : payFrequency
			},
			dataType : "json",
			contentType : 'application/json; charset=utf-8',
			cache : false,
			success : function(modelMap) {
				$('#leaveBalancesDetail').find("span#payFrequency").text(modelMap.payFrequencyDescription);
				var leaveBalances = modelMap.leaveBalances;
				var $tbody = $("#leaveBalancesTable").find("tbody");
				$tbody.empty();
				if (leaveBalances.length==0) {
					$tbody.append("<tr><td colspan='8' style='text-align: center;'>No Rows</td></tr>");
				} else {
					$.each(modelMap.leaveBalances, function(index, leaveInfo) {
//						var balance = Number(leaveInfo.beginBalance)+Number(leaveInfo.advancedEarned)+Number(leaveInfo.pendingEarned)-Number(leaveInfo.used)
//						var availableBalance = balance - Number(leaveInfo.pendingUsed) - Number(leaveInfo.pendingApproval); 
						var totalPendingUsed = Number(leaveInfo.pendingUsed) + Number(leaveInfo.pendingApproval) + Number(leaveInfo.pendingPayroll); 
						var availableBalance = 0;
						if (leaveInfo.subtractFromBalanceValue == 'S') {
							availableBalance = Number(leaveInfo.beginBalance) + Number(leaveInfo.advancedEarned) - Number(leaveInfo.used) + Number(leaveInfo.pendingEarned) - Number(leaveInfo.pendingUsed) - Number(leaveInfo.pendingApproval) - Number(leaveInfo.pendingPayroll);
						} else {
							availableBalance = Number(leaveInfo.beginBalance) + Number(leaveInfo.advancedEarned) + Number(leaveInfo.pendingEarned);							
						}
						var col1 = "<td class='leaverequest'>" + leaveInfo.type.displayLabel + "</td>";
						var col2 = "<td class='leaverequest' style='text-align: right;'>" + leaveInfo.beginBalance + "</td>";
						var col3 = "<td class='leaverequest' style='text-align: right;'>" + leaveInfo.advancedEarned + "</td>";
						var col4 = "<td class='leaverequest' style='text-align: right;'>" + leaveInfo.pendingEarned + "</td>";
						var col5 = "<td class='leaverequest' style='text-align: right;'>" + leaveInfo.used + "</td>";
						var col6 = "<td class='leaverequest' style='text-align: right;'>" + totalPendingUsed.toFixed(3) + "</td>";
						var col7 = "<td class='leaverequest' style='text-align: right;'>" + availableBalance.toFixed(3) + "</td>";
						var col8 = "<td class='leaverequest'>HOURS</td>";
						if (leaveInfo.daysHrs=='D') {
							col8 = "<td class='leaverequest'>DAYS</td>";
						}
						$tbody.append("<tr>"+col1+col2+col3+col4+col5+col6+col7+col8+"</tr>");
					});
				}
				
				$('#leaveBalancesDetail').offset({ top: offsetObj.top-5, left: offsetObj.left-5 }).css("visibility", "visible");
			},
			error : function(xhr, textStatus, errorThrown) {
				alert("getLeaveBalancesSummary for employee: " + empNumber + " failed. " + errorThrown);
			}
		});		
	}
	return false;
}

