		
	//---------------------------------
	// Stuff that happens on page load
	//---------------------------------
	function addLoadEvent(func) {
	  var oldonload = window.onload;
	  if (typeof window.onload != 'function') {
	    window.onload = func;
	  } else {
	    window.onload = function() {
	      if (oldonload) {
	        oldonload();
	      }
	      func();
	      	    }
	  }
	}	
	function setFormsDisabled()
	{
		$("#OffenseCodesDetail select, #OffenseCodesDetail input").each(function()
			{
				this.disabled=true;
			});
		$("#OffenseLevelDetail select, #OffenseLevelDetail input").each(function()
			{
				this.disabled=true;
			});
		$("#ActionCodesDetail select, #ActionCodesDetail input").each(function()
			{
				this.disabled=true;
			});
		$("#LocationCodesDetail select, #LocationCodesDetail input").each(function()
			{
				this.disabled=true;
			});
		$("#ReportedByGroupDetail select, #ReportedByGroupDetail input").each(function()
			{
				this.disabled=true;
			});
	}	
	function checkHistoricalView()
	{
		if(historicalView)
		{
			document.getElementById('OffenseCodesBeginCrudButtons').style.visibility="hidden";
			document.getElementById('OffenseLevelBeginCrudButtons').style.visibility="hidden";
			document.getElementById('ActionCodesBeginCrudButtons').style.visibility="hidden";
			document.getElementById('LocationCodesBeginCrudButtons').style.visibility="hidden";
			document.getElementById('ReportedByGroupBeginCrudButtons').style.visibility="hidden";
			document.getElementById('saveButtonDiv').style.display="none";
		}
	}	
	addLoadEvent(setFormsDisabled);  // On page load, disable both forms.
	addLoadEvent(checkHistoricalView);  // On page load, check to see if historical view is set.  if so, make read only.
		

	//--------------------
	// Variables
	//--------------------
	// Row index of highlighted items in tables.
	var activeOffenseCodes = "";
	var activeOffenseLevel = "";
	var activeActionCode = "";
	var activeLocationCode = "";
	var activeReportedByGroup = "";
	// Flag to indicate currently in the middle of a CRUD operation
	var doingOffenseCodesCRUD = false;
	var doingOffenseLevelCRUD = false;
	var doingActionCodesCRUD = false;
	var doingLocationCodesCRUD = false;
	var doingReportedByGroupCRUD = false;
	
	
	//------------------------------
	// Mouse hover on rows styling
	//------------------------------

	function mouseOverCell(row)
	{
		if (row.className != "selectedRow")
		{
			row.className = "hoverRow";
		}
	}
	function mouseOutCell(row)
	{
		if (row.className != "selectedRow")
		{
			row.className = "unselectedRow";
		}
	}


	//----------------------------------------------
	// Selecting rows and clearing row selections
	//----------------------------------------------

	function selectRow(clickedRow)
	{	
		var type = $(clickedRow).parents("table")[0].id.replace("Table", "");
		
		var doingCRUD = eval("doing" + type + "CRUD");
		if(!doingCRUD)
		{
			//clearSelection(type);
				//clearSelection(type);
			if(clickedRow.className!= "selectedRow")
				clickedRow.className="selectedRow";
			else
				clickedRow.className="unselectedRow";
			
			if(!$("input", clickedRow.cells[0]).attr('checked'))
				$("input", clickedRow.cells[0]).attr('checked', true);
			else
				$("input", clickedRow.cells[0]).attr('checked', false);
				
			storeInfo(clickedRow);
		}
		else
		{
			alert("Please finish add/edit operation below.");
		}
	}
	
	function storeInfo(clickedRow){
		var type = $(clickedRow).parents("table")[0].id.replace("Table", "");
		
		if(type == "OffenseCodes")
		{	
			var id = $("div", clickedRow.cells[1]).text();				
			document.getElementById('OffenseCodesIdTextField').value = id;
			document.getElementById('OffenseCodesPeimsCodeSelect').value = $("div", clickedRow.cells[2]).text();
			document.getElementById('OffenseCodesDescriptionTextField').value = $("div", clickedRow.cells[3]).text();				
			eval("active" + type + " = clickedRow.rowIndex;");
		}
		else if(type == "OffenseLevel")
		{	
			var id = $("div", clickedRow.cells[1]).text();				
			document.getElementById('OffenseLevelIdTextField').value = id;				
			document.getElementById('OffenseLevelDescriptionTextField').value = $("div", clickedRow.cells[2]).text();				
			eval("active" + type + " = clickedRow.rowIndex;");
		}
		else if(type == "ActionCodes")
		{	
			var id = $("div", clickedRow.cells[1]).text();				
			document.getElementById('ActionCodesIdTextField').value = id;			
			document.getElementById('ActionCodesPeimsCodeSelect').value = $("div", clickedRow.cells[2]).text();	
			document.getElementById('ActionCodesDescriptionTextField').value = $("div", clickedRow.cells[3]).text();				
			eval("active" + type + " = clickedRow.rowIndex;");
		}
		else if(type == "LocationCodes")
		{	
			var id = $("div", clickedRow.cells[1]).text();
			document.getElementById('LocationCodesIdTextField').value = id;
			document.getElementById('LocationCodesDescriptionTextField').value = $("div", clickedRow.cells[2]).text();
			eval("active" + type + " = clickedRow.rowIndex;");
		}
		else if(type == "ReportedByGroup")
		{	
			var id = $("div", clickedRow.cells[1]).text();
			document.getElementById('ReportedByGroupIdTextField').value = id;
			document.getElementById('ReportedByGroupDescriptionTextField').value = $("div", clickedRow.cells[2]).text();
			eval("active" + type + " = clickedRow.rowIndex;");
		}
	}

	function clearSelection(type)
	{
		// clear table
		var table = document.getElementById(type + 'Table');
		if (table != null)
		{
			var rows = table.rows;
			for (i=1; i<rows.length; i=i+1)
			{
				rows[i].className="unselectedRow";
			}
		}

		// clear form
		$("#" + type + "Detail select, #" + type + "Detail input").each(function(){
			this.value="";
		});

		eval("active" + type + " = '';");
	}

	//-------------------------------------------------------------------
	// "begin CRUD" button behavior
	// (intermediate state before CRUD action is actually performed)
	//-------------------------------------------------------------------
	
	function beginAdd(type)
	{
		eval("doing" + type + "CRUD = true;");  // set crud operation to true
		clearSelection(type);  // clear out table selection		
			
		document.getElementById(type + 'Detail').style.border="solid 1px black";       // make detail div have a black border
		document.getElementById(type + 'BeginCrudButtons').style.visibility="hidden";  // hide "begin CRUD" buttons
		document.getElementById(type + 'AddButtons').style.display="block";            // reveal Add buttons
		$("#" + type + "Detail select, #" + type + "Detail input").each(function(){    // enable the inputs and selects
			this.disabled=false;
		});
		
		document.getElementById(type + "IdTextField").focus();  // set focus
	}

	function beginEdit(type, clickedRow)
	{
		storeInfo(clickedRow);
		var activeRow = eval("active" + type);	
		if(activeRow != "")
		{
			eval("doing" + type + "CRUD = true;");  // set crud operation to true			

			document.getElementById(type + 'Detail').style.border="solid 1px black";
			document.getElementById(type + 'BeginCrudButtons').style.visibility="hidden";
			document.getElementById(type + 'EditButtons').style.display="block";
			$("#" + type + "Detail select, #" + type + "Detail input").each(function(){
				this.disabled=false;
			});
			
			document.getElementById(type + "IdTextField").focus();  // set focus			
		}
		else
		{
			alert("Please select a row to edit.");
		}
	}

	function beginDelete(type)
	{
		var table = type + 'Table';
		var activeRow = eval("active" + type);
		var results = new Array();
		results = showCheckboxValue(table)
		if(results.length > 0)
		{
			displayModalDialog(type, results);
		}
		else
		{
			alert("Please select a row to delete.");
		}
	}
	
	function showCheckboxValue(divId){
		var results = new Array();
		var i = 1;
		var count = 0
		$("#"+ divId + " :checkbox")
			 .each(
					function(){
						if(this.checked){
						 	results[count] = i;
						 	count++;
						 } 
						 i++;
						 return $(this);
					}
			 );
		return results;
 	}
	
	function setReadOnlyMode(type)
	{
		document.getElementById(type + 'Detail').style.border="solid 1px lightgrey";
		document.getElementById(type + 'BeginCrudButtons').style.visibility="visible";
		document.getElementById(type + 'AddButtons').style.display="none";
		document.getElementById(type + 'EditButtons').style.display="none";
		$("#" + type + "Detail select, #" + type + "Detail input").each(function(){
			this.disabled=true;
		});
	}

	function cancel(type)
	{
		eval("doing" + type + "CRUD = false;");	
		tb_remove();
	}

	


	//-------------------------
	// Commit CRUD actions
	//-------------------------

	function commitDelete(type, results)
	{
		//var activeRow = eval("active" + type);
		//if(activeRow != "")
		var table = document.getElementById(type + 'Table');
		if (table != null)
		{
			for( var i = results.length-1;  i >= 0;  i--) 
			{
				var row = results[i];
				table.deleteRow(row);				
			}		
		}
	}

	function commitAdd(type)
	{
		var table = document.getElementById(type + 'Table');
		if (table != null)
		{		
			var rowIndex = table.rows.length;
			var row = table.insertRow(rowIndex);
			var edit = "<a class=\"anchor thickbox\" onclick=\"beginEdit(\'"+type+"\', this.parentNode.parentNode.parentNode)\" href=\"#TB_inline?height=215&width=620&inlineId=hiddenModal"+type+"&modal=true\">Edit</a>"					
			var empty = "&nbsp;"
			row.onclick = function() {selectRow(this)};
			row.className = "unselectedRow";
			row.onmouseover = function() {mouseOverCell(this)};
			row.onmouseout = function() {mouseOutCell(this)};
			
			if(type == "OffenseCodes")
			{					
				var id = document.getElementById('OffenseCodesIdTextField').value;
				var peimsCode = document.getElementById('OffenseCodesPeimsCodeSelect').value;
				var description = document.getElementById('OffenseCodesDescriptionTextField').value;
				
				if((id=="" && peimsCode=="" && description==""))	
					table.deleteRow(rowIndex);  // Undo the earlier addition of a row, since you can't add a blank row.
				else
				{
					addCellHelper(row, 0, "userOffenseCodes", rowIndex-1, "", empty);
					addCellHelper(row, 1, "userOffenseCodes", rowIndex-1, "id", id);
					addCellHelper(row, 2, "userOffenseCodes", rowIndex-1, "peimsCode", peimsCode);
					addCellHelper(row, 3, "userOffenseCodes", rowIndex-1, "description", description);
					addCellHelper(row, 4, "userOffenseCodes", rowIndex-1, "edit", edit);
				}				
			}
			else if(type == "OffenseLevel")
			{
				var id = document.getElementById('OffenseLevelIdTextField').value;				
				var description = document.getElementById('OffenseLevelDescriptionTextField').value;
									
				if((id=="" && description==""))				
					table.deleteRow(rowIndex);  // Undo the earlier addition of a row, since you can't add a blank row.				
				else
				{
					addCellHelper(row, 0, "userOffenseCodes", rowIndex-1, "", empty);
					addCellHelper(row, 1, "offenseLevels", rowIndex-1, "id", id);					
					addCellHelper(row, 2, "offenseLevels", rowIndex-1, "description", description);
					addCellHelper(row, 3, "userOffenseCodes", rowIndex-1, "edit", edit);
				}
			}
			else if(type == "ActionCodes")
			{					
				var id = document.getElementById('ActionCodesIdTextField').value;
				var peimsCode = document.getElementById('ActionCodesPeimsCodeSelect').value;
				var description = document.getElementById('ActionCodesDescriptionTextField').value;
									
				if((id=="" && peimsCode=="" && description==""))	
					table.deleteRow(rowIndex);  // Undo the earlier addition of a row, since you can't add a blank row.
				else
				{
					addCellHelper(row, 0, "userActionCodes", rowIndex-1, "", empty);
					addCellHelper(row, 1, "userActionCodes", rowIndex-1, "id", id);
					addCellHelper(row, 2, "userActionCodes", rowIndex-1, "peimsCode", peimsCode);
					addCellHelper(row, 3, "userActionCodes", rowIndex-1, "description", description);
					addCellHelper(row, 4, "userActionCodes", rowIndex-1, "edit", edit);
				}				
			}
			else if(type == "LocationCodes")
			{
				var id = document.getElementById('LocationCodesIdTextField').value;				
				var description = document.getElementById('LocationCodesDescriptionTextField').value;
									
				if((id=="" && description==""))				
					table.deleteRow(rowIndex);  // Undo the earlier addition of a row, since you can't add a blank row.				
				else
				{
					addCellHelper(row, 0, "locationCodes", rowIndex-1, "", empty);
					addCellHelper(row, 1, "locationCodes", rowIndex-1, "id", id);					
					addCellHelper(row, 2, "locationCodes", rowIndex-1, "description", description);
					addCellHelper(row, 3, "locationCodes", rowIndex-1, "edit", edit);
				}
			}
			else if(type == "ReportedByGroup")
			{
				var id = document.getElementById('ReportedByGroupIdTextField').value;				
				var description = document.getElementById('ReportedByGroupDescriptionTextField').value;
									
				if((id=="" && description==""))				
					table.deleteRow(rowIndex);  // Undo the earlier addition of a row, since you can't add a blank row.				
				else
				{
					addCellHelper(row, 0, "reportedByGroups", rowIndex-1, "", empty);
					addCellHelper(row, 1, "reportedByGroups", rowIndex-1, "id", id);					
					addCellHelper(row, 2, "reportedByGroups", rowIndex-1, "description", description);
					addCellHelper(row, 3, "reportedByGroups", rowIndex-1, "edit", edit);
				}
			}
			
			// Clear out form		
			cancel(type);
			
			// Scroll table div down to show new row!
			var tableDiv = document.getElementById(type + 'TableDiv');
			tableDiv.scrollTop = 10000;
			
			tb_remove();
		}
	}
	
	// Adds a cell to the given row.  This cell will contain a div and a hidden input.
	function addCellHelper(row, cellIndex, bindingListName, rowIndex, bindingPropertyName, value)
	{
		var Div = document.createElement("div");				
		Div.innerHTML = value;
		var HiddenField = document.createElement("input");
		HiddenField.setAttribute("type", "hidden");
		HiddenField.setAttribute("value", value);
		HiddenField.setAttribute("name", bindingListName + "[" + rowIndex + "]." + bindingPropertyName);
		var Cell = row.insertCell(cellIndex);
		Cell.appendChild(Div);
		Cell.appendChild(HiddenField);
		if(bindingPropertyName == "edit"){
			Cell.style.textAlign = "center";
			tb_init('a.thickbox') //Used to attach the the thickbox classs to the anchor tab.
		}
	}

	function commitEdit(type)
	{
		var activeRow = eval("active" + type);
		var table = document.getElementById(type + 'Table');		
		
		if(activeRow != "" && table != null)
		{	
			var row = table.rows[activeRow];					
			
			if(type == "OffenseCodes")
			{
				var id = document.getElementById('OffenseCodesIdTextField').value;
				var peimsCode = document.getElementById('OffenseCodesPeimsCodeSelect').value;
				var description = document.getElementById('OffenseCodesDescriptionTextField').value;
								
				if(!(id=="" && peimsCode=="" && description==""))
				{	
					editCellHelper(row, 1, id);
					editCellHelper(row, 2, peimsCode);
					editCellHelper(row, 3, description);
				}				
			}
			else if(type == "OffenseLevel")
			{
				var id = document.getElementById('OffenseLevelIdTextField').value;
				var description = document.getElementById('OffenseLevelDescriptionTextField').value;
								
				if(!(id=="" && description==""))
				{	
					editCellHelper(row, 1, id);
					editCellHelper(row, 2, description);
				}	
			}
			else if(type == "ActionCodes")
			{
				var id = document.getElementById('ActionCodesIdTextField').value;
				var peimsCode = document.getElementById('ActionCodesPeimsCodeSelect').value;
				var description = document.getElementById('ActionCodesDescriptionTextField').value;
								
				if(!(id=="" && peimsCode=="" && description==""))
				{	
					editCellHelper(row, 1, id);
					editCellHelper(row, 2, peimsCode);
					editCellHelper(row, 3, description);
				}	
			}
			else if(type == "LocationCodes")
			{
				var id = document.getElementById('LocationCodesIdTextField').value;
				var description = document.getElementById('LocationCodesDescriptionTextField').value;
								
				if(!(id=="" && description==""))
				{	
					editCellHelper(row, 1, id);
					editCellHelper(row, 2, description);
				}	
			}
			else if(type == "ReportedByGroup")
			{
				var id = document.getElementById('ReportedByGroupIdTextField').value;
				var description = document.getElementById('ReportedByGroupDescriptionTextField').value;
								
				if(!(id=="" && description==""))
				{	
					editCellHelper(row, 1, id);
					editCellHelper(row, 2, description);
				}	
			}		
			
			// Clear out form		
			cancel(type);
			eval("active" + type + " = '';");
			
			tb_remove();
		}
	}
	
	// Edits a cell in the given row.  This cell contains a div and a hidden input.
	function editCellHelper(row, cellIndex, value)
	{
		// Set div text
		$("div", row.cells[cellIndex]).text(value);
		// Set hidden field value
		$("input", row.cells[cellIndex]).attr("value", value);	
	}


	//-------------------------
	//  Modal Dialog stuff
	//-------------------------

	var currentModalDialogTableType = "";

	function displayModalDialog(tableType, results)
	{
		currentModalDialogTableType = tableType;
		currentModalDialogResults = results;
		$(".modal_film_add").addClass("modal_film");
		$(".modal_contents_add").addClass("modal_contents");
		$(".modal_film_add").removeClass("hidden");
		$(".modal_contents_add").removeClass("hidden");
	}

	function modalOk()
	{	
		commitDelete(currentModalDialogTableType, currentModalDialogResults);
	
		$(".modal_film_add").addClass("hidden");
		$(".modal_contents_add").addClass("hidden");
		
		currentModalDialogTableType = "";
	}

	function modalCancel()
	{
		$(".modal_film_add").addClass("hidden");
		$(".modal_contents_add").addClass("hidden");
		
		currentModalDialogTableType = "";
	}

