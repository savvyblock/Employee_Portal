<div
    class="modal fade"
    id="changeAvatarModal"
    tabindex="-1"
    role="dialog"
    aria-labelledby="changeAvatarModal"
    aria-hidden="true"
    data-backdrop="static"
>
    <div class="modal-dialog approveForm" style="max-width:350px;">
        <div class="modal-content">
            <div class="modal-header">
                <h4 class="modal-title" data-localize="label.changeAvatar"></h4>
                <button
                    type="button" role="button"
                    class="close closeModal"
                    data-dismiss="modal"
                    aria-label="" data-localize="label.closeModal" data-localize-location="aria-label" data-localize-notText="true"
                    >
                    &times;
                </button>
            </div>
            <form id="changeAvatar" action="changeAvatar" method="POST">
            		<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                      <div class="modal-body">
                        <input id="userName" hidden="hidden" type="text" value="${sessionScope.userDetail.nameF}" aria-label="" data-localize="accessHint.userName">
                        <input id="avatarImg" hidden="hidden" type="text" name="file" aria-label="" data-localize="accessHint.file">
                        <input id="avatarImgName" hidden="hidden" type="text" name="fileName" aria-label="" data-localize="accessHint.fileName">
                        <p>
                            <label data-localize="label.chooseImageSize" for="imgUpFile"></label>
                        </p>
                        <input class="avatar-file" type="file" name="file"  id="imgUpFile"  onchange="startRead()" accept="image/jpeg,image/jpg,image/png"/>
                        <p class="error-hint hide" role="alert" aria-atomic="true" id="pictureError" data-localize="validator.pictureTooLarge"></p>
                        <p class="error-hint hide" role="alert" aria-atomic="true" id="notPictureError" data-localize="validator.pictureChoose"></p>
                        <br>
                        <img class="showUploadImg hide" src="${sessionScope.userDetail.avatar}" alt="">
                      </div>
                    <div class="modal-footer">
                        <button type="button" role="button" id="saveAvatar"  class="btn btn-primary disabled" data-localize="label.ok" disabled>
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
<script src="/<%=request.getContextPath().split("/")[1]%>/js/viewJs/modal/changeAvatar.js"></script>
