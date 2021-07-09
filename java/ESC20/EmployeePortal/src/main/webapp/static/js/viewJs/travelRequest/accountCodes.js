// Global Variables
var activeTab = "";
var keyCodeValue = "";
var codeSortDirection;
var currentCodeSort = "";
var currentAgeFrom = "";
var currentId = "";
var tpdc = "#tabpageHasDataChange";
var cotp = "#hasDataChangedOnTabs";

var tabMap = {
        "1" : 'Fund',
        "2" : 'Func',
        "3" : 'Obj',
        "4" : 'Sobj',
        "5" : 'Org',
        "6" : 'Pgm',
        "7" : 'EdSpan',
        "8" : 'ProjDtl'
}

var startFromMap = {
        "1" : 'startFromFund',
        "2" : 'startFromFunc',
        "3" : 'startFromObj',
        "4" : 'startFromSObj',
        "5" : 'startFromOrg',
        "6" : 'startFromPgm',
        "7" : 'startFromEdSpan',
        "8" : 'startFromProjDtl'
}

$().ready(function() {

    $(cotp).html("false");
    for (var i = 1; i <= 8; i++) {
        fixSortArrow(i);

        tabpageChangeCheck(i);
    }

    // Get the Current Tab
    activeTab = $('#activeTab').val();

    fixSortArrow(activeTab);

    $(".actDelButton").click(function(ev) {
        var id = this.id;
        var idx = id.indexOf("_", 0) + 1;
        idx = id.substring(idx);

        selector = $("#delete_" + idx);

        rowSelector = $(this).parent('td').parent('tr');

        if ($(selector).prop("checked")) {
            $(selector).prop('checked', false);
            $(rowSelector).removeClass('deleteRow');
            $(selector).val(false);
        }
        else {
            $(selector).prop('checked', true);
            $(rowSelector).addClass('deleteRow');
            $(selector).val(true);
        }
        $(selector).blur();
        var $next = $(selector).closest('td').next('td').find('input');
        $next.focus();
    });

    var msgYesNoDelete = $("#displayConfirmDelete").val();
    msgYesNoDelete = (msgYesNoDelete == null || msgYesNoDelete == '' || msgYesNoDelete == 'false') ? 'false' : 'true';
    var deletesPending = $("#deletesPending").html();

    if (msgYesNoDelete == 'true') {
        deletesPending = (deletesPending == null || deletesPending == '') ? 'false' : deletesPending;
        msgYesNoDelete = deletesPending;
    }

    if (msgYesNoDelete == 'true') {
        // displayMsgYesNoDelete();
        displayActOKDelete();
    }

    var targetNodes = $(".act-tab-div");
    var MutationObserver = window.MutationObserver || window.WebKitMutationObserver;
    var myObserver = new MutationObserver(mutationHandler);
    var obsConfig = {
            childList : false,
            characterData : false,
            attributes : true,
            subtree : false
    };

    // --- Add a target node to the observer.
    // Can only add one node at a time.
    targetNodes.each(function() {
        myObserver.observe(this, obsConfig);
    });

    var objTargNodes = $(".objCodeDD");

    objTargNodes.each(function() {
        objDropDownEnabler(this.id);
    });


});

function getCodeField(ptab) {
    var fld = '';

    switch (ptab) {
        case '1':
            fld = 'fundFsclYr';
            break;
        case '2':
            fld = 'fnc';
            break;
        case '3':
            fld = 'obj';
            break;
        case '4':
            fld = 'sObj';
            break;
        case '5':
            fld = 'org';
            break;
        case '6':
            fld = 'pgm';
            break;
        case '7':
            fld = 'edSpan';
            break;
        case '8':
            fld = 'projDtl';
            break;
    }

    return fld;
}

function upCaseFirst(pval) {
    if ((pval == undefined) || (pval == null) || (pval.length == 0)) {
        return '';
    }

    var fc = pval.substring(0, 1).toUpperCase();

    return (fc + pval.substring(1));
}

