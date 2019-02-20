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
                <button
                    type="button"
                    class="close"
                    data-dismiss="modal"
                    aria-hidden="true">
                    <span class="hide" data-localize="label.closeModal"></span>
                    &times;
                </button>
                <h4 class="modal-title" data-localize="label.approveLeaveRequests"></h4>
            </div>
              <div class="modal-body">
                  <table class="table border-table no-thead">
                    <tbody>
                      <tr>
                        <td><b data-localize="label.employee"></b></td>
                        <td data-localize="label.employee" data-localize-location="title">
                            <!-- <button class="showBalanceBtn pull-right a-btn" data-localize="label.showLeaveBalanceSummary" data-localize-location="title" type="button" onclick="showDetailStatic(this)">
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
                            <td><b data-localize="leaveRequest.startDate"></b></td>
                            <td data-localize="leaveRequest.startDate" data-localize-location="title"><div id="startDateStatic"></div></td>
                          </tr>
                          <tr>
                            <td><b data-localize="leaveRequest.endDate"></b></td>
                            <td data-localize="leaveRequest.endDate" data-localize-location="title"><div id="endDateStatic"></div></td>
                          </tr>
                          <tr>
                            <td><b data-localize="leaveRequest.leaveType"></b></td>
                            <td data-localize="leaveRequest.leaveType" data-localize-location="title"><div id="leaveTypeStatic"></div></td>
                          </tr>
                          <tr>
                            <td><b data-localize="leaveRequest.absenceReason"></b></td>
                            <td data-localize="leaveRequest.absenceReason" data-localize-location="title"><div id="absenceReasonStatic"></div></td>
                          </tr>
                          <tr>
                            <td><b data-localize="leaveRequest.leaveRequested"></b></td>
                            <td data-localize="leaveRequest.leaveRequested" data-localize-location="title">
                                <span id="leaveRequestedStatic"></span>
                                <span data-localize="label.days"></span>
                            </td>
                          </tr>
                          <tr>
                                <td><b data-localize="leaveRequest.status"></b></td>
                                <td data-localize="leaveRequest.status" data-localize-location="title"><div id="leaveStatusStatic"></div></td>
                              </tr>
                          <tr>
                                <tr>
                                        <td><b data-localize="leaveRequest.approver"></b></td>
                                        <td data-localize="leaveRequest.approver" data-localize-location="title"><div id="leaveApproverStatic"></div></td>
                                      </tr>
                                  <tr>
                            <td><b data-localize="leaveRequest.commentLog"></b></td>
                            <td data-localize="leaveRequest.commentLog" data-localize-location="title">
                                <div id="commentLogStatic"></div>
                            </td>
                          </tr>
                    </tbody>
                  </table>
              </div>
              <div class="modal-footer">
                  <button
                  type="button"
                      class="btn btn-secondary"
                      data-dismiss="modal"
                      aria-hidden="true"
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
