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
                
                <h4 class="modal-title" data-localize="label.approveLeaveRequests"></h4>
                <button
                    type="button" role="button"
                    class="close closeModal"
                    data-dismiss="modal"
                    aria-label="" data-localize="label.closeModal" data-localize-location="aria-label" data-localize-notText="true"
                    >
                    &times;
                </button>
            </div>
              <div class="modal-body">
                  <table class="table border-table no-thead">
                    <tbody>
                      <tr>
                        <td id="employeeNameModal"><b data-localize="label.employee"></b></td>
                        <td headers="employeeNameModal" data-localize="label.employee" data-localize-location="title">
                            <!-- <button class="showBalanceBtn pull-right a-btn" data-localize="label.showLeaveBalanceSummary" data-localize-location="title" type="button" role="button" onclick="showDetailStatic(this)">
                                <i class="fa fa-angle-double-down"></i>
                                <i class="fa fa-angle-double-up"></i>
                            </button> -->
                            <div id="employeeStatic"></div>
                        </td>
                      </tr>
                      <tr>
                        <td colspan="2" data-localize-location="title" data-localize="label.leaveBalanceSummary" id="leaveBalanceSummaryStatic">
                          <div><span data-localize="label.leaveBalanceSummaryFor"></span> 
                            <span id="infoEmpNameStatic"></span></div>
                            <div id="infoDetailStatic"></div>
                        </td>
                      </tr>
                      <tr>
                            <td id="startDateModalTitle"><b data-localize="leaveRequest.startDate"></b></td>
                            <td headers="startDateModalTitle" data-localize="leaveRequest.startDate" data-localize-location="title"><div id="startDateStatic"></div></td>
                          </tr>
                          <tr>
                            <td id="endDateModalTitle"><b data-localize="leaveRequest.endDate"></b></td>
                            <td headers="endDateModalTitle" data-localize="leaveRequest.endDate" data-localize-location="title"><div id="endDateStatic"></div></td>
                          </tr>
                          <tr>
                            <td id="leaveTypeModalTitle"><b data-localize="leaveRequest.leaveType"></b></td>
                            <td headers="leaveTypeModalTitle" data-localize="leaveRequest.leaveType" data-localize-location="title"><div id="leaveTypeStatic"></div></td>
                          </tr>
                          <tr>
                            <td id="absenceReasonModalTitle"><b data-localize="leaveRequest.absenceReason"></b></td>
                            <td headers="absenceReasonModalTitle" data-localize="leaveRequest.absenceReason" data-localize-location="title"><div id="absenceReasonStatic"></div></td>
                          </tr>
                          <tr>
                            <td id="leaveRequestedModalTitle"><b data-localize="leaveRequest.leaveRequested"></b></td>
                            <td headers="leaveRequestedModalTitle" data-localize="leaveRequest.leaveRequested" data-localize-location="title">
                                <span id="leaveRequestedStatic"></span>
                                <span data-localize="label.days"></span>
                            </td>
                          </tr>
                          <tr>
                                <td id="statusModalTitle"><b data-localize="leaveRequest.status"></b></td>
                                <td headers="statusModalTitle" data-localize="leaveRequest.status" data-localize-location="title"><div id="leaveStatusStatic"></div></td>
                              </tr>
                          <tr>
                        <tr>
                                <td id="approverModalTitle"><b data-localize="leaveRequest.approver"></b></td>
                                <td headers="approverModalTitle" data-localize="leaveRequest.approver" data-localize-location="title"><div id="leaveApproverStatic"></div></td>
                                </tr>
                            <tr>
                                <td id="commentLogModalTitle"><b data-localize="leaveRequest.commentLog"></b></td>
                                <td headers="commentLogModalTitle" data-localize="leaveRequest.commentLog" data-localize-location="title">
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
                      data-localize="label.cancel"
                  >
                  </button>
              </div>
        </div>
        <!-- /.modal-content -->
    </div>
    <!-- /.modal -->
</div>
<script>
// function showDetailStatic(dom){
//                 $(dom).toggleClass("active");
//                 $("#leaveBalanceSummaryStatic").toggle();
//             }
</script>