// Auto insert a new row when tab out the last column of the last row
$('.lastRowTabOut').keydown(function(event) {
    if (event.keyCode == 9 && event.currentTarget.value > "") {
        $('#addLink').click();
    }
});

$('.acctTabClick').click(function(ev) {
    var nm = this.id;
    var ln = nm.length;
    var at = nm.substring(ln - 1);

    if (UnsavedDataWarning.isDirty()) {
        var tp = "#tabpage" + $('#activeTab').val() + "HasDataChange";
        $(tp).val("true");
        $(cotp).html("true");

        UnsavedDataWarning.forceClean();
    }

    $('#activeTab').val(at);

    $('#fnc_0').focus();
});



$(".autoNextField").keyup(function(e) {
    var kc = e.keyCode;
    var ch = String.fromCharCode(kc);

    if (kc != 32) {
        if (ch.trim() == '') {
            return;
        }
        else if (kc < 48) {
            return;
        }
    }

    console.log("Keycode: " + kc + "   char-->|" + ch + "\<--");

    var chkval = $(this).val().trim();
    var $tfld = $(this);
    var $tdfld = $(this).closest('td');

    if (chkval.length == this.maxLength) {
        var $next = $(this).closest('td').next('td').find('.nextField');
        if ($next.length) {

            if ($tdfld.hasClass('actCodeTD')) {
                var id = this.id;
                var pos = id.indexOf('_', 0) + 1;
                id = id.substring(0, pos);
                pos = parseInt(this.id.substring(pos)) + 1;

                if (!$("#" + id + pos).length) {
                    var rid = $next.attr('id');
                    var at = $('#activeTab').val();

                    $("#returnAddFocusField").val(rid);
                    $("#returnAddFocusTab").val(at);

                    $('#AddButton').click();
                }
            }

            $next.focus();
        }
        else {
            $(this).blur();
        }
    }
});

$(".defaultFunds").focusin(
                function(e) {
                    var dot = '.';

                    var fndid = this.id;
                    var pos = fndid.indexOf('_', 0);
                    var idx = fndid.substring(pos);

                    var $budfund = $("#budFundObjSobj" + idx);
                    var $actfund = $("#actFundObjSobj" + idx);
                    var $duefrom = $("#interDueFromObjSobj" + idx);
                    var $dueto = $("#interDueToObjSobj" + idx);

                    if (($budfund.val().trim() != dot) || ($budfund.val().trim() != dot)
                                    || ($budfund.val().trim() != dot) || ($budfund.val().trim() != dot)) {
                        return;
                    }

                    $budfund.val('3700.00');
                    $actfund.val('3600.00');

                    var $tester = $("#fundFsclYr" + idx);

                    // var tst = $(this).val().substring(0, 1);
                    var tst = $tester.val().substring(0, 1);

                    var objval = '2171.00';
                    if (tst == '8') {
                        objval = '2177.00';
                    }

                    $dueto.val(objval);

                    objval = '1260.00';
                    // tst = $(this).val().substring(0, 3);
                    tst = $tester.val().substring(0, 3);

                    if (tst < 200) {
                        objval = '1261.00';
                    }
                    else if (tst < 500) {
                        objval = '1262.00';
                    }
                    else if (tst < 600) {
                        objval = '1263.00';
                    }
                    else if (tst < 700) {
                        objval = '1264.00';
                    }
                    else if (tst < 750) {
                        objval = '1265.00';
                    }
                    else if (tst < 800) {
                        objval = '1266.00';
                    }
                    else if (tst < 900) {
                        objval = '1267.00';
                    }

                    $duefrom.val(objval);

                });

$(".buttonSort").click(function(event) {
    adjustSortColumnIndicator();

    var fld = $('#userClickedSortColumn').val();
    var sc = $('#sortColumn').val();
    var sor = $('#sortOrder').val();
    var atab = $('#activeTab').val();

    $("#selectedTab").val(atab);

    $("#actActionButton").click();

});

