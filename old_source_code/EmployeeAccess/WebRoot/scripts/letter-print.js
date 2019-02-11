var immSelected = false;
var refSelected = false;
var scrSelected = false;

function updateLetterTemplates(letterType) {
	letterPrintController.getLetterTemplates(letterType, function(data) {
		var fieldId = 'letterType';
		var valueText = 'type';
		var nameText = 'description';
		
		//var select = $("#letterType");
		dwr.util.removeAllOptions(fieldId);// step 1
		dwr.util.addOptions(fieldId, data, valueText, nameText);// step 2
	});
}

function doLetterParameterSelection(source) {
	var checked = $(source).attr('checked');

	if ($(source).attr('id') == 'imm') {
		if ($("#imm").attr('checked')) {
			$("#dtp").attr('checked', 'true');
			$("#mmr").attr('checked', 'true');

			$("#polio").attr('checked', 'true');
			$("#hib").attr('checked', 'true');
			$("#hepatitisA").attr('checked', 'true');
			$("#hepatitisB").attr('checked', 'true');
			$("#varicella").attr('checked', 'true');
			$("#pcv7").attr('checked', 'true');
			$("#mcv4").attr('checked', 'true');
		} else {
			$("#dtp").attr('checked', '');
			$("#mmr").attr('checked', '');

			$("#polio").attr('checked', '');
			$("#hib").attr('checked', '');
			$("#hepatitisA").attr('checked', '');
			$("#hepatitisB").attr('checked', '');
			$("#varicella").attr('checked', '');
			$("#pcv7").attr('checked', '');
			$("#mcv4").attr('checked', '');
		}
	}

	if ($(source).attr('id') == 'imm' || $(source).attr('id') == 'dtp') {
		if ($("#dtp").attr('checked')) {
			$("#diptheria").attr('checked', 'true');
			$("#tetanus").attr('checked', 'true');
			$("#pertussis").attr('checked', 'true');
		} else {
			$("#diptheria").attr('checked', '');
			$("#tetanus").attr('checked', '');
			$("#pertussis").attr('checked', '');
		}
	}

	if ($(source).attr('id') == 'imm' || $(source).attr('id') == 'mmr') {
		if ($("#mmr").attr('checked')) {
			$("#measles").attr('checked', 'true');
			$("#mumps").attr('checked', 'true');
			$("#rubella").attr('checked', 'true');
		} else {
			$("#measles").attr('checked', '');
			$("#mumps").attr('checked', '');
			$("#rubella").attr('checked', '');
		}
	}

	// screening
	if ($(source).attr('id') == 'spinalScreening' || $(source).attr('id') == 'acanthosisScreening') {

		if (checked == true) {
			!$("#spinalScreening").attr('checked', '');
			!$("#acanthosisScreening").attr('checked', '');

			$(source).attr('checked', 'true');

			disableImmunizations('true');
			disableReferrals('true');
			
			// set the drop down
			if ($(source).attr('id') == 'spinalScreening') {
				updateLetterTemplates('SSPINL');
			} else if ($(source).attr('id') == 'acanthosisScreening') {
				updateLetterTemplates('SANTES');
			}
		} else if (!$("#spinalScreening").attr('checked') && !$("#acanthosisScreening").attr('checked')) {
			disableImmunizations('');
			disableReferrals('');
		}
	// referral
	} else if ($(source).attr('id') == 'hearing' || $(source).attr('id') == 'spinalReferral'
			|| $(source).attr('id') == 'acanthosisReferral' || $(source).attr('id') == 'vision') {

		if (checked == true) {
			!$("#hearing").attr('checked', '');
			!$("#spinalReferral").attr('checked', '');
			!$("#acanthosisReferral").attr('checked', '');
			!$("#vision").attr('checked', '');

			$(source).attr('checked', 'true');

			disableImmunizations('true');
			disableScreenings('true');
			
			// set the drop down
			if ($(source).attr('id') == 'hearing') {
				updateLetterTemplates('RHEAR');
			} else if ($(source).attr('id') == 'spinalReferral') {
				updateLetterTemplates('RSPINL');
			} else if ($(source).attr('id') == 'acanthosisReferral') {
				updateLetterTemplates('RANTES');
			} else if ($(source).attr('id') == 'vision') {
				updateLetterTemplates('RVISN');
			}
		} else if (!$("#hearing").attr('checked') && !$("#spinalReferral").attr('checked')
				&& !$("#acanthosisReferral").attr('checked') && !$("#vision").attr('checked')) {
			disableImmunizations('');
			disableScreenings('');
		}
	// immunization
	} else {
		if (checked == true) {
			updateLetterTemplates('IMMUN');
			disableReferrals('true');
			disableScreenings('true');
		} else if (!$("#imm").attr('checked') && !$("#imm").attr('checked') && !$("#dtp").attr('checked')
				&& !$("#diptheria").attr('checked') && !$("#tetanus").attr('checked') && !$("#pertussis").attr('checked')
				&& !$("#polio").attr('checked') && !$("#hib").attr('checked') && !$("#hepatitisA").attr('checked')
				&& !$("#hepatitisB").attr('checked') && !$("#varicella").attr('checked') && !$("#pcv7").attr('checked')
				&& !$("#mcv4").attr('checked') && !$("#mmr").attr('checked') && !$("#measles").attr('checked')
				&& !$("#mumps").attr('checked') && !$("#rubella").attr('checked')) {
			disableReferrals('');
			disableScreenings('');
		}
	}
}

