var bank01, bank02
var formSelect
var formUndoSelect
var willSubmitFormDelete
$(function() {
    personalValidator()
    maritalStatusValidator()
    driverLicenseValidator()
    restrictionCodeFormValidator()
    emailFormValidator()
    emergencyContactFormValidator()
    mailingAddressValidator()
    alternativeAddressValidator()
    phoneValidator()
    w4InfoValidator()
    initSessionPws()
    //edit
    bankAccountValidator()
    //add
    bankAccountAddValidator()
    $("#saveEmail").on('click',function(){
        var workE = $("#emailWorkEmail").val()
        var workEV = $("#emailVerifyWorkEmail").val()
        var homeE = $("#emailHomeEmail").val()
        var homeEV = $("#emailVerifyHomeEmail").val()
        var emailFormValidator = $('#emailForm').data(
            'bootstrapValidator'
        )
        emailFormValidator.validate()
        console.log(emailFormValidator.isValid())
        if (emailFormValidator.isValid()) {
            if(workE===workEV && homeE===homeEV){
                $("#emailForm")[0].submit()
            }else{
                if(workE!=workEV){
                    $("#emailWorkEmail").parents(".form-group").addClass("has-error").removeClass('has-success')
                    $("#emailVerifyWorkEmail").parents(".form-group")
                    .addClass("has-error")
                    .removeClass('has-success')
                    .find(".help-block[data-bv-validator='identical']")
                    .show()
                }
                if(homeE!=homeEV){
                    $("#emailHomeEmail").parents(".form-group").addClass("has-error").removeClass('has-success')
                    $("#emailVerifyHomeEmail")
                    .parents(".form-group")
                    .addClass("has-error")
                    .removeClass('has-success')
                    .find(".help-block[data-bv-validator='identical']")
                    .show()
                }
            }
            
        }
    })
    $('.icheckRadioBank').on('click', function(event) {
        if ($(this).is(':checked')) {
            $(this).parents(".profile-item").find(".bankAmount .amount_2").val("0.00")
            console.log($(this).parents(".profile-item").find(".bankAmount .amount_2").val())
            $(".bankAccountBlock").removeClass("asPrimary")
            $(this).parents(".bankAccountBlock").addClass("asPrimary")
            $("input[name='displayAmountNew']").attr('type','text')
            $(this).parents(".bankAccountBlock").find("input[name='displayAmountNew']").attr('type','hidden')
            var indexBank = $('.icheckRadioBank').index(this)
            $('.icheckRadioBank').each(function(index) {
                if (index != indexBank) {
                    $(this).prop('checked', false)
                }
            })
        }
    })
    $(".bankAccountBlock").each(function(){
        var bankAmount = $(this).find(".bankAmount .amount_2").val()
        console.log(bankAmount)
        if(bankAmount == '0.00'){
            $(this).find(".icheckRadioBank").prop('checked', true)
            $(".bankAccountBlock").removeClass("asPrimary")
            $(this).addClass("asPrimary")
            $("input[name='displayAmountNew']").attr('type','text')
            $(this).find("input[name='displayAmountNew']").attr('type','hidden')
            $(this).find(".yesPrimary").show()
            $(this).find(".noPrimary").hide()
            return false
        }
    })
    $('.icheckRadioBank').keypress(function(e) {
        console.log(e)
        var eCode = e.keyCode ? e.keyCode : e.which ? e.which : e.charCode
        if (eCode == 13) {
            $(this).click()
            var indexBank = $('.icheckRadioBank').index(this)
            $('.icheckRadioBank').each(function(index) {
                if (index != indexBank) {
                    $(this).prop('checked', false)
                }
            })
        }
    })
    var bankInputName, bankInputCode
    $('.edit-btn').click(function() {
        $('.addBankForm').hide()
        $('.add-bank-btn').show()
        $('.profile-item').removeClass('activeEdit')
        $(this)
            .parents('.profile-item')
            .addClass('activeEdit')
        var groupSize = $(this)
            .parents('.profile-item')
            .find('.form-line:first-child .form-group').length
        console.log(groupSize)
        if (groupSize > 1) {
            $(this)
                .parents('.profile-item')
                .find('.form-line:first-child')
                .find('.form-group:first-child .form-control')
                .focus()
        } else {
            $(this)
                .parents('.profile-item')
                .find('.form-line:first-child')
                .find('.form-control')
                .focus()
            $(this)
                .parents('.profile-item')
                .find('.form-line:first-child')
                .find('.icheck')
                .focus()
        }
    })
    $('.cancel-btn').click(function() {
        $(this)
            .parents('.profile-item')
            .removeClass('activeEdit')
        clearValidator()
    })
    $('.cancel-add-btn').click(function() {
        $('.addBankForm').hide()
        $('.add-bank-btn').show()
    })
    $('.add-bank-btn').click(function() {
        var arrayBankLength = $('form.usedBank').length
        $('.profile-item').removeClass('activeEdit')
        $('.addBankForm').addClass('activeEdit')
       if (arrayBankLength >= 2) {
           $('.bankSizeError').show()
       } else {
            $('.addBankForm').show()
           $(this).hide()
       }
    })

    $('#saveNewBank').click(function() {
        var bankAccountValidator = $('#addBankAccountForm').data(
            'bootstrapValidator'
        )
        bankAccountValidator.validate()
        console.log(bankAccountValidator.isValid())
        if (bankAccountValidator.isValid()) {
            var freq = $('#freq').val()
            var saveBankDescription = $('#saveBankDescription').val()
            var saveBankCode = $('#newBankCode').val()
            var saveBankAccountNumber = $('#saveBankAccountNumber').val()
            var saveBankDisplayLabel = $('#saveBankDisplayLabel').val()
            var saveBankDisplayAmount = $('#saveBankDisplayAmount').val()

            console.log(saveBankCode)
            $('#hiddenfreq').val(freq)
            $('#hiddendescription').val(saveBankDescription)
            $('#hiddensubCode').val(saveBankCode)
            $('#hiddenaccountNumber').val(saveBankAccountNumber)
            $('#hiddendisplayLabel').val(saveBankDisplayLabel)
            $('#hiddendisplayAmount').val(saveBankDisplayAmount)

            $('#saveBankHidden').submit()
        }
    })
    var getBankBtn
    $('.getBank').click(function() {
        var page = {
            currentPage: 1,
            perPageRows: 10
        }
        var that = this
        getBankBtn = this
        $.ajax({
            type: 'POST',
            dataType: 'json',
            url: urlMain +'/profile/getAllBanks',
            data: JSON.stringify(page),
            contentType: 'application/json;charset=UTF-8',
            success: function(result) {
                console.log(result)
                //$('#bankTable').find('tr').remove();
                $('#bankTable  tbody').empty()
                var res = result.result
                for (var p in res) {
                    var bankTr =
                        "<tr><td scope='"+routingNumberLabel+"'>"
                    bankTr =
                        bankTr +
                        "<button class='a-btn bankNumberBtn' type='button' value='" +
                        res[p].bankCd +
                        "' data-title='" +
                        res[p].bankName +
                        "' > " +
                        res[p].transitRoute +
                        ' </button> </td>'
                    bankTr =
                        bankTr +
                        " <td scope='"+bankNameLabel+"'>" +
                        res[p].bankName +
                        '</td> </tr>'
                    $('#bankTable').append(bankTr)
                }

                // $('#selectBankModal').modal('show')
                bankInputName = $(that)
                    .parent()
                    .find('.form-control.name')
                bankInputBankCode = $(that)
                    .parent()
                    .find('.form-control.bankcode')
                bankInputCode = $(that)
                    .parent()
                    .find('.form-control.code')
                bankInputNewCode = $(that)
                    .parents('.profile-desc')
                    .find('.bankNewCode')
                newBankCode = $(that).parent().find("#newBankCode")
                console.log(newBankCode)
                console.log(bankInputName)
                $('.bankNumberBtn').on('click',function() {
                    var number = $(this).text()
                    var code = $(this).val()
                    var name = $(this).attr('data-title')
                    console.log(number)
                    console.log(name)
                    console.log(code)
                    console.log(bankInputName)
                    newBankCode.val(code).change()
                    bankInputName.val(name).change()
                    bankInputBankCode.val(code)
                    bankInputCode.val(number).change()
                    bankInputNewCode.val(code)
                    $('#selectBankModal').modal('hide')
                })

                
            },
            error: function(e) {
                console.log(e)
            }
        })
    })
    $('#searchBankBtn').on('click',function() {
        var page = {
            currentPage: 1,
            perPageRows: 10
        }

        var searchCode = $('#codeCriteriaSearchCode').val()
        var searchDescription = $('#codeCriteriaSearchDescription').val()

        var criteria = {
            searchCode: searchCode,
            searchDescription: searchDescription
        }

        var searchCriteria = { page: page, criteria: criteria }
        var that = getBankBtn
        $.ajax({
            type: 'POST',
            dataType: 'json',
            url: urlMain + '/profile/searchBanks',
            data: JSON.stringify(searchCriteria),
            contentType: 'application/json;charset=UTF-8',
            success: function(result) {
                console.log(result)
                $('#bankTable  tbody').empty()
                if (result.result && result.result.length > 0) {
                    var res = result.result
                    for (var p in res) {
                        var bankTr =
                            "<tr><td scope='"+routingNumberLabel+"'>"
                        bankTr =
                            bankTr +
                            "<button class='a-btn bankNumberBtn' type='button' value='" +
                            res[p].bankCd +
                            "' data-title='" +
                            res[p].bankName +
                            "' > " +
                            res[p].transitRoute +
                            ' </button> </td>'
                        bankTr =
                            bankTr +
                            " <td scope='"+bankNameLabel+"'>" +
                            res[p].bankName +
                            '</td> </tr>'
                        $('#bankTable').append(bankTr)
                    }

                    //$('#selectBankModal').modal('show')
                    bankInputName = $(that)
                        .parent()
                        .find('.form-control.name')
                    bankInputBankCode = $(that)
                        .parent()
                        .find('.form-control.bankcode')
                    bankInputCode = $(that)
                        .parent()
                        .find('.form-control.code')
                    newBankCode = $(that).parent().find("#newBankCode")
                    $('.bankNumberBtn').on('click',function() {
                        var number = $(this).text()
                        var code = $(this).val()
                        var name = $(this).attr('data-title')
                        console.log(number)
                        console.log(name)
                        console.log(code)
                        console.log(bankInputName)
                        newBankCode.val(code).change()
                        bankInputName.val(name).change()
                        bankInputBankCode.val(code)
                        bankInputCode.val(number).change()
                        bankInputNewCode.val(code)
                        $('#selectBankModal').modal('hide')
                    })
                } else {
                    $('#bankTable tbody').empty()
                    var noResult = '<tr><td colspan="2"> <span>'+noDataLabel+'</span></td></tr>'
                    $('#bankTable tbody').append(noResult)
                }

            },
            error: function(e) {
                console.log(e)
                $('#bankTable tbody').empty()
                var noResult = '<tr><td colspan="2"> <span>'+noDataLabel+'</span></td></tr>'
                $('#bankTable tbody').append(noResult)
            }
        })
    })
    
    $('#undoNameRequest').click(function() {
        // $('#undoModal').modal('show')
        formSelect = $('#deleteNameRequest')
    })
    $('#undoMaritalRequest').click(function(e) {
        e.preventDefault()
        // $('#undoModal').modal('show')
        formSelect = $('#deleteMaritalRequest')
    })
    $('#undoDriverLicense').click(function() {
        // $('#undoModal').modal('show')
        formSelect = $('#deleteDriversLicense')
    })
    $('#undoRestriction').click(function() {
        // $('#undoModal').modal('show')
        formSelect = $('#deleteRestrictionCodesRequest')
    })
    $('#undoEmail').click(function() {
        // $('#undoModal').modal('show')
        formSelect = $('#deleteEmail')
    })
    $('#undoEmergencyContact').click(function() {
        // $('#undoModal').modal('show')
        formSelect = $('#deleteEmergencyContact')
    })
    $('#undoMailingAddress').click(function() {
        // $('#undoModal').modal('show')
        formSelect = $('#deleteMailAddr')
    })
    $('#undoAlternative').click(function() {
        // $('#undoModal').modal('show')
        formSelect = $('#deleteAltMailAddr')
    })
    $('#undoPhoneNumber').click(function() {
        // $('#undoModal').modal('show')
        formSelect = $('#deletePhone')
    })
    $('#undoW4').click(function() {
        // $('#undoModal').modal('show')
        formSelect = $('#deleteW4')
    })
    $('.sureUndo').click(function() {
        console.log('modal -- undo')
        formSelect.submit()
        
    })
    $('.sureDelete').click(function() {
        console.log('modal -- delete')
        willSubmitFormDelete.submit()
    })
    
})
function clearNoNum(obj){
    obj.value = obj.value.replace(/[^\d.]/g, ""); //  
    obj.value = obj.value.replace(/^\./g, ""); //
    obj.value = obj.value.replace(/\.{2,}/g, ".");  
    obj.value = obj.value.replace(".","$#$").replace(/\./g,"").replace("$#$",".");  
  obj.value = obj.value.replace(/^(\-)*(\d+)\.(\d\d).*$/,'$1$2.$3');//
}
function deleteBankAmount(index) {
    // $('#deleteModal').modal('show')
    console.log('delete=' + index)

    var freq = $('#freq').val()
    var code = $('#code_' + index).val()
    var accountNumber = $('#accountNumber_' + index).text()
    var accountType = $('#accountType_' + index).text()
    var displayAmount = $('#displayAmount_' + index).text()
    console.log(code)
    console.log(accountNumber)
    console.log(accountType)
    console.log(displayAmount)

    $('#hidden_freq_delete').val(freq)
    $('#hidden_accountNumber_delete').val(accountNumber)
    $('#hidden_code_delete').val(code)

    $('#hidden_accountType_delete').val(accountType)
    $('#hidden_displayAmount_delete').val(displayAmount)
    willSubmitFormDelete = $('#deleteBankHidden')
}
function updateBank() {
    var bankArry =  $(".updateBankForm");
    var bankLen =  bankArry.length;
    var arrayValidate = bankArry.map(function (index) {
        var bankAccountForm = '#bankAccountForm_' + index
        var bankAccountValidator = $(bankAccountForm).data('bootstrapValidator')
        bankAccountValidator.validate()
        if(bankAccountValidator.isValid()){
            return true
        }
    });
    console.log(bankLen)
    console.log(arrayValidate)
    var freq = $('#freq').val()
    $('.hidden_freq_update').val(freq)
    if(arrayValidate.length == bankLen){
        var successNum = 0;
        var currentBankIndex = 0;
        $(".updateBankForm").each(function(index){
            var one = {};
            var t = $(this).serializeArray();
            $.each(t, function() {
                one[this.name] = this.value;
            });
            console.log("one",one)
            console.log("string",JSON.stringify(one))
            $.ajax({
                type:'POST',
                url:'/'+ctx+'/profile/updateBank',
                dataType:'JSON',
                contentType:'application/json;charset=UTF-8',
                data:JSON.stringify(one),
                success : function (res) {
                    currentBankIndex ++;
                    if(res.success){
                        successNum ++;
                        if(currentBankIndex == bankLen){
                            if(successNum == bankLen){
                                window.location.reload()
                            }else{
                                $(".updateMessageFailed").removeClass("hide")
                            }
                        }
                    }
                },
                error:function(res){
                    console.log(res)
                    $(".updateMessageFailed").removeClass("hide")
                }
            });
        })
       
    }
    
    return false
    console.log('updateBank=' + index)
    var bankAccountForm = '#bankAccountForm_' + index
    var bankAccountValidator = $(bankAccountForm).data('bootstrapValidator')
    console.log($(bankAccountForm))
    console.log(bankAccountValidator)
    bankAccountValidator.validate()
    console.log(bankAccountValidator.isValid())
    if (bankAccountValidator.isValid()) {
        var freq = $('#freq').val()
        var code = $('#code_' + index).val()
        var accountNumber = $('#accountNumber_' + index).text()
        var accountType = $('#accountType_' + index).text()
        var displayAmount = $('#displayAmount_' + index).text()

        var codeNew = $('#codeNew_' + index).val()
        console.log('codeNew' + codeNew)
        var accountNumberNew = $('#accountNumberNew_' + index).val()

        var accountTypeNew = $('#accountTypeNew_' + index).val()
        console.log(accountTypeNew)
        var displayAmountNew = $('#displayAmountNew_' + index).val()
        
        // console.log(parseFloat(parseFloat(displayAmountNew).toFixed(2)))

        $('#hidden_freq_update').val(freq)
        $('#hidden_code_update').val(code)
        $('#hidden_codeNew_update').val(codeNew)
        $('#hidden_accountNumber_update').val(accountNumber)
        $('#hidden_accountNumberNew_update').val(accountNumberNew)

        $('#hidden_accountType_update').val(accountType)
        $('#hidden_accountTypeNew_update').val(accountTypeNew)
        $('#hidden_displayAmount_update').val(displayAmount)
        $('#hidden_displayAmountNew_update').val(parseFloat(displayAmountNew))
        $('#updateBankHidden').submit()
    }
}
function undoBank(index) {
    var freq = $('#freq').val()
    var code = $('#code_' + index).val()
    var codeNew = $('#codeNew_' + index).val()
    var accountNumber = $('#accountNumber_' + index).text()
    var accountNumberNew = $('#accountNumberNew_' + index).val()

    $('#hidden_freq_undo').val(freq)
    $('#hidden_code_undo').val(code)
    $('#hidden_codeNew_undo').val(codeNew)
    $('#hidden_accountNumber_undo').val(accountNumber)
    $('#hidden_accountNumberNew_undo').val(accountNumberNew)

    // $('#undoModal').modal('show')
    formSelect = $('#undoBankHidden')
}

