
function showDocumentModal(columnId, label, formType, viewType, tripNum) {
	$("#noUploadFileError").remove()
	loadPopupMask();
    $(".faspinnerIcon").css("display","none");
    $("#documentStorageColumnId").val(columnId);
    var buttonTextId = "#documentExistDisplaySpan" + columnId;
    sessionStorage.setItem("docId",buttonTextId);
	var tripNbr = '';
    if(viewType === 'Y'){
    	$("#divDocStorPopup").css({"height":"50%"});
	}
	var empNbr = $("#empNbrModal").val();
	if (tripNum !== '') {
		tripNbr = tripNum;
	} else {
		tripNbr = $('#tripNbrModal').val();
	}
	if (tripData != undefined && tripData != 'undefined' && tripData != null && tripData != '') {
		var tripValue = tripData[0];
		if (tripValue != null && tripValue != ''
			&& tripValue != undefined && tripValue != 'undefined') {
			tripNbr = tripData[0];
		}
	}
	var sessionId = "";
	var clientCode = "";
	var schoolYear = "";
	
	var keyfieldmaps = 'EMPNO='+empNbr+ '|' +'TRIPNBR='+tripNbr;
	 var params = {
		        "csrfmiddlewaretoken": $("#csrfmiddlewaretoken").val()
	};
	$.ajax({
		type : 'post',
		url : urlMain + "/travelRequest/getDocumentStorageInfo",
		contentType : 'application/json;charset=utf-8',
		dataType : 'json',
		data : JSON.stringify(params),
		success : function(data) {
			sessionId = data.sessionID;
			clientCode = data.clientCode;
			schoolYear = data.schoolYear;
			var url = data.docStorageServiceURI; //
			openDocumentStorage(url+'/DocManage.aspx', sessionId, viewType, 'EMPLOYEE PORTAL', formType, label, 'N', clientCode, keyfieldmaps, '', columnId );
	      },
		error : function(data) {
			console.log(data);
		}
	});
}

function openDocumentStorage(path, token, rdonly, appname, foldername, pickname, schyr, cntydist, keyfieldmaps, compareFields, formsManagementKey) {
	openURL = path + '?';
	try {
		openURL += returnURLPath(token, rdonly, appname, foldername, pickname, schyr, cntydist, keyfieldmaps);
	} catch (ex) {
		alert(ex + ":: invalid url " + openURL + "\n\n Please contact your administrator.");
		return;
	}
    activeUploadColId = formsManagementKey;
    
    var frame = document.getElementById( "ifr" )
    var spinner = document.getElementById("faspinnerIcon")
    frame.src = openURL;
    frame.addEventListener( "load", function(){
        spinner.style.display = "none";
        frame.style.display = "block";
        var cssURL = window.location.origin + '/EmployeePortal/css/docStyle.css'
        $("#ifr").contents().find("head").append($('<link rel="stylesheet" type="text/css" href="'+cssURL +'">'))

    })
	loadPopupBox();
	$('#divDocStorPopupClose').click(function() {
		unloadPopupBox();
		checkDocumentFiles();
	});
}

function returnURLPath(token, rdonly, appname, foldername, pickname, schyr, cntydist, keyfieldmaps) {
	openURL = "";

	if (!token && token.length != 10 && ("" != token.replace(new RegExp('\d', 'g'), '')))
		throw "invalid token";
	else
		openURL += "TOKEN=" + token;

	if (!appname || ("" == appname.replace(new RegExp(' ', 'g'), '')))
		throw "invalid appname";
	openURL += "&APPLCTN_NAME=" + appname;

	if (!foldername || ("" == foldername.replace(new RegExp(' ', 'g'), '')))
		throw "invalid folder";
	else
		openURL += "&FOLDER_NAME=" + foldername;

	openURL += "&PICK_NAME=" + pickname;

	if (!schyr && schyr != 'Y' && schyr != 'N')
		throw "invalid school year flag";
	else
		openURL += "&SCHYRFLAG=" + schyr;

	if (!cntydist)
		throw "invalid county district data";
	else
		openURL += "&COUNTYDISTRICT=" + cntydist;

	if (!rdonly && rdonly != 'Y' && rdonly != 'N')
		throw "invalid read only flag";
	else
		openURL += "&RDONLYFLAG=" + rdonly;

	fieldMaps = keyfieldmaps.split("|");
	for (var i = 0; i < fieldMaps.length; ++i) {
		fldmap = fieldMaps[i].split("=");
		openURL += "&" + fldmap[0] + "=" + fldmap[1];
	}
	return openURL;

}

function unloadPopupBox() {
    var docId = sessionStorage.getItem("docId")
    if(docId){
        var docRowId = docId.split('#documentExistDisplaySpan');
        var docformId = '#txtNewColValue' + docRowId[1] + '.txtDocumentStorageValue' + docRowId[1];
        var retdocformId = '#formControl_1_' + docRowId[1] + '.txtDocumentStorageValue' + docRowId[1];

    }
    if ( sessionStorage.getItem("ppUpload") == "yes")
    {
        $(docId).text("+");
        $(docformId).val(docRowId[1]);
        $(retdocformId).val(docRowId[1]);
   }else if ( sessionStorage.getItem("ppUpload") == "no"){
        $(docId).text("-");
        $(docformId).val("");
        $(retdocformId).val("");
    }
    sessionStorage.removeItem("docId");
    sessionStorage.removeItem("ppUpload");
    $('#divDocStorPopup').hide();
    $('#popup_mask').fadeOut("fast");
    $('#mainForm1').fadeOut("slow");
    $('#mainForm2').fadeOut("slow");
    $('#mainForm3').fadeOut("slow");
}    

function loadPopupBox() {    
    loadPopupMask();
    $('#divDocStorPopup').show();
}  

function loadPopupMask() {    
    $('#popup_mask').fadeIn(100);	
    $('#popup_mask').fadeTo("slow",0.5);
}
$('.divDocStorPopupClose').click(function() {
	unloadPopupBox();
});

function checkDocumentFiles() {
	var tripNbr = '';
	tripNbr = $('#tripNbrModal').val();
	if (tripData != null && tripData != ''
		&& tripData != undefined && tripData != 'undefined') {
		var tripValue = tripData[0];
		if (tripValue != null && tripValue != ''
			&& tripValue != undefined && tripValue != 'undefined') {
			tripNbr = tripData[0];
		}
	}
	var payload = {
		tripNbr : tripNbr
	}
	$.ajax({
		type : 'post',
		url : urlMain + "/travelRequest/checkDocuments",
		contentType : 'application/json;charset=utf-8',
		dataType : 'json',
		async : false,
		data : JSON.stringify(payload),
		success : function(res) {
			data = res.result;
			if (data) {
					$('#documentUpload').css({
						'width' : '125px',
						'background-position' : '10px 10px',
						'vertical-align' : 'middle',
						'background-repeat' : 'no-repeat',
						'background-image' : 'url("/CommonWebPortals/images/document.gif")'
					});
				} else {
					$('#documentUpload').css({
						'width' : '125px',
						'background-position' : '10px 10px',
						'vertical-align' : 'middle',
						'background-repeat' : 'no-repeat',
						'background-image' : ''
					});
			}
		},
		error : function(res) {
			console.log(res);
		}
	});
}

setTimeout("window.close()", 600000);  // close window when timeout