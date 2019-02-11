 
function displayEaModalDialog(autoApprove)
{	
	if(autoApprove && $(".deleteRow").size() > 1)
	{
		$(".modal_film_add_ea_delete").addClass("modal_film");
		$(".modal_contents_add_ea_delete").addClass("modal_contents");
		$(".modal_film_add_ea_delete").removeClass("hidden");
		$(".modal_contents_add_ea_delete").removeClass("hidden");
	}
	else 
	{
		$("#okButton").click();
	}
}	

function modalOk()
{
	$(".modal_film_add_ea_delete").addClass("hidden");
	$(".modal_contents_add_ea_delete").addClass("hidden");
}

function modalCancel()
{			
	$(".modal_film_add_ea_delete").addClass("hidden");
	$(".modal_contents_add_ea_delete").addClass("hidden");
}

function updatePrimaryAccounts()
{
	var primChecked = false;
	  	
	$(".bankAmt").each(function (i) 
	{
		if($(this).val() == 0 && !primChecked && $('#isDeleteHidden_'+i).val() == "false"  && $('#primaryPayroll').val() == "")
		{
			$('#primary_'+i).prop("checked",true);	
			$('#primaryPayroll').val(i);
			$("#bankDepositAmountCurrent_"+i+"Cell").hide();
			$(this).hide();	
			primChecked = true;	
		}
		
		else if($(this).val() == 0 && !primChecked && $('#primaryPayroll').val() != "" &&  $('#primaryPayroll').val() == parseInt(i) )
		{
			var row = $(this).attr("id").substring($(this).attr("id").length-1, $(this).attr("id").length);
			
			$('#primary_'+$('#primaryPayroll').val()).prop("checked", false);	
			$('#primary_'+row).prop("checked",true);	
			
			oldVal = $('#primaryPayroll').val();
			
			$('#primaryPayroll').val(row);
			
			$("#bankDepositAmountCurrent_"+$('#primaryPayroll').val()+"Cell").hide();
			$(this).hide();
			
			primChecked = true;	
		}
	});
}

function focusOnError()
{
	var errorItems = $('.errorInput');
	if( errorItems.length > 0)
		errorItems[0].focus();
}

$(document).ready(function() 
{ 
    updatePrimaryAccounts();
    focusOnError();
          
    $('.primaryRadio').click(function() 
    {		
		$(".bankAmt").each(function (i) 
		{
			$('#primary_'+i).prop("checked",false);
			$("#bankDepositAmountCurrent_"+i+"Cell").show();
			$(this).show();	
		});
				
		$(this).prop("checked",true);	
		
		var idNum = $(this).attr("id").substring($(this).attr("id").length-1, $(this).attr("id").length);
		
		$('#primaryPayroll').val(idNum);
		$("#bankDepositAmount_"+idNum).val("0.00");
		$("#bankDepositAmountCurrent_"+idNum+"Cell").hide();
		$("#bankDepositAmount_"+idNum).hide();		
				
    });
    
    $('.bankAmt').blur(function() 
    {	
    	if($(this).val().indexOf('.') < 0)
    	{
    		var newValue = $(this).val() + ".00";
    		$(this).val(newValue);
    	}		
    });
		  
});
