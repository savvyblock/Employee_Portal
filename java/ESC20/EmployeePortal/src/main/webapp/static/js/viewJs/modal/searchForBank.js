var nowPage = 1, total
var bankInputName, bankInputCode
var getBankBtn
$(function () {
    $('.getBank').click(function () {
        getBankBtn = this
        goPage(1)
    })
    $('#searchBankBtn').on('click', function () {
        searchPage(1)
    })
    $('#selectBankModal').on('click','.bankNumberBtn', function () {
        bankInputName = $(getBankBtn)
            .parents(".bankPart")
            .find('.form-control.name').change()
        bankInputBankCode = $(getBankBtn)
            .parents(".bankPart")
            .find('.form-control.bankcode').change()
        bankInputCode = $(getBankBtn)
            .parent()
            .find('.form-control.code').change()
        newBankCode = $(getBankBtn).parents('.addBankForm').find("#newBankCode")

        var number = $(this).text()
        var code = $(this).val()
        var name = $(this).attr('data-title')
        console.log(number)
        console.log(code)
        console.log(name)
        newBankCode.val(code).change()
        bankInputName.val(name).change()
        bankInputBankCode.val(code).change()
        bankInputCode.val(number).change()

        $('#selectBankModal').modal('hide')
    })
})
function changePage (num) {

    var searchCode = $('#codeCriteriaSearchCode').val()
    var searchDescription = $('#codeCriteriaSearchDescription').val()
    if ((!searchCode || searchCode == '') && (!searchDescription || searchDescription == '')) {

        if (num == 1) {
            goPage(num)
        }
        if (num == 2) {
            goPage(nowPage - 1)
        }
        if (num == 3) {
            goPage(nowPage + 1)
        }
        if (num == 4) {
            goPage(total)
        }
        if (num == 0) {
            num = $('#pageNow').val();
            goPage(num)
        }
    } else {

        if (num == 1) {
            searchPage(num)
        }
        if (num == 2) {
            searchPage(nowPage - 1)
        }
        if (num == 3) {
            searchPage(nowPage + 1)
        }
        if (num == 4) {
            searchPage(total)
        }
        if (num == 0) {
            num = $('#pageNow').val();
            searchPage(num)
        }
    }

}

function updateHtmlPage (currentPage, totalRows, totalPages) {
    if (totalRows != 0) {
        $('#bankTable  tbody').empty()
    }
    $("#totalBank").text(totalRows)
    $(".totalPate").text(totalPages)
    nowPage = currentPage
    total = totalPages
    $(".selectPage").empty()
    for (var i = 1, len = totalPages; i <= len; i++) {
        var selected = ""
        if (nowPage == i) {
            selected = 'selected="selected"'
        }
        var option = '<option value="' + i + '" ' + selected + '>' + i + '</option>'
        $(".selectPage").append(option)
    }
    if (nowPage <= 1) {
        $(".firstPate").attr("disabled", "disabled")
        $(".prevPage").attr("disabled", "disabled")
    } else {
        $(".firstPate").removeAttr("disabled")
        $(".prevPage").removeAttr("disabled")
    }
    if (nowPage == totalPages) {
        $(".nextPate").attr("disabled", "disabled")
        $(".lastPate").attr("disabled", "disabled")
    } else {
        $(".nextPate").removeAttr("disabled")
        $(".lastPate").removeAttr("disabled")
    }
}

function goPage (num) {
    var page = {
        currentPage: num,
        perPageRows: 10
    }
    $.ajax({
        type: 'POST',
        dataType: 'json',
        url: urlMain + '/profile/getAllBanks',
        data: JSON.stringify(page),
        contentType: 'application/json;charset=UTF-8',
        success: function (result) {
            console.log(result)
            //$('#bankTable').find('tr').remove();
            updateHtmlPage(result.page.currentPage, result.page.totalRows, result.page.totalPages)
            var res = result.result
            for (var p in res) {
                var bankTr =
                    "<tr><td scope='" + routingNumberLabel + "'>"
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
                    " <td scope='" + bankNameLabel + "'>" +
                    res[p].bankName +
                    '</td> </tr>'
                $('#bankTable').append(bankTr)
            }

        },
        error: function (e) {
            console.log(e)
            updateHtmlPage(1, 0, 0)
        }
    })
}

function searchPage (num) {
    var page = {
        currentPage: num,
        perPageRows: 10
    }

    var searchCode = $('#codeCriteriaSearchCode').val()
    var searchDescription = $('#codeCriteriaSearchDescription').val()

    var criteria = {
        searchCode: searchCode,
        searchDescription: searchDescription
    }

    var searchCriteria = { page: page, criteria: criteria }
    $.ajax({
        type: 'POST',
        dataType: 'json',
        url: urlMain + '/profile/searchBanks',
        data: JSON.stringify(searchCriteria),
        contentType: 'application/json;charset=UTF-8',
        success: function (result) {
            console.log(result)
            updateHtmlPage(result.page.currentPage, result.page.totalRows, result.page.totalPages)
            $('#bankTable  tbody').empty()
            if (result.result && result.result.length > 0) {
                var res = result.result
                for (var p in res) {
                    var bankTr =
                        "<tr><td scope='" + routingNumberLabel + "'>"
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
                        " <td scope='" + bankNameLabel + "'>" +
                        res[p].bankName +
                        '</td> </tr>'
                    $('#bankTable').append(bankTr)
                }

            } else {
                $('#bankTable tbody').empty()
                var noResult = '<tr><td colspan="2"> <span>' + noDataLabel + '</span></td></tr>'
                $('#bankTable tbody').append(noResult)
            }

        },
        error: function (e) {
            console.log(e)
            $('#bankTable tbody').empty()
            var noResult = '<tr><td colspan="2"> <span>' + noDataLabel + '</span></td></tr>'
            console.log(noResult)
            updateHtmlPage(1, 0, 0)
            $('#bankTable tbody').append(noResult)
        }
    })
}
