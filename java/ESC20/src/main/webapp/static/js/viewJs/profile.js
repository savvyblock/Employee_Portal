let bank01, bank02
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
    $('.icheckRadioBank').on('click', function(event) {
        if ($(this).is(':checked')) {
            $(".bankAccountBlock").removeClass("asPrimary")
            $(this).parents(".bankAccountBlock").addClass("asPrimary")
            let indexBank = $('.icheckRadioBank').index(this)
            $('.icheckRadioBank').each(function(index) {
                if (index != indexBank) {
                    $(this).prop('checked', false)
                }
            })
        }
    })
    $('.icheckRadioBank').keypress(function(e) {
        console.log(e)
        var eCode = e.keyCode ? e.keyCode : e.which ? e.which : e.charCode
        if (eCode == 13) {
            $(this).click()
            let indexBank = $('.icheckRadioBank').index(this)
            $('.icheckRadioBank').each(function(index) {
                if (index != indexBank) {
                    $(this).prop('checked', false)
                }
            })
        }
    })
    let bankInputName, bankInputCode
    $('.edit-btn').click(function() {
        $('.addBankForm').hide()
        $('.add-bank-btn').show()
        $('.profile-item').removeClass('activeEdit')
        $(this)
            .parents('.profile-item')
            .addClass('activeEdit')
        let groupSize = $(this)
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
    // $('.save-btn').click(function() {
    //     $(this)
    //         .parents('.profile-item')
    //         .removeClass('activeEdit')
    // })
    $('.cancel-add-btn').click(function() {
        $('.addBankForm').hide()
        $('.add-bank-btn').show()
    })
    $('.add-bank-btn').click(function() {
        let arrayBankLength = $('.usedBank').length
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
        let bankAccountValidator = $('#addBankAccountForm').data(
            'bootstrapValidator'
        )
        bankAccountValidator.validate()
        console.log(bankAccountValidator.isValid())
        if (bankAccountValidator.isValid()) {
            var freq = $('#freq').val()
            var saveBankDescription = $('#saveBankDescription').val()
            var saveBankCode = $('#saveBankCode').val()
            var saveBankAccountNumber = $('#saveBankAccountNumber').val()
            var saveBankDisplayLabel = $('#saveBankDisplayLabel').val()
            var saveBankDisplayAmount = $('#saveBankDisplayAmount').val()

            $('#hiddenfreq').val(freq)
            $('#hiddendescription').val(saveBankDescription)
            $('#hiddensubCode').val(saveBankCode)
            $('#hiddenaccountNumber').val(saveBankAccountNumber)
            $('#hiddendisplayLabel').val(saveBankDisplayLabel)
            $('#hiddendisplayAmount').val(saveBankDisplayAmount)

            $('#saveBankHidden').submit()
        }
    })

    $('.getBank').click(function() {
        var page = {
            currentPage: 1,
            perPageRows: 10
        }
        let that = this
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
                        "<tr><td data-localize='profile.routingNumber' data-localize-location='scope'>"
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
                        " <td data-localize='profile.bankName' data-localize-location='scope'>" +
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

                $('.bankNumberBtn').click(function() {
                    let number = $(this).text()
                    let code = $(this).val()
                    let name = $(this).attr('data-title')
                    console.log(number)
                    console.log(name)
                    console.log(code)
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
    $('#searchBankBtn').click(function() {
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
        let that = this
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
                            "<tr><td data-localize='profile.routingNumber' data-localize-location='scope'>"
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
                            " <td data-localize='profile.bankName' data-localize-location='scope'>" +
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

                    $('.bankNumberBtn').click(function() {
                        let number = $(this).text()
                        let code = $(this).val()
                        let name = $(this).attr('data-title')
                        console.log(number)
                        console.log(name)
                        console.log(code)
                        bankInputName.val(name).change()
                        bankInputBankCode.val(code)
                        bankInputCode.val(number).change()
                        $('#selectBankModal').modal('hide')
                    })
                } else {
                    $('#bankTable tbody').empty()
                    let noResult = `<tr><td colspan="2"> <span data-localize="label.noData"></span></td></tr>`
                    $('#bankTable tbody').append(noResult)
                }

                setGlobal()
            },
            error: function(e) {
                console.log(e)
                $('#bankTable tbody').empty()
                let noResult = `<tr><td colspan="2"> <span data-localize="label.noData"></span></td></tr>`
                $('#bankTable tbody').append(noResult)
                setGlobal()
            }
        })
    })

    $('#undoNameRequest').click(function() {
        $('#undoModal').modal('show')
        formSelect = $('#deleteNameRequest')
    })
    $('#undoMaritalRequest').click(function(e) {
        e.preventDefault()
        $('#undoModal').modal('show')
        formSelect = $('#deleteMaritalRequest')
    })
    $('#undoDriverLicense').click(function() {
        $('#undoModal').modal('show')
        formSelect = $('#deleteDriversLicense')
    })
    $('#undoRestriction').click(function() {
        $('#undoModal').modal('show')
        formSelect = $('#deleteRestrictionCodesRequest')
    })
    $('#undoEmail').click(function() {
        $('#undoModal').modal('show')
        formSelect = $('#deleteEmail')
    })
    $('#undoEmergencyContact').click(function() {
        $('#undoModal').modal('show')
        formSelect = $('#deleteEmergencyContact')
    })
    $('#undoMailingAddress').click(function() {
        $('#undoModal').modal('show')
        formSelect = $('#deleteMailAddr')
    })
    $('#undoAlternative').click(function() {
        $('#undoModal').modal('show')
        formSelect = $('#deleteAltMailAddr')
    })
    $('#undoPhoneNumber').click(function() {
        $('#undoModal').modal('show')
        formSelect = $('#deletePhone')
    })
    $('#undoW4').click(function() {
        $('#undoModal').modal('show')
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
function deleteBankAmount(index) {
    $('#deleteModal').modal('show')
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
function updateBank(index) {
    console.log('updateBank=' + index)
    let bankAccountForm = '#bankAccountForm_' + index
    let bankAccountValidator = $(bankAccountForm).data('bootstrapValidator')
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

        $('#hidden_freq_update').val(freq)
        $('#hidden_code_update').val(code)
        $('#hidden_codeNew_update').val(codeNew)
        $('#hidden_accountNumber_update').val(accountNumber)
        $('#hidden_accountNumberNew_update').val(accountNumberNew)

        $('#hidden_accountType_update').val(accountType)
        $('#hidden_accountTypeNew_update').val(accountTypeNew)
        $('#hidden_displayAmount_update').val(displayAmount)
        $('#hidden_displayAmountNew_update').val(displayAmountNew)
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

    $('#undoModal').modal('show')
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
                trigger: 'blur keyup',

                validators: {
                    notEmpty: {
                        message: 'validator.requiredField'
                    }
                }
            },
            nameLNew: {
                trigger: 'blur keyup',

                validators: {
                    notEmpty: {
                        message: 'validator.requiredField'
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
            no: {
                trigger: 'blur keyup',

                validators: {
                    notEmpty: {
                        message: 'validator.requiredField'
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
                trigger: 'blur keyup',
                validators: {
                    stringLength: {
                        max: 19,
                        message: 'validator.maxLength19'
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
                trigger: 'blur keyup',

                validators: {
                    notEmpty: {
                        message: 'validator.requiredField'
                    }
                }
            }
        }
    })
}

function emailFormValidator() {
    $('#emailForm').bootstrapValidator({
        live: 'enable',
        submitButtons: '#saveEmail',
        feedbackIcons: {
            valid: 'fa fa-check ',
            // invalid: 'fa fa-times',
            validating: 'fa fa-refresh'
        },
        fields: {
            emailNew: {
                trigger: 'blur keyup',
                validators: {
                    notEmpty: {
                        message: 'validator.requiredField'
                    },
                    emailAddress: {
                        message: 'validator.pleaseEnterCorrectFormat'
                    }
                }
            },
            hmEmailNew: {
                trigger: 'blur keyup',
                validators: {
                    emailAddress: {
                        message: 'validator.pleaseEnterCorrectFormat'
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
                trigger: 'blur keyup',
                stringLength: {
                    max: 26,
                    message: 'validator.maxLength26'
                }
            },
            emerPhoneAcNew: {
                trigger: 'blur keyup',
                validators: {
                    notEmpty: {
                        message: 'validator.requiredField'
                    },
                    regexp: {
                        regexp: /^[0-9]\d{2}$/,
                        message: 'validator.pleaseEnterCorrectFormat'
                    }
                }
            },
            emerPhoneNbrNew: {
                trigger: 'blur keyup',

                validators: {
                    notEmpty: {
                        message: 'validator.requiredField'
                    },
                    regexp: {
                        regexp: /^[0-9][\d]{2}[\-]?[\d]{4}$/,
                        message: 'validator.pleaseEnterCorrectFormat'
                    }
                }
            },
            emerPhoneExtNew: {
                trigger: 'blur keyup',
                validators: {
                    regexp: {
                        regexp: /^[\d]{0,4}$/,
                        message: 'validator.pleaseEnterCorrectFormat'
                    }
                }
            },
            emerRelNew: {
                trigger: 'blur keyup',
                stringLength: {
                    max: 25,
                    message: 'validator.maxLength25'
                }
            },
            emerNoteNew: {
                trigger: 'blur keyup',
                stringLength: {
                    max: 25,
                    message: 'validator.maxLength25'
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
                trigger: 'blur keyup',
                validators: {
                    regexp: {
                        regexp: /^[0-9]\d{0,7}$/,
                        message: 'validator.pleaseEnterCorrectFormat'
                    }
                }
            },
            addrStrNew: {
                trigger: 'blur keyup',
                validators: {
                    stringLength: {
                        max: 20,
                        message: 'validator.maxLength20'
                    }
                }
            },
            addrAptNew: {
                trigger: 'blur keyup',
                validators: {
                    stringLength: {
                        max: 7,
                        message: 'validator.maxLength7'
                    }
                }
            },
            addrCityNew: {
                trigger: 'blur keyup',
                validators: {
                    stringLength: {
                        max: 20,
                        message: 'validator.maxLength20'
                    }
                }
            },
            addrZipNew: {
                trigger: 'blur keyup',
                validators: {
                    notEmpty: {
                        message: 'validator.requiredField'
                    },
                    regexp: {
                        regexp: /^[0-9]\d{4}$/,
                        message: 'validator.pleaseEnterCorrectFormat'
                    }
                }
            },
            addrZip4New: {
                trigger: 'blur keyup',
                validators: {
                    regexp: {
                        regexp: /^[0-9][\d]{3}$/,
                        message: 'validator.pleaseEnterCorrectFormat'
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
                trigger: 'blur keyup',
                validators: {
                    regexp: {
                        regexp: /^[0-9]\d{0,7}$/,
                        message: 'validator.pleaseEnterCorrectFormat'
                    }
                }
            },
            smrAddrStrNew: {
                trigger: 'blur keyup',
                validators: {
                    stringLength: {
                        max: 20,
                        message: 'validator.maxLength20'
                    }
                }
            },
            smrAddrAptNew: {
                trigger: 'blur keyup',
                validators: {
                    stringLength: {
                        max: 7,
                        message: 'validator.maxLength7'
                    }
                }
            },
            smrAddrCityNew: {
                trigger: 'blur keyup',
                validators: {
                    stringLength: {
                        max: 20,
                        message: 'validator.maxLength20'
                    }
                }
            },
            smrAddrZipNew: {
                trigger: 'blur keyup',
                validators: {
                    notEmpty: {
                        message: 'validator.requiredField'
                    },
                    regexp: {
                        regexp: /^[0-9]\d{4}$/,
                        message: 'validator.pleaseEnterCorrectFormat'
                    }
                }
            },
            smrAddrZip4New: {
                trigger: 'blur keyup',
                validators: {
                    regexp: {
                        regexp: /^[0-9][\d]{3}$/,
                        message: 'validator.pleaseEnterCorrectFormat'
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
                trigger: 'blur keyup',
                validators: {
                    regexp: {
                        regexp: /^[0-9]\d{2}$/,
                        message: 'validator.pleaseEnterCorrectFormat'
                    }
                }
            },
            phoneNbrNew: {
                trigger: 'blur keyup',
                validators: {
                    regexp: {
                        regexp: /^[0-9][\d]{2}[\-]?[\d]{4}$/,
                        message: 'validator.pleaseEnterCorrectFormat'
                    }
                }
            },
            phoneAreaCellNew: {
                trigger: 'blur keyup',
                validators: {
                    regexp: {
                        regexp: /^[0-9]\d{2}$/,
                        message: 'validator.pleaseEnterCorrectFormat'
                    }
                }
            },
            phoneNbrCellNew: {
                trigger: 'blur keyup',
                validators: {
                    regexp: {
                        regexp: /^[0-9][\d]{2}[\-]?[\d]{4}$/,
                        message: 'validator.pleaseEnterCorrectFormat'
                    }
                }
            },
            phoneAreaBusNew: {
                trigger: 'blur keyup',
                validators: {
                    regexp: {
                        regexp: /^[0-9]\d{2}$/,
                        message: 'validator.pleaseEnterCorrectFormat'
                    }
                }
            },
            phoneNbrBusNew: {
                trigger: 'blur keyup',
                validators: {
                    regexp: {
                        regexp: /^[0-9][\d]{2}[\-]?[\d]{4}$/,
                        message: 'validator.pleaseEnterCorrectFormat'
                    }
                }
            },
            busPhoneExtNew: {
                trigger: 'blur keyup',

                validators: {
                    regexp: {
                        regexp: /^[\d]{0,4}$/,
                        message: 'validator.pleaseEnterCorrectFormat'
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
                trigger: 'blur keyup',

                validators: {
                    notEmpty: {
                        message: 'validator.requiredField'
                    }
                }
            },
            nbrTaxExemptsNew: {
                trigger: 'blur keyup',

                validators: {
                    notEmpty: {
                        message: 'validator.requiredField'
                    },
                    regexp: {
                        regexp: /^[0-9]\d{0,1}$/,
                        message: 'validator.pleaseEnterCorrectFormat'
                    }
                }
            }
        }
    })
}

function bankAccountValidator() {
    let arrayBankLength = $('.bankAccountBlock').length
    for (let i = 0; i < arrayBankLength; i++) {
        $('#bankAccountForm_' + i).bootstrapValidator({
            live: 'enable',
            submitButtons: '.saveUpdateBankBtn',
            feedbackIcons: {
                valid: 'fa fa-check ',
                // invalid: 'fa fa-times',
                validating: 'fa fa-refresh'
            },
            fields: {
                description: {
                    trigger: 'blur keyup change',
                    validators: {
                        notEmpty: {
                            message: 'validator.requiredField'
                        }
                    }
                },
                subCode: {
                    trigger: 'blur keyup change',
                    validators: {
                        notEmpty: {
                            message: 'validator.requiredField'
                        }
                    }
                },
                accountNumber: {
                    trigger: 'blur keyup',
                    validators: {
                        notEmpty: {
                            message: 'validator.requiredField'
                        },
                        regexp: {
                            regexp: /^[0-9]\d{0,16}$/,
                            message: 'validator.pleaseEnterCorrectFormat'
                        }
                    }
                },
                displayLabel: {
                    trigger: 'blur keyup',
                    validators: {}
                },
                displayAmount: {
                    trigger: 'blur keyup',
                    validators: {
                        regexp: {
                            regexp: /^\d+$|^\d+[\.]{1}\d{1,2}$/,
                            message: 'validator.pleaseEnterCorrectFormat'
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
                trigger: 'blur keyup change',
                validators: {
                    notEmpty: {
                        message: 'validator.requiredField'
                    }
                }
            },
            subCode: {
                trigger: 'blur keyup change',
                validators: {
                    notEmpty: {
                        message: 'validator.requiredField'
                    }
                }
            },
            accountNumber: {
                trigger: 'blur keyup',
                validators: {
                    notEmpty: {
                        message: 'validator.requiredField'
                    },
                    regexp: {
                        regexp: /^[0-9]\d{0,16}$/,
                        message: 'validator.pleaseEnterCorrectFormat'
                    }
                }
            },
            displayLabel: {
                trigger: 'blur keyup',
                validators: {
                    notEmpty: {
                        message: 'validator.requiredField'
                    }
                }
            },
            displayAmount: {
                trigger: 'blur keyup',
                validators: {
                    regexp: {
                        regexp: /^\d+$|^\d+[\.]{1}\d{1,2}$/,
                        message: 'validator.pleaseEnterCorrectFormat'
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
