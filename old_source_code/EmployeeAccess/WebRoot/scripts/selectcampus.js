
function updateCampuses() {

	var selectedSchoolYear = document.getElementById('schoolYear').value;

	mainDecoratorController.getCampuses(selectedSchoolYear,
		function(campuses) {
			var dropdown = document.getElementById('campus');

			//Clear dropdown
			var oldrows = dropdown.getElementsByTagName('option');

			while (oldrows.length > 0) {
				dropdown.removeChild(oldrows[oldrows.length-1]);
			}

			//Fill dropdown
			var row = document.createElement('option');
			row.value = "";

			for (var i = 0; i < campuses.length; i = i + 1) {

				row = document.createElement('option');
				row.value = campuses[i].id;
				row.innerHTML = campuses[i].displayName;

				dropdown.appendChild(row);
			}

		});
}

function cancelCampusChanges() {
	$('#cancelChangesActionId').val(true);
}
