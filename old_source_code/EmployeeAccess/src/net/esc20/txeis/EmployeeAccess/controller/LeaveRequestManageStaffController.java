package net.esc20.txeis.EmployeeAccess.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import net.esc20.txeis.EmployeeAccess.Exception.LeaveRequestException;
import net.esc20.txeis.EmployeeAccess.domain.LeaveRequestManageStaffCommand;
import net.esc20.txeis.EmployeeAccess.domainobject.LeaveEmployeeData;
import net.esc20.txeis.EmployeeAccess.domainobject.LeaveInfo;
import net.esc20.txeis.EmployeeAccess.domainobject.LeaveRequest;
import net.esc20.txeis.EmployeeAccess.domainobject.reference.AbsenceReason;
import net.esc20.txeis.EmployeeAccess.domainobject.reference.Frequency;
import net.esc20.txeis.EmployeeAccess.domainobject.reference.LeaveType;
import net.esc20.txeis.EmployeeAccess.domainobject.reference.PayFrequency;
import net.esc20.txeis.EmployeeAccess.service.LeaveRequestService;
import net.esc20.txeis.EmployeeAccess.service.LeaveService;
import net.esc20.txeis.EmployeeAccess.service.api.IMailUtilService;
import net.esc20.txeis.EmployeeAccess.validator.LeaveRequestManagerStaffValidator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

@Controller
@SessionAttributes("LeaveRequestManageStaffCommand")
@RequestMapping(value = "/leave/leaveRequestManageStaff")
public class LeaveRequestManageStaffController {
	
	@Autowired
	private LeaveRequestService leaveRequestService;

	@Autowired
	private LeaveRequestManagerStaffValidator validator;
	
	private IMailUtilService mailUtilService;

	@Autowired
	private LeaveService leaveService;

	@RequestMapping(method = RequestMethod.GET)
	public String getForm(Model model, HttpServletRequest request) {
		
		LeaveRequestManageStaffCommand command = new LeaveRequestManageStaffCommand();	
		
		command.setLeaveParameters(leaveRequestService.getLeaveParams());
		command.setSelectedPayFrequencyCode(null);
		command.setUserPayFrequencies(new ArrayList<PayFrequency>());
		
		boolean supervisorsOnly=false; // false indicates all direct reports are to be returned
		boolean excludeTempApprovers=false; // since supervisorsOnly is false, the value of this field is never checked
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
		
		ArrayList<LeaveRequest> emptyRow = new ArrayList<LeaveRequest>();
		LeaveRequest emptyRequest = new LeaveRequest();
		emptyRow.add(emptyRequest);
		command.setEmptyLeaveRequestRow(emptyRow);
		
		model.addAttribute("LeaveRequestManageStaffCommand", command);
		return "leaveRequestManageStaff";
	}

