<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %> <%@ page
language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<html lang="en">
    <head>
        <title data-localize="headTitle.leaveOverview"></title>
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
                                    <span data-localize="title.leaveOverview"></span>
                                <div class="pull-right right-btn">
					                <button class="btn btn-primary" id="new-btn" data-toggle="modal" data-target="#requestModal" onclick="showRequestForm()">Add</button>
                                    <a class="btn btn-primary pull-right" style="height:35px;display:flex;align-items:center;"
                                    data-toggle="modal" data-target="#leaveOverviewCalendarModal" 
                                    data-localize="label.switchToCalendarView" data-localize-location="title">
                                    <i class="fa fa-calendar"></i>
                                </a>
                                </div>
                                </h2>
                            <div class="container-fluid">
                                    <div class="showSelectSupervisor">
                                            <label class="form-title"><span data-localize="label.supervisorHierarchy"></span>: </label>
                                            <c:forEach var="item" items="${chain}" varStatus="status">
                                                    <b> ${item.employeeNumber}:${item.lastName},${item.firstName}</b>
                                                    <c:if test="${!status.last}"> ➝ </c:if>
                                            </c:forEach>
                                    </div>
                                    <form
                                    class="no-print searchForm"
                                    action="nextLevelFromLeaveOverview"
                                    id="filterSupervisor"
                                    method="POST"
                                >
                                <span hidden="hidden" id="chainValue">${chain}</span>
                                    <input hidden="hidden" type="text" value="${level}" name="level" id="level" title="" data-localize="accessHint.level">
                                    <input hidden="hidden" id="chain" name="chain" type="text" value="" title="" data-localize="accessHint.chain">
                                    <input hidden="hidden" type="text" name="isChangeLevel" class="isChangeLevel"  title="" data-localize="accessHint.whetherChangeLevel">
                                    <div class="form-group in-line flex-auto">
                                        <label class="form-title" for="selectEmpNbr"><span data-localize="label.directReportSupervisor"></span>:</label>
                                        <select  class="form-control"name="selectEmpNbr" onchange="changeEmployee()"
                                            id="selectEmpNbr">
                                            <c:forEach var="item" items="${directReportEmployee}" varStatus="count">
                                                <option value="${item.employeeNumber}" <c:if test="${item.employeeNumber==selectedEmployee}">selected</c:if>>${item.selectOptionLabel}</option>
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
                                <form hidden="hidden" action="previousLevelFromLeaveOverview" id="previousLevel" method="POST">
                                        <input hidden="hidden" type="text" value="${level}" name="level" id="preLevel"  title="" data-localize="accessHint.level">
                                        <input hidden="hidden" name="chain" type="text" value="" id="preChain" title="" data-localize="accessHint.chain">
                                </form>
                                <div class="content-white">
                                        
                                        <form
                                        class="no-print searchForm"
                                        action="leaveOverviewList"
                                        id="changeFreqForm"
                                        method="POST"
                                            >
                                            <input hidden="hidden" type="text" name="empNbr" class="employeeNum" value="${selectedEmployee}" title="" data-localize="accessHint.employeeNumber">
                                            <input hidden="hidden" class="chain" name="chain" type="text" value="" title="" data-localize="accessHint.chain">
                                            <input hidden="hidden" type="text" name="isChangeLevel" class="isChangeLevel" value="false" title="" data-localize="accessHint.whetherChangeLevel">
                                        <div class="form-group type-group">
                                                <label class="form-title"  for="freq"  data-localize="label.payrollFreq"></label>
                                                <select class="form-control" name="freq" id="freq" onchange="changeFreq()">
                                                    <c:forEach var="freq" items="${availableFreqs}" varStatus="count">
                                                        <option value="${freq.code}" <c:if test="${freq.code == selectedFreq }">selected</c:if>>${freq.description}</option>
                                                    </c:forEach>
                                                </select>
                                        </div>
                                        <div class="form-group">
                                                <label class="form-title" for="SearchStartDate"><span data-localize="label.from"></span>:</label> 
                                                <div class="button-group">
                                                <input
                                                    class="form-control" type="text" name="startDate"
                                                    id="SearchStartDate" readonly value="${startDate}" />
                                                    <button class="clear-btn" type="button" onclick="clearDate(this)"  tabindex="0">
                                                    <span class="hide" data-localize="label.removeContent"></span>
                                                        <i class="fa fa-times"></i>
                                                    </button>
                                                </div>
                                            </div>
                                            <div class="form-group">
                                                <label class="form-title" for="SearchEndDate"> <span data-localize="label.to"></span>: </label> 
                                                <div class="button-group">
                                                <input
                                                    class="form-control" type="text" name="endDate"
                                                    id="SearchEndDate" value="${endDate}" readonly />
                                                    <button class="clear-btn" type="button" onclick="clearDate(this)" tabindex="0">
                                                    <span class="hide" data-localize="label.removeContent"></span>
                                                        <i class="fa fa-times"></i>
                                                    </button>
                                                </div>
                                            </div>
                                            <div class="form-group btn-group">
                                                <div style="margin-top:20px;">
                                                        <button type="submit" class="btn btn-primary" data-localize="leaveBalance.retrieve">
                                                            </button>
                                                </div>
                                            </div>
                                        </form>

                                        <table class="table request-list responsive-table">
                                                <thead>
                                                    <tr>
                                                            <!-- <th data-localize="approveRequest.employee"></th> -->
                                                            <th data-localize="approveRequest.leaveStartDate"></th>
                                                            <th data-localize="approveRequest.leaveEndDate"> </th>
                                                            <!-- <th data-localize="approveRequest.startTime"></th>
                                                            <th data-localize="approveRequest.endTime"></th> -->
                                                            <th data-localize="approveRequest.leaveType"></th>
                                                            <th data-localize="approveRequest.absenceReason"></th>
                                                            <th data-localize="approveRequest.leaveRequested"></th>
                                                            <th data-localize="approveRequest.commentLog"></th>
                                                            <th data-localize="approveRequest.status"></th>
                                                            <th data-localize="approveRequest.supervisorAction"></th>
                                                    </tr>
                                                </thead>
                                                <tbody>
                                                    <c:if test="${isEmpty==false}">
                                                        <c:forEach var="item" items="${employeeList}" varStatus="status">
                                                            <tr>
                                                                    <!-- <td data-localize="approveRequest.employee" data-localize-location="scope">
                                                                            ${item.firstName} ${item.lastName}
                                                                    </td> -->
                                                                    <td data-localize="approveRequest.leaveStartDate" data-localize-location="scope">${item.LeaveStartDate}</td>
                                                                    <td data-localize="approveRequest.leaveEndDate" data-localize-location="scope">${item.LeaveEndDate}</td>
                                                                    <!-- <td data-localize="approveRequest.startTime" data-localize-location="scope">Start Time</td>
                                                                    <td data-localize="approveRequest.endTime">End Time</td> -->
                                                                    <td data-localize="approveRequest.leaveType" data-localize-location="scope">
                                                                            <c:forEach var="type" items="${leaveTypes}" varStatus="statusType">
                                                                                <c:if test="${type.code==item.LeaveType}">${type.description}</c:if>
                                                                        </c:forEach>
                                                                    </td>
                                                                    <td data-localize="approveRequest.absenceReason" data-localize-location="scope">
                                                                            <c:forEach var="reason" items="${absRsns}" varStatus="statusReason">
                                                                                    <c:if test="${reason.code==item.AbsenseReason}">${reason.description}</c:if>
                                                                            </c:forEach>
                                                                    </td>
                                                                    
                                                                    <td data-localize="approveRequest.leaveRequested" data-localize-location="scope">${item.lvUnitsUsed} 
                                                                        <span data-localize="label.days"></span>
                                                                    </td>
                                                                    <td data-localize="approveRequest.commentLog" data-localize-location="scope">
                                                                            <c:forEach var="comment" items="${item.comments}" varStatus="statusComment">
                                                                                    <p>${comment.detail}</p>
                                                                                </c:forEach>
                                                                    </td>
                                                                    <td data-localize="approveRequest.status" data-localize-location="scope">
                                                                            
                                                                        ${item.statusDescr}
                                                                    </td>
                                                                    <td style="width:150px;" data-localize="approveRequest.supervisorAction" data-localize-location="scope">
                                                                        
                                                                            <c:if test="${leave.statusCd =='P'}">
                                                                            <button class="btn btn-primary sm edit-btn" id="editLeave" data-toggle="modal" data-target="#requestModal" 
                                                                            onClick='editLeave("${item.id}","${item.LeaveType}","${item.AbsenseReason}","${item.start}",
                                                                            "${item.end}", "${item.lvUnitsDaily}","${item.lvUnitsUsed}")'' data-localize="label.edit"></button>
                                                                            <button class="btn btn-secondary sm delete-btn" id="deleteLeave" 
                                                                            onClick="deleteLeave('${item.id}')" data-localize="label.delete"></button>
                                                                            </c:if>
                                                                    
                                                                </td>
                                                            </tr>
                                                        </c:forEach>
                                                    </c:if>
                                                    <c:if test="${isEmpty==true}">
                                                        <tr>
                                                            <td colspan="8">
                                                                <span data-localize="label.noData"></span>
                                                            </td>
                                                        </tr>
                                                    </c:if>
                                                </tbody>
                        
                                            </table>
                                    </div>
                            </div>
                        </section>
                        <form hidden="hidden" id="deleteForm" action="" method="POST">
                            <input type="text" name="" id="deleteId" title="" data-localize="accessHint.id">
                        </form>
            </main>
        </div>
        <%@ include file="../modal/eventModal.jsp"%>
        <%@ include file="../modal/leaveListCalendarEdit.jsp"%>
        <%@ include file="../modal/eventModalStatic.jsp"%>
        <%@ include file="../commons/footer.jsp"%>
    </body>
    <script>
        var directReportEmployee = eval(${directReportEmployee});
        var chain = eval(${chain});
        let chainString = JSON.stringify(chain)
        console.log(chainString)
        $(function(){
            changeLevel()
            $(".chain").val(chainString)
            let level = $("#level").val()
            console.log(initialLocaleCode)
				$('#SearchStartDate').fdatepicker({
					format:'mm/dd/yyyy',
					language:initialLocaleCode
				});
				$('#SearchEndDate').fdatepicker({
					format:'mm/dd/yyyy',
					language:initialLocaleCode
                });
                setGlobal()
            if(chain&&chain!=''&&chain.length>1){
                $("#prevLevel").removeClass("disabled").removeAttr("disabled");
            }else{
                $("#prevLevel").addClass("disabled").attr('disabled',"true");
            }
            $("#nextLevel").click(function(){
                let chain = $("#chainValue").text()
                console.log(chain)
                $("#chain").val(chain)
                $(".isChangeLevel").val(true)
                $("#filterSupervisor")[0].submit()  
            })
            $("#prevLevel").click(function(){
                let chain = $("#chainValue").text()
                console.log(chain)
                $("#preChain").val(chain)
                $(".isChangeLevel").val(true)
                $("#previousLevel")[0].submit()  
            })
        })
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
            function showRequestForm() {
                $('#leaveId').attr('value', '')
                $("[name='Remarks']").text('')
                $('#requestForm')[0].reset()
                $('#requestForm')
                    .data('bootstrapValidator')
                    .destroy()
                $('#requestForm').data('bootstrapValidator', null)
                formValidator()
                $('#cancelAdd').show()
                        $('#deleteLeave').hide()
                        $(".edit-title").hide();
                $(".new-title").show();
                $("#chainModal").val(chainString)
                $("#requestForm").attr("action","updateLeaveFromLeaveOverview")
   
            }
            function changeFreq(){
                let select = $("#freq").val()
                $(".selectFreq").val()
				// $("#changeFreqForm")[0].submit();
			}
            function changeEmployee(){
                let selectNum = $("#selectEmpNbr").val()
                $("#SearchStartDate").val("")
                $("#SearchEndDate").val("")
                $(".employeeNum").val(selectNum)
                $(".isChangeLevel").val(false)
                if(selectNum&&selectNum!=''){
                    $("#changeFreqForm")[0].submit()  
                }
                
            }

            function editLeave(id,leaveType,absenceReason,leaveStartDate,leaveEndDate,lvUnitsDaily,lvUnitsUsed){
                $("#requestForm").attr("action","updateLeaveFromLeaveOverview")
                let comments
                leaveList.forEach(element => {
                    if(element.id == id){
                        console.log(element)
                        comments = element.comments
                    }
                });
                $('#requestForm')
                    .data('bootstrapValidator')
                    .destroy()
                $('#requestForm').data('bootstrapValidator', null)
                formValidator()
				let start_arry = leaveStartDate.split(" ")
				let end_arry = leaveEndDate.split(" ")
				let startTime = start_arry[1].split(":")
				let endTime = end_arry[1].split(":")
				console.log(startTime)
				console.log(endTime)
				let startH = parseInt(startTime[0])
				let endH = parseInt(endTime[0])
				let startAMOrPM,endAMOrPM;
				if(startH>=12){
					startAMOrPM = 'PM'
					if(startH==12){
						startH = 12
					}else{
						startH = startH - 12
					}
				}else{
					startAMOrPM = 'AM'
					startH = startH
				}
				if(endH>=12){
					endAMOrPM = 'PM'
					if(endH == 12){
						endH = 12
					}else{
						endH = endH - 12
					}
				}else{
					endAMOrPM = 'AM'
					endH = endH
				}

				if(startH>=10){
					$("#startHour").val(startH)
				}else{
					$("#startHour").val("0" + startH)
				}

				if(endH>=10){
					$("#endHour").val(endH)
				}else{
					$("#endHour").val("0" + endH)
				}
				$("#startAmOrPm").val(startAMOrPM)
				$("#endAmOrPm").val(endAMOrPM)
				let startTimeValue = startH + ":" + startTime[1] + " " + startAMOrPM
				let endTimeValue = endH + ":" + endTime[1] + " " + endAMOrPM
				$("#startTimeValue").val(startTimeValue)
				$("#endTimeValue").val(endTimeValue)
				$("#startMinute").val(startTime[1])
				$("#endMinute").val(endTime[1])
				$("#cancelAdd").hide();
				$("#deleteLeave").show();	
				$(".edit-title").show();
                $(".new-title").hide();
                $("#commentList").html("")
				for(let i=0;i<comments.length;i++){
						let html = '<p>'+comments[i].detail+'</p>'
						$("#commentList").append(html)
				}
				$("[name='leaveId']").attr("value", id+"");
				$("[name='leaveType']").val(leaveType);
				$("#absenceReason").val(absenceReason);
				$("#startDate").val(changeMMDDFormat(start_arry[0]));
				$("#endDate").val(changeMMDDFormat(end_arry[0]));
				$("#leaveHoursDaily").val(lvUnitsDaily);
				$("#totalRequested").val(lvUnitsUsed);
			}
		
		function deleteLeave(id){
			$("#deleteId").val(id);
			$("#deleteForm").submit();
        }
        function changeMMDDFormat(date){
			let dateArry = date.split("-")
			return dateArry[0]
		}
    </script>
</html>
