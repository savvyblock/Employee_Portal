$(function() {
	changeformValidator()
	$("input").bind('keypress', function(e) {
		var eCode = e.keyCode
			? e.keyCode
			: e.which
				? e.which
				: e.charCode
		if (eCode == 13) {
			$(this).click()
			event.preventDefault();
		}
	})
	$(".requestModalBtn .btn").focus(function() {
		$(".submitClose").removeClass('highlight')
	})
})

function closeRequestForm() {
	$('#changeRequestForm')
		.data('bootstrapValidator')
		.destroy()
	$('#changeRequestForm').data('bootstrapValidator', null)
	changeformValidator()
	$(".submitClose").addClass('highlight')
}

function changeformValidator() {
	$('#changeRequestForm').bootstrapValidator({
		live : 'disabled',
		trigger : null,
		excluded : [ ":disabled" ],
		feedbackIcons : {
			valid : 'fa fa-check ',
			validating : 'fa fa-refresh'
		},
		fields : {
			changeCommuteDist : {
				validators : {
					notEmpty : {
						message : requiredFieldValidator
					},
					regexp : {
						regexp : /^(0\.[0-9]|[0-9][0-9]{0,2}(\.[0-9])?)$/,
						message : pleaseEnterCorrectFormatTravelCommuteValidator
					}
				}
			},
		}
	})
}

function changeRequest(isCommuteChange) {
	if (isCommuteChange) {
		$("#isCommuteChange").val(isCommuteChange)
	}
	$(event.currentTarget).parents(".modal").focus()
	var changeCommuteDist = $("#changeCommuteDist").val().trim()
	var empNbr = $("#empNbrModal").val();
	var bootstrapValidator = $('#changeRequestForm').data('bootstrapValidator')
	bootstrapValidator.validate()
	if (bootstrapValidator.isValid()) {
		{
			var obj = {
				'changeCommuteDist' : changeCommuteDist,
				'empNbr' : empNbr
			}
			$.ajax({
				type : "POST",
				url : urlMain + "/travelRequest/travelRequest",
				data : obj,
				cache : false,
				success : function(data) {
					$('#changeRequestForm')[0].submit()
				},
				error : function(data) {
					console.log(res)
				}
			})
		}
	} else return
}