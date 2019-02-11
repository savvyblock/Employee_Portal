$(document).ready(function() {

	// Display the W2Consent Pop-up on-load of W2 Information screen
	if ($("#elecConsntW2Flag").val() == '' && $('#enableElecConsntW2').val() == 'true') {
		displayW2ElecConsntPopup();
	}
});

/***************************************************************************************
 * Functionality to display the W-2 Electronic Consent pop-up
 ***************************************************************************************/
function displayW2ElecConsntPopup() {
	$("#updateMsg").addClass("hidden");
	// Check for W-2 Electronic Consent message
	if ($("#elecConsntMsgW2").val() != null) {
		if ($("#elecConsntW2Flag").val() == 'Y') {
			$("#yesToElecAccess").val('true');
			$('#yesToElecAccess').attr('checked', true);
		} else if ($("#elecConsntW2Flag").val() == 'N') {
			$("#noToElecAccess").val('true');
			$('#noToElecAccess').attr('checked', true);
		} 
		w2ElecConsntPopup();
	}
};

/***************************************************************************************
 * Functionality to define W-2 Electronic Consent modal pop-up
 ***************************************************************************************/
function w2ElecConsntPopup() {
	var elecConsntMsgW2 = $("#elecConsntMsgW2").val();
	$("#dialog:ui-dialog").dialog("destroy");

	// Set the W-2 Electronic Consent message to the pop-up.
	$("#w2ElecConsntPopup").val(elecConsntMsgW2);

	$("#dialog-message-W2ConsentPopup").dialog( 
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
					closeButtonW2ConsentPopup();
				},
			}
		}
	);
};

/***************************************************************************************
 * Functionality to toggle between YES/NO options in the W-2 Electronic Consent pop-up
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
			$("#elecConsntW2").val('Y');
		} else if (value == 'true') {
			$("#yesToElecAccess").val('false');
			$('#yesToElecAccess').attr('checked', false);
			$("#elecConsntW2").val('');
		}

		$("#elecConsntW2").val('Y');
	}
	else if (id == 'noToElecAccess') {
		// Un-check the YES Radio button
		$("#yesToElecAccess").val('false');
		$('#yesToElecAccess').attr('checked', false);
		// Toggle the value of the NO radio button
		if (value == 'false') {
			$("#noToElecAccess").val('true');
			$('#noToElecAccess').attr('checked', true);
			$("#elecConsntW2").val('N');
		} else if (value == 'true') {
			$("#noToElecAccess").val('false');
			$('#noToElecAccess').attr('checked', false);
			$("#elecConsntW2").val('');
		}
	}
};

/*******************************************************************************
 * W-2 Electronic Consent Pop-up - Close BUTTON
 ******************************************************************************/
function closeButtonW2ConsentPopup() {

	$("#dialog-message-W2ConsentPopup").dialog( "close" );
	$("#dialog:ui-dialog").dialog("destroy");
};

function updateW2ElecConsent(id)
{
	var elecConsntW2 = '';
	if (id == 'yesToElecAccess') {
		elecConsntW2 = 'Y';
	} else if (id == 'noToElecAccess') {
		elecConsntW2 = 'N';
	}
	UnsavedDataWarning.forceCleanTransient();
	Spring.remoting.submitForm(elecConsntW2,'mainForm',{ _eventId : 'w2Consent' });

	$("#dialog-message-W2ConsentPopup").dialog( "close" );
	$("#dialog:ui-dialog").dialog("destroy");
}