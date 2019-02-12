<div
    class="modal fade"
    id="logoutModal"
    tabindex="-1"
    role="dialog"
    aria-labelledby="logoutModal"
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
                    aria-hidden="true"
                    data-localize="label.closeModal"
                    data-localize-location="title"
                    onclick="startCountTime()"
                >
                    &times;
                </button>
                <h4 class="modal-title" data-localize="label.information"></h4>
            </div>
              
                      <div class="modal-body">
                      
                        <p data-localize="label.areYouQuit">Are you sure you want to quit?</p>
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