function changeFreq() {
    $('#changeFreqForm')[0].submit()
}
function clearValidator() {
    // reset  #personalForm form
    $('#personalForm')
        .data('bootstrapValidator')
        .destroy()
    $('#personalForm').data('bootstrapValidator', null)
    $('#personalForm')[0].reset()
    personalValidator()
    // reset  Marital Status form
    $('#maritalStatusForm')
        .data('bootstrapValidator')
        .destroy()
    $('#maritalStatusForm').data('bootstrapValidator', null)
    $('#maritalStatusForm')[0].reset()
    maritalStatusValidator()
    //reset driver license form
    $('#driverLicenseForm')
        .data('bootstrapValidator')
        .destroy()
    $('#driverLicenseForm').data('bootstrapValidator', null)
    $('#driverLicenseForm')[0].reset()
    driverLicenseValidator()
    // reset Restriction Codes form
    $('#restrictionCodeForm')
        .data('bootstrapValidator')
        .destroy()
    $('#restrictionCodeForm').data('bootstrapValidator', null)
    $('#restrictionCodeForm')[0].reset()
    restrictionCodeFormValidator()
    // reset email form
    $('#emailForm')
        .data('bootstrapValidator')
        .destroy()
    $('#emailForm').data('bootstrapValidator', null)
    $('#emailForm')[0].reset()
    emailFormValidator()
    // reset Emergency Contact Information form
    $('#emergencyContactForm')
        .data('bootstrapValidator')
        .destroy()
    $('#emergencyContactForm').data('bootstrapValidator', null)
    $('#emergencyContactForm')[0].reset()
    emergencyContactFormValidator()
    // reset Mailing Address form
    $('#mailingAddressForm')
        .data('bootstrapValidator')
        .destroy()
    $('#mailingAddressForm').data('bootstrapValidator', null)
    $('#mailingAddressForm')[0].reset()
    mailingAddressValidator()

    // reset Alternative Address form
    $('#alternativeAddressForm')
        .data('bootstrapValidator')
        .destroy()
    $('#alternativeAddressForm').data('bootstrapValidator', null)
    $('#alternativeAddressForm')[0].reset()
    alternativeAddressValidator()
    // reset Phone Numbers form
    $('#phoneForm')
        .data('bootstrapValidator')
        .destroy()
    $('#phoneForm').data('bootstrapValidator', null)
    $('#phoneForm')[0].reset()
    phoneValidator()

    // reset W4 Marital Status Information form
    $('#w4InfoForm')
        .data('bootstrapValidator')
        .destroy()
    $('#w4InfoForm').data('bootstrapValidator', null)
    $('#w4InfoForm')[0].reset()
    w4InfoValidator()

    //reset Direct Deposit Bank01 Accounts
    if (bank01 != 0) {
        $('#bankAccountForm_01')
            .data('bootstrapValidator')
            .destroy()
        $('#bankAccountForm_01').data('bootstrapValidator', null)
        $('#bankAccountForm_01')[0].reset()
        bankAccount01Validator()
    }
    //reset Direct Deposit Bank02 Accounts
    if (bank02 != 0) {
        $('#bankAccountForm_02')
            .data('bootstrapValidator')
            .destroy()
        $('#bankAccountForm_02').data('bootstrapValidator', null)
        $('#bankAccountForm_02')[0].reset()
        bankAccount02Validator()
    }
    //reset add Direct Deposit Bank Accounts
    $('#addBankAccountForm')
        .data('bootstrapValidator')
        .destroy()
    $('#addBankAccountForm').data('bootstrapValidator', null)
    $('#addBankAccountForm')[0].reset()
    bankAccountAddValidator()
}
function personalValidator() {
    $('#personalForm').bootstrapValidator({
        live: 'enable',
        submitButtons: '#savePersonal',
        feedbackIcons: {
            valid: 'fa fa-check ',
            // invalid: 'fa fa-times',
            validating: 'fa fa-refresh'
        },
        fields: {
            nameFNew: {
                trigger: null,
                validators: {
                    notEmpty: {
                        message: requiredFieldValidator
                    },
                    stringLength: {
                        max: 25,
                        message: maxLength25Validator
                    }
                }
            },
            nameLNew: {
                trigger: null,
                validators: {
                    notEmpty: {
                        message: requiredFieldValidator
                    },
                    stringLength: {
                        max: 25,
                        message: maxLength25Validator
                    }
                }
            },
            nameMNew: {
                trigger: null,
                validators: {
                    stringLength: {
                        max: 25,
                        message: maxLength25Validator
                    }
                }
            }
        }
    })
}

