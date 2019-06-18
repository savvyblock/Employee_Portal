
$(function(){
    changeLevel()
    var level = $("#level").val()
    if(chain.length>1){
        $("#prevLevel").removeClass("disabled").removeAttr("disabled");
    }else{
        $("#prevLevel").addClass("disabled").attr('disabled',"true");
    }
    $("#nextLevel").click(function(){
        var chainString = $("#chainValue").text()
        console.log(chainString)
        $("#chain").val(chainString)
        $("#filterSupervisor")[0].submit()  
    })
    $("#prevLevel").click(function(){
        var chainString = $("#chainValue").text()
        console.log(chainString)
        $("#preChain").val(chainString)
        $("#previousLevel")[0].submit()  
    })
    var chainSt = $("#chainValue").text()
    $("#disChain").val(chainSt)//unuseful
    $("#appChain").val(chainSt)//unuseful
    $("#actionChain").val(chainSt)
    var requestActionJson = []
    $(".request-list input[type=radio]").change(function(){
        console.log(this.value)
        var requestIndex = $(this).attr('data-index')
        if(this.value == '0'){
            $("#supervisorComment_"+requestIndex+"").removeClass('hide')
        }else{
            $("#supervisorComment_"+requestIndex+"").addClass('hide')
        }
    })
    $("#saveRequestListBtn").click(function(){
        var result = true
        requestActionJson=[]
        leaves.forEach(function(val,index){  
            var actionValue = $('input[type=radio][name=actionRadio_'+index+']:checked').val()
            if(actionValue == '1' || actionValue == '0'){
                var comment = $("#supervisorComment_"+index+"").find("textarea").val()
                if(actionValue=='0'&&(comment == ''||!comment)){
                    $("#errorComment_"+index+"").removeClass('hide')
                    $("#supervisorComment_"+index+"").find("textarea").focus()
                    result = false
                    return false
                }else{
                    $("#errorComment_"+index+"").addClass('hide')
                    var obj = {
                        id:val.id,
                        actionValue:actionValue,
                        comments:comment
                    }
                    requestActionJson.push(obj)
                }
            }
        });
        console.log(requestActionJson)
        $("#actionList").val(JSON.stringify(requestActionJson))
        if(!result&&requestActionJson.length>0){
            return false
        }
        // $("#actionForm")[0].submit()
    })
})

function actionLeave(id){
    var leaveRequest;
    leaves.forEach(function(element) {
        if(element.id == id){
            leaveRequest = element
        }
    });
    console.log(leaveRequest)
    var type
    leaveTypes.forEach(function(element) {
        if(element.code == leaveRequest.LeaveType){
            type = element.description
        }
    });
    var reason
    absRsns.forEach(function(element) {
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
    var comments = leaveRequest.comments
    for(var i=0;i<comments.length;i++){
        var htmlC = '<p>'+comments[i].detail+'</p>'
        $("#commentLog").append(htmlC)
    }
    $("#infoEmpName").html(leaveRequest.empNbr + ":" +leaveRequest.firstName+ ","+leaveRequest.lastName)
    $("#infoDetail").html("")
    var infoDetail = leaveRequest.infos
    for(var i=0;i<infoDetail.length;i++){
        var unit
        if(infoDetail[i].daysHrs=="D"){
            unit = '<span>'+daysFreqLabel+'</span>'
        }else{
            unit = '<span>'+hoursLabel+'</span>'
        }
        var htmlInfo = '<div><h5><span>'+payrollFreqLabel+'</span>:' + infoDetail[i].frequency + '</h5></div>'+
                '<table class="table responsive-table mt-3">'+
                    '<thead>'+
                        '<tr>'+
                            '<th>'+leaveTypeLabel+'</th>'+
                            '<th>'+beginningBalanceLabel+'</th>'+
                            '<th>'+advancedEarnedLabel+'</th>'+
                            '<th>'+pendingEarnedLabel+'</th>'+
                            '<th>'+usedLabel+'</th>'+
                            '<th>'+pendingUsedLabel +'</th>'+
                            '<th>'+availableLabel+'</th>'+
                            '<th>'+unitsLabel+'</th>'+
                        '</tr>'+
                    '</thead>'+
                    '<tbody>'+
                        '<tr>'+
                            '<td scope="'+leaveTypeLabel+'">' + infoDetail[i].lvTyp + infoDetail[i].longDescr +'</td>'+
                            '<td scope="'+beginningBalanceLabel+'">' + infoDetail[i].beginBalance + '</td>'+
                            '<td scope="'+advancedEarnedLabel+'">' + infoDetail[i].advancedEarned + '</td>'+
                            '<td scope="'+pendingEarnedLabel+'">' + infoDetail[i].pendingEarned + '</td>'+
                            '<td scope="'+usedLabel+'">' + infoDetail[i].used +'</td>'+
                            '<td scope="'+pendingUsedLabel +'">' + infoDetail[i].pendingUsed +'</td>'+
                            '<td scope="'+availableLabel+'">' + infoDetail[i].availableBalance + '</td>'+
                            '<td scope="'+unitsLabel+'">'+ unit +'</td>'+
                        '</tr>'+
                    '</tbody>'+
                '</table>'
        $("#infoDetail").append(htmlInfo)
    }
    $("#supervisorComment").val("")
    $(".icheck[name='approve']").iCheck('uncheck');
    $('.approveValidator').hide()
    $('.commentValidator').hide()
    $('.supervisorComment').hide()
    // $('#approveModal').modal('show')
}
function changeLevel(){
    var selectNum = $("#selectEmpNbr").val()
    var numDirect = 0 ;
    directReportEmployee.forEach(function(element) {
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
