$(document).ready(setupDropPicks);

function setupDropPicks(){
	var url = "/EmployeeAccess/app/DropPickSearch";
	var dropPicks = $(".dropPick");
	
	for(var i = 0; i < dropPicks.length; i++)
	{
		var dropPick = dropPicks[i];
		var codeType = $(dropPick).attr("codeType");
		var isCached = $(dropPick).attr("isCached");
		var isFiltered = $(dropPick).attr("isFiltered");
		var isSorted = $(dropPick).attr("isSorted");
		var context = $(dropPick).attr("context");
		var popupWidth = $(dropPick).attr("popupWidth");
		
		if(popupWidth.length == 0)
		{
			popupWidth = "300px";
		}
		
		var itemsId = $(dropPick).attr("items");
		var items = url;
		
		if(itemsId != null && itemsId.length > 0)
		{
			items = eval(itemsId);
			
			if(items == null)
			{
				items = url;
			}
		}
		
		$(dropPick).autocomplete({
		source: function( request, response) {
			$.getJSON(url, {
					codeType: codeType, isCached: isCached, isFiltered: isFiltered, isSorted: isSorted, context: context, q: request.term
				}, function(data) {
						response($.map(data.results, function(item){
							return {
								label: item.code,
								value: item.descr
							}
						}));
				});
		},
		focus: function() {
			return false;
		}
	});
		
		
//		$(dropPick).autocomplete(items, {
//			width: popupWidth,
//			scoll: false,
//			scrollHeight: 150,
//			selectFirst: false,
//			max: 50,
//			highlight : function(value, term) {
//				return value;
//			},
//			extraParams: {codeType: codeType, isCached: isCached, isFiltered: isFiltered, isSorted: isSorted, context: context},
//			formatItem: function(data, i, n, value) {
//				return data[0] + "  :  " + data[1];
//			}
//		});
	}
//	
//	$(".dropPick").bind(function(event, data, formatted) {
//		if (data)
//		{
//			$(this).val(data[0]);
//			$(this).trigger("change");
//			$("#" + $(this).attr('descriptionField')).val(data[1]);
//		}
//	});
}

function doDropPickDropDown(path)
{
	if($(document.getElementById(path)).attr('isOpen') == 'false')
	{
		$(document.getElementById(path)).trigger('focus');
		$(document.getElementById(path)).trigger('click');
		$(document.getElementById(path)).attr('isOpen','true');
	}
	else
	{
		$(document.getElementById(path)).trigger('blur');
		$(document.getElementById(path)).attr('isOpen','false');
	}
}