function maritalStatusValidator() {
    $('#maritalStatusForm').bootstrapValidator({
        live: 'enable',
        submitButtons: '#saveMarital',
        feedbackIcons: {
            valid: 'fa fa-check ',
            // invalid: 'fa fa-times',
            validating: 'fa fa-refresh'
        },
        fields: {
            maritalStatNew: {
                trigger: null,
                validators: {
                    notEmpty: {
                        message: requiredFieldValidator
                    }
                }
            }
        }
    })
}

function driverLicenseValidator() {
    $('#driverLicenseForm').bootstrapValidator({
        live: 'enable',
        submitButtons: '#saveDriver',

        feedbackIcons: {
            valid: 'fa fa-check ',
            // invalid: 'fa fa-times',
            validating: 'fa fa-refresh'
        },
        fields: {
            driversLicNbrNew: {
                trigger: null,
                validators: {
                    stringLength: {
                        max: 19,
                        message: maxLength19Validator
                    }
                }
            }
        }
    })
}

function restrictionCodeFormValidator() {
    $('#restrictionCodeForm').bootstrapValidator({
        live: 'enable',
        submitButtons: '#saveRestrict',
        feedbackIcons: {
            valid: 'fa fa-check ',
            // invalid: 'fa fa-times',
            validating: 'fa fa-refresh'
        },
        fields: {
            no: {
                trigger: null,

                validators: {
                    notEmpty: {
                        message: requiredFieldValidator
                    }
                }
            }
        }
    })
}

