package net.esc20.txeis.EmployeeAccess.controller;

import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import net.esc20.txeis.EmployeeAccess.Exception.LeaveRequestException;
import net.esc20.txeis.EmployeeAccess.domain.LeaveRequestSubmittalsCommand;
import net.esc20.txeis.EmployeeAccess.domainobject.LeaveEmployeeData;
import net.esc20.txeis.EmployeeAccess.domainobject.LeaveRequest;
import net.esc20.txeis.EmployeeAccess.service.LeaveRequestService;
import net.esc20.txeis.EmployeeAccess.service.LeaveService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

@Controller
@SessionAttributes("LeaveRequestSubmittalsCommand")
@RequestMapping(value = "/leave/leaveRequestSubmittals")
public class LeaveRequestSubmittalsController {

	@Autowired
	private LeaveRequestService leaveRequestService;

	@Autowired
	private LeaveService leaveService;

	@RequestMapping(method = RequestMethod.GET)
	public String getForm(Model model, HttpServletRequest request) {
		LeaveRequestSubmittalsCommand command = new LeaveRequestSubmittalsCommand();

		command.setLeaveParameters(leaveRequestService.getLeaveParams());
		command.setSupervisorFirstName(((String) request.getSession().getAttribute("currentUser_firstName")).trim());
		command.setSupervisorLastName(((String) request.getSession().getAttribute("currentUser_lastName")).trim());
		boolean supervisorsOnly=true; // true indicates only those direct reports who are supervisors are to be returned
		boolean excludeTempApprovers=false; // false indicates that non-supervisors who have submittals to process should be added to the list of supervisors
		command.setDirectReportEmployees(leaveRequestService.getSupervisorDirectReports(
				((String) request.getSession().getAttribute("currentUser_employeeNumber")).trim(), 
				command.getLeaveParameters().isUsePMIS(), supervisorsOnly, excludeTempApprovers));
		
		// set up supervisor level zero (logged in supervisor) for the supervisor chain
		LeaveEmployeeData level0Supervisor = new LeaveEmployeeData();
		level0Supervisor.setEmployeeNumber(((String) request.getSession().getAttribute("currentUser_employeeNumber")).trim());
		level0Supervisor.setFirstName(((String) request.getSession().getAttribute("currentUser_firstName")).trim());
		level0Supervisor.setMiddleName(((String) request.getSession().getAttribute("currentUser_middleName")).trim());
		level0Supervisor.setLastName(((String) request.getSession().getAttribute("currentUser_lastName")).trim());
		level0Supervisor.setNumDirectReports(command.getDirectReportEmployees().size());
		ArrayList<LeaveEmployeeData> supervisorChain = new ArrayList<LeaveEmployeeData>();
		supervisorChain.add(0, level0Supervisor);
		command.setSupervisorChain(supervisorChain);
		command.setSupervisorChainLevel(0);

		command.setLeaveRequests(leaveRequestService.getSupervisorSumittedLeaveRequests(((String) request.getSession().getAttribute("currentUser_employeeNumber")).trim()));

		model.addAttribute("LeaveRequestSubmittalsCommand", command);
		return "leaveRequestSubmittals";
	}
	
