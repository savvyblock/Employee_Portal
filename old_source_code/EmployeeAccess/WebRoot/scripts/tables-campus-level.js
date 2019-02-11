		
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
		$("#AdministratedByDetail select, #AdministratedByDetail input").each(function()
			{
				this.disabled=true;
			});
		$("#ReportedByPersonDetail select, #ReportedByPersonDetail input").each(function()
			{
				this.disabled=true;
			});
	}	
	function checkHistoricalView()
	{
		if(historicalView)
		{
			document.getElementById('AdministratedByBeginCrudButtons').style.visibility="hidden";
			document.getElementById('ReportedByPersonBeginCrudButtons').style.visibility="hidden";			
			document.getElementById('saveButtonDiv').style.display="none";
		}
	}	
	addLoadEvent(setFormsDisabled);  // On page load, disable both forms.
	addLoadEvent(checkHistoricalView);  // On page load, check to see if historical view is set.  if so, make read only.
		

	//--------------------
	// Variables
	//--------------------
	// Row index of highlighted items in tables.
	var activeAdministratedBy = "";
	var activeReportedByPerson = "";
	// Flag to indicate currently in the middle of a CRUD operation
	var doingAdministratedByCRUD = false;
	var doingReportedByPersonCRUD = false;
	
	
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
			if(clickedRow.className!= "selectedRow")
				clickedRow.className="selectedRow";
			else
				clickedRow.className="unselectedRow";
				
			if(!$("input", clickedRow.cells[0]).attr('checked'))
				$("input", clickedRow.cells[0]).attr('checked', true);
			else
				$("input", clickedRow.cells[0]).attr('checked', false);
			
			storeInfo(clickRow)
		}
		else
		{
			alert("Please finish add/edit operation below.");
		}
	}
	
	function storeInfo(clickedRow){
		var type = $(clickedRow).parents("table")[0].id.replace("Table", "");
		
		if(type == "AdministratedBy")
			{	
				
				document.getElementById('AdministratedByIdTextField').value = $("div", clickedRow.cells[1]).text();
				document.getElementById('AdministratedByFirstNameTextField').value = $("div", clickedRow.cells[2]).text();
				document.getElementById('AdministratedByMiddleNameTextField').value = $("div", clickedRow.cells[3]).text();
				document.getElementById('AdministratedByLastNameTextField').value = $("div", clickedRow.cells[4]).text();
				document.getElementById('AdministratedByGenTextField').value = $("div", clickedRow.cells[5]).text();
				eval("active" + type + " = clickedRow.rowIndex;");
			}
			else if(type == "ReportedByPerson")
			{
				document.getElementById('ReportedByPersonIdTextField').value = $("div", clickedRow.cells[1]).text();
				document.getElementById('ReportedByPersonFirstNameTextField').value = $("div", clickedRow.cells[2]).text();
				document.getElementById('ReportedByPersonMiddleNameTextField').value = $("div", clickedRow.cells[3]).text();
				document.getElementById('ReportedByPersonLastNameTextField').value = $("div", clickedRow.cells[4]).text();
				document.getElementById('ReportedByPersonGenTextField').value = $("div", clickedRow.cells[5]).text();
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
		//clearSelection(type);
		//setReadOnlyMode(type);
		
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
			var empty = "&nbsp;"
			row.onclick = function() {selectRow(this)};
			row.className = "unselectedRow";
			row.onmouseover = function() {mouseOverCell(this)};
			row.onmouseout = function() {mouseOutCell(this)};
			
			if(type == "AdministratedBy")
			{
				var id = document.getElementById('AdministratedByIdTextField').value;
				var firstName = document.getElementById('AdministratedByFirstNameTextField').value;
				var middleName = document.getElementById('AdministratedByMiddleNameTextField').value;
				var lastName = document.getElementById('AdministratedByLastNameTextField').value;
				var genCode = document.getElementById('AdministratedByGenTextField').value;
				var edit = "<a class=\"anchor thickbox\" onclick=\"beginEdit(\'AdministratedBy\', this.parentNode.parentNode.parentNode)\" href=\"#TB_inline?height=200&width=620&inlineId=hiddenModalContent&modal=true\">Edit</a>"						
 
				if((id=="" && firstName=="" && middleName=="" && lastName=="" && genCode==""))	
					table.deleteRow(rowIndex);  // Undo the earlier addition of a row, since you can't add a blank row.
				else
				{
					addCellHelper(row, 0, "administratedBys", rowIndex-1, "empty", empty);
					addCellHelper(row, 1, "administratedBys", rowIndex-1, "id", id);
					addCellHelper(row, 2, "administratedBys", rowIndex-1, "firstName", firstName);
					addCellHelper(row, 3, "administratedBys", rowIndex-1, "middleName", middleName);
					addCellHelper(row, 4, "administratedBys", rowIndex-1, "lastName", lastName);
					addCellHelper(row, 5, "administratedBys", rowIndex-1, "generationalCode", genCode);
					addCellHelper(row, 6, "administratedBys", rowIndex-1, "edit", edit);
				}				
			}
			else if(type == "ReportedByPerson")
			{
				var id = document.getElementById('ReportedByPersonIdTextField').value;
				var firstName = document.getElementById('ReportedByPersonFirstNameTextField').value;
				var middleName = document.getElementById('ReportedByPersonMiddleNameTextField').value;
				var lastName = document.getElementById('ReportedByPersonLastNameTextField').value;
				var genCode = document.getElementById('ReportedByPersonGenTextField').value;
				var edit = "<a class=\"anchor thickbox\" onclick=\"beginEdit(\'ReportedByPerson\', this.parentNode.parentNode.parentNode)\" href=\"#TB_inline?height=200&width=620&inlineId=hiddenModalContent2&modal=true\">Edit</a>"			
				if((id=="" && firstName=="" && middleName=="" && lastName=="" && genCode==""))	
					table.deleteRow(rowIndex);  // Undo the earlier addition of a row, since you can't add a blank row.
				else
				{
					addCellHelper(row, 0, "administratedBys", rowIndex-1, "empty", empty);
					addCellHelper(row, 1, "reportedByPersons", rowIndex-1, "id", id);
					addCellHelper(row, 2, "reportedByPersons", rowIndex-1, "firstName", firstName);
					addCellHelper(row, 3, "reportedByPersons", rowIndex-1, "middleName", middleName);
					addCellHelper(row, 4, "reportedByPersons", rowIndex-1, "lastName", lastName);
					addCellHelper(row, 5, "reportedByPersons", rowIndex-1, "generationalCode", genCode);
					addCellHelper(row, 6, "administratedBys", rowIndex-1, "edit", edit);
				}	
			}
			
			// Clear out form		
			cancel(type);
			
			// Scroll table div down to show new row!
			var tableDiv = document.getElementById(type + 'TableDiv');
			tableDiv.scrollTop = 10000;
		}
	}
	
	// Adds a cell to the given row.  This cell will contain a div and a hidden input.
	function addCellHelper(row, cellIndex, bindingListName, rowIndex, bindingPropertyName, value)
	{
		var Cell = row.insertCell(cellIndex);
		var Div = document.createElement("div");				
		Div.innerHTML = value;
		var HiddenField = document.createElement("input");
		HiddenField.setAttribute("type", "hidden");
		HiddenField.setAttribute("value", value);
		HiddenField.setAttribute("name", bindingListName + "[" + rowIndex + "]." + bindingPropertyName);
		Cell.appendChild(Div);
		Cell.appendChild(HiddenField);
		if(bindingPropertyName ==  "edit"){
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
			
			if(type == "AdministratedBy")
			{
				var id = document.getElementById('AdministratedByIdTextField').value;
				var firstName = document.getElementById('AdministratedByFirstNameTextField').value;
				var middleName = document.getElementById('AdministratedByMiddleNameTextField').value;
				var lastName = document.getElementById('AdministratedByLastNameTextField').value;
				var genCode = document.getElementById('AdministratedByGenTextField').value;
				
				if(!(id=="" && firstName=="" && middleName=="" && lastName=="" && genCode==""))
				{	
					editCellHelper(row, 1, id);
					editCellHelper(row, 2, firstName);
					editCellHelper(row, 3, middleName);
					editCellHelper(row, 4, lastName);
					editCellHelper(row, 5, genCode);
				}				
			}
			else if(type == "ReportedByPerson")
			{
				var id = document.getElementById('ReportedByPersonIdTextField').value;
				var firstName = document.getElementById('ReportedByPersonFirstNameTextField').value;
				var middleName = document.getElementById('ReportedByPersonMiddleNameTextField').value;
				var lastName = document.getElementById('ReportedByPersonLastNameTextField').value;
				var genCode = document.getElementById('ReportedByPersonGenTextField').value;
								
				if(!(id=="" && firstName=="" && middleName=="" && lastName=="" && genCode==""))
				{	
					editCellHelper(row, 1, id);
					editCellHelper(row, 2, firstName);
					editCellHelper(row, 3, middleName);
					editCellHelper(row, 4, lastName);
					editCellHelper(row, 5, genCode);
				}	
			}		
			
			// Clear out form		
			cancel(type);
			eval("active" + type + " = '';");
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