	@RequestMapping(method=RequestMethod.POST, value = "/nextSupervisorLevel")
	public ModelAndView nextSupervisorLevel(@ModelAttribute("LeaveRequestManageStaffCommand") LeaveRequestManageStaffCommand command, HttpServletRequest request, BindingResult result) {
		ModelMap modelMap = new ModelMap();
		
		// set the value of supervisor chain level; in some situations (such as on a refresh) the value is not accurate
		int currentLevel = command.getSupervisorChain().size() - 1;
		command.setSupervisorChainLevel(currentLevel);			

		LeaveEmployeeData nextLevelSupervisor=null;
		// insure the selected employee is not the current supervisor ... this happens if the user does
		// a refresh immediately after clicking on "Next Level"
		String currentSupervisorEmployeeNumber = command.getSupervisorChain().get(command.getSupervisorChainLevel()).getEmployeeNumber();
		String nextLevelSupervisorEmployeeNumber = command.getSelectedDirectReportEmployeeNumber();
		if (nextLevelSupervisorEmployeeNumber!=null && 
			nextLevelSupervisorEmployeeNumber.trim().length()>0 && 
			!currentSupervisorEmployeeNumber.equals(nextLevelSupervisorEmployeeNumber)) {
			for (LeaveEmployeeData leaveEmployeeData : command.getDirectReportEmployees()) {
				if (leaveEmployeeData.getEmployeeNumber().equals(nextLevelSupervisorEmployeeNumber)) {
					nextLevelSupervisor = leaveEmployeeData;
					break;
				}
			}
		}
			
		// nextLevelSupervisor will be null if the user does a refresh immediately after clicking on "Next Level"
		if (nextLevelSupervisor != null) {
			int nextSupervisorLevel = command.getSupervisorChainLevel() + 1;
			command.setSupervisorChainLevel(nextSupervisorLevel);
			command.getSupervisorChain().add(nextSupervisorLevel, nextLevelSupervisor);
			boolean supervisorsOnly=false; // false indicates all direct reports are to be returned
			boolean excludeTempApprovers=false; // since supervisorsOnly is false, the value of this field is never checked
			command.setDirectReportEmployees(leaveRequestService.getSupervisorDirectReports(nextLevelSupervisor.getEmployeeNumber(), 
					command.getLeaveParameters().isUsePMIS(), supervisorsOnly, excludeTempApprovers));
		} 
		command.setSelectedDirectReportEmployeeNumber("");
		command.setSelectedPayFrequencyCode(null);
		command.setUserPayFrequencies(new ArrayList<PayFrequency>());
		command.setLeaveRequests(new ArrayList<LeaveRequest>());
		
		modelMap.addAttribute("LeaveRequestManageStaffCommand", command);
		
		return new ModelAndView("leaveRequestManageStaff", modelMap);
	}

	@RequestMapping(method=RequestMethod.POST, value = "/prevSupervisorLevel")
	public ModelAndView prevSupervisorLevel(@ModelAttribute("LeaveRequestManageStaffCommand") LeaveRequestManageStaffCommand command, HttpServletRequest request, BindingResult result) {
		ModelMap modelMap = new ModelMap();
		
		command.getSupervisorChain().remove(command.getSupervisorChainLevel());
		int prevSupervisorLevel = command.getSupervisorChainLevel() - 1;
		command.setSupervisorChainLevel(prevSupervisorLevel);
		LeaveEmployeeData prevLevelSupervisor = command.getSupervisorChain().get(prevSupervisorLevel);
		
		boolean supervisorsOnly=false; // false indicates all direct reports are to be returned
		boolean excludeTempApprovers=false; // since supervisorsOnly is false, the value of this field is never checked
		command.setDirectReportEmployees(leaveRequestService.getSupervisorDirectReports(prevLevelSupervisor.getEmployeeNumber(), 
				command.getLeaveParameters().isUsePMIS(), supervisorsOnly, excludeTempApprovers));
		command.setSelectedDirectReportEmployeeNumber("");
		command.setSelectedPayFrequencyCode(null);
		command.setUserPayFrequencies(new ArrayList<PayFrequency>());
		command.setLeaveRequests(new ArrayList<LeaveRequest>());
		
		modelMap.addAttribute("LeaveRequestManageStaffCommand", command);
		
		return new ModelAndView("leaveRequestManageStaff", modelMap);
	}

