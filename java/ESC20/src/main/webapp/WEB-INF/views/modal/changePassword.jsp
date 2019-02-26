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
                <button
                    type="button"
                    class="close"
                    data-dismiss="modal"
                    aria-hidden="true">
                    <span class="hide" data-localize="label.closeModal"></span>
                    &times;
                </button>
                <h4 class="modal-title" data-localize="label.changePassword"></h4>
            </div>
              
                  <form id="updatePassword" action="updatePassword" method="post" style="max-width:350px;">
                    <input type="text" id="sessionPsd" value="${decryptedPwd}" style = "display:none" title="" data-localize="accessHint.sessionPassword"> 
                    <input type="text" name="id" id="userId" value="${sessionScope.user.empNbr}" style = "display:none" title="" data-localize="accessHint.employeeNumber"> 
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
                            <small class="help-block" data-localize="validator.oldPasswordWrong"></small>
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
                        <button type="button" id="changePsd" class="btn btn-primary" data-localize="label.submit">
                          </button>
                        <button
                        type="button"
                            class="btn btn-secondary"
                            data-dismiss="modal"
                            aria-hidden="true"
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

<script>
   
	formValidator();
	
    function formValidator() {
        $('#updatePassword').bootstrapValidator({
                live: 'enable',
                trigger: 'blur keyup',
                feedbackIcons: {
                    valid: 'fa fa-check ',
                    // invalid: 'fa fa-times',
                    validating: 'fa fa-refresh'
                },
                fields: {
                  oldPassword: {
                        validators: {
                            notEmpty: {
                                message: 'validator.requiredField'
                            },
                            stringLength: {
                                min: 6,
                                message:
                                    'validator.passwordLengthNotLessThan6'
                            },
                            password:{
                            	message:''
                            }
                        }
                    },
                    password: {
                        validators: {
                            notEmpty: {
                                message: 'validator.requiredField'
                            },
                            stringLength: {
                                min: 6,
                                message:
                                    'validator.passwordLengthNotLessThan6'
                            }
                        }
                    },
                    newPassword: {
                        validators: {
                            notEmpty: {
                                message: 'validator.requiredField'
                            },
                            identical: {
                                field: 'password',
                                message:
                                    'validator.twoPasswordsNotMatch'
                            },
                            stringLength: {
                                min: 6,
                                message: 'validator.passwordLengthNotLessThan6'
                            }
                        }
                    }
                }
            })
    
    
    }
    
    $(function(){
        $("#changePsd").click(function(e){
        	var bootstrapValidator = $("#updatePassword").data('bootstrapValidator');
        	bootstrapValidator.validate();
            if (bootstrapValidator.isValid()) {
                let old = sessionStorage.getItem("sessionPws");
                let currentOld = sha256_digest($("#oldPassword").val());
                console.log("old", old);
                
                console.log("currentOld", currentOld);
                if(old == currentOld){
                    $('.oldPsdValidator').hide()
                    $('#updatePassword')[0].submit()
                    sessionStorage.setItem("sessionPws",$("#newPassword").val());
                }else{
                    $('.oldPsdValidator').show()
                    return false
                }
            }
        })
    })
    
    </script>
