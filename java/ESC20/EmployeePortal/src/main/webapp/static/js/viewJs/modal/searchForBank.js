var nowPage = 1,total
var bankInputName, bankInputCode
var getBankBtn
$(function(){
    $('.getBank').click(function() {
        getBankBtn = this
        goPage(1)
    })
    $('#searchBankBtn').on('click',function() {
        searchPage(1)
    })
})
function changePage(num){
    var searchCode = $('#codeCriteriaSearchCode').val()
    var searchDescription = $('#codeCriteriaSearchDescription').val()
    if((!searchCode||searchCode=='') && (!searchDescription||searchDescription=='')){
        if(num == 1){
            goPage(num)
        }
        if(num == 2){
            goPage(nowPage - 1)
        }
        if(num == 3){
            goPage(nowPage + 1)
        }
        if(num == 4){
            goPage(total)
        }
        
    }else{
        if(num == 1){
            searchPage(num)
        }
        if(num == 2){
            searchPage(nowPage - 1)
        }
        if(num == 3){
            searchPage(nowPage + 1)
        }
        if(num == 4){
            searchPage(total)
        }
    }
    
}

function updateHtmlPage(currentPage,totalRows,totalPages){
    if(totalRows != 0){
        $('#bankTable  tbody').empty()
    }
    $("#totalBank").text(totalRows)
    $(".totalPate").text(totalPages)
    nowPage = currentPage
    total = totalPages
    $(".selectPage").empty()
    for(var i =1,len = totalPages;i<=len;i++){
        var selected = ""
        if(nowPage == i){
            selected = 'selected="selected"'
        }
        var option = '<option value="'+i+'" '+selected+'>'+i+'</option>'
        $(".selectPage").append(option)
    }
    if(nowPage<=1){
        $(".firstPate").attr("disabled","disabled")
        $(".prevPage").attr("disabled","disabled")
    }else{
        $(".firstPate").removeAttr("disabled")
        $(".prevPage").removeAttr("disabled")
    }
    if(nowPage == totalPages){
        $(".nextPate").attr("disabled","disabled")
        $(".lastPate").attr("disabled","disabled")
    }else{
        $(".nextPate").removeAttr("disabled")
        $(".lastPate").removeAttr("disabled")
    }
}

function goPage(num){
    var page = {
        currentPage: num,
        perPageRows: 10
    }
    $.ajax({
        type: 'POST',
        dataType: 'json',
        url: urlMain +'/profile/getAllBanks',
        data: JSON.stringify(page),
        contentType: 'application/json;charset=UTF-8',
        success: function(result) {
            console.log(result)
            //$('#bankTable').find('tr').remove();
            updateHtmlPage(result.page.currentPage,result.page.totalRows,result.page.totalPages)
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
            bankInputName = $(getBankBtn)
                .parent()
                .find('.form-control.name')
            bankInputBankCode = $(getBankBtn)
                .parent()
                .find('.form-control.bankcode')
            bankInputCode = $(getBankBtn)
                .parent()
                .find('.form-control.code')
            bankInputNewCode = $(getBankBtn)
                .parents('.profile-desc')
                .find('.bankNewCode')
            newBankCode = $(getBankBtn).parent().find("#newBankCode")
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
            updateHtmlPage(1,0,0)
        }
    })
}

function searchPage(num){
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
        success: function(result) {
            console.log(result)
            updateHtmlPage(result.page.currentPage,result.page.totalRows,result.page.totalPages)
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
                bankInputName = $(getBankBtn)
                    .parent()
                    .find('.form-control.name')
                bankInputBankCode = $(getBankBtn)
                    .parent()
                    .find('.form-control.bankcode')
                bankInputCode = $(getBankBtn)
                    .parent()
                    .find('.form-control.code')
                newBankCode = $(getBankBtn).parent().find("#newBankCode")
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
            console.log(noResult)
            updateHtmlPage(1,0,0)
            $('#bankTable tbody').append(noResult)
        }
    })
}