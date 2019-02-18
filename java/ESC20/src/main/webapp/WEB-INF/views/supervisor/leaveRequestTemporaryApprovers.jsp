<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %> <%@ page
language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<html lang="en">
    <head>
        <title data-localize="headTitle.setTempApprovers"></title>
        <meta name="viewport" content="width=device-width, initial-scale=1" />
        <link rel="stylesheet" href="/<%=request.getContextPath().split("/")[1]%>/css/fullcalendar.min.css" />
        <%@ include file="../commons/header.jsp"%>
    </head>
    <body class="hold-transition sidebar-mini">
        <div class="wrapper">
            <%@ include file="../commons/bar.jsp"%>
			
            <main class="content-wrapper">
                    <section class="content">
                    <h2 class="clearfix section-title">
                        <span data-localize="title.setTemporaryApprovers"></span>
                    </h2>
                    <div class="container-fluid">
                            <form
                                    class="no-print searchForm"
                                    action="nextLevelFromTempApprovers"
                                    id="filterSupervisor"
                                    method="POST"
                                >
                                <span hidden="hidden" id="chainValue">${chain}</span>
                                    <input hidden="hidden" id="chain" class="chain" name="chain" type="text" value="">
                                    <div class="form-group in-line flex-auto">
                                        <label class="form-title"><span data-localize="label.directReportSupervisor"></span>:</label>
                                        <select  class="form-control" name="selectEmpNbr" onchange="changeLevel()"
                                            id="selectEmpNbr">
                                            <c:forEach var="item" items="${directReportEmployee}" varStatus="count">
                                                <option value="${item.employeeNumber}">${item.selectOptionLabel}</option>
                                            </c:forEach>
                                        </select>
                                    </div>
                                    <div class="form-group in-line flex-auto">
                                            <button type="button" class="btn btn-primary disabled" 
                                                id="prevLevel" 
                                                data-localize="label.previousLevel"
                                                disabled
                                                ></button>
                                            <button type="button" class="btn btn-primary  disabled" 
                                            id="nextLevel" 
                                            data-localize="label.nextLevel"
                                            disabled
                                            ></button>
                                    </div>
                                </form>
                                <form hidden="hidden" action="previousLevelFromTempApprovers" id="previousLevel" method="POST">
                                        <input hidden="hidden" type="text" value="${level}" name="level" id="preLevel">
                                        <input hidden="hidden" name="chain" type="text" value="" id="preChain">
                                </form>
                                <div class="showSelectSupervisor">
                                        <label class="form-title"><span data-localize="label.supervisorHierarchy"></span>: </label>
                                        <c:forEach var="item" items="${chain}" varStatus="status">
                                                <b> ${item.employeeNumber}:${item.lastName},${item.firstName}</b>
                                                <c:if test="${!status.last}"> ➝ </c:if>
                                        </c:forEach>
                                </div>
                        <br />
                        <div class="content-white EMP-detail">
                            <div class="mb-3">
                                <span data-localize="label.temporaryApproversFor"></span>
                                <b class="highlight">
                                        <c:forEach var="item" items="${chain}" varStatus="status">
                                                <c:if test="${status.last}"><span id="currentEmployee">${item.employeeNumber}</span>:${item.lastName},${item.firstName} </c:if>
                                        </c:forEach>
                                    </b
                                >
                            </div>
                            <form action="saveTempApprovers" id="saveTempApprovers" method="POST">
                                    <input hidden="hidden" id="chainString" class="chain" name="chain" type="text" value="">
                                    <input hidden="hidden" id="empNbrForm" name="empNbr" type="text" value="">
                                    <input hidden="hidden" id="approverJson" name="approverJson" type="text" value="">
                                <table
                                    class="table border-table setApprovers-list responsive-table"
                                >
                                    <thead>
                                        <tr>
                                            <th data-localize="setTemporaryApprovers.rowNbr"></th>
                                            <th data-localize="setTemporaryApprovers.temporaryApprover"></th>
                                            <th data-localize="setTemporaryApprovers.fromDate"></th>
                                            <th data-localize="setTemporaryApprovers.toDate"></th>
                                            <th data-localize="setTemporaryApprovers.delete"></th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                            <c:forEach var="tem" items="${tmpApprovers}" varStatus="status">
                                                    <tr class="">
                                                            <td
                                                                class="countIndex"
                                                                data-localize="setTemporaryApprovers.rowNbr" data-localize-location="scope"
                                                            >
                                                                ${status.index + 1}
                                                            </td>
                                                            <td
                                                            data-localize="setTemporaryApprovers.temporaryApprover" data-localize-location="scope"
                                                            >${tem.tmpApprvrEmpNbr}</td>
                                                            <td data-localize="setTemporaryApprovers.fromDate" data-localize-location="scope">${tem.datetimeFrom}</td>
                                                            <td data-localize="setTemporaryApprovers.toDate" data-localize-location="scope">${tem.datetimeTo}</td>
                                                            <td data-localize="setTemporaryApprovers.delete" data-localize-location="scope">
                                                                <button
                                                                    type="button"
                                                                    class="a-btn"
                                                                    data-localize="label.delete"
                                                                    data-localize-location="title"
                                                                >
                                                                    <i
                                                                        class="fa fa-trash"
                                                                        onclick="deleteApprover()"
                                                                    ></i>
                                                                </button>
                                                            </td>
                                                        </tr>
                                            </c:forEach>
                                        
                                        <tr class="approver_tr">
                                            <td
                                                class="countIndex"
                                                data-localize="setTemporaryApprovers.rowNbr"
                                                data-localize-location="scope"
                                            >
                                                <span id="firstRow"></span>
                                            </td>
                                            <td data-localize="setTemporaryApprovers.temporaryApprover" data-localize-location="scope">
                                                <div class="form-group">
                                                    <input
                                                        class="form-control empControl"
                                                        type="text"
                                                        title=""
                                                        data-localize="setTemporaryApprovers.temporaryApprover"
                                                        name=""
                                                        id="name_01"
                                                    />
                                                </div>
                                            </td>
                                            <td data-localize="setTemporaryApprovers.fromDate" data-localize-location="scope">
                                                <div class="form-group">
                                                    <input
                                                        class="form-control date-control dateFromControl"
                                                        title=""
                                                        data-title="from"
                                                        data-localize="setTemporaryApprovers.fromDate"
                                                        type="text"
                                                        name="temporaryApprovers[${row.index}].fromDateString"
                                                        id="fromDate_01"
                                                        readonly
                                                    />
                                                </div>
                                            </td>
                                            <td data-localize="setTemporaryApprovers.toDate" data-localize-location="scope">
                                                <div class="form-group">
                                                    <input
                                                        class="form-control  date-control dateToControl"
                                                        title=""
                                                        data-localize="setTemporaryApprovers.toDate"
                                                        type="text"
                                                        data-title="to"
                                                        name="temporaryApprovers[${row.index}].toDateString"
                                                        id="toDate_01"
                                                        readonly
                                                    />
                                                </div>
                                            </td>
                                            <td data-localize="setTemporaryApprovers.delete" data-localize-location="scope">
                                                <button
                                                    type="button"
                                                    class="a-btn"
                                                    onclick="deleteRow(this)"
                                                    data-localize="label.delete"
                                                    data-localize-location="title"
                                                >
                                                    <i class="fa fa-trash"></i>
                                                </button>
                                            </td>
                                        </tr>
                                        <tr>
                                            <td class="no-title" colspan="5" data-localize="label.add" data-localize-location="scope">
                                                <button
                                                    type="button"
                                                    class="a-btn add-new-row"
                                                    data-localize="label.add"
                                                    data-localize-location="title"
                                                >
                                                    <i class="fa fa-plus"></i>
                                                    <span data-localize="label.add"></span>
                                                </button>
                                            </td>
                                        </tr>
                                    </tbody>
                                </table>
                                <p class="error-hint hide" id="noResultError" data-localize="validator.noResultError"></p>
                                <p class="error-hint hide" id="errorComplete" data-localize="validator.pleaseCompleteForm"></p>
                                <div class="text-right mt-3">
                                    <button
                                        type="button"
                                        class="btn btn-primary"
                                        title=""
                                        id="saveSet"
                                        data-localize="label.save"
                                    >
                                    </button>
                                    <button
                                        type="button"
                                        class="btn btn-secondary"
                                        title=""
                                        data-localize="label.reset"
                                    >
                                    </button>
                                </div>
                            </form>
                        </div>
                    </div>
                </section>
            </main>
        </div>
        <%@ include file="../commons/footer.jsp"%>
    </body>
    <script>
        var directReportEmployee = eval(${directReportEmployee});
        console.log(directReportEmployee)
        var chain = eval(${chain});
        var employeeList = directReportEmployee;
        $(function() {
            changeLevel()
            initDateControl()
            judgeContent()
            initialCompleteList()
            let level = $("#level").val()
            let chainString = JSON.stringify(chain)
            let empNbr = $("#currentEmployee").text()
            let lengthNow = $('.setApprovers-list tbody tr').length
            if($("#firstRow")){
                $("#firstRow").text(lengthNow - 1)
            }
            $("#empNbr").val(empNbr)
            $(".chain").val(chainString)
            if(chain.length>1){
                $("#prevLevel").removeClass("disabled").removeAttr("disabled");
            }else{
                $("#prevLevel").addClass("disabled").attr('disabled',"true");
            }
            $("#nextLevel").click(function(){
                $("#chain").val(chainString)
                $("#filterSupervisor")[0].submit()  
            })
            $("#prevLevel").click(function(){
                $("#preChain").val(chainString)
                $("#previousLevel")[0].submit()  
            })
            $('.add-new-row').click(function() {
                judgeContent()
                let  trLen = $('.setApprovers-list tbody .approver_tr').length
                let length = trLen + 1
                console.log(approverJson)
                let newRow = `<tr class="approver_tr">
                                            <td class="countIndex" data-localize="setTemporaryApprovers.rowNbr"
                                                data-localize-location="scope">`+ length +`</td>
                                            <td data-localize="setTemporaryApprovers.temporaryApprover" data-localize-location="scope">
                                                <div class="form-group">
                                                    <input class="form-control empControl" type="text" 
                                                    title=""
                                                    data-localize="setTemporaryApprovers.temporaryApprover"
                                                    name="" 
                                                    id="name_01">
                                                </div>
                                            </td>
                                            <td data-localize="setTemporaryApprovers.fromDate" data-localize-location="scope">
                                                    <div class="form-group">
                                                        <input class="form-control date-control dateFromControl"
                                                        title=""
                                                        data-title="from"
                                                        data-localize="setTemporaryApprovers.fromDate" type="text" 
                                                        name="" 
                                                        id="fromDate_01" readonly>
                                                    </div>
                                            </td>
                                            <td data-localize="setTemporaryApprovers.toDate" data-localize-location="scope">
                                                <div class="form-group">
                                                    <input class="form-control  date-control dateToControl"
                                                    title=""
                                                    data-title="to"
                                                    data-localize="setTemporaryApprovers.toDate" type="text" 
                                                    name="" 
                                                    id="toDate_01" readonly>
                                                </div>
                                            </td>
                                            <td  data-localize="setTemporaryApprovers.delete" data-localize-location="scope">
                                                    <button type="button" class="a-btn" onclick="deleteRow(this)" data-localize="label.delete"
                                                     data-localize-location="title">
                                                        <i class="fa fa-trash"></i>
                                                    </button>
                                            </td>
                                        </tr>
                    `
                console.log("add noEmpty:" +noEmpty)
                if(noEmpty == trLen){
                    $('.setApprovers-list tbody tr:last-child').before(newRow)
                    initialCompleteList()
                    $("#errorComplete").hide()
                }else{
                    $("#errorComplete").show()
                }
                
                initLocalize(initialLocaleCode)//Initialize multilingual function
                initDateControl()
            })
        
            $("#saveSet").click(function(){
                initDateControl()
                $("#chainString").val(chainString)
                $("#empNbrForm").val(empNbr)
                $("#approverJson").val(JSON.stringify(approverJson))
                console.log(approverJson)
                console.log($("#chainString").val())
                console.log($("#empNbrForm").val())
                console.log($("#approverJson").val())
                let length = $(".approver_tr").length
                if(noEmpty == length){
                    // let json = {
                    //     chain:chainString,
                    //     empNbr:empNbr,
                    //     approverJson:JSON.stringify(approverJson)
                    // }
                    // console.log(json)
                    // saveTempApprovers(json)
                    $("#errorComplete").hide()
                    $("#saveTempApprovers")[0].submit()
                }else{
                    $("#errorComplete").show()
                }
                return
                
            })
        
            
        })
        function initialCompleteList(){
            $(".empControl").each(function(){
                $(this).autocomplete(employeeList, {
                    max: 10,    //
                    minChars: 0,    //
                    width: $(this).width()+1,     //
                    scrollHeight: 300,   //
                    matchContains: true,    //
                    autoFill: true,    //
                    formatItem: function(row, i, max) {
                        if(row.employeeNumber){
                            $("#noResultError").hide()
                            $("#saveSet").removeClass("disabled").removeAttr("disabled")
                            return  row.employeeNumber + '-' + row.firstName  +","+ row.lastName + row.firstName ;
                        }else{
                            console.log("no result")
                            $("#noResultError").show()
                            $("#saveSet").addClass("disabled").attr("disabled","disabled")
                            $(".ac_results").hide()
                        }
                    },
                    formatMatch: function(row, i, max) {
                        return row.employeeNumber + '-' + row.firstName  +","+ row.lastName + row.firstName ;
                    },
                    formatResult: function(row) {
                        return row.employeeNumber + '-' + row.firstName  +","+ row.lastName + row.firstName ;
                    }
                }).result(function(event, row, formatted) {
                    judgeContent()
                });
            })
        }
        function deleteApprover() {
            console.log('delete')
        }
        function deleteRow(dom) {
            let length = $('.setApprovers-list tbody .approver_tr').length
            if(length>1){
                $(dom)
                .parents('.approver_tr')
                .remove()
            }
            judgeContent()
            
        }
        var checkin = [];
        var checkout=[];
        var haveEndDate = []
        function initDateControl() {
            let nowTemp = new Date()
            $(".approver_tr").each(function(index){
                haveEndDate[index] = false
                let fromCalendar = $(this).find('.date-control[data-title="from"]')
                let toCalendar = $(this).find('.date-control[data-title="to"]')
                checkin[index] = fromCalendar.fdatepicker({
                    format: 'mm/dd/yyyy',
                    language:initialLocaleCode,
                    onRender: function(date) {
                        // if(checkout[index]&&haveEndDate[index]){
                        //     return date.valueOf() > checkout[index].date.valueOf() ? 'disabled' : '';
                        // }
                    }
                }).on('changeDate', function(ev) {
                    let endDate = toCalendar.val()
                    let startDate = fromCalendar.val()
                    if (
                        ev.date &&
                        (ev.date.valueOf() >= checkout[index].date.valueOf() || !endDate||endDate=='')
                    ) {
                        startDate = new Date(startDate)
                        startDate.setDate(startDate.getDate())
                        checkout[index].update(startDate)
                        toCalendar.change()
                        judgeContent()
                    }
                }).data('datepicker')

                checkout[index] = toCalendar.fdatepicker({
                    format: 'mm/dd/yyyy',
                    language:initialLocaleCode,
                    onRender: function(date) {
                        return date.valueOf() < checkin[index].date.valueOf()
                            ? 'disabled'
                            : ''
                    }
                }).on('changeDate', function(ev) {
                    console.log(ev)
                    if(ev.date){
                        haveEndDate[index] = true
                    }
                    judgeContent()
                    
                }).data('datepicker')
            })
            
        }
        function changeLevel(){
                let selectNum = $("#selectEmpNbr").val()
                let numDirect = 0 ;
                directReportEmployee.forEach(element => {
                    if(element.employeeNumber == selectNum){
                        numDirect = element.numDirectReports
                    }
                });
                console.log(numDirect)
                if(parseInt(numDirect)>0){
                    $("#nextLevel").removeClass("disabled").removeAttr("disabled");
                }else{
                    $("#nextLevel").addClass("disabled").attr('disabled',"true");
                }
            }
            
        var noEmpty = 0
        var approverJson=[]
        function judgeContent(){
            approverJson=[]
            noEmpty = 0
            let length = $(".approver_tr").length
            console.log("judge tr length:" + length)
            $(".approver_tr").each(function(index){
                let empNbr = $(this).find(".empControl").val()
                let empArry = empNbr.split("-")
                console.log(empArry)
                let from = $(this).find(".dateFromControl").val()
                let to = $(this).find(".dateToControl").val()
                let obj = {
                    empNbr:empArry[0],
                    from:from,
                    to:to
                }
                if(empNbr==''||from==''||to==''){
                }else{
                    noEmpty += 1
                }
                approverJson.push(obj)
            })
            console.log("judge tr noEmpty:" + noEmpty)
        }
    </script>
</html>
