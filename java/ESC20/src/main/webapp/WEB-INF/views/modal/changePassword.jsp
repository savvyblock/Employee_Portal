<div
    class="modal fade"
    id="changePasswordModal"
    tabindex="-1"
    role="dialog"
    aria-labelledby="changePasswordModal"
    aria-hidden="true"
    data-backdrop="static"
>
    <div class="modal-dialog approveForm" style="max-width:350px;">
        <div class="modal-content">
            <div class="modal-header">
                <h4 class="modal-title" data-localize="label.changePassword"></h4>
                <button
                    type="button" role="button"
                    class="close closeModal"
                    data-dismiss="modal"
                    aria-label="" data-localize="label.closeModal" data-localize-location="aria-label" data-localize-notText="true"
                    >
                    &times;
                </button>
            </div>
              
                  <form id="updatePassword" action="updatePassword" method="post" style="max-width:350px;">
                  	<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                    <input type="text" id="sessionPsd" value="${decryptedPwd}" style = "display:none" aria-label="" data-localize="accessHint.sessionPassword"> 
                    <input type="text" name="id" id="userId" value="${sessionScope.user.empNbr}" style = "display:none" aria-label="" data-localize="accessHint.employeeNumber"> 
                    <div class="modal-body">
                      <div class="form-group">
                          <label class="form-title" for="oldPassword" data-localize="label.oldPassword"></label>
                          <div class="valid-wrap">
                              <input
                                  type="password"
                                  class="form-control"
                                  placeholder=""
                                  data-localize="label.oldPassword"
                                  name="oldPassword"
                                  id="oldPassword"
                              />
                          </div>
                      </div>  
                      <div class="form-group has-error oldPsdValidator" style = "display:none">
                            <small class="help-block" role="alert" aria-atomic="true" data-localize="validator.oldPasswordWrong"></small>
                        </div>
                    <div class="form-group">
                          <label class="form-title" for="newPassword" data-localize="label.newPassword"></label>
                          <div class="valid-wrap">
                              <input
                                  type="password"
                                  class="form-control"
                                  placeholder=""
                                  data-localize="label.newPassword"
                                  name="password"
                                  id="newPassword"
                              />
                          </div>
                      </div>
                      <div class="form-group">
                          <label class="form-title" for="newCheckPassword" data-localize="label.confirmPassword"></label>
                          <div class="valid-wrap">
                              <input
                                  type="password"
                                  class="form-control"
                                  placeholder=""
                                  data-localize="label.confirmPassword"
                                  name="newPassword"
                                  id="newCheckPassword"
                              />
                          </div>
                      </div>
                    </div>
                    <div class="modal-footer">
                        <button type="button" role="button" id="changePsd" class="btn btn-primary" data-localize="label.submit">
                          </button>
                        <button
                        type="button" role="button"
                            class="btn btn-secondary closeModal"
                            data-dismiss="modal"
                            id="cancelAdd"
                            data-localize="label.cancel"
                        >
                        </button>
                    </div>
                  </form>
                
              
              
        </div>
        <!-- /.modal-content -->
    </div>
    <!-- /.modal -->
</div>

<script src="/<%=request.getContextPath().split("/")[1]%>/js/viewJs/modal/changePassword.js"></script>
