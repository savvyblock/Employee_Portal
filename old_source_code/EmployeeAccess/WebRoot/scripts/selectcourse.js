
function updateCourses() {

	var selectedInstructor = document.getElementById('massCriteria.instructorId').value;

	massScreeningDwr.getCourses(selectedInstructor,
		function(courses) {
			var dropdown = document.getElementById('massCriteria.courses');

			//Clear dropdown
			var oldrows = dropdown.getElementsByTagName('option');

			while (oldrows.length > 0) {
				dropdown.removeChild(oldrows[oldrows.length-1]);
			}

			//Fill dropdown
			var row = document.createElement('option');
			row.value = "";
			row.innerHTML = "";
			dropdown.appendChild(row);

			for (var i = 0; i < courses.length; i = i + 1) {

				row = document.createElement('option');
				row.value = courses[i].id;
				row.innerHTML = courses[i].displayName;

				dropdown.appendChild(row);
			}

		});
}
