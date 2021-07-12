$(function(){   
      $("#submitBtn").click(function(){
    	 var agreeOrNot = $("input[name='agreeTermForPopup']:checked").val();
         if(agreeOrNot === 'Y'){      	
        	 $.ajax({
        	        url : '/EmployeePortal/acceptLicense/' ,
        	        data: {csrfmiddlewaretoken: $("#csrfmiddlewaretoken").val()},
        	        type : "POST",
        	        success : function(result) {
        	        	if(result.acceptLicense){  	        		
        	        		 $("#agreementModal").removeClass("show");
        	            	 $("#agreementModal").hide();
        	        	}else{
        	        		console.log("###### false");
        	        	}
        	        },
        	        error : function() {
        	        	console.log("###### error");
        	        }
        	    });
         }else{
        	 document.getElementById('logoutForm').submit();
         }
     })
})
