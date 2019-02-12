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
                                    <input hidden="hidden" class="chain" name="chain" type="text" value="">
                                    <input hidden="hidden" id="empNbr" name="empNbr" type="text" value="">
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
                                                    <tr class="approver_tr">
                                                            <td
                                                                class="countIndex"
                                                                data-localize="setTemporaryApprovers.rowNbr" data-localize-location="scope"
                                                            >
                                                                ${status.index + 1}
                                                            </td>
                                                            <td
                                                            data-localize="setTemporaryApprovers.temporaryApprover" data-localize-location="scope"
                                                            ></td>
                                                            <td data-localize="setTemporaryApprovers.fromDate" data-localize-location="scope"></td>
                                                            <td data-localize="setTemporaryApprovers.toDate" data-localize-location="scope"></td>
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
                                                        class="form-control"
                                                        type="text"
                                                        title=""
                                                        data-localize="setTemporaryApprovers.temporaryApprover"
                                                        name="temporaryApprovers[${row.index}].temporaryApprover.employeeNumber"
                                                        id="name_01"
                                                    />
                                                </div>
                                            </td>
                                            <td data-localize="setTemporaryApprovers.fromDate" data-localize-location="scope">
                                                <div class="form-group">
                                                    <input
                                                        class="form-control date-control"
                                                        title=""
                                                        data-localize="setTemporaryApprovers.fromDate"
                                                        type="text"
                                                        name="temporaryApprovers[${row.index}].fromDateString"
                                                        id="fromDate_01"
                                                    />
                                                </div>
                                            </td>
                                            <td data-localize="setTemporaryApprovers.toDate" data-localize-location="scope">
                                                <div class="form-group">
                                                    <input
                                                        class="form-control  date-control"
                                                        title=""
                                                        data-localize="setTemporaryApprovers.toDate"
                                                        type="text"
                                                        name="temporaryApprovers[${row.index}].toDateString"
                                                        id="toDate_01"
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
                                <div class="text-right mt-3">
                                    <button
                                        type="submit"
                                        class="btn btn-primary"
                                        title=""
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
        var chain = eval(${chain});
        $(function() {
            changeLevel()
            initDateControl()
            let level = $("#level").val()
            let chainString = JSON.stringify(chain)
            let empNbr = $("#currentEmployee").text()
            let lengthNow = $('.setApprovers-list tbody tr').length + 1
            console.log($('.setApprovers-list tbody tr').length)
            console.log(lengthNow)
            if($("#firstRow")){
                $("#firstRow").text(lengthNow)
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
                let length = $('.setApprovers-list tbody tr').length + 1
                console.log(length)
                let newRow = `<tr class="approver_tr">
                                            <td class="countIndex" data-localize="setTemporaryApprovers.rowNbr"
                                                data-localize-location="scope">`+ length +`</td>
                                            <td data-localize="setTemporaryApprovers.temporaryApprover" data-localize-location="scope">
                                                <div class="form-group">
                                                    <input class="form-control" type="text" 
                                                    title=""
                                                    data-localize="setTemporaryApprovers.temporaryApprover"
                                                    name="" 
                                                    id="name_01">
                                                </div>
                                            </td>
                                            <td data-localize="setTemporaryApprovers.fromDate" data-localize-location="scope">
                                                    <div class="form-group">
                                                        <input class="form-control date-control"
                                                        title=""
                                                        data-localize="setTemporaryApprovers.fromDate" type="text" 
                                                        name="" 
                                                        id="fromDate_01">
                                                    </div>
                                            </td>
                                            <td data-localize="setTemporaryApprovers.toDate" data-localize-location="scope">
                                                <div class="form-group">
                                                    <input class="form-control  date-control"
                                                    title=""
                                                    data-localize="setTemporaryApprovers.toDate" type="text" 
                                                    name="" 
                                                    id="toDate_01">
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
                $('.setApprovers-list tbody tr:last-child').before(newRow)
                initLocalize(initialLocaleCode)//Initialize multilingual function
                initDateControl()
            })
        })
        function deleteApprover() {
            console.log('delete')
        }
        function deleteRow(dom) {
            $(dom)
                .parents('.approver_tr')
                .remove()
        }
        var checkin = [];
        var checkout=[];
        var haveEndDate = []
        function initDateControl() {
            let nowTemp = new Date()
            let now = new Date(
                nowTemp.getFullYear(),
                nowTemp.getMonth(),
                nowTemp.getDate(),
                0,
                0,
                0,
                0
            )
            $(".approver_tr").each(function(index){
                console.log(index)
                haveEndDate[index] = false
                checkin[index] = $(this).find('.date-control[data-title="from"]')
                .fdatepicker({
                    // startDate: now,
                    format: 'mm/dd/yyyy',
                    language:initialLocaleCode,
                    onRender: function(date) {
                        if(checkout[index]&&haveEndDate[index]){
                            return date.valueOf() > checkout[index].date.valueOf() ? 'disabled' : '';
                        }
                    }
                })
                .on('changeDate', function(ev) {

                })
                .data('datepicker')
                checkout[index] = $(this).find('.date-control[data-title="to"]')
                .fdatepicker({
                    // startDate: now,
                    format: 'mm/dd/yyyy',
                    language:initialLocaleCode,
                    onRender: function(date) {
                        return date.valueOf() < checkin[index].date.valueOf()
                            ? 'disabled'
                            : ''
                    }
                })
                .on('changeDate', function(ev) {
                    console.log(ev)
                    if(ev.date){
                        haveEndDate[index] = true
                    }
                    
                })
                .data('datepicker')
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
    </script>
</html>
