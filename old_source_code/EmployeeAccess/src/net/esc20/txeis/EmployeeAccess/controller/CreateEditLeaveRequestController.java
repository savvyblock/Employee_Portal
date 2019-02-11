package net.esc20.txeis.EmployeeAccess.controller;

import java.io.IOException;
import java.io.StringReader;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import net.esc20.txeis.EmployeeAccess.domain.LeaveRequestCommand;
import net.esc20.txeis.EmployeeAccess.domainobject.DatabaseContextHolder;
import net.esc20.txeis.EmployeeAccess.domainobject.LeaveEmployeeData;
import net.esc20.txeis.EmployeeAccess.domainobject.LeaveInfo;
import net.esc20.txeis.EmployeeAccess.domainobject.LeaveRequest;
import net.esc20.txeis.EmployeeAccess.domainobject.reference.Frequency;
import net.esc20.txeis.EmployeeAccess.service.LeaveRequestService;
import net.esc20.txeis.EmployeeAccess.service.LeaveService;
import net.esc20.txeis.EmployeeAccess.service.api.IMailUtilService;
import net.esc20.txeis.EmployeeAccess.validator.CreateEditLeaveRequestValidator;
import net.sf.json.util.JSONUtils;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
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
import org.springframework.web.context.ContextLoader;
import org.springframework.web.servlet.ModelAndView;

@Controller
@SessionAttributes("LeaveRequestCommand")
@RequestMapping(value = "/leave/createEditLeaveRequest")
public class CreateEditLeaveRequestController {
	
	@Autowired
	private LeaveRequestService leaveRequestService;

	@Autowired
	private CreateEditLeaveRequestValidator validator;
		
	@Autowired
	private LeaveService leaveService;
	
	@RequestMapping(method = RequestMethod.GET)
	public String getForm(Model model, HttpServletRequest request) {
		
		LeaveRequestCommand command = new LeaveRequestCommand();
		command.setUserEmpNumber(((String) request.getSession().getAttribute("currentUser_employeeNumber")).trim());
		command.setLeaveParameters(leaveRequestService.getLeaveParams());
		command.setSelectedPayFrequencyCode("");
		command.setUserPayFrequencies(leaveRequestService.getUserPayFrequencies(command.getUserEmpNumber()));
		LeaveEmployeeData supervisor = leaveRequestService.getFirstLineSupervisor(command.getUserEmpNumber(), command.getLeaveParameters().isUsePMIS());
		if (supervisor == null) {
			command.setSupervisorEmpNumber("");
		} else {
			command.setSupervisorEmpNumber(supervisor.getEmployeeNumber());
		}
		
		// before retrieving the employee's list of unprocessed requests,
		// update the status of any of the employee's leave request that are are currently set to pending payroll (unprocessed) ... or payroll in process or approved
//		leaveRequestService.updatePendingPayrollLeaveStatuses(command.getUserEmpNumber());
		
		if (command.getUserPayFrequencies()!=null) {
			
			if (command.getUserPayFrequencies().size() > 0) {
				command.setSelectedPayFrequencyCode(command.getUserPayFrequencies().get(0).getCode());
				command.setLeaveRequests(leaveRequestService.getEmployeeUnprocessedLeaveRequests(command.getUserEmpNumber(), command.getUserPayFrequencies().get(0).getCode()));
	
				Map<Frequency,List<LeaveInfo>> leaveInfoMap = leaveService.retrieveLeaveInfos(command.getUserEmpNumber(), false);
				List<LeaveInfo> leaveInfos = leaveInfoMap.get(Frequency.getFrequency(command.getUserPayFrequencies().get(0).getCode()));
				if (leaveInfos == null) {
					leaveInfos = new ArrayList<LeaveInfo>();
				}
				command.setLeaveInfos(leaveInfos);
			}
		}
		
		if (command.getUserPayFrequencies()!=null && command.getUserPayFrequencies().size()>0){
			command.setAbsenceReasons(leaveRequestService.getAbsenceReasons(command.getUserPayFrequencies().get(0).getCode(), command.getUserEmpNumber(), ""));
			command.setLeaveTypes(leaveRequestService.getLeaveTypes(command.getUserPayFrequencies().get(0).getCode(), command.getUserEmpNumber(), ""));
		}
		ArrayList<LeaveRequest> emptyRow = new ArrayList<LeaveRequest>();
		LeaveRequest emptyRequest = new LeaveRequest();
		emptyRow.add(emptyRequest);
		command.setEmptyLeaveRequestRow(emptyRow);
		
		model.addAttribute("LeaveRequestCommand", command);
		model.addAttribute("message",(command.getLeaveParameters().getMessageLeaveRequest()==null ? "" : command.getLeaveParameters().getMessageLeaveRequest()));
		return "createEditRequest";
	}