	@RequestMapping(method=RequestMethod.POST, value = "/selectDirectReport")
	public ModelAndView selectDirectReport(@ModelAttribute("LeaveRequestManageStaffCommand") LeaveRequestManageStaffCommand command, HttpServletRequest request, BindingResult result) {
		ModelMap modelMap = new ModelMap();
		
		// get user pay frequency
		command.setSelectedPayFrequencyCode(null);
		command.setUserPayFrequencies(leaveRequestService.getUserPayFrequencies(command.getSelectedDirectReportEmployeeNumber()));
		boolean supervisorsOnly=false; // false indicates all direct reports are to be returned
		boolean excludeTempApprovers=false; // since supervisorsOnly is false, the value of this field is never checked
		String currentLevelSupervisorEmpNumber = command.getSupervisorChain().get(command.getSupervisorChainLevel()).getEmployeeNumber();
		command.setDirectReportEmployees(leaveRequestService.getSupervisorDirectReports(currentLevelSupervisorEmpNumber, 
				command.getLeaveParameters().isUsePMIS(), supervisorsOnly, excludeTempApprovers));
		if (command.getUserPayFrequencies()!=null) {
			// initialize selected pay frequency, absence reasons, leave types, and leave requests
			if (command.getUserPayFrequencies().size() > 0) {
				// before retrieving the employee's list of requests,
				// update the status of any of the employee's leave request that are are currently set to pending payroll (unprocessed)
//				leaveRequestService.updatePendingPayrollLeaveStatuses(command.getSelectedDirectReportEmployeeNumber());

				command.setSelectedPayFrequencyCode(command.getUserPayFrequencies().get(0).getCode());
				command.setAbsenceReasons(leaveRequestService.getAbsenceReasons(command.getUserPayFrequencies().get(0).getCode(), command.getSelectedDirectReportEmployeeNumber(), ""));
				command.setLeaveTypes(leaveRequestService.getLeaveTypes(command.getUserPayFrequencies().get(0).getCode(), command.getSelectedDirectReportEmployeeNumber(), ""));

				// before retrieving the employee's list of requests,
				// update the status of any of the employee's leave request that are are currently set to pending payroll (unprocessed)
//				leaveRequestService.updatePendingPayrollLeaveStatuses(command.getSelectedDirectReportEmployeeNumber());
				command.setLeaveRequests(leaveRequestService.getEmployeeLeaveRequests(command.getSelectedDirectReportEmployeeNumber(), 
						command.getSelectedPayFrequencyCode(), "", ""));

				// initialize leave balance summary for selected employee/pay frequency
				Map<Frequency,List<LeaveInfo>> leaveInfoMap = leaveService.retrieveLeaveInfos(command.getSelectedDirectReportEmployeeNumber(), false);
				List<LeaveInfo> leaveInfos = leaveInfoMap.get(Frequency.getFrequency(command.getUserPayFrequencies().get(0).getCode()));
				if (leaveInfos == null) {
					leaveInfos = new ArrayList<LeaveInfo>();
				}
				command.setLeaveInfos(leaveInfos);
			
			} else {
				command.setSelectedPayFrequencyCode(null);
				command.setAbsenceReasons(new ArrayList<AbsenceReason>());
				command.setLeaveTypes(new ArrayList<LeaveType>());
				command.setLeaveRequests(new ArrayList<LeaveRequest>());
			}
		}
		
		// initialize empty request row
		ArrayList<LeaveRequest> emptyRow = new ArrayList<LeaveRequest>();
		LeaveRequest emptyRequest = new LeaveRequest();
		emptyRow.add(emptyRequest);
		command.setEmptyLeaveRequestRow(emptyRow);
		
		// reset any filter values
		command.setFromDateFilterString("  -  -    ");
		command.setToDateFilterString("  -  -    ");
		modelMap.addAttribute("LeaveRequestManageStaffCommand", command);
		
		return new ModelAndView("leaveRequestManageStaff", modelMap);
	}

