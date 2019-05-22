<div
    class="modal fade"
    id="EventDetailModal"
    tabindex="-1"
    role="dialog"
    aria-labelledby="EventDetailModal"
    aria-hidden="true"
    data-backdrop="static"
>
    <div class="modal-dialog approveForm">
        <div class="modal-content">
            <div class="modal-header">
                
                <h4 class="modal-title">${sessionScope.languageJSON.label.approveLeaveRequests}</h4>
                <button
                    type="button" role="button"
                    class="close closeModal"
                    data-dismiss="modal"
                    aria-label="${sessionScope.languageJSON.label.closeModal}">
                    &times;
                </button>
            </div>
              <div class="modal-body">
                  <table class="table border-table no-thead">
                    <tbody>
                      <tr>
                        <th id="employeeNameModal"><b>${sessionScope.languageJSON.label.employee}</b></th>
                        <td headers="employeeNameModal" title="${sessionScope.languageJSON.label.employee}">
                            <div id="employeeStatic"></div>
                        </td>
                      </tr>
                      <tr>
                        <td colspan="2" title="${sessionScope.languageJSON.label.leaveBalanceSummary}" id="leaveBalanceSummaryStatic">
                          <div><span>${sessionScope.languageJSON.label.leaveBalanceSummaryFor}</span> 
                            <span id="infoEmpNameStatic"></span></div>
                            <div id="infoDetailStatic"></div>
                        </td>
                      </tr>
                      <tr>
                            <th id="startDateModalTitle"><b>${sessionScope.languageJSON.leaveRequest.startDate}</b></th>
                            <td headers="startDateModalTitle" title="${sessionScope.languageJSON.leaveRequest.startDate}"><div id="startDateStatic"></div></td>
                          </tr>
                          <tr>
                            <th id="endDateModalTitle"><b>${sessionScope.languageJSON.leaveRequest.endDate}</b></th>
                            <td headers="endDateModalTitle" title="${sessionScope.languageJSON.leaveRequest.endDate}"><div id="endDateStatic"></div></td>
                          </tr>
                          <tr>
                            <th id="leaveTypeModalTitle"><b>${sessionScope.languageJSON.leaveRequest.leaveType}</b></th>
                            <td headers="leaveTypeModalTitle" title="${sessionScope.languageJSON.leaveRequest.leaveType}"><div id="leaveTypeStatic"></div></td>
                          </tr>
                          <tr>
                            <th id="absenceReasonModalTitle"><b>${sessionScope.languageJSON.leaveRequest.absenceReason}</b></th>
                            <td headers="absenceReasonModalTitle" title="${sessionScope.languageJSON.leaveRequest.absenceReason}"><div id="absenceReasonStatic"></div></td>
                          </tr>
                          <tr>
                            <th id="leaveRequestedModalTitle"><b>${sessionScope.languageJSON.leaveRequest.leaveRequested}</b></th>
                            <td headers="leaveRequestedModalTitle" title="${sessionScope.languageJSON.leaveRequest.leaveRequested}">
                                <span id="leaveRequestedStatic"></span>
                                <span>${sessionScope.languageJSON.label.days}</span>
                            </td>
                          </tr>
                          <tr>
                                <th id="statusModalTitle"><b>${sessionScope.languageJSON.leaveRequest.status}</b></th>
                                <td headers="statusModalTitle" title="${sessionScope.languageJSON.leaveRequest.status}"><div id="leaveStatusStatic"></div></td>
                            </tr>
                        <tr>
                                <th id="approverModalTitle"><b>${sessionScope.languageJSON.leaveRequest.approver}</b></th>
                                <td headers="approverModalTitle" title="${sessionScope.languageJSON.leaveRequest.approver}"><div id="leaveApproverStatic"></div></td>
                                </tr>
                            <tr>
                                <th id="commentLogModalTitle"><b>${sessionScope.languageJSON.leaveRequest.commentLog}</b></th>
                                <td headers="commentLogModalTitle" title="${sessionScope.languageJSON.leaveRequest.commentLog}">
                                    <div id="commentLogStatic"></div>
                                </td>
                          </tr>
                    </tbody>
                  </table>
              </div>
              <div class="modal-footer">
                  <button
                  type="button" role="button"
                      class="btn btn-secondary closeModal"
                      data-dismiss="modal"
                      id="cancelAddStatic"
                  >
                  	${sessionScope.languageJSON.label.cancel}
                  </button>
              </div>
        </div>
        <!-- /.modal-content -->
    </div>
    <!-- /.modal -->
</div>