function emailFormValidator() {
    $('#emailForm').bootstrapValidator({
        live: 'enable',
        fields: {
            emailNew: {
                trigger: null,
                validators: {
                    emailAddress: {
                        message: pleaseEnterCorrectFormatValidator
                    }
                }
            },
            emailNewVerify: {
                trigger: null,
                validators: {
                    identical: {
                        field: 'emailNew',
                        message:emailNotMatchValidator
                    },
                    emailAddress: {
                        message: pleaseEnterCorrectFormatValidator
                    }
                }
            },
            hmEmailNew: {
                trigger: null,
                validators: {
                    emailAddress: {
                        message: pleaseEnterCorrectFormatValidator
                    }
                }
            },
            hmEmailVerifyNew: {
                trigger: null,
                validators: {
                    identical: {
                        field: 'hmEmailNew',
                        message:emailNotMatchValidator
                    },
                    emailAddress: {
                        message: pleaseEnterCorrectFormatValidator
                    }
                }
            }
        }
    })
}

function emergencyContactFormValidator() {
    $('#emergencyContactForm').bootstrapValidator({
        live: 'enable',
        submitButtons: '#saveEmergency',

        feedbackIcons: {
            valid: 'fa fa-check ',
            // invalid: 'fa fa-times',
            validating: 'fa fa-refresh'
        },
        fields: {
            emerContactNew: {
                trigger: null,
                stringLength: {
                    max: 26,
                    message: maxLength26Validator
                }
            },
            emerPhoneAcNew: {
                trigger: null,
                validators: {
                    notEmpty: {
                        message: requiredFieldValidator
                    },
                    regexp: {
                        regexp: /^[0-9]\d{2}$/,
                        message: pleaseEnterCorrectFormatValidator
                    }
                }
            },
            emerPhoneNbrNew: {
                trigger: null,

                validators: {
                    notEmpty: {
                        message: requiredFieldValidator
                    },
                    regexp: {
                        regexp: /^[0-9][\d]{2}[\-]?[\d]{4}$/,
                        message: pleaseEnterCorrectFormatValidator
                    }
                }
            },
            emerPhoneExtNew: {
                trigger: null,
                validators: {
                    regexp: {
                        regexp: /^[\d]{0,4}$/,
                        message: pleaseEnterCorrectFormatValidator
                    }
                }
            },
            emerRelNew: {
                trigger: null,
                stringLength: {
                    max: 25,
                    message: maxLength25Validator
                }
            },
            emerNoteNew: {
                trigger: null,
                stringLength: {
                    max: 25,
                    message: maxLength25Validator
                }
            }
        }
    })
}