	@RequestMapping(method=RequestMethod.POST, value = "/filterRequests")
	public ModelAndView filterDirectReportLeaveRequests(@ModelAttribute("LeaveRequestManageStaffCommand") LeaveRequestManageStaffCommand command, HttpServletRequest request, BindingResult result) {
		ModelMap modelMap = new ModelMap();
		
		String fromDateFilterString = command.getFromDateFilterString();
		String toDateFilterString = command.getToDateFilterString();
		Date fromDateObj=null;
		Date toDateObj=null;
		SimpleDateFormat dateFormat = new SimpleDateFormat("MM-dd-yyyy");
		try {
			fromDateObj = dateFormat.parse(fromDateFilterString);
		} catch (Exception e) {
			fromDateFilterString = "";
		}
		try {
			toDateObj = dateFormat.parse(toDateFilterString);
		} catch (Exception e) {
			toDateFilterString = "";
		}
		if (fromDateObj!=null && toDateObj!=null) {
			if (fromDateObj.compareTo(toDateObj)>0) {
				// dates out of order
				command.setFromDateFilterString(toDateFilterString);
				command.setToDateFilterString(fromDateFilterString);
				fromDateFilterString = command.getFromDateFilterString();
				toDateFilterString = command.getToDateFilterString();
			}
		}
		command.setLeaveRequests(leaveRequestService.getEmployeeLeaveRequests(command.getSelectedDirectReportEmployeeNumber(), 
				command.getSelectedPayFrequencyCode(), fromDateFilterString, toDateFilterString));

		modelMap.addAttribute("LeaveRequestManageStaffCommand", command);
		return new ModelAndView("leaveRequestManageStaff", modelMap);
	}
	
	
	@RequestMapping(method=RequestMethod.POST, value = "/payrollChange")
	public ModelAndView payrollChange(@ModelAttribute("LeaveRequestManageStaffCommand") LeaveRequestManageStaffCommand command, HttpServletRequest request, BindingResult result) {
		ModelMap modelMap = new ModelMap();
		
		command.setLeaveRequests(leaveRequestService.getEmployeeLeaveRequests(command.getSelectedDirectReportEmployeeNumber(), 
				command.getSelectedPayFrequencyCode(), "", ""));
		command.setAbsenceReasons(leaveRequestService.getAbsenceReasons(command.getSelectedPayFrequencyCode(), command.getSelectedDirectReportEmployeeNumber(), ""));
		command.setLeaveTypes(leaveRequestService.getLeaveTypes(command.getSelectedPayFrequencyCode(), command.getSelectedDirectReportEmployeeNumber(), ""));
		boolean supervisorsOnly=false; // false indicates all direct reports are to be returned
		boolean excludeTempApprovers=false; // since supervisorsOnly is false, the value of this field is never checked
		String currentLevelSupervisorEmpNumber = command.getSupervisorChain().get(command.getSupervisorChainLevel()).getEmployeeNumber();
		command.setDirectReportEmployees(leaveRequestService.getSupervisorDirectReports(currentLevelSupervisorEmpNumber, 
				command.getLeaveParameters().isUsePMIS(), supervisorsOnly, excludeTempApprovers));

		// initialize leave balance summary for selected employee/pay frequency
		Map<Frequency,List<LeaveInfo>> leaveInfoMap = leaveService.retrieveLeaveInfos(command.getSelectedDirectReportEmployeeNumber(), false);
		List<LeaveInfo> leaveInfos = leaveInfoMap.get(Frequency.getFrequency(command.getUserPayFrequencies().get(0).getCode()));
		if (leaveInfos == null) {
			leaveInfos = new ArrayList<LeaveInfo>();
		}
		command.setLeaveInfos(leaveInfos);

		// reset any filter values
		command.setFromDateFilterString("  -  -    ");
		command.setToDateFilterString("  -  -    ");

		modelMap.addAttribute("LeaveRequestManageStaffCommand", command);
		
		return new ModelAndView("leaveRequestManageStaff", modelMap);
	}



	
	@RequestMapping(method = RequestMethod.POST, value = "/createRequests")
	public ModelAndView createLeaveRequests(@ModelAttribute("LeaveRequestManageStaffCommand") LeaveRequestManageStaffCommand command, HttpServletRequest request, BindingResult result, @RequestBody LeaveRequest[] leaveRequestsArray) {
		ModelMap modelMap = new ModelMap();
		
		command.setEditLeaveRequests(new ArrayList<LeaveRequest>(Arrays.asList(leaveRequestsArray)));
		validator.validate(command, result);
		
		if (result.getFieldErrorCount()==0 && result.getGlobalErrorCount()==0) {
			LeaveEmployeeData currentUser = leaveRequestService.getEmployeeData(((String) request.getSession().getAttribute("currentUser_employeeNumber")).trim());
			LeaveEmployeeData firstLineSupervisor = leaveRequestService.getFirstLineSupervisor(command.getSelectedDirectReportEmployeeNumber().trim(), 
				command.getLeaveParameters().isUsePMIS());
			LeaveEmployeeData employeeData = leaveRequestService.getEmployeeData(command.getSelectedDirectReportEmployeeNumber());
			String url = command.getLeaveParameters().getUrlEAHome().trim();
			for (LeaveRequest leaveRequest : leaveRequestsArray) {
				if (!leaveRequest.isEmpty()) {
					leaveRequest.setEmployeeNumber(command.getSelectedDirectReportEmployeeNumber().trim());
					leaveRequest.setPayFrequency(command.getSelectedPayFrequencyCode());
					leaveRequest.setStatus(LeaveRequest.REQUEST_STATUS_PENDING_APPROVAL);
					leaveRequest.setRequestCommentEmpNumber(currentUser.getEmployeeNumber());  // if a comment has been entered, it was entered by the supervisor
					
					leaveRequestService.addLeaveRequest(leaveRequest, firstLineSupervisor, employeeData);
					// send email to employee notifying them that a leave request was created on their behalf
					String message = leaveRequestService.getMessageBodyRequestModified2EmployeeNotification (currentUser.getFullNameTitleCase(), 
							employeeData.getFullNameTitleCase(),
							leaveRequest.getFromDate(), leaveRequest.getToDate(), 
							leaveRequest.getFromHour()+":"+leaveRequest.getFromMinute()+" "+leaveRequest.getFromAmPm().toLowerCase(),
							leaveRequest.getToHour()+":"+leaveRequest.getToMinute()+" "+leaveRequest.getToAmPm().toLowerCase(), url, true);
					String subject = "Leave request created and submitted on your behalf by "+currentUser.getFullNameTitleCase();
					leaveRequestService.sendEmail(subject, employeeData.getEmailAddress(), message);
					
					boolean sendEmail = true;
					if (firstLineSupervisor.getEmployeeNumber().equals(currentUser.getEmployeeNumber())) {
						// do auto approve
						leaveRequest.setApproverAction(LeaveRequest.APPROVER_ACTION_APPROVE);
						leaveRequest.setApproverComment("automatic approval");
						int status;
						String errorMessage="";
						try {
							status = leaveRequestService.approveRequest(currentUser, employeeData, leaveRequest, 
									command.getLeaveParameters().getStandardHours(), command.getLeaveParameters().isIgnoreCutoffDates(), url);
							if (status != LeaveRequestService.REQUEST_SERVICE_SUCCESS) {
								errorMessage ="Approve failed; approve service error.";
							} else {
								sendEmail = false;
							}
						} catch (LeaveRequestException e) {
							if (e.getCode() == LeaveRequestException.SERVICE_SUBMITTAL_NO_PAYDATE) {
								errorMessage = "Approve failed; "+e.getMessage();
							}
						} catch (Exception e) {
							errorMessage = "Approve failed; database error.";
						}					
					}
					if (sendEmail && firstLineSupervisor.getEmailAddress().trim().length()>0) {
						// send email to first line supervisor
						message = leaveRequestService.getMessageBodyApprovalNotification (firstLineSupervisor.getFullNameTitleCase(), 
								employeeData.getFullNameTitleCase(), employeeData.getEmployeeNumber(),
								leaveRequest.getFromDate(), leaveRequest.getToDate(), 
								leaveRequest.getFromHour()+":"+leaveRequest.getFromMinute()+" "+leaveRequest.getFromAmPm().toLowerCase(),
								leaveRequest.getToHour()+":"+leaveRequest.getToMinute()+" "+leaveRequest.getToAmPm().toLowerCase(), url);
						subject = "Leave request submitted for "+employeeData.getFullNameTitleCase();
						leaveRequestService.sendEmail(subject, firstLineSupervisor.getEmailAddress(), message);
					}
				}
			}
		}
		
		return new ModelAndView("jsonView", modelMap);
	}

