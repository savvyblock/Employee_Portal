
$().ready(function () {
	var picklist_type;
	var select_type;
	var picklist_id;
	$(".picklistBtn").click(function (event) {
		picklist_type = this.getAttribute("picklist_type");
		select_type = this.getAttribute("select_type");
		picklist_id = this.getAttribute("picklist_id");
		loadWindow();
		$(document).ready(function() {
			displayPopup("Picklist");
		});
		event.preventDefault();
	});
	
	$(".cancelButton").click(function (event) {
		$('#filterDiv').html("");
		$('#dataDiv').html("");
		hidePopup("Picklist");
		event.preventDefault();
	});
		
	$('.instructFilterBtn').click(function (event){
		$('#instructorDataTable > tbody').html("");
		var year = $('#instructorFilterYear')[0].value;
		var campus = $('#instructorFilterCampus')[0].value;
		var grade = $('#instructorFilterGrade')[0].value;
		$(function() {
			picklistDwrController.getInstructors(year,campus,grade,
				function(instructors) {
					var numOfRows = instructors.length;
					for (x=0; x<numOfRows; x++) {
						$('#instructorDataTable > tbody').append('<tr id=instructorDataTableRow' + x + '><td>' + instructors[x].instructorId + '</td>' +
							'<td>' + instructors[x].name + '</td><td>' + instructors[x].campus + '</td><td>' + instructors[x].grade + '</td><td>' + instructors[x].homeroom +
							'</td></tr>');
					}
				});
		});
		
	});
	
	function setValue(row) {
		var picklist_value = row.getAttribute("picklist_value");
		document.getElementById(picklist_id).value = picklist_value;
		$('#filterDiv').html("");
		$('#dataDiv').html("");
		hidePopup("Picklist");
	}
						
	function getFilter(picklist_type, select_type) {
		$('#filterDiv').load('filter.htm', {'_event_':'getFilter',
			'picklistType':picklist_type,'selectType':select_type});
	}
	function displayPopup(tableType) {
		$(".modal_film_add" + tableType).addClass("modal_film");
		$(".modal_contents_add" + tableType).addClass("modal_contents");
		$(".modal_film_add" + tableType).removeClass("hidden");
		$(".modal_contents_add" + tableType).removeClass("hidden");
	}
	function hidePopup(tableType) {
		$(".modal_film_add" + tableType).removeClass("modal_film");
		$(".modal_contents_add" + tableType).removeClass("modal_contents");
		$(".modal_film_add" + tableType).addClass("hidden");
		$(".modal_contents_add" + tableType).addClass("hidden");
	}
	function loadWindow() {
		if (picklist_type == 'w_picklist_campuses') {
			$('#dataDiv').load('campusData.htm', {'_event_':'getData','picklistType':picklist_type,'selectType':select_type},function() {
				processCampuses();
				$('.pickListSelectBtn').livequery('click', function(event){setValue(this);});
			});
		}
		else if (picklist_type == 'w_picklist_instructors') {
			$('#filterDiv').load('instructorFilter.htm', {'_event_':'getFilter'});
			$('#dataDiv').load('instructorData.htm', {'_event_':'getData','picklistType':picklist_type});
		}
		else {
			$('#filterDiv').html("no filter");
			$('#dataDiv').html("no data");
		}
	}
	function processCampuses() {
		picklistDwrController.getCampuses(
			function(campuses) {
				var numOfRows = campuses.length;
				for (x=0; x<numOfRows; x++) {
					$('#campusDataTable > tbody').append('<tr><td style="white-space:nowrap;">' +
						'<a href="#" id="" class="link_button pickListSelectBtn" picklist_value="' + 
						campuses[x].campusId + '">' + campuses[x].campus + '</a></td></tr>');
				}
		});
	}
});


