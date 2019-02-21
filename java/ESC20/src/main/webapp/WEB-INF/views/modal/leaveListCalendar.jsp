
<div
class="modal fade"
id="leaveListCalendarModal"
tabindex="-1"
role="dialog"
aria-labelledby="leaveListCalendarModal"
aria-hidden="true"
data-backdrop="static"
>
<div class="modal-dialog leaveListCalendarForm">
<div class="modal-content">
    <div class="modal-header">
        <button
                    type="button"
                    class="close"
                    data-dismiss="modal"
                    aria-hidden="true">
                    <span class="hide" data-localize="label.closeModal"></span>
            &times;
        </button>
        <h4 class="modal-title"><span class="hide" data-localize="accessHint.calendarView"></span></h4>
    </div>
    <div class="modal-body">
        <div id="calendar"></div>
    </div>
    <div class="modal-footer">
        <button
          type="button"
            class="btn btn-secondary"
            data-dismiss="modal"
            aria-hidden="true"
            id="cancelAddCalendar"
            data-localize="label.cancel"
        >
        </button>
    </div>
    </div>
    </div>
</div>
<%@ include file="../modal/eventModalStatic.jsp"%>
<script>
var leaveList = eval(${leavesCalendar});
// var leaveTypes = eval(${leaveTypes});
// var absRsns = eval(${absRsns});
$(document).ready(function() {
            initThemeChooser({
                init: function(themeSystem) {
                    $('#calendar').fullCalendar({
                        themeSystem: themeSystem,
                        header: {
                            left: 'prev,next today',
                            center: 'title',
                            right: 'month,agendaWeek,agendaDay,listMonth'
                        },
                        buttonText: {
                            today: 'Today',
                            month: 'Month',
                            agendaWeek: 'AgendaWeek',
                            agendaDay: 'AgendaDay',
                            listMonth: 'ListMonth'
                        },
                        timeFormat: 'hh:mm A',
                        displayEventEnd: true,
                        defaultDate: new Date(),
                        weekNumbers: false,
                        navLinks: true, // can click day/week names to navigate views
                        editable: false,
                        eventLimit: true, // allow "more" link when too many events
                        events: leaveList,
                        locale: initialLocaleCode,
                        eventClick: function(calEvent, jsEvent, view) {
                          console.log(calEvent)
                          let leaveRequest = calEvent;
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
                          let leaveStartDate = leaveRequest.start._i
                          let leaveEndDate = leaveRequest.end._i

                          let start_arry = leaveStartDate.split(" ")
                          let end_arry = leaveEndDate.split(" ")

                          let startTime = changeFormatTimeAm(start_arry[1])
                          let endTime = changeFormatTimeAm(end_arry[1])

                          let startDate = changeMMDDFormat(start_arry[0])
                          let endDate = changeMMDDFormat(end_arry[0])

                          let start = startDate + " " + startTime
                          let end = endDate + " " +endTime
                          // $("#leaveIdStatic").attr("value", leaveRequest.id+"");
                          $("#disIdStatic").attr("value", leaveRequest.id+"");
                          $("#appIdStatic").attr("value", leaveRequest.id+"");
                          $("#employeeStatic").text(leaveRequest.lastName)
                          $("#startDateStatic").html(start)
                          $("#endDateStatic").html(end)
                          $("#leaveTypeStatic").html(type)
                          $("#absenceReasonStatic").html(reason)
                          $("#leaveRequestedStatic").html(leaveRequest.lvUnitsUsed)
                          $("#commentLogStatic").html("")
                          $("#leaveStatusStatic").text(leaveRequest.statusDescr)
                          $("#leaveApproverStatic").text(leaveRequest.approver)
                          let comments = leaveRequest.comments
                          for(let i=0;i<comments.length;i++){
                                  let html = '<p>'+comments[i].detail+'</p>'
                                  $("#commentLogStatic").append(html)
                          }
                          $("infoEmpNameStatic").html(leaveRequest.empNbr + ":" +leaveRequest.firstName+ ","+leaveRequest.firstName)
                          $("#infoDetailStatic").html("")
                          $('#EventDetailModal').modal('show')
                          initLocalize(initialLocaleCode)
                        },
                        viewRender:function(){
                            $(".fc-event").attr("tabindex",0)
                            $(".fc-event").keypress(function(e){
                                console.log(e)
                                var eCode = e.keyCode ? e.keyCode : e.which ? e.which : e.charCode;
                                if (eCode == 13){
                                    $(this).click()
                                }
                            })
                        }
                    })
                }
            })
        })
        function changeMMDDFormat(date){
          let dateArry = date.split("-")
          return dateArry[0]
        }
        function changeFormatTimeAm(value){
          let array = value.split(/[,: ]/);
          let hour,minute,time
          hour = parseInt(array[0])
          minute = array[1]

          if(hour>12){
            hour = (hour-12) < 10 ? "0" + (hour-12) : hour-12;
            time = hour+ ":" +minute+" PM"
          }else{
            if(hour==12){
              time = hour+ ":" +minute+" PM"
            }else{
              hour = hour < 10 ? "0" + hour : hour;
              time = hour+ ":" +minute+" AM"
            }

          }
          return time
      }
</script>