function disableImmunizations(disabled) {
	$("#imm").attr('checked', '');
	$("#imm").attr('disabled', disabled);

	$("#dtp").attr('checked', '');
	$("#dtp").attr('disabled', disabled);

	$("#diptheria").attr('checked', '');
	$("#diptheria").attr('disabled', disabled);
	$("#tetanus").attr('checked', '');
	$("#tetanus").attr('disabled', disabled);
	$("#pertussis").attr('checked', '');
	$("#pertussis").attr('disabled', disabled);

	$("#polio").attr('checked', '');
	$("#polio").attr('disabled', disabled);
	$("#hib").attr('checked', '');
	$("#hib").attr('disabled', disabled);
	$("#hepatitisA").attr('checked', '');
	$("#hepatitisA").attr('disabled', disabled);
	$("#hepatitisB").attr('checked', '');
	$("#hepatitisB").attr('disabled', disabled);
	$("#varicella").attr('checked', '');
	$("#varicella").attr('disabled', disabled);
	$("#pcv7").attr('checked', '');
	$("#pcv7").attr('disabled', disabled);
	$("#mcv4").attr('checked', '');
	$("#mcv4").attr('disabled', disabled);

	$("#mmr").attr('checked', '');
	$("#mmr").attr('disabled', disabled);

	$("#measles").attr('checked', '');
	$("#measles").attr('disabled', disabled);
	$("#mumps").attr('checked', '');
	$("#mumps").attr('disabled', disabled);
	$("#rubella").attr('checked', '');
	$("#rubella").attr('disabled', disabled);
}

function disableReferrals(disabled) {
	$("#hearing").attr('checked', '');
	$("#hearing").attr('disabled', disabled);
	$("#spinalReferral").attr('checked', '');
	$("#spinalReferral").attr('disabled', disabled);
	$("#acanthosisReferral").attr('checked', '');
	$("#acanthosisReferral").attr('disabled', disabled);
	$("#vision").attr('checked', '');
	$("#vision").attr('disabled', disabled);
}

function disableScreenings(disabled) {
	$("#spinalScreening").attr('checked', '');
	$("#spinalScreening").attr('disabled', disabled);
	$("#acanthosisScreening").attr('checked', '');
	$("#acanthosisScreening").attr('disabled', disabled);
}

function disableWarning() {
	UnsavedDataWarning.disable();
}

dojo.addOnLoad(disableWarning);