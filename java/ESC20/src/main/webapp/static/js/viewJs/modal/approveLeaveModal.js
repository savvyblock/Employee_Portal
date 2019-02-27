var showInfo = false
function showDetail(dom){
            $(dom).toggleClass("active");
            if(showInfo){
                $("#leaveBalanceSummary").removeClass("active");
                showInfo = false
            }else{
                $("#leaveBalanceSummary").addClass("active");
                showInfo = true
            }
            
        }
$(function(){
        
        let comment = false
        let action = false;
        let approve = false;
        $('#approve').on('ifChecked', function(event) {
            $('.supervisorComment').show()
            comment = true;
            action = true;
            approve = true
        })
        $('#disApprove').on('ifChecked', function(event) {
            $('.supervisorComment').show()
            comment = true
            action = true;
            approve = false
        })
       
        $('#noAction').on('ifChecked', function(event) {
            $('.supervisorComment').hide()
            comment = false
            action = true;
        })
        $('#save').on('click', function() {
            let commentValue = $(".supervisorComment .form-control").val()
            console.log(commentValue)
            if(action){
                if(!comment){
                    $('.approveValidator').hide()
                    $('.commentValidator').hide()
                    if(approve){
                        $("#appComment").val(commentValue)
                        $('#approveLeave')[0].submit()
                    }else{
                        $("#disComment").val(commentValue)
                        $('#disapproveLeave')[0].submit()
                    }
                    
                }else{
                    if(commentValue&&commentValue!=''){
                        $('.approveValidator').hide()
                        $('.commentValidator').hide()
                        if(approve){
                            $("#appComment").val(commentValue)
                            $('#approveLeave')[0].submit()
                        }else{
                            $("#disComment").val(commentValue)
                            $('#disapproveLeave')[0].submit()
                        }
                    }else{
                        $('.approveValidator').hide()
                        $('.commentValidator').show()
                        return false
                    }
                }
            }else{
                $('.approveValidator').show()
                $('.commentValidator').hide()
                return false
            }
        })
    })
