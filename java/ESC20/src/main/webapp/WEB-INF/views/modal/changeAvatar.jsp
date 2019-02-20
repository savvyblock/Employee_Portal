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
                <button
                    type="button"
                    class="close"
                    data-dismiss="modal"
                    aria-hidden="true">
                    <span class="hide" data-localize="label.closeModal"></span>
                    &times;
                </button>
                <h4 class="modal-title" data-localize="label.changeAvatar"></h4>
            </div>
            <form id="changeAvatar" action="changeAvatar" method="POST">
                      <div class="modal-body">
                        <input id="userName" hidden="hidden" type="text" value="${sessionScope.userDetail.nameF}" title="" data-localize="accessHint.userName">
                        <input id="avatarImg" hidden="hidden" type="text" name="file" title="" data-localize="accessHint.file">
                        <input id="avatarImgName" hidden="hidden" type="text" name="fileName" title="" data-localize="accessHint.fileName">
                        <p>
                            <label data-localize="label.chooseImageSize" for="imgUpFile"></label>
                        </p>
                        <input class="avatar-file" type="file" name="file"  id="imgUpFile"  onchange="startRead()" accept=".jpg, .jpeg, .png"/>
                        <p class="error-hint hide" id="pictureError" data-localize="validator.pictureTooLarge"></p>
                        <br>
                        <img class="showUploadImg" src="${sessionScope.userDetail.avatar}" alt="">
                      </div>
                    <div class="modal-footer">
                        <button type="button" id="saveAvatar"  class="btn btn-primary disabled" data-localize="label.ok" disabled>
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
  var resultImg
  $(function(){
    $("#saveAvatar").click(function(){
        var img = document.getElementById('imgContentImg')
        img.style.backgroundImage = "url('" + resultImg + " ') "
        $("#changeAvatar")[0].submit()
        
    })
  })
function startRead() {
  var fileDom = document.getElementById('imgUpFile')
  var img = document.getElementById('imgContentImg')
  if (fileDom && img) {
      fileHandle(fileDom, img)
  }
            
}
function fileHandle(fileDom, img) {
    //read
    let avatarSize = 0.5*1024*1024
    var file = fileDom.files[0]
    var fileName = file.name
    var type = file.type.split("/")
    var username = $("#userName").val()
    var name = (new Date()).valueOf() + username  + "." + type[1];
    console.log(file.size)
    $("#avatarImgName").val(name)
    if (file.size > avatarSize) {
        $("#pictureError").show()
        $("#saveAvatar").addClass("disabled").attr("disabled","disabled")
      } else {
        $("#pictureError").hide()
        var reader = new FileReader()
        reader.readAsDataURL(file)
        reader.onloadstart = function() {
            console.log('do upload ......')
        }
        //done
        reader.onload = function(e) {
            //file
            $(".showUploadImg").attr("src",reader.result)
            $("#avatarImg").val(reader.result)
            resultImg = reader.result
            $("#saveAvatar").removeClass("disabled").removeAttr("disabled")
            
        }
        reader.onerror = function(){
            /* error handler **/
        }
      }
    
}
</script>