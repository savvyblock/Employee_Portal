package net.esc20.txeis.EmployeeAccess.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import net.esc20.txeis.EmployeeAccess.domain.LeaveRequestCalendarCommand;
import net.esc20.txeis.EmployeeAccess.domainobject.LeaveRequest;
import net.esc20.txeis.EmployeeAccess.service.LeaveRequestService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;

import net.esc20.txeis.EmployeeAccess.domainobject.CalendarDetail;
import net.esc20.txeis.EmployeeAccess.domainobject.CalendarEvents;

@Controller
@SessionAttributes("LeaveRequestCalendarCommand")
@RequestMapping(value = "/leave/leaveRequestCalendar")
public class LeaveRequestCalendarController {

	@Autowired
	private LeaveRequestService leaveRequestService;

	@RequestMapping(method = RequestMethod.GET)
	public String getForm(Model model, HttpServletRequest request) {
		
		LeaveRequestCalendarCommand command = new LeaveRequestCalendarCommand();
		command.setLeaveParameters(leaveRequestService.getLeaveParams());
		command.setUserEmpNumber(((String) request.getSession().getAttribute("currentUser_employeeNumber")).trim());
	
		model.addAttribute("LeaveRequestCalendarCommand", command);
		
		return "leaveRequestCalendar";
	}
	
	
	//This will get the events for a month on the calendar screen
	@RequestMapping(method = RequestMethod.GET, produces="application/json", value = "/getEvents")
	@ResponseBody
	public List<CalendarEvents> getEvents(HttpServletRequest request, @RequestParam("start") String start, @RequestParam("end") String end, @RequestParam("emp_nbr") String empNbr) {
		if (empNbr==null || empNbr.trim().length()==0) {
			empNbr = (String) request.getSession().getAttribute("currentUser_employeeNumber");
		}
		
		List<CalendarEvents> events = new ArrayList<CalendarEvents>();
		
		events = leaveRequestService.getCalendarEvents(empNbr, start, end);
		
		return events;
	}

	//This will get the particular details for one event on the calendar
	@RequestMapping(method = RequestMethod.GET, produces="application/json", value = "/getDetails")
	@ResponseBody
	public CalendarDetail getDetails(@RequestParam("id") String id) {
					
		return leaveRequestService.getCalendarDetail(id);
	}

}
