<div class="modal fade" id="sessionNotLoginModal" style="z-index: 9999;" tabindex="-1" role="dialog" aria-hidden="true" data-backdrop="static">
  <div class="modal-dialog">
      <div class="modal-content">
          <div class="modal-body">
            <p class="text-center">
              <span>${sessionScope.languageJSON.createAccount.sessionWillTimeoutIn}</span> 
              <span id="timeCountdown"></span> 
              <span>${sessionScope.languageJSON.createAccount.minutes}</span>
            </p>
          </div>
          <div class="modal-footer">
              <button type="button" class="btn btn-primary" id="resetTimeBtn">
                ${sessionScope.languageJSON.createAccount.resetTimeout}
              </button>
          </div>
      <!-- /.modal-content -->
  </div>
  </div>
  <!-- /.modal -->
</div>