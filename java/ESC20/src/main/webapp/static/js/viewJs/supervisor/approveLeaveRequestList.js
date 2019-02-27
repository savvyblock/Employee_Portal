
$(function(){
    changeLevel()
    let level = $("#level").val()
    if(chain.length>1){
        $("#prevLevel").removeClass("disabled").removeAttr("disabled");
    }else{
        $("#prevLevel").addClass("disabled").attr('disabled',"true");
    }
    $("#nextLevel").click(function(){
        let chainString = $("#chainValue").text()
        console.log(chainString)
        $("#chain").val(chainString)
        $("#filterSupervisor")[0].submit()  
    })
    $("#prevLevel").click(function(){
        let chainString = $("#chainValue").text()
        console.log(chainString)
        $("#preChain").val(chainString)
        $("#previousLevel")[0].submit()  
    })
    let chainSt = $("#chainValue").text()
    $("#disChain").val(chainSt)
    $("#appChain").val(chainSt)
})
function actionLeave(id){
    let leaveRequest;
    leaves.forEach(element => {
        if(element.id == id){
            leaveRequest = element
        }
    });
    console.log(leaveRequest)
    let type
    leaveTypes.forEach(element => {
        if(element.code == leaveRequest.LeaveType){
            type = element.description
        }
    });
    let reason
    absRsns.forEach(element => {
        if(element.code == leaveRequest.AbsenseReason){
            reason = element.description
        }
    });
    
    $("#leaveId").attr("value", leaveRequest.id+"");
    $("#disId").attr("value", leaveRequest.id+"");
    $("#appId").attr("value", leaveRequest.id+"");
    $("#employee").text(leaveRequest.lastName)
    $("#startDate").html(leaveRequest.LeaveStartDate)
    $("#endDate").html(leaveRequest.LeaveEndDate)
    $("#leaveType").html(type)
    $("#absenceReason").html(reason)
    $("#leaveRequested").html(leaveRequest.lvUnitsUsed)
    $("#commentLog").html("")
    let comments = leaveRequest.comments
    for(let i=0;i<comments.length;i++){
            let html = '<p>'+comments[i].detail+'</p>'
            $("#commentLog").append(html)
    }
    $("#infoEmpName").html(leaveRequest.empNbr + ":" +leaveRequest.firstName+ ","+leaveRequest.lastName)
    $("#infoDetail").html("")
    let infoDetail = leaveRequest.infos
    for(let i=0;i<infoDetail.length;i++){
        let unit
        if(infoDetail[i].daysHrs=="D"){
            unit = '<span data-localize="label.days"></span>'
        }else{
            unit = '<span data-localize="label.hours"></span>'
        }
        let html = `
            <div><span data-localize="label.payrollFreq"></span>: ` + infoDetail[i].frequency + `</div>
                <table class="table responsive-table mt-3">
                    <thead>
                        <tr>
                            <th data-localize="leaveBalance.leaveType"></th>
                            <th data-localize="leaveBalance.beginningBalance"></th>
                            <th data-localize="leaveBalance.advancedEarned"></th>
                            <th data-localize="leaveBalance.pendingEarned"></th>
                            <th data-localize="leaveBalance.used"></th>
                            <th data-localize="leaveBalance.pendingUsed"> </th>
                            <th data-localize="leaveBalance.available"></th>
                            <th data-localize="leaveBalance.units"></th>
                        </tr>
                    </thead>
                    <tbody>
                        <tr>
                            <td data-localize="leaveBalance.leaveType" data-localize-location="scope">` + infoDetail[i].lvTyp + infoDetail[i].longDescr +` </td>
                            <td data-localize="leaveBalance.beginningBalance" data-localize-location="scope">` + infoDetail[i].beginBalance + `</td>
                            <td data-localize="leaveBalance.advancedEarned" data-localize-location="scope">` + infoDetail[i].advancedEarned + `</td>
                            <td data-localize="leaveBalance.pendingEarned" data-localize-location="scope"> `+ infoDetail[i].pendingEarned + `</td>
                            <td data-localize="leaveBalance.used" data-localize-location="scope"> `+ infoDetail[i].used +` </td>
                            <td data-localize="leaveBalance.pendingUsed" data-localize-location="scope"> `+ infoDetail[i].pendingUsed +` </td>
                            <td data-localize="leaveBalance.available" data-localize-location="scope"> `+ infoDetail[i].availableBalance +` </td>
                            <td data-localize="leaveBalance.units" data-localize-location="scope">`+ unit +`</td>
                        </tr>
                    </tbody>
                </table>`
        $("#infoDetail").append(html)
    }
    $("#supervisorComment").val("")
    $(".icheck[name='approve']").iCheck('uncheck');
    $('.approveValidator').hide()
    $('.commentValidator').hide()
    $('.supervisorComment').hide()
    // $('#approveModal').modal('show')
    initLocalize(initialLocaleCode)
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
function showCalendarModal(){
    setTimeout("initialLeaveCalendarModal()",100)
    
}
