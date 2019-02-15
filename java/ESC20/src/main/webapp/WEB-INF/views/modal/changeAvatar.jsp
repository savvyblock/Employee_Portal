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
                    aria-hidden="true"
                    data-localize="label.closeModal"
                    data-localize-location="title"
                >
                    &times;
                </button>
                <h4 class="modal-title" data-localize="label.changeAvatar"></h4>
            </div>
            <form id="changeAvatar" action="changeAvatar" method="POST">
                      <div class="modal-body">
                        <input id="userName" hidden="hidden" type="text" value="${sessionScope.userDetail.nameF}">
                        <input id="avatarImg" hidden="hidden" type="text" name="file">
                        <input id="avatarImgName" hidden="hidden" type="text" name="fileName">
                        <p data-localize="label.chooseImageSize"></p>
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
  console.log(event)
  let avatarSize = 5*1024*1024
  console.log(event.target.size)
  console.log(avatarSize)
  if (event.target.size > avatarSize) {
        $("#pictureError").show()
        $("#saveAvatar").addClass("disabled").attr("disabled","disabled")
      } else {
        $("#pictureError").hide()
        var fileDom = document.getElementById('imgUpFile')
        var img = document.getElementById('imgContentImg')
        if (fileDom && img) {
            fileHandle(fileDom, img)
        }
      }
            
}
function fileHandle(fileDom, img) {
    //read
    var file = fileDom.files[0]
    var fileName = file.name
    var type = file.type.split("/")
    var username = $("#userName").val()
    var name = (new Date()).valueOf() + username  + "." + type[1];
    console.log(name)
    $("#avatarImgName").val(name)
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
</script>