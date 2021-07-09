var inputId = '';
var accountCodeId = '';
var type = '';
var lookupType='';

function closePopup(popupid) {
	$("#" + popupid).dialog("close");
}

$().ready(function() {

	//Function stores the AccountCode input Id when the user clicks the box
	$('.accountCodeBox').click(function(event){
		accountCodeId = this.id;
		$('#lookUpHolder').css('display','');
	});

	// Used in Finance: Postings: Cash Receipt: Contra Account Code lookup.
	$('.contraAccountLookup').click(function() {
		var currentRow = this.id.substring((this.id.lastIndexOf('_') + 1), this.id.length);

		accountCode = $("#AccountCd_" + currentRow).val();
		accountCodeId = "ContraAccountCd_" + currentRow;
		type = 'contra'
		$('#contraAccountCodesDiv').load('showAccountCodes.htm', {'_event_': 'findContraAccounts', 'accountCode': accountCode},
		function(response, status){
			$('#waitImage').css('display', 'none');
			if (status == 'error') {
				showError(response);
			}
			else {
				createDialog ( $("#jobPopup"), {
					title: "Contra Account Codes",
					buttons: {
						Cancel: {
							text: "Cancel",
							"class": "dialogWrap",
							"tabindex": "94",
							click: function() {
								$(this).dialog('close');
							}
						}
					}
				});
			}
		});
	});

	//Function stores which account Code box the user is trying to look up.
	$('.acTD').click(function(event){
		inputId = this.childNodes[0].id;
		type = this.childNodes[0].id;
		
		if(event.which) {
			inputId = this.childNodes[1].id;
			type = this.childNodes[1].id;
		}
	});
	
	$('.acInput').focus(function(event){
		inputId = this.id;
		type = this.id;
	});
	
	$('.acInputCreate').click(function(event){
		inputId = this.id;
		type = this.id;
	});
	
	$('.acInputCreate').focus(function(event){
		inputId = this.id;
		type = this.id;
	});
	
	//Function is called when the user types in one of the account code boxes to see if spacebar was entered. 
	$('.acInput').keydown(function(event){
		type = this.id;
		if(!event)
			var event = window.event;
	
		var keycode='';
		if(event.keyCode) {
			keycode = event.keyCode; //for IE
		}if(event.which) {
			keycode = event.which; //for other browsers
		}
		if(keycode == 113)
			showFunds();
	});
	
	$('.acInputCreate').keydown(function(event){
		type = this.id;
		if(!event)
			var event = window.event;
	
		var keycode='';
		if(event.keyCode) {
			keycode = event.keyCode; //for IE
		}if(event.which) {
			keycode = event.which; //for other browsers
		}
		if(keycode == 113)
			showCreateFunds(this);
	});
	
	$('#lookupFundsButton').click(function(){
		showFunds();
	});
	
	$(".fundLookupBtn").click( function() {
		inputId = $(this).attr("name");
		type = inputId;
		showFunds();
	});
	
	$('.createAccountCodeLookup').click(function(){
		var title = this.id;
		inputId = this.id;
		title = title.replace(/Input/, '')
		title = title.replace(title.charAt(0), title.charAt(0).toUpperCase());
		$('#modalTitleFund').html(title+'s');
		showCreateFunds(this);
	});

	//cs20100401  add blur to populate the interfund due from/to obj.so on the Fund cod tab 
	$('.fundType').blur(function(){
		var fund = $(this).val();
		var row = $(this).attr('row').replace('/', '');
		$.getJSON('showFund.htm', {'_event_':'findObjectSubobject', 'fund':fund}, function(json, callback){
			if(callback == 'success'){
				$('.interDueFrom'+row).val(json.interFundDueFrom);
				$('.interDueTo'+row).val(json.interFundDueTo);
			}
		});
	});

	$('.financeFundLookup').click(function(){
		inputId = $(this).attr('name');

		var applictnId = $('#amendBudgetGLInquiry').val();
		if (applictnId == 'undefined' || applictnId == null) {applictnId = '';}

		var type = this.id;
		var fund = $('.fundInput').val();
		var func = $('.functionInput').val();
		var obj = $('.objectInput').val();
		var sobj = $('.subobjectInput').val();
		var org = $('.organizationInput').val();
		var year = $('.yearInput').val();
		var prog = $('.programInput').val();
		var edSpan = $('.educationInput').val();
		var projDetail = $('.projectInput').val();
		var name = type.replace(type.charAt(0), type.charAt(0).toUpperCase());
		$('#fundsTable').load('showFund.htm', {'_event_':'findFinanceFunds', 'type':type, 'fund': fund, 'function': func, 'object': obj, 'subobject': sobj, 'organization': org, 
			'year': year, 'program': prog, 'education': edSpan, 'project': projDetail, 'applictnId': applictnId}, function(){
			createDialog ( $("#fundsPopup"), {
				title: name + 's',
				close: function(dialog) {
					$(dialog).find(".searchBox").val("");
				},
				buttons: {
					Cancel: {
						text: "Cancel",
						"class": "dialogWrap",
						"tabindex": "18",
						click: function() {
							$(this).dialog('close');
						}
					}
				}
			});
			$("#fundsTable").find(".selectableId").click( function() {
				$("#fundsPopup").dialog("close");
			});
		});
	});
	
	$('.amendFundLookup').click(function(){
		inputId = $(this).attr('name');
		var type = this.id;
		var name = type.replace(type.charAt(0), type.charAt(0).toUpperCase());
		$('#fundsTable').load('showFund.htm', {'_event_':'findAmendFunds', 'type':type}, function(){
			createDialog ( $("#fundsPopup"), {
				title: name + 's',
				close: function(dialog) {
					$(dialog).find(".searchBox").val("");
				},
				buttons: {
					Cancel: {
						text: "Cancel",
						"class": "dialogWrap",
						"tabindex": "18",
						click: function() {
							$(this).dialog('close');
						}
					}
				}
			});
			$("#fundsTable").find(".selectableId").click( function() {
				$("#fundsPopup").dialog('close');
			});
		});
	});
	
	function showCreateFunds(info){
		var title = inputId.replace(inputId.charAt(0), inputId.charAt(0).toUpperCase());
		title = title.replace(/Input/, '');
		$('#fundsTable').load('showFund.htm', {'_event_':'createAccountCodeFunds', 'type':info.id}, function(){
			if(status == 'error'){
				showError(response);
			}else{
				createDialog ( $("#fundsPopup"), {
					title: title + 's',
					close: function(dialog) {
						$(dialog).find(".searchBox").val("");
					},
					buttons: {
						Cancel: {
							text: "Cancel",
							"class": "dialogWrap",
							"tabindex": "18",
							click: function() {
								$(this).dialog('close');
							}
						}
					}
				});
				$("#fundTable").find(".selectableId").click( function() {
					$("#fundsPopup").dialog("close");
				});
			}
		});
	}
	
	//Calls the backend to recieve all of the account code types and displays the 
	//funds popup
	function showFunds(){
		var title = inputId.replace(inputId.charAt(0), inputId.charAt(0).toUpperCase());
		document.getElementById('fundsTable').innerHTML = '';
		if ( type == null || type == "" || type == "AccountCodes" ) type = inputId;
		$('#fundsTable').load('showFund.htm', {'type': type}, 
		function(response){
			createDialog ( $("#fundsPopup"), {
				title: title + 's',
				close: function(dialog) {
					$(dialog).find(".searchBox").val("");
				},
				buttons: {
					Cancel: {
						text: "Cancel",
						"class": "dialogWrap",
						"tabindex": "18",
						click: function() {
							$(this).dialog('close');
						}
					}
				}
			});
			$("#fundTable").find(".selectableId").click( function() {
				$("#fundsPopup").dialog("close");
			});
		});
	}
	
	//method used when the user clicks 'select' on the fund popup and populates the 
	//corresponding section of the account code on the account code popup
	$('#selectFundButton').click(function(){
		var selected = $(".selectedFund:checked").val();
		if (selected != null){
			if(inputId == 'fundInput'){
				var fund = selected.split('/');
				$('#'+inputId).val(fund[0]);
				$('#yearInput').val(fund[1]);
			}else
				$('#'+inputId).val(selected);
			$('#'+inputId).focus();
			cancelModal('Funds');
		}
		else
			alert("You must select a " + type);
	});
	
	$('.accountCodeBox').keydown(function(event){
		if(!event)
			var event = window.event;
		
		var keycode='';
		if(event.keyCode) {
			keycode = event.keyCode; //for IE
		}if(event.which) {
			keycode = event.which; //for other browsers
		}
		if(keycode == 113){
			if($(this).attr('title') == 'createChartOfAccounts') {
				populateAccountCode($(this).val());
			}
			else {
				$('#accountCodeLookupButton').click();
			}
		}
	});
	
	//Displays the account code popup without the table or errors
	//Displays the account code popup without the table or errors
	function populateAccountCode(accountCode){
		var funds = accountCode.split('-');
		$('#fundInput').val(funds[0]);
		$('#functionInput').val(funds[1]);
		var objects = funds[2].split('.');
		$('#objectInput').val(objects[0]);
		$('#subobjectInput').val(objects[1]);
		$('#organizationInput').val(funds[3]);
		$('#yearInput').val(funds[4]);
		$('#programInput').val(funds[5]);
		$('#educationInput').val(funds[6]);
		$('#projectInput').val(funds[7]);
		$('#errorMessageAccount').html('');
		$('#accountCodeTable').css('display', 'none');
		lookupType = $(this).attr('name');
		showModalDialog('AccountCodes');
		$('#fundInput').focus();
	}

	$(".accountCodeLookupEllipsis").click(function() {
		accountCodeId = $(this).parent().find("input").attr("id");
		$('#fundInput').val('XXX');
		$('#functionInput').val('XX');
		$('#objectInput').val('XXXX');
		$('#subobjectInput').val('XX');
		$('#organizationInput').val('XXX');
		$('#yearInput').val('X');
		$('#programInput').val('XX');
		$('#educationInput').val('X');
		$('#projectInput').val('XX');
		$("#accountCodeLookupButton").trigger('click');
	});
	
	//Displays the account code popup without the table or errors
	$('#accountCodeLookupButton').click(function(){
		$('#fund').val('XXX');
		$('#function').val('XX');
		$('#object').val('XXXX');
		$('#subobject').val('XX');
		$('#organization').val('XXX');
		$('#year').val('X');
		$('#program').val('XX');
		$('#education').val('X');
		$('#project').val('XX');
		$('#errorMessageAccount').html('');
		$('#accountCodeTable').css('display', 'none');
		lookupType = $(this).attr('name');
		var wdth = this.getAttribute("popupWidth");
		if ( wdth != null && wdth != undefined && !isNaN ( parseInt(wdth) ) ) {
			wdth = parseInt(wdth);
		} else {
			wdth = "auto";
		}
		
		var btns = {};
		var open = function ( dialog ) {};
		if ( this.getAttribute("nosearch") == "nosearch" ) {
			btns = {
				OK: {
					text: "OK",
					"tabindex": "17",
					click: function() {
						$(this).find(".addBtn").trigger("click");
						$(this).dialog("close");
					}
				},
				Cancel: {
					text: "Cancel",
					"class": "dialogWrap",
					"tabindex": "17",
					click: function() {
						$(this).dialog('close');
					}
				}
			};
		} else {
			open = function(dialog) {
				$(dialog).find(".cancelBtn").click( function() {
					$(dialog).dialog("close");
				});
			};
		}
		
		createDialog ( $("#accountCodesPopup"), {
			title: "Account Codes",
			width: wdth,
			buttons: btns,
			open: open
		}, true);
	});
	
	//method used when the user clicks 'select' on the account code popup and populates the 
	//budget table on the main page.
	$('#selectAccountCodeButton').click(function(){
		var description = $(".selected:checked").parents('tr').children('td')[2];
		var selected = $(".selected:checked");
		if (selected != null){
			$(selected).attr('checked', false);
			if(accountCodeId == ''){
				var accountInputClass = $(this).attr('name');
				$('.'+accountInputClass).val($(selected).val());
				$('.'+accountInputClass).addClass('edit_mask');
			}else{
				$('#'+accountCodeId).val($(selected).val());
				$('#'+accountCodeId).addClass('edit_mask');
			}
			cancelModal('AccountCodes');
		}
		else
			alert("You must select a Account Code.");
	});
	
	//method used when the user clicks 'select' on the account code popup and populates the 
	//budget table on the main page.
	$('#addFundButton').click(function(){
		var selected = $('#fundInput').val()+'-'+$('#functionInput').val()+'-'+$('#objectInput').val()+'.'+$('#subobjectInput').val()+'-'+$('#organizationInput').val()+'-'+$('#yearInput').val()+'-'+$('#programInput').val()+'-'+$('#educationInput').val()+'-'+$('#projectInput').val();
		if (selected != null && selected.length == 28){
			var target = '#'+accountCodeId;
			if ( $(this).attr("targetClass") != null ) target = "." + $(this).attr("targetClass");
			$(target).val(selected);
			$(target).addClass('edit_mask');
			cancelModal('AccountCodes');
		}
		else
			alert("Account Code must be 20 characters.");
	});
	
	$("#addReportFundButton").click( function() {
		var selected = $('#fundInput').val()+'-'+$('#functionInput').val()+'-'+$('#objectInput').val()+'.'+$('#subobjectInput').val()+'-'+$('#organizationInput').val()+'-'+$('#yearInput').val()+$('#programInput').val()+$('#educationInput').val()+$('#projectInput').val();
		if (selected != null && selected.length == 25){
			$(".accountCodeText").val(selected);
			$(".accountCodeText").addClass('edit_mask');
			cancelModal('AccountCodes');
		}
	});
	
	//Calls the backend to recieve all of the account codes and displays the 
	//funds popup
	$('#searchAccountCodeButton').click(function(){
		var fund = $('#fund').val();
		var func = $('#function').val();
		var obj = $('#object').val();
		var sobj = $('#subobject').val();
		var org = $('#organization').val();
		var year = $('#year').val();
		var prog = $('#program').val();
		var edSpan = $('#education').val();
		var projDetail = $('#project').val();
		var poNumber = $('#poNumberInput').val();
		var currentYear = '';
		if($('input[name=currentYearRadio]:checked').val() != undefined && $('input[name=currentYearRadio]:checked').val() != null)
			var currentYear = $('input[name=currentYearRadio]:checked').val();
		type = 'AccountCodes'
		$('#errorMessageAccount').html('');
		$('#waitImage').css('display', '');
		$('#accountCodeTable').css('display', 'none');
		$('#accountCodesTable').load('showAccountCodes.htm', {'fund': fund, 'func': func, 'obj': obj, 'sobj': sobj, 'org': org, 
			'year': year, 'prog': prog, 'edSpan': edSpan, 'projDetail': projDetail, 'poNumber':poNumber, 'lookupType':lookupType, 'amend':$('#amend').val(), 'currentYear':currentYear}, 
			function(response, status){
				$('#waitImage').css('display', 'none');
				if(status == 'error'){
					showError(response);
				}else{
					$('#accountCodeTable').css('display', '');
					reAuto("#accountCodesPopup");
					registerClickables($("#accountCodesTable"));
					reWrap("#accountCodesPopup", $("#accountCodesPopup").find(".cancelBtn"));
					reFocus("#accountCodesPopup");
					$("#accountCodeTable").find(".selectableId").click( function() {
						$("#accountCodesPopup").dialog('close');
					});
				}
			});
	});
	
	$('#searchFilteredAccountCodeButton').click(function(){
		var fund = $('#fund').val();
		var func = $('#function').val();
		var obj = $('#object').val();
		var sobj = $('#subobject').val();
		var org = $('#organization').val();
		var year = $('#year').val();
		var prog = $('#program').val();
		var edSpan = $('#education').val();
		var projDetail = $('#project').val();
		type = 'AccountCodes'
		$('#errorMessageAccount').html('');
		$('#waitImage').css('display', '');
		$('#accountCodeTable').css('display', 'none');
		$('#accountCodesDiv').load('showAccountCodes.htm', {'_event_':'findFilteredAccountCodes', 'lookupType':$(this).attr('name'), 'fund': fund, 'func': func, 'obj': obj, 'sobj': sobj, 'org': org, 'year': year, 'prog': prog, 'edSpan': edSpan, 'projDetail': projDetail, 'lookupType':lookupType}, 
			function(response, status){
				$('#waitImage').css('display', 'none');
				if(status == 'error'){
					showError(response);
				}else{
					$(".errorDisplay").html("");
					reAuto();
					registerClickables($("#accountCodesDiv"));
					reWrap(null, $("#accountCodesPopup").find(".cancelBtn"));
					reFocus();
					$('#accountCodeTable').css('display', '');
				}
			});
	});
	
	$(".fundBtn").click( function() {
		createDialog ( $("#fundPopup"), {
			title: "Funds",
			close: function(dialog) {
				$(".searchBox").val("");
				$(dialog).find("tr.searchTarget").removeClass("hidden");
				$(dialog).find(".fundChk").prop("checked", false);
				$(dialog).find("#selectAll").prop("checked", false);
			},
			buttons: {
				OK: {
					text:"OK",
					"class": "dialogDefaultBtn",
					"tabindex":"3",
					click: function() {
						var output = "";
						var first = true;
						$(this).find(".fundChk").each( function() {
							if ( $(this).prop("checked") ) {
								if ( !first ) output = output + ",";
								output += this.getAttribute("name");
								first = false;
							}
						});
						$(".fundText").val(output);
						$(this).dialog("close");
					}
				},
				Cancel: {
					text: "Cancel",
					"class": "dialogWrap",
					"tabindex": "3",
					click: function() {
						$(this).dialog("close");
					}
				}
			}
		});
	});
});

