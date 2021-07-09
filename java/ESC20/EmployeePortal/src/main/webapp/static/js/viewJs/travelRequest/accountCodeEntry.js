/** *--------------------------------------------------* **/
/** *-- Required script for AccountCodeEntry.jsp     --* **/
/** *--------------------------------------------------* * */

var accountCode20IdPre = '#acctCode20_';
var DOT = '.';

var acctCodeEntryFields = [ "fundInput", "functionInput", "objectInput", "subobjectInput", "organizationInput",
                "yearInput", "programInput", "educationInput", "projectInput" ];

/** *----------------------------------------* * */
/** *-- Ready() start --* * */
/** *----------------------------------------* * */

$().ready(function() {

    refreshAcctCode20();

    $(".aceUpperCase").change(function(e) {
        var txt = $(this).val().toUpperCase();
        $(this).val(txt);
    });

});

/** *-- Ready() end --* * */
/** *----------------------------------------* * */

function refreshAcctCode20() {
    var targetDivs = $(".acctCodesEntryDiv");
    targetDivs.each(function() {
        splitAcctCode20(this);
    });
}

function splitAcctCode20(pdiv) {
    var pt_id = pdiv.id;
    var sfx = pt_id.lastIndexOf('_');
    sfx = pt_id.substring(sfx + 1);

    var accountCode20Id = accountCode20IdPre + sfx;

    var codes = $(accountCode20Id).val().split(DOT);

    if (codes.length != 9) {
        return;
    }

    var chld = $("#" + pt_id).children().find("input");

    var asfx = $(accountCode20Id).attr('acesuffix');
    if ((asfx != undefined) && (asfx != null) && (asfx.length > 0)) {
        sfx = asfx;
    }

    for (idx = 0; idx < 9; idx++) {
        var cfld = acctCodeEntryFields[idx] + "_" + sfx;
        for (i = 0; i < chld.length; i++) {
            if (cfld == chld[i].id) {
                $(chld[i]).val(codes[idx]);
            }
        }
    }
}

function setAccountCodes20() {
    var targetDivs = $(".acctCodesEntryDiv");
    targetDivs.each(function() {
        setAcctCode20(this);
    });
}

function setAcctCode20(pdiv) {
    var pt_id = pdiv.id;
    var sfx = pt_id.lastIndexOf('_');
    sfx = pt_id.substring(sfx + 1);

    var accountCode20Id = accountCode20IdPre + sfx

    var rv = '';

    var asfx = $(accountCode20Id).attr('acesuffix');
    if ((asfx != undefined) && (asfx != null) && (asfx.length > 0)) {
        sfx = asfx;
    }

    var chld = $("#" + pt_id).children().find("input");

    for (idx = 0; idx < 9; idx++) {
        var cfld = acctCodeEntryFields[idx] + "_" + sfx;
        for (i = 0; i < chld.length; i++) {
            if (cfld == chld[i].id) {
                rv += ($(chld[i]).val() + ' ' + DOT);
            }
        }
    }

    var pos = rv.lastIndexOf(DOT);
    rv = rv.substring(0, pos).toUpperCase();

    $(accountCode20Id).val(rv);
}

/** *----------------------------------------* * */
/** *-- Popup and classes --* * */
/** *----------------------------------------* * */

$('.acctCodeEntryLookup').click(function() {
    console.log("acctCodeEntryLookup started...");
    var title = this.id;
    inputId = this.id;

    showAcctCodesPopup(this);
});

function showAcctCodesPopup(info) {
    console.log("showAcctCodesPopup started..." + info);
    var title = inputId.replace(inputId.charAt(0), inputId.charAt(0).toUpperCase());
    var pos = title.indexOf("Input");
    title = title.substring(0, pos);

    console.log("showAcctCodesPopup title=" + title);
    $('#aceTableDiv').load('AcctCodesEntry.htm', {
            'ACTION' : 'aceGetCodes',
            'codeType' : title,
            'IId' : info.id
    }, function(response) {

        var tdt = new Date();
        var mls = tdt.getMilliseconds();
        console.log(mls + "-" + tdt + " [1] Creating dialog...");

        tdt = new Date();
        mls = tdt.getMilliseconds();
        console.log(mls + "-" + tdt + " [2] Creating dialog...");

        if (status == 'error') {
            console.log("showAcctCodesPopup error...");
            showError(response);
        }
        else {
            createDialog($("#acePopup"), {
                    title : getPopupTitle(title),
                    close : function(dialog) {
                        $(dialog).find(".searchBox").val("");
                    },
                    resizable : false,
                    buttons : {
                        Cancel : {
                                text : "Cancel",
                                "class" : "dialogWrap",
                                "tabindex" : "18",
                                click : function() {
                                    $(this).dialog('close');
                                }
                        }
                    }
            });
            $("#aceCodeColId").click();
        }
    });
}

