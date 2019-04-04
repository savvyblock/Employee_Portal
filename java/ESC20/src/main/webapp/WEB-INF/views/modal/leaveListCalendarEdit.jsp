
<div
class="modal fade"
id="leaveOverviewCalendarModal"
tabindex="-1"
role="dialog"
aria-labelledby="leaveOverviewCalendarModal"
aria-hidden="true"
data-backdrop="static"
>
<div class="modal-dialog leaveListCalendarForm">
<div class="modal-content">
    <div class="modal-header">
        
        <h4 class="modal-title"><span class="hide">${sessionScope.languageJSON.accessHint.calendarView}</span></h4>
        <button
            type="button" role="button"
            class="close closeModal"
            data-dismiss="modal"
            aria-label="${sessionScope.languageJSON.label.closeModal}">
            &times;
        </button>
    </div>
    <div class="modal-body">
        <div id="calendar"></div>
    </div>
    <div class="modal-footer">
        <button
          type="button" role="button"
            class="btn btn-secondary closeModal"
            data-dismiss="modal"
            id="cancelAddCalendar">
        	${sessionScope.languageJSON.label.close}
        </button>
    </div>
    </div>
    </div>
</div>
<script>
var leaveList = eval(${leavesCalendar});
var status = eval(${leaveStatus})
var leaveTypes = eval(${leaveTypes});
var absRsns = eval(${absRsns});
console.log(leaveList)
</script>
<script src="/<%=request.getContextPath().split("/")[1]%>/js/viewJs/modal/leaveListCalendarEdit.js"></script>