	@RequestMapping(method=RequestMethod.POST, value = "/payrollChange")
	public ModelAndView payrollChange(@ModelAttribute("LeaveRequestCommand") LeaveRequestCommand command, HttpServletRequest request, BindingResult result) {
		ModelMap modelMap = new ModelMap();
		
		command.setUserPayFrequencies(leaveRequestService.getUserPayFrequencies(command.getUserEmpNumber()));
		command.setLeaveRequests(leaveRequestService.getEmployeeUnprocessedLeaveRequests(command.getUserEmpNumber(), command.getSelectedPayFrequencyCode()));
		command.setAbsenceReasons(leaveRequestService.getAbsenceReasons(command.getSelectedPayFrequencyCode(), command.getUserEmpNumber(), ""));
		command.setLeaveTypes(leaveRequestService.getLeaveTypes(command.getSelectedPayFrequencyCode(), command.getUserEmpNumber(), ""));

		Map<Frequency,List<LeaveInfo>> leaveInfoMap = leaveService.retrieveLeaveInfos(command.getUserEmpNumber(), false);
		List<LeaveInfo> leaveInfos = leaveInfoMap.get(Frequency.getFrequency(command.getSelectedPayFrequencyCode()));
		if (leaveInfos == null) {
			leaveInfos = new ArrayList<LeaveInfo>();
		}
		command.setLeaveInfos(leaveInfos);

		modelMap.addAttribute("LeaveRequestCommand", command);
		modelMap.addAttribute("message",(command.getLeaveParameters().getMessageLeaveRequest()==null ? "" : command.getLeaveParameters().getMessageLeaveRequest()));
		
		return new ModelAndView("createEditRequest", modelMap);
	}
	
	@RequestMapping(method = RequestMethod.POST, value = "/createRequests")
// 	public ModelAndView createLeaveRequests(@ModelAttribute("LeaveRequestCommand") LeaveRequestCommand command, HttpServletRequest request, BindingResult result, @RequestBody LeaveRequest[] leaveRequestsArray) {
	public ModelAndView createLeaveRequests(@ModelAttribute("LeaveRequestCommand") LeaveRequestCommand command, HttpServletRequest request, BindingResult result, @RequestBody Map<String,Object> requestBodyMap) {
		
		ModelMap modelMap = new ModelMap();

		String selectedPayFrequencyCode = (String)requestBodyMap.get("payFrequency");
		LeaveRequest[] leaveRequestsArray = null;
		try {
			ObjectMapper mapper = new ObjectMapper();
			byte[] json = mapper.writeValueAsBytes(requestBodyMap.get("leaveRequests"));
			leaveRequestsArray = (LeaveRequest[])  mapper.readValue(json, LeaveRequest[].class);
		} catch (JsonGenerationException e) {
			e.printStackTrace();
		} catch (JsonParseException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}		
		command.setEditLeaveRequests(new ArrayList<LeaveRequest>(Arrays.asList(leaveRequestsArray)));
		validator.validate(command, result);
		
		if (result.getFieldErrorCount()==0 && result.getGlobalErrorCount()==0) {
			LeaveEmployeeData firstLineSupervisor = leaveRequestService.getFirstLineSupervisor(command.getUserEmpNumber(), command.getLeaveParameters().isUsePMIS());
			LeaveEmployeeData employeeData = leaveRequestService.getEmployeeData(command.getUserEmpNumber());
			
			for (LeaveRequest leaveRequest : leaveRequestsArray) {
				if (!leaveRequest.isEmpty()) {
					leaveRequest.setEmployeeNumber(command.getUserEmpNumber());
					leaveRequest.setPayFrequency(selectedPayFrequencyCode);
					leaveRequest.setStatus(LeaveRequest.REQUEST_STATUS_PENDING_APPROVAL);
					
					leaveRequestService.addLeaveRequest(leaveRequest, firstLineSupervisor, employeeData);

					// send email to first line supervisor
					if (firstLineSupervisor.getEmailAddress().trim().length()>0) {
						String url = command.getLeaveParameters().getUrlEAHome().trim();
						String message = leaveRequestService.getMessageBodyApprovalNotification (firstLineSupervisor.getFullNameTitleCase(), 
								employeeData.getFullNameTitleCase(), employeeData.getEmployeeNumber(),
								leaveRequest.getFromDate(), leaveRequest.getToDate(), 
								leaveRequest.getFromHour()+":"+leaveRequest.getFromMinute()+" "+leaveRequest.getFromAmPm().toLowerCase(),
								leaveRequest.getToHour()+":"+leaveRequest.getToMinute()+" "+leaveRequest.getToAmPm().toLowerCase(), url);
						String subject = "Leave request submitted for "+employeeData.getFullNameTitleCase();
						leaveRequestService.sendEmail(subject, firstLineSupervisor.getEmailAddress(), message);
					}
				}
			}
		}
		
		return new ModelAndView("jsonView", modelMap);
	}

