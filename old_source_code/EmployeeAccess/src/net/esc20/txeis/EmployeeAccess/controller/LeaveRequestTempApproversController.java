package net.esc20.txeis.EmployeeAccess.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import net.esc20.txeis.EmployeeAccess.domain.LeaveRequestCommand;
import net.esc20.txeis.EmployeeAccess.domain.LeaveRequestSubmittalsCommand;
import net.esc20.txeis.EmployeeAccess.domain.LeaveRequestTemporaryApproversCommand;
import net.esc20.txeis.EmployeeAccess.domainobject.LeaveEmployeeData;
import net.esc20.txeis.EmployeeAccess.domainobject.LeaveInfo;
import net.esc20.txeis.EmployeeAccess.domainobject.LeaveRequest;
import net.esc20.txeis.EmployeeAccess.domainobject.LeaveTemporaryApprover;
import net.esc20.txeis.EmployeeAccess.domainobject.reference.Frequency;
import net.esc20.txeis.EmployeeAccess.service.LeaveRequestService;
import net.esc20.txeis.EmployeeAccess.validator.LeaveRequestTempApproverValidator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

@Controller
@SessionAttributes("LeaveRequestTemporaryApproversCommand")
@RequestMapping(value = "/leave/leaveRequestTemporaryApprovers")
public class LeaveRequestTempApproversController {

	@Autowired
	private LeaveRequestService leaveRequestService;

	@Autowired
	private LeaveRequestTempApproverValidator validator;
	
	@RequestMapping(method = RequestMethod.GET)
	public String getForm(Model model, HttpServletRequest request) {

		LeaveRequestTemporaryApproversCommand command = new LeaveRequestTemporaryApproversCommand();

		command.setLeaveParameters(leaveRequestService.getLeaveParams());
		boolean supervisorsOnly=true; // true indicates only those direct reports who are supervisors are to be returned
		boolean excludeTempApprovers=true; // true indicates that direct reports who are not superviors but who are temp approvers should be excluded
		command.setDirectReportEmployees(leaveRequestService.getSupervisorDirectReports(
			((String) request.getSession().getAttribute("currentUser_employeeNumber")).trim(), 
			command.getLeaveParameters().isUsePMIS(), supervisorsOnly, excludeTempApprovers));
		command.setSelectedDirectReportEmployeeNumber("");

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

		command.setTemporaryApprovers(leaveRequestService.getTemporaryApprovers(((String) request.getSession().getAttribute("currentUser_employeeNumber")).trim()));
		
		model.addAttribute("LeaveRequestTemporaryApproversCommand", command);
		return "leaveRequestTemporaryApprovers";
	}

	@RequestMapping(method=RequestMethod.POST, value = "/nextSupervisorLevel")
	public ModelAndView nextSupervisorLevel(@ModelAttribute("LeaveRequestTemporaryApproversCommand") LeaveRequestTemporaryApproversCommand command, HttpServletRequest request, BindingResult result) {
		ModelMap modelMap = new ModelMap();
		
		int nextSupervisorLevel = command.getSupervisorChainLevel() + 1;
		command.setSupervisorChainLevel(nextSupervisorLevel);
		LeaveEmployeeData nextLevelSupervisor=null;
		String nextLevelSupervisorEmployeeNumber = command.getSelectedDirectReportEmployeeNumber();
		for (LeaveEmployeeData leaveEmployeeData : command.getDirectReportEmployees()) {
			if (leaveEmployeeData.getEmployeeNumber().equals(nextLevelSupervisorEmployeeNumber)) {
				nextLevelSupervisor = leaveEmployeeData;
				break;
			}
		}
		command.getSupervisorChain().add(nextSupervisorLevel, nextLevelSupervisor);
		boolean supervisorsOnly=true; // true indicates only those direct reports who are supervisors are to be returned
		boolean excludeTempApprovers=true; // true indicates that direct reports who are not superviors but who are temp approvers should be excluded
		command.setDirectReportEmployees(leaveRequestService.getSupervisorDirectReports(nextLevelSupervisor.getEmployeeNumber(), 
			command.getLeaveParameters().isUsePMIS(), supervisorsOnly, excludeTempApprovers));
		command.setSelectedDirectReportEmployeeNumber("");
		command.setTemporaryApprovers(leaveRequestService.getTemporaryApprovers(nextLevelSupervisor.getEmployeeNumber()));
		modelMap.addAttribute("LeaveRequestTemporaryApproversCommand", command);
		
		return new ModelAndView("leaveRequestTemporaryApprovers", modelMap);
	}

