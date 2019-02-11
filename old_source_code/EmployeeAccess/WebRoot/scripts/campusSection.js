function addMeetingRow(){
	Spring.remoting.submitForm("selectedCampusSection.sectionNumber", "mainForm", { _eventId:"add_courseMeetings" });
}