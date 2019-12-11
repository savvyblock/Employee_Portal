var bank01, bank02
var formSelect, undoForm
var formUndoSelect
var willSubmitFormDelete
// var requestArray = location.pathname.split('/')
// var requestLen = requestArray.length
// document.onreadystatechange = function() { 
//     if (document.readyState === "interactive") { 
//         console.log(document.readyState);
//         if(requestArray[requestLen-1]=='saveAll'){
//             $(".loadingOn").show()
//         }
//     }
//     if (document.readyState === "complete") { 
//         console.log(document.readyState);
//         $(".loadingOn").hide()
//     }
// }
$(function () {
    if (passwordModalShow == 'true') {
        $("#changePasswordModal").modal('show')
    }
    personalValidatorValue == 'U' ? personalValidator() : ''
    maritalStatusValidatorValue == 'U' ? maritalStatusValidator() : ''
    driverLicenseValidatorValue == 'U' ? driverLicenseValidator() : ''
    restrictionCodeFormValidatorValue == 'U' ? restrictionCodeFormValidator() : ''
    emailFormValidatorValue == 'U' ? emailFormValidator() : ''
    emergencyContactFormValidatorValue == 'U' ? emergencyContactFormValidator() : ''
    mailingAddressValidatorValue == 'U' ? mailingAddressValidator() : ''
    alternativeAddressValidatorValue == 'U' ? alternativeAddressValidator() : ''
    workPhoneValidatorValue == 'U' || homePhoneValidatorValue == 'U' || cellPhoneValidatorValue == 'U' ? phoneValidator() : ''
    w4InfoValidatorValue == 'U' ? w4InfoValidator() : ''
    initSessionPws()
    //edit
    bankAccountValidatorValue == 'U' ? bankAccountValidator() : ''
    //add
    bankAccountValidatorValue == 'U' ? bankAccountAddValidator() : ''
    bankAccountAddValidator()
    $("#saveEmail").on('click', function () {
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
            if (workE === workEV && homeE === homeEV) {
                $("#profileForm")[0].submit()
            } else {
                if (workE != workEV) {
                    $("#emailWorkEmail").parents(".form-group").addClass("has-error").removeClass('has-success')
                    $("#emailVerifyWorkEmail").parents(".form-group")
                        .addClass("has-error")
                        .removeClass('has-success')
                        .find(".help-block[data-bv-validator='identical']")
                        .show()
                }
                if (homeE != homeEV) {
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
    $('.icheckRadioBank').on('click', function (event) {
        if ($(this).is(':checked')) {
            $(this).parents(".profile-item").find(".bankAmount .amount_2").val("0.00")
            var currentBankCode = $(this).parents(".profile-item").find("input[name='code']").val()
            //if there is no value in "current bank"
            $(".undo-btn.undoBankHaveModal").show()
            $(".undo-btn.undoBankNoModal").hide()
            if (!currentBankCode || currentBankCode == '') {
                $(this).parents(".profile-item").find(".undo-btn.undoBankHaveModal").hide()
                $(this).parents(".profile-item").find(".undo-btn.undoBankNoModal").show()
            }
            console.log($(this).parents(".profile-item").find(".bankAmount .amount_2").val())
            $(".bankAccountBlock").removeClass("asPrimary")
            $(this).parents(".bankAccountBlock").addClass("asPrimary")
            $(".bankAccountBlock input[name='displayAmountNew']").attr('type', 'text')
            $("#saveBankDisplayAmount").attr('type', 'text')
            $(this).parents(".bankAccountBlock").find("input[name='displayAmountNew']").attr('type', 'hidden')
            var indexBank = $('.icheckRadioBank').index(this)
            $('.icheckRadioBank').each(function (index) {
                if (index != indexBank) {
                    $(this).prop('checked', false)
                }
            })
        }
    })
    var currentAccountLen = $(".bankAccountBlock").length;
    if (currentAccountLen == 0) {
        $("#primary_add").prop('checked', true)
        $("#saveBankDisplayAmount").attr('type', 'hidden')
    } else {
        $("#primary_add").prop('checked', false)
        $("#saveBankDisplayAmount").attr('type', 'text')
    }
    $('#primary_add').on('click', function (event) {
        if ($(this).is(':checked')) {
            $(this).parents(".profile-item").find(".bankAmount .amount_2").val("0.00")
            console.log($(this).parents(".profile-item").find(".bankAmount .amount_2").val())
            $(".bankAccountBlock").removeClass("asPrimary")
            // $(this).parents(".bankAccountBlock").addClass("asPrimary")
            $(".bankAccountBlock input[name='displayAmountNew']").attr('type', 'text')
            $(this).parents(".addBankForm").find("input#saveBankDisplayAmount").attr('type', 'hidden')
            var indexBank = $('.icheckRadioBank').index(this)
            $('.icheckRadioBank').each(function (index) {
                if (index != indexBank) {
                    $(this).prop('checked', false)
                }
            })
        }
    })
    $(".bankAccountBlock").each(function () {
        var bankAmount = $(this).find(".bankAmount .amount_2").val()
        console.log(bankAmount)
        if (bankAmount == '0.00') {
            $(this).find(".icheckRadioBank").prop('checked', true)
            $(".bankAccountBlock").removeClass("asPrimary")
            $(this).addClass("asPrimary")
            $(".bankAccountBlock input[name='displayAmountNew']").attr('type', 'text')
            $(this).find("input[name='displayAmountNew']").attr('type', 'hidden')
            $(this).find(".yesPrimary").show()
            $(this).find(".noPrimary").hide()
            return false
        }
    })
    $('.icheckRadioBank').keypress(function (e) {
        console.log(e)
        var eCode = e.keyCode ? e.keyCode : e.which ? e.which : e.charCode
        if (eCode == 13) {
            $(this).click()
            var indexBank = $('.icheckRadioBank').index(this)
            $('.icheckRadioBank').each(function (index) {
                if (index != indexBank) {
                    $(this).prop('checked', false)
                }
            })
        }
    })
    $(".saveOrCancel .save-btn[type='submit']").click(function(){
        $(".loadingOn").show()
    })
    $('.edit-btn').click(function () {
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
    $('.cancel-btn').click(function () {
        $(this)
            .parents('.profile-item')
            .removeClass('activeEdit')
        clearValidator()
    })
    $('.cancel-add-btn').click(function () {
        $('.addBankForm').hide()
        $('.add-bank-btn').show()
    })
    $('.add-bank-btn').click(function () {
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

    $('#saveNewBank').click(function () {
        var bankArry = $(".updateBankForm");
        var bankLen = bankArry.length;
        var arrayValidate = bankArry.map(function (index) {
            var bankAccountForm = '#bankAccountForm_' + index
            var bankAccountValidator = $(bankAccountForm).data('bootstrapValidator')
            bankAccountValidator.validate()
            if (bankAccountValidator.isValid()) {
                return true
            }
        });

        var bankAccountValidator = $('#addBankAccountForm').data('bootstrapValidator')
        bankAccountValidator.validate()
        console.log(bankAccountValidator.isValid())



        if (bankAccountValidator.isValid() && arrayValidate.length == bankLen) {
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
            $('#hiddenCode').val(saveBankCode)
            $('#hiddenaccountNumber').val(saveBankAccountNumber)
            $('#hiddendisplayLabel').val(saveBankDisplayLabel)
            $('#hiddendisplayAmount').val(saveBankDisplayAmount)

            var newBank = {
                codeNew: saveBankCode,
                accountNumberNew: saveBankAccountNumber,
                accountTypeNew: saveBankDisplayLabel,
                displayAmountNew: saveBankDisplayAmount,
            }
            var result = checkDuplicate(newBank)
            console.log(result.bankArray)
            if (result.hasDuc) {
                $(".duplicateBankAccountError").show()
                return false
            }
            $(".duplicateBankAccountError").hide()
            $("#bankArrayGroup").val(JSON.stringify(result.bankArray))
            $('#saveBankHidden').submit()
        }
    })
    $(".decimal2").blur(function () {
        var val = $(this).val()
        if (!val || val == '') {
            return
        }
        var valArry = val.split('.')
        if (valArry.length > 1) {
            if (valArry[1].length < 2) {
                var len = valArry[1].length
                var res = valArry[1]
                for (var i = 0; i < len; i++) {
                    res = res + '0'
                }
                $(this).val(valArry[0] + '.' + res)
            }
        } else {
            var res = ''
            for (var i = 0; i < 2; i++) {
                res = res + '0'
            }
            $(this).val(valArry[0] + '.' + res)
        }
    })
    $('#undoNameRequest').click(function () {
        // $('#undoModal').modal('show')
        $('#undoName').val("deleteNameRequest");
        //        formSelect = $('#deleteNameRequest')
    })
    $('#undoMaritalRequest').click(function (e) {
        e.preventDefault()
        // $('#undoModal').modal('show')
        //        formSelect = $('#deleteMaritalRequest')
        $('#undoName').val("deleteMaritalRequest");
    })
    $('#undoDriverLicense').click(function () {
        // $('#undoModal').modal('show')
        //        formSelect = $('#deleteDriversLicense')
        $('#undoName').val("deleteDriversLicenseRequest");
    })
    $('#undoRestriction').click(function () {
        // $('#undoModal').modal('show')
        //        formSelect = $('#deleteRestrictionCodesRequest')
        $('#undoName').val("deleteRestrictionCodesRequest");
    })
    $('#undoEmail').click(function () {
        // $('#undoModal').modal('show')
        //        formSelect = $('#deleteEmail')
        $('#undoName').val("deleteEmail");
    })
    $('#undoEmergencyContact').click(function () {
        // $('#undoModal').modal('show')
        //        formSelect = $('#deleteEmergencyContact')
        $('#undoName').val("deleteEmergencyContact");
    })
    $('#undoMailingAddress').click(function () {
        // $('#undoModal').modal('show')
        //        formSelect = $('#deleteMailAddr')
        $('#undoName').val("deleteMailAddr");
    })
    $('#undoAlternative').click(function () {
        // $('#undoModal').modal('show')
        //        formSelect = $('#deleteAltMailAddr')
        $('#undoName').val("deleteAltMailAddr");
    })
    $('#undoPhoneNumber').click(function () {
        // $('#undoModal').modal('show')
        //        formSelect = $('#deletePhone')
        $('#undoName').val("deletePhone");
    })
    $('#undoW4').click(function () {
        // $('#undoModal').modal('show')
        formSelect = $('#deleteW4')
        undoForm = 'deleteW4'
        // $('#undoName').val("deleteW4");
    })
    $('.sureUndo').click(function () {
        if (undoForm == 'deleteW4' || undoForm == 'undoBank') {
            undoForm = null
            formSelect.submit()
        } else {
            console.log('modal -- undo')
            $(".loadingOn").show()
            profileForm = $('#profileForm')
            var t = $("#profileForm").serializeArray();
            profileForm.submit();
        }


    })
    $('.sureDelete').click(function () {
        console.log('modal -- delete')
        willSubmitFormDelete.submit()
    })

    //133
    $('#saveAll').click(function () {
        var personalFormValidator = $('#personalForm').data(
            'bootstrapValidator'
        )
        var maritalStatusFormValidator = $('#maritalStatusForm').data(
            'bootstrapValidator'
        )
        var driverLicenseFormValidator = $('#driverLicenseForm').data(
            'bootstrapValidator'
        )
        var emailFormValidator = $('#emailForm').data(
            'bootstrapValidator'
        )
        var emergencyContactFormValidator = $('#emergencyContactForm').data(
            'bootstrapValidator'
        )
        var mailingAddressFormValidator = $('#mailingAddressForm').data(
            'bootstrapValidator'
        )
        var alternativeAddressFormValidator = $('#alternativeAddressForm').data(
            'bootstrapValidator'
        )
        var phoneFormValidator = $('#phoneForm').data(
            'bootstrapValidator'
        )
        personalFormValidator ? personalFormValidator.validate() : true;
        maritalStatusFormValidator ? maritalStatusFormValidator.validate() : true;
        driverLicenseFormValidator ? driverLicenseFormValidator.validate() : true;
        emailFormValidator ? emailFormValidator.validate() : true;
        emergencyContactFormValidator ? emergencyContactFormValidator.validate() : true;
        mailingAddressFormValidator ? mailingAddressFormValidator.validate() : true;
        alternativeAddressFormValidator ? alternativeAddressFormValidator.validate() : true;
        phoneFormValidator ? phoneFormValidator.validate() : true;

        var personalFormValid = personalFormValidator ? personalFormValidator.isValid() : true
        var maritalStatusFormValid = maritalStatusFormValidator ? maritalStatusFormValidator.isValid() : true
        var emailFormValid = emailFormValidator ? emailFormValidator.isValid() : true
        var emergencyContactFormValid = emergencyContactFormValidator ? emergencyContactFormValidator.isValid() : true
        var mailingAddressFormValid = mailingAddressFormValidator ? mailingAddressFormValidator.isValid() : true
        var phoneFormValid = phoneFormValidator ? phoneFormValidator.isValid() : true
        var driverLicenseFormValid = driverLicenseFormValidator ? driverLicenseFormValidator.isValid() : true
        var alternativeAddressValid = alternativeAddressFormValidator ? alternativeAddressFormValidator.isValid() : true

        if (personalFormValid &&
            maritalStatusFormValid &&
            emailFormValid &&
            emergencyContactFormValid &&
            mailingAddressFormValid &&
            phoneFormValid &&
            alternativeAddressValid &&
            driverLicenseFormValid
        ) {
            console.log('save -- all')
            $(".loadingOn").show()
            $('#undoName').val("");
            // profileForm = $('#profileForm')
            //  var t = $("#profileForm").serializeArray();
            $('#profileForm')[0].submit();
        }

    })
    //    $('#reset').click(function() {
    //        console.log('reset')
    //        profileForm = $('#profileForm')
    //         var t = $("#profileForm").serializeArray();
    //        profileForm.submit();
    //    })
})

function checkDuplicate (newBank) {
    var freq = $('#freq').val()
    $('.hidden_freq_update').val(freq)
    var bankArryHave = $(".updateBankForm");
    var bankArry = new Array()
    bankArry.push(newBank)
    bankArryHave.each(function (index) {
        var one = {};
        var t = $(this).serializeArray();
        $.each(t, function () {
            one[this.name] = this.value;
        });
        bankArry.push(one)
    })
    console.log(bankArry)
    var hasDuc = false
    for (var i = 0; i < bankArry.length; i++) {
        for (var n = i + 1; n < bankArry.length; n++) {
            if (bankArry[i].bankCode == bankArry[n].bankCode && bankArry[i].accountNumber == bankArry[n].accountNumber) {
                hasDuc = true
            }
        }
    }
    var arrayObj = {
        bankArray: bankArry,
        hasDuc: hasDuc
    }
    return arrayObj
}

function showPasswordModal () {
    $('#updatePassword')[0].reset()
    $('#updatePassword')
        .data('bootstrapValidator')
        .destroy()
    $('#updatePassword').data('bootstrapValidator', null)
    formPasswordValidator();
}
function clearNoNum (obj) {
    obj.value = obj.value.replace(/[^\d.]/g, ""); //  
    obj.value = obj.value.replace(/^\./g, ""); //
    obj.value = obj.value.replace(/\.{2,}/g, ".");
    obj.value = obj.value.replace(".", "$#$").replace(/\./g, "").replace("$#$", ".");
    obj.value = obj.value.replace(/^(\-)*(\d+)\.(\d\d).*$/, '$1$2.$3');//
    // $(obj).change()
}
function clearNoNumWhole (obj) {
    obj.value = obj.value.replace(/[^\d]/g, ""); //   
    // $(obj).change()
}
function deleteBankAmount (index) {
    // $('#deleteModal').modal('show')
    console.log('delete=' + index)
    var len = $(".bankAccountBlock").length
    if (len <= 1) {
        $(".atLeastOneBankRequiredError").show()
        return false
    }
    $(".atLeastOneBankRequiredError").hide()
    var freq = $('#freq').val()
    var code = $('#code_' + index).val()
    var accountNumber = $('#accountNumber_' + index).text()
    var accountType = $('#accountType_' + index).text()
    var displayAmount = $('#displayAmount_' + index).text()
    console.log(code)
    console.log(accountNumber)
    console.log(accountType)
    console.log(displayAmount)
    if (parseInt(displayAmount) == 0) {
        $(".selectAnotherAsPrimaryError").show()
        return false
    }
    $(".selectAnotherAsPrimaryError").hide()

    $('#hidden_freq_delete').val(freq)
    $('#hidden_accountNumber_delete').val(accountNumber)
    $('#hidden_code_delete').val(code)

    $('#hidden_accountType_delete').val(accountType)
    $('#hidden_displayAmount_delete').val(displayAmount)
    willSubmitFormDelete = $('#deleteBankHidden')
}
function updateBank () {
    var bankArry = $(".updateBankForm");
    var bankLen = bankArry.length;
    var arrayValidate = bankArry.map(function (index) {
        var bankAccountForm = '#bankAccountForm_' + index
        var bankAccountValidator = $(bankAccountForm).data('bootstrapValidator')
        bankAccountValidator.validate()
        if (bankAccountValidator.isValid()) {
            return true
        }
    });
    console.log(bankLen)
    console.log(arrayValidate)
    var freq = $('#freq').val()
    $('.hidden_freq_update').val(freq)
    if (arrayValidate.length == bankLen) {
        var successNum = 0;
        var currentBankIndex = 0;
        var accountList = new Array()
        $(".updateBankForm").each(function (index) {
            var one = {};
            var t = $(this).serializeArray();
            $.each(t, function () {
                one[this.name] = this.value;
            });
            console.log("one", one)
            console.log("string", JSON.stringify(one))
            accountList.push(one)
            // $.ajax({
            //     type: 'POST',
            //     url: '/' + ctx + '/profile/updateBank',
            //     dataType: 'JSON',
            //     contentType: 'application/json;charset=UTF-8',
            //     data: JSON.stringify(one),
            //     success: function (res) {
            //         currentBankIndex++;
            //         if (res.success) {
            //             successNum++;
            //             if (currentBankIndex == bankLen) {
            //                 if (successNum == bankLen) {
            //                     location.href = '/' + ctx + '/profile/profile'
            //                 } else {
            //                     $(".updateMessageFailed").removeClass("hide")
            //                 }
            //             }
            //         }
            //     },
            //     error: function (res) {
            //         console.log(res)
            //         $(".updateMessageFailed").removeClass("hide")
            //     }
            // });
        })
        console.log(accountList)
        $.ajax({
                type: 'POST',
                url: '/' + ctx + '/profile/updateBank',
                dataType: 'JSON',
                contentType: 'application/json;charset=UTF-8',
                data: JSON.stringify(accountList),
                success: function (res) {
                    if (res.success) {
                        location.href = '/' + ctx + '/profile/profile'
                    }
                },
                error: function (res) {
                    console.log(res)
                    $(".updateMessageFailed").removeClass("hide")
                }
            });
    }
}
function undoBank (index) {
    var freq = $('#freq').val()
    var code = $('#code_' + index).val()
    var codeNew = $('#codeNew_' + index).val()
    var accountNumber = $('#accountNumber_' + index).text()
    var accountNumberNew = $('#accountNumberNew_' + index).val()

    var accountAmount = $("#displayAmountNew_" + index + "").val()
    var primaryCheck = $("#primary_" + index + "").is(':checked')
    if (parseInt(accountAmount) == 0 && primaryCheck && code.trim()=='') {
        $(".selectAnotherAsPrimaryError").show()
        return false
    }
    $(".selectAnotherAsPrimaryError").hide()

    $('#hidden_freq_undo').val(freq)
    $('#hidden_code_undo').val(code)
    $('#hidden_codeNew_undo').val(codeNew)
    $('#hidden_accountNumber_undo').val(accountNumber)
    $('#hidden_accountNumberNew_undo').val(accountNumberNew)

    // $('#undoModal').modal('show')
    undoForm = 'undoBank'
    formSelect = $('#undoBankHidden')
}

function changeFreq () {
    $('#changeFreqForm')[0].submit()
}
function clearValidator () {
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
function personalValidator () {
    $('#personalForm').bootstrapValidator({
        live: 'enable',
        excluded: [':disabled'],
        submitButtons: '#savePersonal',
        feedbackIcons: {
            valid: 'fa fa-check ',
            // invalid: 'fa fa-times',
            validating: 'fa fa-refresh'
        },
        fields: {
            namePreNew: {
                trigger: null,
                validators: {
                    stringLength: {
                        max: 1000,
                        message: maxLength25Validator
                    }
                }
            },
            nameFNew: {
                trigger: null,
                validators: {
                    // notEmpty: {
                    //     message: requiredFieldValidator
                    // },
                    stringLength: {
                        max: 60,
                        message: maxLength25Validator
                    }
                }
            },
            nameLNew: {
                trigger: null,
                validators: {
                    // notEmpty: {
                    //     message: requiredFieldValidator
                    // },
                    stringLength: {
                        max: 60,
                        message: maxLength25Validator
                    }
                }
            },
            nameMNew: {
                trigger: null,
                validators: {
                    stringLength: {
                        max: 60,
                        message: maxLength25Validator
                    }
                }
            },
            nameGenNew: {
                trigger: null,
                validators: {
                    stringLength: {
                        max: 1000,
                        message: maxLength25Validator
                    }
                }
            },
        }
    })
}

function maritalStatusValidator () {
    $('#maritalStatusForm').bootstrapValidator({
        live: 'enable',
        submitButtons: '#saveMarital',
        feedbackIcons: {
            valid: 'fa fa-check ',
            // invalid: 'fa fa-times',
            validating: 'fa fa-refresh'
        },
        fields: {
            // maritalStatNew: {
            //     trigger: null,
            //     validators: {
            //         notEmpty: {
            //             message: requiredFieldValidator
            //         }
            //     }
            // }
        }
    })
}

function driverLicenseValidator () {
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
            },
            driversLicStNew: {
                trigger: null,
                validators: {
                    stringLength: {
                        max: 1000,
                        message: maxLength25Validator
                    }
                }
            },
        }
    })
}

function restrictionCodeFormValidator () {
    $('#restrictionCodeForm').bootstrapValidator({
        live: 'enable',
        submitButtons: '#saveRestrict',
        feedbackIcons: {
            valid: 'fa fa-check ',
            // invalid: 'fa fa-times',
            validating: 'fa fa-refresh'
        },
        fields: {
            restrictCdNew: {
                trigger: null,
                validators: {
                    stringLength: {
                        max: 1000,
                        message: maxLength25Validator
                    }
                }
            },
            restrictCdPublicNew: {
                trigger: null,
                validators: {
                    stringLength: {
                        max: 1000,
                        message: maxLength25Validator
                    }
                }
            },
        }
    })
}
function emailFormValidator () {
    $('#emailForm').bootstrapValidator({
        live: 'enable',
        fields: {
            emailNew: {
                trigger: 'change',
                validators: {
                    stringLength: {
                        max: 26,
                        message: maxLength26Validator
                    }
                }
            },
            emailNewVerify: {
                validators: {
                    identical: {
                        field: 'emailNew',
                        message: emailNotMatchValidator
                    }
                }
            },
            hmEmailNew: {
                trigger: 'change',
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
                        message: emailNotMatchValidator
                    },
                    emailAddress: {
                        message: pleaseEnterCorrectFormatValidator
                    }
                }
            }
        }
    })
}

function emergencyContactFormValidator () {
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
                    // notEmpty: {
                    //     message: requiredFieldValidator
                    // },
                    regexp: {
                        regexp: /^[0-9]\d{2}$/,
                        message: pleaseEnterCorrectFormatValidator
                    }
                }
            },
            emerPhoneNbrNew: {
                trigger: null,

                validators: {
                    // notEmpty: {
                    //     message: requiredFieldValidator
                    // },
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

function mailingAddressValidator () {
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
            addrStNew: {
                trigger: null,
                validators: {
                    stringLength: {
                        max: 1000,
                        message: maxLength25Validator
                    }
                }
            },
            addrZipNew: {
                trigger: null,
                validators: {
                    // notEmpty: {
                    //     message: requiredFieldValidator
                    // },
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
function alternativeAddressValidator () {
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
            smrAddrStNew: {
                trigger: null,
                validators: {
                    stringLength: {
                        max: 1000,
                        message: maxLength25Validator
                    }
                }
            },
            smrAddrZipNew: {
                trigger: null,
                validators: {
                    // notEmpty: {
                    //     message: requiredFieldValidator
                    // },
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

function phoneValidator () {
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

function w4InfoValidator () {
    $('#w4InfoForm').bootstrapValidator({
        live: 'enable',
        submitButtons: '#saveW4',
        feedbackIcons: {
            valid: 'fa fa-check ',
            // invalid: 'fa fa-times',
            validating: 'fa fa-refresh'
        },
        fields: {
            // maritalStatTaxNew: {
            //     trigger: null,
            //     validators: {
            //         notEmpty: {
            //             message: requiredFieldValidator
            //         }
            //     }
            // },
            // nbrTaxExemptsNew: {
            //     trigger: null,
            //     validators: {
            //         // notEmpty: {
            //         //     message: requiredFieldValidator
            //         // },
            //         regexp: {
            //             regexp: /^[0-9]\d{0,1}$/,
            //             message: pleaseEnterCorrectFormatValidator
            //         }
            //     }
            // },
            w4FileStatNew: {
                trigger: null,
                validators: {
                    notEmpty: {
                        message: requiredFieldValidator
                    }
                }
            },
            w4MultiJobNew: {
                trigger: null,
                validators: {
                    notEmpty: {
                        message: requiredFieldValidator
                    }
                }
            },
            w4NbrChldrnNew: {
                trigger: null,
                validators: {
                    notEmpty: {
                        message: requiredFieldValidator
                    },
                    digits: {
                        message: pleaseEnterWholePositiveNum
                    }
                }
            },
            w4NbrOthrDepNew: {
                trigger: null,
                validators: {
                    notEmpty: {
                        message: requiredFieldValidator
                    },
                    digits: {
                        message: pleaseEnterWholePositiveNum
                    }
                }
            },
            w4OthrIncAmtNew: {
                trigger: null,
                validators: {
                    notEmpty: {
                        message: requiredFieldValidator
                    }
                }
            },
            w4OthrDedAmtNew: {
                trigger: null,
                validators: {
                    notEmpty: {
                        message: requiredFieldValidator
                    }
                }
            },
            w4OthrExmptAmtNew: {
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

function bankAccountValidator () {
    var arrayBankLength = $('.bankAccountBlock').length
    for (var i = 0; i < arrayBankLength; i++) {
        $('#bankAccountForm_' + i).bootstrapValidator({
            live: 'enable',
            excluded: [':disabled', ':hidden', ':not(:visible)'],
            // submitButtons: '.saveUpdateBankBtn',
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
function bankAccountAddValidator () {
    $('#addBankAccountForm').bootstrapValidator({
        live: 'enable',
        // submitButtons: '#saveNewBank',
        excluded: [':disabled', ':hidden', ':not(:visible)'],
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

function initSessionPws () {
    const psd = $('#sessionPsd').val()
    if (psd !== '') {
        sessionStorage.setItem('sessionPws', psd)
    }
}