	@RequestMapping(method = RequestMethod.POST, value = "/editRequests")
	public ModelAndView editLeaveRequests(@ModelAttribute("LeaveRequestManageStaffCommand") LeaveRequestManageStaffCommand command, HttpServletRequest request, BindingResult result, @RequestBody LeaveRequest[] leaveRequestsArray) {
		ModelMap modelMap = new ModelMap();
		
		command.setEditLeaveRequests(new ArrayList<LeaveRequest>(Arrays.asList(leaveRequestsArray)));
		validator.validate(command, result);
		
		if (result.getFieldErrorCount()==0 && result.getGlobalErrorCount()==0) {
			LeaveEmployeeData currentUser = leaveRequestService.getEmployeeData(((String) request.getSession().getAttribute("currentUser_employeeNumber")).trim());
			LeaveEmployeeData firstLineSupervisor = leaveRequestService.getFirstLineSupervisor(command.getSelectedDirectReportEmployeeNumber().trim(), 
					command.getLeaveParameters().isUsePMIS());
			LeaveEmployeeData employeeData = leaveRequestService.getEmployeeData(command.getSelectedDirectReportEmployeeNumber());
			String url = command.getLeaveParameters().getUrlEAHome().trim();
			for (LeaveRequest leaveRequest : leaveRequestsArray) {
				leaveRequest.setEmployeeNumber(command.getSelectedDirectReportEmployeeNumber().trim());
				leaveRequest.setPayFrequency(command.getUserPayFrequencies().get(0).getCode());
				leaveRequest.setStatus(LeaveRequest.REQUEST_STATUS_PENDING_APPROVAL);
				leaveRequest.setRequestCommentEmpNumber(currentUser.getEmployeeNumber());  // if a comment has been entered, it was entered by the supervisor
				
				leaveRequestService.updateLeaveRequest(leaveRequest, firstLineSupervisor, employeeData);
				// send email to employee notifying them that a leave request was modified on their behalf
				String message = leaveRequestService.getMessageBodyRequestModified2EmployeeNotification (currentUser.getFullNameTitleCase(), 
						employeeData.getFullNameTitleCase(),
						leaveRequest.getFromDate(), leaveRequest.getToDate(), 
						leaveRequest.getFromHour()+":"+leaveRequest.getFromMinute()+" "+leaveRequest.getFromAmPm().toLowerCase(),
						leaveRequest.getToHour()+":"+leaveRequest.getToMinute()+" "+leaveRequest.getToAmPm().toLowerCase(), url, false);
				String subject = "Leave request modified and resubmitted on your behalf by "+currentUser.getFullNameTitleCase();
				leaveRequestService.sendEmail(subject, employeeData.getEmailAddress(), message);
				
				boolean sendEmail = true;
				if (firstLineSupervisor.getEmployeeNumber().equals(currentUser.getEmployeeNumber())) {
					// do auto approve
					leaveRequest.setApproverAction(LeaveRequest.APPROVER_ACTION_APPROVE);
					leaveRequest.setApproverComment("automatic approval");
					int status;
					String errorMessage="";
					try {
						status = leaveRequestService.approveRequest(currentUser, employeeData, leaveRequest, 
								command.getLeaveParameters().getStandardHours(), command.getLeaveParameters().isIgnoreCutoffDates(), url);
						if (status != LeaveRequestService.REQUEST_SERVICE_SUCCESS) {
							errorMessage ="Approve failed; approve service error.";
						} else {
							sendEmail = false;
						}
					} catch (LeaveRequestException e) {
						if (e.getCode() == LeaveRequestException.SERVICE_SUBMITTAL_NO_PAYDATE) {
							errorMessage = "Approve failed; "+e.getMessage();
						}
					} catch (Exception e) {
						errorMessage = "Approve failed; database error.";
					}					
				}
				if (sendEmail && firstLineSupervisor.getEmailAddress().trim().length()>0) {
					// send email to first line supervisor
					message = leaveRequestService.getMessageBodyApprovalNotification (firstLineSupervisor.getFullNameTitleCase(), 
							employeeData.getFullNameTitleCase(), employeeData.getEmployeeNumber(),
							leaveRequest.getFromDate(), leaveRequest.getToDate(), 
							leaveRequest.getFromHour()+":"+leaveRequest.getFromMinute()+" "+leaveRequest.getFromAmPm().toLowerCase(),
							leaveRequest.getToHour()+":"+leaveRequest.getToMinute()+" "+leaveRequest.getToAmPm().toLowerCase(), url);
					subject = "Leave request submitted for "+employeeData.getFullNameTitleCase();
					leaveRequestService.sendEmail(subject, firstLineSupervisor.getEmailAddress(), message);
				}
			}
		}
//		modelMap.addAttribute("fieldErrors", result.getFieldErrors());
		return new ModelAndView("jsonView", modelMap);
	}

