
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
        
        <h4 class="modal-title">
            <span>${sessionScope.languageJSON.label.calendarForSuper}</span>
            <c:forEach var="item" items="${chain}" varStatus="status">
                <c:if test="${status.last}"> 
                    ${item.employeeNumber}: ${item.lastName}, ${item.firstName} ${item.middleName}
                </c:if>
            </c:forEach>
        </h4>
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
            data-dismiss="modal">
        	${sessionScope.languageJSON.label.close}
        </button>
    </div>
    </div>
    </div>
</div>
<%@ include file="../modal/eventModalStatic.jsp"%>
<script>
var leaveList = eval(${leavesCalendar});
</script>
<script src="/<%=request.getContextPath().split("/")[1]%>/js/viewJs/modal/leaveListCalendar.js"></script>