//Calls the backend to recieve all of the account codes and displays the 
//funds popup
function showAccountCodesOverpayment(){
	var fund = $('#fund').val();
	var func = $('#function').val();
	var obj = $('#object').val();
	var sobj = $('#subobject').val();
	var org = $('#organization').val();
	var year = $('#year').val();
	var prog = $('#program').val();
	var edSpan = $('#education').val();
	var projDetail = $('#project').val();
	
	$('#errorMessageAccount').html('');
	$('#waitImage').css('display', '');
	$('#accountCodeTable').css('display', 'none');
	$('#accountCodesTable').load('showAccountCodes.htm', {'_event_':'findAccountCodesOverpayment' ,'fund': fund, 'func': func, 'obj': obj, 'sobj': sobj, 'org': org, 
		'year': year, 'prog': prog, 'edSpan': edSpan, 'projDetail': projDetail}, 
		function(response, status){
			$('#waitImage').css('display', 'none');
			if(status == 'error'){
				showError(response);
			}else{
				$('#accountCodeTable').css('display', '');
			}
		});
}

//Displays an error message returned from the controller if an error exists
function showError(response){
	var responseObject = eval('('+response+')');
	if (!responseObject.success){
		$('.errorDisplay').html(responseObject.errorMessage);
		$('#accountCodeTable').css('display','none');
	}
}