function mailingAddressValidator() {
    $('#mailingAddressForm').bootstrapValidator({
        live: 'enable',
        submitButtons: '#saveMailingAddress',

        feedbackIcons: {
            valid: 'fa fa-check ',
            // invalid: 'fa fa-times',
            validating: 'fa fa-refresh'
        },
        fields: {
            addrNbrNew: {
                trigger: null,
                validators: {
                    regexp: {
                        regexp: /^[0-9]\d{0,7}$/,
                        message: pleaseEnterCorrectFormatValidator
                    }
                }
            },
            addrStrNew: {
                trigger: null,
                validators: {
                    stringLength: {
                        max: 20,
                        message: maxLength20Validator
                    }
                }
            },
            addrAptNew: {
                trigger: null,
                validators: {
                    stringLength: {
                        max: 7,
                        message: maxLength7Validator
                    }
                }
            },
            addrCityNew: {
                trigger: null,
                validators: {
                    stringLength: {
                        max: 20,
                        message: maxLength20Validator
                    }
                }
            },
            addrZipNew: {
                trigger: null,
                validators: {
                    notEmpty: {
                        message: requiredFieldValidator
                    },
                    regexp: {
                        regexp: /^[0-9]\d{4}$/,
                        message: pleaseEnterCorrectFormatValidator
                    }
                }
            },
            addrZip4New: {
                trigger: null,
                validators: {
                    regexp: {
                        regexp: /^[0-9][\d]{3}$/,
                        message: pleaseEnterCorrectFormatValidator
                    }
                }
            }
        }
    })
}
function alternativeAddressValidator() {
    $('#alternativeAddressForm').bootstrapValidator({
        live: 'enable',
        submitButtons: '#saveAltAddress',

        feedbackIcons: {
            valid: 'fa fa-check ',
            // invalid: 'fa fa-times',
            validating: 'fa fa-refresh'
        },
        fields: {
            smrAddrNbrNew: {
                trigger: null,
                validators: {
                    regexp: {
                        regexp: /^[0-9]\d{0,7}$/,
                        message: pleaseEnterCorrectFormatValidator
                    }
                }
            },
            smrAddrStrNew: {
                trigger: null,
                validators: {
                    stringLength: {
                        max: 20,
                        message: maxLength20Validator
                    }
                }
            },
            smrAddrAptNew: {
                trigger: null,
                validators: {
                    stringLength: {
                        max: 7,
                        message: maxLength7Validator
                    }
                }
            },
            smrAddrCityNew: {
                trigger: null,
                validators: {
                    stringLength: {
                        max: 20,
                        message: maxLength20Validator
                    }
                }
            },
            smrAddrZipNew: {
                trigger: null,
                validators: {
                    notEmpty: {
                        message: requiredFieldValidator
                    },
                    regexp: {
                        regexp: /^[0-9]\d{4}$/,
                        message: pleaseEnterCorrectFormatValidator
                    }
                }
            },
            smrAddrZip4New: {
                trigger: null,
                validators: {
                    regexp: {
                        regexp: /^[0-9][\d]{3}$/,
                        message: pleaseEnterCorrectFormatValidator
                    }
                }
            }
        }
    })
}