	@RequestMapping(method = RequestMethod.POST, value = "/deleteRequests")
	public ModelAndView editLeaveRequests(@ModelAttribute("LeaveRequestManageStaffCommand") LeaveRequestManageStaffCommand command, HttpServletRequest request, @RequestBody LeaveRequest[] leaveRequestsArray) {
		ModelMap modelMap = new ModelMap();
		
		LeaveEmployeeData employeeData = leaveRequestService.getEmployeeData(command.getSelectedDirectReportEmployeeNumber());
		LeaveEmployeeData currentUserEmployeeData = leaveRequestService.getEmployeeData(((String) request.getSession().getAttribute("currentUser_employeeNumber")).trim());
		LeaveEmployeeData employeeSupervisorData = null;
		if (command.getSupervisorChainLevel() > 0) {
			// the supervisor deleting the request is not the employee's immediate supervisor ... the immediate supervisor must be notified;
			// neeed the service call to get the employee's supervisor's email address
			employeeSupervisorData = leaveRequestService.getEmployeeData(command.getSupervisorChain().get(command.getSupervisorChainLevel()).getEmployeeNumber());
		} else {
			employeeSupervisorData = currentUserEmployeeData;
		}

		for (LeaveRequest leaveRequest : leaveRequestsArray) {
			leaveRequestService.deleteLeaveRequest(leaveRequest, employeeData, employeeSupervisorData, currentUserEmployeeData, command.getLeaveParameters().getUrlEAHome());
		}
		return new ModelAndView("jsonView", modelMap);
	}

	@RequestMapping(method = RequestMethod.GET, value = "/adjustLeaveTypeAbsenceReason")
	public ModelAndView adjustLeaveTypeAbsenceReason(@ModelAttribute("LeaveRequestManageStaffCommand") LeaveRequestManageStaffCommand command, 
			@RequestParam("leaveType") String leaveType,
			@RequestParam("absenceReason") String absenceReason
			) {
		ModelMap modelMap = new ModelMap();
		command.setLeaveTypes(leaveRequestService.getLeaveTypes(command.getSelectedPayFrequencyCode(), command.getSelectedDirectReportEmployeeNumber(), absenceReason));
		command.setAbsenceReasons(leaveRequestService.getAbsenceReasons(command.getSelectedPayFrequencyCode(), command.getSelectedDirectReportEmployeeNumber(), leaveType));
		modelMap.addAttribute("LeaveRequestManageStaffCommand", command);
		
		return new ModelAndView("jsonView", modelMap);
	}

}