//displays modal dialog depending on type
function showModalDialog(tableType)
{
	$(".modal_film_add"+tableType).addClass("modal_film");
	$(".modal_contents_add"+tableType).addClass("modal_contents");
	$(".modal_film_add"+tableType).removeClass("hidden");
	$(".modal_contents_add"+tableType).removeClass("hidden");
}

//closes modal dialog depending on type
function cancelModal(tableType){
	$(".modal_film_add"+tableType).addClass("hidden");
	$(".modal_contents_add"+tableType).addClass("hidden");
	$(dialog).dialog("close");
}

function selectAC(input){
	var selected = $(input).attr('title');
	if(inputId == 'fundInput'){
		var fund = selected.split('/');
		$('#'+inputId).val(fund[0]);
		$('#yearInput').val(fund[1]);
		getNext($('#'+inputId));
	} else if ( inputId == "fund" ) {
		var fund = selected.split('/');
		$('#'+inputId).val(fund[0]);
		$('#year').val(fund[1]);
		getNext($('#'+inputId));
	}else if(inputId == ''){
		$('.'+type+'Input').val(selected);
		getNext($('.'+type+'Input'));
	}else {
		document.getElementById(inputId).value = selected;
		getNext($('#'+inputId));
	}
	type='';
	cancelModal('Funds');
}