function getPopupTitle(ptitle) {
    var rv = "";

    switch (ptitle) {
        case 'Project':
            rv = 'Project Detail'
            break;
        case 'Subobject':
            rv = 'Sub-Object'
            break;
        case 'Education':
            rv = 'Ed Span'
            break;
        default:
            rv = ptitle;
    }

    return rv + ' Codes'
}

function selectAceCodeSearch(info, aceInput) {
    console.log("ACE input field: " + aceInput);
    var fid = '#' + aceInput;

    var sel = "." + aceInput;
    var vl = $(info).html();

    var elem;

    if (aceInput.substring(0, 9) == 'fundInput') {
        elem = setSplitCodeVal(info, sel, vl);
    }
    else {
        elem = setCodeVal(info, sel, vl);
    }

    $(info).closest("#acePopup").dialog("close");

    $(elem).focus();
};

function setCodeVal(info, psel, pval) {
    var elm = $(info).parents().find(psel);

    $(elm).val(pval);

    return elm;
}

function setSplitCodeVal(info, psel, pval) {
    var acesp = pval.split('/');

    var sfx = '';
    if (psel.length > 10) {
        sfx = psel.substring(10);
    }

    var elm = $(info).parents().find(psel);

    $(elm).val(acesp[0]);

    var elm1 = $(info).parents().find('.yearInput' + sfx);

    $(elm1).val(acesp[1]);

    return elm;
}

$(".aceNextField").keyup(function(e) {
    var kc = e.keyCode;
    var ch = String.fromCharCode(kc);

    console.log("1 Keycode: " + kc + "   char-->|" + ch + "\<--");

    if (kc != 32) {
        if (ch.trim() == '') {
            return;
        }
        else if (kc < 48) {
            return;
        }
    }

    console.log("2 Keycode: " + kc + "   char-->|" + ch + "\<--");

    var chkval = $(this).val();
    var $tfld = $(this);

    var ml = $(this).attr("aceML");
    console.log("aceNextField ML: " + ml + "\<--");
    if (chkval.length == ml) {
        var thId = $tfld.attr('id');
        var ln = thId.length;
        var nf = $tfld.attr('nextField');

        if ((nf == undefined) || (nf == null) || (nf.length == 0)) {
            return;
        }

        if ($(this).hasClass("aceXField")) {
            xfillfield(null, this);
        }

        $next = $("#" + nf);

        $next.focus();

    }
});

$(".aceXField").on('blur keydown', function(e) {
    xfillfield(e, this);
});

function xfillfield(e, fld) {
    var kc = null;
    var sk = null;

    if (e != null) {
        kc = e.keyCode;
        sk = e.shiftKey
    }

    if (sk && ((kc == 16) || ((kc != null) && (kc != 13) && (kc != 9)  && (kc != 32)))) {
        console.log(" SK FIELD Keycode: " + kc + "\<--");
        return;
    }
    else if ((kc !== undefined) && (kc != null) && (kc != 13) && (kc != 9)  && (kc != 32)) {
        console.log(" XFIELD Keycode: " + kc + "\<--");
        return;
    }
    var ml = $(fld).attr("aceML");
    var vl = $(fld).val();
    var ln = vl.length;

    console.log("xfillfield ML: " + ml + "\<--");

    if ((e !== undefined) && (e != null) && (ml != ln) && (kc == 32)) {
        return;
    }

    vl = vl.replace(/ /g,"") + 'XXXXX';
    vl = vl.substring(0, ml);

    $(fld).val(vl);
}