	@RequestMapping(method=RequestMethod.POST, value = "/nextSupervisorLevel")
	public ModelAndView nextSupervisorLevel(@ModelAttribute("LeaveRequestSubmittalsCommand") LeaveRequestSubmittalsCommand command, HttpServletRequest request, BindingResult result) {
		ModelMap modelMap = new ModelMap();
		
		// set the value of supervisor chain level; in some situations (such as on a refresh) the value is not accurate
		int currentLevel = command.getSupervisorChain().size() - 1;
		command.setSupervisorChainLevel(currentLevel);			

		LeaveEmployeeData nextLevelSupervisor = null;
		// insure the selected employee is not the current supervisor ... this happens if the user does
		// a refresh immediately after clicking on "Next Level"
		String currentSupervisorEmployeeNumber = command.getSupervisorChain().get(command.getSupervisorChainLevel()).getEmployeeNumber();
		if (command.getSelectedDirectReportEmployeeNumber()!=null && 
			command.getSelectedDirectReportEmployeeNumber().trim().length()>0 && 
			!currentSupervisorEmployeeNumber.equals(command.getSelectedDirectReportEmployeeNumber())) {
			nextLevelSupervisor = leaveRequestService.getEmployeeData(command.getSelectedDirectReportEmployeeNumber());
		}
		
		boolean supervisorsOnly=true; // true indicates only those direct reports who are supervisors are to be returned
		boolean excludeTempApprovers=false; // false indicates that non-supervisors who have submittals to process should be added to the list of supervisors
		if (nextLevelSupervisor != null) {
			int nextSupervisorLevel = command.getSupervisorChainLevel() + 1;
			command.setSupervisorChainLevel(nextSupervisorLevel);
			command.getSupervisorChain().add(nextSupervisorLevel, nextLevelSupervisor);
			command.setDirectReportEmployees(leaveRequestService.getSupervisorDirectReports(nextLevelSupervisor.getEmployeeNumber(), 
					command.getLeaveParameters().isUsePMIS(), supervisorsOnly, excludeTempApprovers));
			command.setSelectedDirectReportEmployeeNumber("");
			command.setLeaveRequests(leaveRequestService.getSupervisorSumittedLeaveRequests(nextLevelSupervisor.getEmployeeNumber()));
		} else {
			command.setDirectReportEmployees(leaveRequestService.getSupervisorDirectReports(currentSupervisorEmployeeNumber, 
					command.getLeaveParameters().isUsePMIS(), supervisorsOnly, excludeTempApprovers));
			command.setSelectedDirectReportEmployeeNumber("");
			command.setLeaveRequests(leaveRequestService.getSupervisorSumittedLeaveRequests(currentSupervisorEmployeeNumber));			
		}
		modelMap.addAttribute("LeaveRequestSubmittalsCommand", command);
		
		return new ModelAndView("leaveRequestSubmittals", modelMap);
	}

	@RequestMapping(method=RequestMethod.POST, value = "/prevSupervisorLevel")
	public ModelAndView prevSupervisorLevel(@ModelAttribute("LeaveRequestSubmittalsCommand") LeaveRequestSubmittalsCommand command, HttpServletRequest request, BindingResult result) {
		
		command.getSupervisorChain().remove(command.getSupervisorChainLevel());
		int prevSupervisorLevel = command.getSupervisorChainLevel() - 1;
		command.setSupervisorChainLevel(prevSupervisorLevel);
		LeaveEmployeeData prevLevelSupervisor = command.getSupervisorChain().get(prevSupervisorLevel);
		boolean supervisorsOnly=true; // true indicates only those direct reports who are supervisors are to be returned
		boolean excludeTempApprovers=false; // false indicates that non-supervisors who have submittals to process should be added to the list of supervisors
		command.setDirectReportEmployees(leaveRequestService.getSupervisorDirectReports(prevLevelSupervisor.getEmployeeNumber(), 
				command.getLeaveParameters().isUsePMIS(), supervisorsOnly, excludeTempApprovers));
		command.setSelectedDirectReportEmployeeNumber("");
		command.setLeaveRequests(leaveRequestService.getSupervisorSumittedLeaveRequests(prevLevelSupervisor.getEmployeeNumber()));
		
		ModelMap modelMap = new ModelMap();
		modelMap.addAttribute("LeaveRequestSubmittalsCommand", command);
		
		return new ModelAndView("leaveRequestSubmittals", modelMap);
	}
	
