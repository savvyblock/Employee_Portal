
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
        <button
            type="button"
            class="close closeModal"
            data-dismiss="modal"
            aria-hidden="true"
            aria-label="" data-localize="label.closeModal" data-localize-location="aria-label" data-localize-notText="true"
            >
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
            class="btn btn-secondary closeModal"
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
<script>
var leaveList = eval(${leavesCalendar});
var status = eval(${leaveStatus})
var leaveTypes = eval(${leaveTypes});
var absRsns = eval(${absRsns});
</script>
<script src="/<%=request.getContextPath().split("/")[1]%>/js/viewJs/modal/leaveListCalendarEdit.js"></script>
