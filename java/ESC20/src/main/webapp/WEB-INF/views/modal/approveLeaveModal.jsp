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
                  <input type="hidden" name="leaveId" id="leaveId" title="" data-localize="accessHint.id" />
                  <table class="table border-table no-thead">
                    <tbody>
                      <tr>
                        <td><b data-localize="label.employee"></b></td>
                        <td data-localize="label.employee" data-localize-location="title">
                            <button class="showBalanceBtn pull-right a-btn" type="button" onclick="showDetail(this)">
                            <span class="hide" data-localize="label.showLeaveBalanceSummary"></span>
                                <i class="fa fa-angle-double-down"></i>
                                <i class="fa fa-angle-double-up"></i>
                            </button>
                            <div id="employee"></div>
                        </td>
                      </tr>
                      <tr>
                        <td colspan="2" data-localize-location="title" data-localize="label.leaveBalanceSummary" id="leaveBalanceSummary">
                            <div><span data-localize="label.leaveBalanceSummaryFor"></span> 
                                <span id="infoEmpName"></span>
                            </div>
                            <div id="infoDetail"></div>
                        </td>
                      </tr>
                      <tr>
                        <td><b data-localize="leaveRequest.startDate"></b></td>
                        <td data-localize="leaveRequest.startDate" data-localize-location="title"><div id="startDate"></div></td>
                      </tr>
                      <tr>
                        <td><b data-localize="leaveRequest.endDate"></b></td>
                        <td data-localize="leaveRequest.endDate" data-localize-location="title"><div id="endDate"></div></td>
                      </tr>
                      <tr>
                        <td><b data-localize="leaveRequest.leaveType"></b></td>
                        <td data-localize="leaveRequest.leaveType" data-localize-location="title"><div id="leaveType"></div></td>
                      </tr>
                      <tr>
                        <td><b data-localize="leaveRequest.absenceReason"></b></td>
                        <td data-localize="leaveRequest.absenceReason" data-localize-location="title"><div id="absenceReason"></div></td>
                      </tr>
                      <tr>
                        <td><b data-localize="leaveRequest.leaveRequested"></b></td>
                        <td data-localize="leaveRequest.leaveRequested" data-localize-location="title">
                            <span id="leaveRequested"></span>
                            <span data-localize="label.days"></span>
                        </td>
                      </tr>
                      <tr>
                        <td><b data-localize="leaveRequest.commentLog"></b></td>
                        <td data-localize="leaveRequest.commentLog" data-localize-location="title"><div id="commentLog"></div></td>
                      </tr>
                    </tbody>
                  </table>
                  <div class="form-group action-group">
                        <label for="approve">
                            <input class="icheck" type="radio" name="approve" id="approve" value="Approve">
                            <span data-localize="label.approve"></span>
                        </label>
                        <label for="disApprove">
                            <input class="icheck" type="radio" name="approve" id="disApprove" value="Disapprove">
                            <span data-localize="label.disapprove"></span>
                        </label>
                        <!-- <label for="noAction">
                            <input class="icheck" type="radio" name="approve" id="noAction" value="NoAction">
                            <span data-localize="label.noAction"></span>
                        </label> -->
                  </div>
                  <div class="form-group supervisorComment">
                      <label for="supervisorComment" data-localize="label.supervisorComment"></label>
                      <textarea class="form-control form-text" name="supervisorComment" id="supervisorComment" cols="30" rows="4"></textarea>
                  </div>
                  <p class="approveValidator error-hint" data-localize="validator.pleaseSelectOne"></p>
                  <p class="commentValidator error-hint" data-localize="validator.pleaseEnterComment"></p>
              </div>
              <div class="modal-footer">
                  <button
                      type="submit"
                      class="btn btn-primary"
                      id="save"
                      name="save"
                      data-localize="label.save"
                  >
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
        </div>
        <!-- /.modal-content -->
    </div>
    <!-- /.modal -->
</div>
<form hidden="hidden" action="disapproveLeave" id="disapproveLeave" method="POST">
    <input type="text" name="id" id="disId"  title="" data-localize="accessHint.id">
    <input type="text" name="chain" id="disChain"  title="" data-localize="accessHint.chain">
    <input type="text" name="comment" id="disComment"  title="" data-localize="accessHint.comment">
</form>

<form hidden="hidden" action="approveLeave" id="approveLeave" method="POST">
        <input type="text" name="id" id="appId"  title="" data-localize="accessHint.id">
        <input type="text" name="chain" id="appChain"  title="" data-localize="accessHint.chain">
        <input type="text" name="comment" id="appComment"  title="" data-localize="accessHint.comment">
    </form>

<script>
    var showInfo = false
    function showDetail(dom){
                $(dom).toggleClass("active");
                if(showInfo){
                    $("#leaveBalanceSummary").removeClass("active");
                    showInfo = false
                }else{
                    $("#leaveBalanceSummary").addClass("active");
                    showInfo = true
                }
                
            }
$(function(){
            
            let comment = false
            let action = false;
            let approve = false;
            $('#approve').on('ifChecked', function(event) {
                $('.supervisorComment').show()
                comment = true;
                action = true;
                approve = true
            })
            $('#disApprove').on('ifChecked', function(event) {
                $('.supervisorComment').show()
                comment = true
                action = true;
                approve = false
            })
           
            $('#noAction').on('ifChecked', function(event) {
                $('.supervisorComment').hide()
                comment = false
                action = true;
            })
            $('#save').on('click', function() {
                let commentValue = $(".supervisorComment .form-control").val()
                console.log(commentValue)
                if(action){
                    if(!comment){
                        $('.approveValidator').hide()
                        $('.commentValidator').hide()
                        if(approve){
                            $("#appComment").val(commentValue)
                            $('#approveLeave')[0].submit()
                        }else{
                            $("#disComment").val(commentValue)
                            $('#disapproveLeave')[0].submit()
                        }
                        
                    }else{
                        if(commentValue&&commentValue!=''){
                            $('.approveValidator').hide()
                            $('.commentValidator').hide()
                            if(approve){
                                $("#appComment").val(commentValue)
                                $('#approveLeave')[0].submit()
                            }else{
                                $("#disComment").val(commentValue)
                                $('#disapproveLeave')[0].submit()
                            }
                        }else{
                            $('.approveValidator').hide()
                            $('.commentValidator').show()
                            return false
                        }
                    }
                }else{
                    $('.approveValidator').show()
                    $('.commentValidator').hide()
                    return false
                }
            })
        })

</script>