	@RequestMapping(method=RequestMethod.POST, value = "/prevSupervisorLevel")
	public ModelAndView prevSupervisorLevel(@ModelAttribute("LeaveRequestTemporaryApproversCommand") LeaveRequestTemporaryApproversCommand command, HttpServletRequest request, BindingResult result) {
		ModelMap modelMap = new ModelMap();
		
		command.getSupervisorChain().remove(command.getSupervisorChainLevel());
		int prevSupervisorLevel = command.getSupervisorChainLevel() - 1;
		command.setSupervisorChainLevel(prevSupervisorLevel);
		LeaveEmployeeData prevLevelSupervisor = command.getSupervisorChain().get(prevSupervisorLevel);
		boolean supervisorsOnly=true; // true indicates only those direct reports who are supervisors are to be returned
		boolean excludeTempApprovers=true; // true indicates that non-supervisors who have submittals to process should be excluded from the list of supervisors
		command.setDirectReportEmployees(leaveRequestService.getSupervisorDirectReports(prevLevelSupervisor.getEmployeeNumber(), 
			command.getLeaveParameters().isUsePMIS(), supervisorsOnly, excludeTempApprovers));
		command.setSelectedDirectReportEmployeeNumber("");
		command.setTemporaryApprovers(leaveRequestService.getTemporaryApprovers(prevLevelSupervisor.getEmployeeNumber()));
		modelMap.addAttribute("LeaveRequestTemporaryApproversCommand", command);
		
		return new ModelAndView("leaveRequestTemporaryApprovers", modelMap);
	}

	@RequestMapping(method=RequestMethod.POST, value = "/save")
	public ModelAndView saveTemporaryApprovers(@ModelAttribute("LeaveRequestTemporaryApproversCommand") LeaveRequestTemporaryApproversCommand command, BindingResult result, HttpServletRequest request) {

		String saveMessage = null;
		
		boolean supervisorsOnly=true; // true indicates only those direct reports who are supervisors are to be returned
		boolean excludeTempApprovers=true; // true indicates that direct reports who are not superviors but who are temp approvers should be excluded
		String currentLevelSupervisorEmpNumber = command.getSupervisorChain().get(command.getSupervisorChainLevel()).getEmployeeNumber();
		command.setDirectReportEmployees(leaveRequestService.getSupervisorDirectReports(currentLevelSupervisorEmpNumber, 
				command.getLeaveParameters().isUsePMIS(), supervisorsOnly, excludeTempApprovers));
		validator.validate(command, result);
		if (result.getFieldErrorCount() == 0) {
			boolean savePerformed=false;
			for (LeaveTemporaryApprover temporaryApprover : command.getTemporaryApprovers()) {
				if (temporaryApprover.getId() == 0) {
					// this row was added ... before adding it to the database, check if the user then indicated it is to be deleted
					// or if ther user never entered any data for the row
					if (!temporaryApprover.isDeleteIndicated() && !temporaryApprover.isIgnoreRow()) {
						String supervisorEmployeeNumber = command.getSupervisorChain().get(command.getSupervisorChainLevel()).getEmployeeNumber();
						leaveRequestService.addTemporaryApprover(temporaryApprover, supervisorEmployeeNumber);
						savePerformed = true;
					}
				} else if (temporaryApprover.isDeleteIndicated()) {
					leaveRequestService.deleteTemporaryApprover(temporaryApprover);
					savePerformed = true;
				} else if (temporaryApprover.isModified()) {
					leaveRequestService.updateTemporaryApprover(temporaryApprover);
					savePerformed = true;
				}
			}

			if (savePerformed) {
				saveMessage = "Save Successful";
			} else {
				saveMessage = "No changes made";
			}
			command.setTemporaryApprovers(leaveRequestService.getTemporaryApprovers(command.getSupervisorChain().get(command.getSupervisorChainLevel()).getEmployeeNumber()));
		}
		
		ModelMap modelMap = new ModelMap();
		modelMap.addAttribute("LeaveRequestTemporaryApproversCommand", command);
		modelMap.addAttribute("saveSuccess",saveMessage);
		
		
		return new ModelAndView("leaveRequestTemporaryApprovers", modelMap);
	}

}
