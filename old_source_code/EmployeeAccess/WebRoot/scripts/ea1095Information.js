$(document).ready(function() {

	// Display the 1095ElecConsent Pop-up on-load of 1095 Information screen
	if ($("#ea1095ElecConsntFlag").val() == '' && $('#showEA1095ElecConsntPopup').val() != 'false' && $('#enableElecConsnt1095').val() == 'true') {
		displayEA1095ElecConsntPopup();
	}
	
	if($("#showReportInNewWindow").val() != 'false'){
		showEA1095Report();
	}
});

/***************************************************************************************
 * Functionality to display the 1095 Electronic Consent pop-up
 ***************************************************************************************/
function displayEA1095ElecConsntPopup() {
	$("#updateMsg").addClass("hidden");
	// Check for 1095 Electronic Consent message
	if ($("#ea1095ElecConsntMsg").val() != null) {
		if ($("#ea1095ElecConsntFlag").val() == 'Y') {
			$("#yesToElecAccess").val('true');
			$('#yesToElecAccess').attr('checked', true);
		} else if ($("#ea1095ElecConsntFlag").val() == 'N') {
			$("#noToElecAccess").val('true');
			$('#noToElecAccess').attr('checked', true);
		} 
		ea1095ElecConsntPopup();
	}
};

/***************************************************************************************
 * Functionality to define 1095 Electronic Consent modal pop-up
 ***************************************************************************************/
function ea1095ElecConsntPopup() {
	var ea1095ElecConsntMsg = $("#ea1095ElecConsntMsg").val();
	$("#dialog:ui-dialog").dialog("destroy");
	// Set the ACA Electronic Consent message to the pop-up.
	$("#ea1095ElecConsntPopup").val(ea1095ElecConsntMsg);

	$("#dialog-message-EA1095ConsentPopup").dialog( 
		{
			modal: true,
			resizable: false,
			width: 700,
			height: 450,
			closeOnEscape: false, 
			position: { my: "center", at: "center", of: window },
			open: function(event, ui) { $(".ui-dialog-titlebar-close").hide(); $("#yesToElecAccess").focus()},
			buttons: {
				Cancel : function() {
					closeButtonEA1095ConsentPopup();
				},
			}
		}
	);
};

/***************************************************************************************
 * Functionality to toggle between YES/NO options in the 1095 Electronic Consent pop-up
 ***************************************************************************************/
function toggleOptions(id, value) {
	if (id == 'yesToElecAccess') {
		// Un-check the NO Radio button
		$("#noToElecAccess").val('false');
		$('#noToElecAccess').attr('checked', false);
		// Toggle the value of the YES radio button
		if (value == 'false') {
			$("#yesToElecAccess").val('true');
			$('#yesToElecAccess').attr('checked', true);
			$("#ea1095ElecConsnt").val('Y');
		} else if (value == 'true') {
			$("#yesToElecAccess").val('false');
			$('#yesToElecAccess').attr('checked', false);
			$("#ea1095ElecConsnt").val('');
		}

		$("#ea1095ElecConsnt").val('Y');
	}
	else if (id == 'noToElecAccess') {
		// Un-check the YES Radio button
		$("#yesToElecAccess").val('false');
		$('#yesToElecAccess').attr('checked', false);
		// Toggle the value of the NO radio button
		if (value == 'false') {
			$("#noToElecAccess").val('true');
			$('#noToElecAccess').attr('checked', true);
			$("#ea1095ElecConsnt").val('N');
		} else if (value == 'true') {
			$("#noToElecAccess").val('false');
			$('#noToElecAccess').attr('checked', false);
			$("#ea1095ElecConsnt").val('');
		}
	}
};

/*******************************************************************************
 * 1095 Electronic Consent Pop-up - Close BUTTON
 ******************************************************************************/
function closeButtonEA1095ConsentPopup() {

	$("#dialog-message-EA1095ConsentPopup").dialog( "close" );
	$("#dialog:ui-dialog").dialog("destroy");
};

function updateEA1095ElecConsent(id)
{
	var ea1095ElecConsnt = '';
	if (id == 'yesToElecAccess') {
		ea1095ElecConsnt = 'Y';
	} else if (id == 'noToElecAccess') {
		ea1095ElecConsnt = 'N';
	}
	$('#ea1095Consent').click();

	$("#dialog-message-EA1095ConsentPopup").dialog( "close" );
	$("#dialog:ui-dialog").dialog("destroy");
}

/*******************************************************************************
 * 1095 Show Report
 ******************************************************************************/
function showEA1095Report(){
	window.open("/EmployeeAccess/app/viewreport.htm?hidebuttons=true&hidecsv=true", "reportWindow");	
	$("#showReportInNewWindow").val('false');
}