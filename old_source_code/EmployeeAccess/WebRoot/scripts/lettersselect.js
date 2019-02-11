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

	function selectLetter(clickedRow)
	{	
		clearLetterSelection();
		clickedRow.className="selectedRow";
		document.getElementById("selectedLetterId").value = clickedRow.cells[0].innerHTML;
	}

	function clearLetterSelection()
	{
		// clear table
		var table = document.getElementById("letterTable");
		if (table != null)
		{
			var rows = table.rows;
			for (i=1; i<rows.length; i=i+1)
			{
				rows[i].className="unselectedRow";
			}
		}
	}
	