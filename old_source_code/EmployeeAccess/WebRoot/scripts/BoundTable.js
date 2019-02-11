function sort(fieldName, tableId)
{
	if(tableId != null && tableId.length > 0)
	{
		tableId = "_" + tableId;
	}
	
	if($("#sortField" + tableId).val() == fieldName)
	{
		var currentOrder = $("#sortOrder" + tableId).val();
		if(currentOrder == "false"){ currentOrder = "true"; } else { currentOrder = "false";}
		$("#sortOrder" + tableId).val(currentOrder);
	}
	else
	{
		$("#sortField" + tableId).val(fieldName);
		$("#sortOrder" + tableId).val("false");
	}

	var formId = $("#sortField" + tableId).parents('form').attr('id');
	
	Spring.remoting.submitForm(fieldName + "Link", formId, { _eventId:"sort" + tableId });
	
	return false;
}

function select(submitId, keys, values, tableid)
{
	var selectId = "select";
	var tableIdAppend = tableid;
	
	if(tableid != null && tableid.length > 0)
	{
		selectId += "_" + tableid;
		tableIdAppend = "_" + tableid;
	}
	
	for(i = 0; i < keys.length; i++)
	{
		document.getElementById(keys[i] + "Search").value = values[i];
	}
	
	var formId = $(document.getElementById("sortField" + tableIdAppend)).parents('form').attr('id');
	
	Spring.remoting.submitForm(submitId, formId, { _eventId: selectId });
	
	return false;
}
