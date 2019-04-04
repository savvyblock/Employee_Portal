<div
    class="modal fade"
    id="undoModal"
    tabindex="-1"
    role="dialog"
    aria-labelledby="undoModal"
    aria-hidden="true"
    data-backdrop="static"
>
    <div class="modal-dialog approveForm warn-modal" style="max-width:350px;">
        <div class="modal-content text-center">
            <div class="modal-body">
                <i class="fa fa-exclamation-circle warn-icon"></i>
                <p>${sessionScope.languageJSON.label.areYouRescind}</p>
            </div>
            <div class="modal-footer">
                <button type="button" role="button"  class="btn btn-primary sureUndo">
                	${sessionScope.languageJSON.label.ok}
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
