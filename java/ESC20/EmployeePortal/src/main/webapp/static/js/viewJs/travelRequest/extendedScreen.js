var responseData;

function serverCall(url, method, payload, dataType, contentType, callback) {
	$.ajax({
		type : method,
		url : url,
		data : payload,
		dataType : dataType,
		async : false,
		contentType : contentType,
		success : function(res) {
			responseData = res;
			callback();
		},
		error : function(res) {
			responseData = 'error';
			console.log(res);
		}
	});
}

var indx = 0;
var dindx = 0;
let statesList = [];
getStates();

loadRows();
function loadRows() {
	createGrid();
	createDetailsGrid();
	$(".dateValidator01").hide();
}

function createGrid() {
	var fromZip = "'fromZip'";
	var toZip = "'toZip'";
	var gridData = '<tr id="detailRow_1' + indx + '" class="deleteRow"><td /> <td>Depart: <label id="depart_' + indx + '"></label></td>' +
		'<td></td><td><label class="label-color float-left">' + $('#lblContact1').val() + ':&nbsp;&nbsp;</label>' +
		'<input class="form-control-custom form-rounded border-secondary" style="width:70%" type="text"  id="contact_' + indx + '">' +
		'<div id="contactError_' + indx + '" class="form-group has-error dateValidator01"><small ' +
		'class="help-block" role="alert" aria-atomic="true">' + $('#requiredField').val() + '</small></div>' +
		' </td><td>	<input type="text" class="form-control-custom mr-sm-2 form-rounded border-secondary"' +
		'  style="width:95%" placeholder="Origin Description" id="addressFromLine1_' + indx + '"><div id="addressFromLine1Error_' + indx + '"' +
		' class="form-group has-error dateValidator01">' +
		'<small class="help-block" role="alert" aria-atomic="true">' + $('#requiredField').val() + '</small></div></td>' +
		' <td><input type="text" class="form-control-custom mr-sm-2 form-rounded border-secondary"' +
		' style="width:95%" placeholder="Destination Description" id="addressToLine1_' + indx + '"><div id="addressToLine1Error_' + indx + '"' +
		' class="form-group has-error dateValidator01"><small ' +
		'class="help-block" role="alert" aria-atomic="true">' + $('#requiredField').val() + '</small></div></td> </tr>' +
		'<tr id="detailRow_2' + indx + '"> <td /><td>Return: <label id="return_' + indx + '"></label></td><td /><td><label class="label-color float-left">' + $('#lblPurpose').val() + ':&nbsp;&nbsp;</label>' +
		'<textarea style="border-radius: 15px; border: 1px solid #ced4da; color: #495057; font-size: 14px; padding: 0.375rem 0.75rem; width: 70%;"' +
		' rows="3" cols="19"  id="purpose_' + indx + '" /><div id="purposeError_' + indx + '" class="form-group has-error dateValidator01"><small ' +
		'class="help-block" role="alert" aria-atomic="true">' + $('#requiredField').val() + '</small></div></td><td><input ' +
		'class="form-control-custom mr-sm-2 form-rounded border-secondary" type="text" style="width:95%" placeholder="Origin Address"' +
		' id="addressFromLine2_' + indx + '" ></td> <td><input type="text"' +
		' class="form-control-custom mr-sm-2 form-rounded border-secondary" style="width:95%" placeholder="Destination Address"' +
		' id="addressToLine2_' + indx + '" ></td></tr>' +
		' <tr id="detailRow_3' + indx + '"> <td /> <td /><td /><td /><td><label class="label-color float-left">' + $('#lblCity').val() + ':&nbsp;&nbsp;&nbsp;&nbsp;</label>' +
		'<input class="form-control-custom mr-sm-2 form-rounded border-secondary" type="text" style="width:81%"  id="fromCity_' + indx + '">' +
		'</td> <td><label class="label-color float-left">' + $('#lblCity').val() + ':&nbsp;' +
		'&nbsp;&nbsp;&nbsp;</label><input class="form-control-custom mr-sm-2 form-rounded border-secondary" style="width:81%" type="text"' +
		' id="toCity_' + indx + '"></td></tr>' +
		'<tr id="detailRow_4' + indx + '"> <td /> <td /><td /><td /><td>' +
		' <div class="col-lg-12"><div class="col-lg-5 float-left" style="width: 41%;"><label class="label-color float-left">'
		+ $('#lblState').val() + ':</label>' + formStates('fromState_' + indx + '') +
		' <div id="fromStateError_' + indx + '" class="form-group has-error dateValidator01"><small ' +
		'class="help-block" role="alert" aria-atomic="true">' + $('#requiredField').val() + '</small></div></div>'+
		'<div style="width: 59%;display: flex;"><div style="display:flex">' + $('#lblZip').val() + ':</label><div style="width:77%"><input class="form-control "' +
		
		' type="text" pattern="\d{5}-?(\d{4})?" id="fromZip_' + indx + '" onchange="validateZipField(\'fromZip\', ' + indx + ')" inputmode="numeric" maxlength="10"><div id="fromZipError_' + indx + '" class="form-group has-error dateValidator01">' +
		'<small class="help-block" role="alert" aria-atomic="true">' + $('#notValid').val() + '</small></div></div></div></div>' +
		' <td>' +
		' <div class="col-lg-12"><div class="col-lg-5 float-left" style="width:41%"><label class="label-color float-left">'
		+ $('#lblState').val() + ':</label>' + formStates('toState_' + indx + '') +
		' <div id="toStateError_' + indx + '" class="form-group has-error dateValidator01"><small ' +
		'class="help-block" role="alert" aria-atomic="true">' + $('#requiredField').val() + '</small></div></div>'+
		'<div style="width: 66%;display: inline;"><div style="display:flex;/* width: 59%; */">' + $('#lblZip').val() + ':</label><div style="    width: 79%;"><input class="form-control "' +
		' type="text" pattern="\d{5}-?(\d{4})?" id="toZip_' + indx + '" onchange="validateZipField(\'toZip\', ' + indx + ')" inputmode="numeric" maxlength="10"><div id="toZipError_' + indx + '" class="form-group has-error dateValidator01">' +
		'<small class="help-block" role="alert" aria-atomic="true">' + $('#notValid').val() + '</small></div></div></div></div> </tr>' +
		'<tr id="detailRow_5' + indx + '"><td /><td /><td /><td /><td /><td /></tr>' +
		'<tr id="detailRow_6' + indx + '"> <td /> <td /><td /> <td /><td>' +
		'<a href="" style="color:#666666; text-decoration: underline">Locations</a>' +
		'</td> <td> <div class="col-sm-2">&nbsp;</div> </td> </tr><tr id="detailRow_7' + indx + '"> <td /> <td /><td /><td /><td />' +
		' <td /> </tr>';
	$('#trvlTable').append(gridData);
}
function validateZipField(fieldId, idx) {
	var isValid = true;
	console.log('fieldId:: ', fieldId);
	var fieldValue = $("#" + fieldId + "_" + idx).val();
	console.log('fieldValue:: ', fieldValue);
	if ("fromZip" == fieldId || "toZip" == fieldId) {
		var isValidZip = /(^\d{5}$)|(^\d{5}-\d{4}$)/.test(fieldValue);
		if (fieldValue != undefined && fieldValue != '' && !isValidZip) {
			console.log(' inside error fieldValue:: ', fieldValue);
			$("#" + fieldId + "Error_" + idx).show();
			isValid = false;
		} else {
			$("#" + fieldId + "Error_" + idx).hide();
		}
	}
	return isValid;
}
function createDetailsGrid() {
	var gridData = '<tr id="ddetailRow_1' + dindx + '" class="deleteRow"> <td><label id="travelDate_' + dindx + '"></label></td> <td><div><label class="label-color float-left">' + $('#lblMilage').val() + ':&nbsp;' +
		'&nbsp;</label><input class="form-control-custom mr-sm-2 form-rounded border-secondary usd_input"' +
		' type="number" inputmode="decimal" step=".1" placeholder= "Start" id="mileageBegining_' + dindx + '" style="width:50%"' +
		' onkeyup="calculateMileage(' + dindx + ')"><div id="mileageBeginingError_' + dindx + '" class="form-group has-error dateValidator01"><small ' +
		'class="help-block" role="alert" aria-atomic="true">' + $('#requiredField').val() + '</small></div><div style="color:red"' +
		' id="mileageError_' + dindx + '"></div></div></td><td><div><input class="form-control-custom mr-sm-2 form-rounded border-secondary usd_input float-left"' +
		' type="number" inputmode="decimal" step=".1" placeholder= "Stop" id="mileageEnd_' + dindx + '" style="width:45%"' +
		' onkeyup="calculateMileage(' + dindx + ')"><div id="mileageEndError_' + dindx + '" class="form-group has-error dateValidator01"><small ' +
		'class="help-block" role="alert" aria-atomic="true">' + $('#requiredField').val() + '</small></div></div>' +
		' <div><input class="form-control-custom mr-sm-2 form-rounded border-secondary usd_input float-right" type="number" inputmode="decimal" step=".1"' +
		' placeholder= "Map" id="map_' + dindx + '" style="width:45%" onkeyup="calculateMileage(' + dindx + ')"><div id="mapError_' + dindx + '"' +
		' class="form-group has-error dateValidator01"><small ' +
		'class="help-block" role="alert" aria-atomic="true">' + $('#requiredField').val() + '</small></div></div></td><td><label class="label-color float-left">' + $('#lblRoundTrip').val() + ':&nbsp;&nbsp</label>' +
		'<input class="float-left" type="checkbox" id="isRoundTrip_' + dindx + '" onchange="calculateMileage(' + dindx + ')">' +
		'<label class="label-color float-left">&nbsp;&nbsp;&nbsp;&nbsp;' + $('#lblCommute').val() + ':&nbsp;&nbsp;</label>' +
		'<input class="float-left" type="checkbox" id="isCommute_' + dindx + '" onchange="calculateMileage(' + dindx + ')"></td><td><div class="form-inline">' +
		' <label class="label-color float-left">Tot:&nbsp;&nbsp;</label><label class="label-color float-left" id="total_' + dindx + '"></label></div><div class="float-left"><label class="label-color' +
		' float-left">' + $('#lblMilageRate').val() + ':&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</label>' +
		'<label class="label-color float-right" id="mileageRate_' + dindx + '">0.56</label></div><div class="float-right" /></td><td><div class="float-left"><label class="label-color ' +
		'float-left">' + $('#lblTotMilageAmnt').val() + ':&nbsp;&nbsp;</label><label class="label-color float-right" id="totalMileageRate_' + dindx + '">' +
		'</label></div><div class="float-right" /></td> </tr>' +
		'<tr id="ddetailRow_2' + dindx + '"> <td /><td colspan="2"><label class="label-color float-left">&nbsp;&nbsp;&nbsp;&nbsp;' + $('#lblAccommodations').val() + ':&nbsp;&nbsp;</label>' +
		'<input type="text" class="form-control-custom mr-sm-2 form-rounded border-secondary" style="width:50%"' +
		'  id="accommodations_' + dindx + '"><div id="accommodationsError_' + dindx + '" class="form-group has-error dateValidator01"><small ' +
		'class="help-block" role="alert" aria-atomic="true">' + $('#requiredField').val() + '</small></div></td><td colspan="2">' +
		'<label class="label-color float-left">&nbsp;&nbsp;&nbsp;&nbsp;' + $('#lblDirectBill').val() + ':&nbsp;&nbsp;</label><input type="text"' +
		' class="form-control-custom mr-sm-2 form-rounded border-secondary float-right" style="width:50%" id="directBill_' + dindx + '"><div id="directBillError_' + dindx + '" ' +
		'class="form-group has-error dateValidator01"><small class="help-block" role="alert" aria-atomic="true">' + $('#requiredField').val() + '</small>' +
		'</div></td><td colspan="2"><label class="label-color float-left">' + $('#lblReimburesementAmt').val() + ':&nbsp;&nbsp;</label>' +
		'<input type="text" class="form-control-custom mr-sm-2 form-rounded border-secondary" style="width:45%" id="directBill_' + dindx + '"><div id="directBillError_' + dindx + '" ' +
		'class="form-group has-error dateValidator01"><small class="help-block" role="alert" aria-atomic="true">' + $('#requiredField').val() + '</small>' +
		'</div></td></tr>' +
		'<tr id="ddetailRow_3' + dindx + '"> <td /> <td><label class="label-color float-left">' + $('#lblMeals').val() + ':&nbsp;&nbsp;&nbsp;&nbsp;</label>' +
		'<input class="form-control-custom mr-sm-2 form-rounded border-secondary" type="text" style="width:50%" placeholder= "Breakfast" id="breakfast_' + dindx + '">' +
		'</td> <td><input class="form-control-custom mr-sm-2 form-rounded border-secondary float-left" style="width:45%" placeholder= "Lunch" type="text"' +
		' id="lunch_' + dindx + '"><input class="form-control-custom mr-sm-2 form-rounded border-secondary float-right" placeholder= "Dinner" style="width:45%" type="text"' +
		' id="dinner_' + dindx + '"></td><td colspan="2"><label class="label-color float-left">&nbsp;&nbsp;&nbsp;&nbsp;' + $('#lblMealOverride').val() + ':&nbsp;&nbsp;</label>' +
		'<input class="float-left" type="checkbox" id="mealOverride_' + dindx + '" onchange="calculateMileage(' + dindx + ')"><input class="form-control-custom mr-sm-2 form-rounded border-secondary float-right"' +
		' placeholder= "Override Reason" style="width:50%" type="text" id="overrideReason_' + dindx + '"></td><td><div class="float-left"><label class="label-color ' +
		'float-left">' + $('#lblMealsTotal').val() + ':&nbsp;&nbsp;</label><label class="label-color float-right" id="mealsTotal_' + dindx + '">' +
		'</label></div><div class="float-right" /></td></tr>' +
		'<tr id="ddetailRow_4' + dindx + '"> <td /> <td><label class="label-color float-left">' + $('#lblAdditionalExpenses').val() + ':&nbsp;&nbsp;&nbsp;&nbsp;</label>' +
		'</td> <td><input class="form-control-custom mr-sm-2 form-rounded border-secondary float-left" type="text" style="width:45%" placeholder= "Parking" id="parking_' + dindx + '">' +
		'<input class="form-control-custom mr-sm-2 form-rounded border-secondary float-left" style="width:45%" placeholder= "Taxi" type="text"' +
		' id="taxi_' + dindx + '"></td><td colspan="2"><input class="form-control-custom mr-sm-2 form-rounded border-secondary float-left" placeholder= "Other" style="width:45%" type="text"' +
		' id="other_' + dindx + '"></td><td><div class="float-left"><label class="label-color ' +
		'float-left">' + $('#lblAdditionalExpenseTotal').val() + ':&nbsp;&nbsp;</label><label class="label-color float-right" id="additionalExpenseTotal_' + dindx + '">' +
		'</label></div><div class="float-right" /></td></tr> ' +
		'<tr id="ddetailRow_5' + dindx + '"> <td /> <td></td><td></td><td colspan="2"><a href="" style="color:#666666; text-decoration: underline">' + $('#lblAccntCodes').val() + '&nbsp;&nbsp;</a>' +
		'<div id="accountCodeTotError_' + dindx + '" class="form-group has-error dateValidator01"><small class="help-block" role="alert" ' +
		'aria-atomic="true">' + $('#requiredField').val() + '</small></div><label class="label-color" id="accountCodeTot_' + dindx + '"></label></td>' +
		' <td><label class="label-color"><b>' + $('#lblDailyTotal').val() + '</b>:&nbsp;&nbsp;</label><label class="label-color" ' +
		'id="totReiumbersment_' + dindx + '"></label> </td> </tr>';
	$('#trvlDetailsTable').append(gridData);
}