function getNext(input){
	var inputs = $(input).parents('tr').eq(0).find(':input');
	var index = inputs.index(input);
	if(index == inputs.length - 1)
		$(inputs[0]).focus();
	else{
	 	if($(inputs[index + 1]).attr('tabindex') != '')
	 		$(inputs[index + 1]).focus();
	 	else 
	 		$(inputs[index + 2]).focus();
	}return;
}

function selectAccount(input){
	var selected = $(input).attr('title');
	
	//Close the dialog first
	$(dialog).dialog("close");
	
	if (selected != null){
		if(accountCodeId == ''){
			$('.'+lookupType).val(selected);
			$('.'+lookupType).addClass('edit_mask');
			$('.'+lookupType).focus();
		}else{
			$('#'+accountCodeId).val(selected);
			$('#'+accountCodeId).addClass('edit_mask');
			$('#'+accountCodeId).focus(function(){SubmitAction('itemchanged')});
			$('#'+accountCodeId).focus();
		}
	}
	else{
		$('#'+accountCodeId).val($('#fund').val()+'-'+$('#function').val()+'-'+$('#object').val()+'.'+$('#subobject').val()+'-'+$('#organization').val()+'-'+$('#year').val()+'-'+$('#program').val()+'-'+$('#education').val()+'-'+$('#project').val());
		$('#'+accountCodeId).addClass('edit_mask');
	}
}
 