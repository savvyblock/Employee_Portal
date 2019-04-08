<div
    class="modal fade"
    id="approveModal"
    tabindex="-1"
    role="dialog"
    aria-labelledby="approveModal"
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
                  <input type="hidden" name="leaveId" id="leaveId" aria-label="${sessionScope.languageJSON.accessHint.id}"/>
                  <table class="table border-table no-thead leftTdTable">
                    <tbody>
                      <tr>
                        <td><b>${sessionScope.languageJSON.label.employee}</b></td>
                        <td title="${sessionScope.languageJSON.label.employee}">
                            <button class="showBalanceBtn pull-right a-btn" type="button" role="button" onclick="showDetail(this)" aria-label="${sessionScope.languageJSON.label.showLeaveBalanceSummary}">
                                <i class="fa fa-angle-double-down"></i>
                                <i class="fa fa-angle-double-up"></i>
                            </button>
                            <div id="employee"></div>
                        </td>
                      </tr>
                      <tr>
                        <td colspan="2" title="${sessionScope.languageJSON.label.leaveBalanceSummary}" id="leaveBalanceSummary">
                            <div><span>${sessionScope.languageJSON.label.leaveBalanceSummaryFor}</span> 
                                <span id="infoEmpName"></span>
                            </div>
                            <div id="infoDetail"></div>
                        </td>
                      </tr>
                      <tr>
                        <td><b>${sessionScope.languageJSON.leaveRequest.startDate}</b></td>
                        <td title="${sessionScope.languageJSON.leaveRequest.startDate}"><div id="startDate"></div></td>
                      </tr>
                      <tr>
                        <td><b>${sessionScope.languageJSON.leaveRequest.endDate}</b></td>
                        <td title="${sessionScope.languageJSON.leaveRequest.endDate}"><div id="endDate"></div></td>
                      </tr>
                      <tr>
                        <td><b>${sessionScope.languageJSON.leaveRequest.leaveType}</b></td>
                        <td title="${sessionScope.languageJSON.leaveRequest.leaveType}"><div id="leaveType"></div></td>
                      </tr>
                      <tr>
                        <td><b>${sessionScope.languageJSON.leaveRequest.absenceReason}</b></td>
                        <td title="${sessionScope.languageJSON.leaveRequest.absenceReason}"><div id="absenceReason"></div></td>
                      </tr>
                      <tr>
                        <td><b>${sessionScope.languageJSON.leaveRequest.leaveRequested}</b></td>
                        <td title="${sessionScope.languageJSON.leaveRequest.leaveRequested}">
                            <span id="leaveRequested"></span>
                            <span>${sessionScope.languageJSON.label.days}</span>
                        </td>
                      </tr>
                      <tr>
                        <td><b>${sessionScope.languageJSON.leaveRequest.commentLog}</b></td>
                        <td title="${sessionScope.languageJSON.leaveRequest.commentLog}"><div id="commentLog"></div></td>
                      </tr>
                    </tbody>
                  </table>
                  <div class="form-group action-group">
                        <label for="approve">
                            <input class="icheck" type="radio" name="approve" id="approve" value="Approve">
                            <span>${sessionScope.languageJSON.label.approve}</span>
                        </label>
                        <label for="disApprove">
                            <input class="icheck" type="radio" name="approve" id="disApprove" value="Disapprove">
                            <span>${sessionScope.languageJSON.label.disapprove}</span>
                        </label>
                  </div>
                  <div class="form-group supervisorComment">
                      <label for="supervisorComment">${sessionScope.languageJSON.label.supervisorComment}</label>
                      <textarea class="form-control form-text" name="supervisorComment" id="supervisorComment" cols="30" rows="4"></textarea>
                  </div>
                  <p class="approveValidator error-hint" role="alert" aria-atomic="true">${sessionScope.languageJSON.validator.pleaseSelectOne}</p>
                  <p class="commentValidator error-hint" role="alert" aria-atomic="true">${sessionScope.languageJSON.validator.pleaseEnterComment}</p>
              </div>
              <div class="modal-footer">
                  <button
                      type="submit" role="submitButton"
                      class="btn btn-primary"
                      id="save"
                      name="save"
                  >
                  	${sessionScope.languageJSON.label.save}
                  </button>
                  <button
                  type="button" role="button"
                      class="btn btn-secondary closeModal"
                      data-dismiss="modal"
                      id="cancelAdd"
                  >
                  	${sessionScope.languageJSON.label.cancel}
                  </button>
              </div>
        </div>
        <!-- /.modal-content -->
    </div>
    <!-- /.modal -->
</div>
<form hidden="hidden" action="disapproveLeave" id="disapproveLeave" method="POST">
    <input type="text" name="id" id="disId" aria-label="${sessionScope.languageJSON.accessHint.id}"/>
    <input type="text" name="chain" id="disChain" aria-label="${sessionScope.languageJSON.accessHint.chain}"/>
    <input type="text" name="comment" id="disComment" aria-label="${sessionScope.languageJSON.accessHint.comment}"/>
    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
</form>

<form hidden="hidden" action="approveLeave" id="approveLeave" method="POST">
        <input type="text" name="id" id="appId" aria-label="${sessionScope.languageJSON.accessHint.id}"/>
        <input type="text" name="chain" id="appChain" aria-label="${sessionScope.languageJSON.accessHint.chain}"/>
        <input type="text" name="comment" id="appComment" aria-label="${sessionScope.languageJSON.accessHint.comment}"/>
        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
    </form>

<script src="/<%=request.getContextPath().split("/")[1]%>/js/viewJs/modal/approveLeaveModal.js"></script>
