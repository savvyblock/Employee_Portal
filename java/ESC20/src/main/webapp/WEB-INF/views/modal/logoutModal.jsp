<div
    class="modal fade"
    id="logoutModal"
    tabindex="-1"
    role="dialog"
    aria-labelledby="logoutModal"
    aria-hidden="true"
    data-backdrop="static"
>
    <div class="modal-dialog approveForm warn-modal" style="max-width:350px;">
        <div class="modal-content text-center">             
            <div class="modal-body">
                <i class="fa fa-exclamation-circle warn-icon"></i>
                <p style="text-align:center;font-size:20px;margin:10px 0;"><b id="timeCounter"></b></p>
                <p data-localize="label.areYouQuit"></p>
            </div>
            <div class="modal-footer">
                <a href="/<%=request.getContextPath().split("/")[1]%>/logout">
                    <button type="button"  class="btn btn-primary" data-localize="label.ok">
                    </button>
                </a>
                <button
                    type="button"
                    class="btn btn-secondary"
                    data-dismiss="modal"
                    aria-hidden="true"
                    id="cancelAdd"
                    data-localize="label.cancel"
                    onclick="startCountTime()"
                >
                </button>
            </div>             
        </div>
        <!-- /.modal-content -->
    </div>
    <!-- /.modal -->
</div>