	@RequestMapping(method=RequestMethod.POST, value = "/save")
	public ModelAndView saveSubmittalActions(@ModelAttribute("LeaveRequestSubmittalsCommand") LeaveRequestSubmittalsCommand command, BindingResult result, HttpServletRequest request) {
		int status=0;
		boolean savePerformed=false;
		String saveMessage=null;
		HashMap<Integer, String> errorsHashMessage = new HashMap<Integer,String>();
		HashMap<Integer, LeaveRequest> errorsHashRequest = new HashMap<Integer,LeaveRequest>();
		for (LeaveRequest leaveRequest : command.getLeaveRequests()) {
			LeaveEmployeeData approver = leaveRequestService.getEmployeeData(((String) request.getSession().getAttribute("currentUser_employeeNumber")).trim());
			LeaveEmployeeData employee = leaveRequestService.getEmployeeData(leaveRequest.getEmployeeNumber());
			String url = command.getLeaveParameters().getUrlEAHome().trim();
			
			Integer leaveIdInteger = new Integer(leaveRequest.getId());
			boolean error=false;
			if (leaveRequest.getApproverAction().equals(LeaveRequest.APPROVER_ACTION_APPROVE)) {
				savePerformed = true;
				try {
					String leaveStatus = leaveRequestService.getLeaveReqStatus(leaveRequest.getId());
					if(leaveStatus.equals("P")) {
						status = leaveRequestService.approveRequest(approver, employee, leaveRequest, command.getLeaveParameters().getStandardHours(), command.getLeaveParameters().isIgnoreCutoffDates(), url);
					}
					else {
						((Errors)result).rejectValue("leaveRequests[0].approverAction", "-1", "Leave Request for " + employee.getFullNameTitleCase() + " could not be processed due to a status change.");
						error = true;
					}
					
					if (status != LeaveRequestService.REQUEST_SERVICE_SUCCESS) {
						errorsHashMessage.put(leaveIdInteger, "Approve failed; approve service error.");
						error = true;
					}
				} catch (LeaveRequestException e) {
					if (e.getCode() == LeaveRequestException.SERVICE_SUBMITTAL_NO_PAYDATE) {
						errorsHashMessage.put(leaveIdInteger, "Approve failed; "+e.getMessage());
						error = true;
					}
				} catch (Exception e) {
					errorsHashMessage.put(leaveIdInteger, "Approve failed; database error.");
					error = true;
				}
				if (error) {
					errorsHashRequest.put(leaveIdInteger, leaveRequest);
				}
			} else if (leaveRequest.getApproverAction().equals(LeaveRequest.APPROVER_ACTION_DISAPPROVE)) {
				savePerformed = true;
				status = leaveRequestService.disapproveRequest(approver, employee, leaveRequest, url);
				if (status != LeaveRequestService.REQUEST_SERVICE_SUCCESS) {
					errorsHashMessage.put(leaveIdInteger, "Disapprove failed; database error.");
					errorsHashRequest.put(leaveIdInteger, leaveRequest);
				}
			}
		}
		
		// repopulate the directReportEmployees which are the options in the select dropdown; no hidden fields were defined for this data in the jsp page
		boolean supervisorsOnly=true; // true indicates only those direct reports who are supervisors are to be returned
		boolean excludeTempApprovers=false; // false indicates that non-supervisors who have submittals to process should be added to the list of supervisors
		String currentLevelSupervisorEmpNumber = command.getSupervisorChain().get(command.getSupervisorChainLevel()).getEmployeeNumber();
		command.setDirectReportEmployees(leaveRequestService.getSupervisorDirectReports(currentLevelSupervisorEmpNumber, 
				command.getLeaveParameters().isUsePMIS(), supervisorsOnly, excludeTempApprovers));

		// repopulate the leave requests
		command.setLeaveRequests(leaveRequestService.getSupervisorSumittedLeaveRequests(currentLevelSupervisorEmpNumber));
		
		if (savePerformed) {
			if (errorsHashMessage.size() == 0) {
				saveMessage = "Save Successful";
			} else {
				int index = 0;
				for (LeaveRequest leaveRequest : command.getLeaveRequests()) {
					Integer leaveIdInteger = new Integer(leaveRequest.getId());
					LeaveRequest errorRequest = errorsHashRequest.get(leaveIdInteger);
					if (errorRequest != null) {
						leaveRequest.setApproverAction(errorRequest.getApproverAction());
						leaveRequest.setApproverComment(errorRequest.getApproverComment());
						((Errors)result).rejectValue("leaveRequests["+index+"].approverAction", "-1", errorsHashMessage.get(leaveIdInteger));
					}
					index++;
				}				
			}
		} else {
			saveMessage="No changes made";
		}
		

		ModelMap modelMap = new ModelMap();
		modelMap.addAttribute("LeaveRequestSubmittalsCommand", command);
		modelMap.addAttribute("saveSuccess",saveMessage);
		return new ModelAndView("leaveRequestSubmittals", modelMap);
	}

}