function phoneValidator() {
    $('#phoneForm').bootstrapValidator({
        live: 'enable',
        submitButtons: '#savePhone',
        feedbackIcons: {
            valid: 'fa fa-check ',
            // invalid: 'fa fa-times',
            validating: 'fa fa-refresh'
        },
        fields: {
            phoneAreaNew: {
                trigger: null,
                validators: {
                    regexp: {
                        regexp: /^[0-9]\d{2}$/,
                        message: pleaseEnterCorrectFormatValidator
                    }
                }
            },
            phoneNbrNew: {
                trigger: null,
                validators: {
                    regexp: {
                        regexp: /^[0-9][\d]{2}[\-]?[\d]{4}$/,
                        message: pleaseEnterCorrectFormatValidator
                    }
                }
            },
            phoneAreaCellNew: {
                trigger: null,
                validators: {
                    regexp: {
                        regexp: /^[0-9]\d{2}$/,
                        message: pleaseEnterCorrectFormatValidator
                    }
                }
            },
            phoneNbrCellNew: {
                trigger: null,
                validators: {
                    regexp: {
                        regexp: /^[0-9][\d]{2}[\-]?[\d]{4}$/,
                        message: pleaseEnterCorrectFormatValidator
                    }
                }
            },
            phoneAreaBusNew: {
                trigger: null,
                validators: {
                    regexp: {
                        regexp: /^[0-9]\d{2}$/,
                        message: pleaseEnterCorrectFormatValidator
                    }
                }
            },
            phoneNbrBusNew: {
                trigger: null,
                validators: {
                    regexp: {
                        regexp: /^[0-9][\d]{2}[\-]?[\d]{4}$/,
                        message: pleaseEnterCorrectFormatValidator
                    }
                }
            },
            busPhoneExtNew: {
                trigger: null,

                validators: {
                    regexp: {
                        regexp: /^[\d]{0,4}$/,
                        message: pleaseEnterCorrectFormatValidator
                    }
                }
            }
        }
    })
}

