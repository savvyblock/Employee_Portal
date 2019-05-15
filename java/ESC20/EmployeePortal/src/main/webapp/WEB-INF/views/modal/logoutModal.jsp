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
                <p>${sessionScope.languageJSON.label.areYouQuit}</p>
            </div>
            <div class="modal-footer">
                <a href="/<%=request.getContextPath().split("/")[1]%>/logout">
                    <button type="button" role="button"  class="btn btn-primary">
                    	${sessionScope.languageJSON.label.ok}
                    </button>
                </a>
                <button
                    type="button" role="button"
                    class="btn btn-secondary closeModal"
                    data-dismiss="modal"
                    id="cancelAdd"
                    onclick="setCountTime()"
                >
                	${sessionScope.languageJSON.label.cancel}
                </button>
            </div>             
        </div>
        <!-- /.modal-content -->
    </div>
    <!-- /.modal -->
</div>
<script src="/<%=request.getContextPath().split("/")[1]%>/js/viewJs/modal/logoutModal.js"></script>

