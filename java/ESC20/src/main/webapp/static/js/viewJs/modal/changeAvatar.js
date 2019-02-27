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