function w4InfoValidator() {
    $('#w4InfoForm').bootstrapValidator({
        live: 'enable',
        submitButtons: '#saveW4',
        feedbackIcons: {
            valid: 'fa fa-check ',
            // invalid: 'fa fa-times',
            validating: 'fa fa-refresh'
        },
        fields: {
            maritalStatTaxNew: {
                trigger: null,
                validators: {
                    notEmpty: {
                        message: requiredFieldValidator
                    }
                }
            },
            nbrTaxExemptsNew: {
                trigger: null,
                validators: {
                    notEmpty: {
                        message: requiredFieldValidator
                    },
                    regexp: {
                        regexp: /^[0-9]\d{0,1}$/,
                        message: pleaseEnterCorrectFormatValidator
                    }
                }
            }
        }
    })
}

function bankAccountValidator() {
    var arrayBankLength = $('.bankAccountBlock').length
    for (var i = 0; i < arrayBankLength; i++) {
        $('#bankAccountForm_' + i).bootstrapValidator({
            live: 'enable',
            excluded: [':disabled', ':hidden', ':not(:visible)'],
            submitButtons: '.saveUpdateBankBtn',
            feedbackIcons: {
                valid: 'fa fa-check ',
                // invalid: 'fa fa-times',
                validating: 'fa fa-refresh'
            },
            fields: {
                description: {
                    trigger: null,
                    validators: {
                        notEmpty: {
                            message: requiredFieldValidator
                        }
                    }
                },
                subCode: {
                    trigger: null,
                    validators: {
                        notEmpty: {
                            message: requiredFieldValidator
                        }
                    }
                },
                accountNumberNew: {
                    trigger: null,
                    validators: {
                        notEmpty: {
                            message: requiredFieldValidator
                        },
                        regexp: {
                            regexp: /^[0-9]\d{0,16}$/,
                            message: pleaseEnterCorrectFormatValidator
                        }
                    }
                },
                accountTypeNew: {
                    trigger: null,
                    validators: {
                        notEmpty: {
                            message: requiredFieldValidator
                        },
                    }
                },
                displayAmountNew: {
                    trigger: null,
                    validators: {
                        regexp: {
                            // regexp: /^[1-9]{1,7}[\.]{1}\d{1}$/,
                            // regexp: /^[0][\.]{1}[1-9]{1}$/,

                            // regexp: /^[0][\.]{1}\d{1}[1-9]{1}$/,
                            // regexp: /^[0][\.]{1}[1-9]{1}\d{1}$/,

                            // regexp: /^[1-9]{1}\d{0,6}[\.]{1}\d{1,2}$/,

                            // regexp: /^[1-9]{1}\d{0,6}$/,

                            regexp: /^[1-9]{1}\d{0,6}$|^[1-9]{1}\d{0,6}[\.]{1}\d{1,2}|^[0][\.]{1}[1-9]{1}\d{1}|^[0][\.]{1}\d{1}[1-9]{1}|^[1-9]{1,7}[\.]{1}\d{1}|^[0][\.]{1}[1-9]{1}$/,
                            message: pleaseEnterCorrectFormatBankAmountValidator
                        }
                    }
                }
            }
        })
    }
}
function bankAccountAddValidator() {
    $('#addBankAccountForm').bootstrapValidator({
        live: 'enable',
        submitButtons: '#saveNewBank',
        feedbackIcons: {
            valid: 'fa fa-check ',
            // invalid: 'fa fa-times',
            validating: 'fa fa-refresh'
        },
        fields: {
            description: {
                trigger: null,
                validators: {
                    notEmpty: {
                        message: requiredFieldValidator
                    }
                }
            },
            subCode: {
                trigger: null,
                validators: {
                    notEmpty: {
                        message: requiredFieldValidator
                    }
                }
            },
            accountNumber: {
                trigger: null,
                validators: {
                    notEmpty: {
                        message: requiredFieldValidator
                    },
                    regexp: {
                        regexp: /^[0-9]\d{0,16}$/,
                        message: pleaseEnterCorrectFormatValidator
                    }
                }
            },
            displayLabel: {
                trigger: null,
                validators: {
                    notEmpty: {
                        message: requiredFieldValidator
                    }
                }
            },
            displayAmount: {
                trigger: null,
                validators: {
                    notEmpty: {
                        message: requiredFieldValidator
                    },
                    regexp: {
                        regexp: /^[1-9]{1}\d{0,6}$|^[1-9]{1}\d{0,6}[\.]{1}\d{1,2}|^[0][\.]{1}[1-9]{1}\d{1}|^[0][\.]{1}\d{1}[1-9]{1}|^[1-9]{1,7}[\.]{1}\d{1}|^[0][\.]{1}[1-9]{1}$/,
                        message: pleaseEnterCorrectFormatBankAmountValidator
                    }
                }
            }
        }
    })
}

function initSessionPws() {
    const psd = $('#sessionPsd').val()
    if (psd !== '') {
        sessionStorage.setItem('sessionPws', psd)
    }
}
