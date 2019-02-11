var dwSortcolumnsSelectedRow = 0;
var dwSortedSelectedRow = 0;


function exportReport(type) {
	document.viewReportForm.output.value = "" + type;
	document.getElementById('exportButton').click();
}

function cancelSort() 
{
	var div = $("#reportSort")[0];
	div.closePopup();
}

function cancelFilter()
{
	var div = $("#reportFilter")[0];
	div.closePopup();
}

function addFilterCriteria() {
	
	var tablebody = document.getElementById('wFilterCriteriaTBody');
	var lastRow = tablebody.rows.length;
	var row = tablebody.insertRow(lastRow);
	
	row.id = tablebody.rows.length;
	
	var cbtd = row.insertCell(0);
	var cb = document.createElement('input');
	cb.type = 'checkbox';
	cb.name = 'deleteFilterCriteria'
	cb.id = 'deleteFilterCriteria_' + lastRow;
	cbtd.appendChild(cb);
	
	var coltd = row.insertCell(1);
	viewReportController.getAvailableColumns( 
		function(columns) {
			var col = document.createElement('select');
			col.id = 'filterCriteria[' + lastRow + '].column';
			col.name = 'filterCriteria[' + lastRow + '].column';
			col.className = 'fill';
			for (var i = 0; i < columns.length; i = i + 1) {
				col.options[i] = new Option(columns[i][1], columns[i][0]);
			}
			coltd.appendChild(col);
		});
	
	var opertd = row.insertCell(2);
	viewReportController.getComparators(
		function(comparators) {
			var oper = document.createElement('select');
			oper.id = 'filterCriteria[' + lastRow + '].comparator';
			oper.name = 'filterCriteria[' + lastRow + '].comparator';
			oper.className = 'fill';
			for (var i = 0; i < comparators.length; i = i + 1) {
				oper.options[i] = new Option(comparators[i].display, comparators[i].operator); 
			}
			opertd.appendChild(oper);
		});
	
	var valtd = row.insertCell(3);
	var val = document.createElement('input');
	val.id = 'filterCriteria[' + lastRow + '].value';
	val.name = 'filterCriteria[' + lastRow + '].value';
	val.type = 'text';
	val.value = '';
	valtd.appendChild(val);
	
	var logtd = row.insertCell(4);
	var log = document.createElement('select');
	log.id = 'filterCriteria[' + lastRow + '].logicalOperator';
	log.name = 'filterCriteria[' + lastRow + '].logicalOperator';
	log.options[0] = new Option(' ', '');
	log.options[1] = new Option('And', 'AND');
	log.options[2] = new Option('Or', 'OR');
	logtd.appendChild(log);
}


function deleteClickFunc() {
	var oFilterCriteriaTBody = document.getElementById('wFilterCriteriaTBody');
	var rowCount = oFilterCriteriaTBody.rows.length;

	var tblRow = null;
	var tblRowID = null;

	var isChecked = false;
	
	var i = rowCount - 1;
	while (i >= 0) {
		tblRow = oFilterCriteriaTBody.rows[i];

		var checkBox = null;		
		for (var j = 0; j < tblRow.cells[0].childNodes.length; j = j + 1) {
			if (tblRow.cells[0].childNodes[j].nodeName == "INPUT") {
				checkBox = tblRow.cells[0].childNodes[j];
				break;
			}
		}

		isChecked = checkBox.checked;				
		if (isChecked) {
			oFilterCriteriaTBody.removeChild(tblRow);
		} 
		i = i - 1;
	}
}

function addSortCriteria() {
	var tablebody = document.getElementById('wSortCriteriaTBody');
	var lastRow = tablebody.rows.length;
	var row = tablebody.insertRow(lastRow);
	
	row.id = tablebody.rows.length;
	
	var cbtd = row.insertCell(0);
	var cb = document.createElement('input');
	cb.type = 'checkbox';
	cb.name = 'deleteSortCriteria'
	cb.id = 'deleteSortCriteria_' + lastRow;
	cbtd.appendChild(cb);
	
	var coltd = row.insertCell(1);
	viewReportController.getAvailableColumns( 
		function(columns) {
			var col = document.createElement('select');
			col.id = 'sortCriteria[' + lastRow + '].column';
			col.name = 'sortCriteria[' + lastRow + '].column';
			col.className = 'fill';
			for (var i = 0; i < columns.length; i = i + 1) {
				col.options[i] = new Option(columns[i][1], columns[i][0]);
			}
			coltd.appendChild(col);
		});
	

	
	var logtd = row.insertCell(2);
	var log = document.createElement('select');
	log.id = 'sortCriteria[' + lastRow + '].ascending';
	log.name = 'sortCriteria[' + lastRow + '].ascending';
	log.options[0] = new Option('Ascending', 'ascending');
	log.options[1] = new Option('Descending', 'decending');
	logtd.appendChild(log);
}

function deleteSortClickFunc() {
	var oSortCriteriaTBody = document.getElementById('wSortCriteriaTBody');
	var rowCount = oSortCriteriaTBody.rows.length;

	var tblRow = null;
	var tblRowID = null;

	var isChecked = false;
	
	var i = rowCount - 1;
	while (i >= 0) {
		tblRow = oSortCriteriaTBody.rows[i];

		var checkBox = null;		
		for (var j = 0; j < tblRow.cells[0].childNodes.length; j = j + 1) {
			if (tblRow.cells[0].childNodes[j].nodeName == "INPUT") {
				checkBox = tblRow.cells[0].childNodes[j];
				break;
			}
		}

		isChecked = checkBox.checked;				
		if (isChecked) {
			oSortCriteriaTBody.removeChild(tblRow);
		} 
		i = i - 1;
	}
}