	@RequestMapping(method = RequestMethod.POST, value = "/editRequests")
	public ModelAndView editLeaveRequests(@ModelAttribute("LeaveRequestCommand") LeaveRequestCommand command, HttpServletRequest request, BindingResult result, @RequestBody Map<String,Object> requestBodyMap) {
		ModelMap modelMap = new ModelMap();
		
		String selectedPayFrequencyCode = (String)requestBodyMap.get("payFrequency");
		LeaveRequest[] leaveRequestsArray = null;
		try {
			ObjectMapper mapper = new ObjectMapper();
			byte[] json = mapper.writeValueAsBytes(requestBodyMap.get("leaveRequests"));
			leaveRequestsArray = (LeaveRequest[])  mapper.readValue(json, LeaveRequest[].class);
		} catch (JsonGenerationException e) {
			e.printStackTrace();
		} catch (JsonParseException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}		
		command.setEditLeaveRequests(new ArrayList<LeaveRequest>(Arrays.asList(leaveRequestsArray)));

		validator.validate(command, result);
				
		if (result.getFieldErrorCount()==0 && result.getGlobalErrorCount()==0) {
			LeaveEmployeeData firstLineSupervisor = leaveRequestService.getFirstLineSupervisor(command.getUserEmpNumber(), command.getLeaveParameters().isUsePMIS());
			LeaveEmployeeData employeeData = leaveRequestService.getEmployeeData(command.getUserEmpNumber());
			
			for (LeaveRequest leaveRequest : leaveRequestsArray) {
				leaveRequest.setEmployeeNumber(command.getUserEmpNumber());
				leaveRequest.setPayFrequency(selectedPayFrequencyCode);
				leaveRequest.setStatus(LeaveRequest.REQUEST_STATUS_PENDING_APPROVAL);
				leaveRequestService.updateLeaveRequest(leaveRequest, firstLineSupervisor, employeeData);
				
				// send email to first line supervisor
				if (firstLineSupervisor.getEmailAddress().trim().length()>0) {
					String url = command.getLeaveParameters().getUrlEAHome().trim();
					String message = leaveRequestService.getMessageBodyApprovalNotification (firstLineSupervisor.getFullNameTitleCase(), 
							employeeData.getFullNameTitleCase(), employeeData.getEmployeeNumber(),
							leaveRequest.getFromDate(), leaveRequest.getToDate(), 
							leaveRequest.getFromHour()+":"+leaveRequest.getFromMinute()+" "+leaveRequest.getFromAmPm().toLowerCase(),
							leaveRequest.getToHour()+":"+leaveRequest.getToMinute()+" "+leaveRequest.getToAmPm().toLowerCase(), url);
					String subject = "Leave request submitted for "+employeeData.getFullNameTitleCase();
					leaveRequestService.sendEmail(subject, firstLineSupervisor.getEmailAddress(), message);
				}				
			}
		}
		return new ModelAndView("jsonView", modelMap);
	}

	@RequestMapping(method = RequestMethod.POST, value = "/deleteRequests")
	public ModelAndView deleteLeaveRequests(@ModelAttribute("LeaveRequestCommand") LeaveRequestCommand command, HttpServletRequest request, BindingResult result, @RequestBody LeaveRequest[] leaveRequestsArray) {
		ModelMap modelMap = new ModelMap();
		LeaveEmployeeData employeeData = leaveRequestService.getEmployeeData(command.getUserEmpNumber());
		LeaveEmployeeData supervisorData = leaveRequestService.getEmployeeData(command.getSupervisorEmpNumber());
		
		validator.validate(leaveRequestsArray, result);

		if (result.getFieldErrorCount()==0 && result.getGlobalErrorCount()==0) {
			for (LeaveRequest leaveRequest : leaveRequestsArray) {
				leaveRequestService.deleteLeaveRequest(leaveRequest, employeeData, supervisorData, employeeData, command.getLeaveParameters().getUrlEAHome());
			}
		}

		return new ModelAndView("jsonView", modelMap);
	}

	@RequestMapping(method = RequestMethod.GET, value = "/adjustLeaveTypeAbsenceReason")
	public ModelAndView adjustLeaveTypeAbsenceReason(@ModelAttribute("LeaveRequestCommand") LeaveRequestCommand command, 
			@RequestParam("leaveType") String leaveType,
			@RequestParam("absenceReason") String absenceReason
			) {
		ModelMap modelMap = new ModelMap();
		command.setLeaveTypes(leaveRequestService.getLeaveTypes(command.getUserPayFrequencies().get(0).getCode(), command.getUserEmpNumber(), absenceReason));
		command.setAbsenceReasons(leaveRequestService.getAbsenceReasons(command.getUserPayFrequencies().get(0).getCode(), command.getUserEmpNumber(), leaveType));

		modelMap.addAttribute("LeaveRequestCommand", command);
		
		return new ModelAndView("jsonView", modelMap);
	}

}
