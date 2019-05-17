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
  var avatarSize = 0.5*1024*1024
  var file = fileDom.files[0]
  var fileName = file.name
  var type = file.type.split("/")
  var username = $("#userName").val()
  var name = (new Date()).valueOf() + username  + "." + type[1];
  console.log(file.name)
  if((file.type).indexOf("image/jpeg")==-1&&(file.type).indexOf("image/jpg")==-1&&(file.type).indexOf("image/png")==-1){  
      $("#notPictureError").show()
      $("#saveAvatar").addClass("disabled").attr("disabled","disabled")
      $(".showUploadImg").attr("src","")
      $(".showUploadImg").hide()
   }else{
    $("#avatarImgName").val(name)
    $("#notPictureError").hide()
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
            $(".showUploadImg").show()
            $("#avatarImg").val(reader.result)
            resultImg = reader.result
            $("#saveAvatar").removeClass("disabled").removeAttr("disabled")
            
        }
        reader.onerror = function(){
            /* error handler **/
        }
      }  
   }
  
  
}