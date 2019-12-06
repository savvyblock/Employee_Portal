
$(function(){
    // console.log(leaves)
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
    if(requestActionJson.length<1){
        $("#saveRequestListBtn").attr('disabled','disabled')
    }else{
        $("#saveRequestListBtn").removeAttr('disabled')
    }
    if(leaves.length<1){
        $('.saveHr').hide()
        $('#saveRequestListBtn').hide()
    }
    $(".request-list input[type=radio]").change(function(){
        var actionRadio = $(".request-list input[type=radio]")
        var actionList = new Array()
        var radioName = new Array()
        $(".request-list input[type=radio]").each(function(){
            radioName.push($(this).attr("name"));
        })
        radioName.sort();
        $.unique(radioName);
        $.each(radioName,function(i,val){
            var checkVal = $("input[name="+val+"]:checked").val()
            var requestIndex = $("input[name="+val+"]:checked").attr('data-index')
            if(checkVal == '1'||checkVal == '0'){
                $("#supervisorComment_"+requestIndex+"").removeClass('hide')
                actionList.push(checkVal)
            }else{
                $("#supervisorComment_"+requestIndex+"").addClass('hide')
            }
        });
        if(actionList.length>0){
            $("#saveRequestListBtn").removeAttr('disabled')
        }else{
            $("#saveRequestListBtn").attr('disabled','disabled')
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
        // console.log(requestActionJson)
        $("#actionList").val(JSON.stringify(requestActionJson))
        console.log(result)
        console.log(requestActionJson.length)
        if(result==false){
            return false
        }else{
            $("#actionForm")[0].submit()
        }
        
    })
    $(".noActionRadio").click(function(){
       $(this).closest("tr").find("textarea.form-text").val("");
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
    if(leaveRequest.daysHrs == 'H'){
        $("#unitsHours").show()
        $("#unitsDays").hide()
    }
    if(!leaveRequest.daysHrs || leaveRequest.daysHrs == '' || leaveRequest.daysHrs == 'D'){
        $("#unitsDays").show()
        $("#unitsHours").hide()
    }
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
function closeBalance(){
    $("body").removeClass("showBalance")
}
function showBalance(id){
    $("body").addClass("showBalance")
    var y = $(event.currentTarget).offset().top;
    var x = $(event.currentTarget).offset().left;
    console.log(x)
    console.log(y)
    var leaveCurrent,leaveBalance
    for(var i = 0,len = leaves.length;i<len;i++){
        if(id == leaves[i].empNbr){
            leaveCurrent = leaves[i]
            leaveBalance = leaves[i].infos
        }
    } 
    $("#leaveBalanceSummaryFor").text(leaveCurrent.empNbr + ': '+ leaveCurrent.lastName + ', '+ leaveCurrent.firstName + ' ' +leaveCurrent.middleName)
    $("#payrollFreqEmp").text(leaveCurrent.payFreqDescr)
    var tbody = ''
    for(var i = 0,len = leaveBalance.length;i<len;i++){
        tbody += '<tr> <td data-title="'+labelLeaveType+'">' +
                        leaveBalance[i].lvTyp + '-' + leaveBalance[i].longDescr +
                '</td>'+
                '<td class="text-right" data-title="'+beginningBalanceType+'">'+
                parseFloat(leaveBalance[i].beginBalance).toFixed(3) +
                '</td>'+
                '<td class="text-right" data-title="'+advancedEarnedType+'">'+
                parseFloat(leaveBalance[i].advancedEarned).toFixed(3) +
                '</td>'+
                '<td class="text-right" data-title="'+pendingEarnedType+'">'+
                parseFloat(leaveBalance[i].pendingEarned).toFixed(3) +
                '</td>'+
                '<td class="text-right" data-title="'+usedType+'">'+
                parseFloat(leaveBalance[i].used).toFixed(3) +
                '</td>'+
                '<td class="text-right" data-title="'+pendingUsedType+'">'+
                parseFloat(leaveBalance[i].totalPendingUsed).toFixed(3) +
                '</td>'+
                '<td class="text-right" data-title="'+availableType+'">'+
                parseFloat(leaveBalance[i].availableBalance).toFixed(3)+
                '</td>'+
                '<td class="text-left" data-title="'+unitsType+'">'+
                    (leaveBalance[i].daysHrs=='D'?daysType:hoursType) +
                '</td></tr>'
    } 
    if(leaveBalance.length < 1){
        tbody = '<tr><td class="text-center" colspan="8">'+noRows+'</td></tr>'
    }
    $("#leaveBalanceDetail tbody").html(tbody)

}