function fixSortArrow(ptab) {
    var tabv = tabMap[ptab];

    var sortColumn = $('#sortColumn' + tabv).val();
    var sortOrder = $('#sortOrder' + tabv).val();

    $('.sortCol' + sortColumn).removeClass('tableColSortUpImLeft');
    $('.sortCol' + sortColumn).removeClass('tableColSortDownImLeft');

    if (sortOrder == 'ASC') {
        $('.sortCol' + sortColumn).addClass('tableColSortUpImLeft');
    }
    else {
        $('.sortCol' + sortColumn).addClass('tableColSortDownImLeft');
    }

    // Set Sort Order and Column
    $('#sortOrder').val(sortOrder);
    $('#sortColumn').val(sortColumn);
}

function tabpageChangeCheck(ptab) {
    console.log("tabpageChangeCheck(" + ptab + ")");
    var tp = "#tabpage" + ptab + "HasDataChange";

    $(tpdc).html($(tp).val());

    if ($(tp).val() == 'true') {
        $(cotp).html("true");
    }
}

$(".lastAction").click(function(event) {
    var la = this.id;
    $("#lastAction").val(la);
});

var fin2200ReportWindow;

function printAccountCodesClick(ptab) {
    var tabv = tabMap[ptab];
    if (ptab == '4') {
        tabv = 'SObj';
    }

    var btid = "#Retrieve" + tabv + "DataButton"

    var strtFr = getStartFromVal(ptab);

    var params = {
            'action' : 'printAccountCodes',
            'activeTab' : ptab,
            'startFrom' : strtFr
    };

    $.getJSON("AccountCodes", params, function(model) {

        if (model.errorList.length == 0) {

            if (fin2200ReportWindow) {
                if (!fin2200ReportWindow.closed) {
                    fin2200ReportWindow.close();
                }
            }

            fin2200ReportWindow = window.open("txeisReport.htm", "reportWindow");

            $(btid).click();
        }
    });

}

function getStartFromVal(ptab) {
    var sfVal = ''; //$("#" + startFromMap[ptab]).val().toUpperCase();

    return sfVal;
}

$(".objCodeDD").focusout(function(e) {
    var fid = this.id;
    objDropDownEnabler(fid);
});

function objDropDownEnabler(objcId) {
    console.log("Object targets: " + objcId);
    var vl = $("#" + objcId).val();
    var nm = objcId.indexOf("_");
    nm = objcId.substring(nm);

    var oposId = "#objPos" + nm;
    var oactId = "#objActv" + nm;
    var ocflowId = "#objCFlow" + nm;

    $(oposId).prop('disabled', true);
    $(oactId).prop('disabled', true);
    $(ocflowId).prop('disabled', false);

    if (!((vl == undefined) || (vl == null) || (vl.length < 4))) {
        var ch = vl.substring(0, 1);

        switch (ch) {
            case '1':
                $(oposId).prop('disabled', false);
                $(ocflowId).prop('disabled', false);

                $(oactId).val('');
                break;
            case '2':
            case '3':
            case '4':
                $(oposId).prop('disabled', false);

                $(oactId).val('');
                $(ocflowId).val('');
                break;
            case '5':
            case '6':
            case '7':
            case '8':
                $(oactId).prop('disabled', false);

                $(oposId).val('');
                $(ocflowId).val('');
                break;
            default:
                $(oposId).val('');
                $(oactId).val('');
                $(ocflowId).val('');
        }
    }
}