function getStates() {
	serverCall('/' + ctx + '/travelRequest/getStates', 'GET', null, 'JSON', 'application/json;charset=UTF-8', statesResponse);
}

function statesResponse() {
	if (responseData != 'error') {
		for (var i = 0; i < responseData.result.length; i++) {
			statesList[i] = responseData.result[i].displayLabel;
		}
	} else {
		console.log('error getting states list.');
	}
}

function formStates(id) {
	var selectElement = '<select class="form-control" style="width:71%" id="' + id + '">';
	var optionElements = '';
	for (var i = 0; i < statesList.length; i++) {
		var optionElement = '<option value="' + responseData.result[i].code + '" temp="' + responseData.result[i].code + '-' + responseData.result[i].description  + '" >' + responseData.result[i].code + '-' + responseData.result[i].description + '</option>'
		if (responseData.result[i].code == 'TX') {
			optionElement = '<option value="' + responseData.result[i].code + '" label="' + responseData.result[i].code + '" temp="' + responseData.result[i].code + '-' + responseData.result[i].description  + '" selected>' + responseData.result[i].code + '-' + responseData.result[i].description + '</option>'
		}
		optionElements = optionElements + optionElement;
	}
	selectElement = selectElement + optionElements + '</select>';
	
	
   var script=  "<script>$('#"+id+"').unbind('click').click(function(){$('#"+id+" option:not(:selected)').each(function(){$(this).text($(this).attr('temp'))});$('#"+id+" option:selected').text($(this).val())}).change(function(){$('#"+id+" option:selected').text($(this).val()) })	;</script>";
   console.log(script);
   selectElement = selectElement+script;
   return selectElement;
}