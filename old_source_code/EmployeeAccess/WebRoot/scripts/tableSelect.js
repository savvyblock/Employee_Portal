function clearSelection(table) {
	// clear table
	if (table != null) {
		var rows = table.rows;
		for (i = 1; i < rows.length; i = i + 1) {
			if (!$(rows[i]).hasClass("deleteRow")) {
				$(rows[i]).removeClass("selected");
				$(rows[i]).addClass("unselectedRow");
			}
		}
	}

	// clear form
	//$(table).find("input:checked").each( function() {
	//	this.checked = false;
	//});
}

function selectRow(clickedRow) {
	var table = $(clickedRow).parents("table")[0];

	clearSelection(table);
	if (!$(clickedRow).hasClass("deleteRow")) {
		$(clickedRow).addClass("selected");
		$(clickedRow).removeClass("unselectedRow");
	}
	
	//$(clickedRow).find("input:checkbox").each( function() {
	//	this.checked = true;
	//})
}

function deleteSelectedRow(tableId, inputId) {
	var table = $(clickedRow).parents("table")[0];

	if (tableRow != null) {
		$(clickedRow).find("input:checkbox").each( function() {
			this.checked = true;
		})
	}
}

//function toggleImage(image) {
//	var row = $(image).parents("tr")[0];
//	if (image.className == 'deleteRowButton') {
//		image.className = 'deleteRowButtonSelected';
//		row.className = 'deleteRow'
//	} else {
//		image.className = 'deleteRowButton';
//		row.className = 'unselectedRow';
//	}
//}

function toggleDelete(image, inputId) {
	var row = $(image).parents("tr")[0];
	if (image.className == "deleteRowButton") {
		image.className = 'deleteRowButtonSelected';
		row.className = 'deleteRow';
			
		var input = document.getElementById(inputId);
		input.value = "true";
			
	} else {
		image.className = 'deleteRowButton';
		row.className = 'unselectedRow';
		
		var input = document.getElementById(inputId);
		input.value = "false";
	}
}

// ----------------------------------------------
// Selecting rows and clearing row selections
// ----------------------------------------------

function selectRowMulti(clickedRow) {
	
	var table = $(clickedRow).parents("table")[0];
	var td = $(clickedRow).children("td")[0];
	var image = $(td).find("span")[0];
	
	if(clickedRow.className!= "selected"){
		if (image != null) {
			image.className = 'deleteRowButton';
		}
		clickedRow.className="selected";
	} else {
		clickedRow.className="unselectedRow";
	}
}