function checkReadonly(pat) {
    var ro = false;

    switch (pat) {
        case '1':
            ro = $("#readOnlyFund").val();
            break;
        case '2':
            ro = $("#readOnlyFunc").val();
            break;
        case '3':
            ro = $("#readOnlyObj").val();
            break;
        case '4':
            ro = $("#readOnlySobj").val();
            break;
        case '5':
            ro = $("#readOnlyOrg").val();
            break;
        case '6':
            ro = $("#readOnlyPgm").val();
            break;
        case '7':
            ro = $("#readOnlyEdSpan").val();
            break;
        case '8':
            ro = $("#readOnlyProjDtl").val();
            break;
    }

    ro = (ro == 'true');

    $("#SaveButton").prop("disabled", ro);
}


/** ********************************************************************************************** * */
/** **************************************************************** **/
/** * handler for tabs gaining focus * **/
/** **************************************************************** **/

function mutationHandler(mutationRecords) {
    console.info("mutationHandler:");

    mutationRecords.forEach(function(mutation) {
        var tid = mutation.target.id;
        var at = $('#activeTab').val();
        var dsp = $('#' + tid).css('display');
        var attrVal = $('#' + tid).attr(mutation.attributeName);

        tabpageChangeCheck(at);

        console.log("Mutation: target: " + tid + "    type=" + mutation.type + "   attr=" + mutation.attributeName
                        + "   val=" + attrVal);

        if ((mutation.type == "attributes") && (mutation.attributeName == 'aria-hidden') && (attrVal == 'false')) {

            if ($('#focusField').val() != "ADDROW") {

                var fld = getCodeField(at);

                $('#' + fld + '_0').focus();
            }

            checkReadonly(at);

        }

        if ((mutation.type == "attributes") && (mutation.attributeName == 'role')) {

            console.log("target: " + tid + "   attr=" + mutation.attributeName);

            if (dsp != 'none') {

                var rt = $('#returnAddFocusTab').val();

                if ((rt == at) && ($("#returnAddFocusField").val().length)) {
                    var fid = $("#returnAddFocusField").val();

                    if ($('#focusField').val() == "ADDROW") {
                        console.log("ready(): returnAddFocusField=" + fid);

                        $("#" + fid).focus();
                        $("#returnAddFocusField").val('');
                        return;
                    }

                    if ($("#" + fid).is(':focus')) {
                        $("#returnAddFocusField").val('');
                        return;
                    }
                }

                $("#returnAddFocusField").val('');
                $("#returnAddFocusTab").val('');

                var fld = getCodeField(at);
                var rowIdx = '#rowIndex' + upCaseFirst(fld);
                rowIdx = "#blankRowIndex";

                var rnum = "_";

                if ($('#focusField').val() == "ADDROW") {
                    rnum += ($(rowIdx).val());
                }
                else {
                    rnum += '0';
                }

                $('#' + fld + rnum).focus();

            }
        }
    });
}

// ***********************************************************************
// Popup Dialog Box to display YesNo Delete
function displayActOKDelete() {
    $("#dialog:ui-dialog").dialog("destroy");
    var msgYesNoDelete = $("#displayConfirmDelete").val();
    if (msgYesNoDelete == 'true') {
        var msg = "<div class='msgQuestionImg'></div>All marked row(s) will be deleted.<br/> <br/>Click OK to continue, or Cancel to stay on the current page.";
        $("#dialog-popup").html(msg).dialog({
                modal : true,
                resizable : false,
                width : 420,
                height : 'auto',
                closeOnEscape : false,
                title : "Confirm Delete",
                position : {
                        my : "center",
                        at : "center",
                        of : window
                },
                open : function(event, ui) {
                    $(".ui-dialog-titlebar-close").hide();
                },
                buttons : {
                        OK : function() {
                            $(this).dialog("close");
                            $("#dialog:ui-dialog").dialog("destroy");
                            $('#confirmDelete').val('Y');
                            $('#displayConfirmDelete').val('false');
                            $("#SaveButton").click();
                        },

                        Cancel : function() {
                            $(this).dialog("close");
                            $("#dialog:ui-dialog").dialog("destroy");
                            $('#confirmDelete').val('');
                            $('#displayConfirmDelete').val('false');
                        }
                }
        });
    }
};