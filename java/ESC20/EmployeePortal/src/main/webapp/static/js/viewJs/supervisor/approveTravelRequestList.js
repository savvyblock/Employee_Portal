
$(document).ready(function() {
	
	console.log("JS");
	
		/** 
	 * Open Approved Request pop up in case of being the last approver for transaction(s)
	 * 
	 * method openPopup()
	 *  
	 */
	
	if($('#showFinalApproverPopup').val() === "true"){
		$("#approvedRequestPopup").modal('show');
	}
	
	$('#showFinalApproverPopup').on('hidden.bs.modal', function () {
	    location.reload();
	});

});


/**
 * 
 * this function select all or deselect all checkboxes based on a css class
 * 
 * @param className css class that apply for all the checkboxes group
 * @param elem 'select All' checkbox element
 * 
 * 
 */

function selectAllCheckboxes(className, elem, anySelectedElem) {
	if ($(elem).is(':checked')) {
		$('.' + className).prop('checked', true);
		$('#'+anySelectedElem).val(true);
	} else {
		$('.' + className).prop('checked', false);
		$('#'+anySelectedElem).val(false);
	}
};


/**
 * 
 * This function select or deselect the 'select All' checkbox based on the checkboxes group behavior
 * 
 * @param selectAllId 'select All' checkbox is
 * @param elem individual checkbox 
 * @param elemClass css class for all checkboxes group that is being validated
 * @param anySelectedElem value equals to true if at least 1 got selected 
 * 
 * 
 */

function selectCheckbox(selectAllId, elem, elemClass, anySelectedElem) {

	if ($('#'+selectAllId).is(':checked')) {
		if ($(elem).not(':checked')) {
			$('#'+selectAllId).prop('checked', false);
		}
		
	}else if($('.'+elemClass).not(':checked').length == 0){
		$('#'+selectAllId).prop('checked', true);
	}
	
	
	if($('.'+elemClass).is(':checked')){
		$('#'+anySelectedElem).val(true);
	}else{
		$('#'+anySelectedElem).val(false);
	}
	
	
};

/**
 * Method for detail view of request 
 * 
 * method 
 * 
 * 
 */

function showDetailTrvlRequest(tripNbr, empNbr, overnight) {
	if (tripNbr != null) {
		var overnightTrip = false;
		if (overnight === 'Y') {
			overnightTrip = true;
		}
		var payload = JSON.stringify({
			'tripNbr' : tripNbr,
			'empNbr' : empNbr,
			'overnightTrip' : overnightTrip
		})
		var gridForMileage = new Array();
		gridForMileage.push(payload);
		var gridForMileageObj = {
			gridForMileage : gridForMileage
		}
		$.ajax({
			type : "POST",
			url : urlMain + "/travelRequest/travelRequestAdd",
			dataType : 'JSON',
			async : false,
			processData : false,
			contentType : 'application/json;charset=UTF-8',
			data : JSON.stringify(gridForMileageObj),
			cache : false,
			complete : function(xhr) {
				if (xhr.status === 200) {
					window.location.href = urlMain + '/travelRequest/travelRequestMapper';
				} else {
					console.log(xhr.status);
				}
			}
		})
	}
}

