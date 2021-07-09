
function saveWrkjlRequest(isAdd){
  
    $(event.currentTarget).parents(".modal").focus()

    var date = moment($("#startDateInputWrkjl").val()).format('YYYYMMDD');
    var jc = $("#jobCode").val().split("-");
    var jobCode =  jc[0];
    var payFreq =  '';
    
    var startHour = $("#startH").val();
    var startMinute = $("#startM").val();
    var endHour = $("#endH").val();
    var endMinute = $("#endM").val();
    var comment = $("#comment").val();
    var empNbr = $("#empNbrModal").val();
    var fromTmAp = $("#startAmOrPmW").val();
    var toTmAp = $("#endAmOrPmW").val();
	var isNew = $('#isNew').val();

        var obj = { 
            'wrkDate': date,
            'jobCode':jobCode,
            'fromTmHr':startHour,
            'fromTmMin':startMinute,
            'toTmHr':endHour,
            'toTmMin': endMinute,
            'commnt':comment,
            'toTmAp':toTmAp,
            'fromTmAp':fromTmAp,
            'payFreq':payFreq,
            'empNbr':empNbr,
            'isNew':isNew
        }
        $.ajax({
            type: "POST", 
            url: urlMain +"/wrkjl/submitWrkjlRequest", 
            data: JSON.stringify(obj), // this creates formatted JSON string for ajax post to asmx service
        	contentType: "application/json; charset=utf-8",
        	success: function (result) {
                console.log(result);
                if(isAdd){
                	 $("#jobCode").val('')
                	    
                	  $("#startH").val('');
                	  $("#startM").val('');
                	  $("#endH").val('');
                	  $("#endM").val('');
                	  $("#comment").val('');
                	  $("#empNbrModal").val('');
                	  $("#startAmOrPm").val('');
                	  $("#endAmOrPm").val('');
                }else{
                	closewrkjlForm()
                }
                location.reload();
            },
            error:function(res){
                console.log(res)
            }
        })	
 
}
function closewrkjlForm() {
$("#wrkjlModal").modal('hide')
	$('#wrkjlModal').data('bootstrapValidator', null)
$(".submitClose").addClass('highlight')
}